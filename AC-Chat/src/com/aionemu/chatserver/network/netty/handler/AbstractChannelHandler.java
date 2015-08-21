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
package com.aionemu.chatserver.network.netty.handler;

import java.io.IOException;
import java.net.InetAddress;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.chatserver.common.netty.BaseServerPacket;

/**
 * @author ATracer
 */
public abstract class AbstractChannelHandler extends SimpleChannelUpstreamHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractChannelHandler.class);
    /**
     * IP address of channel client
     */
    protected InetAddress inetAddress;
    /**
     * Associated channel
     */
    protected Channel channel;

    /**
     * Invoked when a Channel was disconnected from its remote peer
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        log.info("Channel disconnected IP: " + inetAddress.getHostAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        if (!(e.getCause() instanceof IOException)) {
            log.error("NETTY: Exception caught: ", e.getCause());
        }
    }

    /**
     * Closes the channel but ensures that packet is send before close
     *
     * @param packet
     */
    public void close(BaseServerPacket packet) {
        channel.write(packet).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * Closes the channel
     */
    public void close() {
        channel.close();
    }

    /**
     * @return the IP address string
     */
    public String getIP() {
        return inetAddress.getHostAddress();
    }
}
