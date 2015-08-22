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

/**
 * @author ATracer
 * @author GiGatR00n v4.7.5.x
 */
public enum TaskId {

    DECAY,
    RESPAWN,
    PRISON,
    PROTECTION_ACTIVE,
    DROWN,
    DESPAWN,
    /**
     * Quest task with timer
     */
    QUEST_TIMER,
    /**
     * Follow task checker
     */
    QUEST_FOLLOW,
    PLAYER_UPDATE,
    INVENTORY_UPDATE,
    GAG,
    ITEM_USE,
    ACTION_ITEM_NPC,
    HOUSE_OBJECT_USE,
    EXPRESS_MAIL_USE,
    SKILL_USE,
    GATHERABLE,
    PET_UPDATE,
    SUMMON_FOLLOW,
    MATERIAL_ACTION,
    BATTLEGROUND_CARRY_FLAG,
    HOTSPOT_TELEPORT
}
