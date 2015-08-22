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
package com.aionemu.gameserver.model.instance.playerreward;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.InstanceBuff;

/**
 * @author Eloann
 */
public class EternalBastionWarPlayerReward extends InstancePlayerReward {

    private InstanceBuff boostMorale;
    private int timeBonus;
    private long logoutTime;
    private float timeBonusModifier;

    public EternalBastionWarPlayerReward(Integer object, int timeBonus, byte buffId) {
        super(object);
        super.addPoints(4000);
        this.timeBonus = timeBonus;
        timeBonusModifier = ((float) this.timeBonus / (float) 660000);
        boostMorale = new InstanceBuff(buffId);
    }

    public float getParticipation() {
        return (float) getTimeBonus() / timeBonus;
    }

    public int getScorePoints() {
        return timeBonus + getPoints();
    }

    public int getTimeBonus() {
        return timeBonus > 0 ? timeBonus : 0;
    }

    public void updateLogOutTime() {
        logoutTime = System.currentTimeMillis();
    }

    public void updateBonusTime() {
        int offlineTime = (int) (System.currentTimeMillis() - logoutTime);
        timeBonus -= offlineTime * timeBonusModifier;
    }

    public boolean hasBoostMorale() {
        return boostMorale.hasInstanceBuff();
    }

    public void applyBoostMoraleEffect(Player player) {
        boostMorale.applyEffect(player, 20000);
    }

    public void endBoostMoraleEffect(Player player) {
        boostMorale.endEffect(player);
    }

    public int getRemaningTime() {
        int time = boostMorale.getRemaningTime();
        if (time >= 0 && time < 20) {
            return 20 - time;
        }
        return 0;
    }
}
