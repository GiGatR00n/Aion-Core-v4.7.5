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
package pirate.events.enums;

/**
 *
 * @author flashman
 */
public enum EventPlayerLevel {

    L_DEFAULT(false, 20, 60),
    L20_30(true, 20, 30),
    L30_40(true, 31, 40),
    L40_50(true, 41, 50),
    L50_55(true, 51, 55),
    L55_60(true, 56, 60),
    L60_65(true, 61, 65);
    private boolean regular;
    private int min;
    private int max;

    private EventPlayerLevel(boolean regular, int min, int max) {
        this.regular = regular;
        this.min = min;
        this.max = max;
    }

    public boolean isRegularLevelGroup() {
        return this.regular;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    /**
     * Returns eventa level for the current level of the player.
     *
     * @param lvl
     * @return If the player is not suitable for participation in eventah, then
     * back L_DEFAULT
     */
    public static EventPlayerLevel getEventLevelByPlayerLevel(byte lvl) {
        for (EventPlayerLevel epl : values()) {
            if (lvl >= epl.getMin() && lvl <= epl.getMax() && epl.isRegularLevelGroup()) {
                return epl;
            }
        }
        return EventPlayerLevel.L_DEFAULT;
    }
}
