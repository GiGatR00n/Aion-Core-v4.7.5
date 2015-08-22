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
package com.aionemu.gameserver.network.aion.iteminfo;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * This blob is sent for weapons. It keeps info about slots that weapon can be
 * equipped to.
 *
 * @author -Nemesiss-
 * @modified Rolandas
 */
public class WeaponInfoBlobEntry extends ItemBlobEntry {

    WeaponInfoBlobEntry() {
        super(ItemBlobType.SLOTS_WEAPON);
    }

    @Override
    public void writeThisBlob(ByteBuffer buf) {
        Item item = ownerItem;

        ItemSlot[] slots = ItemSlot.getSlotsFor(item.getItemTemplate().getItemSlot());
        if (slots.length == 1) {
            writeQ(buf, slots[0].getSlotIdMask());
            writeQ(buf, item.hasFusionedItem() ? 0x00 : 0x02);
            return;
        }
        // must occupy two slots
        if (item.getItemTemplate().isTwoHandWeapon()) {
            writeQ(buf, slots[0].getSlotIdMask() | slots[1].getSlotIdMask());
            writeQ(buf, 0);
        } else {
            // primary and secondary slots
            writeQ(buf, slots[0].getSlotIdMask());
            writeQ(buf, slots[1].getSlotIdMask());
        }
        //writeQ(buf, ItemSlot.getSlotFor(item.getItemTemplate().getItemSlot()).getSlotIdMask());
        // TODO: check this, seems wrong
        //writeQ(buf, item.hasFusionedItem() ? 0x00 : 0x02);
    }

    @Override
    public int getSize() {
        return 16;
    }
}
