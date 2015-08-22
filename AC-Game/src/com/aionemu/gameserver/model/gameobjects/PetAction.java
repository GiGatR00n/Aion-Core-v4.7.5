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
package com.aionemu.gameserver.model.gameobjects;

import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author ATracer
 */
public enum PetAction {

    ADOPT(1),
    SURRENDER(2),
    SPAWN(3),
    DISMISS(4),
    TALK_WITH_MERCHANT(6),
    TALK_WITH_MINDER(7),
    FOOD(9),
    RENAME(10),
    MOOD(12),
    UNKNOWN(255);
    private static TIntObjectHashMap<PetAction> petActions;

    static {
        petActions = new TIntObjectHashMap<PetAction>();
        for (PetAction action : values()) {
            petActions.put(action.getActionId(), action);
        }
    }

    private int actionId;

    private PetAction(int actionId) {
        this.actionId = actionId;
    }

    public int getActionId() {
        return actionId;
    }

    public static PetAction getActionById(int actionId) {
        PetAction action = petActions.get(actionId);
        return action != null ? action : UNKNOWN;
    }
}
