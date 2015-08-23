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
package ai.instance.voidCube;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@AIName("barukan")
public class barukanAI2 extends AggressiveNpcAI2 {
    protected List<Integer> percents = new ArrayList<Integer>();

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private boolean canThink = true;

    @Override
    public boolean canThink() {
        return canThink;
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
                    case 50:

                        Npc npc = getOwner();
                        float direction = Rnd.get(0, 199) / 100f;
                        int distance = Rnd.get(1, 3);
                        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
                        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
                        spawn(230092, npc.getX() + x1, npc.getY() + y1, npc.getZ(), (byte) 0);
                        spawn(230092, npc.getX() + y1, npc.getY() + x1, npc.getZ(), (byte) 0);

            }
            percents.remove(percent);
            break;
            }
        }
    }

    private void despawnAdds() {
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        deleteNpcs(instance.getNpcs(230092));
    }

    private void deleteNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.getController().onDelete();
            }
        }
    }

    @Override
    protected void handleDied() {
        percents.clear();
        despawnAdds();
        super.handleDied();
    }
    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{50});
    }

    @Override
    protected void handleBackHome() {
        addPercent();
        super.handleBackHome();
        despawnAdds();
        isHome.set(true);
    }

}