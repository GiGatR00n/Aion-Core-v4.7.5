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
package com.aionemu.gameserver.controllers.attack;

import com.aionemu.gameserver.configs.main.CustomConfig;
import javolution.util.FastMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sarynth
 */
public class KillList {

    private FastMap<Integer, List<Long>> killList;

    public KillList() {
        killList = new FastMap<Integer, List<Long>>();
    }

    /**
     * @param winnerId
     * @param victimId
     * @return killsForVictimId
     */
    public int getKillsFor(int victimId) {
        List<Long> killTimes = killList.get(victimId);

        if (killTimes == null) {
            return 0;
        }

        long now = System.currentTimeMillis();
        int killCount = 0;

        for (Iterator<Long> i = killTimes.iterator(); i.hasNext(); ) {
            if (now - i.next().longValue() > CustomConfig.PVP_DAY_DURATION) {
                i.remove();
            } else {
                killCount++;
            }
        }

        return killCount;
    }

    /**
     * @param victimId
     */
    public void addKillFor(int victimId) {
        List<Long> killTimes = killList.get(victimId);
        if (killTimes == null) {
            killTimes = new ArrayList<Long>();
            killList.put(victimId, killTimes);
        }

        killTimes.add(System.currentTimeMillis());
    }
}
