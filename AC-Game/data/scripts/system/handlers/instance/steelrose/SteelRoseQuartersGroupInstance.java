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
package instance.steelrose;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.Set;

/**
 * @author Eloann
 */
@InstanceID(301040000)
public class SteelRoseQuartersGroupInstance extends GeneralInstanceHandler {

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        int npcId = 230724;
        if (Rnd.get(1, 4) == 1) { // Nerukiki The Timid
            spawn(npcId, 517.2308f, 506.71335f, 951.7029f, (byte) 60);
        } else if (Rnd.get(1, 4) == 2) {
            spawn(npcId, 524.70435f, 519.89136f, 952.4762f, (byte) 59);
        } else if (Rnd.get(1, 4) == 3) {
            spawn(npcId, 473.1518f, 575.4152f, 958.0783f, (byte) 90);
        } else {
            spawn(npcId, 461.933350f, 510.545654f, 877.618103f, (byte) 90);
        }
    }

    @Override
    public void onDie(Npc npc) {
        int npcId = npc.getNpcId();

        switch (npcId) {
            case 230740: // Accountant Kanerunerk
                spawn(730766, 734.18994f, 484.61578f, 941.70868f, (byte) 0, 61); // Hidden Passage
                break;
        }
    }

    public void onDropRegistered(Npc npc) {
        Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
        int npcId = npc.getNpcId();
        switch (npcId) {
            case 701815: // Treasure Box
            case 701826: // Suspicious Water Vessel
            case 701827: // Suspicious Water Vessel
                switch (Rnd.get(1, 8)) {
                    case 1:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050009, 2)); // Steel Rose Idian: Physical Attack
                        break;
                    case 2:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050010, 2)); // Steel Rose Idian: Magical Attack
                        break;
                    case 3:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050011, 2)); // Steel Rose Idian: Physical Defense
                        break;
                    case 4:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050012, 2)); // Steel Rose Idian: Magical Defense
                        break;
                    case 5:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050013, 2)); // Glossy Steel Rose Idian: Physical Attack
                        break;
                    case 6:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050014, 2)); // Glossy Steel Rose Idian: Magical Attack
                        break;
                    case 7:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050015, 2)); // Glossy Steel Rose Idian: Physical Defense
                        break;
                    case 8:
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 166050016, 2)); // Glossy Steel Rose Idian: Magical Defense
                        break;
                }
                break;
        }
    }

    @Override
    public void onPlayerLogOut(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}
