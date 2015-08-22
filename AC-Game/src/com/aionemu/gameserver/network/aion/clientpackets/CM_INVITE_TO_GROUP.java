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

import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.model.team2.league.LeagueService;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.ChatUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

/**
 * @author Lyahim, ATracer, Modified by Simple
 * @author GiGatR00n v4.7.5.x
 */
public class CM_INVITE_TO_GROUP extends AionClientPacket {

    private String name;
    private int inviteType;

    public CM_INVITE_TO_GROUP(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        inviteType = readC();
        name = readS();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {

        name = ChatUtil.getRealAdminName(name);

        final Player inviter = getConnection().getActivePlayer();
        final String playerName = Util.convertName(name);

        if (inviter.getLifeStats().isAlreadyDead()) {
            // You cannot issue an invitation while you are dead.
            PacketSendUtility.sendPacket(inviter, new SM_SYSTEM_MESSAGE(1300163));
            return;
        }

        final Player invited = World.getInstance().findPlayer(playerName);
        if (invited != null) {
            if (invited.getPlayerSettings().isInDeniedStatus(DeniedStatus.GROUP)) {
                sendPacket(SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_INVITE_PARTY(invited.getName()));
                return;
            }
            switch (inviteType) {
                case 0:
                    PlayerGroupService.inviteToGroup(inviter, invited);
                    break;
                case 12: // 2.5
                    PlayerAllianceService.inviteToAlliance(inviter, invited);
                    break;
                case 28:
                    LeagueService.inviteToLeague(inviter, invited);
                    break;
                default:
                    PacketSendUtility.sendMessage(inviter, "You used an unknown invite type: " + inviteType);
                    break;
            }
        } else {
            inviter.getClientConnection().sendPacket(SM_SYSTEM_MESSAGE.STR_NO_SUCH_USER(name));
        }
    }
}
