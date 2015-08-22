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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;

/**
 * @author MrPoke
 */
public abstract class ItemUseObserver extends ActionObserver {

    /**
     * @param observerType
     */
    public ItemUseObserver() {
        super(ObserverType.ALL);
    }

    @Override
    public final void attack(Creature creature) {
        abort();
    }

    @Override
    public final void attacked(Creature creature) {
        abort();
    }

    @Override
    public final void died(Creature creature) {
        abort();
    }

    @Override
    public final void dotattacked(Creature creature, Effect dotEffect) {
        abort();
    }

    @Override
    public final void equip(Item item, Player owner) {
        abort();
    }

    @Override
    public final void moved() {
        abort();
    }

    @Override
    public final void skilluse(Skill skill) {
        abort();
    }

    public abstract void abort();
}
