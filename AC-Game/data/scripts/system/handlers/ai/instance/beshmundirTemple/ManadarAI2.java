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
package ai.instance.beshmundirTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;

/**
 * @author xTz
 */
@AIName("manadar")
public class ManadarAI2 extends AggressiveNpcAI2 {

    private boolean isStart = false;

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 90 && !isStart) {
            isStart = true;
            check();
        }
    }

    @Override
    protected void handleBackHome() {
        isStart = false;
        super.handleBackHome();
    }

    private void check() {
        if (getPosition().isSpawned() && !isAlreadyDead() && isStart) {
            for (int i = 0; i < 5; i++) {
                int distance = Rnd.get(4, 11);
                int nrNpc = Rnd.get(1, 2);
                switch (nrNpc) {
                    case 1:
                        nrNpc = 281545;
                        break;
                    case 2:
                        nrNpc = 281756;
                        break;
                }
                rndSpawnInRange(nrNpc, distance);
            }
            doSchedule();
        }
    }

    private void rndSpawnInRange(int npcId, float distance) {
        float direction = Rnd.get(0, 199) / 100f;
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        spawn(npcId, getPosition().getX() + x1, getPosition().getY() + y1, getPosition().getZ(), (byte) 0);
    }

    private void doSchedule() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                check();
            }
        }, 6000);
    }
}
