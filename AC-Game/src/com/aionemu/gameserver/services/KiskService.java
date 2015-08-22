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
package com.aionemu.gameserver.services;

import java.util.Map;

import javolution.util.FastMap;

import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BIND_POINT_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Sarynth, nrg
 */
public class KiskService {

    private static final KiskService instance = new KiskService();
    private final Map<Integer, Kisk> boundButOfflinePlayer = new FastMap<Integer, Kisk>().shared();
    private final Map<Integer, Kisk> ownerPlayer = new FastMap<Integer, Kisk>().shared();

    /**
     * Remove kisk references and containers.
     *
     * @param kisk
     */
    public void removeKisk(Kisk kisk) {
        //remove offline binds
        for (int memberId : kisk.getCurrentMemberIds()) {
            boundButOfflinePlayer.remove(memberId);
        }

        for (Integer obj : ownerPlayer.keySet()) {
            if (ownerPlayer.get(obj).equals(kisk)) {
                ownerPlayer.remove(obj);
                break;
            }
        }

        //send players SET_BIND_POINT and send them die packet again, if they lie dead, but are still not revived
        for (Player member : kisk.getCurrentMemberList()) {
            member.setKisk(null);
            PacketSendUtility.sendPacket(member, new SM_BIND_POINT_INFO(0, 0f, 0f, 0f, member));
            if (member.getLifeStats().isAlreadyDead()) {
                member.getController().sendDie();
            }
        }
    }

    /**
     * @param kisk
     * @param player
     */
    public void onBind(Kisk kisk, Player player) {
        if (player.getKisk() != null) {
            player.getKisk().removePlayer(player);
        }

        kisk.addPlayer(player);

        // Send Bind Point Data
        TeleportService2.sendSetBindPoint(player);

        // Send System Message
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_BINDSTONE_REGISTER);

        // Send Animated Bind Flash
        PacketSendUtility.broadcastPacket(player, new SM_LEVEL_UPDATE(player.getObjectId(), 2, player.getCommonData().getLevel()), true);
    }

    /**
     * @param player
     */
    public void onLogin(Player player) {
        Kisk kisk = this.boundButOfflinePlayer.get(player.getObjectId());
        if (kisk != null) {
            kisk.addPlayer(player);
            this.boundButOfflinePlayer.remove(player.getObjectId());
        }
    }

    public void onLogout(Player player) {
        Kisk kisk = player.getKisk();
        //store binding if existent
        if (kisk != null) {
            this.boundButOfflinePlayer.put(player.getObjectId(), kisk);
        }
    }

    public void regKisk(Kisk kisk, Integer objOwnerId) {
        ownerPlayer.put(objOwnerId, kisk);
    }

    public boolean haveKisk(Integer objOwnerId) {
        return ownerPlayer.containsKey(objOwnerId);
    }

    public static KiskService getInstance() {
        return instance;
    }
}
