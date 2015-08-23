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
package ai.worlds.katalam;

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

@AIName("sillusmercenaryelyos")
public class SillusMercenaryElyosAI2 extends NpcAI2
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
			spawn(272070, 2160.2812f, 1874.4827f, 311.00818f, (byte) 0); //Elyos Offense Elite Sniper.
			spawn(272070, 2161.8381f, 1879.8955f, 311.00815f, (byte) 0); //Elyos Offense Elite Sniper.
			spawn(272070, 2161.9878f, 1876.8461f, 311.00815f, (byte) 0); //Elyos Offense Elite Sniper.
			spawn(272070, 2117.2002f, 1874.6698f, 332.05746f, (byte) 105); //Elyos Offense Elite Sniper.
			spawn(272070, 2103.5344f, 1859.9352f, 332.03583f, (byte) 105); //Elyos Offense Elite Sniper.
			spawn(272070, 1999.8871f, 1724.2051f, 318.26813f, (byte) 105); //Elyos Offense Elite Sniper.
			announceMercenaries();
		} else if (dialogId == 10001) { //Blood Mark.
			spawn(272080, 2310.5618f, 1891.5841f, 297.4462f, (byte) 0); //Elyos Offense Archpriest.
			spawn(272080, 2248.4426f, 1856.122f, 279.89194f, (byte) 15); //Elyos Offense Archpriest.
			spawn(272080, 2199.7979f, 1874.4004f, 290.16302f, (byte) 105); //Elyos Offense Archpriest.
			spawn(272080, 2009.3325f, 1787.3176f, 316.03876f, (byte) 90); //Elyos Offense Archpriest.
			spawn(272080, 2024.449f, 1726.4705f, 308.93158f, (byte) 90); //Elyos Offense Archpriest.
			spawn(272080, 2012.5654f, 1689.2875f, 299.40112f, (byte) 63); //Elyos Offense Archpriest.
			announceMercenaries();
		} else if (dialogId == 10002) { //Blood Mark.
			spawn(272036, 1998.9874f, 1790.6902f, 317.125f, (byte) 95); //Elyos Defense Elite Cannoneer.
            spawn(272036, 2011.273f, 1793.6339f, 317.29504f, (byte) 95); //Elyos Defense Elite Cannoneer.
            spawn(272036, 2105.16f, 1863.1708f, 317.5952f, (byte) 107); //Elyos Defense Elite Cannoneer.
            spawn(272036, 2113.8284f, 1872.8129f, 317.79007f, (byte) 107); //Elyos Defense Elite Cannoneer.
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