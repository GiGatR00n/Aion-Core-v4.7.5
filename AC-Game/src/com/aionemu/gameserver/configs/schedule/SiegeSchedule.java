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
package com.aionemu.gameserver.configs.schedule;

import com.aionemu.commons.utils.xml.JAXBUtil;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.List;

/**
 * @author SoulKeeper, Source
 */
@XmlRootElement(name = "siege_schedule")
@XmlAccessorType(XmlAccessType.FIELD)
public class SiegeSchedule {

    @XmlElement(name = "fortress", required = true)
    private List<Fortress> fortressesList;
    @XmlElement(name = "source", required = true)
    private List<Source> sourcesList;

    public List<Fortress> getFortressesList() {
        return fortressesList;
    }

    public void setFortressesList(List<Fortress> fortressList) {
        this.fortressesList = fortressList;
    }

    public List<Source> getSourcesList() {
        return sourcesList;
    }

    public void setSourcesList(List<Source> sourceList) {
        this.sourcesList = sourceList;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "fortress")
    public static class Fortress {

        @XmlAttribute(required = true)
        private int id;
        @XmlElement(name = "siegeTime", required = true)
        private List<String> siegeTimes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getSiegeTimes() {
            return siegeTimes;
        }

        public void setSiegeTimes(List<String> siegeTimes) {
            this.siegeTimes = siegeTimes;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "source")
    public static class Source {

        @XmlAttribute(required = true)
        private int id;
        @XmlElement(name = "siegeTime", required = true)
        private List<String> siegeTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getSiegeTimes() {
            return siegeTime;
        }

        public void setSiegeTimes(List<String> siegeTime) {
            this.siegeTime = siegeTime;
        }
    }

    public static SiegeSchedule load() {
        SiegeSchedule ss;
        try {
            String xml = FileUtils.readFileToString(new File("./config/schedule/siege_schedule.xml"));
            ss = JAXBUtil.deserialize(xml, SiegeSchedule.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize sieges", e);
        }
        return ss;
    }
}
