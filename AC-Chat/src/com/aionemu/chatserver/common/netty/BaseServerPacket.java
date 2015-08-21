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
package com.aionemu.chatserver.common.netty;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * @author ATracer
 */
public abstract class BaseServerPacket extends AbstractPacket {

    /**
     * @param opCode
     */
    public BaseServerPacket(int opCode) {
        super(opCode);
    }

    /**
     * @param buf
     * @param value
     */
    protected final void writeD(ChannelBuffer buf, int value) {
        buf.writeInt(value);
    }

    /**
     * @param buf
     * @param value
     */
    protected final void writeH(ChannelBuffer buf, int value) {
        buf.writeShort((short) value);
    }

    /**
     * @param buf
     * @param value
     */
    protected final void writeC(ChannelBuffer buf, int value) {
        buf.writeByte((byte) value);
    }

    /**
     * Write double to buffer.
     *
     * @param buf
     * @param value
     */
    protected final void writeDF(ChannelBuffer buf, double value) {
        buf.writeDouble(value);
    }

    /**
     * Write float to buffer.
     *
     * @param buf
     * @param value
     */
    protected final void writeF(ChannelBuffer buf, float value) {
        buf.writeFloat(value);
    }

    /**
     * @param buf
     * @param data
     */
    protected final void writeB(ChannelBuffer buf, byte[] data) {
        buf.writeBytes(data);
    }

    /**
     * Write String to buffer
     *
     * @param buf
     * @param text
     */
    protected final void writeS(ChannelBuffer buf, String text) {
        if (text == null) {
            buf.writeChar('\000');
        } else {
            final int len = text.length();
            for (int i = 0; i < len; i++) {
                buf.writeChar(text.charAt(i));
            }
            buf.writeChar('\000');
        }
    }

    /**
     * @param buf
     * @param data
     */
    protected final void writeQ(ChannelBuffer buf, long data) {
        buf.writeLong(data);
    }
}
