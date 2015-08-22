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

import com.aionemu.gameserver.model.templates.item.ItemTemplate;

/**
 * @author ATracer
 */
public class TradeItem {

    private int itemId;
    private long count;
    private ItemTemplate itemTemplate;

    public TradeItem(int itemId, long count) {
        super();
        this.itemId = itemId;
        this.count = count;
    }

    /**
     * @return the itemTemplate
     */
    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    /**
     * @param itemTemplate the itemTemplate to set
     */
    public void setItemTemplate(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @return the count
     */
    public long getCount() {
        return count;
    }

    /**
     * This method will decrease the current count
     */
    public void decreaseCount(long decreaseCount) {
        //TODO probably <= count ?
        if (decreaseCount < count) {
            this.count = count - decreaseCount;
        }
    }
}
