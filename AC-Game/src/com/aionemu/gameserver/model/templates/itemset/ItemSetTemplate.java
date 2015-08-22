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
package com.aionemu.gameserver.model.templates.itemset;

import com.aionemu.gameserver.model.stats.calc.StatOwner;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author ATracer, modified by Antivirus
 */
@XmlRootElement(name = "itemset")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSetTemplate implements StatOwner {

    @XmlElement(required = true)
    protected List<ItemPart> itempart;
    @XmlElement(required = true)
    protected List<PartBonus> partbonus;
    protected FullBonus fullbonus;
    @XmlAttribute
    protected String name;
    @XmlAttribute
    protected int id;

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        if (fullbonus != null) {
            // Set number of items to apply the full bonus
            fullbonus.setNumberOfItems(itempart.size());
        }
    }

    /**
     * @return the itempart
     */
    public List<ItemPart> getItempart() {
        return itempart;
    }

    /**
     * @return the partbonus
     */
    public List<PartBonus> getPartbonus() {
        return partbonus;
    }

    /**
     * @return the fullbonus
     */
    public FullBonus getFullbonus() {
        return fullbonus;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
}
