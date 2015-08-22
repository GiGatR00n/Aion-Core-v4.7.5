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
package com.aionemu.gameserver.restrictions;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * @author lord_rex
 */
public class PrisonRestrictions extends AbstractRestrictions {

    @Override
    public boolean isRestricted(Player player, Class<? extends Restrictions> callingRestriction) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You are in prison!");
            return true;
        }

        return false;
    }

    @Override
    public boolean canAttack(Player player, VisibleObject target) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You cannot attack in prison!");
            return false;
        }

        return true;
    }

    @Override
    public boolean canUseSkill(Player player, Skill skill) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You cannot use skills in prison!");
            return false;
        }

        return true;
    }

    @Override
    public boolean canAffectBySkill(Player player, VisibleObject target, Skill skill) {
        return true;
    }

    @Override
    public boolean canChat(Player player) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You cannot chat in prison!");
            return false;
        }

        return true;
    }

    @Override
    public boolean canInviteToGroup(Player player, Player target) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You cannot invite members to group in prison!");
            return false;
        }

        return true;
    }

    @Override
    public boolean canInviteToAlliance(Player player, Player target) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You cannot invite members to alliance in prison!");
            return false;
        }

        return true;
    }

    @Override
    public boolean canChangeEquip(Player player) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You cannot equip / unequip item in prison!");
            return false;
        }

        return true;
    }

    @Override
    public boolean canUseItem(Player player, Item item) {
        if (isInPrison(player)) {
            PacketSendUtility.sendMessage(player, "You cannot use item in prison!");
            return false;
        }
        return true;
    }

    private boolean isInPrison(Player player) {
        return player.isInPrison() || player.getWorldId() == WorldMapType.DE_PRISON.getId()
                || player.getWorldId() == WorldMapType.DF_PRISON.getId();
    }
}
