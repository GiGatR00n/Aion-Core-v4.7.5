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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_IMMOBILIZE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Created by Kill3r
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StunAlwaysEffect")
public class StunAlwaysEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect){
        if(!effect.getEffected().getEffectController().hasMagicalStateEffect() && !effect.getEffected().getEffectController().hasAbnormalEffect(AbnormalState.STUN.getId())){
            effect.addToEffectedController();
            effect.setIsMagicalState(true);
        }
    }

    @Override
    public void startEffect(Effect effect){
        final Creature effected = effect.getEffected();
        effected.getController().cancelCurrentSkill();
        effected.getMoveController().abortMove();
        effect.getEffected().getEffectController().setAbnormal(AbnormalState.STUN.getId());
        effect.setAbnormal(AbnormalState.STUN.getId());
        PacketSendUtility.broadcastPacketAndReceive(effect.getEffected(), new SM_TARGET_IMMOBILIZE(effect.getEffected()));
    }

    @Override
    public void endEffect(Effect effect){
        effect.setIsMagicalState(false);
        effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.STUN.getId());
    }
}