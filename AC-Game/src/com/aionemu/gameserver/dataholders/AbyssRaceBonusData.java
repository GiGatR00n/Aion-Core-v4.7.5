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

import com.aionemu.gameserver.model.templates.abyssracebonus.AbyssRaceBonus;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Eloann
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"abyssRaceBonus"})
@XmlRootElement(name = "abyss_race_bonuses")
public class AbyssRaceBonusData {

    @XmlElement(name = "abyss_race_bonus")
    protected List<AbyssRaceBonus> abyssRaceBonus;
    @XmlTransient
    private TIntObjectHashMap<AbyssRaceBonus> templates = new TIntObjectHashMap<AbyssRaceBonus>();

    /**
	 * @param u  
     * @param parent 
	 */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (AbyssRaceBonus template : abyssRaceBonus) {
            templates.put(template.getId(), template);
        }
        abyssRaceBonus.clear();
        abyssRaceBonus = null;
    }

    public int size() {
        return templates.size();
    }

    public AbyssRaceBonus getAbyssRaceBonus(int id) {
        return templates.get(id);
    }
}
