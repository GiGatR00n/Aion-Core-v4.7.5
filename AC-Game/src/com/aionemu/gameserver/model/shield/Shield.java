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
package com.aionemu.gameserver.model.shield;

import com.aionemu.gameserver.controllers.ShieldController;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.shield.ShieldTemplate;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldPosition;
import com.aionemu.gameserver.world.knownlist.SphereKnownList;

/**
 * @author Wakizashi
 */
public class Shield extends VisibleObject {

    private ShieldTemplate template = null;
    private String name = null;
    private int id = 0;

    public Shield(ShieldTemplate template) {
        super(IDFactory.getInstance().nextId(), new ShieldController(), null, null, null);

        ((ShieldController) getController()).setOwner(this);
        this.template = template;
        this.name = (template.getName() == null) ? "SHIELD" : template.getName();
        this.id = template.getId();
        setKnownlist(new SphereKnownList(this, template.getRadius() * 2));
    }

    public ShieldTemplate getTemplate() {
        return template;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void spawn() {
        World w = World.getInstance();
        WorldPosition position = w.createPosition(template.getMap(), template.getCenter().getX(), template.getCenter().getY(), template
                .getCenter().getZ(), (byte) 0, 0);
        this.setPosition(position);
        w.storeObject(this);
        w.spawn(this);
    }
}
