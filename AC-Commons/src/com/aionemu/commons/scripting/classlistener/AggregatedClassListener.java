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
package com.aionemu.commons.scripting.classlistener;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * ClassListener that aggregates a collection of ClassListeners.<br>
 * Please note that "shutdown" listeners will be executed in reverse order.
 *
 * @author SoulKeeper
 */
public class AggregatedClassListener implements ClassListener {

    private final List<ClassListener> classListeners;

    public AggregatedClassListener() {
        classListeners = Lists.newArrayList();
    }

    public AggregatedClassListener(List<ClassListener> classListeners) {
        this.classListeners = classListeners;
    }

    public List<ClassListener> getClassListeners() {
        return classListeners;
    }

    public void addClassListener(ClassListener cl) {
        getClassListeners().add(cl);
    }

    @Override
    public void postLoad(Class<?>[] classes) {
        for (ClassListener cl : getClassListeners()) {
            cl.postLoad(classes);
        }
    }

    @Override
    public void preUnload(Class<?>[] classes) {
        for (ClassListener cl : Lists.reverse(getClassListeners())) {
            cl.preUnload(classes);
        }
    }
}
