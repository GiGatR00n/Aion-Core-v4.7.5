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
package ai.instance.haramel;

import ai.ChestAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author xTz
 */
@AIName("haramelchest")
public class HaramelChestAI2 extends ChestAI2 {

    @Override
    protected void handleDespawned() {
        WorldPosition p = getPosition();
        if (p != null && p.getWorldMapInstance() != null) {
            spawn(700852, 224.598f, 331.143f, 141.892f, (byte) 90);
        }
        super.handleDespawned();
    }
}
