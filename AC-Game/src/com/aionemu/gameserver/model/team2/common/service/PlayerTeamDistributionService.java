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
package com.aionemu.gameserver.model.team2.common.service;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RewardType;
import com.aionemu.gameserver.model.gameobjects.player.XPCape;
import com.aionemu.gameserver.model.team2.TemporaryPlayerTeam;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import com.google.common.base.Predicate;

/**
 * @author ATracer, nrg
 */
public class PlayerTeamDistributionService {

    /**
     * This method will send a reward if a player is in a team
     */
    public static void doReward(TemporaryPlayerTeam<?> team, float damagePercent, Npc owner, AionObject winner) {
        if (team == null || owner == null) {
            return;
        }

        // Find team's members and determine highest level
        PlayerTeamRewardStats filteredStats = new PlayerTeamRewardStats(owner);
        team.applyOnMembers(filteredStats);

        // All are dead or not nearby
        if (filteredStats.players.isEmpty() || !filteredStats.hasLivingPlayer) {
            return;
        }

        // Reward mode
        long expReward;
        if (filteredStats.players.size() + filteredStats.mentorCount == 1) {
            expReward = (long) (StatFunctions.calculateSoloExperienceReward(filteredStats.players.get(0), owner));
        } else {
            expReward = (long) (StatFunctions.calculateGroupExperienceReward(filteredStats.highestLevel, owner));
        }

        // Party Bonus 2 members 10%, 3 members 20% ... 6 members 50%
        int size = filteredStats.players.size();
        int bonus = 100;
        if (size > 1) {
            bonus = 150 + (size - 2) * 10;
        }

        for (Player member : filteredStats.players) {
            Player partner = member.findPartner();
            //mentor and dead players shouldn't receive AP/EP/DP
            if (member.isMentor() || member.getLifeStats().isAlreadyDead()) {
                continue;
            }

            // Reward init
            long rewardXp = (long) (expReward * bonus * member.getLevel()) / (filteredStats.partyLvlSum * 100);
            int rewardDp = StatFunctions.calculateGroupDPReward(member, owner);
            float rewardAp = 1;

            // Players 10 levels below highest member get 0 reward.
            if (filteredStats.highestLevel - member.getLevel() >= 10) {
                rewardXp = 0;
                rewardDp = 0;
            } else if (filteredStats.mentorCount > 0) {
                int cape = XPCape.values()[(int) member.getLevel()].value();
                if (cape < rewardXp) {
                    rewardXp = cape;
                }
            }

            // Dmg percent correction
            rewardXp *= damagePercent;
            rewardDp *= damagePercent;
            rewardAp *= damagePercent;

            if (member.isMarried() && member.getPlayerGroup2().getMembers() == partner && member.getPlayerGroup2().getMembers().size() == 2) {
                member.getCommonData().addExp(rewardXp + (rewardXp * 20 / 100), RewardType.GROUP_HUNTING, owner.getObjectTemplate().getNameId());
                member.getCommonData().addEventExp(rewardXp + (rewardXp * 20 / 100));
            } else {
                member.getCommonData().addExp(rewardXp, RewardType.GROUP_HUNTING, owner.getObjectTemplate().getNameId());
                member.getCommonData().addEventExp(rewardXp);
            }

            // DP reward
            member.getCommonData().addDp(rewardDp);

            // AP reward
            if (owner.isRewardAP() && !(filteredStats.mentorCount > 0 && CustomConfig.MENTOR_GROUP_AP)) {
                rewardAp *= StatFunctions.calculatePvEApGained(member, owner);
                int ap = (int) rewardAp / filteredStats.players.size();
                if (ap >= 1) {
                    AbyssPointsService.addAp(member, owner, ap);
                }
            }
        }

        // Give Drop
        Player mostDamagePlayer = owner.getAggroList().getMostPlayerDamageOfMembers(team.getMembers(), filteredStats.highestLevel);
        if (mostDamagePlayer == null) {
            return;
        }

        if (winner.equals(team) && (!owner.getAi2().getName().equals("chest") || filteredStats.mentorCount == 0)) {
            DropRegistrationService.getInstance().registerDrop(owner, mostDamagePlayer, filteredStats.highestLevel, filteredStats.players);
        }
    }

    private static class PlayerTeamRewardStats implements Predicate<Player> {

        final List<Player> players = new ArrayList<Player>();
        int partyLvlSum = 0;
        int highestLevel = 0;
        int mentorCount = 0;
        boolean hasLivingPlayer = false;
        Npc owner;

        public PlayerTeamRewardStats(Npc owner) {
            this.owner = owner;
        }

        @Override
        public boolean apply(Player member) {
            if (member.isOnline()) {
                if (MathUtil.isIn3dRange(member, owner, GroupConfig.GROUP_MAX_DISTANCE)) {
                    QuestEngine.getInstance().onKill(new QuestEnv(owner, member, 0, 0));

                    if (member.isMentor()) {
                        mentorCount++;
                        return true;
                    }

                    if (!hasLivingPlayer && !member.getLifeStats().isAlreadyDead()) {
                        hasLivingPlayer = true;
                    }

                    players.add(member);
                    partyLvlSum += member.getLevel();
                    if (member.getLevel() > highestLevel) {
                        highestLevel = member.getLevel();
                    }
                }
            }
            return true;
        }
    }
}
