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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.TransformType;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Sweetkr, kecimis
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransformEffect")
public abstract class TransformEffect extends EffectTemplate {

    @XmlAttribute
    protected int model;
    @XmlAttribute
    protected TransformType type = TransformType.NONE;
    @XmlAttribute
    protected int panelid;
    @XmlAttribute
    protected AbnormalState state = AbnormalState.BUFF;

    @Override
    public void applyEffect(Effect effect) {
        effect.addToEffectedController();
        if (state != null) {
            effect.getEffected().getEffectController().setAbnormal(state.getId());
            effect.setAbnormal(state.getId());
        }
    }

    public void endEffect(Effect effect) {
        final Creature effected = effect.getEffected();

        if (state != null) {
            effected.getEffectController().unsetAbnormal(state.getId());
        }

        if (effected instanceof Player) {
            int newModel = 0;
            TransformType transformType = TransformType.PC;
            for (Effect tmp : effected.getEffectController().getAbnormalEffects()) {
                for (EffectTemplate template : tmp.getEffectTemplates()) {
                    if (template instanceof TransformEffect) {
                        if (((TransformEffect) template).getTransformId() == model) {
                            continue;
                        }
                        newModel = ((TransformEffect) template).getTransformId();
                        transformType = ((TransformEffect) template).getTransformType();
                        break;
                    }
                }
            }
            effected.getTransformModel().setModelId(newModel);
            effected.getTransformModel().setTransformType(transformType);
        } else if (effected instanceof Summon) {
            effected.getTransformModel().setModelId(0);
        } else if (effected instanceof Npc) {
            effected.getTransformModel().setModelId(effected.getObjectTemplate().getTemplateId());
        }
        effected.getTransformModel().setPanelId(0);
        PacketSendUtility.broadcastPacketAndReceive(effected, new SM_TRANSFORM(effected, 0, false));

        if (effected instanceof Player) {
            ((Player) effected).setTransformed(false);
        }
    }

    public void startEffect(Effect effect) {
        final Creature effected = effect.getEffected();
        effected.getTransformModel().setModelId(model);
        effected.getTransformModel().setPanelId(panelid);
        effected.getTransformModel().setTransformType(effect.getTransformType());
        PacketSendUtility.broadcastPacketAndReceive(effected, new SM_TRANSFORM(effected, panelid, true));

        if (effected instanceof Player) {
            ((Player) effected).setTransformed(true);
        }
    }

    public TransformType getTransformType() {
        return type;
    }

    public int getTransformId() {
        return model;
    }

    public int getPanelId() {
        return panelid;
    }
}
