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
package ai.instance.rentusBase;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
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
@AIName("captain_xasta")
public class CaptainXastaAI2 extends AggressiveNpcAI2 {

    private boolean canThink = true;
    private Future<?> phaseTask;
    private AtomicBoolean isHome = new AtomicBoolean(true);

    @Override
    public boolean canThink() {
        return canThink;
    }

    @Override
    public void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            if (getNpcId() == 217309) {
                sendMsg(1500388);
                startPhaseTask(this);
            } else {
                startPhase2Task();
            }
        }
    }

    private void cancelPhaseTask() {
        if (phaseTask != null && !phaseTask.isDone()) {
            phaseTask.cancel(true);
        }
    }

    private void startPhaseTask(final NpcAI2 ai) {
        phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    canThink = false;
                    EmoteManager.emoteStopAttacking(getOwner());
                    getSpawnTemplate().setWalkerId("B186C8F43FF13FDD50FA9483B7D8C2BEABAE7F5C");
                    WalkManager.startWalking(ai);
                    startRun(getOwner());
                    spawnHelpers(ai);
                    startSanctuaryEvent();
                }
            }
        }, 28000, 28000);
    }

    @Override
    protected void handleMoveArrived() {
        super.handleMoveArrived();
        if (getSpawnTemplate().getWalkerId() != null) {
            getSpawnTemplate().setWalkerId(null);
            WalkManager.stopWalking(this);
        }
    }

    private void startPhase2Task() {
        phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    SkillEngine.getInstance().getSkill(getOwner(), 19729, 60, getOwner()).useNoAnimationSkill();
                    sendMsg(1500392);
                }
            }
        }, 30000, 30000);
    }

    private void startSanctuaryEvent() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    canThink = true;
                    Creature creature = getAggroList().getMostHated();
                    if (creature == null || creature.getLifeStats().isAlreadyDead() || !getOwner().canSee(creature)) {
                        setStateIfNot(AIState.FIGHT);
                        getMoveController().recallPreviousStep();
                        getMoveController().abortMove();
                        onGeneralEvent(AIEventType.ATTACK_FINISH);
                        onGeneralEvent(AIEventType.BACK_HOME);
                    } else {
                        getMoveController().abortMove();
                        getOwner().setTarget(creature);
                        getOwner().getGameStats().renewLastAttackTime();
                        getOwner().getGameStats().renewLastAttackedTime();
                        getOwner().getGameStats().renewLastChangeTargetTime();
                        getOwner().getGameStats().renewLastSkillTime();
                        setStateIfNot(AIState.FIGHT);
                        handleMoveValidate();
                        SkillEngine.getInstance().getSkill(getOwner(), 19657, 60, getOwner()).useNoAnimationSkill();
                    }
                }
            }
        }, 23000);
    }

    private void startRun(Npc npc) {
        npc.setState(1);
        PacketSendUtility.broadcastPacket(npc, new SM_EMOTION(npc, EmotionType.START_EMOTE2, 0, npc.getObjectId()));
    }

    @SuppressWarnings("unused")
    private void spawnHelpers(final NpcAI2 ai) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    sendMsg(1500389);
                    SkillEngine.getInstance().getSkill(getOwner(), 19968, 60, getOwner()).useNoAnimationSkill();
                    Npc npc1 = (Npc) spawn(282604, 263f, 537f, 203f, (byte) 0);
                    Npc npc2 = (Npc) spawn(282604, 186f, 555f, 203f, (byte) 0);
                    npc1.getSpawn().setWalkerId("30028000014");
                    WalkManager.startWalking((NpcAI2) npc1.getAi2());
                    npc2.getSpawn().setWalkerId("30028000015");
                    WalkManager.startWalking((NpcAI2) npc2.getAi2());
                    startRun(npc1);
                    startRun(npc2);
                }
            }
        }, 3000);
    }

    private void deleteHelpers() {
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        if (instance != null) {
            deleteNpcs(instance.getNpcs(282604));
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
        cancelPhaseTask();
        if (getNpcId() == 217309) {
            sendMsg(1500390);
            spawn(217310, 238.160f, 598.624f, 178.480f, (byte) 0);
            deleteHelpers();
            AI2Actions.deleteOwner(this);
        } else {
            sendMsg(1500391);
            final WorldMapInstance instance = getPosition().getWorldMapInstance();
            if (instance != null) {
                final Npc ariana = instance.getNpc(799668);
                if (ariana != null) {
                    ariana.getEffectController().removeEffect(19921);
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            ariana.getSpawn().setWalkerId("30028000016");
                            WalkManager.startWalking((NpcAI2) ariana.getAi2());
                        }
                    }, 1000);
                    NpcShoutsService.getInstance().sendMsg(ariana, 1500415, ariana.getObjectId(), 0, 4000);
                    NpcShoutsService.getInstance().sendMsg(ariana, 1500416, ariana.getObjectId(), 0, 13000);
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (instance != null) {
                                SkillEngine.getInstance().getSkill(ariana, 19358, 60, ariana).useNoAnimationSkill();
                                instance.getDoors().get(145).setOpen(true);
                                deleteNpcs(instance.getNpcs(701156));
                                ThreadPoolManager.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ariana != null && !NpcActions.isAlreadyDead(ariana)) {
                                            NpcActions.delete(ariana);
                                        }
                                    }
                                }, 13000);
                            }
                        }
                    }, 13000);
                }
            }
        }
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelPhaseTask();
        super.handleDespawned();
    }

    @Override
    protected void handleBackHome() {
        canThink = true;
        cancelPhaseTask();
        deleteHelpers();
        isHome.set(true);
        super.handleBackHome();
    }
}
