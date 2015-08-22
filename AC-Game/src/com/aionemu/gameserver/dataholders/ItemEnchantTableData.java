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

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemCategory;
import com.aionemu.gameserver.model.templates.item.ItemEnchantTable;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "enchant_tables")
public class ItemEnchantTableData {

	@XmlElement(name = "enchant_table", required = true)
    protected List<ItemEnchantTable> enchantTables;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @XmlTransient
    private TIntObjectHashMap<ItemEnchantTable> enchants = new TIntObjectHashMap();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (ItemEnchantTable it : this.enchantTables) {
            getEnchantMap().put(it.getId(), it);
        }
    }

    private TIntObjectHashMap<ItemEnchantTable> getEnchantMap() {
        return this.enchants;
    }

    public ItemEnchantTable getTableWeapon(ItemCategory cType) {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getType().equalsIgnoreCase(cType.toString())) {
    			return (ItemEnchantTable) it;
    		} 
    		
    	}
    	return null;
    }
    
    public ItemEnchantTable getTableArmor(ArmorType aType, ItemCategory cType) {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getPart() == null)
    			continue;
    		else if (aType == ArmorType.NO_ARMOR) 
    			continue;
            else if (cType == ItemCategory.SHARD)
                continue;
    		if (it.getType().equalsIgnoreCase(aType.toString()) && it.getPart().equalsIgnoreCase(cType.toString())) {
    			return (ItemEnchantTable) it;
    		} 
    		
    	}
    	return null;
    }
    
    public ItemEnchantTable getTablePlume() {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getType() != "PLUME") {
    			continue;
    		}
    		return (ItemEnchantTable) it;
    		
    	}
    	return null;
    }
    
    public ItemEnchantTable getTableAuthorize() {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getType() != "AUTHORIZE") {
    			continue;
    		}
    		return (ItemEnchantTable) it;
    		
    	}
    	return null;
    }

    public int size() {
        return this.enchants.size();
    }
}
