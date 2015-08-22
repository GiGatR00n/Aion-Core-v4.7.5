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

import com.aionemu.gameserver.model.templates.assemblednpc.AssembledNpcTemplate;
import javolution.util.FastMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

/**
 * @author xTz
 */
@XmlRootElement(name = "assembled_npcs")
@XmlAccessorType(XmlAccessType.FIELD)
public class AssembledNpcsData {

    @XmlElement(name = "assembled_npc", type = AssembledNpcTemplate.class)
    private List<AssembledNpcTemplate> templates;
    private final Map<Integer, AssembledNpcTemplate> assembledNpcsTemplates = new FastMap<Integer, AssembledNpcTemplate>().shared();

    /**
	 * @param u  
     * @param parent 
	 */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (AssembledNpcTemplate template : templates) {
            assembledNpcsTemplates.put(template.getNr(), template);
        }
        templates.clear();
        templates = null;
    }

    public int size() {
        return assembledNpcsTemplates.size();
    }

    public AssembledNpcTemplate getAssembledNpcTemplate(Integer i) {
        return assembledNpcsTemplates.get(i);
    }
}
