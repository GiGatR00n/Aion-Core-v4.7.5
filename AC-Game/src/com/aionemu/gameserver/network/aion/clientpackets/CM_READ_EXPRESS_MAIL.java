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

import java.util.concurrent.Future;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.LetterType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.spawnengine.VisibleObjectSpawner;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author antness thx to Guapo for sniffing
 */
public class CM_READ_EXPRESS_MAIL extends AionClientPacket {

    private int action;

    public CM_READ_EXPRESS_MAIL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        this.action = readC();
    }

    @Override
    protected void runImpl() {

        final Player player = getConnection().getActivePlayer();
        boolean haveUnreadExpress = (player.getMailbox().haveUnreadByType(LetterType.EXPRESS) || player.getMailbox().haveUnreadByType(LetterType.BLACKCLOUD));
        switch (this.action) {
            case 0:
                // window is closed
                if (player.getPostman() != null) {
                    player.getPostman().getController().onDelete();
                    player.setPostman(null);
                }
                break;
            case 1:
                // click on icon
                if (player.getPostman() != null) {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_POSTMAN_ALREADY_SUMMONED);
                } else if (player.isFlying()) {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_POSTMAN_UNABLE_IN_FLIGHT);
                } else if (player.getController().hasTask(TaskId.EXPRESS_MAIL_USE)) {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_POSTMAN_UNABLE_IN_COOLTIME);
                } else if (haveUnreadExpress) {
                    VisibleObjectSpawner.spawnPostman(player);
                    Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 600000); // 10 min
                    player.getController().addTask(TaskId.EXPRESS_MAIL_USE, task);
                }
                break;
        }
    }
}
