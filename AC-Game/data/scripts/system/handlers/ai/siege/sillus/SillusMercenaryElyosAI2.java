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
package ai.siege.sillus;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Eloann
 */
@AIName("sillus_mercenary_elyos")
public class SillusMercenaryElyosAI2 extends NpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        if (player.getInventory().getItemCountByItemId(186000236) > 0) {
            super.handleDialogStart(player);
        } else {
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
            PacketSendUtility.sendMessage(player, "You need item [item: 186000236] to ask reinforcements");
        }
    }

    @Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
        switch (DialogAction.getActionByDialogId(dialogId)) {
            case SETPRO1:
                spawn(272070, 2160.2812f, 1874.4827f, 311.00818f, (byte) 0); // Sniper
                spawn(272070, 2161.8381f, 1879.8955f, 311.00815f, (byte) 0); // Sniper
                spawn(272070, 2161.9878f, 1876.8461f, 311.00815f, (byte) 0); // Sniper
                spawn(272070, 2117.2002f, 1874.6698f, 332.05746f, (byte) 105); // Sniper
                spawn(272070, 2103.5344f, 1859.9352f, 332.03583f, (byte) 105); // Sniper
                spawn(272070, 1999.8871f, 1724.2051f, 318.26813f, (byte) 105); // Sniper
                break;
            case SETPRO2:
                spawn(272080, 2310.5618f, 1891.5841f, 297.4462f, (byte) 0); // Archpriest
                spawn(272080, 2248.4426f, 1856.122f, 279.89194f, (byte) 15); // Archpriest
                spawn(272080, 2199.7979f, 1874.4004f, 290.16302f, (byte) 105); // Archpriest
                spawn(272080, 2009.3325f, 1787.3176f, 316.03876f, (byte) 90); // Archpriest
                spawn(272080, 2024.449f, 1726.4705f, 308.93158f, (byte) 90); // Archpriest
                spawn(272080, 2012.5654f, 1689.2875f, 299.40112f, (byte) 63); // Archpriest
                break;
            case SETPRO3:
                spawn(272036, 1998.9874f, 1790.6902f, 317.125f, (byte) 95); // Cannoneer
                spawn(272036, 2011.273f, 1793.6339f, 317.29504f, (byte) 95); // Cannoneer
                spawn(272036, 2105.16f, 1863.1708f, 317.5952f, (byte) 107); // Cannoneer
                spawn(272036, 2113.8284f, 1872.8129f, 317.79007f, (byte) 107); // Cannoneer
                break;
        }
        return true;
    }
}
