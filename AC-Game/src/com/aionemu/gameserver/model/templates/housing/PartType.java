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
package com.aionemu.gameserver.model.templates.housing;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rolandas
 */
@XmlType(name = "PartType")
@XmlEnum
public enum PartType {

    ROOF(1, 1),
    OUTWALL(2, 2),
    FRAME(3, 3),
    DOOR(4, 4),
    GARDEN(5, 5),
    FENCE(6, 6),
    INWALL_ANY(8, 13),
    INFLOOR_ANY(14, 19),
    ADDON(27, 27);
    private int lineNrStart;
    private int lineNrEnd;

    private PartType(int packetLineStart, int packetLineEnd) {
        this.lineNrStart = packetLineStart;
        this.lineNrEnd = packetLineEnd;
    }

    public int getStartLineNr() {
        return lineNrStart;
    }

    public int getEndLineNr() {
        return lineNrEnd;
    }

    public static PartType getForLineNr(int lineNr) {
        for (PartType type : PartType.values()) {
            if (type.getStartLineNr() <= lineNr && type.getEndLineNr() >= lineNr) {
                return type;
            }
        }
        return null;
    }
}
