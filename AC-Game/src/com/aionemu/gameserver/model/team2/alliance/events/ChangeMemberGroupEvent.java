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

import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceGroup;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.team2.common.events.AlwaysTrueTeamEvent;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class ChangeMemberGroupEvent extends AlwaysTrueTeamEvent implements Predicate<PlayerAllianceMember> {

    private final PlayerAlliance alliance;
    private final int firstMemberId;
    private final int secondMemberId;
    private final int allianceGroupId;
    private PlayerAllianceMember firstMember;
    private PlayerAllianceMember secondMember;

    public ChangeMemberGroupEvent(PlayerAlliance alliance, int firstMemberId, int secondMemberId, int allianceGroupId) {
        this.alliance = alliance;
        this.firstMemberId = firstMemberId;
        this.secondMemberId = secondMemberId;
        this.allianceGroupId = allianceGroupId;
    }

    @Override
    public void handleEvent() {
        firstMember = alliance.getMember(firstMemberId);
        secondMember = alliance.getMember(secondMemberId);
        Preconditions.checkNotNull(firstMember, "First member should not be null");
        Preconditions.checkArgument(secondMemberId == 0 || secondMember != null, "Second member should not be null");
        if (secondMember != null) {
            swapMembersInGroup(firstMember, secondMember);
        } else {
            moveMemberToGroup(firstMember, allianceGroupId);
        }
        alliance.apply(this);
    }

    @Override
    public boolean apply(PlayerAllianceMember member) {
        PacketSendUtility.sendPacket(member.getObject(), new SM_ALLIANCE_MEMBER_INFO(firstMember,
                PlayerAllianceEvent.MEMBER_GROUP_CHANGE));
        if (secondMember != null) {
            PacketSendUtility.sendPacket(member.getObject(), new SM_ALLIANCE_MEMBER_INFO(secondMember,
                    PlayerAllianceEvent.MEMBER_GROUP_CHANGE));
        }
        return true;
    }

    private void swapMembersInGroup(PlayerAllianceMember firstMember, PlayerAllianceMember secondMember) {
        PlayerAllianceGroup firstAllianceGroup = firstMember.getPlayerAllianceGroup();
        PlayerAllianceGroup secondAllianceGroup = secondMember.getPlayerAllianceGroup();
        firstAllianceGroup.removeMember(firstMember);
        secondAllianceGroup.removeMember(secondMember);
        firstAllianceGroup.addMember(secondMember);
        secondAllianceGroup.addMember(firstMember);
    }

    private void moveMemberToGroup(PlayerAllianceMember firstMember, int allianceGroupId) {
        PlayerAllianceGroup firstAllianceGroup = firstMember.getPlayerAllianceGroup();
        firstAllianceGroup.removeMember(firstMember);
        PlayerAllianceGroup newAllianceGroup = alliance.getAllianceGroup(allianceGroupId);
        newAllianceGroup.addMember(firstMember);
    }
}
