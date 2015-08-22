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

import com.aionemu.gameserver.model.templates.housing.Building;
import com.aionemu.gameserver.model.templates.housing.HousePart;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"houseParts"})
@XmlRootElement(name = "house_parts")
public class HousePartsData {

    @XmlElement(name = "house_part")
    protected List<HousePart> houseParts;
    @XmlTransient
    Map<String, List<HousePart>> partsByTags = new HashMap<String, List<HousePart>>(5);
    @XmlTransient
    Map<Integer, HousePart> partsById = new HashMap<Integer, HousePart>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        if (houseParts == null) {
            return;
        }

        for (HousePart part : houseParts) {
            partsById.put(part.getId(), part);
            Iterator<String> iterator = part.getTags().iterator();
            while (iterator.hasNext()) {
                String tag = iterator.next();
                List<HousePart> parts = partsByTags.get(tag);
                if (parts == null) {
                    parts = new ArrayList<HousePart>();
                    partsByTags.put(tag, parts);
                }
                parts.add(part);
            }
        }

        houseParts.clear();
        houseParts = null;
    }

    public HousePart getPartById(int partId) {
        return partsById.get(partId);
    }

    public List<HousePart> getPartsForBuilding(Building building) {
        return partsByTags.get(building.getPartsMatchTag());
    }

    public int size() {
        return partsById.size();
    }
}
