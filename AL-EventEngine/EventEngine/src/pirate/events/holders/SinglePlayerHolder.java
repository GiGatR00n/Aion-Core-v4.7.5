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

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.List;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pirate.events.enums.EventPlayerLevel;
import pirate.events.enums.EventType;

/**
 *
 * @author flashman
 */
public abstract class SinglePlayerHolder extends BaseEventHolder {

    protected static final Logger log = LoggerFactory.getLogger(SinglePlayerHolder.class);
    protected List<Player> allPlayers = new FastList<Player>();

    public SinglePlayerHolder(int index, EventType etype, EventPlayerLevel epl) {
        super(index, etype, epl);
    }

    @Override
    public final boolean contains(Player p) {
        for (Player plr : this.allPlayers) {
            if (plr != null && plr.getObjectId() == p.getObjectId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deletePlayer(Player player) {
        for (int i = 0; i < this.allPlayers.size(); i++) {
            Player p = this.allPlayers.get(i);
            if (p == null || !p.isOnline()) {
                this.allPlayers.remove(i);
                i--;
                continue;
            }
            if (p.getObjectId() == player.getObjectId()) {
                this.allPlayers.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.allPlayers.isEmpty();
    }

    public final List<Player> getAllPlayers() {
        return allPlayers;
    }

    public final Collection<Player> getPlayresByRace(final Race race) {
        return Collections2.filter(this.allPlayers, new Predicate<Player>() {
            @Override
            public boolean apply(Player t) {
                return t.getRace() == race;
            }
        });
    }

    public final int getPlayersCountByRace(Race race) {
        int count = 0;
        for (Player p : this.allPlayers) {
            if (p.getRace() == race) {
                count += 1;
            }
        }
        return count;
    }
}
