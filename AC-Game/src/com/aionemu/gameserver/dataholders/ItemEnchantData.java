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

import com.aionemu.gameserver.model.templates.item.ItemEnchantTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "enchant_templates")
public class ItemEnchantData {

    @XmlElement(name = "enchant_template", required = true)
    protected List<ItemEnchantTemplate> enchantTemplates;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @XmlTransient
    private TIntObjectHashMap<ItemEnchantTemplate> authorizes = new TIntObjectHashMap();

    void afterUnmarshal(@SuppressWarnings("unused") Unmarshaller u, @SuppressWarnings("unused") Object parent) {
        for (ItemEnchantTemplate it : this.enchantTemplates) {
            getEnchantMap().put(it.getId(), it);
        }
    }

    private TIntObjectHashMap<ItemEnchantTemplate> getEnchantMap() {
        return this.authorizes;
    }

    public ItemEnchantTemplate getEnchantTemplate(int id) {
        return (ItemEnchantTemplate) this.authorizes.get(id);
    }

    public int size() {
        return this.authorizes.size();
    }
}
