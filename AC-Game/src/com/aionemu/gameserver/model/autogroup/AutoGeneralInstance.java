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
package com.aionemu.gameserver.model.autogroup;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamType;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.model.templates.portal.PortalLoc;
import com.aionemu.gameserver.model.templates.portal.PortalPath;
import com.aionemu.gameserver.services.teleport.TeleportService2;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author xTz
 */
public class AutoGeneralInstance extends AutoInstance {

    @Override
    public AGQuestion addPlayer(Player player, SearchInstance searchInstance) {
        super.writeLock();
        try {
            if (!satisfyTime(searchInstance) || (players.size() >= agt.getPlayerSize())) {
                return AGQuestion.FAILED;
            }
            PlayerClass playerClass = player.getPlayerClass();
            int clericSize = getPlayersByClass(PlayerClass.CLERIC).size();
            int templarSize = getPlayersByClass(PlayerClass.TEMPLAR).size();
            if (playerClass.equals(PlayerClass.CLERIC)) {
                if (clericSize > 0) {
                    return AGQuestion.FAILED;
                }
            } else if (playerClass.equals(PlayerClass.TEMPLAR)) {
                if (templarSize > 0) {
                    return AGQuestion.FAILED;
                }
            } else {
                int size = players.size();
                size -= clericSize;
                size -= templarSize;
                if (size >= 4) {
                    return AGQuestion.FAILED;
                }
            }
            players.put(player.getObjectId(), new AGPlayer(player));
            return instance != null ? AGQuestion.ADDED : (players.size() == agt.getPlayerSize() ? AGQuestion.READY : AGQuestion.ADDED);
        } finally {
            super.writeUnlock();
        }
    }

    @Override
    public void onEnterInstance(Player player) {
        super.onEnterInstance(player);
        List<Player> playersByRace = instance.getPlayersInside();
        if (playersByRace.size() == 1 && !playersByRace.get(0).isInGroup2()) {
            PlayerGroup newGroup = PlayerGroupService.createGroup(playersByRace.get(0), player, TeamType.AUTO_GROUP);
            int groupId = newGroup.getObjectId();
            if (!instance.isRegistered(groupId)) {
                instance.register(groupId);
            }
        } else if (!playersByRace.isEmpty() && playersByRace.get(0).isInGroup2()) {
            PlayerGroupService.addPlayer(playersByRace.get(0).getPlayerGroup2(), player);
        }
        Integer object = player.getObjectId();
        if (!instance.isRegistered(object)) {
            instance.register(object);
        }
    }

    @Override
    public void onPressEnter(Player player) {
        super.onPressEnter(player);
        int worldId = instance.getMapId();
        PortalPath portal = DataManager.PORTAL2_DATA.getPortalDialog(worldId, 10000, player.getRace());
        if (portal == null) {
            return;
        }
        PortalLoc loc = DataManager.PORTAL_LOC_DATA.getPortalLoc(portal.getLocId());
        if (loc == null) {
            return;
        }
        TeleportService2.teleportTo(player, worldId, instance.getInstanceId(), loc.getX(), loc.getY(), loc.getZ(), loc.getH());
        long instanceCoolTime = DataManager.INSTANCE_COOLTIME_DATA.getInstanceEntranceCooltime(player, worldId);
        if (instanceCoolTime > 0) {
            player.getPortalCooldownList().addPortalCooldown(worldId, instanceCoolTime);
        }
    }

    @Override
    public void onLeaveInstance(Player player) {
        super.unregister(player);
        PlayerGroupService.removePlayer(player);
    }

    private List<AGPlayer> getPlayersByClass(PlayerClass playerClass) {
        return select(players, having(on(AGPlayer.class).getPlayerClass(), equalTo(playerClass)));
    }
}
