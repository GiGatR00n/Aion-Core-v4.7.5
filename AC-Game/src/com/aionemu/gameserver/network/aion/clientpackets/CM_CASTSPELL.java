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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author alexa026
 * @author rhys2002
 */
public class CM_CASTSPELL extends AionClientPacket {

    private Logger log = LoggerFactory.getLogger(CM_CASTSPELL.class);
    private int spellid;
    // 0 - obj id, 1 - point location, 2 - unk, 3 - object not in sight(skill 1606)? 4 - unk
    private int targetType;
    private float x, y, z;
    @SuppressWarnings("unused")
    private int targetObjectId;
    private int hitTime;
    private int level;
    private int unk;

    /**
     * Constructs new instance of <tt>CM_CM_REQUEST_DIALOG </tt> packet
     *
     * @param opcode
     */
    public CM_CASTSPELL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        spellid = readH();
        level = readC();

        targetType = readC();

        switch (targetType) {
            case 0:
            case 3:
            case 4:
            case 87:
                targetObjectId = readD();
                break;
            case 1:
                x = readF();
                y = readF();
                z = readF();
                break;
            case 2:
                x = readF();
                y = readF();
                z = readF();
                readF();// unk1
                readF();// unk2
                readF();// unk3
                readF();// unk4
                readF();// unk5
                readF();// unk6
                readF();// unk7
                readF();// unk8
                break;
        }

        hitTime = readH();
        unk = readD();//unk can be big values
        log.debug("[CM_CASTSPELL] Unk value: " + unk);
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();

        SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(spellid);
        if (template == null || template.isPassive()) {
            return;
        }

        if (player.isProtectionActive()) {
            player.getController().stopProtectionActiveTask();
        }

        long currentTime = System.currentTimeMillis();
        if (player.getNextSkillUse() > currentTime) {
            PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300021));
            return;
        }

        if (!player.getLifeStats().isAlreadyDead()) {
            player.getController().useSkill(template, targetType, x, y, z, hitTime, level);
        }
    }
}
