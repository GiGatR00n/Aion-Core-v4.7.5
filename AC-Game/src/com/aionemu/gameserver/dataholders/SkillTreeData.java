/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, ragarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.skillengine.model.SkillLearnTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ATracer
 */
@XmlRootElement(name = "skill_tree")
@XmlAccessorType(XmlAccessType.FIELD)
public class SkillTreeData {

    @XmlElement(name = "skill")
    private List<SkillLearnTemplate> skillTemplates;
    private final TIntObjectHashMap<ArrayList<SkillLearnTemplate>> templates = new TIntObjectHashMap<ArrayList<SkillLearnTemplate>>();
    private final TIntObjectHashMap<ArrayList<SkillLearnTemplate>> templatesById = new TIntObjectHashMap<ArrayList<SkillLearnTemplate>>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (SkillLearnTemplate template : skillTemplates) {
            addTemplate(template);
        }
        skillTemplates = null;
    }

    private void addTemplate(SkillLearnTemplate template) {
        Race race = template.getRace();
        if (race == null) {
            race = Race.PC_ALL;
        }

        int hash = makeHash(template.getClassId().ordinal(), race.ordinal(), template.getMinLevel());
        ArrayList<SkillLearnTemplate> value = templates.get(hash);
        if (value == null) {
            value = new ArrayList<SkillLearnTemplate>();
            templates.put(hash, value);
        }

        value.add(template);

        value = templatesById.get(template.getSkillId());
        if (value == null) {
            value = new ArrayList<SkillLearnTemplate>();
            templatesById.put(template.getSkillId(), value);
        }

        value.add(template);
    }

    /**
     * @return the templates
     */
    public TIntObjectHashMap<ArrayList<SkillLearnTemplate>> getTemplates() {
        return templates;
    }

    /**
     * Perform search for: - class specific skills (race = ALL) - class and race
     * specific skills - non-specific skills (race = ALL, class = ALL)
     *
     * @param playerClass
     * @param level
     * @param race
     * @return SkillLearnTemplate[]
     */
    public SkillLearnTemplate[] getTemplatesFor(PlayerClass playerClass, int level, Race race) {
        List<SkillLearnTemplate> newSkills = new ArrayList<SkillLearnTemplate>();

        List<SkillLearnTemplate> classRaceSpecificTemplates = templates.get(makeHash(playerClass.ordinal(), race.ordinal(),
                level));
        List<SkillLearnTemplate> classSpecificTemplates = templates.get(makeHash(playerClass.ordinal(),
                Race.PC_ALL.ordinal(), level));
        List<SkillLearnTemplate> generalTemplates = templates.get(makeHash(PlayerClass.ALL.ordinal(),
                Race.PC_ALL.ordinal(), level));

        if (classRaceSpecificTemplates != null) {
            newSkills.addAll(classRaceSpecificTemplates);
        }
        if (classSpecificTemplates != null) {
            newSkills.addAll(classSpecificTemplates);
        }
        if (generalTemplates != null) {
            newSkills.addAll(generalTemplates);
        }

        return newSkills.toArray(new SkillLearnTemplate[newSkills.size()]);
    }

    public SkillLearnTemplate[] getTemplatesForSkill(int skillId) {
        List<SkillLearnTemplate> searchSkills = new ArrayList<SkillLearnTemplate>();

        List<SkillLearnTemplate> byId = templatesById.get(skillId);
        if (byId != null) {
            searchSkills.addAll(byId);
        }

        return searchSkills.toArray(new SkillLearnTemplate[searchSkills.size()]);
    }

    public boolean isLearnedSkill(int skillId) {
        return templatesById.get(skillId) != null;
    }

    public int size() {
        int size = 0;
        for (Integer key : templates.keys()) {
            size += templates.get(key).size();
        }
        return size;
    }

    private static int makeHash(int classId, int race, int level) {
        int result = classId << 8;
        result = (result | race) << 8;
        return result | level;
    }
}
