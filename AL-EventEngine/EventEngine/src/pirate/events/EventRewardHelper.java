/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package pirate.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import pirate.events.enums.EventType;
import pirate.events.xml.EventRankTemplate;
import pirate.events.xml.EventRewardItem;
import pirate.events.xml.EventRewardItemGroup;
import pirate.events.xml.EventRewardTemplate;

/**
 *
 * @author f14shm4n
 */
public class EventRewardHelper {

    public static void GiveRewardFor(Player player, EventType etype, EventScore score, int rank) {
        EventRewardTemplate rt = etype.getEventTemplate().getRewardInfo();
        if (rt == null) {
            return;
        }
        EventRankTemplate rw = rt.getRewardByRank(rank);
        if (rw == null) {
            // no rewatd in template for this rank
            return;
        }
        if (rw.getAp() > 0) { // abyss point reward
            AbyssPointsService.addAp(player, rw.getAp());
        }
        if (rw.getGamePoint() > 0) { // toll point reward
            InGameShopEn.getInstance().addToll(player, rw.getGamePoint());
        }
        for (EventRewardItemGroup gr : rw.getRewards()) { // items reward
            for (EventRewardItem item : gr.getItems()) {
                ItemService.addItem(player, item.getItemId(), item.getCount());
            }
        }
    }
}
