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

/**
 * @author ATracer
 */
public class ExchangeItem {

    private int itemObjId;
    private long itemCount;
    private int itemDesc;
    private Item item;

    /**
     * Used when exchange item != original item
     *
     * @param itemObjId
     * @param itemCount
     * @param item
     */
    public ExchangeItem(int itemObjId, long itemCount, Item item) {
        this.itemObjId = itemObjId;
        this.itemCount = itemCount;
        this.item = item;
        this.itemDesc = item.getItemTemplate().getNameId();
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @param countToAdd
     */
    public void addCount(long countToAdd) {
        this.itemCount += countToAdd;
        this.item.setItemCount(itemCount);
    }

    /**
     * @return the newItem
     */
    public Item getItem() {
        return item;
    }

    /**
     * @return the itemObjId
     */
    public int getItemObjId() {
        return itemObjId;
    }

    /**
     * @return the itemCount
     */
    public long getItemCount() {
        return itemCount;
    }

    /**
     * @return the itemDesc
     */
    public int getItemDesc() {
        return itemDesc;
    }
}
