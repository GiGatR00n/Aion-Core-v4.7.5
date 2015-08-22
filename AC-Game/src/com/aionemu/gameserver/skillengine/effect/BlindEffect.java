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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.controllers.observer.AttackCalcObserver;
import com.aionemu.gameserver.controllers.observer.AttackStatusObserver;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BlindEffect")
public class BlindEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        effect.getEffected().getEffectController().removeHideEffects();
        effect.addToEffectedController();
    }

    @Override
    public void calculate(Effect effect) {
        super.calculate(effect, StatEnum.BLIND_RESISTANCE, null);
    }

    @Override
    public void startEffect(Effect effect) {
        effect.setAbnormal(AbnormalState.BLIND.getId());
        effect.getEffected().getEffectController().setAbnormal(AbnormalState.BLIND.getId());
        AttackCalcObserver acObserver = new AttackStatusObserver(value, AttackStatus.DODGE) {
            @Override
            public boolean checkAttackerStatus(AttackStatus status) {
                return Rnd.get(0, 100) <= value;
            }
        };
        effect.getEffected().getObserveController().addAttackCalcObserver(acObserver);
        effect.setAttackStatusObserver(acObserver, position);
    }

    @Override
    public void endEffect(Effect effect) {
        AttackCalcObserver acObserver = effect.getAttackStatusObserver(position);
        if (acObserver != null) {
            effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver);
        }
        effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.BLIND.getId());
    }
}
