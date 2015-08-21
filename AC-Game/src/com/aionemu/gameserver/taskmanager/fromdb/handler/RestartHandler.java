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
package com.aionemu.gameserver.taskmanager.fromdb.handler;

import com.aionemu.gameserver.ShutdownHook;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Divinity, nrg
 */
public class RestartHandler extends TaskFromDBHandler {

    private static final Logger log = LoggerFactory.getLogger(RestartHandler.class);
    private int countDown;
    private int announceInterval;
    private int warnCountDown;

    @Override
    public boolean isValid() {
        if (params.length == 3) {
            try {
                countDown = Integer.parseInt(params[0]);
                announceInterval = Integer.parseInt(params[1]);
                warnCountDown = Integer.parseInt(params[2]);

                return true;
            } catch (NumberFormatException e) {
                log.warn("Invalid parameters for RestartHandler. Only valid integers allowed - not registered", e);
            }
        }
        log.warn("RestartHandler has more or less than 3 parameters - not registered");
        return false;
    }

    @Override
    public void trigger() {
        log.info("Task[" + taskId + "] launched : restarting the server !");

        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Automatic Task: The server will restart in " + warnCountDown
                        + " seconds ! Please find a safe place and disconnect your character.");
            }
        });

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                ShutdownHook.getInstance().doShutdown(countDown, announceInterval, ShutdownHook.ShutdownMode.RESTART);
            }
        }, warnCountDown * 1000);
    }
}
