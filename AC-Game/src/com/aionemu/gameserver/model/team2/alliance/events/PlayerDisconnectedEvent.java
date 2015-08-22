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
package com.aionemu.gameserver.model.team2.alliance.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamEvent;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class PlayerDisconnectedEvent implements TeamEvent, Predicate<PlayerAllianceMember> {

    private final PlayerAlliance alliance;
    private final Player disconnected;
    private final PlayerAllianceMember disconnectedMember;

    public PlayerDisconnectedEvent(PlayerAlliance alliance, Player player) {
        this.alliance = alliance;
        this.disconnected = player;
        this.disconnectedMember = alliance.getMember(disconnected.getObjectId());
    }

    /**
     * Player should be in alliance before disconnection
     */
    @Override
    public boolean checkCondition() {
        return alliance.hasMember(disconnected.getObjectId());
    }

    @Override
    public void handleEvent() {
        Preconditions.checkNotNull(disconnectedMember, "Disconnected member should not be null");
        alliance.apply(this);
        if (alliance.onlineMembers() <= 1) {
            PlayerAllianceService.disband(alliance);
        } else {
            if (disconnected.equals(alliance.getLeader().getObject())) {
                alliance.onEvent(new ChangeAllianceLeaderEvent(alliance));
            }
        }
    }

    @Override
    public boolean apply(PlayerAllianceMember member) {
        Player player = member.getObject();
        if (!disconnected.getObjectId().equals(player.getObjectId())) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_FORCE_HE_BECOME_OFFLINE(disconnected.getName()));
            PacketSendUtility.sendPacket(player, new SM_ALLIANCE_MEMBER_INFO(disconnectedMember,
                    PlayerAllianceEvent.DISCONNECTED));
        }
        return true;
    }
}
