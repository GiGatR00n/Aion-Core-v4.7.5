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
package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Calendar;

import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.configs.main.MembershipConfig;
import com.aionemu.gameserver.configs.network.IPConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.network.NetworkController;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.ChatService;
import com.aionemu.gameserver.services.EventService;

/**
 * @author -Nemesiss- CC fix
 * @modified by Novo, cura
 * @author GiGatR00n, NewLives
 */
@SuppressWarnings("unused")
public class SM_VERSION_CHECK extends AionServerPacket {
    /**
     * Aion Client version
     */
    private int version;
    /**
     * Number of characters can be created
     */
    private int characterLimitCount;
    /**
     * Related to the character creation mode
     */
    private final int characterFactionsMode;
    private final int characterCreateMode;

    /**
     * @param chatService
     */
    public SM_VERSION_CHECK(int version) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        this.version = version;

        if (MembershipConfig.CHARACTER_ADDITIONAL_ENABLE != 10 && MembershipConfig.CHARACTER_ADDITIONAL_COUNT > GSConfig.CHARACTER_LIMIT_COUNT) {
            characterLimitCount = MembershipConfig.CHARACTER_ADDITIONAL_COUNT;
        } else {
            characterLimitCount = GSConfig.CHARACTER_LIMIT_COUNT;
        }
        characterLimitCount *= NetworkController.getInstance().getServerCount();

        if (GSConfig.CHARACTER_CREATION_MODE < 0 || GSConfig.CHARACTER_CREATION_MODE > 2) {
            characterFactionsMode = 0;
        } else {
            characterFactionsMode = GSConfig.CHARACTER_CREATION_MODE;
        }

        if (GSConfig.CHARACTER_FACTION_LIMITATION_MODE < 0 || GSConfig.CHARACTER_FACTION_LIMITATION_MODE > 3) {
            characterCreateMode = 0;
        } else {
            characterCreateMode = GSConfig.CHARACTER_FACTION_LIMITATION_MODE * 0x04;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
        //aion 3.0 = 194
        //aion 3.5 = 196
        //aion 4.0 = 201
        //aion 4.5 = 203
        //aion 4.7 = 204
        //aion 4.7.0.7 = 205
    	//aion 4.7.5.x = 206
        if (version < 206) {
            //Send wrong client version
            writeC(0x02);
            return;
        }
        writeC(0x00);
        writeC(NetworkConfig.GAMESERVER_ID);
		// need to check this, cause if you check date in Aion World, we havent the good one :/
        writeD(150119);// start year month day
        writeD(141120);// start year month day
        writeD(0x00);// spacing
        writeD(141120);// year month day
        writeD((int) (Calendar.getInstance().getTimeInMillis() / 1000)); // Start Server Time in Seconds Unit (Need to Implements in Config Files)
        writeC(0x00);// unk
        writeC(GSConfig.SERVER_COUNTRY_CODE);// country code;
        writeC(0x00);// unk

        int serverMode = (characterLimitCount * 0x10) | characterFactionsMode;
        writeC(serverMode | characterCreateMode);
        
        writeD((int) (Calendar.getInstance().getTimeInMillis() / 1000));
        writeH(350);//4.3
        writeH(2561);//4.5
        writeH(2561);//4.5
        writeH(5140);//4.5
        writeH(276);//4.5
        writeH(2);//4.5
        writeC(GSConfig.CHARACTER_REENTRY_TIME);
	    switch (EventsConfig.ENABLE_DECOR) {
	    	case 0:
	    		writeC(EventService.getInstance().getEventType().getId());
	    		break;
	    	default:
	    		writeC(EventsConfig.ENABLE_DECOR);
	    }
        writeC(0);//4.5
        writeC(0);//4.5
        writeD(1415577600);//4.5
        writeC(0);//4.5
        writeC(0);//4.5
        writeC(4);//4.5
        writeC(120);//4.5
        writeH(25233);//4.3
        writeC(2);// 4.3
        writeC(1);//4.3
        writeD(0);// 4.3
        writeD(0);// 4.5
        writeD(68536);// 4.5
        writeC(0);//4.7
        writeC(0);//4.7
        writeC(1);//4.7
        writeC(0);//4.7
        writeC(0);//4.7
        writeC(0);//4.7.5.4
        writeH(0x00);//4.7.5.4
        writeH(0x01);//its loop size
        //for... chat servers?
        {
            writeC(0x00);//spacer
            // if the correct ip is not sent it will not work
            writeB(IPConfig.getDefaultAddress());
            writeH(ChatService.getPort());
        }
    }
}
