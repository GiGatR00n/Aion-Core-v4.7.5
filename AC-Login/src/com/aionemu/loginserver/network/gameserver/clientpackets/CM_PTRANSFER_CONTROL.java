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
package com.aionemu.loginserver.network.gameserver.clientpackets;

import com.aionemu.loginserver.network.gameserver.GsClientPacket;
import com.aionemu.loginserver.service.PlayerTransferService;

/**
 * @author KID
 */
public class CM_PTRANSFER_CONTROL extends GsClientPacket {

    private byte actionId;

    @Override
    protected void readImpl() {
        actionId = this.readSC();
        switch (actionId) {
            case 1: // request transfer
            {
                int taskId = readD();
                String name = readS();
                int bytes = this.getRemainingBytes();
                byte[] db = this.readB(bytes);
                PlayerTransferService.getInstance().requestTransfer(taskId, name, db);
            }
            break;
            case 2: // ERROR
            {
                int taskId = readD();
                String reason = readS();
                PlayerTransferService.getInstance().onError(taskId, reason);
            }
            break;
            case 3: // ok
            {
                int taskId = readD();
                int playerId = readD();
                PlayerTransferService.getInstance().onOk(taskId, playerId);
            }
            break;
            case 4: // Task stop
            {
                int taskId = readD();
                String reason = readS();
                PlayerTransferService.getInstance().onTaskStop(taskId, reason);
            }
        }
    }

    @Override
    protected void runImpl() {
        // no actions required
    }
}
