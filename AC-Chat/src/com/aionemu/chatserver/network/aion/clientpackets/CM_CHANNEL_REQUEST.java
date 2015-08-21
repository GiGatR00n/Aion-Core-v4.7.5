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
package com.aionemu.chatserver.network.aion.clientpackets;

import java.io.UnsupportedEncodingException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import com.aionemu.chatserver.configs.Config;
import com.aionemu.chatserver.model.ChatClient;
import com.aionemu.chatserver.model.channel.Channel;
import com.aionemu.chatserver.network.aion.AbstractClientPacket;
import com.aionemu.chatserver.network.aion.serverpackets.SM_CHANNEL_RESPONSE;
import com.aionemu.chatserver.network.netty.handler.ClientChannelHandler;
import com.aionemu.chatserver.service.ChatService;

/**
 * @author ATracer
 */
public class CM_CHANNEL_REQUEST extends AbstractClientPacket {

    private static final Logger log = LoggerFactory.getLogger(CM_CHANNEL_REQUEST.class);
    private int channelIndex;
    private byte[] channelIdentifier;
    private ChatService chatService;

    /**
     * @param channelBuffer
     * @param gameChannelHandler
     * @param opCode
     */
    public CM_CHANNEL_REQUEST(ChannelBuffer channelBuffer, ClientChannelHandler gameChannelHandler, ChatService chatService) {
        super(channelBuffer, gameChannelHandler, 0x10);
        this.chatService = chatService;
    }

    @Override
    protected void readImpl() {
        readC(); // 0x40
        readH(); // 0x00
        channelIndex = readH();
        readB(18); //?
        int length = (readH() * 2);
        channelIdentifier = readB(length);
        readD(); // ?
    }

    @Override
    protected void runImpl() {
        try {
            if (Config.LOG_CHANNEL_REQUEST) {
                log.info("Channel requested " + new String(channelIdentifier, "UTF-16le"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ChatClient chatClient = clientChannelHandler.getChatClient();
        Channel channel = chatService.registerPlayerWithChannel(chatClient, channelIndex, channelIdentifier);
        if (channel != null) {
            clientChannelHandler.sendPacket(new SM_CHANNEL_RESPONSE(channel, channelIndex));
        }
    }

    @Override
    public String toString() {
        return "CM_CHANNEL_REQUEST [channelIndex=" + channelIndex + ", channelIdentifier=" + new String(channelIdentifier)
                + "]";
    }
}
