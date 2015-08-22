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
package com.aionemu.gameserver.model.team2.group.events;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.team2.TeamType;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
public class PlayerGroupInvite extends RequestResponseHandler {

    private final Player inviter;
    private final Player invited;

    public PlayerGroupInvite(Player inviter, Player invited) {
        super(inviter);
        this.inviter = inviter;
        this.invited = invited;
    }

    @Override
    public void acceptRequest(Creature requester, Player responder) {
        if (PlayerGroupService.canInvite(inviter, invited)) {
            PacketSendUtility.sendPacket(inviter, SM_SYSTEM_MESSAGE.STR_PARTY_INVITED_HIM(invited.getName()));
            PlayerGroup group = inviter.getPlayerGroup2();
            if (group != null) {
                PlayerGroupService.addPlayer(group, invited);
            } else {
                PlayerGroupService.createGroup(inviter, invited, TeamType.GROUP);
            }
        }
    }

    @Override
    public void denyRequest(Creature requester, Player responder) {
        PacketSendUtility.sendPacket(inviter, SM_SYSTEM_MESSAGE.STR_PARTY_HE_REJECT_INVITATION(responder.getName()));
    }
}
