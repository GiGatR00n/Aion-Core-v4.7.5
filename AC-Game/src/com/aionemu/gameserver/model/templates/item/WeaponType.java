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
@XmlType(name = "weapon_type")
@XmlEnum
public enum WeaponType {

    DAGGER_1H(new int[]{30, 9}, 1),
    MACE_1H(new int[]{3, 10}, 1),
    SWORD_1H(new int[]{1, 8}, 1),
    TOOLHOE_1H(new int[]{}, 1),
    GUN_1H(new int[]{83, 76}, 1),
    BOOK_2H(new int[]{64}, 2),
    ORB_2H(new int[]{64}, 2), // 65 right skill, why 64 ? u_u
    POLEARM_2H(new int[]{16}, 2),
    STAFF_2H(new int[]{53}, 2),
    SWORD_2H(new int[]{15}, 2),
    TOOLPICK_2H(new int[]{}, 2),
    TOOLROD_2H(new int[]{}, 2),
    BOW(new int[]{17}, 2),
    CANNON_2H(new int[]{77}, 2),
    HARP_2H(new int[]{92, 78}, 2),
    GUN_2H(new int[]{}, 2),
    KEYBLADE_2H(new int[]{79}, 2),
    KEYHAMMER_2H(new int[]{80}, 2);
    private int[] requiredSkill;
    private int slots;

    private WeaponType(int[] requiredSkills, int slots) {
        this.requiredSkill = requiredSkills;
        this.slots = slots;
    }

    public int[] getRequiredSkills() {
        return requiredSkill;
    }

    public int getRequiredSlots() {
        return slots;
    }

    /**
     * @return int
     */
    public int getMask() {
        return 1 << this.ordinal();
    }
}
