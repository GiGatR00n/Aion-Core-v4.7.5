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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.observer.AttackCalcObserver;
import com.aionemu.gameserver.controllers.observer.AttackShieldObserver;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Ever'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpShieldEffect")
public class MpShieldEffect extends EffectTemplate {

    @XmlAttribute
    protected int hitdelta;
    @XmlAttribute
    protected int hitvalue;
    @XmlAttribute
    protected boolean percent;
    @XmlAttribute
    protected int radius = 0;
    @XmlAttribute
    protected int minradius = 0;
    @XmlAttribute
    protected Race condrace = null;

    public void applyEffect(Effect effect)
    {
      if ((this.condrace != null) && (effect.getEffected().getRace() != this.condrace)) {
        return;
      }
      effect.addToEffectedController();
    }
    
    public void calculate(Effect effect)
    {
      effect.addSucessEffect(this);
    }
    
    public void startEffect(Effect effect)
    {
      int skillLvl = effect.getSkillLevel();
      int valueWithDelta = this.value + this.delta * skillLvl;
      int hitValueWithDelta = this.hitvalue + this.hitdelta * skillLvl;
      
      AttackShieldObserver asObserver = new AttackShieldObserver(hitValueWithDelta, valueWithDelta, this.percent, effect, this.hitType, getType(), this.hitTypeProb);
      

      effect.getEffected().getObserveController().addAttackCalcObserver(asObserver);
      effect.setAttackShieldObserver(asObserver, this.position);
      effect.getEffected().getEffectController().setUnderShield(true);
    }
    
    public void endEffect(Effect effect)
    {
      AttackCalcObserver acObserver = effect.getAttackShieldObserver(this.position);
      if (acObserver != null) {
        effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver);
      }
      effect.getEffected().getEffectController().setUnderShield(false);
    }
    
    public int getType()
    {
      return 16;
    }
}