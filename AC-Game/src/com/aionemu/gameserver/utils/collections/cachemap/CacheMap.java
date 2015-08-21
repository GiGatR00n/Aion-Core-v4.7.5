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
package com.aionemu.gameserver.utils.collections.cachemap;

/**
 * This interface represents a Map structure for cache usage.
 *
 * @author Luno
 */
public interface CacheMap<K, V> {

    /**
     * Adds a pair <key,value> to cache map.<br>
     * <br>
     * <font color='red'><b>NOTICE:</b> </font> if there is already a value with
     * given id in the map, {@link IllegalArgumentException} will be thrown.
     *
     * @param key
     * @param value
     */
    public void put(K key, V value);

    /**
     * Returns cached value correlated to given key.
     *
     * @param key
     * @return V
     */
    public V get(K key);

    /**
     * Checks whether this map contains a value related to given key.
     *
     * @param key
     * @return true or false
     */
    public boolean contains(K key);

    /**
     * Removes an entry from the map, that has given key.
     *
     * @param key
     */
    public void remove(K key);
}
