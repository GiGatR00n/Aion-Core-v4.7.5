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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.controllers.observer.ObserverType;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.EquipType;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RIDE_ROBOT;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rolandas
 * @Reworked Kill3r
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RideRobotEffect")
public class RideRobotEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        effect.addToEffectedController();
    }

    @Override
    public void startEffect(final Effect effect) {
        final Player player = (Player) effect.getEffected();
        final Item key = player.getEquipment().getMainHandWeapon();
        ItemTemplate template = DataManager.ITEM_DATA.getItemTemplate(key.getItemSkinTemplate().getTemplateId());
        PacketSendUtility.broadcastPacketAndReceive(player, new SM_RIDE_ROBOT(player.getObjectId(), template.getRobotId()));
        player.setRobotId(template.getRobotId());

        ActionObserver obsZ = new ActionObserver(ObserverType.SKILLUSE){
            @Override
            public void skilluse(Skill skill) { // can put stability thruster and mobility thrsuter in here (but for now not sure if its stackable or not)
                int[] batteryId = {3770, 3769, 3768, 3767, 3766};
                int[] bulwarkId = {3774, 3773, 3772, 3771};
                for(int id : batteryId){
                    if (skill.getSkillTemplate().getSkillId() == id){
                        for(int bulId : bulwarkId){
                            if (player.getEffectController().hasAbnormalEffect(bulId)){
                                player.getEffectController().removeEffect(bulId);
                            }
                        }
                    }
                }
                for(int id : bulwarkId){
                    if (skill.getSkillTemplate().getSkillId() == id){
                        for(int baId : batteryId){
                            if (player.getEffectController().hasAbnormalEffect(baId)){
                                player.getEffectController().removeEffect(baId);
                            }
                        }
                    }
                }
            }
        };
        player.getObserveController().addObserver(obsZ);

        ActionObserver observer = new ActionObserver(ObserverType.UNEQUIP) {
            @Override
            public void unequip(Item item, Player owner) {
                if (item.getEquipmentType() == EquipType.WEAPON) {
                    effect.endEffect();
                }
            }
        };
        player.getObserveController().addObserver(observer);
        effect.setActionObserver(observer, position);
        effect.setActionObserver(obsZ, position);
    }

    @Override
    public void endEffect(Effect effect) {
        Player player = (Player) effect.getEffected();
        List<Effect> myEffects = player.getEffectController().getAbnormalEffects();

        for(Effect ef : myEffects){
            int skillId = ef.getSkillTemplate().getSkillId();

            if(ef.isRiderEffect(skillId)){
                player.getEffectController().removeEffect(skillId);
                player.getEffectController().removeNoshowEffect(skillId);
            }
        }
        player.setRobotId(0);
        PacketSendUtility.broadcastPacketAndReceive(player, new SM_RIDE_ROBOT(player.getObjectId(), player.getRobotId()));
        ActionObserver acObsz = effect.getActionObserver(position);
        ActionObserver observer = effect.getActionObserver(position);
        if (acObsz != null) {
            effect.getEffected().getObserveController().removeObserver(acObsz);
        }
        if (observer != null) {
            effect.getEffected().getObserveController().removeObserver(observer);
        }
    }
}
