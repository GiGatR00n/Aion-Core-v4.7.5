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
package com.aionemu.gameserver.model.templates.rewards;

/**
 * @author Rolandas
 */
public enum BonusType {

    BOSS, // %Quest_L_boss, items having suffix _g_
    COIN, // %Quest_L_coin -- not used, 99 lvl quests replaced with trade
    ENCHANT,
    FOOD, // %Quest_L_food
    FORTRESS, // %Quest_L_fortress
    GATHER,
    GOODS, // %Quest_D_Goods
    ISLAND,
    LUNAR, // %Quest_A_BranchLunarEvent, exchange charms
    MAGICAL, // %Quest_L_magical -- unknown
    MANASTONE, // %Quest_L_matter_option
    MASTER_RECIPE, // %Quest_ws_master_recipe -- not used, 99 lvl quests, heart exchange now
    MATERIAL, // %Quest_D_material (Only Asmodian)
    MEDAL, // %Quest_L_medal, fountain rewards
    MEDICINE, // %Quest_L_medicine; potions and remedies
    MOVIE, // %Quest_L_Christmas; cut scenes
    NONE,
    RECIPE, // %Quest_L_Recipe_20a_LF2A (Only Elyos, Theobomos)
    REDEEM, // %Quest_L_redeem, exchange angel's/demon's eyes + kinah
    RIFT, // %Quest_L_BranchRiftEvent
    TASK, // %Quest_L_task; craft related
    WINTER                    // %Quest_A_BranchWinterEvent
}
