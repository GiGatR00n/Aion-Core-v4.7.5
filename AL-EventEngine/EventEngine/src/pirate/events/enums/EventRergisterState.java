/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package pirate.events.enums;

/**
 *
 * @author flashman
 */
public enum EventRergisterState {

    ERROR,
    CRITICAL_ERROR,
    GROUP_NOT_REGISTRED,
    REGISTRED,
    UNREGISTRED,
    ALREADY_REGISTRED,
    ONE_PLAYER_IN_GROUP_ALREADY_REGISTRED,
    EVENT_NOT_START,
    PLAYER_HAS_VISIT_EVENT,
    HOLDER_ADD_PLAYER,
    HOLDER_ADD_GROUP,
    HOLDER_ALREADY_IN_OR_IS_FULL,
    PLAYERS_IN_GROUP_MISSMATCH,
    HAVE_MAX_PLAYERS,
    HAVE_MAX_GROUP,
    HAVE_MAX_ASMOS,
    HAVE_MAX_ELYS,
    PLAYER_IS_NOT_GROUP_LEADER,
    PLAYER_IN_GROUP_ALREADY_VISIT_EVENT;
    
    private EventRergisterState() {}
}

