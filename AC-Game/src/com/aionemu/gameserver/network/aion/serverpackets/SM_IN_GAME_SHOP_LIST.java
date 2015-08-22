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

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Collection;
import java.util.List;

import javolution.util.FastList;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.ingameshop.IGItem;
import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author xTz, KID
 */
public class SM_IN_GAME_SHOP_LIST extends AionServerPacket {

    private Player player;
    private int nrList;
    private int salesRanking;
    private TIntObjectHashMap<FastList<IGItem>> allItems = new TIntObjectHashMap<FastList<IGItem>>();

    public SM_IN_GAME_SHOP_LIST(Player player, int nrList, int salesRanking) {
        this.player = player;
        this.nrList = nrList;
        this.salesRanking = salesRanking;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        List<IGItem> inAllItems;
        Collection<IGItem> items;
        byte category = player.inGameShop.getCategory();
        byte subCategory = player.inGameShop.getSubCategory();
        if (salesRanking == 1) {
            items = InGameShopEn.getInstance().getItems(category);
            int size = 0;
            int tabSize = 9;
            int f = 0;
            for (IGItem a : items) {
                if (subCategory != 2) {
                    if (a.getSubCategory() != subCategory) {
                        continue;
                    }
                }

                if (size == tabSize) {
                    tabSize += 9;
                    f++;
                }
                FastList<IGItem> template = allItems.get(f);
                if (template == null) {
                    template = FastList.newInstance();
                    allItems.put(f, template);
                }
                template.add(a);
                size++;
            }

            inAllItems = allItems.get(nrList);
            writeD(salesRanking);
            writeD(nrList);
            writeD(size > 0 ? tabSize : 0);
            writeH(inAllItems == null ? 0 : inAllItems.size());
            if (inAllItems != null) {
                for (IGItem item : inAllItems) {
                    writeD(item.getObjectId());
                }
            }
        } else {
            FastList<Integer> salesRankingItems = InGameShopEn.getInstance().getTopSales(subCategory, category);
            writeD(salesRanking);
            writeD(nrList);
            writeD((InGameShopEn.getInstance().getMaxList(subCategory, category) + 1) * 9);
            writeH(salesRankingItems.size());
            for (int id : salesRankingItems) {
                writeD(id);
            }

            FastList.recycle(salesRankingItems);
        }
    }
}
