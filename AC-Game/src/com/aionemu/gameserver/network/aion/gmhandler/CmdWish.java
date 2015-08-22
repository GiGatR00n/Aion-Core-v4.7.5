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
package com.aionemu.gameserver.network.aion.gmhandler;

import gnu.trove.map.hash.TIntObjectHashMap;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

/**
 * @reworked Kill3r
 */
public class CmdWish extends AbstractGMHandler {

    public CmdWish(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));

        String[] p = params.split(" ");
        if (p.length != 2) {
            PacketSendUtility.sendMessage(admin, "NPC Spawn is not yet implemented");
            return;
        }

        String[] itemN = params.split(" ");
        TIntObjectHashMap<ItemTemplate> item = DataManager.ITEM_DATA.getItemData();

        String itemName = itemN[0];
        Integer itemcount = Integer.parseInt(itemN[1]);
        if(itemcount == 0){
            itemcount = 1;
        }

        for(ItemTemplate tt : item.valueCollection()){
            if(tt.getNamedesc().equalsIgnoreCase(itemName)){
                ItemService.addItem(t, tt.getTemplateId(), itemcount);
            }
        }

    }
}
