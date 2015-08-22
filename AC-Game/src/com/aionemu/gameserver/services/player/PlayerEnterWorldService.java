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
package com.aionemu.gameserver.services.player;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javolution.util.FastList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.main.AutoGroupConfig;
import com.aionemu.gameserver.configs.main.CraftConfig;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.configs.main.FastTrackConfig;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.configs.main.HTMLConfig;
import com.aionemu.gameserver.configs.main.MembershipConfig;
import com.aionemu.gameserver.configs.main.PeriodicSaveConfig;
import com.aionemu.gameserver.configs.main.RateConfig;
import com.aionemu.gameserver.configs.main.SecurityConfig;
import com.aionemu.gameserver.dao.AbyssRankDAO;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.ItemStoneListDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dao.PlayerPasskeyDAO;
import com.aionemu.gameserver.dao.PlayerPunishmentsDAO;
import com.aionemu.gameserver.dao.PlayerQuestListDAO;
import com.aionemu.gameserver.dao.PlayerSkillListDAO;
import com.aionemu.gameserver.dao.WeddingDAO;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.CharacterBanInfo;
import com.aionemu.gameserver.model.account.CharacterPasskey.ConnectType;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.HouseObject;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.player.emotion.Emotion;
import com.aionemu.gameserver.model.gameobjects.player.motion.Motion;
import com.aionemu.gameserver.model.gameobjects.player.title.Title;
import com.aionemu.gameserver.model.gameobjects.state.CreatureSeeState;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.items.storage.IStorage;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.model.skill.PlayerSkillEntry;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.serverpackets.SM_A6_UNK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AFTER_TIME_CHECK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BE_UNK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHANNEL_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHARACTER_SELECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ENTER_WORLD_CHECK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FC_UNK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GAME_TIME;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOTSPOT_TELEPORT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INSTANCE_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MACRO_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PACKAGE_INFO_NOTIFY;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SPAWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PRICES;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_COMPLETED_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RECIPE_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_REQUEST_BEGINNER_SERVER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UI_SETTINGS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UNK_104;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.AutoGroupService;
import com.aionemu.gameserver.services.BrokerService;
import com.aionemu.gameserver.services.ClassChangeService;
import com.aionemu.gameserver.services.DisputeLandService;
import com.aionemu.gameserver.services.EventService;
import com.aionemu.gameserver.services.FastTrackService;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.services.HousingService;
import com.aionemu.gameserver.services.KiskService;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.services.LoginEventService;
import com.aionemu.gameserver.services.PetitionService;
import com.aionemu.gameserver.services.PunishmentService;
import com.aionemu.gameserver.services.PunishmentService.PunishmentType;
import com.aionemu.gameserver.services.SerialKillerService;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.services.StigmaService;
import com.aionemu.gameserver.services.SurveyService;
import com.aionemu.gameserver.services.VortexService;
import com.aionemu.gameserver.services.abyss.AbyssSkillService;
import com.aionemu.gameserver.services.craft.RelinquishCraftStatus;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.mail.MailService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.services.toypet.PetService;
import com.aionemu.gameserver.services.transfers.PlayerTransferService;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.taskmanager.tasks.ExpireTimerTask;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.audit.AuditLogger;
import com.aionemu.gameserver.utils.collections.ListSplitter;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import com.aionemu.gameserver.utils.rates.Rates;
import com.aionemu.gameserver.world.World;


/**
 * @author ATracer
 * @author GiGatR00n v4.7.5.x
 *
 */

/**
 * <tt>GameServer Presentation Settings</tt>
 * This are only the main server information settings placeholders.
 * You can customise your server if you wish, by editing the "CustomMessageId.java" file, located in the i18n folder
 * You can also setup an individual language file, located in languages folder
 * <p/>
 * <p/>
 * Please do not change here something, ragarding the developer credits, except
 * the "developed by XXXX". Even if you edit a lot of files in this source, you
 * still have no rights to call it as "your Core". Everybody knows that this
 * Emulator Core was developed by Aion Lightning
 */

public final class PlayerEnterWorldService {

    private static final Logger log = LoggerFactory.getLogger("GAMECONNECTION_LOG");
    
    private static final Set<Integer> pendingEnterWorld = new HashSet<Integer>();
    
    private static final String alInfo; 
    private static final String serverInfo;
    private static final String serverName = "Welcome to " + GSConfig.SERVER_NAME + "!";
    private static final String serverIntro = "Please remember: Accountsharing is not permitted";


    static {
        String infoBuffer = LanguageHandler.translate(CustomMessageId.HOMEPAGE) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.TEAMSPEAK) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.INFO1) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.INFO2) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.INFO3) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.INFO4) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.INFO5) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.INFO6) + "\n";
        infoBuffer = infoBuffer + LanguageHandler.translate(CustomMessageId.INFO7);

        String alBuffer = "=============================\n";
        alBuffer = alBuffer + "Core Developed by: \n";
        alBuffer = alBuffer + "->AionUnique\n";
        alBuffer = alBuffer + "->AionLightning\n";
        alBuffer = alBuffer + "->AionEngine\n";
        alBuffer = alBuffer + "->Aion-Extreme\n";
        alBuffer = alBuffer + "->NextGenAion\n";
        alBuffer = alBuffer + "->Aion-Core\n";
        alBuffer = alBuffer + "Copyright 2010-2015 by GiGatR00n.\n";
        alBuffer = alBuffer + "=============================\n";
        alBuffer = alBuffer +  "Server&Client Version:" + GSConfig.SERVER_VERSION + " .";
        alBuffer = alBuffer + "=============================\n";
        serverInfo = infoBuffer;
        alInfo = alBuffer;

        infoBuffer = null;
        alBuffer = null;
    }

    /**
     * @param objectId
     * @param client
     */
    private static final void validateAndEnterWorld(final int objectId, final AionConnection client) {
        synchronized (pendingEnterWorld) {
            if (pendingEnterWorld.contains(objectId)) {
                log.warn("Skipping enter world " + objectId);
                return;
            }
            pendingEnterWorld.add(objectId);
        }
        int delay = 0;
        // double checked enter world
        if (World.getInstance().findPlayer(objectId) != null) {
            delay = 15000;
            log.warn("Postponed enter world " + objectId);
        }
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Player player = World.getInstance().findPlayer(objectId);
                    if (player != null) {
                        AuditLogger.info(player, "Duplicate player in world");
                        client.close(new SM_QUIT_RESPONSE(), false);
                        return;
                    }
                    enterWorld(client, objectId);
                } catch (Throwable ex) {
                    log.error("Error during enter world " + objectId, ex);
                } finally {
                    synchronized (pendingEnterWorld) {
                        pendingEnterWorld.remove(objectId);
                    }
                }
            }
        }, delay);
    }    
    
    /**
     * @param objectId
     * @param client
     */
    private static final void showPasskey(final int objectId, final AionConnection client) {
        client.getAccount().getCharacterPasskey().setConnectType(ConnectType.ENTER);
        client.getAccount().getCharacterPasskey().setObjectId(objectId);
        boolean isExistPasskey = DAOManager.getDAO(PlayerPasskeyDAO.class).existCheckPlayerPasskey(client.getAccount().getId());

        if (!isExistPasskey) {
            client.sendPacket(new SM_CHARACTER_SELECT(0));
        } else {
            client.sendPacket(new SM_CHARACTER_SELECT(1));
        }
    }    
    
    /**
     * @param objectId
     * @param client
     */
    public static final void startEnterWorld(final int objectId, final AionConnection client) {
        // check if char is banned
        PlayerAccountData playerAccData = client.getAccount().getPlayerAccountData(objectId);
        Timestamp lastOnline = playerAccData.getPlayerCommonData().getLastOnline();
        if (lastOnline != null && client.getAccount().getAccessLevel() < AdminConfig.GM_LEVEL) {
            if (System.currentTimeMillis() - lastOnline.getTime() < (GSConfig.CHARACTER_REENTRY_TIME * 1000)) {
                client.sendPacket(new SM_ENTER_WORLD_CHECK((byte) 6)); // 20 sec time
                client.sendPacket(new SM_AFTER_TIME_CHECK());//TODO
                return;
            }
        }
        CharacterBanInfo cbi = client.getAccount().getPlayerAccountData(objectId).getCharBanInfo();
        if (cbi != null) {
            if (cbi.getEnd() > System.currentTimeMillis() / 1000) {
                client.close(new SM_QUIT_RESPONSE(), false);
                return;
            } else {
                DAOManager.getDAO(PlayerPunishmentsDAO.class).unpunishPlayer(objectId, PunishmentType.CHARBAN);
            }
        }
        // passkey check
        if (SecurityConfig.PASSKEY_ENABLE && !client.getAccount().getCharacterPasskey().isPass()) {
            showPasskey(objectId, client);
        } else {
            validateAndEnterWorld(objectId, client);
        }
    }

    /**
     * @param client
     * @param player
     */
    private static void sendItemInfos(AionConnection client, Player player) {
        // Cube size limit set in inventory.
        int questExpands = player.getQuestExpands();
        int npcExpands = player.getNpcExpands();
        player.getInventory().setLimit(StorageType.CUBE.getLimit() + (questExpands + npcExpands) * 9);
        player.getWarehouse().setLimit(StorageType.REGULAR_WAREHOUSE.getLimit() + player.getWarehouseSize() * 8);

        // items
        Storage inventory = player.getInventory();
        List<Item> allItems = new ArrayList<Item>();
        if (inventory.getKinah() == 0) inventory.increaseKinah(0); // create an empty object with value 0
        allItems.add(inventory.getKinahItem()); // always included even with 0 count, and first in the packet !
        allItems.addAll(player.getEquipment().getEquippedItems());
        allItems.addAll(inventory.getItems());
        
        boolean isFirst = true;
        ListSplitter<Item> splitter = new ListSplitter<Item>(allItems, 10);
        while (!splitter.isLast()) {
            client.sendPacket(new SM_INVENTORY_INFO(isFirst, splitter.getNext(), npcExpands, questExpands, false, player));
            isFirst = false;
        }

        client.sendPacket(new SM_INVENTORY_INFO(false, new ArrayList<Item>(0), npcExpands, questExpands, false, player));
        client.sendPacket(new SM_STATS_INFO(player));
        client.sendPacket(SM_CUBE_UPDATE.stigmaSlots(player.getCommonData().getAdvancedStigmaSlotSize()));
    }    
    
    /**
     * @param client
     * @param objectId
     */
    public static final void enterWorld(AionConnection client, int objectId) {
        Account account = client.getAccount();
        PlayerAccountData playerAccData = client.getAccount().getPlayerAccountData(objectId);

        if (playerAccData == null) {
            // Somebody wanted to login on character that is not at his account
            return;
        }
        Player player = PlayerService.getPlayer(objectId, account);

        if (player != null && client.setActivePlayer(player)) {
            player.setClientConnection(client);

            log.info("[MAC_AUDIT] Player " + player.getName() + " (account " + account.getName() + ") has entered world with " + client.getMacAddress() + " MAC.");
            World.getInstance().storeObject(player);

            StigmaService.onPlayerLogin(player);

            /**
             * Energy of Repose must be calculated before sending SM_STATS_INFO
             */
            if (playerAccData.getPlayerCommonData().getLastOnline() != null) {
                long lastOnline = playerAccData.getPlayerCommonData().getLastOnline().getTime();
                PlayerCommonData pcd = player.getCommonData();
                long secondsOffline = (System.currentTimeMillis() / 1000) - lastOnline / 1000;
                if (pcd.isReadyForSalvationPoints()) {
                    // 10 mins offline = 0 salvation points.
                    if (secondsOffline > 10 * 60) {
                        player.getCommonData().resetSalvationPoints();
                    }
                }
                if (pcd.isReadyForReposteEnergy()) {
                    pcd.updateMaxReposte();
                    // more than 4 hours offline = start counting Reposte Energy addition.
                    if (secondsOffline > 4 * 3600) {
                        double hours = secondsOffline / 3600d;
                        long maxRespose = player.getCommonData().getMaxReposteEnergy();
                        if (hours > 24) {
                            hours = 24;
                        }
                        // 24 hours offline = 100% Reposte Energy
                        long addResposeEnergy = (long) ((hours / 24) * maxRespose);

                        // Additional Energy of Repose bonus
                        // TODO: use player house zones
                        if (player.getHouseOwnerId() / 10000 * 10000 == player.getWorldId()) {
                            switch (player.getActiveHouse().getHouseType()) {
                                case STUDIO:
                                    addResposeEnergy *= 1.05f;
                                    break;
                                case MANSION:
                                    addResposeEnergy *= 1.08f;
                                    break;
                                case ESTATE:
                                    addResposeEnergy *= 1.15f;
                                    break;
                                case PALACE:
                                    addResposeEnergy *= 1.50f;
                                    break;
                                default:
                                    addResposeEnergy *= 1.10f;
                                    break;
                            }
                        }

                        pcd.addReposteEnergy(addResposeEnergy > maxRespose ? maxRespose : addResposeEnergy);
                    }
                }
                if (((System.currentTimeMillis() / 1000) - lastOnline) > 300) {
                    player.getCommonData().setDp(0);
                }
            }
            InstanceService.onPlayerLogin(player);
            client.sendPacket(new SM_REQUEST_BEGINNER_SERVER(1, 1, true));
            if (!player.getSkillList().isSkillPresent(3512)) {
                player.getSkillList().addSkill(player, 3512, 129);
            }
            // Update player skills first!!!
            AbyssSkillService.onEnterWorld(player);
            // TODO: check the split size
            client.sendPacket(new SM_SKILL_LIST(player, player.getSkillList().getBasicSkills()));
            for (PlayerSkillEntry stigmaSkill : player.getSkillList().getStigmaSkills()) {
                client.sendPacket(new SM_SKILL_LIST(player, stigmaSkill));
            }

            if (player.getSkillCoolDowns() != null) {
                client.sendPacket(new SM_SKILL_COOLDOWN(player.getSkillCoolDowns()));
            }

            if (player.getItemCoolDowns() != null) {
                client.sendPacket(new SM_ITEM_COOLDOWN(player.getItemCoolDowns()));
            }

            FastList<QuestState> questList = FastList.newInstance();
            FastList<QuestState> completeQuestList = FastList.newInstance();
            for (QuestState qs : player.getQuestStateList().getAllQuestState()) {
                if (qs.getStatus() == QuestStatus.NONE && qs.getCompleteCount() == 0) {
                    continue;
                }
                if (qs.getStatus() != QuestStatus.COMPLETE && qs.getStatus() != QuestStatus.NONE) {
                    questList.add(qs);
                }
                if (qs.getCompleteCount() > 0) {
                    completeQuestList.add(qs);
                }
            }
            client.sendPacket(new SM_QUEST_COMPLETED_LIST(completeQuestList));
            client.sendPacket(new SM_QUEST_LIST(questList));
            // Seems crazy but this is correct on official server [Patch 4.7]
            if (player.getLevel() == 1) {
                client.sendPacket(new SM_TITLE_INFO(2, 1));
                client.sendPacket(new SM_TITLE_INFO(7, 1));
            } else {
                client.sendPacket(new SM_TITLE_INFO(player.getCommonData().getTitleId()));
                client.sendPacket(new SM_TITLE_INFO(6, player.getCommonData().getBonusTitleId()));
            }
            client.sendPacket(new SM_MOTION(player.getMotions().getMotions().values()));
            client.sendPacket(new SM_FC_UNK());//TODO
            client.sendPacket(new SM_ENTER_WORLD_CHECK());
            if (FastTrackConfig.FASTTRACK_ENABLE) FastTrackService.getInstance().checkAuthorizationRequest(player);
            byte[] uiSettings = player.getPlayerSettings().getUiSettings();
            byte[] shortcuts = player.getPlayerSettings().getShortcuts();
            byte[] houseBuddies = player.getPlayerSettings().getHouseBuddies();

            if (uiSettings != null) client.sendPacket(new SM_UI_SETTINGS(uiSettings, 0));
            if (shortcuts != null) client.sendPacket(new SM_UI_SETTINGS(shortcuts, 1));
            if (houseBuddies != null) client.sendPacket(new SM_UI_SETTINGS(houseBuddies, 2));

            sendItemInfos(client, player);
            playerLoggedIn(player);

            client.sendPacket(new SM_INSTANCE_INFO(player, false, player.getCurrentTeam()));
            client.sendPacket(new SM_CHANNEL_INFO(player.getPosition()));

            KiskService.getInstance().onLogin(player);
            TeleportService2.sendSetBindPoint(player);
            
            // Without player spawn initialization can't access to his mapRegion for chk below
            World.getInstance().preSpawn(player);
            player.getController().validateLoginZone();
            VortexService.getInstance().validateLoginZone(player);

            client.sendPacket(new SM_PLAYER_SPAWN(player));

            // SM_WEATHER miss on login (but he 'live' in CM_LEVEL_READY.. need invistigate)
            client.sendPacket(new SM_GAME_TIME());

            SerialKillerService.getInstance().onLogin(player);

            if (player.isLegionMember()) LegionService.getInstance().onLogin(player);

            client.sendPacket(new SM_TITLE_INFO(player));
            client.sendPacket(new SM_EMOTION_LIST((byte) 0, player.getEmotions().getEmotions()));
            client.sendPacket(new SM_BE_UNK());//TODO

            // SM_INFLUENCE_RATIO, SM_SIEGE_LOCATION_INFO, SM_RIFT_ANNOUNCE (Balaurea), SM_RIFT_ANNOUNCE (Tiamaranta)
            SiegeService.getInstance().onPlayerLogin(player);

            client.sendPacket(new SM_PRICES());
            client.sendPacket(new SM_A6_UNK(1));//TODO
            client.sendPacket(new SM_A6_UNK(0));//TODO
            client.sendPacket(new SM_HOTSPOT_TELEPORT(0, 0));
            DisputeLandService.getInstance().onLogin(player);
            client.sendPacket(new SM_ABYSS_RANK(player.getAbyssRank()));
            
            if (CustomConfig.FATIGUE_SYSTEM_ENABLED) FatigueService.getInstance().onPlayerLogin(player);

            client.sendPacket(new SM_PACKAGE_INFO_NOTIFY(0));
            client.sendPacket(new SM_UNK_104());

            // Intro message
            PacketSendUtility.sendWhiteMessage(player, serverName);
            PacketSendUtility.sendYellowMessage(player, serverIntro);
            PacketSendUtility.sendBrightYellowMessage(player, serverInfo);
            PacketSendUtility.sendWhiteMessage(player, alInfo);
            if (player.isMarried()) {
            PacketSendUtility.sendYellowMessage(player, "You are married");
            }
			
            String serverMessage = null;
            String serverMessageRegular = null;
            String serverMessagePremium = null;
            String serverMessageVip = null;            
			String bufferDisplayRev = null;
					
			if (RateConfig.DISPLAY_RATE) {
			  String bufferRegular =  String.format(MembershipConfig.WELCOME_REGULAR, new Object[] { GSConfig.SERVER_NAME, (int) (RateConfig.XP_RATE), (int) (RateConfig.QUEST_XP_RATE), (int) (RateConfig.DROP_RATE)});
			  String bufferVip =  String.format(MembershipConfig.WELCOME_VIP, new Object[] { GSConfig.SERVER_NAME, (int) (RateConfig.VIP_XP_RATE), (int) (RateConfig.VIP_QUEST_XP_RATE), (int) (RateConfig.VIP_DROP_RATE)});
			  String bufferPremium =  String.format(MembershipConfig.WELCOME_PREMIUM, new Object[] { GSConfig.SERVER_NAME, (int) (RateConfig.PREMIUM_XP_RATE), (int) (RateConfig.PREMIUM_QUEST_XP_RATE), (int)
			  (RateConfig.PREMIUM_DROP_RATE)});

			  if (bufferDisplayRev != null) {
				  bufferRegular = bufferRegular + bufferDisplayRev;
				  bufferPremium = bufferPremium + bufferDisplayRev;
				  bufferVip = bufferVip + bufferDisplayRev;
			  }
			  
			  serverMessageRegular = bufferRegular;
			  bufferRegular = null;
			  
			  serverMessagePremium = bufferPremium;
			  bufferPremium = null;
			  
			  serverMessageVip = bufferVip;
			  bufferVip = null;
			  
			} else {
				
			  String buffer = LanguageHandler.translate(CustomMessageId.WELCOME_BASIC, new Object[] { GSConfig.SERVER_NAME });
			  
			  if (bufferDisplayRev != null) {
				  buffer = buffer + bufferDisplayRev;
			  }

			  serverMessage = buffer;
			  buffer = null;
			}
			
			if (serverMessage != null) {
				client.sendPacket(new SM_MESSAGE(0, null, serverMessage, ChatType.GOLDEN_YELLOW));
			} else if (client.getAccount().getMembership() == 1) {
				client.sendPacket(new SM_MESSAGE(0, null, serverMessagePremium, ChatType.GOLDEN_YELLOW));
			} else if (client.getAccount().getMembership() == 2) {
				client.sendPacket(new SM_MESSAGE(0, null, serverMessageVip, ChatType.GOLDEN_YELLOW));
			} else {
				client.sendPacket(new SM_MESSAGE(0, null, serverMessageRegular, ChatType.GOLDEN_YELLOW));
			}
			
            player.setRates(Rates.getRatesFor(client.getAccount().getMembership()));
            if (CustomConfig.PREMIUM_NOTIFY) {
                showPremiumAccountInfo(client, account);
            }

            if (player.isGM()) {
                if (AdminConfig.INVULNERABLE_GM_CONNECTION || AdminConfig.INVISIBLE_GM_CONNECTION
                        || AdminConfig.ENEMITY_MODE_GM_CONNECTION.equalsIgnoreCase("Neutral")
                        || AdminConfig.ENEMITY_MODE_GM_CONNECTION.equalsIgnoreCase("Enemy") || AdminConfig.VISION_GM_CONNECTION
                        || AdminConfig.WHISPER_GM_CONNECTION
						|| AdminConfig.GM_MODE_CONNECTION) {
                    PacketSendUtility.sendMessage(player, "=============================");
                    if (AdminConfig.INVULNERABLE_GM_CONNECTION) {
                        player.setInvul(true);
                        PacketSendUtility.sendMessage(player, ">> Invulnerable Mode : ON <<");
                    }
                    if (AdminConfig.INVISIBLE_GM_CONNECTION) {
                        player.getEffectController().setAbnormal(AbnormalState.HIDE.getId());
                        player.setVisualState(CreatureVisualState.HIDE20);
                        PacketSendUtility.broadcastPacket(player, new SM_PLAYER_STATE(player), true);
                        PacketSendUtility.sendMessage(player, ">> Invisible Mode : ON <<");
                    }
                    if (AdminConfig.ENEMITY_MODE_GM_CONNECTION.equalsIgnoreCase("Neutral")) {
                        player.setAdminNeutral(3);
                        player.setAdminEnmity(0);
                        PacketSendUtility.sendMessage(player, ">> Neutral Mode : ALL <<");
                    }
                    if (AdminConfig.ENEMITY_MODE_GM_CONNECTION.equalsIgnoreCase("Enemy")) {
                        player.setAdminNeutral(0);
                        player.setAdminEnmity(3);
                        PacketSendUtility.sendMessage(player, ">> Neutral Mode : ENNEMY <<");
                    }
                    if (AdminConfig.VISION_GM_CONNECTION) {
                        player.setSeeState(CreatureSeeState.SEARCH10);
                        PacketSendUtility.broadcastPacket(player, new SM_PLAYER_STATE(player), true);
                        PacketSendUtility.sendMessage(player, ">> Vision mode : ON <<");
                    }
                    if (AdminConfig.WHISPER_GM_CONNECTION) {
                        player.setUnWispable();
                        PacketSendUtility.sendMessage(player, ">> Whisper : OFF <<");
                    }
					if (AdminConfig.GM_MODE_CONNECTION) {
						player.setGmMode(true);
                        PacketSendUtility.sendMessage(player, ">> GM Mode : Enable <<");
                    }
                    PacketSendUtility.sendMessage(player, "=============================");
                }
            }

            // Special skill for gm
            if (player.getAccessLevel() >= AdminConfig.COMMAND_SPECIAL_SKILL) {
                FastList<Integer> gmSkill = FastList.newInstance();
                gmSkill.add(174);
                gmSkill.add(175);
                gmSkill.add(1904);
                gmSkill.add(1911);
                gmSkill.add(3224);
                gmSkill.add(3226);
                gmSkill.add(3227);
                gmSkill.add(3232);
                gmSkill.add(3233);
                gmSkill.add(3234);
                gmSkill.add(3235);
                gmSkill.add(3236);
                gmSkill.add(3237);
                gmSkill.add(3238);
                gmSkill.add(3239);
                gmSkill.add(3240);
                gmSkill.add(3241);
                for (FastList.Node<Integer> n = gmSkill.head(), end = gmSkill.tail(); (n = n.getNext()) != end; ) {
                    PlayerSkillEntry skill = new PlayerSkillEntry(n.getValue(), true, 1, PersistentState.NOACTION);
                    player.getSkillList().addStigmaSkill(player, skill.getSkillId(), skill.getSkillLevel());
                }
            }
            // Alliance Packet after SetBindPoint
            PlayerAllianceService.onPlayerLogin(player);

            if (player.isInPrison()) {
                PunishmentService.updatePrisonStatus(player);
            }

            if (player.isNotGatherable()) {
                PunishmentService.updateGatherableStatus(player);
            }

            PlayerGroupService.onPlayerLogin(player);
            PetService.getInstance().onPlayerLogin(player);

            MailService.getInstance().onPlayerLogin(player);
            LoginEventService.getInstance().onPlayerLogin(player);
            HousingService.getInstance().onPlayerLogin(player);
            BrokerService.getInstance().onPlayerLogin(player);
            sendMacroList(client, player);
            client.sendPacket(new SM_RECIPE_LIST(player.getRecipeList().getRecipeList()));

            PetitionService.getInstance().onPlayerLogin(player);
            
            if (AutoGroupConfig.AUTO_GROUP_ENABLE) AutoGroupService.getInstance().onPlayerLogin(player);

            ClassChangeService.showClassChangeDialog(player);
            //Homeward Bound Skill fix
            if (player.getActiveHouse() != null) {
                if (player.getSkillList().getSkillEntry(2670) != null || player.getSkillList().getSkillEntry(2671) != null) {
                    return;
                } else {
                    if (player.getRace() == Race.ASMODIANS) {
                        player.getSkillList().addSkill(player, 2671, 1);
                    } else if (player.getRace() == Race.ELYOS) {
                        player.getSkillList().addSkill(player, 2670, 1);
                    }
                }
            }

            /**
             * Trigger restore services on login.
             */
            player.getLifeStats().updateCurrentStats();

            if (HTMLConfig.ENABLE_HTML_WELCOME) {
                HTMLService.showHTML(player, HTMLCache.getInstance().getHTML("welcome.xhtml"));
            }

            player.getNpcFactions().sendDailyQuest();

            if (HTMLConfig.ENABLE_GUIDES) {
                HTMLService.onPlayerLogin(player);
            }

            for (StorageType st : StorageType.values()) {
                if (st == StorageType.LEGION_WAREHOUSE) {
                    continue;
                }
                IStorage storage = player.getStorage(st.getId());
                if (storage != null) {
                    for (Item item : storage.getItemsWithKinah()) {
                        if (item.getExpireTime() > 0) {
                            ExpireTimerTask.getInstance().addTask(item, player);
                        }
                    }
                }
            }

            for (Item item : player.getEquipment().getEquippedItems()) {
                if (item.getExpireTime() > 0) {
                    ExpireTimerTask.getInstance().addTask(item, player);
                }
            }

            player.getEquipment().checkRankLimitItems(); // Remove items after offline changed rank

            for (Motion motion : player.getMotions().getMotions().values()) {
                if (motion.getExpireTime() != 0) {
                    ExpireTimerTask.getInstance().addTask(motion, player);
                }
            }

            for (Emotion emotion : player.getEmotions().getEmotions()) {
                if (emotion.getExpireTime() != 0) {
                    ExpireTimerTask.getInstance().addTask(emotion, player);
                }
            }

            for (Title title : player.getTitleList().getTitles()) {
                if (title.getExpireTime() != 0) {
                    ExpireTimerTask.getInstance().addTask(title, player);
                }
            }

            if (player.getHouseRegistry() != null) {
                for (HouseObject<?> obj : player.getHouseRegistry().getObjects()) {
                    if (obj.getPersistentState() == PersistentState.DELETED) {
                        continue;
                    }
                    if (obj.getObjectTemplate().getUseDays() > 0) {
                        ExpireTimerTask.getInstance().addTask(obj, player);
                    }
                }
            }
            // scheduler periodic update
            player.getController().addTask(TaskId.PLAYER_UPDATE, ThreadPoolManager.getInstance().scheduleAtFixedRate(new GeneralUpdateTask(player.getObjectId()), PeriodicSaveConfig.PLAYER_GENERAL * 1000,
            PeriodicSaveConfig.PLAYER_GENERAL * 1000));
            player.getController().addTask(TaskId.INVENTORY_UPDATE, ThreadPoolManager.getInstance().scheduleAtFixedRate(new ItemUpdateTask(player.getObjectId()), PeriodicSaveConfig.PLAYER_ITEMS * 1000, 
            PeriodicSaveConfig.PLAYER_ITEMS * 1000));
            
            SurveyService.getInstance().showAvailable(player);

            if (EventsConfig.ENABLE_EVENT_SERVICE) {
                EventService.getInstance().onPlayerLogin(player);
            }

            if (CraftConfig.DELETE_EXCESS_CRAFT_ENABLE) {
                RelinquishCraftStatus.removeExcessCraftStatus(player, false);
            }

            PlayerTransferService.getInstance().onEnterWorld(player);
            player.setPartnerId(DAOManager.getDAO(WeddingDAO.class).loadPartnerId(player));
        } else {
            log.info("[DEBUG] enter world" + objectId + ", Player: " + player);
        }
    }

    private static void sendMacroList(AionConnection client, Player player) {
        client.sendPacket(new SM_MACRO_LIST(player));
    }

    private static void showPremiumAccountInfo(AionConnection client, Account account) {
        byte membership = account.getMembership();
        if (membership > 0) {
            String accountType = "";
            switch (account.getMembership()) {
                case 1:
                    accountType = "premium";
                    break;
                case 2:
                    accountType = "VIP";
                    break;
            }
            client.sendPacket(new SM_MESSAGE(0, null, "Your account is " + accountType, ChatType.GOLDEN_YELLOW));
        }
    }    
    
    /**
     * @param player
     */
    private static void playerLoggedIn(Player player) {
        log.info("Player logged in: " + player.getName() + " Account: " + player.getClientConnection().getAccount().getName());
        player.getCommonData().setOnline(true);
        DAOManager.getDAO(PlayerDAO.class).onlinePlayer(player, true);
        player.onLoggedIn();
        player.setOnlineTime();
    }
}

class ItemUpdateTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ItemUpdateTask.class);
    private final int playerId;

    ItemUpdateTask(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void run() {
        Player player = World.getInstance().findPlayer(playerId);
        if (player != null) {
            try {
                DAOManager.getDAO(InventoryDAO.class).store(player);
                DAOManager.getDAO(ItemStoneListDAO.class).save(player);
            } catch (Exception ex) {
                log.error("Exception during periodic saving of player items " + player.getName(), ex);
            }
        }
    }
}

class GeneralUpdateTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(GeneralUpdateTask.class);
    private final int playerId;

    GeneralUpdateTask(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public void run() {
        Player player = World.getInstance().findPlayer(playerId);
        if (player != null) {
            try {
                DAOManager.getDAO(AbyssRankDAO.class).storeAbyssRank(player);
                DAOManager.getDAO(PlayerSkillListDAO.class).storeSkills(player);
                DAOManager.getDAO(PlayerQuestListDAO.class).store(player);
                DAOManager.getDAO(PlayerDAO.class).storePlayer(player);
                for (House house : player.getHouses()) {
                    house.save();
                }
            } catch (Exception ex) {
                log.error("Exception during periodic saving of player " + player.getName(), ex);
            }
        }

    }
}
