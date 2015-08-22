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
package zone;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.ZoneName;
import com.aionemu.gameserver.world.zone.handler.ZoneHandler;
import com.aionemu.gameserver.world.zone.handler.ZoneNameAnnotation;

/**
 * @author MrPoke
 */
@ZoneNameAnnotation("ASMODIANS_BASE_400010000 ELYOS_BASE_400010000")
public class AbyssBaseShield implements ZoneHandler {

    @Override
    public void onEnterZone(Creature creature, ZoneInstance zone) {
        Creature actingCreature = creature.getActingCreature();
        if (actingCreature instanceof Player && !((Player) actingCreature).isGM()) {
            ZoneName currZone = zone.getZoneTemplate().getName();
            if (currZone == ZoneName.get("ASMODIANS_BASE_400010000")) {
                if (((Player) actingCreature).getRace() == Race.ELYOS) {
                    creature.getController().die();
                }
            } else if (currZone == ZoneName.get("ELYOS_BASE_400010000")) {
                if (((Player) actingCreature).getRace() == Race.ASMODIANS) {
                    creature.getController().die();
                }
            }
        }
    }

    @Override
    public void onLeaveZone(Creature player, ZoneInstance zone) {
    }
}
