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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.stats.container.StatEnum;

/**
 * @author ATracer
 */
public abstract class Stat2 {

    float bonusRate = 1f;
    int base;
    int bonus;
    private final Creature owner;
    protected final StatEnum stat;

    public Stat2(StatEnum stat, int base, Creature owner) {
        this(stat, base, owner, 1);
    }

    public Stat2(StatEnum stat, int base, Creature owner, float bonusRate) {
        this.stat = stat;
        this.base = base;
        this.owner = owner;
        this.bonusRate = bonusRate;
    }

    public final StatEnum getStat() {
        return stat;
    }

    public final int getBase() {
        return base;
    }

    public final void setBase(int base) {
        this.base = base;
    }

    public abstract void addToBase(int base);

    public final int getBonus() {
        return bonus;
    }

    public final int getCurrent() {
        return this.base + this.bonus;

    }

    public final void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public final float getBonusRate() {
        return bonusRate;
    }

    public final void setBonusRate(float bonusRate) {
        this.bonusRate = bonusRate;
    }

    public abstract void addToBonus(int bonus);

    public abstract float calculatePercent(int delta);

    public final Creature getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return "[" + stat.name() + " base=" + base + ", bonus=" + bonus + "]";
    }
}
