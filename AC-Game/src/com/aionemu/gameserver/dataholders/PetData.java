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

import com.aionemu.gameserver.model.templates.pet.PetTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * This is a container holding and serving all {@link PetTemplate}
 * instances.<br>
 *
 * @author IlBuono
 */
@XmlRootElement(name = "pets")
@XmlAccessorType(XmlAccessType.FIELD)
public class PetData {

    @XmlElement(name = "pet")
    private List<PetTemplate> pets;
    /**
     * A map containing all pet templates
     */
    private TIntObjectHashMap<PetTemplate> petData = new TIntObjectHashMap<PetTemplate>();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (PetTemplate pet : pets) {
            petData.put(pet.getId(), pet);
        }
        pets.clear();
        pets = null;
    }

    public int size() {
        return petData.size();
    }

    /**
     * /** Returns an {@link PetTemplate} object with given id.
     *
     * @param id id of Pet
     * @return PetTemplate object containing data about Pet with that id.
     */
    public PetTemplate getPetTemplate(int id) {
        return petData.get(id);
    }
}
