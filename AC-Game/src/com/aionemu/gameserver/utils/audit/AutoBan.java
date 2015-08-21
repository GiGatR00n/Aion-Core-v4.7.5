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
package com.aionemu.gameserver.utils.audit;

import com.aionemu.gameserver.configs.main.PunishmentConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.BannedMacManager;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.services.PunishmentService;

/**
 * @author synchro2
 */
public class AutoBan {

    protected static void punishment(Player player, String message) {

        String reason = "AUTO " + message;
        String address = player.getClientConnection().getMacAddress();
        String accountIp = player.getClientConnection().getIP();
        int accountId = player.getClientConnection().getAccount().getId();
        int playerId = player.getObjectId();
        int time = PunishmentConfig.PUNISHMENT_TIME;
        int minInDay = 1440;
        int dayCount = (int) (Math.floor((double) (time / minInDay)));

        switch (PunishmentConfig.PUNISHMENT_TYPE) {
            case 1:
                player.getClientConnection().close(new SM_QUIT_RESPONSE(), false);
                break;
            case 2:
                PunishmentService.banChar(playerId, dayCount, reason);
                break;
            case 3:
                LoginServer.getInstance().sendBanPacket((byte) 1, accountId, accountIp, time, 0);
                break;
            case 4:
                LoginServer.getInstance().sendBanPacket((byte) 2, accountId, accountIp, time, 0);
                break;
            case 5:
                player.getClientConnection().closeNow();
                BannedMacManager.getInstance().banAddress(address, System.currentTimeMillis() + time * 60000, reason);
                break;
        }
    }
}
