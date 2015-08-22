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
package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.teleport.HotspotTeleportTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class CM_HOTSPOT_TELEPORT extends AionClientPacket {

    public int id;
    public int teleportGoal;
    public int kinah;
    public int reqLevel;

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_HOTSPOT_TELEPORT(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#readImpl()
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        id = readC();
        teleportGoal = readD();
        kinah = readD();
        reqLevel = readD();

    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
     */
    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
        if (player == null)
            return;
        if (player.getLifeStats().isAlreadyDead()) {
            return;
        }
        HotspotTeleportTemplate teleport = DataManager.HOTSPOT_TELEPORTER_DATA.getHotspotTemplate(teleportGoal);
        if (teleport != null) {
            TeleportService2.teleport(teleport, teleportGoal, player, kinah, reqLevel);
        } else {
            LoggerFactory.getLogger(CM_HOTSPOT_TELEPORT.class).warn("teleportation id " + teleportGoal + " was not found!");
        }
    }

}
