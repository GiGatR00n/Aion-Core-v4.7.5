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
package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.model.DialogPage;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.templates.housing.HousingPostbox;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_OBJECT_USE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Rolandas
 */
public class PostboxObject extends HouseObject<HousingPostbox> {

    private AtomicReference<Player> usingPlayer = new AtomicReference<Player>();

    public PostboxObject(House owner, int objId, int templateId) {
        super(owner, objId, templateId);
    }

    @Override
    public void onUse(final Player player) {
        if (!usingPlayer.compareAndSet(null, player)) {
            // The same player is using, return. It might be double-click
            if (usingPlayer.compareAndSet(player, player)) {
                return;
            }
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_HOUSING_OBJECT_OCCUPIED_BY_OTHER);
            return;
        }

        final ItemUseObserver observer = new ItemUseObserver() {
            @Override
            public void abort() {
                player.getObserveController().removeObserver(this);
                usingPlayer.set(null);
            }
        };

        player.getObserveController().attach(observer);

        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_HOUSING_OBJECT_USE(getObjectTemplate().getNameId()));
        player.getController().addTask(TaskId.HOUSE_OBJECT_USE, ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), DialogPage.MAIL.id()));
                    player.getMailbox().sendMailList(false);
                    PacketSendUtility.sendPacket(player, new SM_OBJECT_USE_UPDATE(player.getObjectId(), 0, 0, PostboxObject.this));
                } finally {
                    player.getObserveController().removeObserver(observer);
                    usingPlayer.set(null);
                }
            }
        }, 0));
    }

    @Override
    public boolean canExpireNow() {
        return usingPlayer.get() == null;
    }
}
