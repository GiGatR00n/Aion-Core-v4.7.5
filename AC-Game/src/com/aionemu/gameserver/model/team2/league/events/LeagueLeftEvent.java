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
package com.aionemu.gameserver.model.team2.league.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.common.events.AlwaysTrueTeamEvent;
import com.aionemu.gameserver.model.team2.league.League;
import com.aionemu.gameserver.model.team2.league.LeagueMember;
import com.aionemu.gameserver.model.team2.league.LeagueService;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class LeagueLeftEvent extends AlwaysTrueTeamEvent implements Predicate<LeagueMember> {

    private final League league;
    private final PlayerAlliance alliance;
    private final LeaveReson reason;

    public static enum LeaveReson {

        LEAVE,
        EXPEL,
        DISBAND;
    }

    public LeagueLeftEvent(League league, PlayerAlliance alliance) {
        this(league, alliance, LeaveReson.LEAVE);
    }

    public LeagueLeftEvent(League league, PlayerAlliance alliance, LeaveReson reason) {
        this.league = league;
        this.alliance = alliance;
        this.reason = reason;
    }

    @Override
    public void handleEvent() {
        league.removeMember(alliance.getTeamId());
        league.apply(this);

        switch (reason) {
            case LEAVE:
                alliance.sendPacket(new SM_ALLIANCE_INFO(alliance));
                checkDisband();
                break;
            case EXPEL:
                // TODO getLeaderName in team2
                alliance.sendPacket(new SM_ALLIANCE_INFO(alliance, SM_ALLIANCE_INFO.LEAGUE_EXPELLED, league.getLeaderObject()
                        .getLeader().getName()));
                checkDisband();
                break;
            case DISBAND:
                alliance.sendPacket(new SM_ALLIANCE_INFO(alliance));
                break;
        }
    }

    private final void checkDisband() {
        if (league.onlineMembers() <= 1) {
            LeagueService.disband(league);
        }
    }

    @Override
    public boolean apply(LeagueMember member) {

        PlayerAlliance leagueAlliance = member.getObject();
        leagueAlliance.applyOnMembers(new Predicate<Player>() {
            @Override
            public boolean apply(Player member) {
                switch (reason) {
                    case LEAVE:
                        PacketSendUtility.sendPacket(member, new SM_ALLIANCE_INFO(alliance, SM_ALLIANCE_INFO.LEAGUE_LEFT, alliance
                                .getLeader().getName()));
                        break;
                    case EXPEL:
                        //TODO may be EXPEL message only to leader
                        PacketSendUtility.sendPacket(member, new SM_ALLIANCE_INFO(alliance, SM_ALLIANCE_INFO.LEAGUE_EXPEL, alliance
                                .getLeader().getName()));
                        break;
                }
                return true;
            }
        });

        return true;
    }
}
