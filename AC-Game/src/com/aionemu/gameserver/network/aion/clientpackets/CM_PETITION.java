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

import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PETITION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.PetitionService;

/**
 * @author zdead
 */
public class CM_PETITION extends AionClientPacket {

    private int action;
    private String title = "";
    private String text = "";
    private String additionalData = "";

    public CM_PETITION(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        action = readH();
        if (action == 2) {
            readD();
        } else {
            String data = readS();
            String[] dataArr = data.split("/", 3);
            title = dataArr[0];
            text = dataArr[1];
            additionalData = dataArr[2];
        }
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        int playerObjId = player.getObjectId();
        if (action == 2) {
            if (PetitionService.getInstance().hasRegisteredPetition(playerObjId)) {
                int petitionId = PetitionService.getInstance().getPetition(playerObjId).getPetitionId();
                PetitionService.getInstance().deletePetition(playerObjId);
                sendPacket(new SM_SYSTEM_MESSAGE(1300552, petitionId));
                sendPacket(new SM_SYSTEM_MESSAGE(1300553, 49));
                return;
            }

        }

        if (!PetitionService.getInstance().hasRegisteredPetition(playerObjId)) {
            Petition petition = PetitionService.getInstance().registerPetition(player, action,
                    title, text, additionalData);
            sendPacket(new SM_PETITION(petition));
        }
    }
}
