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
package com.aionemu.gameserver.model.autogroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Collections;
import java.util.List;

/**
 * @author MrPoke
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoGroup")
public class AutoGroup {

    @XmlAttribute(required = true)
    protected int id;
    @XmlAttribute(required = true)
    protected int instanceId;
    @XmlAttribute(name = "name_id")
    protected int nameId;
    @XmlAttribute(name = "title_id")
    protected int titleId;
    @XmlAttribute(name = "min_lvl")
    protected int minLvl;
    @XmlAttribute(name = "max_lvl")
    protected int maxLvl;
    @XmlAttribute(name = "register_quick")
    protected boolean registerQuick;
    @XmlAttribute(name = "register_group")
    protected boolean registerGroup;
    @XmlAttribute(name = "register_new")
    protected boolean registerNew;
    @XmlAttribute(name = "npc_ids")
    protected List<Integer> npcIds;

    public int getId() {
        return id;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public int getNameId() {
        return nameId;
    }

    public int getTitleId() {
        return titleId;
    }

    public int getMinLvl() {
        return minLvl;
    }

    public int getMaxLvl() {
        return maxLvl;
    }

    public boolean hasRegisterQuick() {
        return registerQuick;
    }

    public boolean hasRegisterGroup() {
        return registerGroup;
    }

    public boolean hasRegisterNew() {
        return registerNew;
    }

    public List<Integer> getNpcIds() {
        if (npcIds == null) {
            npcIds = Collections.emptyList();
        }
        return this.npcIds;
    }
}
