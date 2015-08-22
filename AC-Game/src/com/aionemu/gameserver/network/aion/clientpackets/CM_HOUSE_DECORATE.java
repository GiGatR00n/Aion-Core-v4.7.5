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

import com.aionemu.gameserver.controllers.HouseController;
import com.aionemu.gameserver.model.gameobjects.HouseDecoration;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.templates.housing.PartType;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOUSE_EDIT;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;

/**
 * @author Rolandas
 */
public class CM_HOUSE_DECORATE extends AionClientPacket {

    int objectId;
    int templateId;
    int lineNr; // Line number (starts from 1 in 3.0 and from 2 in 3.5) of part in House render/update packet

    public CM_HOUSE_DECORATE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        objectId = readD();
        templateId = readD();
        lineNr = readH();
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if (player == null) {
            return;
        }

        House house = player.getHouseRegistry().getOwner();

        PartType partType = PartType.getForLineNr(lineNr);
        int floor = lineNr - partType.getStartLineNr();

        if (objectId == 0) {
            // change appearance to default, delete any applied customs finally
            HouseDecoration decor = house.getRegistry().getDefaultPartByType(partType, floor);
            if (decor.isUsed()) {
                return;
            }
            house.getRegistry().setPartInUse(decor, floor);
        } else {
            // remove from inventory
            HouseDecoration decor = house.getRegistry().getCustomPartByObjId(objectId);
            house.getRegistry().setPartInUse(decor, floor);
            sendPacket(new SM_HOUSE_EDIT(4, 2, objectId)); // yes, in retail it's sent twice!
        }

        sendPacket(new SM_HOUSE_EDIT(4, 2, objectId));
        house.getRegistry().setPersistentState(PersistentState.UPDATE_REQUIRED);
        ((HouseController) house.getController()).updateAppearance();
        QuestEngine.getInstance().onHouseItemUseEvent(new QuestEnv(null, player, 0, 0));
    }
}
