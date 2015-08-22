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

import com.aionemu.gameserver.controllers.SummonController;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_USESKILL;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer modified by Sippolo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PetOrderUseUltraSkillEffect")
public class PetOrderUseUltraSkillEffect extends EffectTemplate {

    @XmlAttribute
    protected boolean release;

    @Override
    public void applyEffect(Effect effect) {
        Player effector = (Player) effect.getEffector();

        if (effector.getSummon() == null) {
            return;
        }

        int effectorId = effector.getSummon().getObjectId();

        int npcId = effector.getSummon().getNpcId();
        int orderSkillId = effect.getSkillId();

        int petUseSkillId = DataManager.PET_SKILL_DATA.getPetOrderSkill(orderSkillId, npcId);
        int targetId = effect.getEffected().getObjectId();

        // Handle automatic release if skill expects so
        if (release) {
            SummonController controller = effector.getSummon().getController();
            if (controller instanceof SummonController) {
                effector.getSummon().getController().setReleaseAfterSkill(petUseSkillId);
            }
        }
        PacketSendUtility.sendPacket(effector, new SM_SUMMON_USESKILL(effectorId, petUseSkillId, 1, targetId));
    }

    @Override
    public void calculate(Effect effect) {
        if (effect.getEffector() instanceof Player && effect.getEffected() != null) {
            super.calculate(effect, null, null);
        }
    }
}
