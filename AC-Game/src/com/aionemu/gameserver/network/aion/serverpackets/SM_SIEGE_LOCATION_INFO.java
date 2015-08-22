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

import java.util.Map;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.model.team.legion.LegionEmblem;
import com.aionemu.gameserver.model.team.legion.LegionEmblemType;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.services.SiegeService;

/**
 * @author Sarynth
 */
public class SM_SIEGE_LOCATION_INFO extends AionServerPacket {

    /**
     * infoType 0 - reset 1 - update
     */
    private int infoType;
    private Map<Integer, SiegeLocation> locations;
    private static final Logger log = LoggerFactory.getLogger(SM_SIEGE_LOCATION_INFO.class);

    public SM_SIEGE_LOCATION_INFO() {
        this.infoType = 0;
        locations = SiegeService.getInstance().getSiegeLocations();
    }

    public SM_SIEGE_LOCATION_INFO(SiegeLocation loc) {
        this.infoType = 1;
        locations = new FastMap<Integer, SiegeLocation>();
        locations.put(loc.getLocationId(), loc);
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        Player player = con.getActivePlayer();
        if (!SiegeConfig.SIEGE_ENABLED) {
            writeC(0);
            writeH(0);
            return;
        }

        writeC(infoType);
        writeH(locations.size());

        for (SiegeLocation loc : locations.values()) {
            LegionEmblem emblem = new LegionEmblem();
            writeD(loc.getLocationId());

            int legionId = loc.getLegionId();
            writeD(legionId);

            if (legionId != 0) {
                if (LegionService.getInstance().getLegion(legionId) == null) {
                    log.error("Can't find or load legion with id " + legionId);
                } else {
                    emblem = LegionService.getInstance().getLegion(legionId).getLegionEmblem();
                }
            }

            if (emblem.getEmblemType() == LegionEmblemType.DEFAULT) {
                writeD(emblem.getEmblemId());
                writeC(255);
                writeC(emblem.getColor_r());
                writeC(emblem.getColor_g());
                writeC(emblem.getColor_b());
            } else {
                writeD(emblem.getCustomEmblemData().length);
                writeC(255);
                writeC(emblem.getColor_r());
                writeC(emblem.getColor_g());
                writeC(emblem.getColor_b());
            }

            writeC(loc.getRace().getRaceId());

            // is vulnerable (0 - no, 2 - yes)
            writeC(loc.isVulnerable() ? 2 : 0);

            // faction can teleport (0 - no, 1 - yes)
            writeC(loc.isCanTeleport(player) ? 1 : 0);

            // Next State (0 - invulnerable, 1 - vulnerable)
            writeC(loc.getNextState());

            writeH(0); // unk
            writeH(1);
            switch (loc.getLocationId()) {
                case 2111: // veille timer
                case 3111: // mastarius timer
                    writeD(SiegeService.getInstance().getRemainingSiegeTimeInSeconds(loc.getLocationId()));
                    break;
                default:
                    writeD(10000);
                    break;
            }
            writeD(0);//unk 4.7
            writeD(67);//unk 4.7
            writeD(0);//unk 4.7
        }
    }
}
