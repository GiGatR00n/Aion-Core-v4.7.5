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

import com.aionemu.gameserver.model.team.legion.LegionMember;
import com.aionemu.gameserver.model.team.legion.LegionMemberEx;

import java.util.ArrayList;

/**
 * Class that is responsible for storing/loading legion data
 *
 * @author Simple
 */
public abstract class LegionMemberDAO implements IDFactoryAwareDAO {

    /**
     * Returns true if name is used, false in other case
     *
     * @param name name to check
     * @return true if name is used, false in other case
     */
    public abstract boolean isIdUsed(int playerObjId);

    /**
     * Creates legion member in DB
     *
     * @param legionMember
     */
    public abstract boolean saveNewLegionMember(LegionMember legionMember);

    /**
     * Stores legion member to DB
     *
     * @param player
     */
    public abstract void storeLegionMember(int playerObjId, LegionMember legionMember);

    /**
     * Loads a legion member
     *
     * @param playerObjId
     * @param legionService
     * @return LegionMember
     */
    public abstract LegionMember loadLegionMember(int playerObjId);

    /**
     * Loads an off line legion member by id
     *
     * @param playerObjId
     * @param legionService
     * @return LegionMemberEx
     */
    public abstract LegionMemberEx loadLegionMemberEx(int playerObjId);

    /**
     * Loads an off line legion member by name
     *
     * @param playerName
     * @param legionService
     * @return LegionMemberEx
     */
    public abstract LegionMemberEx loadLegionMemberEx(String playerName);

    /**
     * Loads all legion members of a legion
     *
     * @param legionId
     * @return ArrayList<Integer>
     */
    public abstract ArrayList<Integer> loadLegionMembers(int legionId);

    /**
     * Removes legion member and all related data (Done by CASCADE DELETION)
     *
     * @param playerId legion member to delete
     */
    public abstract void deleteLegionMember(int playerObjId);

    /**
     * Identifier name for all LegionDAO classes
     *
     * @return LegionDAO.class.getName()
     */
    @Override
    public final String getClassName() {
        return LegionMemberDAO.class.getName();
    }
}
