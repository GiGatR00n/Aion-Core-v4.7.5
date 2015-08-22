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
package com.aionemu.gameserver.services.siegeservice;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.callbacks.util.GlobalCallbackHelper;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.LoggingConfig;
import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dao.SiegeDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.siege.SiegeModType;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.model.siege.SourceLocation;
import com.aionemu.gameserver.model.templates.siegelocation.SiegeReward;
import com.aionemu.gameserver.services.mail.AbyssSiegeLevel;
import com.aionemu.gameserver.services.mail.MailFormatter;
import com.aionemu.gameserver.services.mail.SiegeResult;
import com.aionemu.gameserver.services.player.PlayerService;
import com.google.common.collect.Lists;

/**
 * @author Source
 */
public class SourceSiege extends Siege<SourceLocation> {

    private static final Logger log = LoggerFactory.getLogger("SIEGE_LOG");
    private final AbyssPointsListener addAPListener = new AbyssPointsListener(this);

    public SourceSiege(SourceLocation siegeLocation) {
        super(siegeLocation);
    }

    @Override
    protected void onSiegeStart() {
        if (LoggingConfig.LOG_SIEGE) {
            log.info("[SIEGE] > Siege started. [SOURCE:" + getSiegeLocationId() + "] [RACE: " + getSiegeLocation().getRace() + "] [LegionId:" + getSiegeLocation().getLegionId() + "]");
        }
        getSiegeLocation().setPreparation(false);
        getSiegeLocation().setVulnerable(true);
        getSiegeLocation().setUnderShield(true);
        broadcastState(getSiegeLocation());
        GlobalCallbackHelper.addCallback(addAPListener);
        deSpawnNpcs(getSiegeLocationId());
        spawnNpcs(getSiegeLocationId(), getSiegeLocation().getRace(), SiegeModType.SIEGE);
        initSiegeBoss();
    }

    @Override
    protected void onSiegeFinish() {
        if (LoggingConfig.LOG_SIEGE) {
            SiegeRaceCounter winner = getSiegeCounter().getWinnerRaceCounter();
            if (winner != null) {
                log.info("[SIEGE] > Siege finished. [SOURCE:" + getSiegeLocationId() + "] [OLD RACE: " + getSiegeLocation().getRace() + "] [OLD LegionId:" + getSiegeLocation().getLegionId() + "] [NEW RACE: " + winner.getSiegeRace() + "] [NEW LegionId:" + (winner.getWinnerLegionId() == null ? 0 : winner.getWinnerLegionId()) + "]");
            } else {
                log.info("[SIEGE] > Siege finished. No winner found [SOURCE:" + getSiegeLocationId() + "] [RACE: " + getSiegeLocation().getRace() + "] [LegionId:" + getSiegeLocation().getLegionId() + "]");
            }
        }
        GlobalCallbackHelper.removeCallback(addAPListener);
        unregisterSiegeBossListeners();
        deSpawnNpcs(getSiegeLocationId());
        getSiegeLocation().setVulnerable(false);
        getSiegeLocation().setUnderShield(false);
        if (isBossKilled()) {
            onCapture();
            broadcastUpdate(getSiegeLocation(), getSiegeLocation().getTemplate().getNameId());
        } else {
            broadcastState(getSiegeLocation());
        }
        spawnNpcs(getSiegeLocationId(), getSiegeLocation().getRace(), SiegeModType.PEACE);
        if (SiegeRace.BALAUR != getSiegeLocation().getRace()) {
            giveRewardsToPlayers(getSiegeCounter().getRaceCounter(getSiegeLocation().getRace()));
        }
        DAOManager.getDAO(SiegeDAO.class).updateSiegeLocation(getSiegeLocation());
        updateTiamarantaRiftsStatus(false, false);
    }

    @Override
    public boolean isEndless() {
        return false;
    }

    @Override
    public void addAbyssPoints(Player player, int abysPoints) {
        getSiegeCounter().addAbyssPoints(player, abysPoints);
    }

    @Override
    public void addGloryPoints(Player player, int gloryPoints) {
        getSiegeCounter().addGloryPoints(player, gloryPoints);
    }

    public void onCapture() {
        SiegeRaceCounter winner = getSiegeCounter().getWinnerRaceCounter();
        getSiegeLocation().setRace(winner.getSiegeRace());
        // If new race is balaur
        if (SiegeRace.BALAUR == winner.getSiegeRace()) {
            getSiegeLocation().setLegionId(0);
        } else {
            Integer topLegionId = winner.getWinnerLegionId();
            getSiegeLocation().setLegionId(topLegionId != null ? topLegionId : 0);
        }
    }

    protected void giveRewardsToPlayers(SiegeRaceCounter winnerDamage) {
        // Get the map with playerId to siege reward
        Map<Integer, Long> playerAbyssPoints = winnerDamage.getPlayerAbyssPoints();
        List<Integer> topPlayersIds = Lists.newArrayList(playerAbyssPoints.keySet());
        Map<Integer, String> playerNames = PlayerService.getPlayerNames(playerAbyssPoints.keySet());

        // Black Magic Here :)
        int i = 0;
        List<SiegeReward> playerRewards = getSiegeLocation().getReward();
        int rewardLevel = 0;
        for (SiegeReward topGrade : playerRewards) {
            AbyssSiegeLevel level = AbyssSiegeLevel.getLevelById(++rewardLevel);
            for (int rewardedPC = 0; i < topPlayersIds.size() && rewardedPC < topGrade.getTop(); ++i) {
                Integer playerId = topPlayersIds.get(i);
                PlayerCommonData pcd = DAOManager.getDAO(PlayerDAO.class).loadPlayerCommonData(playerId);
                ++rewardedPC;
                if (LoggingConfig.LOG_SIEGE) {
                    log.info("[SIEGE]  > [SOURCE:" + getSiegeLocationId() + "] [RACE: " + getSiegeLocation().getRace() + "] Player Reward to: " + playerNames.get(playerId) + "] ITEM RETURN " + topGrade.getItemId() + " ITEM COUNT " + topGrade.getCount() * SiegeConfig.SIEGE_MEDAL_RATE);
                }
                MailFormatter.sendAbyssRewardMail(getSiegeLocation(), pcd, level, SiegeResult.OCCUPY, System.currentTimeMillis(), topGrade.getItemId(), topGrade.getCount() * SiegeConfig.SIEGE_MEDAL_RATE, 0);
            }
        }
    }
}
