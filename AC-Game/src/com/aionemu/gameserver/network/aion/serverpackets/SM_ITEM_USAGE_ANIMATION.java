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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.world.World;

/**
 * @author ATracer
 */
public class SM_ITEM_USAGE_ANIMATION extends AionServerPacket {

    private int playerObjId;
    private int targetObjId;
    private int itemObjId;
    private int itemId;
    private int time;
    private int end;
    private int unk;

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId) {
        this.playerObjId = playerObjId;
        this.targetObjId = playerObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = 0;
        this.end = 1;
        this.unk = 1;
    }

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end) {
        this.playerObjId = playerObjId;
        this.targetObjId = playerObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = time;
        this.end = end;
    }

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end, int unk) {
        this.playerObjId = playerObjId;
        this.targetObjId = playerObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = time;
        this.end = end;
        this.unk = unk;
    }

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int targetObjId, int itemObjId, int itemId, int time, int end, int unk) {
        this.playerObjId = playerObjId;
        this.targetObjId = targetObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = time;
        this.end = end;
        this.unk = unk;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if (time > 0) {
            final Player player = World.getInstance().findPlayer(playerObjId);
            final Item item = player.getInventory().getItemByObjId(itemObjId);
            player.setUsingItem(item);
        }

        writeD(playerObjId); // player obj id
        writeD(targetObjId); // target obj id

        writeD(itemObjId); // itemObjId
        writeD(itemId); // item id

        writeD(time); // unk
        writeC(end); // unk
        writeC(0); // unk
        writeC(1);
        writeD(unk);
        writeC(0);//unk
    }
}
