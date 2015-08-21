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
package pirate.announcer;

import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import java.util.Iterator;

/**
 *
 * @author flashman
 */
public class Balalaka {

    /**
     * Sends a message to all players in the world.
     *
     * @param message
     */
    public static void sayInWorld(final String message) {
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            PacketSendUtility.sendYellowMessageOnCenter(iter.next(), message);
        }
    }

    /**
     * Sends a message to all players in the specified location.
     *
     * @param worldId
     * @param message
     */
    public static void sayInWorld(final int worldId, final String message) {
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            Player p = iter.next();
            if (p.isOnline() && p.getWorldId() == worldId) {
                PacketSendUtility.sendYellowMessageOnCenter(p, message);
            }
        }
    }

    /**
     * Sends a message to all players in the world, indicating the delay.
     *
     * @param sender
     * @param msg
     * @param delay seconds
     */
    public static void sayInWorldWithDelay(final String msg, int delay) {
        if (delay == 0) {
            sayInWorld(msg);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    sayInWorld(msg);
                }
            }, delay * 1000);
        }
    }

    /**
     * Sends a message to all players in the world, indicating the delay.
     *
     * @param sender
     * @param msg
     * @param delay seconds
     */
    public static void sayInWorldWithDelay(final String msg, final int worldId, int delay) {
        if (delay == 0) {
            sayInWorld(worldId, msg);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    sayInWorld(worldId, msg);
                }
            }, delay * 1000);
        }
    }

    /**
     * Sends a message to all players in the world. [Sender]: Text
     *
     * @param sender
     * @param msg
     */
    public static void sayInWorldOrangeTextCenter(final String sender, final String msg) {
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            PacketSendUtility.sendMessage(iter.next(), sender, msg, ChatType.GROUP_LEADER);
        }
    }

    /**
     * Sends a message to all players in the specified location. [Sender]: Text
     *
     * @param sender
     * @param msg
     */
    public static void sayInWorldOrangeTextCenter(final String sender, final String msg, final int worldId) {
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            Player p = iter.next();
            if (p.isOnline() && p.getWorldId() == worldId) {
                PacketSendUtility.sendMessage(p, sender, msg, ChatType.GROUP_LEADER);
            }
        }
    }

    /**
     * Sends a message to all players in the world, indicating the delay. [Sender]:
     * Text
     *
     * @param sender
     * @param msg
     * @param delay seconds
     */
    public static void sayInWorldOrangeTextCenterWithDelay(final String sender, final String msg, int delay) {
        if (delay == 0) {
            sayInWorldOrangeTextCenter(sender, msg);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    sayInWorldOrangeTextCenter(sender, msg);
                }
            }, delay * 1000);
        }
    }

    /**
     * Sends a message to all players in the world, indicating the delay. [Sender]:
     * Text
     *
     * @param sender
     * @param msg
     * @param delay seconds
     */
    public static void sayInWorldOrangeTextCenterWithDelay(final String sender, final String msg, final int worldId, int delay) {
        if (delay == 0) {
            sayInWorldOrangeTextCenter(sender, msg, worldId);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    sayInWorldOrangeTextCenter(sender, msg, worldId);
                }
            }, delay * 1000);
        }
    }
}
