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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.loginserver.LoginServer;

/**
 * In this packets aion client is authenticating himself by providing accountId
 * and rest of sessionKey - we will check if its valid at login server side.
 *
 * @author -Nemesiss-
 */
// TODO: L2AUTH? Really? :O
public class CM_L2AUTH_LOGIN_CHECK extends AionClientPacket {

    /**
     * playOk2 is part of session key - its used for security purposes we will
     * check if this is the key what login server sends.
     */
    private int playOk2;
    /**
     * playOk1 is part of session key - its used for security purposes we will
     * check if this is the key what login server sends.
     */
    private int playOk1;
    /**
     * accountId is part of session key - its used for authentication we will
     * check if this accountId is matching any waiting account login server side
     * and check if rest of session key is ok.
     */
    private int accountId;
    /**
     * loginOk is part of session key - its used for security purposes we will
     * check if this is the key what login server sends.
     */
    private int loginOk;
    @SuppressWarnings("unused")
    private int unk1;
    @SuppressWarnings("unused")
    private int unk2;

    /**
     * Constructs new instance of <tt>CM_L2AUTH_LOGIN_CHECK </tt> packet
     *
     * @param opcode
     */
    public CM_L2AUTH_LOGIN_CHECK(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        playOk2 = readD();
        playOk1 = readD();
        accountId = readD();
        loginOk = readD();
        unk1 = readD();
        unk2 = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        LoginServer.getInstance().requestAuthenticationOfClient(accountId, getConnection(), loginOk, playOk1, playOk2);
    }
}
