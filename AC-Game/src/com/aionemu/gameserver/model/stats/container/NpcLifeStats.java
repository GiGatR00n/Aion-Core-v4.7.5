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
package com.aionemu.gameserver.model.stats.container;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.LOG;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.services.LifeStatsRestoreService;

/**
 * @author ATracer
 */
public class NpcLifeStats extends CreatureLifeStats<Npc> {

    /**
     * @param owner
     */
    public NpcLifeStats(Npc owner) {
        super(owner, owner.getGameStats().getMaxHp().getCurrent(), owner.getGameStats().getMaxMp().getCurrent());
    }

    @Override
    protected void onIncreaseHp(TYPE type, int value, int skillId, LOG log) {
        sendAttackStatusPacketUpdate(type, value, skillId, log);
    }

    @Override
    protected void onIncreaseMp(TYPE type, int value, int skillId, LOG log) {
        // nothing todo
    }

    @Override
    protected void onReduceHp() {
        // nothing todo
    }

    @Override
    protected void onReduceMp() {
        // nothing todo
    }

    @Override
    public void triggerRestoreTask() {
        restoreLock.lock();
        try {
            if (lifeRestoreTask == null && !alreadyDead) {
                this.lifeRestoreTask = LifeStatsRestoreService.getInstance().scheduleHpRestoreTask(this);
            }
        } finally {
            restoreLock.unlock();
        }
    }

    public void startResting() {
        triggerRestoreTask();
    }
}
