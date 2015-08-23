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
package ai.instance.kromedesTrial;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Gigi, xTz
 */
@AIName("krobject")
public class KromedesItemNpcsAI2 extends ActionItemNpcAI2 {

    @Override
    public boolean onDialogSelect(final Player player, int dialogId, int questId, int extendedRewardIndex) {
        if (dialogId == 1012) {
            switch (getNpcId()) {
                case 730325:
                    if (player.getInventory().getItemCountByItemId(164000142) < 1) {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1012));
                        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400701)); // TODO: more sys messages, but for
                        // now not needed!
                        ItemService.addItem(player, 164000142, 1);
                    } else {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
                    }
                    break;
                case 730340:
                    if (player.getInventory().getItemCountByItemId(164000140) < 1) {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1012));
                        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400701)); // TODO: more sys messages, but for
                        // now not needed!
                        ItemService.addItem(player, 164000140, 1);
                    } else {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
                    }
                    break;
                case 730341:
                    if (player.getInventory().getItemCountByItemId(164000143) < 1) {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1012));
                        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400701)); // TODO: more sys messages, but for
                        // now not needed!
                        ItemService.addItem(player, 164000143, 1);
                    } else {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    protected void handleUseItemFinish(Player player) {
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
    }
}
