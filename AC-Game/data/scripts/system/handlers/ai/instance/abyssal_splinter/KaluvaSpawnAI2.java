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
package ai.instance.abyssal_splinter;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;

@AIName("kaluvaspawn")
public class KaluvaSpawnAI2 extends NpcAI2 {

    private Future<?> task;

    @Override
    protected void handleDied() {
        super.handleDied();
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
        checkKaluva();
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        scheduleHatch();
    }

    private void checkKaluva() {
        Npc kaluva = getPosition().getWorldMapInstance().getNpc(216950);
        if (kaluva != null && !kaluva.getLifeStats().isAlreadyDead()) {
            kaluva.getEffectController().removeEffect(19152);
        }
        AI2Actions.deleteOwner(this);
    }

    private void scheduleHatch() {
        task = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    hatchAdds();
                    checkKaluva();
                }
            }
        }, 22000); // schedule hatch when debuff ends(20s)
    }

    private void hatchAdds() { // 4 different spawn-formations; See Powerwiki for more information
        WorldPosition p = getPosition();
        switch (Rnd.get(1, 4)) {
            case 1:
                spawn(281911, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(281911, p.getX(), p.getY(), p.getZ(), p.getHeading());
                break;
            case 2:
                for (int i = 0; i < 12; i++) {
                    spawn(281912, p.getX(), p.getY(), p.getZ(), p.getHeading());
                }
                break;
            case 3:
                spawn(282057, p.getX(), p.getY(), p.getZ(), p.getHeading());
                break;
            case 4:
                spawn(281911, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(281912, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(281912, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(281912, p.getX(), p.getY(), p.getZ(), p.getHeading());
                break;
        }
    }
}
