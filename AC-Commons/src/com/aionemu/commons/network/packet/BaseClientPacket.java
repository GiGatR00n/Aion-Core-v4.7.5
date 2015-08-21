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
package com.aionemu.commons.network.packet;

import com.aionemu.commons.network.AConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Base class for every Client Packet
 *
 * @param <T> AConnection - owner of this client packet.
 * @author -Nemesiss-
 */
public abstract class BaseClientPacket<T extends AConnection> extends BasePacket implements Runnable {

    /**
     * Logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(BaseClientPacket.class);
    /**
     * Owner of this packet.
     */
    private T client;
    /**
     * ByteBuffer that contains this packet data
     */
    private ByteBuffer buf;

    /**
     * Constructs a new client packet with specified id and data buffer.
     *
     * @param buf    packet data container.
     * @param opcode packet opcode.
     */
    public BaseClientPacket(ByteBuffer buf, int opcode) {
        this(opcode);
        this.buf = buf;
    }

    /**
     * Constructs a new client packet with specified id. ByteBuffer must be
     * later set with setBuffer method.
     *
     * @param opcode packet opcode.
     */
    public BaseClientPacket(int opcode) {
        super(PacketType.CLIENT, opcode);
    }

    /**
     * Attach ByteBuffer to this packet.
     *
     * @param buf
     */
    public void setBuffer(ByteBuffer buf) {
        this.buf = buf;
    }

    /**
     * Attach client connection to this packet.
     *
     * @param client
     */
    public void setConnection(T client) {
        this.client = client;
    }

    /**
     * This method reads data from a packet buffer. If the error occurred while
     * reading data, the connection is closed.
     *
     * @return <code>true</code> if reading was successful, otherwise
     * <code>false</code>
     */
    public final boolean read() {
        try {
            readImpl();

            if (getRemainingBytes() > 0) {
                log.debug("Packet " + this + " not fully readed!");
            }

            return true;
        } catch (Exception re) {
            log.error("Reading failed for packet " + this, re);
            return false;
        }
    }

    /**
     * Data reading implementation
     */
    protected abstract void readImpl();

    /**
     * @return number of bytes remaining in this packet buffer.
     */
    public final int getRemainingBytes() {
        return buf.remaining();
    }

    /**
     * Read int from this packet buffer.
     *
     * @return int
     */
    protected final int readD() {
        try {
            return buf.getInt();
        } catch (Exception e) {
            log.error("Missing D for: " + this);
        }
        return 0;
    }

    /**
     * Read byte from this packet buffer.
     *
     * @return int
     */
    protected final int readC() {
        try {
            return buf.get() & 0xFF;
        } catch (Exception e) {
            log.error("Missing C for: " + this);
        }
        return 0;
    }

    /**
     * Read signed byte from this packet buffer.
     *
     * @return int
     */
    protected final byte readSC() {
        try {
            return buf.get();
        } catch (Exception e) {
            log.error("Missing C for: " + this);
        }
        return 0;
    }

    /**
     * Read signed short from this packet buffer.
     *
     * @return int
     */
    protected final short readSH() {
        try {
            return buf.getShort();
        } catch (Exception e) {
            log.error("Missing H for: " + this);
        }
        return 0;
    }

    protected final int readH() {
        try {
            return buf.getShort() & 0xFFFF;
        } catch (Exception e) {
            log.error("Missing H for: " + this);
        }
        return 0;
    }

    /**
     * Read double from this packet buffer.
     *
     * @return double
     */
    protected final double readDF() {
        try {
            return buf.getDouble();
        } catch (Exception e) {
            log.error("Missing DF for: " + this);
        }
        return 0;
    }

    /**
     * Read double from this packet buffer.
     *
     * @return double
     */
    protected final float readF() {
        try {
            return buf.getFloat();
        } catch (Exception e) {
            log.error("Missing F for: " + this);
        }
        return 0;
    }

    /**
     * Read long from this packet buffer.
     *
     * @return long
     */
    protected final long readQ() {
        try {
            return buf.getLong();
        } catch (Exception e) {
            log.error("Missing Q for: " + this);
        }
        return 0;
    }

    /**
     * Read String from this packet buffer.
     *
     * @return String
     */
    protected final String readS() {
        StringBuffer sb = new StringBuffer();
        char ch;
        try {
            while ((ch = buf.getChar()) != 0) {
                sb.append(ch);
            }
        } catch (Exception e) {
            log.error("Missing S for: " + this);
        }
        return sb.toString();
    }

    /**
     * Read n bytes from this packet buffer, n = length.
     *
     * @param length
     * @return byte[]
     */
    protected final byte[] readB(int length) {
        byte[] result = new byte[length];
        try {
            buf.get(result);
        } catch (Exception e) {
            log.error("Missing byte[] for: " + this);
        }
        return result;
    }

    /**
     * Execute this packet action.
     */
    protected abstract void runImpl();

    /**
     * @return Connection that is owner of this packet.
     */
    public final T getConnection() {
        return client;
    }
}
