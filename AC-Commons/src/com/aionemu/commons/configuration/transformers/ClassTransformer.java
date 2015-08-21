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
package com.aionemu.commons.configuration.transformers;

import com.aionemu.commons.configuration.PropertyTransformer;
import com.aionemu.commons.configuration.TransformationException;

import java.lang.reflect.Field;

/**
 * Returns the <code>Class</code> object associated with the class or interface
 * with the given string name. The class is not being initialized. <br />
 * Created on: 12.09.2009 15:10:47
 *
 * @author Aquanox
 * @see Class#forName(String)
 * @see Class#forName(String, boolean, ClassLoader)
 */
public class ClassTransformer implements PropertyTransformer<Class<?>> {

    /**
     * Shared instance.
     */
    public static final ClassTransformer SHARED_INSTANCE = new ClassTransformer();

    @Override
    public Class<?> transform(String value, Field field) throws TransformationException {
        try {
            return Class.forName(value, false, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new TransformationException("Cannot find class with name '" + value + "'");
        }
    }
}
