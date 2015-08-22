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

import com.aionemu.gameserver.model.actions.PlayerMode;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.world.World;

/**
 * Packet about player flying teleport movement.
 *
 * @author -Nemesiss-, Sweetkr, KID
 */
public class CM_MOVE_IN_AIR extends AionClientPacket {

    float x, y, z;
    int distance;
    @SuppressWarnings("unused")
    private byte locationId;
    @SuppressWarnings("unused")
    private int worldId;

    /**
     * Constructs new instance of <tt>CM_MOVE_IN_AIR </tt> packet
     *
     * @param opcode
     */
    public CM_MOVE_IN_AIR(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        worldId = readD();
        x = readF();
        y = readF();
        z = readF();
        locationId = (byte) readC();
        distance = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if (player.isInState(CreatureState.FLIGHT_TELEPORT)) {
            if (player.isUsingFlyTeleport()) {
                player.setFlightDistance(distance);
            } else if (player.isInPlayerMode(PlayerMode.WINDSTREAM)) {
                player.windstreamPath.distance = distance;
            }
            World.getInstance().updatePosition(player, x, y, z, (byte) 0);
            player.getMoveController().updateLastMove();
        }
    }
}
