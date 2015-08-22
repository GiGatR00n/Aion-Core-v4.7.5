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
package com.aionemu.gameserver.model.flyring;

import com.aionemu.gameserver.controllers.FlyRingController;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.flyring.FlyRingTemplate;
import com.aionemu.gameserver.model.utils3d.Plane3D;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.SphereKnownList;

/**
 * @author xavier
 */
public class FlyRing extends VisibleObject {

    private FlyRingTemplate template = null;
    private String name = null;
    private Plane3D plane = null;
    private Point3D center = null;
    private Point3D p1 = null;
    private Point3D p2 = null;

    public FlyRing(FlyRingTemplate template, int instanceId) {
        super(IDFactory.getInstance().nextId(), new FlyRingController(), null, null, World.getInstance().createPosition(
                template.getMap(), template.getCenter().getX(), template.getCenter().getY(), template.getCenter().getZ(),
                (byte) 0, instanceId));

        ((FlyRingController) getController()).setOwner(this);
        this.template = template;
        this.name = (template.getName() == null) ? "FLY_RING" : template.getName();
        this.center = new Point3D(template.getCenter().getX(), template.getCenter().getY(), template.getCenter().getZ());
        this.p1 = new Point3D(template.getP1().getX(), template.getP1().getY(), template.getP1().getZ());
        this.p2 = new Point3D(template.getP2().getX(), template.getP2().getY(), template.getP2().getZ());
        this.plane = new Plane3D(center, p1, p2);
        setKnownlist(new SphereKnownList(this, template.getRadius() * 2));
    }

    public Plane3D getPlane() {
        return plane;
    }

    public FlyRingTemplate getTemplate() {
        return template;
    }

    @Override
    public String getName() {
        return name;
    }

    public void spawn() {
        World.getInstance().spawn(this);
    }
}
