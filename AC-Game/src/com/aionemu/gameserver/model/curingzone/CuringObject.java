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
package com.aionemu.gameserver.model.curingzone;

import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.curingzones.CuringTemplate;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.NpcKnownList;

/**
 * @author xTz
 */
public class CuringObject extends VisibleObject {

    private CuringTemplate template;
    private float range;

    public CuringObject(CuringTemplate template, int instanceId) {
        super(IDFactory.getInstance().nextId(), new VisibleObjectController<CuringObject>() {
        }, null, null, World.getInstance().
                createPosition(template.getMapId(), template.getX(), template.getY(), template.getZ(), (byte) 0, instanceId));
        this.template = template;
        this.range = template.getRange();
        setKnownlist(new NpcKnownList(this));
    }

    public CuringTemplate getTemplate() {
        return template;
    }

    @Override
    public String getName() {
        return "";
    }

    public float getRange() {
        return range;
    }

    public void spawn() {
        World w = World.getInstance();
        w.storeObject(this);
        w.spawn(this);
    }
}
