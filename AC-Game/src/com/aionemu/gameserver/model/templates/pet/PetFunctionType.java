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
package com.aionemu.gameserver.model.templates.pet;

/**
 * @author IlBuono, Rolandas
 */
public enum PetFunctionType {

    WAREHOUSE(0, true),
    FOOD(1, 64),
    DOPING(2, 256),
    LOOT(3, 8),
    APPEARANCE(1),
    NONE(4, true),
    // non writable to packets
    BAG(-1),
    WING(-2);
    private short id;
    private boolean isPlayerFunc = false;

    PetFunctionType(int id, boolean isPlayerFunc) {
        this(id);
        this.isPlayerFunc = isPlayerFunc;
    }

    PetFunctionType(int id, int dataBitCount) {
        this(dataBitCount << 5 | id);
        this.isPlayerFunc = true;
    }

    PetFunctionType(int id) {
        this.id = (short) (id & 0xFFFF);
    }

    public int getId() {
        return id;
    }

    public boolean isPlayerFunction() {
        return isPlayerFunc;
    }
}
