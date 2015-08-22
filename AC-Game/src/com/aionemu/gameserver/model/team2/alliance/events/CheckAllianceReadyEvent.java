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
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.common.events.AlwaysTrueTeamEvent;
import com.aionemu.gameserver.model.team2.common.events.TeamCommand;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_READY_CHECK;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class CheckAllianceReadyEvent extends AlwaysTrueTeamEvent implements Predicate<Player> {

    private final PlayerAlliance alliance;
    private final Player player;
    private final TeamCommand eventCode;

    public CheckAllianceReadyEvent(PlayerAlliance alliance, Player player, TeamCommand eventCode) {
        this.alliance = alliance;
        this.player = player;
        this.eventCode = eventCode;
    }

    @Override
    public void handleEvent() {
        int readyStatus = alliance.getAllianceReadyStatus();
        switch (eventCode) {
            case ALLIANCE_CHECKREADY_CANCEL:
                readyStatus = 0;
                break;
            case ALLIANCE_CHECKREADY_START:
                readyStatus = alliance.onlineMembers() - 1;
                break;
            case ALLIANCE_CHECKREADY_AUTOCANCEL:
                readyStatus = 0;
                break;
            case ALLIANCE_CHECKREADY_READY:
            case ALLIANCE_CHECKREADY_NOTREADY:
                readyStatus -= 1;
                break;
        }
        alliance.setAllianceReadyStatus(readyStatus);
        alliance.applyOnMembers(this);
    }

    @Override
    public boolean apply(Player member) {
        switch (eventCode) {
            case ALLIANCE_CHECKREADY_CANCEL:
                PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(player.getObjectId(), 0));
                break;
            case ALLIANCE_CHECKREADY_START:
                PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(player.getObjectId(), 5));
                PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(player.getObjectId(), 1));
                break;
            case ALLIANCE_CHECKREADY_AUTOCANCEL:
                PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(player.getObjectId(), 2));
                break;
            case ALLIANCE_CHECKREADY_READY:
                PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(player.getObjectId(), 5));
                if (alliance.getAllianceReadyStatus() == 0) {
                    PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(0, 3));
                }
                break;
            case ALLIANCE_CHECKREADY_NOTREADY:
                PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(player.getObjectId(), 4));
                if (alliance.getAllianceReadyStatus() == 0) {
                    PacketSendUtility.sendPacket(member, new SM_ALLIANCE_READY_CHECK(0, 3));
                }
                break;
        }
        return true;
    }
}
