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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * This packet is notify client what map should be loaded.
 *
 * @author -Nemesiss-
 */
public class SM_PLAYER_SPAWN extends AionServerPacket {

    /**
     * Player that is entering game.
     */
    private final Player player;

    /**
     * Constructor.
     *
     * @param player
     */
    public SM_PLAYER_SPAWN(Player player) {
        super();
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(player.getWorldId());
        writeD(player.getWorldId()); // world + chnl
        writeD(0x00); // unk
        writeC(WorldMapType.getWorld(player.getWorldId()).isPersonal() ? 1 : 0);
        writeF(player.getX()); // x
        writeF(player.getY()); // y
        writeF(player.getZ()); // z
        writeC(player.getHeading()); // heading
        writeD(1);
        writeD(0); // TODO => can take some value but dunno what this info is atm
        if (player.getUseAutoGroup() == 1) {
            switch (player.getWorldId()) {
                case 300030000:
                    writeD(3); // Victorys Pledge, boost attack 20, magic boost by 105, max hp by 520, healing boost by 30
                    break;
                case 320100000:
                    writeD(4); // Victorys Pledge, boost attack 20, magic boost by 115, max hp by 600, healing boost by 30
                    break;
                case 300220000:
                case 300260000:
                case 300270000:
                case 300380000:
                case 300520000:
                case 300600000:
                    writeD(5); // Victorys Pledge, boost attack 25, magic boost by 125, max hp by 680, healing boost by 50
                    break;
                case 300040000:
                case 300100000:
                case 300150000:
                case 300160000:
                case 300300000:
                case 301230000:
                case 301240000:
                case 301250000:
                case 301260000:
                case 301270000:
                case 301280000:
                case 301290000:
                case 301300000:
                    writeD(6); // Victorys Pledge, boost attack 25, magic boost by 125, max hp by 680, healing boost by 50
                    break;
                case 301320000:
                case 301330000:
                    writeD(14); // Victorys Pledge 4.5
                    break;
            }
        } else {
            writeD(0);
        }
        if (World.getInstance().getWorldMap(player.getWorldId()).getTemplate().getBeginnerTwinCount() > 0) {
            writeC(1); // Fast Track Server Enabled
        } else {
            writeC(0); // Fast Track Server Disabled
        }
        writeD(0); // 4.0 protocol changed
        writeC(0); // 4.7 new part
    }
}
