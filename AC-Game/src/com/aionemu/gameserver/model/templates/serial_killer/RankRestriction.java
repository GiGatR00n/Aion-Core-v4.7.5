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
package com.aionemu.gameserver.model.templates.serial_killer;

import javax.xml.bind.annotation.*;

import com.aionemu.gameserver.model.Race;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dtem
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RankRestriction", propOrder = {
        "penaltyAttr"
})
public class RankRestriction {

    @XmlElement(name = "penalty_attr")
    protected List<RankPenaltyAttr> penaltyAttr;
    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "race", required = true)
    protected Race race;
    @XmlAttribute(name = "rank_num", required = true)
    protected int rankNum;
    @XmlAttribute(name = "restrict_direct_portal", required = true)
    protected boolean restrictDirectPortal;
    @XmlAttribute(name = "restrict_dynamic_bindstone", required = true)
    protected boolean restrictDynamicBindstone;

    /**
     * @return the restrictDirectPortal
     */
    public boolean isRestrictDirectPortal() {
        return restrictDirectPortal;
    }

    /**
     * @param restrictDirectPortal the restrictDirectPortal to set
     */
    public void setRestrictDirectPortal(boolean restrictDirectPortal) {
        this.restrictDirectPortal = restrictDirectPortal;
    }

    /**
     * @return the restrictDynamicBindstone
     */
    public boolean isRestrictDynamicBindstone() {
        return restrictDynamicBindstone;
    }

    /**
     * @param restrictDynamicBindstone the restrictDynamicBindstone to set
     */
    public void setRestrictDynamicBindstone(boolean restrictDynamicBindstone) {
        this.restrictDynamicBindstone = restrictDynamicBindstone;
    }

    public List<RankPenaltyAttr> getPenaltyAttr() {
        if (penaltyAttr == null) {
            penaltyAttr = new ArrayList<RankPenaltyAttr>();
        }
        return this.penaltyAttr;
    }

    public int getRankNum() {
        return rankNum;
    }

    public void setRankNum(int value) {
        this.rankNum = value;
    }
    
    public int getId() {
    	return id;
    }
    
    public Race getRace() {
    	return race;
    }
}
