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

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.DuelService;

/**
 * @author xavier
 */
public class CM_DUEL_REQUEST extends AionClientPacket {

    /**
     * Target object id that client wants to start duel with
     */
    private int objectId;

    /**
     * Constructs new instance of <tt>CM_DUEL_REQUEST</tt> packet
     *
     * @param opcode
     */
    public CM_DUEL_REQUEST(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        objectId = readD();
    }

    @Override
    protected void runImpl() {
        Player activePlayer = getConnection().getActivePlayer();
        AionObject target = activePlayer.getKnownList().getObject(objectId);

        if (!CustomConfig.INSTANCE_DUEL_ENABLE && activePlayer.isInInstance()) {
            return;
        }

        if (target == null) {
            return;
        }

        if (target instanceof Player && !((Player) target).equals(activePlayer)) {
            DuelService duelService = DuelService.getInstance();

            Player targetPlayer = (Player) target;

            if (duelService.isDueling(activePlayer.getObjectId())) {
                sendPacket(SM_SYSTEM_MESSAGE.STR_DUEL_YOU_ARE_IN_DUEL_ALREADY);
                return;
            }
            if (duelService.isDueling(targetPlayer.getObjectId())) {
                sendPacket(SM_SYSTEM_MESSAGE.STR_DUEL_PARTNER_IN_DUEL_ALREADY(target.getName()));
                return;
            }
            if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.DUEL)) {
                sendPacket(SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_DUEL(targetPlayer.getName()));
                return;
            }
            duelService.onDuelRequest(activePlayer, targetPlayer);
            duelService.confirmDuelWith(activePlayer, targetPlayer);
        } else {
            sendPacket(SM_SYSTEM_MESSAGE.STR_DUEL_PARTNER_INVALID(target.getName()));
        }
    }
}
