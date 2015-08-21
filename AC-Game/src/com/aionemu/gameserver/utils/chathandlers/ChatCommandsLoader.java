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
package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.commons.scripting.classlistener.ClassListener;
import com.aionemu.commons.utils.ClassUtils;

import java.lang.reflect.Modifier;

/**
 * Created on: 12.09.2009 14:13:24
 *
 * @author Aquanox
 */
public class ChatCommandsLoader implements ClassListener {

    private ChatProcessor processor;

    public ChatCommandsLoader(ChatProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void postLoad(Class<?>[] classes) {
        for (Class<?> c : classes) {
            if (!isValidClass(c)) {
                continue;
            }
            Class<?> tmp = (Class<?>) c;
            if (tmp != null) {
                try {
                    processor.registerCommand((ChatCommand) tmp.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        processor.onCompileDone();
    }

    @Override
    public void preUnload(Class<?>[] classes) {
    }

    public boolean isValidClass(Class<?> clazz) {
        final int modifiers = clazz.getModifiers();

        if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
            return false;
        }

        if (!Modifier.isPublic(modifiers)) {
            return false;
        }

        if (!ClassUtils.isSubclass(clazz, AdminCommand.class) && !ClassUtils.isSubclass(clazz, PlayerCommand.class)
                && !ClassUtils.isSubclass(clazz, WeddingCommand.class)) {
            return false;
        }
        return true;
    }
}
