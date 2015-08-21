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
 * This class implements basic boolean transfromer.
 * <p/>
 * Boolean can be represented by "true/false" (case doen't matter) or "1/0". In
 * other cases {@link com.aionemu.commons.configuration.TransformationException}
 * is thrown
 *
 * @author SoulKeeper
 */
public class BooleanTransformer implements PropertyTransformer<Boolean> {

    /**
     * Shared instance of this transformer, it's thread safe so no need to
     * create multiple instances
     */
    public static final BooleanTransformer SHARED_INSTANCE = new BooleanTransformer();

    /**
     * Transforms string to boolean.
     *
     * @param value value that will be transformed
     * @param field value will be assigned to this field
     * @return Boolean object that represents transformed value
     * @throws TransformationException if something goes wrong
     */
    @Override
    public Boolean transform(String value, Field field) throws TransformationException {
        // We should have error here if value is not correct, default
        // "Boolean.parseBoolean" returns false if string
        // is not "true" ignoring case
        if ("true".equalsIgnoreCase(value) || "1".equals(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value) || "0".equals(value)) {
            return false;
        } else {
            throw new TransformationException("Invalid boolean string: " + value);
        }
    }
}
