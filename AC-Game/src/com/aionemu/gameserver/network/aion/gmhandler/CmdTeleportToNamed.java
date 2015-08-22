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

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdTeleportToNamed extends AbstractGMHandler {

    public CmdTeleportToNamed(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        int npcId = 0;
        String message = "";
        try {
            npcId = Integer.valueOf(params);
        } catch (ArrayIndexOutOfBoundsException e) {
            onFail(admin, e.getMessage());
        } catch (NumberFormatException e) {
            String npcDesc = params;

            for (NpcTemplate template : DataManager.NPC_DATA.getNpcData().valueCollection()) {
                if (template.getDesc() != null && template.getDesc().equalsIgnoreCase(npcDesc)) {
                    TeleportService2.teleportToNpc(admin, template.getTemplateId());
                    message = "Teleporting to Npc: " + template.getTemplateId();
                    PacketSendUtility.sendMessage(admin, message);
                }
            }
        }

        if (npcId > 0) {
            if (!message.equals(""))
                message = "Teleporting to Npc: " + npcId + "\n" + message;
            else
                message = "Teleporting to Npc: " + npcId;
            PacketSendUtility.sendMessage(admin, message);
            TeleportService2.teleportToNpc(admin, npcId);
        }
    }

    /**
     * @param message
     */
    public void onFail(Player admin, String message) {
        PacketSendUtility.sendMessage(admin, "syntax //movetonpc <npc_id|npc name>");
    }
}