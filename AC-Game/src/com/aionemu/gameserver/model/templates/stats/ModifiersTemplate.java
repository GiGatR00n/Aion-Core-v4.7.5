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
package com.aionemu.gameserver.model.templates.stats;

import com.aionemu.gameserver.model.stats.calc.functions.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xavier
 * @modified Rolandas
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "modifiers")
public class ModifiersTemplate {

    @XmlElements({
            @XmlElement(name = "sub", type = StatSubFunction.class),
            @XmlElement(name = "add", type = StatAddFunction.class),
            @XmlElement(name = "rate", type = StatRateFunction.class),
            @XmlElement(name = "set", type = StatSetFunction.class)
    })
    private List<StatFunction> modifiers;
    @XmlAttribute
    private float chance = 100f;

    public List<StatFunction> getModifiers() {
        return modifiers;
    }

    /**
     * @return the chance
     */
    public float getChance() {
        return chance;
    }
}
