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
package com.aionemu.gameserver.model.templates.assemblednpc;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssembledNpcTemplate")
public class AssembledNpcTemplate {

    @XmlAttribute(name = "nr")
    private int nr;
    @XmlAttribute(name = "routeId")
    private int routeId;
    @XmlAttribute(name = "mapId")
    private int mapId;
    @XmlAttribute(name = "liveTime")
    private int liveTime;
    @XmlElement(name = "assembled_part")
    private List<AssembledNpcPartTemplate> parts;

    public int getNr() {
        return nr;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getMapId() {
        return mapId;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public List<AssembledNpcPartTemplate> getAssembledNpcPartTemplates() {
        return parts;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AssembledNpcPart")
    public static class AssembledNpcPartTemplate {

        @XmlAttribute(name = "npcId")
        private int npcId;
        @XmlAttribute(name = "staticId")
        private int staticId;

        public int getNpcId() {
            return npcId;
        }

        public int getStaticId() {
            return staticId;
        }
    }
}
