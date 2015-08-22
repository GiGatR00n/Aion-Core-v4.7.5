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
package com.aionemu.gameserver.model.templates.flyring;

import com.aionemu.gameserver.model.utils3d.Point3D;

import javax.xml.bind.annotation.*;

/**
 * @author M@xx
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlyRing")
public class FlyRingTemplate {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "map")
    protected int map;
    @XmlAttribute(name = "radius")
    protected float radius;
    @XmlElement(name = "center")
    protected FlyRingPoint center;
    @XmlElement(name = "p1")
    protected FlyRingPoint p1;
    @XmlElement(name = "p2")
    protected FlyRingPoint p2;

    public String getName() {
        return name;
    }

    public int getMap() {
        return map;
    }

    public float getRadius() {
        return radius;
    }

    public FlyRingPoint getCenter() {
        return center;
    }

    public FlyRingPoint getP1() {
        return p1;
    }

    public FlyRingPoint getP2() {
        return p2;
    }

    public FlyRingTemplate() {
    }

    ;

    public FlyRingTemplate(String name, int mapId, Point3D center, Point3D p1, Point3D p2, int radius) {
        this.name = name;
        this.map = mapId;
        this.radius = radius;
        this.center = new FlyRingPoint(center);
        this.p1 = new FlyRingPoint(p1);
        this.p2 = new FlyRingPoint(p2);
    }
}
