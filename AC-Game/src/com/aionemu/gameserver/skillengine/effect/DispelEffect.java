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

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.skillengine.model.DispelType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
public class DispelEffect extends EffectTemplate {

    @XmlElement(type = Integer.class)
    protected List<Integer> effectids;
    @XmlElement
    protected List<String> effecttype;
    @XmlElement
    protected List<String> slottype;
    @XmlAttribute
    protected DispelType dispeltype;
    @XmlAttribute
    protected Integer value;

    @Override
    public void applyEffect(Effect effect) {
        if (effect.getEffected() == null || effect.getEffected().getEffectController() == null) {
            return;
        }

        if (dispeltype == null) {
            return;
        }

        if ((dispeltype == DispelType.EFFECTID || dispeltype == DispelType.EFFECTIDRANGE)
                && effectids == null) {
            return;
        }

        if (dispeltype == DispelType.EFFECTTYPE && effecttype == null) {
            return;
        }

        if (dispeltype == DispelType.SLOTTYPE && slottype == null) {
            return;
        }

        switch (dispeltype) {
            case EFFECTID:
                for (Integer effectId : effectids) {
                    effect.getEffected().getEffectController().removeEffectByEffectId(effectId);
                }
                break;
            case EFFECTIDRANGE:
                for (int i = effectids.get(0); i <= effectids.get(1); i++) {
                    effect.getEffected().getEffectController().removeEffectByEffectId(i);
                }
                break;
            case EFFECTTYPE:
                if (effecttype == null) {
                    return;
                }

                //fix for AT HyperGate skill - 3853 , 3818
                for (String type : effecttype) {
                    if (type.equals("RIDEROBOT")){
                        Player player = (Player) effect.getEffector();
                        if (checkEmbark(player) != 0){
                            player.getEffectController().removeEffect(checkEmbark(player));
                        }
                    } else {
                        AbnormalState abnormalType = AbnormalState.getIdByName(type);
                        if (abnormalType != null && effect.getEffected().getEffectController().isAbnormalSet(abnormalType)) {
                            for (Effect ef : effect.getEffected().getEffectController().getAbnormalEffects()) {
                                if ((ef.getAbnormals() & abnormalType.getId()) == abnormalType.getId()) {
                                    ef.endEffect();
                                }
                            }
                        }
                    }
                }

                break;
            case SLOTTYPE:
                for (String type : slottype) {
                    effect.getEffected().getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.valueOf(type));
                }
                break;
        }
        PacketSendUtility.broadcastPacketAndReceive(effect.getEffected(), new SM_PLAYER_STATE(effect.getEffected()));
    }

    private int checkEmbark(Player player){
        //All Embark Skills 1 to 5
        int[] embarkSkills = {3601, 3600, 3599, 3598, 3597};
        for(int eIds : embarkSkills){
            if(player.getEffectController().isNoshowPresentBySkillId(eIds)){
                return eIds;
            }
        }
        return 0;
    }
}