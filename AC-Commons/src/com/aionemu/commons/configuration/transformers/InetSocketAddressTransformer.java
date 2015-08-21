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
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Thransforms string to InetSocketAddress. InetSocketAddress can be represented
 * in following ways:
 * <ul>
 * <li>address:port</li>
 * <li>*:port - will use all avaiable network interfaces</li>
 * </ul>
 *
 * @author SoulKeeper
 */
public class InetSocketAddressTransformer implements PropertyTransformer<InetSocketAddress> {

    /**
     * Shared instance of this transformer. It's thread-safe so no need of
     * multiple instances
     */
    public static final InetSocketAddressTransformer SHARED_INSTANCE = new InetSocketAddressTransformer();

    /**
     * Transforms string to InetSocketAddress
     *
     * @param value value that will be transformed
     * @param field value will be assigned to this field
     * @return InetSocketAddress that represetns value
     * @throws TransformationException if somehting went wrong
     */
    @Override
    public InetSocketAddress transform(String value, Field field) throws TransformationException {
        String[] parts = value.split(":");

        if (parts.length != 2) {
            throw new TransformationException("Can't transform property, must be in format \"address:port\"");
        }

        try {
            if ("*".equals(parts[0])) {
                return new InetSocketAddress(Integer.parseInt(parts[1]));
            }
            InetAddress address = InetAddress.getByName(parts[0]);
            int port = Integer.parseInt(parts[1]);
            return new InetSocketAddress(address, port);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}
