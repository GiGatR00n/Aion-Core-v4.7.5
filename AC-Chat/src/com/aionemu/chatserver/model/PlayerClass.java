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
package com.aionemu.chatserver.model;

/**
 * @author ATracer
 */
public enum PlayerClass {

    WARRIOR(0),
    GLADIATOR(1),
    TEMPLAR(2),
    SCOUT(3),
    ASSASSIN(4),
    RANGER(5),
    MAGE(6),
    SORCERER(7),
    SPIRIT_MASTER(8),
    PRIEST(9),
    CLERIC(10),
    CHANTER(11),
    ENGINEER(12),
    RIDER(13),
    GUNNER(14),
    ARTIST(15),
    BARD(16),
    ALL(17);
    private byte classId;

    /**
     * @param classId
     */
    private PlayerClass(int classId) {
        this.classId = (byte) classId;
    }

    /**
     * @return classId
     */
    public byte getClassId() {
        return classId;
    }
}
