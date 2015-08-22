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
package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ATracer
 */
@XmlType(name = "armor_type")
@XmlEnum
public enum ArmorType {

    NO_ARMOR(new int[]{}),
    CHAIN(new int[]{6, 13}),
    CLOTHES(new int[]{4}),
    LEATHER(new int[]{5, 12}),
    PLATE(new int[]{18}),
    ROBE(new int[]{67, 70}),
    SHIELD(new int[]{7, 14}),
    ARROW(new int[]{}),
    WING(new int[]{}),
    PLUME(new int[]{});
    private int[] requiredSkills;

    private ArmorType(int[] requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public int[] getRequiredSkills() {
        return requiredSkills;
    }

    /**
     * @return int
     */
    public int getMask() {
        return 1 << this.ordinal();
    }
}
