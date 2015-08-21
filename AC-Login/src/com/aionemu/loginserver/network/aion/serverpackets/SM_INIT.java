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
package com.aionemu.loginserver.network.aion.serverpackets;

import com.aionemu.loginserver.network.aion.AionServerPacket;
import com.aionemu.loginserver.network.aion.LoginConnection;

import javax.crypto.SecretKey;

/**
 * Format: dd b dddd s d: session id d: protocol revision b: 0x90 bytes : 0x80
 * bytes for the scrambled RSA public key 0x10 bytes at 0x00 d: unknow d: unknow
 * d: unknow d: unknow s: blowfish key
 */
public final class SM_INIT extends AionServerPacket {

    /**
     * Session Id of this connection
     */
    private final int sessionId;
    /**
     * public Rsa key that client will use to encrypt login and password that
     * will be send in RequestAuthLogin client packet.
     */
    private final byte[] publicRsaKey;
    /**
     * blowfish key for packet encryption/decryption.
     */
    private final byte[] blowfishKey;

    /**
     * Constructor
     *
     * @param client
     * @param blowfishKey
     */
    public SM_INIT(LoginConnection client, SecretKey blowfishKey) {
        this(client.getEncryptedModulus(), blowfishKey.getEncoded(), client.getSessionId());
    }

    /**
     * Creates new instance of <tt>SM_INIT</tt> packet.
     *
     * @param publicRsaKey Public RSA key
     * @param blowfishKey  Blowfish key
     * @param sessionId    Session identifier
     */
    private SM_INIT(byte[] publicRsaKey, byte[] blowfishKey, int sessionId) {
        super(0x00);
        this.sessionId = sessionId;
        this.publicRsaKey = publicRsaKey;
        this.blowfishKey = blowfishKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(LoginConnection con) {
        writeD(sessionId); // session id
        writeD(0x0000c621); // protocol revision
        writeB(publicRsaKey); // RSA Public Key
        // unk
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);

        writeB(blowfishKey); // BlowFish key
        writeD(197635); // unk
        writeD(2097152); // unk
    }
}
