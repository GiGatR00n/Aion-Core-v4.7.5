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
package com.aionemu.loginserver;

import com.aionemu.loginserver.configs.Config;
import com.aionemu.loginserver.network.gameserver.GsConnection;
import com.aionemu.loginserver.network.gameserver.serverpackets.SM_PING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author KID
 */
public class PingPongThread implements Runnable {

    private final Logger log = LoggerFactory.getLogger(PingPongThread.class);
    private GsConnection connection;
    public volatile boolean uptime = true;
    private SM_PING ping;
    private byte requests = 0;
    private int serverPID = -1;
    private boolean killProcess = false;

    public PingPongThread(GsConnection connection) {
        this.uptime = true;
        this.connection = connection;
        this.ping = new SM_PING();
    }

    @Override
    public void run() {
        log.info("PingPong for gameserver #" + this.connection.getGameServerInfo().getId() + " has started.");
        while (uptime) {
            try {
                Thread.sleep(Config.PINGPONG_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!uptime || validateResponse()) {
                return;
            }

            try {
                connection.sendPacket(ping);
                requests++;
            } catch (Exception ex) {
                log.error("PingThread#" + connection.getGameServerInfo().getId(), ex);
            }
        }
    }

    public void onResponse(int pid) {
        requests--;
        this.serverPID = pid;
    }

    public boolean validateResponse() {
        if (requests >= 2) {
            uptime = false;
            log.info("Gameserver #" + connection.getGameServerInfo().getId() + " [PID=" + this.serverPID + "] died, closing.");
            connection.close(false);
            if (killProcess && serverPID != -1) {
                if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
                    try {
                        Runtime.getRuntime().exec("taskkill /pid " + serverPID + " /f");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void closeMe() {
        uptime = false;
    }
}
