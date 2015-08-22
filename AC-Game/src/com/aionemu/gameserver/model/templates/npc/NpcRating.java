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
package com.aionemu.gameserver.model.templates.npc;

import com.aionemu.gameserver.model.gameobjects.state.CreatureSeeState;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ATracer
 */
@XmlType(name = "rating")
@XmlEnum
public enum NpcRating {

    JUNK(CreatureSeeState.NORMAL),
    NORMAL(CreatureSeeState.NORMAL),
    ELITE(CreatureSeeState.SEARCH1),
    HERO(CreatureSeeState.SEARCH2),
    LEGENDARY(CreatureSeeState.SEARCH2);
    private final CreatureSeeState congenitalSeeState;

    private NpcRating(CreatureSeeState congenitalSeeState) {
        this.congenitalSeeState = congenitalSeeState;
    }

    public CreatureSeeState getCongenitalSeeState() {
        return congenitalSeeState;
    }
}
