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
package ai.instance.tiamatStrongHold;

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Cheatkiller
 */
@AIName("muragan")
public class MuraganAI2 extends GeneralNpcAI2 {

    private boolean isMove;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        if (getOwner().getNpcId() == 800438) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 390852, getOwner().getObjectId(), 0, 1000);
        }
    }

    @Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(this, creature);
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(this, creature);
    }

    @SuppressWarnings("unused")
    private void checkDistance(NpcAI2 ai, Creature creature) {
        if (creature instanceof Player) {
            if (MathUtil.isIn3dRange(getOwner(), creature, 15) && !isMove) {
                isMove = true;
                openSuramaDoor();
                startWalk((Player) creature);
            }
        }
    }

    private void startWalk(final Player player) {
        int owner = getOwner().getNpcId();
        if (owner == 800436 || owner == 800438) {
            return;
        }
        switch (owner) {
            case 800435:
                NpcShoutsService.getInstance().sendMsg(getOwner(), 390837, getOwner().getObjectId(), 0, 0);
                NpcShoutsService.getInstance().sendMsg(getOwner(), 390838, getOwner().getObjectId(), 0, 4000);
                killGuardCaptain();
                break;
        }
        setStateIfNot(AIState.WALKING);
        getOwner().setState(1);
        getMoveController().moveToPoint(838, 1317, 396);
        PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getOwner().getObjectId()));
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                forQuest(player);
                AI2Actions.deleteOwner(MuraganAI2.this);
            }
        }, 10000);
    }

    private void openSuramaDoor() {
        if (getOwner().getNpcId() == 800436) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 390835, getOwner().getObjectId(), 0, 0);
            getPosition().getWorldMapInstance().getDoors().get(56).setOpen(true);
            AI2Actions.deleteOwner(this);
        }
    }

    private void killGuardCaptain() {
        WorldMapInstance instance = getOwner().getPosition().getWorldMapInstance();
        for (Npc npc : instance.getNpcs()) {
            if (npc.getNpcId() == 219440) {
                spawn(283448, npc.getX(), npc.getY(), npc.getZ(), (byte) npc.getHeading());
                npc.getController().onDelete();
            }
        }
    }

    private void forQuest(Player player) {
        int quest = player.getRace().equals(Race.ELYOS) ? 30708 : 30758;
        final QuestState qs = player.getQuestStateList().getQuestState(quest);
        if (qs != null && qs.getQuestVarById(0) != 5) {
            qs.setQuestVar(qs.getQuestVarById(0) + 1);
            PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(quest, qs.getStatus(), qs.getQuestVars().getQuestVars()));
        }
    }
}
