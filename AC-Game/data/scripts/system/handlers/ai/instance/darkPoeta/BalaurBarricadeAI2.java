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
package ai.instance.darkPoeta;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ritsu
 */
@AIName("balaurbarricade")
public class BalaurBarricadeAI2 extends AggressiveNpcAI2 {

    protected List<Integer> percents = new ArrayList<Integer>();

    @Override
    public int modifyDamage(int damage) {
        return 1;
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 60:
                    case 10:
                        sp();
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void sp() {
        Npc npc = getOwner();
        float direction = Rnd.get(0, 199) / 100f;
        int distance = Rnd.get(1, 4);
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        if (npc.getNpcId() == 700517 || npc.getNpcId() == 700556) {
            spawn(215262, npc.getX() + x1, npc.getY() + y1, npc.getZ(), (byte) 0);
            spawn(215262, npc.getX() + y1, npc.getY() + x1, npc.getZ(), (byte) 0);
        } else if (npc.getNpcId() == 700558) {
            spawn(215262, npc.getX() + x1, npc.getY() + y1, npc.getZ(), (byte) 0);
            spawn(214883, npc.getX() + y1, npc.getY() + x1, npc.getZ(), (byte) 0);
        }
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{60, 10});
    }

    @Override
    protected void handleSpawned() {
        addPercent();
        super.handleDespawned();
    }

    @Override
    protected void handleBackHome() {
        addPercent();
        super.handleBackHome();
    }

    @Override
    protected void handleDespawned() {
        percents.clear();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        percents.clear();
        super.handleDied();
    }
}
