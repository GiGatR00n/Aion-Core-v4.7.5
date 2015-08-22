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

import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.item.ItemQuality;

import javax.xml.bind.annotation.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractHouseObject")
@XmlSeeAlso({PlaceableHouseObject.class})
public abstract class AbstractHouseObject extends VisibleObjectTemplate {

    @XmlAttribute(name = "talking_distance", required = true)
    protected float talkingDistance;
    @XmlAttribute(required = true)
    protected ItemQuality quality;
    @XmlAttribute(required = true)
    protected HousingCategory category;
    @XmlAttribute(name = "name_id", required = true)
    protected int nameId;
    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute(name = "can_dye")
    protected boolean canDye;

    @Override
    public int getTemplateId() {
        return id;
    }

    public float getTalkingDistance() {
        return talkingDistance;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public HousingCategory getCategory() {
        return category;
    }

    public boolean getCanDye() {
        return canDye;
    }

    @Override
    public int getNameId() {
        return nameId;
    }

    @Override
    public String getName() {
        return null;
    }
}
