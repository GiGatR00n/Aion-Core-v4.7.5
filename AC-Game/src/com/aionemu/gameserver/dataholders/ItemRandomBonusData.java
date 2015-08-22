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
package com.aionemu.gameserver.dataholders;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.items.RandomBonusResult;
import com.aionemu.gameserver.model.templates.item.bonuses.RandomBonus;
import com.aionemu.gameserver.model.templates.item.bonuses.StatBonusType;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Rolandas
 * @fixed Eloann
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"randomBonuses"})
@XmlRootElement(name = "random_bonuses")
public class ItemRandomBonusData {

    @XmlElement(name = "random_bonus", required = true)
    protected List<RandomBonus> randomBonuses;
    @XmlTransient
    private TIntObjectHashMap<RandomBonus> inventoryRandomBonusData = new TIntObjectHashMap<RandomBonus>();
    @XmlTransient
    private TIntObjectHashMap<RandomBonus> polishRandomBonusData = new TIntObjectHashMap<RandomBonus>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (RandomBonus bonus : randomBonuses) {
            getBonusMap(bonus.getBonusType()).put(bonus.getId(), bonus);
        }
        randomBonuses.clear();
        randomBonuses = null;
    }

    private TIntObjectHashMap<RandomBonus> getBonusMap(StatBonusType bonusType) {
        if (bonusType == StatBonusType.INVENTORY) {
            return inventoryRandomBonusData;
        }
        return polishRandomBonusData;
    }

    /**
     * Gets a randomly chosen modifiers from bonus list.
     *
     * @param item rnd_bonus from the item template
     * @return null if not a chance
     */
    public RandomBonusResult getRandomModifiers(StatBonusType bonusType, int rndOptionSet) {
        RandomBonus bonus = getBonusMap(bonusType).get(rndOptionSet);
        if (bonus == null) {
            return null;
        }

        List<ModifiersTemplate> modifiersGroup = bonus.getModifiers();

        int chance = Rnd.get(10000);
        int current = 0;
        ModifiersTemplate template = null;
        int number = 0;

        for (int i = 0; i < modifiersGroup.size(); i++) {
            ModifiersTemplate modifiers = modifiersGroup.get(i);

            current += modifiers.getChance() * 100;
            if (current >= chance) {
                template = modifiers;
                number = i + 1;
                break;
            }
        }
        return template == null ? null : new RandomBonusResult(template, number);
    }

    public ModifiersTemplate getTemplate(StatBonusType bonusType, int rndOptionSet, int number) {
        RandomBonus bonus = getBonusMap(bonusType).get(rndOptionSet);
        if (bonus == null) {
            return null;
        }
        return bonus.getModifiers().get(number - 1);
    }

    public int size() {
        return inventoryRandomBonusData.size() + polishRandomBonusData.size();
    }
}
