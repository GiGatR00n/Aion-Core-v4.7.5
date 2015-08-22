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

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob;

/**
 * @author Avol, xTz
 */
public class SM_VIEW_PLAYER_DETAILS extends AionServerPacket {

    private List<Item> items;
    private int itemSize;
    private int targetObjId;
    private Player player;

    public SM_VIEW_PLAYER_DETAILS(List<Item> items, Player player) {
        this.player = player;
        this.targetObjId = player.getObjectId();
        this.items = items;
        this.itemSize = items.size();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());

        writeD(targetObjId);
        writeC(11);
        writeH(itemSize);
        for (Item item : items) {
            writeItemInfo(item);
        }
    }

    private void writeItemInfo(Item item) {
        ItemTemplate template = item.getItemTemplate();

        writeD(0);
        writeD(template.getTemplateId());
        writeNameId(template.getNameId());

        ItemInfoBlob itemInfoBlob = ItemInfoBlob.getFullBlob(player, item);
        itemInfoBlob.writeMe(getBuf());
    }
}
