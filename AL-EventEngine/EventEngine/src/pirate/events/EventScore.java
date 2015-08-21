/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package pirate.events;

/**
 *
 * @author f14shm4n
 */
public class EventScore implements Comparable<EventScore> {

    public final int PlayerObjectId;
    public boolean isWinner;
    private int points;
    private int kills;
    private int death;
    private int wins;
    private int loses;
    private int killStreak;
    private int endKillStreak;

    public EventScore(int id) {
        this.PlayerObjectId = id;
        this.isWinner = false;
    }

    public void incKills() {
        this.kills += 1;
        this.killStreak += 1;
    }

    public void incDeath() {
        this.death += 1;
        this.endKillStreak = killStreak;
        this.killStreak = 0;
    }

    public void incWin() {
        this.wins += 1;
    }

    public void incLose() {
        this.loses += 1;
    }

    public void incPoints(int points) {
        this.points += points;
        if (this.points < 0) {
            this.points = 0;
        }
    }

    public int getEndStreak() {
        return this.endKillStreak;
    }

    public int getStreak() {
        return this.killStreak;
    }

    public void setStreak(int streak) {
        this.killStreak = streak;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    @Override
    public int compareTo(EventScore o) {
        Integer p1 = points;
        Integer p2 = o.points;
        return p2.compareTo(p1);
    }
}
