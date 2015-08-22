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
package com.aionemu.gameserver.skillengine.periodicaction;

import javax.xml.bind.annotation.XmlAttribute;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author antness
 */
public class MpUsePeriodicAction extends PeriodicAction {

    @XmlAttribute(name = "value")
    protected int value;
    @XmlAttribute(name = "ratio")
    protected boolean ratio;

    @Override
	public void act(final Effect effect) {
        Creature effected = effect.getEffected();
        int maxMp = effected.getGameStats().getMaxMp().getCurrent();
        int requiredMp = (int) (maxMp * (value / 100f));
        if (effected.getLifeStats().getCurrentMp() < requiredMp) {
            effect.endEffect();
			return;
		}
        if (ratio) {
        	//requiredMp = (int) ((effected.getLifeStats().getMaxMp() * requiredMp) / 100);
            requiredMp = value;
        }

        effected.getLifeStats().reduceMp(requiredMp);
    }
}
