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
package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;
import pirate.events.EventManager;
import pirate.events.EventRegisterInfo;
import pirate.events.enums.EventType;

/**
 *
 * @author f14shm4n
 */
public class cmd_event extends PlayerCommand {

    private static final StringBuilder info = new StringBuilder();

    static {
        info.append("Information from the Team:\n");
        info.append(".event - Displays the Information you reading\n");
        info.append(".event reg <event type> - registration to the specified event\n");
        info.append(".event unreg <event type> - out of the list of registration to the specified event\n");
        info.append("Available Event Types(event type):\n");
        for (EventType et : EventType.values()) {
            if (et.IsDone()) {
                info.append("- ").append(et.getEventTemplate().getCmdName()).append("\n");
            }
        }
    }

    public cmd_event() {
        super("event");
    }

    @Override
    public void execute(Player player, String... params) {
        if ((player.getController().isInCombat() || player.isInInstance() || player.isInPrison()) && !player.isGM()) {
            //PacketSendUtility.sendMessage(player, "You can not participate in the event, while you are in prison, in a time zone or in combat.");
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.Event_1));
            return;
        }
        if (params.length == 2) {
            EventType et = parseType(params[1]);
            if (params[0].equals("reg")) {
                if (et == null) {
                    ShowSyntax(player);
                    return;
                }
                EventRegisterInfo eri = EventManager.getInstance().register(player, et);
                PacketSendUtility.sendMessage(player, eri.getMessage());
            } else if (params[0].equals("unreg")) {
                if (et == null) {
                    ShowSyntax(player);
                    return;
                }
                EventRegisterInfo eri = EventManager.getInstance().unregister(player, et);
                PacketSendUtility.sendMessage(player, eri.getMessage());
            }
        } else {
            this.ShowSyntax(player);
        }
    }

    private void ShowSyntax(Player p) {
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

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}