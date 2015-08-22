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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.common.legacy.LootDistribution;
import com.aionemu.gameserver.model.team2.common.legacy.LootGroupRules;
import com.aionemu.gameserver.model.team2.common.legacy.LootRuleType;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

/**
 * @author Lyahim, Simple, xTz
 */
public class CM_DISTRIBUTION_SETTINGS extends AionClientPacket {

    @SuppressWarnings("unused")
    private int unk1;
    private int lootrul;
    private int misc;
    private LootRuleType lootrules;
    private LootDistribution autodistribution;
    private int common_item_above;
    private int superior_item_above;
    private int heroic_item_above;
    private int fabled_item_above;
    private int ethernal_item_above;
    @SuppressWarnings("unused")
    private int unk2;
    private int autodistr;

    public CM_DISTRIBUTION_SETTINGS(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        unk1 = readD();
        lootrul = readD();
        switch (lootrul) {
            case 0:
                lootrules = LootRuleType.FREEFORALL;
                break;
            case 1:
                lootrules = LootRuleType.ROUNDROBIN;
                break;
            case 2:
                lootrules = LootRuleType.LEADER;
                break;
            default:
                lootrules = LootRuleType.FREEFORALL;
                break;
        }
        misc = readD();
        common_item_above = readD();
        superior_item_above = readD();
        heroic_item_above = readD();
        fabled_item_above = readD();
        ethernal_item_above = readD();
        autodistr = readD();
        unk2 = readD();

        switch (autodistr) {
            case 0:
                autodistribution = LootDistribution.NORMAL;
                break;
            case 2:
                autodistribution = LootDistribution.ROLL_DICE;
                break;
            case 3:
                autodistribution = LootDistribution.BID;
                break;
            default:
                autodistribution = LootDistribution.NORMAL;
                break;
        }
    }

    @Override
    protected void runImpl() {
        Player leader = getConnection().getActivePlayer();

        PlayerGroup group = leader.getPlayerGroup2();
        if (group != null) {
            PlayerGroupService.changeGroupRules(group, new LootGroupRules(lootrules, autodistribution, common_item_above,
                    superior_item_above, heroic_item_above, fabled_item_above, ethernal_item_above, misc));
        }
        com.aionemu.gameserver.model.team2.alliance.PlayerAlliance alliance = leader.getPlayerAlliance2();
        if (alliance != null) {
            PlayerAllianceService.changeGroupRules(alliance, new LootGroupRules(lootrules, autodistribution,
                    common_item_above, superior_item_above, heroic_item_above, fabled_item_above, ethernal_item_above, misc));
        }
    }
}
