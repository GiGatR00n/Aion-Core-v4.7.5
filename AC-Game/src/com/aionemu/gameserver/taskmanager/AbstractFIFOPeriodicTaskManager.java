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
package com.aionemu.gameserver.taskmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.utils.AEFastSet;
import com.aionemu.commons.utils.concurrent.RunnableStatsManager;

/**
 * @author lord_rex and MrPoke based on l2j-free engines.
 */
public abstract class AbstractFIFOPeriodicTaskManager<T> extends AbstractPeriodicTaskManager {

    protected static final Logger log = LoggerFactory.getLogger(AbstractFIFOPeriodicTaskManager.class);
    private final AEFastSet<T> queue = new AEFastSet<T>();
    private final AEFastSet<T> activeTasks = new AEFastSet<T>();

    public AbstractFIFOPeriodicTaskManager(int period) {
        super(period);
    }

    public final void add(T t) {
        writeLock();
        try {
            queue.add(t);
        } finally {
            writeUnlock();
        }
    }

    @Override
    public final void run() {
        writeLock();
        try {
            activeTasks.addAll(queue);

            queue.clear();
        } finally {
            writeUnlock();
        }

        for (T task; (task = activeTasks.removeFirst()) != null; ) {
            final long begin = System.nanoTime();

            try {
                callTask(task);
            } catch (RuntimeException e) {
                log.warn("", e);
            } finally {
                RunnableStatsManager.handleStats(task.getClass(), getCalledMethodName(), System.nanoTime() - begin);
            }
        }
    }

    protected abstract void callTask(T task);

    protected abstract String getCalledMethodName();
}
