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
package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.effect.SummonEffect;
import com.aionemu.gameserver.skillengine.effect.TransformEffect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillUseAction")
public class SkillUseAction extends AbstractItemAction {

    @XmlAttribute
    protected int skillid;
    @XmlAttribute
    protected int level;
    @XmlAttribute(required = false)
    private Integer mapid;

    /**
     * Gets the value of the skillid property.
     */
    public int getSkillid() {
        return skillid;
    }

    /**
     * Gets the value of the level property.
     */
    public int getLevel() {
        return level;
    }

    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
        Skill skill = SkillEngine.getInstance().getSkill(player, skillid, level, player.getTarget(), parentItem.getItemTemplate());
        if (skill == null) {
            return false;
        }
        int nameId = parentItem.getItemTemplate().getNameId();
        byte levelRestrict = parentItem.getItemTemplate().getMaxLevelRestrict(player);
        if (levelRestrict != 0) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CANNOT_USE_ITEM_TOO_LOW_LEVEL_MUST_BE_THIS_LEVEL(levelRestrict, nameId));
            return false;
        }
        // Cant use transform items while already transformed
        if (player.isTransformed()) {
            for (EffectTemplate template : skill.getSkillTemplate().getEffects().getEffects()) {
                if (template instanceof TransformEffect) {
                    PacketSendUtility.sendPacket(player,
                            SM_SYSTEM_MESSAGE.STR_CANT_USE_ITEM(new DescriptionId(nameId)));
                    return false;
                }
            }
        }
        if (player.getSummon() != null) {
            for (EffectTemplate template : skill.getSkillTemplate().getEffects().getEffects()) {
                if (template instanceof SummonEffect) {
                    PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300072));
                    return false;
                }
            }
        }
        return skill.canUseSkill();
    }

    @Override
    public void act(Player player, Item parentItem, Item targetItem) {
        Skill skill = SkillEngine.getInstance().getSkill(player, skillid, level, player.getTarget(), parentItem.getItemTemplate());
        if (skill != null) {
            player.getController().cancelUseItem();
            skill.setItemObjectId(parentItem.getObjectId());
            skill.useSkill();
        }
    }

    public int getMapid() {
        if (mapid == null) {
            return 0;
        }
        return mapid;
    }
}
