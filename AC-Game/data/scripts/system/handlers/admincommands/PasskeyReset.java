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
package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dao.PlayerPasskeyDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author cura
 */
public class PasskeyReset extends AdminCommand {

    public PasskeyReset() {
        super("passkeyreset");
    }

    @Override
    public void execute(Player player, String... params) {
        if (params == null || params.length < 1) {
            PacketSendUtility.sendMessage(player, "syntax: //passkeyreset <player> <passkey>");
            return;
        }

        String name = Util.convertName(params[0]);
        int accountId = DAOManager.getDAO(PlayerDAO.class).getAccountIdByName(name);
        if (accountId == 0) {
            PacketSendUtility.sendMessage(player, "player " + name + " can't find!");
            PacketSendUtility.sendMessage(player, "syntax: //passkeyreset <player> <passkey>");
            return;
        }

        try {
            Integer.parseInt(params[1]);
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(player, "parameters should be number!");
            return;
        }

        String newPasskey = params[1];
        if (!(newPasskey.length() > 5 && newPasskey.length() < 9)) {
            PacketSendUtility.sendMessage(player, "passkey is 6~8 digits!");
            return;
        }

        DAOManager.getDAO(PlayerPasskeyDAO.class).updateForcePlayerPasskey(accountId, newPasskey);
        LoginServer.getInstance().sendBanPacket((byte) 2, accountId, "", -1, player.getObjectId());
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax: //passkeyreset <player> <passkey>");
    }
}
