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

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("cadella")
public class CadellaAI2 extends AggressiveNpcAI2 {

    private boolean canThink = true;
    private Future<?> phaseTask;
    private AtomicBoolean isHome = new AtomicBoolean(true);
    int eventSkillId = 19533;

    @Override
    public boolean canThink() {
        return canThink;
    }

    @Override
    public void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            spawn(282345, 958.779f, 1104.99f, 70f, (byte) 106);
            spawn(282346, 988.687f, 1113.22f, 70f, (byte) 82);
            spawn(282347, 1005.22f, 1088.83f, 70f, (byte) 60);
            spawn(282348, 988.57f, 1064.3f, 70f, (byte) 38);
            spawn(282349, 959.04f, 1072.83f, 70f, (byte) 13);
            startPhaseTask();
            sendMsg(1500460);
        }
    }

    private void cancelPhaseTask() {
        if (phaseTask != null && !phaseTask.isDone()) {
            phaseTask.cancel(true);
        }
    }

    private void startPhaseTask() {
        phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    startHealPhase();
                }
            }
        }, 20000, 60000);
    }

    private void startHealPhase() {
        final List<Npc> helpers = getHelpers();
        if (helpers.isEmpty()) {
            cancelPhaseTask();
            return;
        }
        SkillEngine.getInstance().getSkill(getOwner(), 19541, 60, getOwner()).useNoAnimationSkill();
        canThink = false;
        EmoteManager.emoteStopAttacking(getOwner());
        setStateIfNot(AIState.WALKING);
        sendMsg(1500461);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    Npc npc = helpers.get(Rnd.get(helpers.size()));
                    npc.getEffectController().removeEffect(19571);
                    npc.setTarget(getOwner());
                    npc.setState(64);
                    PacketSendUtility.broadcastPacket(npc, new SM_EMOTION(npc, EmotionType.START_EMOTE2, 0, npc.getObjectId()));
                    npc.getMoveController().moveToTargetObject();
                }
            }
        }, 2000);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    canThink = true;
                    eventSkillId++;
                    Creature creature = getAggroList().getMostHated();
                    if (creature == null || creature.getLifeStats().isAlreadyDead() || !getOwner().canSee(creature)) {
                        setStateIfNot(AIState.FIGHT);
                        think();
                    } else {
                        getOwner().setTarget(creature);
                        getOwner().getGameStats().renewLastAttackTime();
                        getOwner().getGameStats().renewLastAttackedTime();
                        getOwner().getGameStats().renewLastChangeTargetTime();
                        getOwner().getGameStats().renewLastSkillTime();
                        //setStateIfNot(AIState.FIGHT);
                        handleCreatureAggro(creature);
                        SkillEngine.getInstance().getSkill(getOwner(), eventSkillId, 60, getOwner()).useNoAnimationSkill();
                    }
                }
            }
        }, 17000);
    }

    private List<Npc> getHelpers() {
        List<Npc> helpers = new ArrayList<Npc>();
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        Npc npc1 = instance.getNpc(282345);
        if (npc1 != null && !NpcActions.isAlreadyDead(npc1)) {
            helpers.add(npc1);
        }
        Npc npc2 = instance.getNpc(282346);
        if (npc2 != null && !NpcActions.isAlreadyDead(npc2)) {
            helpers.add(npc2);
        }
        Npc npc3 = instance.getNpc(282347);
        if (npc3 != null && !NpcActions.isAlreadyDead(npc3)) {
            helpers.add(npc3);
        }
        Npc npc4 = instance.getNpc(282348);
        if (npc4 != null && !NpcActions.isAlreadyDead(npc4)) {
            helpers.add(npc4);
        }
        Npc npc5 = instance.getNpc(282349);
        if (npc5 != null && !NpcActions.isAlreadyDead(npc5)) {
            helpers.add(npc5);
        }
        return helpers;
    }

    private void deleteHelpers() {
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        if (instance != null) {
            deleteNpcs(instance.getNpcs(282345));
            deleteNpcs(instance.getNpcs(282346));
            deleteNpcs(instance.getNpcs(282347));
            deleteNpcs(instance.getNpcs(282348));
            deleteNpcs(instance.getNpcs(282349));
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
        deleteHelpers();
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        if (instance != null) {
            instance.getDoors().get(15).setOpen(true);
            instance.getDoors().get(64).setOpen(true);
            instance.getDoors().get(76).setOpen(true);
        }
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelPhaseTask();
        deleteHelpers();
        super.handleDespawned();
    }

    @Override
    protected void handleBackHome() {
        getEffectController().removeEffect(19533);
        getEffectController().removeEffect(19534);
        getEffectController().removeEffect(19535);
        getEffectController().removeEffect(19536);
        getEffectController().removeEffect(19537);
        getEffectController().removeEffect(19538);
        getEffectController().removeEffect(19539);
        getEffectController().removeEffect(19540);
        getEffectController().removeEffect(19525);
        getEffectController().removeEffect(19526);
        getEffectController().removeEffect(19527);
        getEffectController().removeEffect(19528);
        getEffectController().removeEffect(19529);
        canThink = true;
        eventSkillId = 19533;
        cancelPhaseTask();
        deleteHelpers();
        isHome.set(true);
        super.handleBackHome();
    }
}
