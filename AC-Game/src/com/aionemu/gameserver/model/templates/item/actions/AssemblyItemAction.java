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

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.AssemblyItem;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AssemblyItemAction")
public class AssemblyItemAction extends AbstractItemAction {

    @XmlAttribute
    private int item;

    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
        AssemblyItem assemblyItem = getAssemblyItem();
        if (assemblyItem == null) {
            return false;
        }
        for (Integer itemId : assemblyItem.getParts()) {
            if (player.getInventory().getFirstItemByItemId(itemId) == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void act(final Player player, final Item parentItem, Item targetItem) {
        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(),
                parentItem.getObjectId(), parentItem.getItemId(), 1000, 0, 0), true);
        final ItemUseObserver observer = new ItemUseObserver() {
            @Override
            public void abort() {
                player.getController().cancelTask(TaskId.ITEM_USE);
                player.removeItemCoolDown(parentItem.getItemTemplate().getUseLimits().getDelayId());
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ITEM_CANCELED(new DescriptionId(parentItem.getItemTemplate().getNameId())));
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(),
                        parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, 2, 0), true);
                player.getObserveController().removeObserver(this);
            }
        };

        player.getObserveController().attach(observer);
        player.getController().addTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {

                player.getObserveController().removeObserver(observer);
                player.getController().cancelTask(TaskId.ITEM_USE);
                AssemblyItem assemblyItem = getAssemblyItem();
                for (Integer itemId : assemblyItem.getParts()) {
                    if (!player.getInventory().decreaseByItemId(itemId, 1)) {
                        return;
                    }
                }
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(),
                        parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, 1, 0), true);
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401122));
                ItemService.addItem(player, assemblyItem.getId(), 1);
            }
        }, 1000));

    }

    public AssemblyItem getAssemblyItem() {
        return DataManager.ASSEMBLY_ITEM_DATA.getAssemblyItem(item);
    }
}
