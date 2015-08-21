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
package com.aionemu.loginserver.utils;

import com.aionemu.commons.utils.ExitCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.*;

/**
 * @author -Nemesiss-
 */
public class DeadLockDetector extends Thread {

    /**
     * Logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(DeadLockDetector.class);
    /**
     * What should we do on DeadLock
     */
    public static final byte NOTHING = 0;
    /**
     * What should we do on DeadLock
     */
    public static final byte RESTART = 1;
    /**
     * how often check for deadlocks
     */
    private final int sleepTime;
    /**
     * ThreadMXBean
     */
    private final ThreadMXBean tmx;
    /**
     * What should we do on DeadLock
     */
    private final byte doWhenDL;

    /**
     * Create new DeadLockDetector with given values.
     *
     * @param sleepTime
     * @param doWhenDL
     */
    public DeadLockDetector(int sleepTime, byte doWhenDL) {
        super("DeadLockDetector");
        this.sleepTime = sleepTime * 1000;
        this.tmx = ManagementFactory.getThreadMXBean();
        this.doWhenDL = doWhenDL;
    }

    /**
     * Check if there is a DeadLock.
     */
    @Override
    public final void run() {
        boolean deadlock = false;
        while (!deadlock) {
            try {
                long[] ids = tmx.findDeadlockedThreads();

                if (ids != null) {
                    /**
                     * deadlock found :/
                     */
                    deadlock = true;
                    ThreadInfo[] tis = tmx.getThreadInfo(ids, true, true);
                    String info = "DeadLock Found!\n";
                    for (ThreadInfo ti : tis) {
                        info += ti.toString();
                    }

                    for (ThreadInfo ti : tis) {
                        LockInfo[] locks = ti.getLockedSynchronizers();
                        MonitorInfo[] monitors = ti.getLockedMonitors();
                        if (locks.length == 0 && monitors.length == 0) {
                            /**
                             * this thread is deadlocked but its not guilty
                             */
                            continue;
                        }

                        ThreadInfo dl = ti;
                        info += "Java-level deadlock:\n";
                        info += "\t" + dl.getThreadName() + " is waiting to lock " + dl.getLockInfo().toString()
                                + " which is held by " + dl.getLockOwnerName() + "\n";
                        while ((dl = tmx.getThreadInfo(new long[]{dl.getLockOwnerId()}, true, true)[0]).getThreadId() != ti
                                .getThreadId()) {
                            info += "\t" + dl.getThreadName() + " is waiting to lock " + dl.getLockInfo().toString()
                                    + " which is held by " + dl.getLockOwnerName() + "\n";
                        }
                    }
                    log.warn(info);

                    if (doWhenDL == RESTART) {
                        System.exit(ExitCode.CODE_RESTART);
                    }
                }
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                log.warn("DeadLockDetector: " + e, e);
            }
        }
    }
}
