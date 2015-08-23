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
package ai.worlds.danaria;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/**
 * 
 * @author Ranastic
 *
 */

@AIName("silonamercenaryelyos")
public class SilonaMercenaryElyosAI2 extends NpcAI2
{
    @Override
    protected void handleDialogStart(Player player) {
        if (player.getInventory().getItemCountByItemId(186000236) > 0) { //Blood Mark.
            super.handleDialogStart(player);
        } else {
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 27));
        }
    }
	
	@Override
    public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
		if (dialogId == 10000) { //Blood Mark.
			spawn(272576, 1599.2748f, 910.92035f, 53.5f, (byte) 23); //Elyos Defense Elite Cannoneer.
            spawn(272576, 1585.2769f, 921.29706f, 53.581066f, (byte) 18); //Elyos Defense Elite Cannoneer.
            spawn(272576, 1535.5685f, 804.60486f, 93.432f, (byte) 30); //Elyos Defense Elite Cannoneer.
            spawn(272576, 1512.3474f, 821.4114f, 93.44656f, (byte) 9); //Elyos Defense Elite Cannoneer.
		    spawn(272820, 1496.2777f, 901.2098f, 68.430786f, (byte) 42); //Elyos Mine.
            spawn(272820, 1551.4484f, 903.64246f, 70.36379f, (byte) 8); //Elyos Mine.
            spawn(272820, 1514.3641f, 863.8583f, 107.63131f, (byte) 92); //Elyos Mine.
            spawn(272820, 1513.1644f, 919.59576f, 80.2405f, (byte) 36); //Elyos Mine.
			announceMercenaries();
		} else if (dialogId == 10001) { //Blood Mark.
			spawn(272586, 1639.0613f, 982.8706f, 55.894577f, (byte) 37); //Elyos Offense Archmagus.
            spawn(272586, 1634.7057f, 981.8072f, 55.5f, (byte) 35); //Elyos Offense Archmagus.
            spawn(272586, 1636.3159f, 984.11444f, 55.50544f, (byte) 35); //Elyos Offense Archmagus.
            spawn(272586, 1521.8398f, 920.99414f, 53.75f, (byte) 35); //Elyos Offense Archmagus.
            spawn(272586, 1516.6506f, 919.58185f, 53.51453f, (byte) 35); //Elyos Offense Archmagus.
            spawn(272586, 1518.8091f, 922.89966f, 53.675568f, (byte) 35); //Elyos Offense Archmagus.
			announceMercenaries();
		} else if (dialogId == 10002) { //Blood Mark.
			spawn(272837, 1623.2288f, 1014.32184f, 54.903088f, (byte) 46); //Elyos Mercenary Gunner.
            spawn(272837, 1616.7952f, 1006.62463f, 55.38566f, (byte) 46); //Elyos Mercenary Gunner.
            spawn(272837, 1618.6987f, 1014.48865f, 55.05079f, (byte) 46); //Elyos Mercenary Gunner.
            spawn(272837, 1615.7753f, 1011.0701f, 55.183117f, (byte) 46); //Elyos Mercenary Gunner.
            spawn(272837, 1511.2913f, 953.0544f, 55.146603f, (byte) 55); //Elyos Mercenary Gunner.
            spawn(272837, 1483.1779f, 976.3054f, 52.570473f, (byte) 64); //Elyos Mercenary Gunner.
            spawn(272837, 1496.0297f, 906.3323f, 52.992584f, (byte) 41); //Elyos Mercenary Gunner.
			announceMercenaries();
		}
        return true;
    }
	
	private void announceMercenaries() {
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401837));
			}
		});
	}
}