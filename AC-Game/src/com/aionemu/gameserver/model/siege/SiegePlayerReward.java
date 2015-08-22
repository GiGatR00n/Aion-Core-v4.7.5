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
package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;

/**
 * @author Eloann
 */
public class SiegePlayerReward extends InstanceReward<SiegeAbyssRace> {

    private final byte buffId;

    public SiegePlayerReward(Integer mapId) {
        super(mapId, 0);
        buffId = 12;
    }

    public final boolean isKatalam() {
        return mapId == 600050000;
    }

    public final boolean isDanaria() {
        return mapId == 600060000;
    }

    public void regPlayerReward(Integer object) {
        if (!containPlayer(object)) {
            addPlayerReward(new SiegeAbyssRace(object, buffId));
        }
    }

    @Override
    public void addPlayerReward(SiegeAbyssRace reward) {
        super.addPlayerReward(reward);
    }

    @Override
    public SiegeAbyssRace getPlayerReward(Integer object) {
        return (SiegeAbyssRace) super.getPlayerReward(object);
    }

    public boolean canRewarded() {
        return mapId == 600050000 || mapId == 600060000;
    }

    public byte getBuffId() {
        return buffId;
    }

    @Override
    public void clear() {
        super.clear();
    }
}
