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
package ai.instance.empyreanCrucible;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.instance.StageType;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Luzien
 */
@AIName("spectral_warrior")
public class SpectralWarriorAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isDone = new AtomicBoolean(false);

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 50 && isDone.compareAndSet(false, true)) {
            getPosition().getWorldMapInstance().getInstanceHandler().onChangeStage(StageType.START_STAGE_6_ROUND_5);

            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    resurrectAllies();
                }
            }, 2000);
        }
    }

    private void resurrectAllies() {
        for (VisibleObject obj : getKnownList().getKnownObjects().values()) {
            if (obj instanceof Npc) {
                Npc npc = (Npc) obj;

                if (npc == null || NpcActions.isAlreadyDead(npc)) {
                    continue;
                }

                switch (npc.getNpcId()) {
                    case 205413:
                        spawn(217576, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading());
                        NpcActions.delete(npc);
                        break;
                    case 205414:
                        spawn(217577, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading());
                        NpcActions.delete(npc);
                        break;
                }
            }
        }
    }
}
