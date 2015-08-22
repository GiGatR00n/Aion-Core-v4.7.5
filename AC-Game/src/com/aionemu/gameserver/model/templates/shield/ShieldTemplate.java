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
package com.aionemu.gameserver.model.templates.shield;

import com.aionemu.gameserver.model.utils3d.Point3D;

import javax.xml.bind.annotation.*;

/**
 * @author M@xx, Wakizashi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Shield")
public class ShieldTemplate {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "map")
    protected int map;
    @XmlAttribute(name = "id")
    protected int id;
    @XmlAttribute(name = "radius")
    protected float radius;
    @XmlElement(name = "center")
    protected ShieldPoint center;

    public String getName() {
        return name;
    }

    public int getMap() {
        return map;
    }

    public float getRadius() {
        return radius;
    }

    public ShieldPoint getCenter() {
        return center;
    }

    public int getId() {
        return id;
    }

    public ShieldTemplate() {
    }

    ;

    public ShieldTemplate(String name, int mapId, Point3D center) {
        this.name = name;
        this.map = mapId;
        this.radius = 6;
        this.center = new ShieldPoint(center);
    }
}
