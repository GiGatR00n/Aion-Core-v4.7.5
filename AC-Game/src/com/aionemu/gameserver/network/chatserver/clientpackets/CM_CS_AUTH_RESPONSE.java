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
package com.aionemu.gameserver.network.chatserver.clientpackets;

import com.aionemu.commons.utils.ExitCode;
import com.aionemu.gameserver.network.chatserver.ChatServerConnection.State;
import com.aionemu.gameserver.network.chatserver.CsClientPacket;
import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_AUTH;
import com.aionemu.gameserver.services.ChatService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ATracer
 */
public class CM_CS_AUTH_RESPONSE extends CsClientPacket {

    /**
     * Logger for this class.
     */
    protected static final Logger log = LoggerFactory.getLogger(CM_CS_AUTH_RESPONSE.class);
    /**
     * Response: 0=Authed,<br>
     * 1=NotAuthed,<br>
     * 2=AlreadyRegistered
     */
    private int response;
    private byte[] ip;
    private int port;

    /**
     * @param opcode
     */
    public CM_CS_AUTH_RESPONSE(int opcode) {
        super(opcode);
    }

    @Override
    protected void readImpl() {
        response = readC();
        ip = readB(4);
        port = readH();
    }

    @Override
    protected void runImpl() {
        switch (response) {
            case 0: // Authed
                log.info("GameServer authed successfully IP : " + (ip[0] & 0xFF) + "." + (ip[1] & 0xFF) + "." + (ip[2] & 0xFF) + "." + (ip[3] & 0xFF) + " Port: " + port);
                getConnection().setState(State.AUTHED);
                ChatService.setIp(ip);
                ChatService.setPort(port);
                break;
            case 1: // NotAuthed
                log.error("GameServer is not authenticated at ChatServer side");
                System.exit(ExitCode.CODE_ERROR);
                break;
            case 2: // AlreadyRegistered
                log.info("GameServer is already registered at ChatServer side! trying again...");
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        CM_CS_AUTH_RESPONSE.this.getConnection().sendPacket(new SM_CS_AUTH());
                    }
                }, 10000);
                break;
        }
    }
}
