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
package ai.instance.shugoImperialTomb;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.drop.DropService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.audit.AuditLogger;

import java.util.Collection;
import java.util.HashSet;

import static ch.lambdaj.Lambda.maxFrom;

/**
 * @author Swig
 */
@AIName("shugo_relic") // 831122, 831123, 831124, 831373
public class ShugoRelicsAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) {
        int npcId = getOwner().getNpcId();
        if ((npcId == 831122 || npcId == 831123 || npcId == 831124) && (player.getInventory().decreaseByItemId(185000129, 1) || player.getInventory().decreaseByItemId(185000128, 1))) {
            analyzeOpening(player);
        } else if (npcId == 831373 && (player.getInventory().decreaseByItemId(185000129, 3) || player.getInventory().decreaseByItemId(185000128, 3))) {
            analyzeOpening(player);
        } else {
            if (npcId == 831373) {
                PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "I could use 3 keys to open this box", ChatType.NORMAL), true);
            } else {
                PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "I could use 1 key to open this box", ChatType.NORMAL), true);
            }
            PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401587));
        }
    }

    private void analyzeOpening(Player player) {
        if (getOwner().isInState(CreatureState.DEAD)) {
            AuditLogger.info(player, "Attempted multiple Chest looting!");
            return;
        }

        AI2Actions.dieSilently(this, player);
        Collection<Player> players = new HashSet<Player>();
        if (player.isInGroup2()) {
            for (Player member : player.getPlayerGroup2().getOnlineMembers()) {
                if (MathUtil.isIn3dRange(member, getOwner(), GroupConfig.GROUP_MAX_DISTANCE)) {
                    players.add(member);
                }
            }
        } else if (player.isInAlliance2()) {
            for (Player member : player.getPlayerAlliance2().getOnlineMembers()) {
                if (MathUtil.isIn3dRange(member, getOwner(), GroupConfig.GROUP_MAX_DISTANCE)) {
                    players.add(member);
                }
            }
        } else {
            players.add(player);
        }
        DropRegistrationService.getInstance().registerDrop(getOwner(), player, maxFrom(players).getLevel(), players);
        DropService.getInstance().requestDropList(player, getObjectId());
    }
}
