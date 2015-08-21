/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package pirate.events.xml;

import com.aionemu.commons.utils.Rnd;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javolution.util.FastList;

/**
 *
 * @author f14shm4n
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rItemGroup")
public class EventRewardItemGroup {

    // If true - that of the entire group of objects is selected specified number of items
    @XmlAttribute(name = "random")
    public boolean isRandom = false;
    @XmlAttribute(name = "random_count")
    public int randomCount = 1;
    @XmlElement(name = "item")
    public List<EventRewardItem> items;

    public boolean isRandom() {
        return isRandom;
    }

    public int getRandomCount() {
        return randomCount;
    }

    public List<EventRewardItem> getItems() {
        if (this.items == null) {
            this.items = new ArrayList<EventRewardItem>();
        }
        if (this.isRandom) {
            List<EventRewardItem> result = new FastList<EventRewardItem>(items);
            while (result.size() > this.randomCount) {
                result.remove(Rnd.get(0, result.size() - 1));
            }
            return result;
        }
        return items;
    }
}
