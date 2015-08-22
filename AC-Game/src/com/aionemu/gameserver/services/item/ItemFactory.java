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
package com.aionemu.gameserver.services.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.utils.idfactory.IDFactory;

/**
 * @author ATracer
 */
public class ItemFactory {

    private static final Logger log = LoggerFactory.getLogger(ItemFactory.class);

    public static final Item newItem(int itemId) {
        ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
        if (itemTemplate == null) {
            log.error("Item was not populated correctly. Item template is missing for item id: " + itemId);
            return null;
        }
        return new Item(IDFactory.getInstance().nextId(), itemTemplate);
    }

    public static Item newItem(int itemId, long count) {
        Item item = newItem(itemId);
        item.setItemCount(calculateCount(item.getItemTemplate(), count));
        return item;
    }

    private static final long calculateCount(ItemTemplate itemTemplate, long count) {
        long maxStackCount = itemTemplate.getMaxStackCount();
        if (count > maxStackCount && !itemTemplate.isKinah()) {
            count = maxStackCount;
        }
        return count;
    }
}
