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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.EnchantsConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.stats.calc.functions.IStatFunction;
import com.aionemu.gameserver.model.stats.calc.functions.StatEnchantFunction;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.model.stats.listeners.ItemEquipmentListener;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemCategory;
import com.aionemu.gameserver.model.templates.item.ItemEnchantTable;
import com.aionemu.gameserver.model.templates.item.ItemEnchantTemplate;
import com.aionemu.gameserver.model.templates.item.ItemQuality;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.actions.EnchantItemAction;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.item.ItemPacketService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.item.ItemSocketService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.audit.AuditLogger;

/**
 * @author ATracer
 * @modified Wakizashi, Source, vlog
 * @rework GiGatR00n v4.7.5.x, Blackfire, Kill3r
 */
public class EnchantService {

    private static final Logger log = LoggerFactory.getLogger(EnchantService.class);

    public static void amplifyItem(Player player, Item targetItem, Item material, Item tool) {
    	int buffId = 0;
    	if (targetItem == null || player == null) 
    		return;
    	
    	if (!targetItem.getItemTemplate().getExceedEnchant())
    		return;
    	
    	if (targetItem.getEnchantLevel() < 15 && targetItem.getItemTemplate().getMaxEnchantLevel() == 15)
    		return;
    	
    	if (targetItem.getItemId() != material.getItemId() && material.getItemId() != 166500002) {
    		log.warn("[AMPLIFICATION] player " + player.getName() + " tried to amplificate with material " + material.getItemId());
    		return;
    	}
    	
    	if (targetItem.getItemTemplate().isArmor()) {
    		buffId = getArmorBuff(targetItem);
    	} else if (targetItem.getItemTemplate().isWeapon()) {
    		buffId = getWeaponBuff(player);
    	}
    	
    	targetItem.setAmplified(true);
    	targetItem.setBuffSkill(buffId);
    	player.getInventory().decreaseByObjectId(material.getObjectId(), 1);
    	player.getInventory().decreaseByObjectId(tool.getObjectId(), 1);
    	PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_EXCEED_SUCCEED(new DescriptionId(targetItem.getNameId())));
    	ItemPacketService.updateItemAfterInfoChange(player, targetItem);
    }    
    public static void amplifyItemCommand(Player player, Item item) {
    	int buffId = 0;
    	if (item == null || player == null) 
    		return;
    	
    	if (!item.getItemTemplate().getExceedEnchant())
    		return;
    	
    	if (item.getEnchantLevel() < 15)
    		return;
    	
    	if (item.getItemTemplate().isArmor()) {
    		buffId = getArmorBuff(item);
    	} else if (item.getItemTemplate().isWeapon()) {
    		buffId = getWeaponBuff(player);
    	}
    	
    	item.setAmplified(true);
    	item.setBuffSkill(buffId);
    	ItemPacketService.updateItemAfterInfoChange(player, item);
    }
	
	public static int getArmorBuff(Item armor) {
        int skillId = 0;
        // Skill range of armor buffs 13061 - 13147
        if(armor.getItemTemplate().getCategory() == ItemCategory.JACKET){
            skillId = Rnd.get(13128, 13147);
        }else if(armor.getItemTemplate().getCategory() == ItemCategory.GLOVES){
            skillId = Rnd.get(13038, 13060);
        }else if(armor.getItemTemplate().getCategory() == ItemCategory.SHOULDERS){
            skillId = Rnd.get(13082, 13107);
        }else if(armor.getItemTemplate().getCategory() == ItemCategory.PANTS){
            skillId = Rnd.get(13061, 13081);
        }else if(armor.getItemTemplate().getCategory() == ItemCategory.SHOES){
            skillId = Rnd.get(13108, 13127);
        }

        return skillId;
    }
    public static int getWeaponBuff(Player player) {
        int skillId = 0;
        skillId = Rnd.get(13001, 13037);
        if (player.getSkillList().getSkillEntry(skillId) != null) {
            skillId = Rnd.get(13001, 13037);
        }
        return skillId;
    }
    /**
     * @param player
     * @param parentItem     the enchantment stone
     * @param targetItem     the item to enchant
     * @param supplementItem the item, giving additional chance
     * @return true, if successful
     */
    public static boolean enchantItem(Player player, Item parentItem, Item targetItem, Item supplementItem) {
        ItemTemplate enchantStone = parentItem.getItemTemplate();
        int enchantStoneLevel = enchantStone.getLevel();
        int targetItemLevel = targetItem.getItemTemplate().getLevel();
        int enchantitemLevel = targetItem.getEnchantLevel() + 1;

        // Modifier, depending on the quality of the item
        // Decreases the chance of enchant
        int qualityCap = 0;

        ItemQuality quality = targetItem.getItemTemplate().getItemQuality();

        switch (quality) {
            case JUNK:
            case COMMON:
                qualityCap = 5;
                break;
            case RARE:
                qualityCap = 10;
                break;
            case LEGEND:
                qualityCap = 15;
                break;
            case UNIQUE:
                qualityCap = 20;
                break;
            case EPIC:
                qualityCap = 25;
                break;
            case MYTHIC:
                qualityCap = 30;
                break;
        }

        // Start value of success
        float success = EnchantsConfig.ENCHANT_STONE;
        
        // Since 4.7.5 we need to calculate the success for the new enchantment stones a little bit different
        // Every new enchantment stone got a declared level range, so we pickup random a level value for the
        // success calculation.
        switch (parentItem.getItemId()) {
        	case 166000191: //Alpha
        		enchantStoneLevel = Rnd.get(1, 29);
        		break;
        	case 166000192: //Beta
        		enchantStoneLevel = Rnd.get(30, 59);
        		break;
        	case 166000193: //Gamma
        		enchantStoneLevel = Rnd.get(60, 84);
        		break;
        	case 166000194: //Delta
        		enchantStoneLevel = Rnd.get(85, 104);
        		break;
        	case 166000195: //Epsilon
        		enchantStoneLevel = Rnd.get(105, 190);
        		break;
        	case 166020000: //Omega
        		enchantStoneLevel = 190;
        		break;
        }

        // Extra success chance
        // The greater the enchant stone level, the greater the
        // level difference modifier
        int levelDiff = enchantStoneLevel - targetItemLevel;
        success += levelDiff > 0 ? levelDiff * 3f / qualityCap : 0;

        // Level difference
        // Can be negative, if the item quality too hight
        // And the level difference too small
        success += levelDiff - qualityCap;

        // Enchant next level difficulty
        // The greater item enchant level,
        // the lower start success chance
        success -= targetItem.getEnchantLevel() * qualityCap / (enchantitemLevel > 10 ? 5f : 6f);

        // Supplement is used
        if (supplementItem != null) {
            // Amount of supplement items
            int supplementUseCount = 1;
            // Additional success rate for the supplement
            ItemTemplate supplementTemplate = supplementItem.getItemTemplate();
            float addSuccessRate = 0f;

            EnchantItemAction action = supplementTemplate.getActions().getEnchantAction();
            if (action != null) {
                if (action.isManastoneOnly()) {
                    return false;
                }
                addSuccessRate = action.getChance() * 2;
            }

            action = enchantStone.getActions().getEnchantAction();
            if (action != null) {
                supplementUseCount = action.getCount();
            }

            // Beginning from the level 11 of the enchantment of the item,
            // There will be 2 times more supplements required
            if (enchantitemLevel > 10) {
                supplementUseCount = supplementUseCount * 2;
            }

            // Check the required amount of the supplements
            if (player.getInventory().getItemCountByItemId(supplementTemplate.getTemplateId()) < supplementUseCount) {
                return false;
            }

            // Adjust Add Success Rate to rates in Config
            switch (parentItem.getItemTemplate().getItemQuality()) {
                case LEGEND:
                    addSuccessRate *= EnchantsConfig.LESSER_SUP;
                    break;
                case UNIQUE:
                    addSuccessRate *= EnchantsConfig.REGULAR_SUP;
                    break;
                case EPIC:
                    addSuccessRate *= EnchantsConfig.GREATER_SUP;
                    break;
                case MYTHIC:
                	addSuccessRate *= EnchantsConfig.MYTHIC_SUP;
                	break;
                default:
                    break;
            }

            // Add success rate of the supplement to the overall chance
            success += addSuccessRate;

            // Put supplements to wait for update
            player.subtractSupplements(supplementUseCount, supplementTemplate.getTemplateId());
        }

        // The overall success chance can't be more, than 95
        if (success >= 95) {
            success = 95;
        }

        boolean result = false;
        float random = Rnd.get(1, 1000) / 10f;

        // If the random number < or = overall success rate,
        // The item will be successfully enchanted
        if (random <= success) {
            result = true;
        }

        // For test purpose. To use by administrator
        if (player.getAccessLevel() > 2) {
            PacketSendUtility.sendMessage(player, (result ? "Success" : "Fail") + " Rnd:" + random + " Luck:" + success);
        }

        return result;
    }

	/**
     * @param player
     * @param targetItem
     * @param parentItem
     */
    public static boolean breakItem(Player player, Item targetItem, Item parentItem) {
        Storage inventory = player.getInventory();

        if (inventory.getItemByObjId(targetItem.getObjectId()) == null) {
            return false;
        }
        if (inventory.getItemByObjId(parentItem.getObjectId()) == null) {
            return false;
        }

        ItemTemplate itemTemplate = targetItem.getItemTemplate();
        int quality = itemTemplate.getItemQuality().getQualityId();

        if (!itemTemplate.isArmor() && !itemTemplate.isWeapon()) {
            AuditLogger.info(player, "Player try break dont compatible item type.");
            return false;
        }

        if (!itemTemplate.isArmor() && !itemTemplate.isWeapon()) {
            AuditLogger.info(player, "Break item hack, armor/weapon iD changed.");
            return false;
        }

        // Quality modifier
        if (itemTemplate.isSoulBound() && !itemTemplate.isArmor()) {
            quality += 1;
        } else if (!itemTemplate.isSoulBound() && itemTemplate.isArmor()) {
            quality -= 1;
        }

        int number = 0;
        int level = 1;
        switch (quality) {
            case 0: // JUNK
            case 1: // COMMON
                number = Rnd.get(1, 2);
                level = Rnd.get(-4, 10);
                break;
            case 2: // RARE
                number = Rnd.get(1, 4);
                level = Rnd.get(-3, 20);
                break;
            case 3: // LEGEND
                number = Rnd.get(1, 6);
                level = Rnd.get(-2, 30);
                break;
            case 4: // UNIQUE
                number = Rnd.get(1, 8);
                level = Rnd.get(-1, 50);
                break;
            case 5: // EPIC
                number = Rnd.get(1, 10);
                level = Rnd.get(0, 70);
                break;
            case 6: // MYTHIC
            case 7:
                number = Rnd.get(1, 12);
                level = Rnd.get(0, 80);
                break;
        }

        // You can't add stone < 166000000
        if (level < 1) {
            level = 1;
        }
        int enchantItemLevel = targetItem.getItemTemplate().getLevel() + level;
        int enchantItemId = 166000000 + enchantItemLevel;

        if (inventory.delete(targetItem) != null) {
            if (inventory.decreaseByObjectId(parentItem.getObjectId(), 1)) {
                ItemService.addItem(player, enchantItemId, number);
            }
        } else {
            AuditLogger.info(player, "Possible break item hack, do not remove item.");
        }
        return true;
    }   

    public static void enchantItemAct(Player player, Item parentItem, Item targetItem, int currentEnchant, boolean result) {
        int addLevel = 1;
        int rnd = Rnd.get(100); //crit modifier
        if (rnd < 2) {
            addLevel = 3;
        } else if (rnd < 7) {
            addLevel = 2;
        }
        ItemQuality targetQuality = targetItem.getItemTemplate().getItemQuality();

        if (!player.getInventory().decreaseByObjectId(parentItem.getObjectId(), 1)) {
            AuditLogger.info(player, "Possible enchant hack, do not remove enchant stone.");
            return;
        }
        //Decrease required supplements
        player.updateSupplements();

        // Items that are Fabled or Eternal can get up to +15.
        // Note: Amplificated items only can enchant over 15 with a 
        // Omega Enchantment Stone
        if (result || player.isGM() && AdminConfig.INSTANT_ENCHANT_SUCCESS) {
            switch (targetQuality) {
                case COMMON:
                case RARE:
                case LEGEND:
                    if (currentEnchant > EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1 && !targetItem.isAmplified()) {
                        currentEnchant = EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1;
                        AuditLogger.info(player, "Possible enchant hack, send fake packet for enchant up more posible.");
                    } else if (targetItem.isAmplified() && parentItem.getItemId() == 166020000) { //Omega Enchantment Stone
                        		currentEnchant += 1;
					} else if (currentEnchant == EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1 && !targetItem.isAmplified()) {
                        return;
                    } else if (currentEnchant + addLevel <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1) {
                        currentEnchant += addLevel;
                    } else if (((addLevel - 1) > 1) && ((currentEnchant + addLevel - 1) <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE1)) {
                        currentEnchant += (addLevel - 1);
                    } else {
                        currentEnchant += 1;
                    }
                    break;
                case UNIQUE:
                case EPIC:
                case MYTHIC:
                    if (currentEnchant > EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2 && !targetItem.isAmplified()) {
                        currentEnchant = EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2;
                        AuditLogger.info(player, "Possible enchant hack, send fake packet for enchant up more posible.");
                    } else if (targetItem.isAmplified() && parentItem.getItemId() == 166020000) { //Omega Enchantment Stone
								currentEnchant += 1;
					} else if (currentEnchant == EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2 && !targetItem.isAmplified()) {
                        return;
                    } else if (currentEnchant + addLevel <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2) {
                        currentEnchant += addLevel;
                    } else if (((addLevel - 1) > 1) && ((currentEnchant + addLevel - 1) <= EnchantsConfig.ENCHANT_MAX_LEVEL_TYPE2)) {
                        currentEnchant += (addLevel - 1);
                    } else {
                        currentEnchant += 1;
                    }
                    break;
                case JUNK:
                    return;
            }
        } else {
            // Retail: http://powerwiki.na.aiononline.com/aion/Patch+Notes:+1.9.0.1
            // When socketing fails at +11~+15, the value falls back to +10.
        	if (targetItem.isAmplified()) {
        		int skillId = targetItem.getBuffSkill();
				int maxEnchant = targetItem.getItemTemplate().getMaxEnchantLevel();
				currentEnchant = maxEnchant;
				targetItem.setAmplified(false);
				targetItem.setBuffSkill(0);
				if (player.getSkillList().isSkillPresent(skillId)){
				SkillLearnService.removeSkill(player, skillId);
				}
			} else if (currentEnchant > 10 && !targetItem.isAmplified()) {
                currentEnchant = 10;
            } else if (currentEnchant > 0 && !targetItem.isAmplified()) {
                currentEnchant -= 1;
            }
            
        }

        targetItem.setEnchantLevel(currentEnchant);
        if (targetItem.isEquipped()) {
            player.getGameStats().updateStatsVisually();
        }

        ItemPacketService.updateItemAfterInfoChange(player, targetItem);

        if (targetItem.isEquipped()) {
            player.getEquipment().setPersistentState(PersistentState.UPDATE_REQUIRED);
        } else {
            player.getInventory().setPersistentState(PersistentState.UPDATE_REQUIRED);
        }

        if (result) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_SUCCEED(new DescriptionId(targetItem.getNameId())));
        } else {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ENCHANT_ITEM_FAILED(new DescriptionId(targetItem.getNameId())));
        }
    }

    /**
     * @param player
     * @param parentItem     the manastone
     * @param targetItem     the item to socket
     * @param supplementItem
     * @param targetWeapon   fusioned weapon
     */
    public static boolean socketManastone(Player player, Item parentItem, Item targetItem, Item supplementItem, int targetWeapon) {

        int targetItemLevel = 1;

        // Fusioned weapon. Primary weapon level.
        if (targetWeapon == 1) {
            targetItemLevel = targetItem.getItemTemplate().getLevel();
        } // Fusioned weapon. Secondary weapon level.
        else {
            targetItemLevel = targetItem.getFusionedItemTemplate().getLevel();
        }

        int stoneLevel = parentItem.getItemTemplate().getLevel();
        int slotLevel = (int) (10 * Math.ceil((targetItemLevel + 10) / 10d));
        boolean result = false;

        // Start value of success
        float success = EnchantsConfig.MANA_STONE;

        // The current amount of socketed stones
        int stoneCount;

        // Manastone level shouldn't be greater as 20 + item level
        // Example: item level: 1 - 10. Manastone level should be <= 20
        if (stoneLevel > slotLevel) {
            return false;
        }

        // Fusioned weapon. Primary weapon slots.
        if (targetWeapon == 1) // Count the inserted stones in the primary weapon
        {
            stoneCount = targetItem.getItemStones().size();
        } // Fusioned weapon. Secondary weapon slots.
        else // Count the inserted stones in the secondary weapon
        {
            stoneCount = targetItem.getFusionStones().size();
        }

        // Fusioned weapon. Primary weapon slots.
        if (targetWeapon == 1) {
            // Find all free slots in the primary weapon
            if (stoneCount >= targetItem.getSockets(false)) {
                AuditLogger.info(player, "Manastone socket overload");
                return false;
            }
        } // Fusioned weapon. Secondary weapon slots.
        else if (!targetItem.hasFusionedItem() || stoneCount >= targetItem.getSockets(true)) {
            // Find all free slots in the secondary weapon
            AuditLogger.info(player, "Manastone socket overload");
            return false;
        }

        // Stone quality modifier
        success += parentItem.getItemTemplate().getItemQuality() == ItemQuality.COMMON ? 25f : 15f;

        // Next socket difficulty modifier
        float socketDiff = stoneCount * 1.25f + 1.75f;

        // Level difference
        success += (slotLevel - stoneLevel) / socketDiff;

        // The supplement item is used
        if (supplementItem != null) {
            int supplementUseCount = 0;
            ItemTemplate manastoneTemplate = parentItem.getItemTemplate();

            int manastoneCount;
            // Not fusioned
            if (targetWeapon == 1) {
                manastoneCount = targetItem.getItemStones().size() + 1;
            } // Fusioned
            else {
                manastoneCount = targetItem.getFusionStones().size() + 1;
            }

            // Additional success rate for the supplement
            ItemTemplate supplementTemplate = supplementItem.getItemTemplate();
            float addSuccessRate = 0f;

            boolean isManastoneOnly = false;
            EnchantItemAction action = manastoneTemplate.getActions().getEnchantAction();
            if (action != null) {
                supplementUseCount = action.getCount();
            }

            action = supplementTemplate.getActions().getEnchantAction();
            if (action != null) {
                addSuccessRate = action.getChance();
                isManastoneOnly = action.isManastoneOnly();
            }

            // Adjust addsuccessrate to rates in config
            switch (parentItem.getItemTemplate().getItemQuality()) {
                case LEGEND:
                    addSuccessRate *= EnchantsConfig.LESSER_SUP;
                    break;
                case UNIQUE:
                    addSuccessRate *= EnchantsConfig.REGULAR_SUP;
                    break;
                case EPIC:
                    addSuccessRate *= EnchantsConfig.GREATER_SUP;
                    break;
                case MYTHIC:
			     addSuccessRate *= EnchantsConfig.MYTHIC_SUP;
                    break;
			default:
				break;
            }

            if (isManastoneOnly) {
                supplementUseCount = 1;
            } else if (stoneCount > 0) {
                supplementUseCount = supplementUseCount * manastoneCount;
            }

            if (player.getInventory().getItemCountByItemId(supplementTemplate.getTemplateId()) < supplementUseCount) {
                return false;
            }

            // Add successRate
            success += addSuccessRate;

            // Put up supplements to wait for update
            player.subtractSupplements(supplementUseCount, supplementTemplate.getTemplateId());
        }

        float random = Rnd.get(1, 1000) / 10f;

        if (random <= success) {
            result = true;
        }

        // For test purpose. To use by administrator
        if (player.getAccessLevel() > 2) {
            PacketSendUtility.sendMessage(player, (result ? "Success" : "Fail") + " Rnd:" + random + " Luck:" + success);
        }

        return result;
    }

    public static void socketManastoneAct(Player player, Item parentItem, Item targetItem, int targetWeapon, boolean result) {

        // Decrease required supplements
        player.updateSupplements();

        if (player.getInventory().decreaseByObjectId(parentItem.getObjectId(), 1) && result) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_SUCCEED(new DescriptionId(targetItem.getNameId())));

            if (targetWeapon == 1) {
                ManaStone manaStone = ItemSocketService.addManaStone(targetItem, parentItem.getItemTemplate().getTemplateId());
                if (targetItem.isEquipped()) {
                    ItemEquipmentListener.addStoneStats(targetItem, manaStone, player.getGameStats());
                    player.getGameStats().updateStatsAndSpeedVisually();
                }
            } else {
                ManaStone manaStone = ItemSocketService.addFusionStone(targetItem, parentItem.getItemTemplate().getTemplateId());
                if (targetItem.isEquipped()) {
                    ItemEquipmentListener.addStoneStats(targetItem, manaStone, player.getGameStats());
                    player.getGameStats().updateStatsAndSpeedVisually();
                }
            }
        } else {
            PacketSendUtility.sendPacket(player,
                    SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_OPTION_FAILED(new DescriptionId(targetItem.getNameId())));
            player.getInventory().decreaseByObjectId(parentItem.getObjectId(), 1);
        }

        ItemPacketService.updateItemAfterInfoChange(player, targetItem);
    }

    /**
     * @param player
     * @param item
     */
    @SuppressWarnings("rawtypes")
    public static void onItemEquip(Player player, Item item) {
        @SuppressWarnings("unchecked")
        List<IStatFunction> modifiers = new ArrayList();
        try {
            if (item.getItemTemplate().isWeapon()) {
                switch (item.getItemTemplate().getWeaponType()) {
                    case BOOK_2H:
                    case ORB_2H:
                    case HARP_2H:
                    case GUN_1H:
                    case CANNON_2H:
                    case KEYBLADE_2H:
                        modifiers.add(new StatEnchantFunction(item, StatEnum.BOOST_MAGICAL_SKILL, 0));
                        modifiers.add(new StatEnchantFunction(item, StatEnum.MAGICAL_ATTACK, 0));
                        break;
                    case MACE_1H:
                    case STAFF_2H:
                        modifiers.add(new StatEnchantFunction(item, StatEnum.BOOST_MAGICAL_SKILL, 0));
                    case DAGGER_1H:
                    case BOW:
                    case POLEARM_2H:
                    case SWORD_1H:
                    case SWORD_2H:
                    case KEYHAMMER_2H:
                        modifiers.add(new StatEnchantFunction(item, StatEnum.PHYSICAL_ATTACK, 0));
                        if (item.getEquipmentSlot() == ItemSlot.MAIN_HAND.getSlotIdMask()) {
                            modifiers.add(new StatEnchantFunction(item, StatEnum.MAIN_HAND_POWER, 0));
                        } else {
                            modifiers.add(new StatEnchantFunction(item, StatEnum.OFF_HAND_POWER, 0));
                        }
                        break;
                    default:
                        break;
                }
                
                // For the future...it looks like ncsoft will enable it also for weapons
                if (item.getItemTemplate().getAuthorizeName() > 0) {
                    ItemEnchantTemplate ie = DataManager.ITEM_ENCHANT_DATA.getEnchantTemplate(item.getItemTemplate().getAuthorizeName());
                    if (item.getAuthorize() > 0 && ie != null) {
                        try {
                            modifiers.addAll(ie.getStats(item.getAuthorize()));
                        } catch (Exception e) {
                            log.error("Cant add tempering modifiers for item: " + item.getItemId() + " , " + ie.getStats(item.getAuthorize()));
                        }
                    }
                }
                
                if (CustomConfig.ENABLE_ENCHANT_BONUS) {
                	ItemEnchantTable it = DataManager.ITEM_ENCHANT_TABLE_DATA.getTableWeapon(item.getItemTemplate().getCategory());
                	if (item.getEnchantLevel() > 0 && it != null && item.getEnchantLevel() < 22) {
                		try {
                			modifiers.addAll(it.getStats(item.getEnchantLevel()));
                		} catch (Exception ex) {
                			log.error("Cant add enchant table modifiers for item: " + item.getItemId() + " , " + it.getStats(item.getEnchantLevel()));
                		}
                	}
                }
                
                if (item.isAmplified() && item.getEnchantLevel() >= 20) {
                	player.getSkillList().addSkill(player, item.getBuffSkill(), 1);
                }
            } else if (item.getItemTemplate().isArmor()) {
                if (item.getItemTemplate().getArmorType() == ArmorType.SHIELD) {
                    modifiers.add(new StatEnchantFunction(item, StatEnum.DAMAGE_REDUCE, 0));
                    modifiers.add(new StatEnchantFunction(item, StatEnum.BLOCK, 0));
                }
				if (item.getItemTemplate().isAccessory() && item.getItemTemplate().getCategory() != ItemCategory.PLUME) {
				    switch (item.getItemTemplate().getCategory()) {
                        case HELMET:
                        case EARRINGS:
                        case NECKLACE:
							modifiers.add(new StatEnchantFunction(item, StatEnum.PVP_ATTACK_RATIO, 0));
                            break;
                        case RINGS:
                        case BELT:
							modifiers.add(new StatEnchantFunction(item, StatEnum.PVP_DEFEND_RATIO, 0));
					default:
						break;
                    }
				}
                if (item.getItemTemplate().getCategory() == ItemCategory.PLUME) {
                    int id = item.getItemTemplate().getAuthorizeName();
                    switch (id) {
                        case 52:
                            modifiers.add(new StatEnchantFunction(item, StatEnum.PHYSICAL_ATTACK, 0));
                            modifiers.add(new StatEnchantFunction(item, StatEnum.MAXHP, 0));
                            break;
                        case 53:
                            modifiers.add(new StatEnchantFunction(item, StatEnum.BOOST_MAGICAL_SKILL, 0));
                            modifiers.add(new StatEnchantFunction(item, StatEnum.MAXHP, 0));
                    }
                    if (CustomConfig.ENABLE_ENCHANT_BONUS) {
                    	ItemEnchantTable it = DataManager.ITEM_ENCHANT_TABLE_DATA.getTablePlume();
                    	if (item.getAuthorize() > 0 && it != null && item.getEnchantLevel() < 22) {
                    		try {
                    			modifiers.addAll(it.getStats(item.getEnchantLevel()));
                    		} catch (Exception ex) {
                    			log.error("Cant add enchant table modifiers for item: " + item.getItemId() + " , " + it.getStats(item.getEnchantLevel()));
                    		}
                    	}
                    }
                } else {
                    if(item.getItemTemplate().getArmorType() != ArmorType.SHIELD){
                        modifiers.add(new StatEnchantFunction(item, StatEnum.PHYSICAL_DEFENSE, 0));
                        modifiers.add(new StatEnchantFunction(item, StatEnum.MAGICAL_DEFEND, 0));
                        modifiers.add(new StatEnchantFunction(item, StatEnum.MAXHP, 0));
                        modifiers.add(new StatEnchantFunction(item, StatEnum.PHYSICAL_CRITICAL_RESIST, 0));
                    }
                    player.getGameStats().updateStatsAndSpeedVisually();
                }
                // For the future...it looks like ncsoft will enable it also for armors
                if (item.getItemTemplate().getAuthorizeName() > 0 && !item.getItemTemplate().isAccessory()) {
                    ItemEnchantTemplate ie = DataManager.ITEM_ENCHANT_DATA.getEnchantTemplate(item.getItemTemplate().getAuthorizeName());
                    if (item.getAuthorize() > 0 && ie != null) {
                        try {
                            modifiers.addAll(ie.getStats(item.getAuthorize()));
                        } catch (Exception e) {
                            log.error("Cant add tempering modifiers for item: " + item.getItemId() + " , " + ie.getStats(item.getAuthorize()));
                        }
                    }
                }
                if (CustomConfig.ENABLE_ENCHANT_BONUS && !item.getItemTemplate().isAccessory()) {
                	ItemEnchantTable it = DataManager.ITEM_ENCHANT_TABLE_DATA.getTableArmor(item.getItemTemplate().getArmorType(), item.getItemTemplate().getCategory());
                	if (item.getEnchantLevel() > 0 && it != null && item.getEnchantLevel() < 22) {
                		try {
                			modifiers.addAll(it.getStats(item.getEnchantLevel()));
                		} catch (Exception ex) {
                			log.error("Cant add enchant table modifiers for item: " + item.getItemId() + " , " + it.getStats(item.getEnchantLevel()));
                		}
                	}
                }
                if (item.isAmplified() && item.getEnchantLevel() >= 20) {
                	player.getSkillList().addSkill(player, item.getBuffSkill(), 1);
                }
            }
            if (CustomConfig.ENABLE_ENCHANT_BONUS && item.getItemTemplate().getCategory() != ItemCategory.PLUME && item.getAuthorize() > 0) {
            	ItemEnchantTable it = DataManager.ITEM_ENCHANT_TABLE_DATA.getTableAuthorize();
            	if (item.getEnchantLevel() > 0 && it != null && item.getEnchantLevel() < 22) {
            		try {
            			modifiers.addAll(it.getStats(item.getEnchantLevel()));
            		} catch (Exception ex) {
            			log.error("Cant add enchant table modifiers for item: " + item.getItemId() + " , " + it.getStats(item.getEnchantLevel()));
            		}
            	}
            }
            if (!modifiers.isEmpty()) {
                player.getGameStats().addEffect(item, modifiers);
            }
        } catch (Exception ex) {
            log.error("Error on item equip.", ex);
        }
    }

}
