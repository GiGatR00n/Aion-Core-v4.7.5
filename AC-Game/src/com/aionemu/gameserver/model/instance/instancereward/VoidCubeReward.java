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
package com.aionemu.gameserver.model.instance.instancereward;

import com.aionemu.gameserver.model.instance.playerreward.VoidCubePlayerReward;

/**
 * @author Eloann
 */
public class VoidCubeReward extends InstanceReward<VoidCubePlayerReward> {

    private int points;
    private int npcKills;
    private int rank = 7;
    private int scoreAP;
    private int ceramium;
    private int sillus;
    private int favorable;
    private boolean isRewarded = false;

    public VoidCubeReward(Integer mapId, int instanceId) {
        super(mapId, instanceId);
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    public void addNpcKill() {
        npcKills++;
    }

    public int getNpcKills() {
        return npcKills;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int getScoreAP() {
        return scoreAP;
    }

    public void setScoreAP(int ap) {
        this.scoreAP = ap;
    }

    public boolean isRewarded() {
        return isRewarded;
    }

    public void setRewarded() {
        isRewarded = true;
    }

    public int getCeramium() {
        return ceramium;
    }

    public void setCeramium(int ceramium) {
        this.ceramium = ceramium;
    }

    public int getSillus() {
        return sillus;
    }

    public void setSillus(int sillus) {
        this.sillus = sillus;
    }

    public int getFavorable() {
        return favorable;
    }

    public void setFavorable(int favorable) {
        this.favorable = favorable;
    }
}
