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
import com.aionemu.gameserver.model.AbyssRankingResult;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author ATracer
 */
public abstract class AbyssRankDAO implements DAO {

    @Override
    public final String getClassName() {
        return AbyssRankDAO.class.getName();
    }

    public abstract void loadAbyssRank(Player player);

    public abstract AbyssRank loadAbyssRank(int playerId);

    public abstract boolean storeAbyssRank(Player player);

    public abstract ArrayList<AbyssRankingResult> getAbyssRankingPlayers(Race race, final int maxOfflineDays);

    public abstract ArrayList<AbyssRankingResult> getAbyssRankingLegions(Race race);

    public abstract Map<Integer, Integer> loadPlayersAp(Race race, final int lowerApLimit, final int maxOfflineDays);

    public abstract Map<Integer, Integer> loadPlayersGp(Race race, final int lowerGpLimit, final int maxOfflineDays);

    public abstract void updateAbyssRank(int playerId, AbyssRankEnum rankEnum);

    public abstract void updateRankList(final int maxOfflineDays);
}
