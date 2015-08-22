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

import com.aionemu.gameserver.configs.main.AutoGroupConfig;
import com.aionemu.gameserver.model.autogroup.EntryRequestType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.AutoGroupService;
import com.aionemu.gameserver.services.instance.DredgionService;
import com.aionemu.gameserver.services.instance.IdgelDomeService;
import com.aionemu.gameserver.services.instance.IronWallWarFrontService;
import com.aionemu.gameserver.services.instance.KamarBattlefieldService;
import com.aionemu.gameserver.services.instance.OphidanBridgeService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Shepper, Guapo, nrg
 * @author GiGatR00n v4.7.5.x
 */
public class CM_AUTO_GROUP extends AionClientPacket {

    private byte instanceMaskId;
    private byte windowId;
    private byte entryRequestId;

    public CM_AUTO_GROUP(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        instanceMaskId = (byte) readD();
        windowId = (byte) readC();
        entryRequestId = (byte) readC();
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if (!AutoGroupConfig.AUTO_GROUP_ENABLE) {
            PacketSendUtility.sendMessage(player, "Auto Group is disabled");
            return;
        }
        switch (windowId) {
            case 100:
                EntryRequestType ert = EntryRequestType.getTypeById(entryRequestId);
                if (ert == null) {
                    return;
                }
                AutoGroupService.getInstance().startLooking(player, instanceMaskId, ert);
                break;
            case 101:
                AutoGroupService.getInstance().unregisterLooking(player, instanceMaskId);
                break;
            case 102:
                AutoGroupService.getInstance().pressEnter(player, instanceMaskId);
                break;
            case 103:
                AutoGroupService.getInstance().cancelEnter(player, instanceMaskId);
                break;
            case 104:
                DredgionService.getInstance().showWindow(player, instanceMaskId);
                IronWallWarFrontService.getInstance().showWindow(player, instanceMaskId);
                KamarBattlefieldService.getInstance().showWindow(player, instanceMaskId);
                OphidanBridgeService.getInstance().showWindow(player, instanceMaskId);
                IdgelDomeService.getInstance().showWindow(player, instanceMaskId);
                break;
            case 105:
                // DredgionRegService.getInstance().failedEnterDredgion(player);
                break;
        }
    }
}
