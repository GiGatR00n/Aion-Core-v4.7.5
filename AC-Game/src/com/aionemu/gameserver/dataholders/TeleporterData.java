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
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This is a container holding and serving all {@link NpcTemplate}
 * instances.<br>
 * Briefly: Every {@link Npc} instance represents some class of NPCs among which
 * each have the same id, name, items, statistics. Data for such NPC class is
 * defined in {@link NpcTemplate} and is uniquely identified by npc id.
 *
 * @author orz
 */
@XmlRootElement(name = "npc_teleporter")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleporterData {

    @XmlElement(name = "teleporter_template")
    private List<TeleporterTemplate> tlist;
    /**
     * A map containing all trade list templates
     */
    private TIntObjectHashMap<TeleporterTemplate> npctlistData = new TIntObjectHashMap<TeleporterTemplate>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (TeleporterTemplate template : tlist) {
            npctlistData.put(template.getTeleportId(), template);
        }
    }

    public int size() {
        return npctlistData.size();
    }

    public TeleporterTemplate getTeleporterTemplateByNpcId(int npcId) {
        for (TeleporterTemplate template : npctlistData.valueCollection()) {
            if (template.containNpc(npcId)) {
                return template;
            }
        }
        return null;
    }

    public TeleporterTemplate getTeleporterTemplateByTeleportId(int teleportId) {
        return npctlistData.get(teleportId);
    }
}
