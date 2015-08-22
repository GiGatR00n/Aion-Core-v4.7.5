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
package com.aionemu.gameserver.model.stats.container;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.stats.calc.Stat2;

/**
 * @author ATracer
 */
public class SummonedObjectGameStats extends NpcGameStats {

    public SummonedObjectGameStats(Npc owner) {
        super(owner);
    }

    @Override
    public Stat2 getStat(StatEnum statEnum, int base) {
        Stat2 stat = super.getStat(statEnum, base);
        if (owner.getMaster() == null) {
            return stat;
        }
        switch (statEnum) {
            case BOOST_MAGICAL_SKILL:
            case MAGICAL_ATTACK:
            case MAGICAL_ACCURACY:
            case MAGICAL_RESIST:
                stat.setBonusRate(0.2f);
                return owner.getMaster().getGameStats().getItemStatBoost(statEnum, stat);
            case PHYSICAL_ACCURACY:
                stat.setBonusRate(0.2f);
                owner.getMaster().getGameStats().getItemStatBoost(StatEnum.MAIN_HAND_ACCURACY, stat);
                return owner.getMaster().getGameStats().getItemStatBoost(statEnum, stat);
            case PHYSICAL_ATTACK:
                stat.setBonusRate(0.2f);
                owner.getMaster().getGameStats().getItemStatBoost(StatEnum.MAIN_HAND_POWER, stat);
                return owner.getMaster().getGameStats().getItemStatBoost(statEnum, stat);
            default:
                break;

        }
        return stat;
    }

    @Override
    public Stat2 getMainHandMAttack() {
        int power = owner.getObjectTemplate().getStatsTemplate().getPower();
        return getStat(StatEnum.MAGICAL_ATTACK, power);
    }
}
