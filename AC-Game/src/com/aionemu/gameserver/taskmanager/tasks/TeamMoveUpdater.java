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
package com.aionemu.gameserver.taskmanager.tasks;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.common.legacy.GroupEvent;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.taskmanager.AbstractIterativePeriodicTaskManager;

/**
 * @author Sarynth Supports PlayerGroup and PlayerAlliance movement updating.
 */
public final class TeamMoveUpdater extends AbstractIterativePeriodicTaskManager<Player> {

    private static final class SingletonHolder {

        private static final TeamMoveUpdater INSTANCE = new TeamMoveUpdater();
    }

    public static TeamMoveUpdater getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public TeamMoveUpdater() {
        super(2000);
    }

    @Override
    protected void callTask(Player player) {
        if (player.isInGroup2()) {
            PlayerGroupService.updateGroup(player, GroupEvent.MOVEMENT);
        }
        if (player.isInAlliance2()) {
            PlayerAllianceService.updateAlliance(player, PlayerAllianceEvent.MOVEMENT);
        }

        // Remove task from list. It will be re-added if player moves again.
        this.stopTask(player);
    }

    @Override
    protected String getCalledMethodName() {
        return "teamMoveUpdate()";
    }
}
