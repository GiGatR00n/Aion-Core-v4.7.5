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
package com.aionemu.gameserver.world.zone.handler;

import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.controllers.observer.IActor;
import com.aionemu.gameserver.geoEngine.scene.Spatial;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.templates.materials.MaterialSkill;
import com.aionemu.gameserver.model.templates.materials.MaterialTemplate;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import javolution.util.FastMap;

/**
 * @author Rolandas
 */
public class MaterialZoneHandler implements ZoneHandler {

    FastMap<Integer, IActor> observed = new FastMap<Integer, IActor>();
    private MaterialTemplate template;
    private Race ownerRace = Race.NONE;

    public MaterialZoneHandler(Spatial geometry, MaterialTemplate template) {
        this.template = template;
        String name = geometry.getName();
        if (name.indexOf("FIRE_BOX") != -1 || name.indexOf("FIRE_SEMISPHERE") != -1 || name.indexOf("FIREPOT") != -1
                || name.indexOf("FIRE_CYLINDER") != -1 || name.indexOf("FIRE_CONE") != -1 || name.startsWith("BU_H_CENTERHALL")) {
        }
        if (name.startsWith("BU_AB_DARKSP")) {
            ownerRace = Race.ASMODIANS;
        } else if (name.startsWith("BU_AB_LIGHTSP")) {
            ownerRace = Race.ELYOS;
        }
    }

    @Override
    public void onEnterZone(Creature creature, ZoneInstance zone) {
        if (ownerRace == creature.getRace()) {
            return;
        }
        MaterialSkill foundSkill = null;
        for (MaterialSkill skill : template.getSkills()) {
            if (skill.getTarget().isTarget(creature)) {
                foundSkill = skill;
                break;
            }
        }
        if (foundSkill == null) {
            return;
        }
        /*CollisionMaterialActor actor = new CollisionMaterialActor(creature, geometry, template);
         creature.getObserveController().addObserver(actor);
         observed.put(creature.getObjectId(), actor);
         if (actOnEnter)
         actor.act();*/
    }

    @Override
    public void onLeaveZone(Creature creature, ZoneInstance zone) {
        IActor actor = observed.get(creature.getObjectId());
        if (actor != null) {
            creature.getObserveController().removeObserver((ActionObserver) actor);
            observed.remove(creature.getObjectId());
            actor.abort();
        }
    }
}
