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

import ai.AggressiveNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;

/**
 * @author Romanz
 */
@AIName("kutol")
public class HeadKutolAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);

        if (Rnd.get(1, 100) < 1) {
            spawnClone();
        }
    }

    private void spawnClone() {
        Npc KutolClone = getPosition().getWorldMapInstance().getNpc(282302);
        int random = Rnd.get(1, 3);
        if (KutolClone == null) {
            switch (random) {
                case 1:
                    spawn(282302, getOwner().getX(), getOwner().getY(), getOwner().getZ() + 2, (byte) 3);
                    break;
                case 2:
                    spawn(282302, getOwner().getX(), getOwner().getY(), getOwner().getZ() + 2, (byte) 3);
                    spawn(282302, getOwner().getX() - 5, getOwner().getY() - 3, getOwner().getZ() + 2, (byte) 3);
                    break;
                default:
                    spawn(282302, getOwner().getX(), getOwner().getY(), getOwner().getZ() + 2, (byte) 3);
                    spawn(282302, getOwner().getX() - 5, getOwner().getY() - 3, getOwner().getZ() + 2, (byte) 3);
                    spawn(282302, getOwner().getX() + 5, getOwner().getY() - 3, getOwner().getZ() + 2, (byte) 3);
                    break;
            }
        }
    }
}
