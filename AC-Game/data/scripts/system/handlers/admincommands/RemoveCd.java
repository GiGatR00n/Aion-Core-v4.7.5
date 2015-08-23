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
package admincommands;

import com.aionemu.gameserver.model.gameobjects.HouseObject;
import com.aionemu.gameserver.model.gameobjects.UseableItemObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemCooldown;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author kecimis
 */
public class RemoveCd extends AdminCommand {

    public RemoveCd() {
        super("removecd");
    }

    @Override
    public void execute(Player admin, String... params) {
        VisibleObject target = admin.getTarget();
        if (target == null) {
            target = admin;
        }

        if (target instanceof Player) {
            Player player = (Player) target;
            if (params.length == 0) {
                List<Integer> delayIds = new ArrayList<Integer>();
                /*for (PlayerSkillEntry skillEntry : player.getSkillList().getAllSkills()) {
                 SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillEntry.getSkillId());
                 if (template == null) {
                 continue;
                 }

                 PacketSendUtility.sendPacket(player, new SM_SKILL_COOLDOWN(skillEntry.getSkillId()));
                 }*/
                if (player.getSkillCoolDowns() != null) {
                    long currentTime = System.currentTimeMillis();
                    for (Entry<Integer, Long> en : player.getSkillCoolDowns().entrySet()) {
                        delayIds.add(en.getKey());
                    }

                    for (Integer delayId : delayIds) {
                        player.setSkillCoolDown(delayId, currentTime);
                    }

                    delayIds.clear();
                    PacketSendUtility.sendPacket(player, new SM_SKILL_COOLDOWN(player.getSkillCoolDowns()));
                }

                if (player.getItemCoolDowns() != null) {
                    for (Entry<Integer, ItemCooldown> en : player.getItemCoolDowns().entrySet()) {
                        delayIds.add(en.getKey());
                    }

                    for (Integer delayId : delayIds) {
                        player.addItemCoolDown(delayId, 0, 0);
                    }

                    delayIds.clear();
                    PacketSendUtility.sendPacket(player, new SM_ITEM_COOLDOWN(player.getItemCoolDowns()));
                }

                if (player.getHouseRegistry() != null && player.getHouseObjectCooldownList().getHouseObjectCooldowns().size() > 0) {
                    Iterator<HouseObject<?>> iter = player.getHouseRegistry().getObjects().iterator();
                    while (iter.hasNext()) {
                        HouseObject<?> obj = iter.next();
                        if (obj instanceof UseableItemObject) {
                            if (!player.getHouseObjectCooldownList().isCanUseObject(obj.getObjectId())) {
                                player.getHouseObjectCooldownList().addHouseObjectCooldown(obj.getObjectId(), 0);
                            }
                        }
                    }
                }

                if (player.equals(admin)) {
                    PacketSendUtility.sendMessage(admin, "Your cooldowns were removed");
                } else {
                    PacketSendUtility.sendMessage(admin, "You have removed cooldowns of player: " + player.getName());
                    PacketSendUtility.sendMessage(player, "Your cooldowns were removed by admin");
                }
            } else if (params[0].contains("instance")) {
                if (player.getPortalCooldownList() == null || player.getPortalCooldownList().getPortalCoolDowns() == null) {
                    return;
                }
                if (params.length >= 2) {
                    if (params[1].equalsIgnoreCase("all")) {
                        List<Integer> mapIds = new ArrayList<Integer>();
                        for (Entry<Integer, Long> mapId : player.getPortalCooldownList().getPortalCoolDowns().entrySet()) {
                            mapIds.add(mapId.getKey());
                        }

                        for (Integer id : mapIds) {
                            player.getPortalCooldownList().addPortalCooldown(id, 0);
                        }

                        mapIds.clear();
                        if (player.equals(admin)) {
                            PacketSendUtility.sendMessage(admin, "Your instance cooldowns were removed");
                        } else {
                            PacketSendUtility.sendMessage(admin, "You have removed instance cooldowns of player: " + player.getName());
                            PacketSendUtility.sendMessage(player, "Your instance cooldowns were removed by admin");
                        }
                    } else {
                        int worldId = 0;
                        try {
                            worldId = Integer.parseInt(params[1]);
                        } catch (NumberFormatException e) {
                            PacketSendUtility.sendMessage(admin, "WorldId has to be integer or use \"all\"");
                            return;
                        }

                        if (player.getPortalCooldownList().isPortalUseDisabled(worldId)) {
                            player.getPortalCooldownList().addPortalCooldown(worldId, 0);

                            if (player.equals(admin)) {
                                PacketSendUtility.sendMessage(admin, "Your instance cooldown worldId: " + worldId + " was removed");
                            } else {
                                PacketSendUtility.sendMessage(admin, "You have removed instance cooldown worldId: " + worldId
                                        + " of player: " + player.getName());
                                PacketSendUtility.sendMessage(player, "Your instance cooldown worldId: " + worldId
                                        + " was removed by admin");
                            }
                        } else {
                            PacketSendUtility.sendMessage(admin, "You or your target can enter given instance");
                        }

                    }
                } else {
                    PacketSendUtility.sendMessage(admin, "syntax: //removecd instance <all|worldId>");
                }
            }
        } else {
            PacketSendUtility.sendMessage(admin, "Only players are allowed as target");
        }
    }
}
