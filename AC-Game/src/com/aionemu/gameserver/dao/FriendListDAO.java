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
package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author Ben
 */
public abstract class FriendListDAO implements DAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClassName() {
        return FriendListDAO.class.getName();
    }

    /**
     * Loads the friend list for the given player
     *
     * @param player Player to get friend list of
     * @return FriendList for player
     */
    public abstract FriendList load(final Player player);

    /**
     * Makes the given players friends
     * <ul>
     * <li>Note: Adds for both players</li>
     * </ul>
     *
     * @param player Player who is adding
     * @param friend Friend to add to the friend list
     * @return Success
     */
    public abstract boolean addFriends(final Player player, final Player friend);

    /**
     * Deletes the friends from eachothers lists
     *
     * @param player     Player whos is deleting
     * @param friendName Name of friend to delete
     * @return Success
     */
    public abstract boolean delFriends(final int playerOid, final int friendOid);
}
