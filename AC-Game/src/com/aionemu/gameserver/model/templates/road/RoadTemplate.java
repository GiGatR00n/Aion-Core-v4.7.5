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
package com.aionemu.gameserver.model.templates.road;

import com.aionemu.gameserver.model.utils3d.Point3D;

import javax.xml.bind.annotation.*;

/**
 * @author SheppeR
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Road")
public class RoadTemplate {

    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "map")
    protected int map;
    @XmlAttribute(name = "radius")
    protected float radius;
    @XmlElement(name = "center")
    protected RoadPoint center;
    @XmlElement(name = "p1")
    protected RoadPoint p1;
    @XmlElement(name = "p2")
    protected RoadPoint p2;
    @XmlElement(name = "roadexit")
    protected RoadExit roadExit;

    public String getName() {
        return name;
    }

    public int getMap() {
        return map;
    }

    public float getRadius() {
        return radius;
    }

    public RoadPoint getCenter() {
        return center;
    }

    public RoadPoint getP1() {
        return p1;
    }

    public RoadPoint getP2() {
        return p2;
    }

    public RoadExit getRoadExit() {
        return roadExit;
    }

    public RoadTemplate() {
    }

    ;

    public RoadTemplate(String name, int mapId, Point3D center, Point3D p1, Point3D p2) {
        this.name = name;
        this.map = mapId;
        this.radius = 6;
        this.center = new RoadPoint(center);
        this.p1 = new RoadPoint(p1);
        this.p2 = new RoadPoint(p2);
    }
}
