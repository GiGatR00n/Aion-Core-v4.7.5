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

/**
 * This exception is internal for configuration process. Thrown by
 * {@link com.aionemu.commons.configuration.PropertyTransformer} when
 * transformaton error occurs and is catched by
 * {@link com.aionemu.commons.configuration.ConfigurableProcessor}
 *
 * @author SoulKeeper
 */
public class TransformationException extends RuntimeException {

    /**
     * SerialID
     */
    private static final long serialVersionUID = -6641235751743285902L;

    /**
     * Creates new instance of exception
     */
    public TransformationException() {
    }

    /**
     * Creates new instance of exception
     *
     * @param message exception message
     */
    public TransformationException(String message) {
        super(message);
    }

    /**
     * Creates new instance of exception
     *
     * @param message exception message
     * @param cause   exception that is the reason of this exception
     */
    public TransformationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new instance of exception
     *
     * @param cause exception that is the reason of this exception
     */
    public TransformationException(Throwable cause) {
        super(cause);
    }
}
