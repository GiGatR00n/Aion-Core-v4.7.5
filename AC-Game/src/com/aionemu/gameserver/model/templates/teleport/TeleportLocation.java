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
package com.aionemu.gameserver.model.templates.teleport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ATracer
 */
@XmlRootElement(name = "telelocation")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleportLocation {

    @XmlAttribute(name = "loc_id", required = true)
    private int locId;
    @XmlAttribute(name = "teleportid")
    private int teleportid = 0;
    @XmlAttribute(name = "price", required = true)
    private int price = 0;
    @XmlAttribute(name = "pricePvp")
    private int pricePvp = 0;
    @XmlAttribute(name = "required_quest")
    private int required_quest = 0;
    @XmlAttribute(name = "type", required = true)
    private TeleportType type;

    public int getLocId() {
        return locId;
    }

    public int getTeleportId() {
        return teleportid;
    }

    public int getPrice() {
        return price;
    }

    public int getPricePvp() {
        return pricePvp;
    }

    public int getRequiredQuest() {
        return required_quest;
    }

    public TeleportType getType() {
        return type;
    }
}
