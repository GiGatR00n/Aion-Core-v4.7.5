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
package com.aionemu.commons.utils.concurrent;

import com.aionemu.commons.network.util.ThreadUncaughtExceptionHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author -Nemesiss-
 */
public class PriorityThreadFactory implements ThreadFactory {

    /**
     * Priority of new threads
     */
    private int prio;
    /**
     * Thread group name
     */
    private String name;

    /*
     * Default pool for the thread group, can be null for default
     */
    private ExecutorService threadPool;

    /**
     * Number of created threads
     */
    private AtomicInteger threadNumber = new AtomicInteger(1);
    /**
     * ThreadGroup for created threads
     */
    private ThreadGroup group;

    /**
     * Constructor.
     *
     * @param name
     * @param prio
     */
    public PriorityThreadFactory(final String name, final int prio) {
        this.prio = prio;
        this.name = name;
        group = new ThreadGroup(this.name);
    }

    public PriorityThreadFactory(final String name, ExecutorService defaultPool) {
        this(name, Thread.NORM_PRIORITY);
        setDefaultPool(defaultPool);
    }

    protected void setDefaultPool(ExecutorService pool) {
        threadPool = pool;
    }

    protected ExecutorService getDefaultPool() {
        return threadPool;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Thread newThread(final Runnable r) {
        Thread t = new Thread(group, r);
        t.setName(name + "-" + threadNumber.getAndIncrement());
        t.setPriority(prio);
        t.setUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler());
        return t;
    }
}
