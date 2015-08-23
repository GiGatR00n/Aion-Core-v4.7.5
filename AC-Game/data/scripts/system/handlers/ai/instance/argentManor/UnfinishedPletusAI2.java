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
package ai.instance.argentManor;

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("unfinished_pletus")
public class UnfinishedPletusAI2 extends GeneralNpcAI2 {

    private Future<?> phaseTask;
    private Future<?> skillTask;
    private AtomicBoolean isHome = new AtomicBoolean(true);
    private AtomicBoolean isSpawnedHelpers = new AtomicBoolean(false);

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            getPosition().getWorldMapInstance().getDoors().get(26).setOpen(false);
            startPhaseTask();
            sendMsg(1500466);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 75) {
            if (isSpawnedHelpers.compareAndSet(false, true)) {
                startWalker((Npc) spawn(282146, 880.497f, 1091.01f, 91.2582f, (byte) 0), "3001500001");
                startWalker((Npc) spawn(282146, 880.497f, 1091.01f, 91.2582f, (byte) 0), "3001500002");
            }
        }
    }

    private void startWalker(Npc npc, String walkerId) {
        npc.getSpawn().setWalkerId(walkerId);
        WalkManager.startWalking((NpcAI2) npc.getAi2());
        npc.setState(1);
        PacketSendUtility.broadcastPacket(npc, new SM_EMOTION(npc, EmotionType.START_EMOTE2, 0, npc.getObjectId()));
    }

    private void cancelPhaseTask() {
        if (phaseTask != null && !phaseTask.isDone()) {
            phaseTask.cancel(true);
        }
    }

    private void cancelSkillTask() {
        if (skillTask != null && !skillTask.isDone()) {
            skillTask.cancel(true);
        }
    }

    private void startPhaseTask() {
        phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    sendMsg(1500467);
                    SkillEngine.getInstance().getSkill(getOwner(), 19304, 60, getOwner()).useNoAnimationSkill();
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (!isAlreadyDead()) {
                                sendMsg(1500469);
                                SkillEngine.getInstance().getSkill(getOwner(), 19300, 60, getOwner()).useNoAnimationSkill();
                                starSkillEvent();
                            }
                        }
                    }, 3000);
                }
            }
        }, 34000, 60000);
    }

    private void starSkillEvent() {
        skillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    sendMsg(1500468);
                    SkillEngine.getInstance().getSkill(getOwner(), 19303, 60, getOwner()).useNoAnimationSkill();
                }
            }
        }, 30000);
    }

    private void deleteHelpers() {
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        if (instance != null) {
            deleteNpcs(instance.getNpcs(282148));
            deleteNpcs(instance.getNpcs(282146));
        }
    }

    private void deleteNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.getController().onDelete();
            }
        }
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
    }

    @Override
    protected void handleDied() {
        sendMsg(1500470);
        cancelSkillTask();
        cancelPhaseTask();
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        if (instance != null) {
            instance.getDoors().get(26).setOpen(true);
            instance.getDoors().get(158).setOpen(true);
            instance.getDoors().get(10).setOpen(true);
        }
        spawn(701013, 928.74146f, 1090.8639f, 91.22978f, (byte) 0);
        super.handleDied();
        deleteHelpers();
        AI2Actions.deleteOwner(this);
    }

    @Override
    protected void handleDespawned() {
        super.handleDespawned();
        cancelPhaseTask();
        cancelSkillTask();
        deleteHelpers();
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        cancelPhaseTask();
        cancelSkillTask();
        getPosition().getWorldMapInstance().getDoors().get(26).setOpen(true);
        isSpawnedHelpers.set(false);
        isHome.set(true);
        deleteHelpers();
    }
}
