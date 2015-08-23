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
package ai.instance.azoturanFortress;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Antraxx
 */
@AIName("betrayericaronix")
public class BetrayerIcaronixAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isStartEvent = new AtomicBoolean(false);

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 50) {
            if (isStartEvent.compareAndSet(false, true)) {
                scheduleSpawnIcaronixTheBetrayer(
                        this.getPosition().getX(),
                        this.getPosition().getY(),
                        this.getPosition().getZ(),
                        this.getPosition().getHeading());
                AI2Actions.deleteOwner(this);
            }
        }
    }

    /**
     * @param h
     */
    private void scheduleSpawnIcaronixTheBetrayer(final float x, final float y, final float z, final byte h) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                spawn(214599, x, y, z, (byte) h);
            }
        }, 5000);
    }
}
