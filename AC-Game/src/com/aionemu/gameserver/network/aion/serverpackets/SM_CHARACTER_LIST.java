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

import java.util.Iterator;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.MailDAO;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.CharacterBanInfo;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.PlayerInfo;
import com.aionemu.gameserver.services.BrokerService;
import com.aionemu.gameserver.services.player.PlayerService;

/**
 * In this packet Server is sending Character List to client.
 *
 * @author Nemesiss, AEJTester
 * @author GiGatR00n
 */
public class SM_CHARACTER_LIST extends PlayerInfo {

    /**
     * PlayOk2 - we dont care...
     */
    private final int playOk2;

    /**
     * Constructs new <tt>SM_CHARACTER_LIST </tt> packet
     */
    public SM_CHARACTER_LIST(int playOk2) {
        this.playOk2 = playOk2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(playOk2);

        Account account = con.getAccount();
        
        /* Checks for Deleted Characters for each client request */
        removeDeletedCharacters(account);
        
        writeC(account.size()); // characters count

        for (PlayerAccountData playerData : account.getSortedAccountsList()) {
            PlayerCommonData pcd = playerData.getPlayerCommonData();
            CharacterBanInfo cbi = playerData.getCharBanInfo();
            Player player = PlayerService.getPlayer(pcd.getPlayerObjId(), account);

            writePlayerInfo(playerData);
            writeD(player.getPlayerSettings().getDisplay());//display helmet 0 show, 5 dont show
            //writeB()//What is this  //characher Name?
            writeD(0);
            writeD(0);
            writeD(DAOManager.getDAO(MailDAO.class).haveUnread(pcd.getPlayerObjId()) ? 1 : 0); // mail
            writeD(0); //unk
            writeD(0); //unk
            writeQ(BrokerService.getInstance().getCollectedMoney(pcd)); // collected money from broker
            writeD(0);

            if (cbi != null && cbi.getEnd() > System.currentTimeMillis() / 1000) {
                //client wants int so let's hope we do not reach long limit with timestamp while this server is used :P
                writeD((int) cbi.getStart()); //startPunishDate
                writeD((int) cbi.getEnd()); //endPunishDate
                writeS(cbi.getReason());
                writeD(0); // unk 4.5
                writeD(0); // unk 4.5
                writeD(0); // unk 4.5
                writeD(0); // unk 4.5
                writeB(new byte[88]); // unk 4.5.0.18
            } else {
                writeD(0);
                writeD(0);
                writeH(0);
                writeD(0); // unk 4.5
                writeD(0); // unk 4.5
                writeD(0); // unk 4.5
                writeD(0); // unk 4.5
                writeB(new byte[26]); // unk 4.7.5.4
                writeD(playerData.getDeletionTimeInSeconds()); // v4.7.5.4
                writeB(new byte[58]); // unk 4.5.0.18
            }
        }
    }
    
    /**
     * 
     * @param account
     */
    public void removeDeletedCharacters(Account account) {
        /* Removes chars that should be removed */
        Iterator<PlayerAccountData> it = account.iterator();
        while (it.hasNext()) {
            PlayerAccountData pad = it.next();
            Race race = pad.getPlayerCommonData().getRace();
            long deletionTime = (long) pad.getDeletionTimeInSeconds() * (long) 1000;
            if (deletionTime != 0 && deletionTime <= System.currentTimeMillis()) {
                it.remove();
                account.decrementCountOf(race);
                PlayerService.deletePlayerFromDB(pad.getPlayerCommonData().getPlayerObjId());
            }
        }
        if (account.isEmpty()) {
            removeAccountWH(account.getId());
            account.getAccountWarehouse().clear();
        }
    }

    private static void removeAccountWH(int accountId) {
        DAOManager.getDAO(InventoryDAO.class).deleteAccountWH(accountId);
    }      
}
