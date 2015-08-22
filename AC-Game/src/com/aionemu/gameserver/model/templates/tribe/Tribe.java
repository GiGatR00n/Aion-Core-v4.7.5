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
package com.aionemu.gameserver.model.templates.tribe;

import com.aionemu.gameserver.model.TribeClass;

import javax.xml.bind.annotation.*;
import java.util.Collections;
import java.util.List;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tribe")
public class Tribe {

    @XmlList
    protected List<TribeClass> aggro;
    @XmlList
    protected List<TribeClass> hostile;
    @XmlList
    protected List<TribeClass> friend;
    @XmlList
    protected List<TribeClass> neutral;
    @XmlList
    protected List<TribeClass> none;
    @XmlList
    protected List<TribeClass> support;
    @XmlAttribute
    protected TribeClass base = TribeClass.NONE;
    @XmlAttribute(required = true)
    protected TribeClass name;

    public List<TribeClass> getAggro() {
        if (aggro == null) {
            aggro = Collections.emptyList();
        }
        return this.aggro;
    }

    public List<TribeClass> getHostile() {
        if (hostile == null) {
            hostile = Collections.emptyList();
        }
        return this.hostile;
    }

    public List<TribeClass> getFriend() {
        if (friend == null) {
            friend = Collections.emptyList();
        }
        return this.friend;
    }

    public List<TribeClass> getNeutral() {
        if (neutral == null) {
            neutral = Collections.emptyList();
        }
        return this.neutral;
    }

    public List<TribeClass> getNone() {
        if (none == null) {
            none = Collections.emptyList();
        }
        return this.none;
    }

    public List<TribeClass> getSupport() {
        if (support == null) {
            support = Collections.emptyList();
        }
        return this.support;
    }

    public TribeClass getBase() {
        return base == TribeClass.NONE ? name : base;
    }

    public TribeClass getName() {
        return name;
    }

    public final boolean isGuard() {
        return name.isGuard();
    }

    public final boolean isBasic() {
        return name.isBasicClass();
    }

    @Override
    public String toString() {
        return name + " (" + base + ")";
    }
}
