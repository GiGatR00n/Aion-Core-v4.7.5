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
package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.configs.main.LegionConfig;
import com.aionemu.gameserver.model.stats.container.StatEnum;

/**
 * @author antness
 */
public enum RewardType {

    AP_PLAYER {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
            return (long) (reward * player.getRates().getApPlayerGainRate() * statRate);
        }
    },
    AP_NPC {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.AP_BOOST, 100).getCurrent() / 100f;
            return (long) (reward * player.getRates().getApNpcRate() * statRate);
        }
    },
    GP_NPC {
    	@Override
        public long calcReward(Player player, long reward) {
            return (long) (reward * player.getRates().getGpNpcRate());
        }
    },
    HUNTING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_HUNTING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getXpRate() * statRate + legionOnlineBonus);
        }
    },
    GROUP_HUNTING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_GROUP_HUNTING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getGroupXpRate() * statRate + legionOnlineBonus);
        }
    },
    PVP_KILL {
        @Override
        public long calcReward(Player player, long reward) {
            return (reward);
        }
    },
    QUEST {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_QUEST_XP_RATE, 100).getCurrent() / 100f;
            return (long) (reward * player.getRates().getQuestXpRate() * statRate);
        }
    },
    CRAFTING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_CRAFTING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getCraftingXPRate() * statRate + legionOnlineBonus);
        }
    },
    GATHERING {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_GATHERING_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getGatheringXPRate() * statRate + legionOnlineBonus);
        }
    },
    USEITEM {
        @Override
        public long calcReward(Player player, long reward) {
            float statRate = player.getGameStats().getStat(StatEnum.BOOST_QUEST_XP_RATE, 100).getCurrent() / 100f;
            long legionOnlineBonus = 0;
            if (player.isLegionMember() && player.getLegion().getOnlineMembersCount() >= LegionConfig.LEGION_BUFF_REQUIRED_MEMBERS) {
                legionOnlineBonus = (long) (reward * player.getRates().getXpRate() * statRate) / 100 * 10;
            }
            return (long) (reward * player.getRates().getQuestXpRate() * statRate + legionOnlineBonus);
        }
    };

    public abstract long calcReward(Player player, long reward);
}
