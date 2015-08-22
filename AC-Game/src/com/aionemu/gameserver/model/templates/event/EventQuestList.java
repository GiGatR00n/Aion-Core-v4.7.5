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
package com.aionemu.gameserver.model.templates.event;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

/**
 * @author Rolandas
 */
@XmlType(name = "EventQuestList", propOrder = {"startable", "maintainable"})
@XmlAccessorType(XmlAccessType.FIELD)
public class EventQuestList {

    protected String startable;
    protected String maintainable;
    @XmlTransient
    private List<Integer> startQuests;
    @XmlTransient
    private List<Integer> maintainQuests;

    void afterUnmarshal(Unmarshaller u, Object parent) {
        if (startable != null) {
            startQuests = getQuestsFromData(startable);
        }

        if (maintainable != null) {
            maintainQuests = getQuestsFromData(maintainable);
        }
    }

    List<Integer> getQuestsFromData(String data) {
        Set<String> q = new HashSet<String>();
        Collections.addAll(q, data.split(";"));
        List<Integer> result = new ArrayList<Integer>();

        if (q.size() > 0) {
            result = new ArrayList<Integer>();
            Iterator<String> it = q.iterator();
            while (it.hasNext()) {
                result.add(Integer.parseInt(it.next()));
            }
        }

        return result;
    }

    /**
     * @return the startQuests (automatically started on logon)
     */
    public List<Integer> getStartableQuests() {
        if (startQuests == null) {
            startQuests = new ArrayList<Integer>();
        }
        return startQuests;
    }

    /**
     * @return the maintainQuests (started indirectly from other quests)
     */
    public List<Integer> getMaintainQuests() {
        if (maintainQuests == null) {
            maintainQuests = new ArrayList<Integer>();
        }
        return maintainQuests;
    }
}
