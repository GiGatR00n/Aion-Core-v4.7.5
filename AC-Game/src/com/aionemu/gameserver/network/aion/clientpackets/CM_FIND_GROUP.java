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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FIND_GROUP;
import com.aionemu.gameserver.services.FindGroupService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author cura, MrPoke
 */
public class CM_FIND_GROUP extends AionClientPacket {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(CM_FIND_GROUP.class);
    private int action;
    private int playerObjId;
    private String message;
    private int groupType;
    @SuppressWarnings("unused")
    private int classId;
    @SuppressWarnings("unused")
    private int level;
    private int unk;
    private int instanceId;
    private int minMembers;

    public CM_FIND_GROUP(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        action = readC();

        switch (action) {
            case 0x00: // recruit list
                break;
            case 0x01: // offer delete
                playerObjId = readD();
                unk = readD(); // unk(65557)
                break;
            case 0x02: // send offer
                playerObjId = readD();
                message = readS();
                groupType = readC();
                break;
            case 0x03: // recruit update
                playerObjId = readD();
                unk = readD(); // unk(65557)
                message = readS();
                groupType = readC();
                break;
            case 0x04: // apply list
                break;
            case 0x05: // post delete
                playerObjId = readD();
                break;
            case 0x06: // apply create
                playerObjId = readD();
                message = readS();
                groupType = readC();
                classId = readC();
                level = readC();
                break;
            case 0x07: // apply update
                // TODO need packet check
                break;
            case 0x08: //register InstanceGroup
                instanceId = readD();
                groupType = readC();//need to be tested
                message = readS();//text
                minMembers = readC();//minMembers chosen by writer
                break;
            case 0x0A: //New 4.0 Group Recruitment
                break;
            default:
                log.error("Unknown find group packet? 0x" + Integer.toHexString(action).toUpperCase());
                break;
        }
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        switch (action) {
            case 0x00:
            case 0x04:
                FindGroupService.getInstance().sendFindGroups(player, action);
                break;
            case 0x01:
            case 0x05:
                FindGroupService.getInstance().removeFindGroup(player.getRace(), action - 1, playerObjId);
                break;
            case 0x02:
            case 0x06:
                FindGroupService.getInstance().addFindGroupList(player, action, message, groupType);
                break;
            case 0x03:
                FindGroupService.getInstance().updateFindGroupList(player, message, playerObjId);
                break;
            case 0x08:
                FindGroupService.getInstance().registerInstanceGroup(player, 0x0E, instanceId, message, minMembers, groupType);
                break;
            case 0x0A: //search
                FindGroupService.getInstance().sendFindGroups(player, action);
                break;
            default:
                PacketSendUtility.sendPacket(player, new SM_FIND_GROUP(action, playerObjId, unk));
                break;
        }
    }
}
