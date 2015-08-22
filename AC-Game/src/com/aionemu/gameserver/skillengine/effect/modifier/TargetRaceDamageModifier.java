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
package com.aionemu.gameserver.skillengine.effect.modifier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer modified by Sippolo, kecimis
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetRaceDamageModifier")
public class TargetRaceDamageModifier extends ActionModifier {

    @XmlAttribute(name = "race")
    private Race skillTargetRace;

    @Override
    public int analyze(Effect effect) {
        Creature effected = effect.getEffected();

        int newValue = (value + effect.getSkillLevel() * delta);
        if (effected instanceof Player) {

            Player player = (Player) effected;
            switch (skillTargetRace) {
                case ASMODIANS:
                    if (player.getRace() == Race.ASMODIANS) {
                        return newValue;
                    }
                    break;
                case ELYOS:
                    if (player.getRace() == Race.ELYOS) {
                        return newValue;
                    }
            }
        } else if (effected instanceof Npc) {
            Npc npc = (Npc) effected;
            if (npc.getObjectTemplate().getRace().toString().equals(skillTargetRace.toString())) {
                return newValue;
            } else {
                return 0;
            }
        }

        return 0;
    }

    @Override
    public boolean check(Effect effect) {
        Creature effected = effect.getEffected();
        if (effected instanceof Player) {

            Player player = (Player) effected;
            Race race = player.getRace();
            return (race == Race.ASMODIANS && skillTargetRace == Race.ASMODIANS)
                    || (race == Race.ELYOS && skillTargetRace == Race.ELYOS);
        } else if (effected instanceof Npc) {
            Npc npc = (Npc) effected;

            Race race = npc.getObjectTemplate().getRace();
            if (race == null) {
                return false;
            }

            return race.toString().equals(skillTargetRace.toString());
        }

        return false;
    }
}
