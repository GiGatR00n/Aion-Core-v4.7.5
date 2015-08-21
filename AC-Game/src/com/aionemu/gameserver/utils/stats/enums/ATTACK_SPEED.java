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
package com.aionemu.gameserver.utils.stats.enums;

/**
 * @author ATracer
 */
public enum ATTACK_SPEED {

    WARRIOR(1500),
    GLADIATOR(1500),
    TEMPLAR(1500),
    SCOUT(1500),
    ASSASSIN(1500),
    RANGER(1500),
    MAGE(1500),
    SORCERER(1500),
    SPIRIT_MASTER(1500),
    PRIEST(1500),
    CLERIC(1500),
    CHANTER(1500),
    ENGINEER(1500),
    RIDER(1500),
    GUNNER(1500),
    ARTIST(1500),
    BARD(1500);
    private int value;

    private ATTACK_SPEED(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
