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

import org.jboss.netty.buffer.ChannelBuffer;

import com.aionemu.chatserver.model.ChatClient;
import com.aionemu.chatserver.network.aion.AbstractClientPacket;
import com.aionemu.chatserver.network.aion.serverpackets.SM_CHAT_INI;
import com.aionemu.chatserver.network.netty.handler.ClientChannelHandler;
import com.aionemu.chatserver.service.ChatService;

/**
 * @author ginho1
 */
public class CM_CHAT_INI extends AbstractClientPacket {

    public CM_CHAT_INI(ChannelBuffer channelBuffer, ClientChannelHandler clientChannelHandler, ChatService chatService) {
        super(channelBuffer, clientChannelHandler, 0x30);
    }

    @Override
    protected void readImpl() {
        readC();
        readH();
        readD();
        readD();
        readD();
    }

    @Override
    protected void runImpl() {
        ChatClient chatClient = clientChannelHandler.getChatClient();
        clientChannelHandler.sendPacket(new SM_CHAT_INI());
    }
}
