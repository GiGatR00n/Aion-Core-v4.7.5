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
package com.aionemu.gameserver.ai2.follow;

import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.summons.SummonMode;
import com.aionemu.gameserver.model.summons.UnsummonType;
import com.aionemu.gameserver.services.summons.SummonsService;
import com.aionemu.gameserver.utils.MathUtil;

import java.util.concurrent.Future;

/**
 * @author xTz
 */
public class FollowSummonTaskAI implements Runnable {

    private Creature target;
    private Summon summon;
    private Player master;
    private float targetX;
    private float targetY;
    private float targetZ;
    private Future<?> task;

    public FollowSummonTaskAI(Creature target, Summon summon) {
        this.target = target;
        this.summon = summon;
        this.master = summon.getMaster();
        task = summon.getMaster().getController().getTask(TaskId.SUMMON_FOLLOW);
        setLeadingCoordinates();
    }

    private void setLeadingCoordinates() {
        targetX = target.getX();
        targetY = target.getY();
        targetZ = target.getZ();
    }

    @Override
    public void run() {
        if (target == null || summon == null || master == null) {
            if (task != null) {
                task.cancel(true);
            }
            return;
        }
        if (!isInMasterRange()) {
            SummonsService.doMode(SummonMode.RELEASE, summon, UnsummonType.DISTANCE);
            return;
        }
        if (!isInTargetRange()) {
            if (targetX != target.getX() || targetY != target.getY() || targetZ != target.getZ()) {
                setLeadingCoordinates();
                onOutOfTargetRange();
            }
        } else if (!master.equals(target)) {
            onDestination();
        }
    }

    private boolean isInTargetRange() {
        return MathUtil.isIn3dRange(target, summon, 2);
    }

    private boolean isInMasterRange() {
        return MathUtil.isIn3dRange(master, summon, 50);
    }

    protected void onDestination() {
        summon.getAi2().onCreatureEvent(AIEventType.ATTACK, target);
    }

    private void onOutOfTargetRange() {
        summon.getAi2().onGeneralEvent(AIEventType.MOVE_VALIDATE);
    }
}
