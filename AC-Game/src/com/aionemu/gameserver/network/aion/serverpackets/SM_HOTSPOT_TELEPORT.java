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


/**
 * @author Ever, GiGatR00n, Kill3r
 */
public class SM_HOTSPOT_TELEPORT extends AionServerPacket {

    int playerObjectId;
    int teleportGoal;
    int id;
    int unk;
    int cooldown;
    
    public SM_HOTSPOT_TELEPORT(Player player, int telegoal, int id) {
        this.playerObjectId = player.getObjectId();
        this.teleportGoal = telegoal;
        this.id = id;
    }

    public SM_HOTSPOT_TELEPORT(Player player, int telegoal, int id, int cooldown) {
        this.playerObjectId = player.getObjectId();
        this.teleportGoal = telegoal;
        this.id = id;
        this.cooldown = cooldown;
    }

    public SM_HOTSPOT_TELEPORT(Player player, int id) {
        this.playerObjectId = player.getObjectId();
        this.id = id;
    }    
    
    public SM_HOTSPOT_TELEPORT(int unk, int id) {
        this.unk = unk;
        this.id = id;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(id);
        switch (id) {
            case 0:
                writeD(unk);
                break;
            case 1://Start Teleportation
                writeD(playerObjectId);
                writeD(teleportGoal);
                break;
            case 2://Cancel Current Teleportation
                writeD(playerObjectId);
                break;
            case 3://Send after 10 seconds for Teleport Confirmation
                writeD(playerObjectId);
                writeD(teleportGoal);
                writeD(cooldown);
        }
    }
}
