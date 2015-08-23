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
package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.StateEvents;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.ai2.handler.FollowEventHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;

/**
 * @author ATracer
 */
@AIName("following")
public class FollowingNpcAI2 extends GeneralNpcAI2 {

    @Override
    protected void handleFollowMe(Creature creature) {
        FollowEventHandler.follow(this, creature);
    }

    @Override
    protected boolean canHandleEvent(AIEventType eventType) {
        switch (getState()) {
            case DESPAWNED:
                return StateEvents.DESPAWN_EVENTS.hasEvent(eventType);
            case DIED:
                return StateEvents.DEAD_EVENTS.hasEvent(eventType);
            case CREATED:
                return StateEvents.CREATED_EVENTS.hasEvent(eventType);
        }

        if (eventType == AIEventType.CREATURE_MOVED) {
            return getState() == AIState.FOLLOWING;
        }
        return true;
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        if (creature == getOwner().getTarget()) {
            FollowEventHandler.creatureMoved(this, creature);
        } else if (getOwner().getTarget() == null) {
            FollowEventHandler.stopFollow(this, creature);
        }
    }

    @Override
    protected void handleStopFollowMe(Creature creature) {
        FollowEventHandler.stopFollow(this, creature);
    }
}
