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
package com.aionemu.gameserver.services;

import com.aionemu.gameserver.configs.main.AutoGroupConfig;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.autogroup.AutoGroupType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerHouseOwnerFlags;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.team.legion.Legion;
import com.aionemu.gameserver.model.team.legion.LegionWarehouse;
import com.aionemu.gameserver.model.templates.portal.PortalPath;
import com.aionemu.gameserver.model.templates.teleport.TeleportLocation;
import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
import com.aionemu.gameserver.model.templates.tradelist.TradeListTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AUTO_GROUP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PET;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLASTIC_SURGERY;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_REPURCHASE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SELL_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRADELIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRADE_IN_LIST;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.services.craft.CraftSkillUpdateService;
import com.aionemu.gameserver.services.craft.RelinquishCraftStatus;
import com.aionemu.gameserver.services.instance.DredgionService;
import com.aionemu.gameserver.services.instance.IdgelDomeService;
import com.aionemu.gameserver.services.instance.IronWallWarFrontService;
import com.aionemu.gameserver.services.instance.KamarBattlefieldService;
import com.aionemu.gameserver.services.instance.OphidanBridgeService;
import com.aionemu.gameserver.services.item.ItemChargeService;
import com.aionemu.gameserver.services.teleport.PortalService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.services.trade.PricesService;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;

/**
 * @author VladimirZ
 * @author GiGatR00n v4.7.5.x
 */
public class DialogService {

    public static void onCloseDialog(Npc npc, Player player) {
        switch (npc.getObjectTemplate().getTitleId()) {
            case 350409:
            case 315073:
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(npc.getObjectId(), 0));
                Legion legion = player.getLegion();
                if (legion != null) {
                    LegionWarehouse lwh = player.getLegion().getLegionWarehouse();
                    if (lwh.getWhUser() == player.getObjectId()) {
                        lwh.setWhUser(0);
                    }
                }
                break;
        }
    }

    public static void onDialogSelect(int dialogId, final Player player, Npc npc, int questId, int extendedRewardIndex) {

        QuestEnv env = new QuestEnv(npc, player, questId, dialogId);
        env.setExtendedRewardIndex(extendedRewardIndex);
        int targetObjectId = npc.getObjectId();
        int titleId = npc.getObjectTemplate().getTitleId();

        if (player.getAccessLevel() >= 1 && CustomConfig.ENABLE_SHOW_DIALOGID) {
            PacketSendUtility.sendMessage(player, "dialogId: " + dialogId);
            PacketSendUtility.sendMessage(player, "questId: " + questId);
        }

        if (questId == 0) {
            switch (DialogAction.getActionByDialogId(dialogId)) {
                case BUY: {
                    TradeListTemplate tradeListTemplate = DataManager.TRADE_LIST_DATA.getTradeListTemplate(npc.getNpcId());
                    if (tradeListTemplate == null) {
                        PacketSendUtility.sendMessage(player, "Buy list is missing!!");
                        break;
                    }
                    int tradeModifier = tradeListTemplate.getSellPriceRate();
                    PacketSendUtility.sendPacket(player, new SM_TRADELIST(player, npc, tradeListTemplate, PricesService.getVendorBuyModifier()
                            * tradeModifier / 100));
                    break;
                }
                case SELL: {
                    PacketSendUtility.sendPacket(player, new SM_SELL_ITEM(targetObjectId, PricesService.getVendorSellModifier(player.getRace())));
                    break;
                }
                case OPEN_STIGMA_WINDOW: { // stigma
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 1));
                    break;
                }
                case CREATE_LEGION: { // create legion
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 2));
                    break;
                }
                case DISPERSE_LEGION: { // disband legion
                    LegionService.getInstance().requestDisbandLegion(npc, player);
                    break;
                }
                case RECREATE_LEGION: { // recreate legion
                    LegionService.getInstance().recreateLegion(npc, player);
                    break;
                }
                case DEPOSIT_CHAR_WAREHOUSE: { // warehouse (2.5)
                    switch (titleId) {
                        case 315008:
                        case 350417:
                        case 370503:
                        case 462878:
                        case 0:
                            if (!RestrictionsManager.canUseWarehouse(player)) {
                                return;
                            }
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 26));
                            WarehouseService.sendWarehouseInfo(player, true);
                            break;
                    }
                    break;
                }
                case OPEN_VENDOR: { // Consign trade?? npc karinerk, koorunerk (2.5)
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 13));
                    break;
                }
                case RECOVERY: { // soul healing (2.5)
                    final long expLost = player.getCommonData().getExpRecoverable();
                    if (expLost == 0) {
                        player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.SPEC2);
                        player.getCommonData().setDeathCount(0);
                    }
                    final double factor = (expLost < 1000000 ? 0.25 - (0.00000015 * expLost) : 0.1);
                    final int price = (int) (expLost * factor);

                    RequestResponseHandler responseHandler = new RequestResponseHandler(npc) {
                        @Override
                        public void acceptRequest(Creature requester, Player responder) {
                            if (player.getInventory().getKinah() >= price) {
                                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_GET_EXP2(expLost));
                                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_SUCCESS_RECOVER_EXPERIENCE);
                                player.getCommonData().resetRecoverableExp();
                                player.getInventory().decreaseKinah(price);
                                player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.SPEC2);
                                player.getCommonData().setDeathCount(0);
                            } else {
                                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_NOT_ENOUGH_KINA(price));
                            }
                        }

                        @Override
                        public void denyRequest(Creature requester, Player responder) {
                            // no message
                        }
                    };
                    if (player.getCommonData().getExpRecoverable() > 0) {
                        boolean result = player.getResponseRequester().putRequest(SM_QUESTION_WINDOW.STR_ASK_RECOVER_EXPERIENCE, responseHandler);
                        if (result) {
                            PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(SM_QUESTION_WINDOW.STR_ASK_RECOVER_EXPERIENCE, 0, 0, String.valueOf(price)));
                        }
                    } else {
                        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_DONOT_HAVE_RECOVER_EXPERIENCE);
                    }
                    break;
                }
                case ENTER_PVP: { // (2.5)
                    switch (npc.getNpcId()) {
                        case 204089: // pvp arena in pandaemonium.
                            TeleportService2.teleportTo(player, 120010000, 1, 984f, 1543f, 222.1f);
                            break;
                        case 203764: // pvp arena in sanctum.
                            TeleportService2.teleportTo(player, 110010000, 1, 1462.5f, 1326.1f, 564.1f);
                            break;
                        case 203981:
                            TeleportService2.teleportTo(player, 210020000, 1, 439.3f, 422.2f, 274.3f);
                            break;
                    }
                    break;
                }
                case LEAVE_PVP: { // (2.5)
                    switch (npc.getNpcId()) {
                        case 204087:
                            TeleportService2.teleportTo(player, 120010000, 1, 1005.1f, 1528.9f, 222.1f);
                            break;
                        case 203875:
                            TeleportService2.teleportTo(player, 110010000, 1, 1470.3f, 1343.5f, 563.7f);
                            break;
                        case 203982:
                            TeleportService2.teleportTo(player, 210020000, 1, 446.2f, 431.1f, 274.5f);
                            break;
                    }
                    break;
                }
                case GIVE_ITEM_PROC: { // Godstone socketing (2.5)
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 21));
                    break;
                }
                case REMOVE_MANASTONE: { // remove mana stone (2.5)
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 20));
                    break;
                }
                case CHANGE_ITEM_SKIN: { // modify appearance (2.5)
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 19));
                    break;
                }
                case AIRLINE_SERVICE: { // flight and teleport (2.5)
                    if (CustomConfig.ENABLE_SIMPLE_2NDCLASS) {
                        int level = player.getLevel();
                        if (level < 9) {
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 27));
                        } else {
                            TeleportService2.showMap(player, targetObjectId, npc.getNpcId());
                        }
                    } else {
                        switch (npc.getNpcId()) {
                            case 203194: {
                                if (player.getRace() == Race.ELYOS) {
                                    QuestState qs = player.getQuestStateList().getQuestState(1006);
                                    if (qs == null || qs.getStatus() != QuestStatus.COMPLETE) {
                                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 27));
                                    } else {
                                        TeleportService2.showMap(player, targetObjectId, npc.getNpcId());
                                    }
                                }
                                break;
                            }
                            case 203679: {
                                if (player.getRace() == Race.ASMODIANS) {
                                    QuestState qs = player.getQuestStateList().getQuestState(2008);
                                    if (qs == null || qs.getStatus() != QuestStatus.COMPLETE) {
                                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 27));
                                    } else {
                                        TeleportService2.showMap(player, targetObjectId, npc.getNpcId());
                                    }
                                }
                                break;
                            }
                            default: {
                                TeleportService2.showMap(player, targetObjectId, npc.getNpcId());
                            }
                        }
                    }
                    break;
                }
                case GATHER_SKILL_LEVELUP: // improve extraction (2.5)
                case COMBINE_SKILL_LEVELUP: { // learn tailoring armor smithing etc. (2.5)
                    CraftSkillUpdateService.getInstance().learnSkill(player, npc);
                    break;
                }
                case EXTEND_INVENTORY: { // expand cube (2.5)
                    CubeExpandService.expandCube(player, npc);
                    break;
                }
                case EXTEND_CHAR_WAREHOUSE: { // (2.5)
                    WarehouseService.expandWarehouse(player, npc);
                    break;
                }
                case OPEN_LEGION_WAREHOUSE: { // legion warehouse (2.5)
                    switch (titleId) {
                        case 350409:
                        case 315073:
                        case 463212:
                            LegionService.getInstance().openLegionWarehouse(player, npc);
                            break;
                    }
                    break;
                }
                case CLOSE_LEGION_WAREHOUSE: { // WTF??? Quest dialog packet (2.5)
                    break;
                }
                case CRAFT: { // (2.5)
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 28));
                    break;
                }
                case EDIT_CHARACTER:
                case EDIT_GENDER: { // (2.5)
                    byte changesex = 0; // 0 plastic surgery, 1 gender switch
                    byte check_ticket = 2; // 2 no ticket, 1 have ticket
                    if (dialogId == DialogAction.EDIT_GENDER.id()) {
                        // Gender Switch
                        changesex = 1;
					if (player.getInventory().getItemCountByItemId(169660000) > 0 || //Gender Switch Ticket
					    player.getInventory().getItemCountByItemId(169660001) > 0 || //[Event] Gender Switch Ticket
						player.getInventory().getItemCountByItemId(169660002) > 0 || //Gender Switch Ticket (60 min)
						player.getInventory().getItemCountByItemId(169660003) > 0 || //[Event] Gender Switch Ticket
						player.getInventory().getItemCountByItemId(169660004) > 0) { //[Event] Gender Switch Ticket
                            check_ticket = 1;
                        }
                    } else {
                        // Plastic Surgery
					if (player.getInventory().getItemCountByItemId(169650000) > 0 || //Plastic Surgery Ticket
					    player.getInventory().getItemCountByItemId(169650001) > 0 || //[Event] Plastic Surgery Ticket
				        player.getInventory().getItemCountByItemId(169650002) > 0 || //[Special] Plastic Surgery Ticket
				        player.getInventory().getItemCountByItemId(169650003) > 0 || //[Special] Plastic Surgery Ticket
			            player.getInventory().getItemCountByItemId(169650004) > 0 || //Plastic Surgery Ticket (60 mins)
				        player.getInventory().getItemCountByItemId(169650005) > 0 || //Plastic Surgery Ticket (60 mins)
						player.getInventory().getItemCountByItemId(169650006) > 0 || //[Event] Plastic Surgery Ticket
						player.getInventory().getItemCountByItemId(169650007) > 0 || //[Event] Plastic Surgery Ticket
						player.getInventory().getItemCountByItemId(169650008) > 0 || //Plastic Surgery Ticket
						player.getInventory().getItemCountByItemId(169691000) > 0) { //Plastic Surgery Ticket
                            check_ticket = 1;
                        }
                    }
                    PacketSendUtility.sendPacket(player, new SM_PLASTIC_SURGERY(player, check_ticket, changesex));
                    player.setEditMode(true);
                    break;
                }
                case MATCH_MAKER: // Dredgion, OphidanBridge, IronWallWarFront, IdgelDome, KamarBattlefield
                    if (AutoGroupConfig.AUTO_GROUP_ENABLE && DredgionService.getInstance().isDredgionAvailable()) {
                        AutoGroupType agt = AutoGroupType.getAutoGroup(npc.getNpcId());
                        if (agt != null && agt.isDredgion()) {
                            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(agt.getInstanceMaskId()));
                        } else {
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 0));
                        }
                    } else if (AutoGroupConfig.AUTO_GROUP_ENABLE && KamarBattlefieldService.getInstance().isKamarAvailable()) {
                        AutoGroupType agt = AutoGroupType.getAutoGroup(npc.getNpcId());
                        if (agt != null && agt.isKamar()) {
                            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(agt.getInstanceMaskId()));
                        } else {
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 0));
                        }
                    } else if (AutoGroupConfig.AUTO_GROUP_ENABLE && OphidanBridgeService.getInstance().isOphidanAvailable()) {
                        AutoGroupType agt = AutoGroupType.getAutoGroup(npc.getNpcId());
                        if (agt != null && agt.isOphidan()) {
                            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(agt.getInstanceMaskId()));
                        } else {
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 0));
                        }
                    } else if (AutoGroupConfig.AUTO_GROUP_ENABLE && IronWallWarFrontService.getInstance().isIronWallAvailable()) {
                        AutoGroupType agt = AutoGroupType.getAutoGroup(npc.getNpcId());
                        if (agt != null && agt.isIronWall()) {
                            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(agt.getInstanceMaskId()));
                        } else {
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 0));
                        }
                    } else if (AutoGroupConfig.AUTO_GROUP_ENABLE && IdgelDomeService.getInstance().isIdgleDomeAvailable()) {
                        AutoGroupType agt = AutoGroupType.getAutoGroup(npc.getNpcId());
                        if (agt != null && agt.isIdgelDome()) {
                            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(agt.getInstanceMaskId()));
                        } else {
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 0));
                        }
                    } else {
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 1011));
                    }
                    break;
                case OPEN_INSTANCE_RECRUIT: {
                    //TODO
                    break;
                }
                case INSTANCE_ENTRY: { // (2.5)
                    break;
                }
                case COMPOUND_WEAPON: { // armsfusion (2.5)
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 29));
                    break;
                }
                case DECOMPOUND_WEAPON: { // armsbreaking (2.5)
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 30));
                    break;
                }
                case FACTION_JOIN: { // join npcFaction (2.5)
                    player.getNpcFactions().enterGuild(npc);
                    break;
                }
                case FACTION_SEPARATE: { // leave npcFaction (2.5)
                    player.getNpcFactions().leaveNpcFaction(npc);
                    break;
                }
                case BUY_AGAIN: { // repurchase (2.5)
                    PacketSendUtility.sendPacket(player, new SM_REPURCHASE(player, npc.getObjectId()));
                    break;
                }
                case PET_ADOPT: { // adopt pet (2.5)
                    PacketSendUtility.sendPacket(player, new SM_PET(6));
                    break;
                }
                case PET_ABANDON: { // surrender pet (2.5)
                    PacketSendUtility.sendPacket(player, new SM_PET(7));
                    break;
                }
                case HOUSING_BUILD: { // housing build
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 32));
                    break;
                }
                case HOUSING_DESTRUCT: { // housing destruct
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 33));
                    break;
                }
                case CHARGE_ITEM_SINGLE: { // condition an individual item
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 35));
                    break;
                }
                case CHARGE_ITEM_MULTI: { // condition all equiped items
                    ItemChargeService.startChargingEquippedItems(player, targetObjectId, 1);
                    break;
                }
                case TRADE_IN: {
                    TradeListTemplate tradeListTemplate = DataManager.TRADE_LIST_DATA.getTradeInListTemplate(npc.getNpcId());
                    if (tradeListTemplate == null) {
                        PacketSendUtility.sendMessage(player, "Buy list is missing!!");
                        break;
                    }
                    PacketSendUtility.sendPacket(player, new SM_TRADE_IN_LIST(npc, tradeListTemplate, 100));
                    break;
                }
                case TRADE_SELL_LIST: {
                    TradeListTemplate tradeListTemplate = DataManager.TRADE_LIST_DATA.getPurchaseTemplate(npc.getNpcId());
                    PacketSendUtility.sendPacket(player, new SM_SELL_ITEM(targetObjectId, tradeListTemplate, 100));
                    break;
                }
                case GIVEUP_CRAFT_EXPERT: { // relinquish Expert Status
                    RelinquishCraftStatus.relinquishExpertStatus(player, npc);
                    break;
                }
                case GIVEUP_CRAFT_MASTER: { // relinquish Master Status
                    RelinquishCraftStatus.relinquishMasterStatus(player, npc);
                    break;
                }
                case HOUSING_PERSONAL_AUCTION: { // housing auction
                    if ((player.getBuildingOwnerStates() & PlayerHouseOwnerFlags.BIDDING_ALLOWED.getId()) == 0) {
                        if (player.getRace() == Race.ELYOS) {
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_HOUSING_CANT_OWN_NOT_COMPLETE_QUEST(18802));
                        } else {
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_HOUSING_CANT_OWN_NOT_COMPLETE_QUEST(28802));
                        }
                        return;
                    }
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 38));
                    break;
                }
                case FUNC_PET_H_ADOPT:
                    PacketSendUtility.sendPacket(player, new SM_PET(16));
                    break;
                case FUNC_PET_H_ABANDON:
                    PacketSendUtility.sendPacket(player, new SM_PET(17));
                    break;
                case CHARGE_ITEM_SINGLE2: // augmenting an individual item
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 42));
                    break;
                case CHARGE_ITEM_MULTI2: // augmenting all equiped items
                    ItemChargeService.startChargingEquippedItems(player, targetObjectId, 2);
                    break;
                case HOUSING_RECREATE_PERSONAL_INS: // recreate personal house instance (studio)
                    HousingService.getInstance().recreatePlayerStudio(player);
                    break;
                case TOWN_CHALLENGE: // town improvement
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 43));
                    break;
                case ITEM_UPGRADE: // item purification
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, 52));
                case TELEPORT_SIMPLE:
                    if (player.getRace() == Race.ELYOS) {
                        switch (npc.getNpcId()) {
                            case 802437:
                                if (player.getAbyssRank().getRank().getId() < AbyssRankEnum.GENERAL.getId() && !player.isGM()) {
                                    PacketSendUtility.sendMessage(player, "You cant enter this location");
                                    return;
                                }
                                TeleportService2.teleportTo(player, 110070000, 503.50354F, 416.99362F, 126.78963F, (byte) 30);
                                break;
                            case 802438:
                                TeleportService2.teleportTo(player, 110070000, 503.60794F, 410.61899F, 126.78963F, (byte) 90);
                        }
                    } else {
                        switch (npc.getNpcId()) {
                            case 802439:
                                if (player.getAbyssRank().getRank().getId() < AbyssRankEnum.GENERAL.getId() && !player.isGM()) {
                                    PacketSendUtility.sendMessage(player, "You cant enter this location");
                                    return;
                                }
                                TeleportService2.teleportTo(player, 120080000, 386.42404F, 250.28336F, 93.129425F, (byte) 60);
                                break;
                            case 802440:
                                TeleportService2.teleportTo(player, 120080000, 392.75845F, 250.5977F, 93.129425F, (byte) 120);
                        }
                    }
                    break;
                case SETPRO1:
                case SETPRO2:
                case SETPRO3:
                    if (QuestEngine.getInstance().onDialog(env)) { //remove this shit after assigning AI portal_dialog
                        return;
                    }
                    TeleporterTemplate template = DataManager.TELEPORTER_DATA.getTeleporterTemplateByNpcId(npc.getNpcId());
                    PortalPath portalPath = DataManager.PORTAL2_DATA.getPortalDialog(npc.getNpcId(), dialogId, player.getRace());
                    if (portalPath != null) {
                        PortalService.port(portalPath, player, targetObjectId);
                    } else if (template != null) {
                        TeleportLocation loc = template.getTeleLocIdData().getTelelocations().get(0);
                        if (loc != null) {
                            TeleportService2.teleport(template, loc.getLocId(), player, npc,
                                    npc.getAi2().getName().equals("general") ? TeleportAnimation.JUMP_AIMATION : TeleportAnimation.BEAM_ANIMATION);
                        }
                    }
                    break;
                default:
                    if (QuestEngine.getInstance().onDialog(env)) {
                        return;
                    }
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, dialogId));
                    break;
            }
        } else {
            if (QuestEngine.getInstance().onDialog(env)) {
                return;
            }
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(targetObjectId, dialogId, questId));
        }
    }
}
