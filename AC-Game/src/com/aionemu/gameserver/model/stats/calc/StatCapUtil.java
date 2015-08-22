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
package com.aionemu.gameserver.model.stats.calc;

import com.aionemu.gameserver.model.stats.container.StatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ATracer
 */
public class StatCapUtil {

    protected static final Logger log = LoggerFactory.getLogger(StatCapUtil.class);
    static final int LOWER_CAP = Short.MIN_VALUE; // -32767
    static final int UPPER_CAP = Short.MAX_VALUE; // 32767

    static class StatLimits {

        public final int lowerCap;
        public final int upperCap;

        public StatLimits() {
            this.lowerCap = LOWER_CAP;
            this.upperCap = UPPER_CAP;
        }

        public StatLimits(int lowerCap, int upperCap) {
            this.lowerCap = lowerCap;
            this.upperCap = upperCap;
        }
    }

    static HashMap<StatEnum, Integer> minValues = new HashMap<StatEnum, Integer>();
    static HashMap<StatEnum, Integer> maxValues = new HashMap<StatEnum, Integer>();
    static HashMap<StatEnum, StatLimits> limits = new HashMap<StatEnum, StatLimits>();

    static {
        for (StatEnum stat : StatEnum.values()) {
            minValues.put(stat, getLowerCap(stat));
            maxValues.put(stat, getUpperCap(stat));
            limits.put(stat, new StatLimits(getLowerCap(stat), getUpperCap(stat)));
        }
    }

    public static void calculateBaseValue(Stat2 stat, byte isPlayer) {

        int lowerCap = getLowerCap(stat.getStat());
        int upperCap = getUpperCap(stat.getStat());

        if (stat.getStat() == StatEnum.ATTACK_SPEED) {
            int base = stat.getBase() / 2;
            if (stat.getBonus() > 0 && base < stat.getBonus()) {
                stat.setBonus(base);
            } else if (stat.getBonus() < 0 && base < -stat.getBonus()) {
                stat.setBonus(-base);
            }
        } else if (stat.getStat() == StatEnum.SPEED || stat.getStat() == StatEnum.FLY_SPEED) {
            if (isPlayer == 2) {
                upperCap = Integer.MAX_VALUE;
            }
        }

        calculate(stat, lowerCap, upperCap);
        if (isPlayer != 1) { // not admins, and not npcs
            int newValue = stat.getCurrent();
            if (newValue < LOWER_CAP) {
                minValues.put(stat.getStat(), newValue);
            }
            if (newValue > UPPER_CAP) {
                maxValues.put(stat.getStat(), newValue);
            }
        }
    }

    public static int getMinValue(StatEnum stat) {
        return minValues.get(stat);
    }

    public static int getMaxValue(StatEnum stat) {
        return maxValues.get(stat);
    }

    public static int getLowerCap(StatEnum stat) {
        if (limits.containsKey(stat)) {
            return limits.get(stat).lowerCap;
        }
        int value = LOWER_CAP;
        switch (stat) {
            case MAIN_HAND_POWER:
            case MAIN_HAND_ACCURACY:
            case MAIN_HAND_CRITICAL:
            case OFF_HAND_POWER:
            case OFF_HAND_ACCURACY:
            case OFF_HAND_CRITICAL:
            case MAGICAL_RESIST:
            case PHYSICAL_CRITICAL_RESIST:
            case EVASION:
            case PHYSICAL_DEFENSE:
            case PHYSICAL_ACCURACY:
            case MAGICAL_ACCURACY:
            case SPEED:
            case FLY_SPEED:
            case MAXHP:
            case MAXMP:
                value = 0;
                break;
            default:
                break;
        }
        return value;
    }

    public static int getUpperCap(StatEnum stat) {
        if (limits.containsKey(stat)) {
            return limits.get(stat).upperCap;
        }
        int value = UPPER_CAP;
        switch (stat) {
            case SPEED:
                value = 12000;
                break;
            case FLY_SPEED:
                value = 16000;
                break;
            case PVP_DEFEND_RATIO:
                value = 900;
            case MAXHP:
            case MAXMP:
            case HEAL_BOOST:
            case HEAL_SKILL_BOOST:
            case BOOST_DURATION_BUFF:
                value = Integer.MAX_VALUE;
                break;
            default:
                break;
        }
        return value;
    }

    private static void calculate(Stat2 stat2, int lowerCap, int upperCap) {
        if (stat2.getCurrent() > upperCap) {
            stat2.setBonus(upperCap - stat2.getBase());
        } else if (stat2.getCurrent() < lowerCap) {
            stat2.setBonus(lowerCap - stat2.getBase());
        }
    }

    public static void dumpWrongStats(String ownerInfo, Stat2... stats) {
        List<Stat2> wrongStats = null;
        for (Stat2 stat : stats) {
            Stat2 wrongStat = null;
            if (stat.getCurrent() < getLowerCap(stat.getStat())) {
                wrongStat = stat;
            }
            if (stat.getCurrent() > getUpperCap(stat.getStat())) {
                wrongStat = stat;
            }
            if (wrongStat != null) {
                if (wrongStats == null) {
                    wrongStats = new ArrayList<Stat2>();
                }
                wrongStats.add(wrongStat);
            }
        }
        if (wrongStats == null) {
            return;
        }
        StringBuilder msg = new StringBuilder();
        msg.append(ownerInfo);
        msg.append('\n');
        for (Stat2 stat : wrongStats) {
            msg.append(stat.getStat());
            msg.append(": [");
            msg.append(" Min: " + Integer.toString(getMinValue(stat.getStat())));
            msg.append(" Max: " + Integer.toString(getMaxValue(stat.getStat())));
            msg.append(" Base: " + Integer.toString(stat.getBase()));
            msg.append(" Bonus: " + Integer.toString(stat.getBase()));
            msg.append("]\n");
        }
        log.error(msg.toString());
    }
}
