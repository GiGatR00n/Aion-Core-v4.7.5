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
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.item.DecomposableItemInfo;
import com.aionemu.gameserver.model.templates.item.ExtractedItemsCollection;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author antness
 */
@XmlRootElement(name = "decomposable_items")
@XmlAccessorType(XmlAccessType.FIELD)
public class DecomposableItemsData {

    @XmlElement(name = "decomposable")
    private List<DecomposableItemInfo> decomposableItemsTemplates;
    private TIntObjectHashMap<List<ExtractedItemsCollection>> decomposableItemsInfo = new TIntObjectHashMap<List<ExtractedItemsCollection>>();

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        decomposableItemsInfo.clear();
        for (DecomposableItemInfo template : decomposableItemsTemplates) {
            decomposableItemsInfo.put(template.getItemId(), template.getItemsCollections());
        }
    }

    public int size() {
        return decomposableItemsInfo.size();
    }

    public List<ExtractedItemsCollection> getInfoByItemId(int itemId) {
        return decomposableItemsInfo.get(itemId);
    }
}
