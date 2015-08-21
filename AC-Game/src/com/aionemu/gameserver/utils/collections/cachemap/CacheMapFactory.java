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

import com.aionemu.gameserver.configs.main.CacheConfig;

/**
 * @author Luno
 */
public class CacheMapFactory {

    /**
     * Returns new instance of either {@link WeakCacheMap} or
     * {@link SoftCacheMap} depending on {@link CacheConfig#SOFT_CACHE_MAP}
     * setting.
     *
     * @param <K>       - Type of keys
     * @param <V>       - Type of values
     * @param cacheName - The name for this cache map
     * @param valueName - Mnemonic name for values stored in the cache
     * @return CacheMap<K, V>
     */
    public static <K, V> CacheMap<K, V> createCacheMap(String cacheName, String valueName) {
        if (CacheConfig.SOFT_CACHE_MAP) {
            return createSoftCacheMap(cacheName, valueName);
        } else {
            return createWeakCacheMap(cacheName, valueName);
        }
    }

    /**
     * Creates and returns an instance of {@link SoftCacheMap}
     *
     * @param <K>       - Type of keys
     * @param <V>       - Type of values
     * @param cacheName - The name for this cache map
     * @param valueName - Mnemonic name for values stored in the cache
     * @return CacheMap<K, V>
     */
    public static <K, V> CacheMap<K, V> createSoftCacheMap(String cacheName, String valueName) {
        return new SoftCacheMap<K, V>(cacheName, valueName);
    }

    /**
     * Creates and returns an instance of {@link WeakCacheMap}
     *
     * @param <K>       - Type of keys
     * @param <V>       - Type of values
     * @param cacheName - The name for this cache map
     * @param valueName - Mnemonic name for values stored in the cache
     * @return CacheMap<K, V>
     */
    public static <K, V> CacheMap<K, V> createWeakCacheMap(String cacheName, String valueName) {
        return new WeakCacheMap<K, V>(cacheName, valueName);
    }
}
