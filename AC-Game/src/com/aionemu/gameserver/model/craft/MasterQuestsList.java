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
package com.aionemu.gameserver.model.craft;

import com.aionemu.gameserver.model.Race;

/**
 * @author synchro2
 */
public enum MasterQuestsList {

    COOKING_ELYOS(new int[]{19039, 19038}, Race.ELYOS, 40001),
    COOKING_ASMODIANS(new int[]{29039, 29038}, Race.ASMODIANS, 40001),
    WEAPONSMITHING_ELYOS(new int[]{19009, 19008}, Race.ELYOS, 40002),
    WEAPONSMITHING_ASMODIANS(new int[]{29009, 29008}, Race.ASMODIANS, 40002),
    ARMORSMITHING_ELYOS(new int[]{19015, 19014}, Race.ELYOS, 40003),
    ARMORSMITHING_ASMODIANS(new int[]{29015, 29014}, Race.ASMODIANS, 40003),
    TAILORING_ELYOS(new int[]{19021, 19020}, Race.ELYOS, 40004),
    TAILORING_ASMODIANS(new int[]{29021, 29020}, Race.ASMODIANS, 40004),
    ALCHEMY_ELYOS(new int[]{19033, 19032}, Race.ELYOS, 40007),
    ALCHEMY_ASMODIANS(new int[]{29033, 29032}, Race.ASMODIANS, 40007),
    HANDICRAFTING_ELYOS(new int[]{19027, 19026}, Race.ELYOS, 40008),
    HANDICRAFTING_ASMODIANS(new int[]{29027, 29026}, Race.ASMODIANS, 40008),
    MENUSIER_ELYOS(new int[]{19058, 19057}, Race.ELYOS, 40010),
    MENUSIER_ASMODIANS(new int[]{29058, 29057}, Race.ASMODIANS, 40010);
    private int[] skillsIds;
    private Race race;
    private int craftSkillId;

    private MasterQuestsList(int[] skillsIds, Race race, int craftSkillId) {
        this.skillsIds = skillsIds;
        this.race = race;
        this.craftSkillId = craftSkillId;
    }

    private Race getRace() {
        return race;
    }

    private int getCraftSkillId() {
        return craftSkillId;
    }

    public static int[] getSkillsIds(int craftSkillId, Race race) {
        for (MasterQuestsList mql : values()) {
            if (race.equals(mql.getRace()) && craftSkillId == mql.getCraftSkillId()) {
                return mql.skillsIds;
            }
        }
        throw new IllegalArgumentException("Invalid craftSkillId: " + craftSkillId + " or race: " + race);
    }
}
