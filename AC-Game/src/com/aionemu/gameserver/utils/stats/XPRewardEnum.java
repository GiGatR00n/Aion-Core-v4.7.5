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
package com.aionemu.gameserver.utils.stats;

import java.util.NoSuchElementException;

/**
 * @author ATracer
 */
public enum XPRewardEnum {

    MINUS_11(-11, 0),
    MINUS_10(-10, 1),
    MINUS_9(-9, 10),
    MINUS_8(-8, 20),
    MINUS_7(-7, 30),
    MINUS_6(-6, 40),
    MINUS_5(-5, 50),
    MINUS_4(-4, 70),
    MINUS_3(-3, 90),
    MINUS_2(-2, 100),
    MINUS_1(-1, 100),
    ZERO(0, 100),
    PLUS_1(1, 105),
    PLUS_2(2, 110),
    PLUS_3(3, 115),
    PLUS_4(4, 120);
    private int xpRewardPercent;
    private int levelDifference;

    private XPRewardEnum(int levelDifference, int xpRewardPercent) {
        this.levelDifference = levelDifference;
        this.xpRewardPercent = xpRewardPercent;
    }

    public int rewardPercent() {
        return xpRewardPercent;
    }

    /**
     * @param levelDifference between two objects
     * @return XP reward percentage
     */
    public static int xpRewardFrom(int levelDifference) {
        if (levelDifference < MINUS_11.levelDifference) {
            return MINUS_11.xpRewardPercent;
        }
        if (levelDifference > PLUS_4.levelDifference) {
            return PLUS_4.xpRewardPercent;
        }

        for (XPRewardEnum xpReward : values()) {
            if (xpReward.levelDifference == levelDifference) {
                return xpReward.xpRewardPercent;
            }
        }

        throw new NoSuchElementException("XP reward for such level difference was not found");
    }
}
