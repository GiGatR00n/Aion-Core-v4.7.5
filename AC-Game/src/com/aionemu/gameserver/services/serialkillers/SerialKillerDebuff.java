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
package com.aionemu.gameserver.services.serialkillers;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.calc.StatOwner;
import com.aionemu.gameserver.model.stats.calc.functions.IStatFunction;
import com.aionemu.gameserver.model.stats.calc.functions.StatAddFunction;
import com.aionemu.gameserver.model.stats.calc.functions.StatRateFunction;
import com.aionemu.gameserver.model.templates.serial_killer.RankPenaltyAttr;
import com.aionemu.gameserver.model.templates.serial_killer.RankRestriction;
import com.aionemu.gameserver.skillengine.change.Func;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dtem
 */
public class SerialKillerDebuff implements StatOwner {

    private List<IStatFunction> functions = new ArrayList<IStatFunction>();
    private RankRestriction rankRestriction;

    public void applyEffect(Player player, int rank) {
        if (rank == 0) {
            return;
        }

        rankRestriction = DataManager.SERIAL_KILLER_DATA.getRankRestriction(rank, player.getRace());

        if (hasDebuff()) {
            endEffect(player);
        }

        for (RankPenaltyAttr rankPenaltyAttr : rankRestriction.getPenaltyAttr()) {
            if (rankPenaltyAttr.getFunc().equals(Func.PERCENT)) {
                functions.add(new StatRateFunction(rankPenaltyAttr.getStat(), rankPenaltyAttr.getValue(), true));
            } else {
                functions.add(new StatAddFunction(rankPenaltyAttr.getStat(), rankPenaltyAttr.getValue(), true));
            }
        }
        player.getGameStats().addEffect(this, functions);
    }

    public boolean hasDebuff() {
        return !functions.isEmpty();
    }

    public void endEffect(Player player) {
        functions.clear();
        player.getGameStats().endEffect(this);
    }
}
