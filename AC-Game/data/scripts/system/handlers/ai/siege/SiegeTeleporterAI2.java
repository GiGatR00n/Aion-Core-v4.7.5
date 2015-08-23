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
package ai.siege;

import ai.GeneralNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.siege.SiegeNpc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_ARTIFACT_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORTRESS_INFO;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Source
 */
@AIName("siege_teleporter")
public class SiegeTeleporterAI2 extends GeneralNpcAI2 {

    boolean isArtifact = false;

    @Override
    protected void handleDespawned() {
        canTeleport(false);
        super.handleDespawned();
    }

    @Override
    protected void handleSpawned() {
        canTeleport(true);
        super.handleSpawned();
    }

    private void canTeleport(final boolean status) {
        final int id = ((SiegeNpc) getOwner()).getSiegeId();
        try {
            SiegeService.getInstance().getFortress(id).setCanTeleport(status);
        } catch (@SuppressWarnings("unused") Exception e) {
            SiegeService.getInstance().getArtifact(id).setCanTeleport(status);
        }


        getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                if (isArtifact) {
                    PacketSendUtility.sendPacket(player, new SM_ABYSS_ARTIFACT_INFO(id, status));
                } else {
                    PacketSendUtility.sendPacket(player, new SM_FORTRESS_INFO(id, status));
                }
            }
        });
    }
}
