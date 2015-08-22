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

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.templates.portal.PortalDialog;
import com.aionemu.gameserver.model.templates.portal.PortalPath;
import com.aionemu.gameserver.model.templates.portal.PortalScroll;
import com.aionemu.gameserver.model.templates.portal.PortalUse;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "portalUse",
        "portalDialog",
        "portalScroll"
})
@XmlRootElement(name = "portal_templates2")
public class Portal2Data {

    @XmlElement(name = "portal_use")
    protected List<PortalUse> portalUse;
    @XmlElement(name = "portal_dialog")
    protected List<PortalDialog> portalDialog;
    @XmlElement(name = "portal_scroll")
    protected List<PortalScroll> portalScroll;
    @XmlTransient
    private TIntObjectHashMap<PortalUse> portalUses = new TIntObjectHashMap<PortalUse>();
    @XmlTransient
    private TIntObjectHashMap<PortalDialog> portalDialogs = new TIntObjectHashMap<PortalDialog>();
    @XmlTransient
    private Map<String, PortalScroll> portalScrolls = new HashMap<String, PortalScroll>();

    void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        if (portalUse != null) {
            for (PortalUse portal : portalUse) {
                portalUses.put(portal.getNpcId(), portal);
            }
        }
        if (portalDialog != null) {
            for (PortalDialog portal : portalDialog) {
                portalDialogs.put(portal.getNpcId(), portal);
            }
        }
        if (portalScroll != null) {
            for (PortalScroll portal : portalScroll) {
                portalScrolls.put(portal.getName(), portal);
            }
        }
    }

    public int size() {
        return portalScrolls.size() + portalDialogs.size() + portalUses.size();
    }

    public PortalPath getPortalDialog(int npcId, int dialogId, Race race) {
        PortalDialog portal = portalDialogs.get(npcId);
        if (portal != null) {
            for (PortalPath path : portal.getPortalPath()) {
                if (path.getDialog() == dialogId && (race.equals(path.getRace())
                        || path.getRace().equals(Race.PC_ALL))) {
                    return path;
                }
            }
        }
        return null;
    }

    public boolean isPortalNpc(int npcId) {
        return portalUses.get(npcId) != null || portalDialogs.get(npcId) != null;
    }

    public PortalUse getPortalUse(int npcId) {
        return portalUses.get(npcId);
    }

    public PortalScroll getPortalScroll(String name) {
        return portalScrolls.get(name);
    }

    public int getTeleportDialogId(int npcId) {
        PortalDialog portal = portalDialogs.get(npcId);
        return portal == null ? 1011 : portal.getTeleportDialogId();
    }
}
