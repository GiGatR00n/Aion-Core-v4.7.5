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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a simple map implementation for cache usage.<br>
 * <br>
 * Values from the map will be removed after the first garbage collector run if
 * there isn't any strong reference to the value object.
 *
 * @author Luno
 */
class WeakCacheMap<K, V> extends AbstractCacheMap<K, V> implements CacheMap<K, V> {

    private static final Logger log = LoggerFactory.getLogger(WeakCacheMap.class);

    /**
     * This class is a {@link WeakReference} with additional responsibility of
     * holding key object
     *
     * @author Luno
     */
    private class Entry extends WeakReference<V> {

        private K key;

        Entry(K key, V referent, ReferenceQueue<? super V> q) {
            super(referent, q);
            this.key = key;
        }

        K getKey() {
            return key;
        }
    }

    WeakCacheMap(String cacheName, String valueName) {
        super(cacheName, valueName, log);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected synchronized void cleanQueue() {
        Entry en = null;
        while ((en = (Entry) refQueue.poll()) != null) {
            K key = en.getKey();
            if (log.isDebugEnabled()) {
                log.debug(cacheName + " : cleaned up " + valueName + " for key: " + key);
            }
            cacheMap.remove(key);
        }
    }

    @Override
    protected Reference<V> newReference(K key, V value, ReferenceQueue<V> vReferenceQueue) {
        return new Entry(key, value, vReferenceQueue);
    }
}
