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
package com.aionemu.gameserver.model.templates.abyssracebonus;

import com.aionemu.gameserver.model.Race;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Eloann
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbyssRaceBonus", propOrder = {"bonus"})
public class AbyssRaceBonus {

    @XmlElement(name = "bonus")
    protected List<AbyssRacePenalty> bonus;
    @XmlAttribute(name = "id", required = true)
    protected int Id;
    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "race", required = true)
    private Race race;

    public List<AbyssRacePenalty> getPenalty() {
        if (bonus == null) {
            bonus = new ArrayList<AbyssRacePenalty>();
        }
        return bonus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int value) {
        Id = value;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }
}
