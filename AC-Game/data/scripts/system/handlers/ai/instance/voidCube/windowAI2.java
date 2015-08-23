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

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;


@AIName("window")
public class windowAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
      if(getOwner().getNpcId() == 701581)
      {
          if (player.getInventory().getItemCountByItemId(164000271) > 0)
          {
              player.getInventory().decreaseByItemId(164000271, 1);
              getOwner().getController().die();
          }
          else {
              PacketSendUtility.sendBrightYellowMessageOnCenter(player, "\u041d\u0443\u0436\u043d\u0430 \u0411\u043e\u043c\u0431\u0430 \u0434\u043b\u044f \u0440\u0430\u0437\u0440\u0443\u0448\u0435\u043d\u0438\u044f \u043a\u0440\u0430\u0441\u043d\u044b\u0445 \u0432\u043e\u0440\u043e\u0442 \u0431\u0430\u0440\u044c\u0435\u0440\u0430.");
          }
      }
        if(getOwner().getNpcId() == 701582)
        {
            if (player.getInventory().getItemCountByItemId(164000272) > 0)
            {
                player.getInventory().decreaseByItemId(164000272, 1);
                getOwner().getController().die();
            }
            else {
                PacketSendUtility.sendBrightYellowMessageOnCenter(player, "\u041d\u0443\u0436\u043d\u0430 \u0411\u043e\u043c\u0431\u0430 \u0434\u043b\u044f \u0440\u0430\u0437\u0440\u0443\u0448\u0435\u043d\u0438\u044f \u0441\u0438\u043d\u0438\u0445 \u0432\u043e\u0440\u043e\u0442 \u0431\u0430\u0440\u044c\u0435\u0440\u0430.");
            }
        }
        if(getOwner().getNpcId() == 701583)
        {
            if (player.getInventory().getItemCountByItemId(164000273) > 0)
            {
                player.getInventory().decreaseByItemId(164000273, 1);
                getOwner().getController().die();
            }
            else {
                PacketSendUtility.sendBrightYellowMessageOnCenter(player, "\u041d\u0443\u0436\u043d\u0430 \u0411\u043e\u043c\u0431\u0430 \u0434\u043b\u044f \u0440\u0430\u0437\u0440\u0443\u0448\u0435\u043d\u0438\u044f \u0436\u0435\u043b\u0442\u044b\u0445 \u0432\u043e\u0440\u043e\u0442 \u0431\u0430\u0440\u044c\u0435\u0440\u0430.");
            }
        }
          else
            return;

    }

    @Override
    protected void handleUseItemFinish(Player player) {
        AI2Actions.deleteOwner(this);
    }
}
