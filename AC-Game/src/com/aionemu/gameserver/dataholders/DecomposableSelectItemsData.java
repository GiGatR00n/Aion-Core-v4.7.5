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

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.templates.decomposable.DecomposableSelectItem;
import com.aionemu.gameserver.model.templates.decomposable.SelectItems;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@XmlRootElement(name = "decomposable_selectitems")
@XmlAccessorType(XmlAccessType.FIELD)
public class DecomposableSelectItemsData {

    @XmlElement(name = "decomposable_selectitem", required = true)
    protected List<DecomposableSelectItem> selectItems;

    @SuppressWarnings({"unchecked", "rawtypes"})
    private HashMap<Integer, HashMap<PlayerClass, SelectItems>> selectItemData = new HashMap();

    /**
     * @param u
     * @param parent
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (Iterator i$ = this.selectItems.iterator(); i$.hasNext(); ) {
            DecomposableSelectItem item;
            item = (DecomposableSelectItem) i$.next();
            if (item.getItems() != null) {
                if (!this.selectItemData.containsKey(Integer.valueOf(item.getItemId()))) {
                    this.selectItemData.put(Integer.valueOf(item.getItemId()), new HashMap());
                }
                for (SelectItems its : item.getItems()) {
                    ((HashMap) this.selectItemData.get(Integer.valueOf(item.getItemId()))).put(its.getPlayerClass(), its);
                }
            }
        }

        this.selectItems.clear();
        this.selectItems = null;
    }

    @SuppressWarnings("rawtypes")
    public SelectItems getSelectItem(PlayerClass playerClass, int itemid) {
        if (this.selectItemData.containsKey(Integer.valueOf(itemid))) {
            if (((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).containsKey(playerClass)) {
                return (SelectItems) ((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).get(playerClass);
            }
            if (((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).containsKey(PlayerClass.ALL)) {
                return (SelectItems) ((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).get(PlayerClass.ALL);
            }
            return null;
        }
        return null;
    }

    public int size() {
        return selectItemData.size();
    }

}
