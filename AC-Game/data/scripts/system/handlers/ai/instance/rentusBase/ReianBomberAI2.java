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

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("reian_bomber")
public class ReianBomberAI2 extends GeneralNpcAI2 {

    private AtomicBoolean hasArrivedBoss = new AtomicBoolean(false);
    private int position = 1;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        getSpawnTemplate().setWalkerId("30028000024");
        WalkManager.startWalking(this);
        getOwner().setState(1);
        PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getObjectId()));
    }

    @Override
    protected void handleMoveArrived() {
        int point = getOwner().getMoveController().getCurrentPoint();
        super.handleMoveArrived();
        if (hasArrivedBoss.get()) {
            startHelpEvent();
        } else if (point == 7 && hasArrivedBoss.compareAndSet(false, true)) {
            getSpawnTemplate().setWalkerId(null);
            WalkManager.stopWalking(this);
            startHelpEvent();
        }
    }

    private void startHelpEvent() {
        getMoveController().abortMove();
        setStateIfNot(AIState.IDLE);
        setSubStateIfNot(AISubState.NONE);
        SkillEngine.getInstance().getSkill(getOwner(), 19374, 60, getOwner()).useNoAnimationSkill();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    setSubStateIfNot(AISubState.WALK_RANDOM);
                    setStateIfNot(AIState.WALKING);
                    switch (position) {
                        case 1:
                            help(359.763f, 585.597f, 145.525f);
                            getMoveController().moveToPoint(346.47787f, 604.0337f, 145.8766f);
                            position++;
                            break;
                        case 2:
                            help(346.086f, 597.062f, 146.119f);
                            getMoveController().moveToPoint(370.93597f, 607.6427f, 145.41916f);
                            position++;
                            break;
                        case 3:
                            help(362.143f, 604.723f, 146.125f);
                            getMoveController().moveToPoint(361.7722f, 584.4937f, 145.63573f);
                            position = 1;
                            break;
                    }
                }
            }
        }, 8000);

    }

    private void deleteNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }

    private void help(float x, float y, float z) {
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        if (instance != null) {
            for (Npc npc : instance.getNpcs(282530)) {
                WorldPosition p = npc.getPosition();
                if (p.getX() == x && p.getY() == y) {
                    deleteNpc(npc);
                }
            }
            for (Npc npc : instance.getNpcs(282387)) {
                WorldPosition p = npc.getPosition();
                if (p.getX() == x && p.getY() == y) {
                    return;
                }
            }
            Npc npc = (Npc) spawn(282387, x, y, z, (byte) 0);
            SkillEngine.getInstance().getSkill(npc, 19731, 1, npc).useNoAnimationSkill();
        }
    }
}
