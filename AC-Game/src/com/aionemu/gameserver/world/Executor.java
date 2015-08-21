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
package com.aionemu.gameserver.world;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author xavier
 */
public abstract class Executor<T extends AionObject> {

    private static final Logger log = LoggerFactory.getLogger(Executor.class);

    public abstract boolean run(T object);

    private final void runImpl(Collection<T> objects) {
        try {
            for (T o : objects) {
                if (o != null) {
                    if (!Executor.this.run(o)) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    public final void execute(final Collection<T> objects, boolean now) {
        if (now) {
            runImpl(objects);
        } else {
            ThreadPoolManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    runImpl(objects);
                }
            });
        }
    }

    public final void execute(final Collection<T> objects) {
        execute(objects, false);
    }
}
