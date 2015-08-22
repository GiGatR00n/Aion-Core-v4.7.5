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
package com.aionemu.gameserver.skillengine.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.LeftHandSlot;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Cheatkiller
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LeftHandCondition")
public class LeftHandCondition extends Condition {

    @XmlAttribute(name = "type")
    private LeftHandSlot type;

    @Override
    public boolean validate(Skill env) {
        if (env.getEffector() instanceof Player) {
            Player player = (Player) env.getEffector();
            switch (type) {
                case DUAL: { //temporary fix with offhand >_<
                    if (player.getEquipment().isWeaponEquipped(WeaponType.CANNON_2H)
                            || player.getEquipment().isWeaponEquipped(WeaponType.HARP_2H)
                            || player.getEquipment().isWeaponEquipped(WeaponType.SWORD_1H)
                            || player.getEquipment().isWeaponEquipped(WeaponType.GUN_1H)
                            || player.getEquipment().isWeaponEquipped(WeaponType.KEYBLADE_2H)) {
                        return true;
                    } else {
                        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_SKILL_NEED_DUAL_WEAPON);
                        return false;
                    }
                }
                case SHIELD: {
                    if (player.getEquipment().isShieldEquipped()) {
                        return true;
                    } else {
                        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_SKILL_NEED_SHIELD);
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
