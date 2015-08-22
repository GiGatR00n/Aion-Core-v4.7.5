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
package com.aionemu.gameserver.controllers.observer;

/**
 * @author ATracer
 */
public enum ObserverType {

    MOVE(1),
    ATTACK(1 << 1),
    ATTACKED(1 << 2),
    EQUIP(1 << 3),
    UNEQUIP(1 << 4),
    SKILLUSE(1 << 5),
    DEATH(1 << 6),
    DOT_ATTACKED(1 << 7),
    ITEMUSE(1 << 8),
    NPCDIALOGREQUEST(1 << 9),
    ABNORMALSETTED(1 << 10),
    SUMMONRELEASE(1 << 11),
    EQUIP_UNEQUIP(EQUIP.observerMask | UNEQUIP.observerMask),
    ATTACK_DEFEND(ATTACK.observerMask | ATTACKED.observerMask),
    MOVE_OR_DIE(MOVE.observerMask | DEATH.observerMask),
    ALL(MOVE.observerMask | ATTACK.observerMask | ATTACKED.observerMask | SKILLUSE.observerMask | DEATH.observerMask | DOT_ATTACKED.observerMask | ITEMUSE.observerMask | NPCDIALOGREQUEST.observerMask | ABNORMALSETTED.observerMask | SUMMONRELEASE.observerMask);
    private int observerMask;

    private ObserverType(int observerMask) {
        this.observerMask = observerMask;
    }

    public boolean matchesObserver(ObserverType observerType) {
        return (observerType.observerMask & observerMask) == observerType.observerMask;
    }
}
