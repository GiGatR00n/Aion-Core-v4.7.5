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
package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.instance.InstanceScoreType;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;
import javolution.util.FastList;

/**
 * @author xTz
 */
public class InstanceReward<T extends InstancePlayerReward> {

    protected FastList<T> instanceRewards = new FastList<T>();
    private InstanceScoreType instanceScoreType = InstanceScoreType.START_PROGRESS;
    protected Integer mapId;
    protected int instanceId;

    public InstanceReward(Integer mapId, int instanceId) {
        this.mapId = mapId;
        this.instanceId = instanceId;
    }

    public FastList<T> getInstanceRewards() {
        return instanceRewards;
    }

    public boolean containPlayer(Integer object) {
        for (InstancePlayerReward instanceReward : instanceRewards) {
            if (instanceReward.getOwner().equals(object)) {
                return true;
            }
        }
        return false;
    }

    public void removePlayerReward(T reward) {
        if (instanceRewards.contains(reward)) {
            instanceRewards.remove(reward);
        }
    }

    public InstancePlayerReward getPlayerReward(Integer object) {
        for (InstancePlayerReward instanceReward : instanceRewards) {
            if (instanceReward.getOwner().equals(object)) {
                return instanceReward;
            }
        }
        return null;
    }

    public void addPlayerReward(T reward) {
        instanceRewards.add(reward);
    }

    public void setInstanceScoreType(InstanceScoreType instanceScoreType) {
        this.instanceScoreType = instanceScoreType;
    }

    public InstanceScoreType getInstanceScoreType() {
        return instanceScoreType;
    }

    public Integer getMapId() {
        return mapId;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public boolean isRewarded() {
        return instanceScoreType.isEndProgress();
    }

    public boolean isPreparing() {
        return instanceScoreType.isPreparing();
    }

    public boolean isStartProgress() {
        return instanceScoreType.isStartProgress();
    }

    public void clear() {
        instanceRewards.clear();
    }

    protected InstanceReward<?> getInstanceReward() {
        return this;
    }
}
