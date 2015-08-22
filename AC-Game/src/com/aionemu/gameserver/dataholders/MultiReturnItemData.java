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


import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.aionemu.gameserver.model.templates.teleport.ScrollItem;
import com.aionemu.gameserver.model.templates.teleport.ScrollItemLocationList;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author GiGatR00n
 */
@XmlRootElement(name = "item_multi_returns")
@XmlAccessorType(XmlAccessType.FIELD)
public class MultiReturnItemData {

	@XmlElement(name = "item")
	private List<ScrollItem> ItemList;
	
	private TIntObjectHashMap<List<ScrollItemLocationList>> ItemLocationList = new TIntObjectHashMap<>();

	void afterUnmarshal(Unmarshaller u, Object parent) {
		ItemLocationList.clear();
		for (ScrollItem template : ItemList)
			ItemLocationList.put(template.getId(), template.getLocationList());
	}

	public int size() {
		return ItemLocationList.size();
	}

	public ScrollItem getScrollItembyId(int id) {
		
		for (ScrollItem template : ItemList) {
			if (template.getId() == id) {
				return template;
			}
		}
		return null;
	}	
	
	public List<ScrollItem> getScrollItems() {
		return ItemList;
	}
}