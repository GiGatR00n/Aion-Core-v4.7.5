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
package com.aionemu.gameserver.world.container;

import com.aionemu.gameserver.model.team.legion.Legion;
import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
import javolution.util.FastMap;

import java.util.Iterator;
import java.util.Map;

/**
 * Container for storing Legions by legionId and name.
 *
 * @author Simple
 */
public class LegionContainer implements Iterable<Legion> {

    /**
     * Map<LegionId, Legion>
     */
    private final Map<Integer, Legion> legionsById = new FastMap<Integer, Legion>().shared();
    /**
     * Map<LegionName, Legion>
     */
    private final Map<String, Legion> legionsByName = new FastMap<String, Legion>().shared();

    /**
     * Add Legion to this Container.
     *
     * @param legion
     */
    public void add(Legion legion) {
        if (legion == null || legion.getLegionName() == null) {
            return;
        }

        if (legionsById.put(legion.getLegionId(), legion) != null) {
            throw new DuplicateAionObjectException();
        }
        if (legionsByName.put(legion.getLegionName().toLowerCase(), legion) != null) {
            throw new DuplicateAionObjectException();
        }
    }

    /**
     * Remove Legion from this Container.
     *
     * @param legion
     */
    public void remove(Legion legion) {
        legionsById.remove(legion.getLegionId());
        legionsByName.remove(legion.getLegionName().toLowerCase());
    }

    /**
     * Get Legion object by objectId.
     *
     * @param legionId - legionId of legion.
     * @return Legion with given ojectId or null if Legion with given legionId
     * is not logged.
     */
    public Legion get(int legionId) {
        return legionsById.get(legionId);
    }

    /**
     * Get Legion object by name.
     *
     * @param name - name of legion
     * @return Legion with given name or null if Legion with given name is not
     * logged.
     */
    public Legion get(String name) {
        return legionsByName.get(name.toLowerCase());
    }

    /**
     * Returns true if legion is in cached by id
     *
     * @param legionId
     * @return true or false
     */
    public boolean contains(int legionId) {
        return legionsById.containsKey(legionId);
    }

    /**
     * Returns true if legion is in cached by name
     *
     * @param name
     * @return true or false
     */
    public boolean contains(String name) {
        return legionsByName.containsKey(name.toLowerCase());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Legion> iterator() {
        return legionsById.values().iterator();
    }

    public void clear() {
        legionsById.clear();
        legionsByName.clear();
    }
}
