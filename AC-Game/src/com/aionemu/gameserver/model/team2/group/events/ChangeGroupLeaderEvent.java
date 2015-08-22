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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.common.events.ChangeLeaderEvent;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class ChangeGroupLeaderEvent extends ChangeLeaderEvent<PlayerGroup> {

    public ChangeGroupLeaderEvent(PlayerGroup team, Player eventPlayer) {
        super(team, eventPlayer);
    }

    public ChangeGroupLeaderEvent(PlayerGroup team) {
        super(team, null);
    }

    @Override
    public void handleEvent() {
        Player oldLeader = team.getLeaderObject();
        if (eventPlayer == null) {
            team.applyOnMembers(this);
        } else {
            changeLeaderTo(eventPlayer);
        }
        checkLeaderChanged(oldLeader);
    }

    @Override
    protected void changeLeaderTo(final Player player) {
        team.changeLeader(team.getMember(player.getObjectId()));
        team.applyOnMembers(new Predicate<Player>() {
            @Override
            public boolean apply(Player member) {
                PacketSendUtility.sendPacket(member, new SM_GROUP_INFO(team));
                if (!player.equals(member)) {
                    PacketSendUtility.sendPacket(member, SM_SYSTEM_MESSAGE.STR_PARTY_HE_IS_NEW_LEADER(player.getName()));
                } else {
                    PacketSendUtility.sendPacket(member, SM_SYSTEM_MESSAGE.STR_PARTY_YOU_BECOME_NEW_LEADER);
                }
                return true;
            }
        });
    }
}
