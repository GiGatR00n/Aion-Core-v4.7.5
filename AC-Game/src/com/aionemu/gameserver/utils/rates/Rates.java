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
package com.aionemu.gameserver.utils.rates;

/**
 * @author ATracer
 * @author GiGatR00n v4.7.5.x
 */
public abstract class Rates {

    public abstract float getGroupXpRate();

    public abstract float getXpRate();

    public abstract float getApNpcRate();

    public abstract float getApPlayerGainRate();
    
    public abstract float getGpNpcRate();

    public abstract float getXpPlayerGainRate();

    public abstract float getApPlayerLossRate();

    public abstract float getGatheringXPRate();

    public abstract int getGatheringCountRate();

    public abstract float getCraftingXPRate();

    public abstract float getDropRate();

    public abstract float getQuestXpRate();

    public abstract float getQuestKinahRate();

    public abstract float getQuestApRate();
	
	public abstract float getQuestGpRate();

    public abstract float getDpNpcRate();

    public abstract float getDpPlayerRate();

    public abstract int getCraftCritRate();

    public abstract int getComboCritRate();

    public abstract float getDisciplineRewardRate();

    public abstract float getChaosRewardRate();

    public abstract float getHarmonyRewardRate();

    public abstract float getGloryRewardRate();

    public abstract float getSellLimitRate();

    public abstract float getKamarRewardRate();
    
    public abstract float getIdgelDomeBoxRewardRate();

    /**
     * @param membership
     * @return Rates
     */
    public static Rates getRatesFor(byte membership) {
        switch (membership) {
            case 0:
                return new RegularRates();
            case 1:
                return new PremiumRates();
            case 2:
                return new VipRates();
            default:
                return new VipRates();
        }
    }
}
