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
package com.aionemu.gameserver.model.gm;

/**
 * @author Ever' - Magenik
 */
public enum GmPanelCommands {

    /**
     * @STANDARD FUNCTION TAB
     */
    REMOVE_SKILL_DELAY_ALL,
    ITEMCOOLTIME,
    CLEARUSERCOOLT,
    SET_MAKEUP_BONUS,
    SET_VITALPOINT,
    SET_DISABLE_ITEMUSE_GAUGE,
    PARTYRECALL,
    ATTRBONUS,
    TELEPORTTO,
    RESURRECT,
    INVISIBLE,
    VISIBLE,
    /**
     * @CHARACTER SETTING TAB
     */
    LEVELDOWN,
    LEVELUP,
    CHANGECLASS,
    CLASSUP,
    DELETECQUEST,
    ADDQUEST,
    ENDQUEST,
    SETINVENTORYGROWTH,
    SKILLPOINT,
    COMBINESKILL,
    ADDSKILL,
    DELETESKILL,
    GIVETITLE,
    /**
     * @OVERALL FUNCTION TAB
     */
    ENCHANT100,
    FREEFLY,
    /**
     * @NPC QUEST ITEM TAB
     */
    TELEPORT_TO_NAMED,
    WISH,
    WISHID,
    DELETE_ITEMS,
    /**
     * @PLAYER INFO
     */
    BOOKMARK_ADD,
    SEARCH;

    public static GmPanelCommands getValue(String command) {
        for (GmPanelCommands value : values()) {
            if (value.name().equals(command.toUpperCase())) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid GmPanelCommands id: " + command);
    }
}
