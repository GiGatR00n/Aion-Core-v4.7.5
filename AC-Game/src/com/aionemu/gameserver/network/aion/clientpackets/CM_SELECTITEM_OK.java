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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.decomposable.SelectItem;
import com.aionemu.gameserver.model.templates.decomposable.SelectItems;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SELECT_ITEM_ADD;
import com.aionemu.gameserver.services.item.ItemService;


public class CM_SELECTITEM_OK extends AionClientPacket {

    private int uniqueItemId;
    private int index;
    @SuppressWarnings("unused")
    private int unk;

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_SELECTITEM_OK(int opcode, AionConnection.State state, AionConnection.State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        this.uniqueItemId = readD();
        this.unk = readD();
        this.index = readC();

    }

    @Override
    protected void runImpl() {
        Player player = ((AionConnection) getConnection()).getActivePlayer();
        Item item = player.getInventory().getItemByObjId(this.uniqueItemId);
        if (item == null) {
            return;
        }
        sendPacket(new SM_ITEM_USAGE_ANIMATION(player.getObjectId().intValue(), player.getObjectId().intValue(), this.uniqueItemId, item.getItemId(), 0, 1, 0));
        boolean delete = player.getInventory().decreaseByObjectId(this.uniqueItemId, 1L);
        if (delete) {
            SelectItems selectitem = DataManager.DECOMPOSABLE_SELECT_ITEM_DATA.getSelectItem(player.getPlayerClass(), item.getItemId());
            SelectItem st = (SelectItem) selectitem.getItems().get(this.index);
            ItemService.addItem(player, st.getSelectItemId(), st.getCount());
            sendPacket(new SM_SELECT_ITEM_ADD(this.uniqueItemId, this.index));
        }

    }


}
