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

import com.aionemu.gameserver.configs.ingameshop.InGameShopProperty;
import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.model.templates.ingameshop.IGCategory;
import com.aionemu.gameserver.model.templates.ingameshop.IGSubCategory;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author xTz
 */
public class SM_IN_GAME_SHOP_CATEGORY_LIST extends AionServerPacket {

    private int type;
    private int categoryId;
    private InGameShopProperty ing;

    public SM_IN_GAME_SHOP_CATEGORY_LIST(int type, int category) {
        this.type = type;
        this.categoryId = category;
        ing = InGameShopEn.getInstance().getIGSProperty();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(type);
        switch (type) {
            case 0:
                writeH(ing.size()); // size
                for (IGCategory category : ing.getCategories()) {
                    writeD(category.getId()); // categry Id
                    writeS(category.getName()); // category Name
                }
                break;
            case 2:
                if (categoryId < ing.size()) {
                    IGCategory iGCategory = ing.getCategories().get(categoryId);
                    writeH(iGCategory.getSubCategories().size()); // size
                    for (IGSubCategory subCategory : iGCategory.getSubCategories()) {
                        writeD(subCategory.getId()); // sub category Id
                        writeS(subCategory.getName()); // sub category Name
                    }
                }
                break;
        }
    }
}
