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
package com.aionemu.commons.configuration;

import java.lang.annotation.*;

/**
 * This annotation is used to mark field that should be processed by
 * {@link com.aionemu.commons.configuration.ConfigurableProcessor}<br>
 * <br>
 * This annotation is Documented, all definitions with it will appear in javadoc
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {

    /**
     * This string shows to
     * {@link com.aionemu.commons.configuration.ConfigurableProcessor} that init
     * value of the object should not be overriden.
     */
    public static final String DEFAULT_VALUE = "DO_NOT_OVERWRITE_INITIALIAZION_VALUE";

    /**
     * Property name in configuration
     *
     * @return name of the property that will be used
     */
    public String key();

    /**
     * PropertyTransformer to use.<br>
     * List of automaticly transformed types:<br>
     * <ul>
     * <li>{@link Boolean} and boolean by
     * {@link com.aionemu.commons.configuration.transformers.BooleanTransformer}</li>
     * <li>{@link Byte} and byte by
     * {@link com.aionemu.commons.configuration.transformers.ByteTransformer}</li>
     * <li>{@link Character} and char by
     * {@link com.aionemu.commons.configuration.transformers.CharTransformer}</li>
     * <li>{@link Short} and short by
     * {@link com.aionemu.commons.configuration.transformers.ShortTransformer}</li>
     * <li>{@link Integer} and int by
     * {@link com.aionemu.commons.configuration.transformers.IntegerTransformer}</li>
     * <li>{@link Float} and float by
     * {@link com.aionemu.commons.configuration.transformers.FloatTransformer}</li>
     * <li>{@link Long} and long by
     * {@link com.aionemu.commons.configuration.transformers.LongTransformer}</li>
     * <li>{@link Double} and double by
     * {@link com.aionemu.commons.configuration.transformers.DoubleTransformer}</li>
     * <li>{@link String} by
     * {@link com.aionemu.commons.configuration.transformers.StringTransformer}</li>
     * <li>{@link Enum} and enum by
     * {@link com.aionemu.commons.configuration.transformers.EnumTransformer}</li>
     * <li>{@link java.io.File} by
     * {@link com.aionemu.commons.configuration.transformers.FileTransformer}</li>
     * <li>{@link java.net.InetSocketAddress} by
     * {@link com.aionemu.commons.configuration.transformers.InetSocketAddressTransformer}</li>
     * <li>{@link java.util.regex.Pattern} by
     * {@link com.aionemu.commons.configuration.transformers.PatternTransformer}
     * </ul>
     * <p/>
     * If your value is one of this types - just leave this field empty
     *
     * @return returns class that will be used to transform value
     */
    @SuppressWarnings("rawtypes")
    public Class<? extends PropertyTransformer> propertyTransformer() default PropertyTransformer.class;

    /**
     * Represents default value that will be parsed if key not found. If this
     * key equals(default) {@link #DEFAULT_VALUE} init value of the object won't
     * be overriden
     *
     * @return default value of the property
     */
    public String defaultValue() default DEFAULT_VALUE;
}
