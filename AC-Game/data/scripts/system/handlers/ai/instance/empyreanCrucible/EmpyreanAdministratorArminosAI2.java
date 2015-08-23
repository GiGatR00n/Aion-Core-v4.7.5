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

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.services.NpcShoutsService;

/**
 * @author xTz
 */
@AIName("empadministratorarminos")
public class EmpyreanAdministratorArminosAI2 extends NpcAI2 {

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        startEvent();
    }

    private void startEvent() {
        switch (getNpcId()) {
            case 217744:
                sendMsg(1500247, getObjectId(), false, 8000);
                sendMsg(1500250, getObjectId(), false, 20000);
                sendMsg(1500251, getObjectId(), false, 60000);
                break;
            case 217749:
                sendMsg(1500252, getObjectId(), false, 8000);
                sendMsg(1500253, getObjectId(), false, 16000);
                sendMsg(1400982, 0, false, 25000);
                sendMsg(1400988, 0, false, 27000);
                sendMsg(1400989, 0, false, 29000);
                sendMsg(1400990, 0, false, 31000);
                sendMsg(1401013, 0, false, 93000);
                sendMsg(1401014, 0, false, 113000);
                sendMsg(1401015, 0, false, 118000);
                sendMsg(1500255, getObjectId(), true, 118000);
                break;
            //case
            //despawn after 1min
        }
    }

    private void sendMsg(int msg, int Obj, boolean isShout, int time) {
        NpcShoutsService.getInstance().sendMsg(getPosition().getWorldMapInstance(), msg, Obj, isShout, 0, time);
    }
}
