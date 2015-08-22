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
package com.aionemu.gameserver.model.items.storage;

import com.aionemu.gameserver.model.gameobjects.Item;
import javolution.util.FastList;
import javolution.util.FastMap;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

/**
 * @author KID
 */
public class ItemStorage {

    public static final long FIRST_AVAILABLE_SLOT = 65535L;
    private FastMap<Integer, Item> items;
    private int limit;
    private int specialLimit;
    private final StorageType storageType;

    public ItemStorage(StorageType storageType) {
        this.limit = storageType.getLimit();
        this.specialLimit = storageType.getSpecialLimit();
        this.storageType = storageType;
        this.items = FastMap.newInstance();
    }

    public FastList<Item> getItems() {
        FastList<Item> temp = FastList.newInstance();
        temp.addAll(items.values());
        return temp;
    }

    public int getLimit() {
        return this.limit;
    }

    public boolean setLimit(int limit) {
        if (getCubeItems().size() > limit) {
            return false;
        }

        this.limit = limit;
        return true;
    }

    public int getRowLength() {
        return storageType.getLength();
    }

    public Item getFirstItemById(int itemId) {
        for (Item item : items.values()) {
            if (item.getItemTemplate().getTemplateId() == itemId) {
                return item;
            }
        }
        return null;
    }

    public FastList<Item> getItemsById(int itemId) {
        FastList<Item> temp = FastList.newInstance();
        for (Item item : items.values()) {
            if (item.getItemTemplate().getTemplateId() == itemId) {
                temp.add(item);
            }
        }
        return temp;
    }

    public Item getItemByObjId(int itemObjId) {
        return this.items.get(itemObjId);
    }

    public long getSlotIdByItemId(int itemId) {
        for (Item item : this.items.values()) {
            if (item.getItemTemplate().getTemplateId() == itemId) {
                return item.getEquipmentSlot();
            }
        }
        return -1;
    }

    public Item getItemBySlotId(short slotId) {
        for (Item item : getCubeItems()) {
            if (item.getEquipmentSlot() == slotId) {
                return item;
            }
        }
        return null;
    }

    public Item getSpecialItemBySlotId(short slotId) {
        for (Item item : getSpecialCubeItems()) {
            if (item.getEquipmentSlot() == slotId) {
                return item;
            }
        }
        return null;
    }

    public long getSlotIdByObjId(int objId) {
        Item item = this.getItemByObjId(objId);
        if (item != null) {
            return item.getEquipmentSlot();
        } else {
            return -1;
        }
    }

    public long getNextAvailableSlot() {
        return FIRST_AVAILABLE_SLOT;
    }

    public boolean putItem(Item item) {
        if (this.items.containsKey(item.getObjectId())) {
            return false;
        }

        this.items.put(item.getObjectId(), item);
        return true;
    }

    public Item removeItem(int objId) {
        return this.items.remove(objId);
    }

    public boolean isFull() {
        return getCubeItems().size() >= limit;
    }

    public boolean isFullSpecialCube() {
        return getSpecialCubeItems().size() >= specialLimit;
    }

    public List<Item> getSpecialCubeItems() {
        return select(items.values(), having(on(Item.class).getItemTemplate().getExtraInventoryId(), greaterThan(0)));
    }

    public List<Item> getCubeItems() {
        return select(items.values(), having(on(Item.class).getItemTemplate().getExtraInventoryId(), lessThan(1)));
    }

    public int getFreeSlots() {
        return limit - getCubeItems().size();
    }

    public int getSpecialCubeFreeSlots() {
        return specialLimit - getSpecialCubeItems().size();
    }

    public int size() {
        return this.items.size();
    }
}
