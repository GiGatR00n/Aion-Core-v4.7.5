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
package com.aionemu.gameserver.ai2.handler;

import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.TownService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
public class TalkEventHandler {

    /**
     * @param npcAI
     * @param creature
     */
    public static void onTalk(NpcAI2 npcAI, Creature creature) {
        onSimpleTalk(npcAI, creature);

        if (creature instanceof Player) {
            Player player = (Player) creature;
            if (QuestEngine.getInstance().onDialog(new QuestEnv(npcAI.getOwner(), player, 0, -1))) {
                return;
            }
            // only player villagers can use villager npcs in oriel/pernon
            switch (npcAI.getOwner().getObjectTemplate().getTitleId()) {
                case 462877:
                    int playerTownId = TownService.getInstance().getTownResidence(player);
                    int currentTownId = TownService.getInstance().getTownIdByPosition(player);
                    if (playerTownId != currentTownId) {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(npcAI.getOwner().getObjectId(), 44));
                        return;
                    } else {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(npcAI.getOwner().getObjectId(), 10));
                        return;
                    }
            default:
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(npcAI.getOwner().getObjectId(), 10));
                break;
            }
        }

    }

    /**
     * @param npcAI
     * @param creature
     */
    public static void onSimpleTalk(NpcAI2 npcAI, Creature creature) {
        if (npcAI.getOwner().getObjectTemplate().isDialogNpc()) {
            npcAI.setSubStateIfNot(AISubState.TALK);
            npcAI.getOwner().setTarget(creature);
        }
    }

    /**
     * @param npcAI
     * @param creature
     */
    public static void onFinishTalk(NpcAI2 npcAI, Creature creature) {
        Npc owner = npcAI.getOwner();
        if (owner.isTargeting(creature.getObjectId())) {
            if (npcAI.getState() != AIState.FOLLOWING) {
                owner.setTarget(null);
            }
            npcAI.think();
        }
    }

    /**
     * No SM_LOOKATOBJECT broadcast
     *
     * @param npcAI
     * @param creature
     */
    public static void onSimpleFinishTalk(NpcAI2 npcAI, Creature creature) {
        Npc owner = npcAI.getOwner();
        if (owner.isTargeting(creature.getObjectId()) && npcAI.setSubStateIfNot(AISubState.NONE)) {
            owner.setTarget(null);
        }
    }
}
