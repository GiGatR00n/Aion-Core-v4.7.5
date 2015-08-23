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
package ai.instance.beshmundirTemple;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Gigi
 */
@AIName("door2")
public class Door2AI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        QuestState qsneedasmo = player.getQuestStateList().getQuestState(30311);
        QuestState qsneedelyos = player.getQuestStateList().getQuestState(30211);
        if (player.getRace() == Race.ELYOS) {
            if (qsneedelyos != null && qsneedelyos.getStatus() != QuestStatus.NONE) {// TODO: Only one player in group has to																																		// have this quest
                super.handleDialogStart(player);
            } else {
                PacketSendUtility.sendMessage(player, "You need quest 30211- [Group] The Rod and the Orb, to enter here.");
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
            }
        } else {
            if (qsneedasmo != null && qsneedasmo.getStatus() != QuestStatus.NONE) { // TODO: Only one player in group has to																																			// have this quest
                super.handleDialogStart(player);
            } else {
                PacketSendUtility.sendMessage(player, "You need quest 30311 - [Group] A Quartz Is a Quartz, to enter here.");
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
            }
        }
    }

    @Override
    protected void handleUseItemFinish(Player player) {
        AI2Actions.deleteOwner(this);
    }
}
