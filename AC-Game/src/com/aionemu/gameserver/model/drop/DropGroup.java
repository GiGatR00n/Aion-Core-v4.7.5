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
package com.aionemu.gameserver.model.drop;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author MrPoke
 */
public class DropGroup implements DropCalculator {

    protected List<Drop> drop;
    protected Race race = Race.PC_ALL;
    protected Boolean useCategory = true;
    protected String group_name;

    /**
     * @param drop
     * @param race
     * @param useCategory
     * @param group_name
     */
    public DropGroup(List<Drop> drop, Race race, Boolean useCategory, String group_name) {
        this.drop = drop;
        this.race = race;
        this.useCategory = useCategory;
        this.group_name = group_name;
    }

    public List<Drop> getDrop() {
        return this.drop;
    }

    public Race getRace() {
        return race;
    }

    public Boolean isUseCategory() {
        return useCategory;
    }

    /**
     * @return the name
     */
    public String getGroupName() {
        if (group_name == null) {
            return "";
        }
        return group_name;
    }

    @Override
    public int dropCalculator(Set<DropItem> result, int index, float dropModifier, Race race, Collection<Player> groupMembers) {
        if (useCategory) {
            Drop d = drop.get(Rnd.get(0, drop.size() - 1));
            return d.dropCalculator(result, index, dropModifier, race, groupMembers);
        } else {
            for (int i = 0; i < drop.size(); i++) {
                Drop d = drop.get(i);
                index = d.dropCalculator(result, index, dropModifier, race, groupMembers);
            }
        }
        return index;
    }
}
