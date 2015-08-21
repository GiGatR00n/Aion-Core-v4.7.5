/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package pirate.events.holders;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import java.util.ArrayList;
import java.util.List;
import pirate.events.enums.EventPlayerLevel;
import pirate.events.enums.EventRergisterState;
import pirate.events.enums.EventType;

/**
 *
 * @author f14shm4n
 */
public class PlayerGroupEventHolder extends SinglePlayerHolder {

    private final List<PlayerGroup> groups = new ArrayList<PlayerGroup>();

    public PlayerGroupEventHolder(int index, EventType etype, EventPlayerLevel epl) {
        super(index, etype, epl);
    }

    @Override
    public boolean isReadyToGo() {
        if (groups.size() == this.getStartCondition().getGroupsToStart()) {
            for (PlayerGroup pg : this.groups) {
                if (pg.size() != this.getStartCondition().getPlayersForEachGroup()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canAddGroup(PlayerGroup group) {
        if (this.contains(group)) {
            return false;
        }
        for (Player m : group.getMembers()) {
            if (this.contains(m)) {
                return false;
            }
        }
        if (this.isReadyToGo()) {
            return false;
        }
        return true;
    }

    @Override
    public EventRergisterState addPlayerGroup(PlayerGroup group) {
        this.groups.add(group);
        for (Player m : group.getMembers()) {
            this.addPlayer(m);
        }
        return EventRergisterState.HOLDER_ADD_GROUP;
    }

    @Override
    public boolean deletePlayerGroup(PlayerGroup group) {
        for (int i = 0; i < this.groups.size(); i++) {
            PlayerGroup pg = this.groups.get(i);
            if (pg != null) {
                for (Player m : pg.getMembers()) {
                    this.deletePlayer(m);
                }
                this.groups.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(PlayerGroup group) {
        if (this.groups.contains(group)) {
            return true;
        }
        for (PlayerGroup pg : this.groups) {
            if (pg.getObjectId().intValue() == group.getObjectId().intValue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EventRergisterState addPlayer(Player player) {
        this.allPlayers.add(player);
        return EventRergisterState.HOLDER_ADD_PLAYER;
    }

    public final List<PlayerGroup> getPlayerGroups() {
        return this.groups;
    }
}
