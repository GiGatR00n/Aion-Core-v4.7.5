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
package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAttribute;

import com.aionemu.gameserver.controllers.observer.AttackShieldObserver;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HealType;

/**
 * @author kecimis
 */
public class ConvertHealEffect extends ShieldEffect {

    @XmlAttribute
    protected HealType type;
    @XmlAttribute(name = "hitpercent")
    protected boolean hitPercent;

    @Override
    public void startEffect(final Effect effect) {
        int skillLvl = effect.getSkillLevel();
        int valueWithDelta = value + delta * skillLvl;
        int hitValueWithDelta = hitvalue + hitdelta * skillLvl;

        AttackShieldObserver asObserver = new AttackShieldObserver(hitValueWithDelta, valueWithDelta, this.percent, this.hitPercent, effect, this.hitType, getType(), this.hitTypeProb, 0, 0, this.type, 0, 0);

        effect.getEffected().getObserveController().addAttackCalcObserver(asObserver);
        effect.setAttackShieldObserver(asObserver, position);
        effect.getEffected().getEffectController().setUnderShield(true);
    }

    /**
     * shieldType 0: convertHeal 1: reflector 2: normal shield 8: protect
     *
     * @return
     */
    public int getType() {
        return 0;
    }
}
