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
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.stats.container.CreatureLifeStats;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.LOG;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.skillengine.model.SkillSubType;
import com.aionemu.gameserver.skillengine.model.SkillType;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author ViAl
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MagicCounterAtkEffect")
public class MagicCounterAtkEffect extends EffectTemplate {

    @XmlAttribute
    protected int maxdmg;

    //TODO bosses are resistent to this?
    @Override
    public void applyEffect(Effect effect) {
        effect.addToEffectedController();
    }

    @Override
    public void startEffect(final Effect effect) {
        final Creature effector = effect.getEffector();
        final Creature effected = effect.getEffected();
        final CreatureLifeStats<? extends Creature> cls = effect.getEffected().getLifeStats();
        ActionObserver observer = new ActionObserver(ObserverType.SKILLUSE) {
            @Override
            public void skilluse(final Skill skill) {
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (skill.getSkillTemplate().getType() == SkillType.MAGICAL
                                && skill.getSkillTemplate().getSubType() == SkillSubType.ATTACK) {
                            if ((int) (cls.getMaxHp() / 100f * value) <= maxdmg) {
                                effected.getController().onAttack(effector, effect.getSkillId(), TYPE.DAMAGE,
                                        (int) (cls.getMaxHp() / 100f * value), true, LOG.REGULAR);
                            } else {
                                effected.getController().onAttack(effector, maxdmg, true);
                            }
                        }
                    }
                }, 0);

            }
        };

        effect.setActionObserver(observer, position);
        effected.getObserveController().addObserver(observer);
    }

    @Override
    public void endEffect(Effect effect) {
        ActionObserver observer = effect.getActionObserver(position);
        if (observer != null) {
            effect.getEffected().getObserveController().removeObserver(observer);
        }
    }
}
