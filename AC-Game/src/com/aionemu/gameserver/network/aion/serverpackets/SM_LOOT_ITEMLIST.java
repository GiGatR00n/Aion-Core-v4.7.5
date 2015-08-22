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
package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Set;

import javolution.util.FastList;

import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.drop.Drop;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemCategory;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author alexa026, Avol, Corrected by Metos modified by ATracer, KID
 */
public class SM_LOOT_ITEMLIST extends AionServerPacket {

    private int targetObjectId;
    private FastList<DropItem> dropItems;

    public SM_LOOT_ITEMLIST(int targetObjectId, Set<DropItem> setItems, Player player) {
        this.targetObjectId = targetObjectId;
        this.dropItems = new FastList<DropItem>();
        if (setItems == null) {
            LoggerFactory.getLogger(SM_LOOT_ITEMLIST.class).warn("null Set<DropItem>, skip");
            return;
        }

        for (DropItem item : setItems) {
            if (item.getPlayerObjId() == 0 || player.getObjectId() == item.getPlayerObjId()) {
                if (DataManager.ITEM_DATA.getItemTemplate(item.getDropTemplate().getItemId()) != null) {
                    dropItems.add(item);
                }
            }
        }
    }

    /**
     * {@inheritDoc} dc
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(targetObjectId);
        writeC(dropItems.size());

        for (DropItem dropItem : dropItems) {
            Drop drop = dropItem.getDropTemplate();
            writeC(dropItem.getIndex()); // index in droplist
            writeD(drop.getItemId());
            writeD((int) dropItem.getCount());
            writeH(dropItem.getOptionalSocket());
            writeC(0);
            ItemTemplate template = drop.getItemTemplate();
            writeC(!template.getCategory().equals(ItemCategory.QUEST) && !template.isTradeable() ? 1 : 0);
        }
        FastList.recycle(dropItems);
    }
}
