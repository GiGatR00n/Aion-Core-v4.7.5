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
package com.aionemu.gameserver.model.items;

import com.aionemu.gameserver.dataholders.loadingutils.adapters.NpcEquipmentList;
import com.aionemu.gameserver.dataholders.loadingutils.adapters.NpcEquippedGearAdapter;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Luno
 */
@XmlJavaTypeAdapter(NpcEquippedGearAdapter.class)
public class NpcEquippedGear implements Iterable<Entry<ItemSlot, ItemTemplate>> {

    private Map<ItemSlot, ItemTemplate> items;
    private short mask;
    private NpcEquipmentList v;

    public NpcEquippedGear(NpcEquipmentList v) {
        this.v = v;
    }

    /**
     * @return short
     */
    public short getItemsMask() {
        if (items == null) {
            init();
        }
        return mask;
    }

    @Override
    public Iterator<Entry<ItemSlot, ItemTemplate>> iterator() {
        if (items == null) {
            init();
        }
        return items.entrySet().iterator();
    }

    /**
     * Here NPC equipment mask is initialized. All NPC slot masks should be
     * lower than 65536
     */
    public void init() {
        synchronized (this) {
            if (items == null) {
                items = new TreeMap<ItemSlot, ItemTemplate>();
                for (ItemTemplate item : v.items) {
                    ItemSlot[] itemSlots = ItemSlot.getSlotsFor(item.getItemSlot());
                    for (ItemSlot itemSlot : itemSlots) {
                        if (items.get(itemSlot) == null) {
                            items.put(itemSlot, item);
                            mask |= itemSlot.getSlotIdMask();
                            break;
                        }
                    }
                }
            }
            v = null;
        }
    }

    /**
     * @param itemSlot
     * @return
     */
    public ItemTemplate getItem(ItemSlot itemSlot) {
        return items != null ? items.get(itemSlot) : null;
    }
}
