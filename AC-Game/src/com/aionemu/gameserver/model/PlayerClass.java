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
package com.aionemu.gameserver.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 * This enum represent class that a player may belong to.
 *
 * @author Luno
 */
@XmlEnum
public enum PlayerClass {

    WARRIOR(0, true),
    GLADIATOR(1), // fighter
    TEMPLAR(2), // knight
    SCOUT(3, true),
    ASSASSIN(4),
    RANGER(5),
    MAGE(6, true),
    SORCERER(7), // wizard
    SPIRIT_MASTER(8), // elementalist
    PRIEST(9, true),
    CLERIC(10),
    CHANTER(11),
    ENGINEER(12, true),
    RIDER(13),
    GUNNER(14),
    ARTIST(15, true),
    BARD(16),
    ALL(17);
    /**
     * This id is used on client side
     */
    private byte classId;
    /**
     * This is the mask for this class id, used with bitwise AND in arguments
     * that contain more than one possible class
     */
    private int idMask;
    /**
     * Tells whether player can create new character with this class
     */
    private boolean startingClass;

    private PlayerClass(int classId) {
        this(classId, false);
    }

    private PlayerClass(int classId, boolean startingClass) {
        this.classId = (byte) classId;
        this.startingClass = startingClass;
        this.idMask = (int) Math.pow(2, classId);
    }

    /**
     * Returns client-side id for this PlayerClass
     *
     * @return classID
     */
    public byte getClassId() {
        return classId;
    }

    /**
     * Returns <tt>PlayerClass</tt> object correlating with given classId.
     *
     * @param classId - id of player class
     * @return PlayerClass objects that matches the given classId. If there
     * isn't any objects that matches given id, then
     * <b>IllegalArgumentException</b> is being thrown.
     */
    public static PlayerClass getPlayerClassById(byte classId) {
        for (PlayerClass pc : values()) {
            if (pc.getClassId() == classId) {
                return pc;
            }
        }

        throw new IllegalArgumentException("There is no player class with id " + classId);
    }

    /**
     * @return true if this is one of starting classes ( player can create char
     * with this class )
     */
    public boolean isStartingClass() {
        return startingClass;
    }

    /**
     * @param pc
     * @return starting class for second class
     */
    public static PlayerClass getStartingClassFor(PlayerClass pc) {
        // TODO: remove that shit, we already have everything in the enum itself!
        switch (pc) {
            case ASSASSIN:
            case RANGER:
                return SCOUT;
            case GLADIATOR:
            case TEMPLAR:
                return WARRIOR;
            case CHANTER:
            case CLERIC:
                return PRIEST;
            case SORCERER:
            case SPIRIT_MASTER:
                return MAGE;
            case GUNNER:
            case RIDER:
                return ENGINEER;
            case BARD:
                return ARTIST;
            case SCOUT:
            case WARRIOR:
            case PRIEST:
            case MAGE:
            case ENGINEER:
            case ARTIST:
                return pc;
            default:
                throw new IllegalArgumentException("Given player class is starting class: " + pc);
        }
    }

    public static PlayerClass getPlayerClassByString(String fieldName) {
        for (PlayerClass pc : values()) {
            if (pc.toString().equals(fieldName)) {
                return pc;
            }
        }
        return null;
    }

    public int getMask() {
        return idMask;
    }
}
