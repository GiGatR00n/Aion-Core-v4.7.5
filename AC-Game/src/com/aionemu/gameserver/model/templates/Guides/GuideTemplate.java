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
package com.aionemu.gameserver.model.templates.Guides;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuideTemplate")
public class GuideTemplate {

    @XmlAttribute(name = "level")
    private int level;
    @XmlAttribute(name = "classType")
    private PlayerClass classType;
    @XmlAttribute(name = "title")
    private String title;
    @XmlAttribute(name = "race")
    private Race race;
    @XmlElement(name = "reward_info")
    private String rewardInfo = StringUtils.EMPTY;
    @XmlElement(name = "message")
    private String message = StringUtils.EMPTY;
    @XmlElement(name = "select")
    private String select = StringUtils.EMPTY;
    @XmlElement(name = "survey")
    private List<SurveyTemplate> surveys;
    @XmlAttribute(name = "rewardCount")
    private int rewardCount;
    @XmlTransient
    private boolean isActivated = true;

    /**
     * @return the level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @return the classId
     */
    public PlayerClass getPlayerClass() {
        return this.classType;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return the race
     */
    public Race getRace() {
        return this.race;
    }

    /**
     * @return the surveys
     */
    public List<SurveyTemplate> getSurveys() {
        return this.surveys;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return the select
     */
    public String getSelect() {
        return this.select;
    }

    /**
     * @return the select
     */
    public String getRewardInfo() {
        return this.rewardInfo;
    }

    public int getRewardCount() {
        return this.rewardCount;
    }

    /**
     * @return the isActivated
     */
    public boolean isActivated() {
        return isActivated;
    }

    /**
     * @param isActivated the isActivated to set
     */
    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }
}
