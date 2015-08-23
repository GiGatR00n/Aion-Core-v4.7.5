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

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * @rework Eloann
 */
public class Eye extends AdminCommand {

    public Eye() {
        super("eye");
    }

    @Override
    public void execute(Player player, String... params) {
        if (params.length != 0) {
            onFail(player, null);
            return;
        }
        if (player.isAttackMode()) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_USE_WHILE_FIGHT));
            return;
        }
        if (player.getPosition().getMapId() == WorldMapType.TIAMARANTA_EYE_2.getId()) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.NOT_USE_ON_PVP_MAP));
            return;
        }
        if (player.getLevel() > 1 && player.getLevel() < 55) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.LEVEL_TOO_LOW));
            return;
        }

        if (player.getRace() == Race.ELYOS && !player.isInPrison()) {
            goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 753, 134, 1196);
        } else if (player.getRace() == Race.ASMODIANS && !player.isInPrison()) {
            goTo(player, WorldMapType.TIAMARANTA_EYE_2.getId(), 754, 1459, 1196);
        }
    }

    private static void goTo(final Player player, int worldId, float x, float y, float z) {
        WorldMap destinationMap = World.getInstance().getWorldMap(worldId);
        if (destinationMap.isInstanceType()) {
            TeleportService2.teleportTo(player, worldId, getInstanceId(worldId, player), x, y, z);
        } else {
            TeleportService2.teleportTo(player, worldId, x, y, z);
        }
    }

    private static int getInstanceId(int worldId, Player player) {
        if (player.getWorldId() == worldId) {
            WorldMapInstance registeredInstance = InstanceService.getRegisteredInstance(worldId, player.getObjectId());
            if (registeredInstance != null) {
                return registeredInstance.getInstanceId();
            }
        }
        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(worldId);
        InstanceService.registerPlayerWithInstance(newInstance, player);
        return newInstance.getInstanceId();
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax: .eye");
    }
}
