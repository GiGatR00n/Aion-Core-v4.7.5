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
package ai.instance.sauroSupplyBase;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author boscar
 *
 */
@AIName("saurobossportal")
public class SauroBossPortalAI2 extends NpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
    }

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        long keyCount = ((Item) player.getInventory().getFirstItemByItemId(185000179) != null ? ((Item) player.getInventory().getFirstItemByItemId(185000179)).getItemCount() : 0);
        boolean spawnportal = false;
        int portal = 0;
        DialogAction finalAction = DialogAction.getActionByDialogId(dialogId);
        switch (finalAction) {
            case SETPRO1:
                if (keyCount >= 1) {
                    player.getInventory().decreaseByItemId(185000179, 1);
                    spawnportal = true;
                    portal = 730876; // 1 key boss portal
                }
                break;
            case SETPRO2:
                if (keyCount >= 2) {
                    player.getInventory().decreaseByItemId(185000179, 2);
                    spawnportal = true;
                    portal = 730877; // 2 key boss portal
                }
                break;
            default:
                break;
        }
        if (spawnportal) {
            spawn(portal, 129.99165f, 432.41684f, 151.66982f, (byte) 117);
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 0));
            AI2Actions.deleteOwner(this);
        } else {
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1352));
        }
        return true;
    }
}
