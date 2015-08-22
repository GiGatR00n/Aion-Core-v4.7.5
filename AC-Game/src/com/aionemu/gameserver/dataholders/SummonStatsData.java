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

import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ATracer
 */
@XmlRootElement(name = "summon_stats_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class SummonStatsData {

    @XmlElement(name = "summon_stats", required = true)
    private List<SummonStatsType> summonTemplatesList = new ArrayList<SummonStatsType>();
    private final TIntObjectHashMap<SummonStatsTemplate> summonTemplates = new TIntObjectHashMap<SummonStatsTemplate>();

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (SummonStatsType st : summonTemplatesList) {
            int code1 = makeHash(st.getNpcIdDark(), st.getRequiredLevel());
            summonTemplates.put(code1, st.getTemplate());
            int code2 = makeHash(st.getNpcIdLight(), st.getRequiredLevel());
            summonTemplates.put(code2, st.getTemplate());
        }
    }

    /**
     * @param npcId
     * @param level
     * @return
     */
    public SummonStatsTemplate getSummonTemplate(int npcId, int level) {
        SummonStatsTemplate template = summonTemplates.get(makeHash(npcId, level));
        if (template == null) {
            template = summonTemplates.get(makeHash(201022, 10));// TEMP till all templates are done
        }
        return template;
    }

    /**
     * Size of summon templates
     *
     * @return
     */
    public int size() {
        return summonTemplates.size();
    }

    @XmlRootElement(name = "summonStatsTemplateType")
    private static class SummonStatsType {

        @XmlAttribute(name = "npc_id_dark", required = true)
        private int npcIdDark;
        @XmlAttribute(name = "npc_id_light", required = true)
        private int npcIdLight;
        @XmlAttribute(name = "level", required = true)
        private int requiredLevel;
        @XmlElement(name = "stats_template")
        private SummonStatsTemplate template;

        public int getNpcIdDark() {
            return npcIdDark;
        }

        public int getNpcIdLight() {
            return npcIdLight;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }

        public SummonStatsTemplate getTemplate() {
            return template;
        }
    }

    /**
     * Note:<br>
     * max level is 255
     *
     * @param npcId
     * @param level
     * @return
     */
    private static int makeHash(int npcId, int level) {
        return npcId << 8 | level;
    }
}
