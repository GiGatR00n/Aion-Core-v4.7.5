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
package com.aionemu.gameserver.model.templates.vortex;

import com.aionemu.gameserver.model.Race;

import javax.xml.bind.annotation.*;

/**
 * @author Source
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vortex")
public class VortexTemplate {

    @XmlAttribute(name = "id")
    protected int id;
    @XmlAttribute(name = "defends_race")
    protected Race dRace;
    @XmlAttribute(name = "offence_race")
    protected Race oRace;
    @XmlElement(name = "home_point")
    protected HomePoint home;
    @XmlElement(name = "resurrection_point")
    protected ResurrectionPoint resurrection;
    @XmlElement(name = "start_point")
    protected StartPoint start;

    /**
     * @return the location id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the defenders race
     */
    public Race getDefendersRace() {
        return this.dRace;
    }

    /**
     * @return the invaders race
     */
    public Race getInvadersRace() {
        return this.oRace;
    }

    public HomePoint getHomePoint() {
        return home;
    }

    public ResurrectionPoint getResurrectionPoint() {
        return resurrection;
    }

    public StartPoint getStartPoint() {
        return start;
    }
}
