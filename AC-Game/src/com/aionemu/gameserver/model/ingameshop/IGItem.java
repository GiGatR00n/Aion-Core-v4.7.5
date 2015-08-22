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
package com.aionemu.gameserver.model.ingameshop;

/**
 * @author xTz
 */
public class IGItem {

    private int objectId;
    private int itemId;
    private long itemCount;
    private long itemPrice;
    private byte category;
    private byte subCategory;
    private int list;
    private int salesRanking;
    private byte itemType;
    private byte gift;
    private String titleDescription;
    private String itemDescription;

    public IGItem(int objectId, int itemId, long itemCount, long itemPrice, byte category, byte subCategory, int list, int salesRanking,
                  byte itemType, byte gift, String titleDescription, String itemDescription) {
        this.objectId = objectId;
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
        this.category = category;
        this.subCategory = subCategory;
        this.list = list;
        this.salesRanking = salesRanking;
        this.itemType = itemType;
        this.gift = gift;
        this.titleDescription = titleDescription;
        this.itemDescription = itemDescription;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getItemId() {
        return itemId;
    }

    public long getItemCount() {
        return itemCount;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public byte getCategory() {
        return category;
    }

    public byte getSubCategory() {
        return subCategory;
    }

    public int getList() {
        return list;
    }

    public int getSalesRanking() {
        return salesRanking;
    }

    public byte getItemType() {
        return itemType;
    }

    public byte getGift() {
        return gift;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getTitleDescription() {
        return titleDescription;
    }

    public void increaseSales() {
        salesRanking++;
    }
}
