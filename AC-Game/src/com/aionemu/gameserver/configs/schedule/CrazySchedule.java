/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
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

import java.io.File;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.FileUtils;

import com.aionemu.commons.utils.xml.JAXBUtil;

/**
 * @author Cloudious
 * @rework Ever
 */
@XmlRootElement(name = "crazy_daeva_schedule")
@XmlAccessorType(XmlAccessType.FIELD)
public class CrazySchedule {

    @XmlElement(name = "schedule", required = true)
    private List<Schedule> schedule;

    public List<Schedule> getScheduleList() {
        return schedule;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.schedule = scheduleList;
    }

    public static CrazySchedule load() {
        CrazySchedule ss;
        try {
            String xml = FileUtils.readFileToString(new File("./config/schedule/crazy_daeva_schedule.xml"));
            ss = (CrazySchedule) JAXBUtil.deserialize(xml, CrazySchedule.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Crazy Daeva Schedule...", e);
        }
        return ss;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "schedule")
    public static class Schedule {

        @XmlAttribute(required = true)
        private int id;
        @XmlElement(name = "scheduleTime", required = true)
        private List<String> scheduleTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getScheduleTimes() {
            return scheduleTime;
        }

        public void setScheduleTimes(List<String> scheduleTimes) {
            this.scheduleTime = scheduleTimes;
        }
    }
}
