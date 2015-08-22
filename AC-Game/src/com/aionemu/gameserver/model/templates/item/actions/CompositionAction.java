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
import javax.xml.bind.annotation.XmlType;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Created with IntelliJ IDEA. User: pixfid Date: 7/14/13 Time: 5:18 PM
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompositionAction")
public class CompositionAction extends AbstractItemAction {

    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
        return false;
    }

    @Override
    public void act(Player player, Item parentItem, Item targetItem) {
    }

    public boolean canAct(Player player, Item tools, Item first, Item second) {

        if (!tools.getItemTemplate().isCombinationItem()) {
            return false;
        }

        if (!first.getItemTemplate().isEnchantmentStone()) {
            return false;
        }

        if (!second.getItemTemplate().isEnchantmentStone()) {
            return false;
        }

        if (first.getItemCount() < 1 || second.getItemCount() < 1) {
            return false;
        }

        if (first.getItemTemplate().getLevel() > 95 || second.getItemTemplate().getLevel() > 95) {
            return false;
        }

        return true;
    }

    public void act(final Player player, final Item tools, final Item first, final Item second) {

        PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), tools.getObjectId(), tools.getItemTemplate().getTemplateId(), 5000, 0, 0));
        player.getController().cancelTask(TaskId.ITEM_USE);

        final ItemUseObserver observer = new ItemUseObserver() {
            @Override
            public void abort() {
                player.getController().cancelTask(TaskId.ITEM_USE);
                PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), tools.getObjectId(), tools.getItemTemplate().getTemplateId(), 0, 2, 0));
                player.getObserveController().removeObserver(this);
            }
        };
        player.getObserveController().attach(observer);
        player.getController().addTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                player.getObserveController().removeObserver(observer);
                boolean result = player.getInventory().decreaseByObjectId(tools.getObjectId(), 1);
                boolean result1 = player.getInventory().decreaseByObjectId(first.getObjectId(), 1);
                boolean result2 = player.getInventory().decreaseByObjectId(second.getObjectId(), 1);
                if (result && result1 && result2) {
                    ItemService.addItem(player, getItemId(calcLevel(first.getItemTemplate().getLevel(), second.getItemTemplate().getLevel())), 1);
                }
                PacketSendUtility.sendPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), tools.getObjectId(), tools.getItemTemplate().getTemplateId(), 0, 1, 0));
            }
        }, 5000));

    }

    private int calcLevel(int first, int second) {
        int value = ((first + second) / 2);
        if (value < 11) {
            value = Rnd.get(1, 20);
        } else {
            int random = Rnd.get(1, 10);
            int bit = Rnd.get(0, 1);
            value = (bit == 0 ? value - random : value + random);
        }
        return value;
    }

    public int getItemId(int value) {
        return 166000000 + value;
    }
}
