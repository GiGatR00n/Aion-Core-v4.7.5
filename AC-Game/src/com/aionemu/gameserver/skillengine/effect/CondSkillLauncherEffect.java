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

import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.controllers.observer.ObserverType;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HealType;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;

/**
 * @author Sippolo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CondSkillLauncherEffect")
public class CondSkillLauncherEffect extends EffectTemplate {

    @XmlAttribute(name = "skill_id")
    protected int skillId;
    @XmlAttribute
    protected HealType type;

    //TODO what if you fall? effect is not applied? what if you use skill that consume hp?
    @Override
    public void applyEffect(Effect effect) {
        effect.addToEffectedController();
    }

    @Override
    public void endEffect(Effect effect) {
        effect.getEffected().getGameStats().endEffect(effect);
        ActionObserver observer = effect.getActionObserver(position);
        effect.getEffected().getObserveController().removeObserver(observer);
    }

    @Override
    public void startEffect(final Effect effect) {
        ActionObserver observer = new ActionObserver(ObserverType.ATTACKED) {
            @Override
            public void attacked(Creature creature) {
                if (!effect.getEffected().getEffectController().hasAbnormalEffect(skillId)) {
                    if (effect.getEffected().getLifeStats().getCurrentHp() <= (int) (value / 100f * effect.getEffected().getLifeStats().getMaxHp())) {
                        SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
                        Effect e = new Effect(effect.getEffector(), effect.getEffected(), template, template.getLvl(), 0);
                        e.initialize();
                        e.applyEffect();
                    }
                }
            }
        };
        effect.getEffected().getObserveController().addObserver(observer);
        effect.setActionObserver(observer, position);
    }
}
