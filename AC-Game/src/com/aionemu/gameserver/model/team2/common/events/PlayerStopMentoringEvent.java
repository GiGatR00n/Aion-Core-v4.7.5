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
package com.aionemu.gameserver.model.team2.common.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamMember;
import com.aionemu.gameserver.model.team2.TemporaryPlayerTeam;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public abstract class PlayerStopMentoringEvent<T extends TemporaryPlayerTeam<? extends TeamMember<Player>>> extends AlwaysTrueTeamEvent implements Predicate<Player> {

    protected final T team;
    protected final Player player;

    public PlayerStopMentoringEvent(T team, Player player) {
        this.team = team;
        this.player = player;
    }

    @Override
    public void handleEvent() {
        player.setMentor(false);
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_END);
        team.applyOnMembers(this);
        PacketSendUtility.broadcastPacketAndReceive(player, new SM_ABYSS_RANK_UPDATE(2, player));
    }

    @Override
    public boolean apply(Player member) {
        if (!player.equals(member)) {
            PacketSendUtility.sendPacket(member, SM_SYSTEM_MESSAGE.STR_MSG_MENTOR_END_PARTYMSG(player.getName()));
        }
        sendGroupPacketOnMentorEnd(member);
        return true;
    }

    /**
     * @param member
     */
    protected abstract void sendGroupPacketOnMentorEnd(Player member);
}
