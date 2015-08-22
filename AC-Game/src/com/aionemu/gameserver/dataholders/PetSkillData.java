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

import com.aionemu.gameserver.model.templates.petskill.PetSkillTemplate;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author ATracer
 */
@XmlRootElement(name = "pet_skill_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class PetSkillData {

    @XmlElement(name = "pet_skill")
    private List<PetSkillTemplate> petSkills;
    /**
     * A map containing all npc skill templates
     */
    private TIntObjectHashMap<TIntIntHashMap> petSkillData = new TIntObjectHashMap<TIntIntHashMap>();
    private TIntObjectHashMap<TIntArrayList> petSkillsMap = new TIntObjectHashMap<TIntArrayList>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (PetSkillTemplate petSkill : petSkills) {
            TIntIntHashMap orderSkillMap = petSkillData.get(petSkill.getOrderSkill());
            if (orderSkillMap == null) {
                orderSkillMap = new TIntIntHashMap();
                petSkillData.put(petSkill.getOrderSkill(), orderSkillMap);
            }
            orderSkillMap.put(petSkill.getPetId(), petSkill.getSkillId());

            TIntArrayList skillList = petSkillsMap.get(petSkill.getPetId());
            if (skillList == null) {
                skillList = new TIntArrayList();
                petSkillsMap.put(petSkill.getPetId(), skillList);
            }
            skillList.add(petSkill.getSkillId());
        }
    }

    public int size() {
        return petSkillData.size();
    }

    public int getPetOrderSkill(int orderSkill, int petNpcId) {
        return petSkillData.get(orderSkill).get(petNpcId);
    }

    public boolean petHasSkill(int petNpcId, int skillId) {
        return petSkillsMap.get(petNpcId).contains(skillId);
    }
}
