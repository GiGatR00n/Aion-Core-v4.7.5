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
package com.aionemu.gameserver.model.items;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.stats.calc.functions.StatFunction;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

import java.util.List;

/**
 * @author ATracer
 */
public class ManaStone extends ItemStone {

    private List<StatFunction> modifiers;

    public ManaStone(int itemObjId, int itemId, int slot, PersistentState persistentState) {
        super(itemObjId, itemId, slot, persistentState);

        ItemTemplate stoneTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
        if (stoneTemplate != null && stoneTemplate.getModifiers() != null) {
            this.modifiers = stoneTemplate.getModifiers();
        }
    }

    /**
     * @return modifiers
     */
    public List<StatFunction> getModifiers() {
        return modifiers;
    }

    public StatFunction getFirstModifier() {
        return (modifiers != null && modifiers.size() > 0) ? modifiers.get(0) : null;
    }

    public boolean isBasic() {
        return !isAncient();
    }

    public boolean isAncient() {
        return getItemId() >= 167020000 && getItemId() <= 167020095;
    }
}
