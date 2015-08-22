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
package com.aionemu.gameserver.model.templates.ride;

import com.aionemu.gameserver.model.templates.Bounds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RideInfo", propOrder = {"bounds"})
public class RideInfo {

    protected Bounds bounds;
    @XmlAttribute(name = "cost_fp")
    protected Integer costFp;
    @XmlAttribute(name = "start_fp")
    protected int startFp;
    @XmlAttribute(name = "sprint_speed")
    protected float sprintSpeed;
    @XmlAttribute(name = "fly_speed")
    protected float flySpeed;
    @XmlAttribute(name = "move_speed")
    protected float moveSpeed;
    @XmlAttribute
    protected Integer type;
    @XmlAttribute(required = true)
    protected int id;

    /**
     * Gets the value of the bounds property.
     *
     * @return possible object is {@link Bounds }
     */
    public Bounds getBounds() {
        return bounds;
    }

    public Integer getCostFp() {
        return costFp;
    }

    public int getStartFp() {
        return startFp;
    }

    public float getSprintSpeed() {
        return sprintSpeed;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public Integer getType() {
        return type;
    }

    public int getNpcId() {
        return id;
    }

    public boolean canSprint() {
        return sprintSpeed != 0;
    }
}
