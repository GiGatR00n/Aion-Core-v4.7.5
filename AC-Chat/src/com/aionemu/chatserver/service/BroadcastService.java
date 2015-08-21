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
package com.aionemu.chatserver.service;

import java.util.Map;

import com.aionemu.chatserver.model.ChatClient;
import com.aionemu.chatserver.model.message.Message;
import com.aionemu.chatserver.network.aion.serverpackets.SM_CHANNEL_MESSAGE;
import com.aionemu.chatserver.network.netty.handler.ClientChannelHandler;
import com.aionemu.commons.utils.internal.chmv8.PlatformDependent;

/**
 * @author ATracer
 */
public class BroadcastService {

    private static BroadcastService instance = new BroadcastService();

    public static BroadcastService getInstance() {
        return instance;
    }
    private Map<Integer, ChatClient> clients = PlatformDependent.newConcurrentHashMap();

    /**
     * @param client
     */
    public void addClient(ChatClient client) {
        clients.put(client.getClientId(), client);
    }

    /**
     * @param client
     */
    public void removeClient(ChatClient client) {
        clients.remove(client.getClientId());
    }

    /**
     * @param message
     */
    public void broadcastMessage(Message message) {
        for (ChatClient client : clients.values()) {
            if (client.isInChannel(message.getChannel())) {
                sendMessage(client, message);
            }
        }
    }

    /**
     * @param chatClient
     * @param message
     */
    public void sendMessage(ChatClient chatClient, Message message) {
        ClientChannelHandler cch = chatClient.getChannelHandler();
        cch.sendPacket(new SM_CHANNEL_MESSAGE(message));
    }
}
