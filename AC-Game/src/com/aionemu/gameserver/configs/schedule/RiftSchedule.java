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
import com.aionemu.gameserver.model.templates.rift.OpenRift;
import org.apache.commons.io.FileUtils;

import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.List;

/**
 * @author Source
 */
@XmlRootElement(name = "rift_schedule")
@XmlAccessorType(XmlAccessType.FIELD)
public class RiftSchedule {

    @XmlElement(name = "rift", required = true)
    private List<Rift> riftsList;

    public List<Rift> getRiftsList() {
        return riftsList;
    }

    public void setRiftsList(List<Rift> fortressList) {
        this.riftsList = fortressList;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "rift")
    public static class Rift {

        @XmlAttribute(required = true)
        private int id;
        @XmlElement(name = "open")
        private List<OpenRift> openRift;

        public int getWorldId() {
            return id;
        }

        public List<OpenRift> getRift() {
            return openRift;
        }
    }

    public static RiftSchedule load() {
        RiftSchedule rs;
        try {
            String xml = FileUtils.readFileToString(new File("./config/schedule/rift_schedule.xml"));
            rs = JAXBUtil.deserialize(xml, RiftSchedule.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize rifts", e);
        }
        return rs;
    }
}
