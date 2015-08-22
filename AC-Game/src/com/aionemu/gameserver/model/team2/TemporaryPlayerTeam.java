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
package com.aionemu.gameserver.model.team2;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.common.legacy.LootGroupRules;
import com.aionemu.gameserver.model.team2.common.legacy.LootRuleType;
import com.aionemu.gameserver.model.team2.group.PlayerFilters;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PET;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import java.util.Collection;

/**
 * @author ATracer
 */
public abstract class TemporaryPlayerTeam<TM extends TeamMember<Player>> extends GeneralTeam<Player, TM> {

    private LootGroupRules lootGroupRules = new LootGroupRules();

    public TemporaryPlayerTeam(Integer objId) {
        super(objId);
    }

    /**
     * Level of the player with lowest exp
     */
    public abstract int getMinExpPlayerLevel();

    /**
     * Level of the player with highest exp
     */
    public abstract int getMaxExpPlayerLevel();

    @Override
    public Race getRace() {
        return getLeader().getObject().getRace();
    }

    @Override
    public void sendPacket(AionServerPacket packet) {
        applyOnMembers(new TeamMessageSender(packet, Predicates.<Player>alwaysTrue()));
    }

    @Override
    public void sendPacket(AionServerPacket packet, Predicate<Player> predicate) {
        applyOnMembers(new TeamMessageSender(packet, predicate));
    }

    @Override
    public final int onlineMembers() {
        return getOnlineMembers().size();
    }

    @Override
    public final Collection<Player> getOnlineMembers() {
        return filterMembers(PlayerFilters.ONLINE);
    }

    protected final void initializeTeam(TM leader) {
        setLeader(leader);
    }

    public final LootGroupRules getLootGroupRules() {
        return lootGroupRules;
    }

    public void setLootGroupRules(LootGroupRules lootGroupRules) {
        this.lootGroupRules = lootGroupRules;
        if (lootGroupRules != null && lootGroupRules.getLootRule() == LootRuleType.FREEFORALL) {
            applyOnMembers(new TeamPacketGroupSender(PlayerFilters.HAS_LOOT_PET,
                    SM_SYSTEM_MESSAGE.STR_MSG_LOOTING_PET_MESSAGE03, new SM_PET(13, false)));
        }
    }

    public static final class TeamPacketGroupSender implements Predicate<Player> {

        private final AionServerPacket[] packets;
        private final Predicate<Player> predicate;

        public TeamPacketGroupSender(Predicate<Player> predicate, AionServerPacket... packets) {
            this.packets = packets;
            this.predicate = predicate;
        }

        @Override
        public boolean apply(Player player) {
            if (predicate.apply(player)) {
                for (AionServerPacket packet : packets) {
                    PacketSendUtility.sendPacket(player, packet);
                }
            }
            return true;
        }
    }

    public static final class TeamMessageSender implements Predicate<Player> {

        private final AionServerPacket packet;
        private final Predicate<Player> predicate;

        public TeamMessageSender(AionServerPacket packet, Predicate<Player> predicate) {
            this.packet = packet;
            this.predicate = predicate;
        }

        @Override
        public boolean apply(Player player) {
            if (predicate.apply(player)) {
                PacketSendUtility.sendPacket(player, packet);
            }
            return true;
        }
    }
}
