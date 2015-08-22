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
package com.aionemu.gameserver.model.templates.bonus;

/**
 * @author Wakizashi
 */
public enum InventoryBonusType {

    BOSS, // %Quest_L_boss; siege related?
    COIN, // %Quest_L_coin
    ENCHANT,
    FOOD, // %Quest_L_food
    FORTRESS, // %Quest_L_fortress; sends promotion mails with medals?
    GOODS, // %Quest_L_Goods
    ISLAND, // %Quest_L_3_island; siege related?
    MAGICAL, // %Quest_L_magical
    MANASTONE, // %Quest_L_matter_option
    MASTER_RECIPE, // %Quest_ta_l_master_recipe
    MATERIAL, // %Quest_L_material
    MEDAL, // %Quest_L_medal
    MEDICINE, // %Quest_L_medicine; potions and remedies
    MOVIE, // %Quest_L_Christmas; cut scenes
    NONE,
    RECIPE, // %Quest_L_Recipe
    REDEEM, // %Quest_L_Rnd_Redeem and %Quest_L_redeem
    TASK, // %Quest_L_task; craft related
}
