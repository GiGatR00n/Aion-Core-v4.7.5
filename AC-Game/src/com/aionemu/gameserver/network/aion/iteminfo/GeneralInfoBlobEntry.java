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
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * This blob entry is sent with ALL items. (unless partial blob is constructed,
 * ie: sending equip slot only) It is the first and only block for non-equipable
 * items, and the last blob for EquipableItems
 *
 * @author -Nemesiss-
 * @modified Rolandas
 */
public class GeneralInfoBlobEntry extends ItemBlobEntry {

    GeneralInfoBlobEntry() {
        super(ItemBlobType.GENERAL_INFO);
    }

    @Override
    public void writeThisBlob(ByteBuffer buf) {// TODO what with kinah?
        Item item = ownerItem;
        writeH(buf, item.getItemMask(owner));
        writeQ(buf, item.getItemCount());
        writeS(buf, item.getItemCreator());// Creator name
        writeC(buf, 0);
        writeD(buf, item.getExpireTimeRemaining()); // Disappears time
        writeD(buf, 0);
        writeD(buf, item.getTemporaryExchangeTimeRemaining());
        writeH(buf, 0);
        writeD(buf, 0);
    }

    @Override
    public int getSize() {
        return 29 + ownerItem.getItemCreator().length() * 2 + 2;
    }
}
