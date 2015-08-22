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
package com.aionemu.gameserver.model.templates.housing;

import com.aionemu.gameserver.model.templates.item.ItemQuality;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "house_part")
public class HousePart {

    @XmlAttribute(name = "building_tags", required = true)
    private List<String> buildingTags;
    @XmlAttribute(required = true)
    protected PartType type;
    @XmlAttribute(required = true)
    protected ItemQuality quality;
    @XmlAttribute
    protected String name;
    @XmlAttribute(required = true)
    protected int id;
    @XmlTransient
    protected Set<String> tagsSet = new HashSet<String>(1);

    void afterUnmarshal(Unmarshaller u, Object parent) {
        if (buildingTags == null) {
            return;
        }

        for (String tag : buildingTags) {
            tagsSet.add(tag);
        }

        buildingTags.clear();
        buildingTags = null;
    }

    public PartType getType() {
        return type;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Set<String> getTags() {
        return tagsSet;
    }

    public boolean isForBuilding(Building building) {
        return tagsSet.contains(building.getPartsMatchTag());
    }
}
