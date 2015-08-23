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
package ai.instance.elementisForest;

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("jurdins_illusion")
public class JurdinsIllusionAI2 extends GeneralNpcAI2 {

    private AtomicBoolean isSpawned = new AtomicBoolean(false);

    @Override
    protected void handleDialogStart(Player player) {
        if (isSpawned.compareAndSet(false, true)) {
            WorldPosition p = getPosition();
            final int instanceId = p.getInstanceId();
            final int worldId = p.getMapId();
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            spawn(worldId, 217238, 472.989f, 798.109f, 130.072f, (byte) 90, 0, instanceId);
                            Npc smoke = (Npc) spawn(282465, 472.989f, 798.109f, 130.072f, (byte) 0);
                            NpcActions.delete(smoke);
                        }
                    }, 4000);
                    AI2Actions.deleteOwner(JurdinsIllusionAI2.this);
                }
            }, 3000);
        }
        super.handleDialogStart(player);
    }
}
