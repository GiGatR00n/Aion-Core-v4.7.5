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
package com.aionemu.gameserver.model.trade;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.RepurchaseService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xTz
 */
public class RepurchaseList {

    private final int sellerObjId;
    private List<Item> repurchases = new ArrayList<Item>();

    public RepurchaseList(int sellerObjId) {
        this.sellerObjId = sellerObjId;
    }

    /**
     * @param player
     * @param itemObjectId
     * @param count
     */
    public void addRepurchaseItem(Player player, int itemObjectId, long count) {
        Item item = RepurchaseService.getInstance().getRepurchaseItem(player, itemObjectId);
        if (item != null) {
            repurchases.add(item);
        }
    }

    /**
     * @return the tradeItems
     */
    public List<Item> getRepurchaseItems() {
        return repurchases;
    }

    public int size() {
        return repurchases.size();
    }

    public final int getSellerObjId() {
        return sellerObjId;
    }
}
