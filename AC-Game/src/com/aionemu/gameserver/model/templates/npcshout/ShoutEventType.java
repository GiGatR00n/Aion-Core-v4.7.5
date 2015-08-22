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
package com.aionemu.gameserver.model.templates.npcshout;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rolandas
 */
@XmlType(name = "ShoutEventType")
@XmlEnum
public enum ShoutEventType {

    IDLE,
    ATTACKED, // NPC was being attacked (the same as aggro)
    ATTACK_BEGIN, // NPC starts an attack (the same as aggro)
    ATTACK_END, // NPC leaves FIGHT state
    ATTACK_K, // Numeric hit shouts
    SUMMON_ATTACK, // Summon attack
    CASTING,
    CAST_K, // Numeric cast shouts
    DIED, // Npc died
    HELP, // Calls help without running away
    HELPCALL, // Calls help and runs away
    WALK_WAYPOINT, // Reached the walk point
    START,
    WAKEUP,
    SLEEP,
    RESET_HATE,
    UNK_ACC, // Not clear but seems the same as ATTACKED
    WALK_DIRECTION, // NPC reached the 0 walk point
    STATUP, // Skill statup shouts
    SWITCH_TARGET, // NPC switched the target
    SEE, // NPC sees a player from aggro range
    PLAYER_MAGIC, // Player uses magic attack (merge with attacked?)
    PLAYER_SNARE,
    PLAYER_DEBUFF,
    PLAYER_SKILL,
    PLAYER_SLAVE,
    PLAYER_BLOW,
    PLAYER_PULL,
    PLAYER_PROVOKE,
    PLAYER_CAST,
    GOD_HELP,
    LEAVE, // when player leaves an attack
    BEFORE_DESPAWN, // NPC despawns
    ATTACK_DEADLY,
    WIN,
    ENEMY_DIED;                // NPC's enemy died

    public String value() {
        return name();
    }

    public static ShoutEventType fromValue(String v) {
        return valueOf(v);
    }
}
