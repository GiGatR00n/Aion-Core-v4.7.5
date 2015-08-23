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
package ai.instance.unstableSplinterpath;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;

@AIName("unstablekaluvaspawn")
public class UnstableKaluvaSpawnAI2 extends NpcAI2 {

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
    protected void handleCreatureSee(Creature creature) {
        checkDistance(this, creature);
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(this, creature);
    }

    @SuppressWarnings("unused")
    private void checkDistance(NpcAI2 ai, Creature creature) {
        Npc kaluva = getPosition().getWorldMapInstance().getNpc(219553);
        if (creature instanceof Npc) {
            if (MathUtil.isIn3dRange(getOwner(), kaluva, 7) && task == null) {
                kaluva.getEffectController().removeEffect(19152);
                scheduleHatch();
            }
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        SkillEngine.getInstance().getSkill(getOwner(), 19222, 55, getOwner()).useNoAnimationSkill();
    }

    private void checkKaluva() {
        Npc kaluva = getPosition().getWorldMapInstance().getNpc(219553);
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
        }, 28000); // schedule hatch when debuff ends(20s)
    }

    private void hatchAdds() { // 4 different spawn-formations; See Powerwiki for more information
        WorldPosition p = getPosition();
        switch (Rnd.get(1, 4)) {
            case 1:
                spawn(219572, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(219572, p.getX(), p.getY(), p.getZ(), p.getHeading());
                break;
            case 2:
                for (int i = 0; i < 12; i++) {
                    spawn(219573, p.getX(), p.getY(), p.getZ(), p.getHeading());
                }
                break;
            case 3:
                spawn(219574, p.getX(), p.getY(), p.getZ(), p.getHeading());
                break;
            case 4:
                spawn(219572, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(219573, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(219573, p.getX(), p.getY(), p.getZ(), p.getHeading());
                spawn(219573, p.getX(), p.getY(), p.getZ(), p.getHeading());
                break;
        }
    }
}
