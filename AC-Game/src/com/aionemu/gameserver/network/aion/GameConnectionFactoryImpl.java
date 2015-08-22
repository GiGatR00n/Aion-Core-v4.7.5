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
package com.aionemu.gameserver.network.aion;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.network.AConnection;
import com.aionemu.commons.network.ConnectionFactory;
import com.aionemu.commons.network.Dispatcher;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.network.sequrity.FloodManager;
import com.aionemu.gameserver.network.sequrity.FloodManager.Result;

/**
 * ConnectionFactory implementation that will be creating AionConnections
 *
 * @author -Nemesiss-
 */
public class GameConnectionFactoryImpl implements ConnectionFactory {

    private final Logger log = LoggerFactory.getLogger(GameConnectionFactoryImpl.class);
    private FloodManager floodAcceptor;

    /**
     * Create a new {@link com.aionemu.commons.network.AConnection AConnection}
     * instance.<br>
     *
     * @param socket     that new
     *                   {@link com.aionemu.commons.network.AConnection AConnection} instance will
     *                   represent.<br>
     * @param dispatcher to witch new connection will be registered.<br>
     * @return a new instance of
     * {@link com.aionemu.commons.network.AConnection AConnection}<br>
     * @throws IOException
     * @see com.aionemu.commons.network.AConnection
     * @see com.aionemu.commons.network.Dispatcher
     */
    public GameConnectionFactoryImpl() {
        if (NetworkConfig.ENABLE_FLOOD_CONNECTIONS) {
            floodAcceptor = new FloodManager(NetworkConfig.Flood_Tick,
                    new FloodManager.FloodFilter(NetworkConfig.Flood_SWARN, NetworkConfig.Flood_SReject, NetworkConfig.Flood_STick), // short period
                    new FloodManager.FloodFilter(NetworkConfig.Flood_LWARN, NetworkConfig.Flood_LReject, NetworkConfig.Flood_LTick)); // long period
        }
    }

    /*
     * (non-Javadoc)
     * @see com.aionemu.commons.network.ConnectionFactory#create(java.nio.channels.SocketChannel,
     * com.aionemu.commons.network.Dispatcher)
     */
    @Override
    public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
        if (NetworkConfig.ENABLE_FLOOD_CONNECTIONS) {
            String host = socket.socket().getInetAddress().getHostAddress();
            final Result isFlooding = floodAcceptor.isFlooding(host, true);
            switch (isFlooding) {
                case REJECTED: {
                    log.warn("Rejected connection from " + host);
                    socket.close();
                    return null;
                }
                case WARNED: {
                    log.warn("Connection over warn limit from " + host);
                    break;
                }
            }
        }

        return new AionConnection(socket, dispatcher);
    }
}
