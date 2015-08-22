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
package com.aionemu.gameserver.model.templates.itemgroups;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rolandas
 */
public final class FeedEntries {

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FeedFluid")
    public static class FeedFluid extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FeedArmor")
    public static class FeedArmor extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FeedThorn")
    public static class FeedThorn extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FeedBalaur")
    public static class FeedBalaur extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FeedBone")
    public static class FeedBone extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FeedSoul")
    public static class FeedSoul extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "FeedExclude")
    public static class FeedExclude extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "StinkingJunk")
    public static class StinkingJunk extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "HealthyFoodAll")
    public static class HealthyFoodAll extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "HealthyFoodSpicy")
    public static class HealthyFoodSpicy extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AetherPowderBiscuit")
    public static class AetherPowderBiscuit extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AetherCrystalBiscuit")
    public static class AetherCrystalBiscuit extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AetherGemBiscuit")
    public static class AetherGemBiscuit extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PoppySnack")
    public static class PoppySnack extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PoppySnackTasty")
    public static class PoppySnackTasty extends ItemRaceEntry {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "PoppySnackNutritious")
    public static class PoppySnackNutritious extends ItemRaceEntry {
    }
}
