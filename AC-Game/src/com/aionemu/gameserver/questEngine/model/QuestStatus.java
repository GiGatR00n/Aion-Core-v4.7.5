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
package com.aionemu.gameserver.questEngine.model;

import javax.xml.bind.annotation.XmlEnum;

/**
 * @author MrPoke
 */
@XmlEnum
public enum QuestStatus {

    NONE(0), // Default status. Aborted quests and the quests, where the quest timer ended. Used for beginning a new
    // quest. Stored together with other quests in the player's quest list, so don't count them! Invisible
    // in the player's quest list
    START(3), // Accepted quests
    REWARD(4), // The quests, that are finished. "Go and get your reward"
    COMPLETE(5), // Completed quests
    LOCKED(6); // Not (yet) available quests
    private int id;

    private QuestStatus(int id) {
        this.id = id;
    }

    public int value() {
        return id;
    }
}
