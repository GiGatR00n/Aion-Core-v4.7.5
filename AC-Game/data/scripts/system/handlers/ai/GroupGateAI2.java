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
package ai;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AI2Request;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer, nrg
 */
@AIName("groupgate")
public class GroupGateAI2 extends NpcAI2 {

    private final int CANCEL_DIALOG_METERS = 9;

    @Override
    protected void handleDialogStart(Player player) {

        boolean isMember = false;
        int creatorId = getCreatorId();
        if (player.getObjectId().equals(creatorId)) {
            isMember = true;
        } else if (player.isInGroup2()) {
            isMember = player.getPlayerGroup2().hasMember(creatorId);
        }

        if (isMember && player.getLevel() >= 10) {

            AI2Actions.addRequest(this, player, SM_QUESTION_WINDOW.STR_ASK_GROUP_GATE_DO_YOU_ACCEPT_MOVE, getOwner().getObjectId(), CANCEL_DIALOG_METERS,
                    new AI2Request() {
                        private boolean decisionTaken = false;

                        @Override
                        public void acceptRequest(Creature requester, Player responder) {
                            if (!decisionTaken) {
                                switch (getNpcId()) {
                                    // Group Gates
                                    case 749017:
                                        TeleportService2.teleportTo(responder, 110010000, 1444.9f, 1577.2f, 572.9f, (byte) 0, TeleportAnimation.JUMP_AIMATION);
                                        break;
                                    case 749083:
                                        TeleportService2.teleportTo(responder, 120010000, 1657.5f, 1398.7f, 194.7f, (byte) 0, TeleportAnimation.JUMP_AIMATION);
                                        break;
                                    // Binding Group Gates
                                    case 749131:
                                    case 749132:
                                        TeleportService2.moveToBindLocation(responder, true);
                                        break;
                                }
                                decisionTaken = true;
                            }
                        }

                        @Override
                        public void denyRequest(Creature requester, Player responder) {
                            decisionTaken = true;
                        }
                    });

        } else if (player.getLevel() >= 50) {

            AI2Actions.addRequest(this, player, SM_QUESTION_WINDOW.STR_ASK_GROUP_GATE_DO_YOU_ACCEPT_MOVE, getOwner().getObjectId(), CANCEL_DIALOG_METERS, new AI2Request() {
                private boolean decisionTaken = false;

                @Override
                public void acceptRequest(Creature requester, Player responder) {
                    if (!decisionTaken) {
                        switch (getNpcId()) {
                            // Group Gates Invasion Rift
                            case 296534:
                                if (responder.getWorldId() == 210050000 || responder.getWorldId() == 220070000) {
                                    switch (responder.getRace()) {
                                        case ASMODIANS:
                                            TeleportService2.teleportTo(responder, 110010000, 1768.2721f, 1513.0216f, 585.5641f, (byte) 0, TeleportAnimation.JUMP_AIMATION);
                                        case ELYOS:
                                            TeleportService2.teleportTo(responder, 120010000, 1096.4459f, 1463.9629f, 210.61098f, (byte) 0, TeleportAnimation.JUMP_AIMATION);
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    PacketSendUtility.sendMessage(responder, "You cannot use this portal here.");
                                }
                        }
                        decisionTaken = true;
                    }
                }

                @Override
                public void denyRequest(Creature requester, Player responder) {
                    decisionTaken = true;
                }
            });

        } else {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_SKILL_CAN_NOT_USE_GROUPGATE_NO_RIGHT);
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        //getOwner().setVisualState(CreatureVisualState.HIDE10);
    }

    @Override
    protected void handleDialogFinish(Player player) {
    }
}
