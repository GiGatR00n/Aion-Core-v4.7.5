/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import pirate.events.EventManager;
import pirate.events.enums.EventType;

/**
 *
 * @author flashman
 */
public class EventUtil extends AdminCommand {

    private static final StringBuilder info = new StringBuilder();

    static {
        info.append("Information from the Team:\n");
        info.append("//emanager - Displays the Information you reading\n");
        info.append("//emanager start <event type> - if the data event is not running, perform a forced run\n");
        info.append("//emanager rcd all - removes the cooldown for all players in all of the event, in which they participated\n");
        info.append("//emanager rcd <event type> <player name> - removes the cooldown of this Event for the specified player\n");
        info.append("Available Event Types(event type):\n");
        for (EventType et : EventType.values()) {
            if (et.IsDone()) {
                info.append(et.getEventTemplate().getCmdName()).append("\n");
            }
        }
    }

    public EventUtil() {
        super("emanager");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length == 0) {
            showCommandInfo(admin);
        } else {
            // start event cmd
            if (params[0].equals("start")) {
                if (admin.getAccessLevel() < 3) {
                    //PacketSendUtility.sendMessage(admin, "You can not use this command.");
                    PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.Event_Util_1));
                    return;
                }
                EventType et = parseType(params[1]);
                if (et == null) {
                    //PacketSendUtility.sendMessage(admin, "Wrong Event Type.");
                    PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.Event_Util_2));
                    return;
                }
                PacketSendUtility.sendMessage(admin, EventManager.getInstance().CMD_StartEvent(et));
                return;
            }
            // remove event cd cmd
            if (params[0].equals("rcd")) {
                Player p;
                EventType type;
                if (params.length == 2 && params[1].equals("all")) {
                    for (EventType et : EventType.values()) {
                        EventManager.getInstance().createNewEventSession(et);
                    }
                } else if (params.length == 3) {
                    type = parseType(params[1]);
                    p = World.getInstance().findPlayer(Util.convertName(params[2]));
                    if (type == null) {
                        //PacketSendUtility.sendMessage(admin, "Wrong Event Type.");
                        PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.Event_Util_2));
                        return;
                    }
                    EventManager.getInstance().removePlayerFromPlayedList(p, type);
                }
                return;
            }
            //PacketSendUtility.sendMessage(admin, "Unknown User.");
            PacketSendUtility.sendMessage(admin, LanguageHandler.translate(CustomMessageId.Event_Util_3));
        }
    }

    private void showCommandInfo(Player p) {
        PacketSendUtility.sendMessage(p, info.toString());
    }

    private EventType parseType(String str) {
        for (EventType et : EventType.values()) {
            if (!et.IsDone()) {
                continue;
            }
            if (str.equals(et.getEventTemplate().getCmdName())) {
                return et;
            }
        }
        return null;
    }
}
