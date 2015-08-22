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
package com.aionemu.gameserver.model.templates.siegelocation;

import com.aionemu.gameserver.model.siege.SiegeType;

import javax.xml.bind.annotation.*;
import java.util.Collections;
import java.util.List;

/**
 * @author Sarynth modified by antness & Source & Wakizashi
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "siegelocation")
public class SiegeLocationTemplate {

    @XmlAttribute(name = "id")
    protected int id;
    @XmlAttribute(name = "type")
    protected SiegeType type;
    @XmlAttribute(name = "world")
    protected int world;
    @XmlElement(name = "artifact_activation")
    protected ArtifactActivation artifactActivation;
    @XmlElement(name = "siege_reward")
    protected List<SiegeReward> siegeRewards;
    @XmlElement(name = "legion_reward")
    protected List<SiegeLegionReward> siegeLegionRewards;
    @XmlAttribute(name = "name_id")
    protected int nameId = 0;
    @XmlAttribute(name = "repeat_count")
    protected int repeatCount = 1;
    @XmlAttribute(name = "repeat_interval")
    protected int repeatInterval = 1;
    @XmlAttribute(name = "siege_duration")
    protected int siegeDuration;
    @XmlAttribute(name = "influence")
    protected int influenceValue;
    @XmlList
    @XmlAttribute(name = "fortress_dependency")
    protected List<Integer> fortressDependency;

    /**
     * @return the location id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the location type
     */
    public SiegeType getType() {
        return this.type;
    }

    /**
     * @return the world id
     */
    public int getWorldId() {
        return this.world;
    }

    public ArtifactActivation getActivation() {
        return this.artifactActivation;
    }

    /**
     * @return the reward list
     */
    public List<SiegeReward> getSiegeRewards() {
        return this.siegeRewards;
    }

    /**
     * @return the siege zone
     */
    public List<SiegeLegionReward> getSiegeLegionRewards() {
        return this.siegeLegionRewards;
    }

    /**
     * @return the nameId
     */
    public int getNameId() {
        return nameId;
    }

    /**
     * @return the repeatCount
     */
    public int getRepeatCount() {
        return repeatCount;
    }

    /**
     * @return the repeatInterval
     */
    public int getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * @return the fortressDependency
     */
    public List<Integer> getFortressDependency() {
        if (fortressDependency == null) {
            return Collections.emptyList();
        }
        return fortressDependency;
    }

    /**
     * @return the Duration in Seconds
     */
    public int getSiegeDuration() {
        return this.siegeDuration;
    }

    /**
     * @return the influence Points
     */
    public int getInfluenceValue() {
        return this.influenceValue;
    }
}
