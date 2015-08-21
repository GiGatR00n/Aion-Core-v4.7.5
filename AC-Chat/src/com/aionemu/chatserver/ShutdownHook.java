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
package com.aionemu.chatserver;

import com.aionemu.chatserver.network.netty.NettyServer;
import com.aionemu.chatserver.service.GameServerService;
import com.aionemu.commons.utils.ExitCode;

/**
 *
 * @author nrg
 */
public class ShutdownHook extends Thread {

    private static final ShutdownHook instance = new ShutdownHook();
    /**
     * Indicates wether the loginserver should shut dpwn or only restart
     */
    private static boolean restartOnly = false;

    /**
     * get the shutdown-hook instance the shutdown-hook instance is created by
     * the first call of this function, but it has to be registrered externaly.
     *
     * @return instance of Shutdown, to be used as shutdown hook
     */
    public static ShutdownHook getInstance() {
        return instance;
    }

    /**
     * Set's restartOnly attribute
     *
     * @param restartOnly Indicates wether the loginserver should shut dpwn or
     * only restart
     */
    public static void setRestartOnly(boolean restartOnly) {
        ShutdownHook.restartOnly = restartOnly;
    }

    @Override
    public void run() {
        NettyServer.getInstance().shutdownAll();
        GameServerService.getInstance().setOffline();

        // Do system exit
        if (restartOnly) {
            Runtime.getRuntime().halt(ExitCode.CODE_RESTART);
        } else {
            Runtime.getRuntime().halt(ExitCode.CODE_NORMAL);
        }
    }
}
