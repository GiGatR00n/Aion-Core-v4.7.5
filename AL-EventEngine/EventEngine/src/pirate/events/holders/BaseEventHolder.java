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
import pirate.events.enums.EventPlayerLevel;
import pirate.events.enums.EventRergisterState;
import pirate.events.enums.EventType;
import pirate.events.xml.EventStartCondition;

/**
 *
 * @author f14shm4n
 */
public abstract class BaseEventHolder implements IEventHolder {

    private final int _index;
    private final EventPlayerLevel holderLevel;
    private final EventType eventType;
    private EventStartCondition startCond;

    public BaseEventHolder(int index, EventType etype, EventPlayerLevel epl) {
        this._index = index;
        this.holderLevel = epl;
        this.eventType = etype;
        this.startCond = this.eventType.getEventTemplate().getStartCondition();
    }

    @Override
    public int Index() {
        return this._index;
    }

    @Override
    public final EventPlayerLevel getHolderLevel() {
        return holderLevel;
    }

    @Override
    public final EventType getEventType() {
        return eventType;
    }

    public EventStartCondition getStartCondition() {
        return this.startCond;
    }

    @Override
    public boolean canAddPlayer(Player player) {
        return false;
    }

    @Override
    public EventRergisterState addPlayer(Player player) {
        return null;
    }

    @Override
    public boolean deletePlayer(Player player) {
        return false;
    }

    @Override
    public boolean isReadyToGo() {
        return false;
    }

    @Override
    public boolean contains(Player p) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean canAddGroup(PlayerGroup group) {
        return false;
    }

    @Override
    public EventRergisterState addPlayerGroup(PlayerGroup group) {
        return null;
    }

    @Override
    public boolean deletePlayerGroup(PlayerGroup group) {
        return false;
    }

    @Override
    public boolean contains(PlayerGroup group) {
        return false;
    }    
}
