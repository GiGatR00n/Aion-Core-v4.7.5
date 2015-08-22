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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

/**
 * @author ATracer, KID
 */
public class CM_SUMMON_CASTSPELL extends AionClientPacket {

    private static final Logger log = LoggerFactory.getLogger(CM_SUMMON_CASTSPELL.class);
    private int summonObjId;
    private int targetObjId;
    private int skillId;
    @SuppressWarnings("unused")
    private int skillLvl;
    @SuppressWarnings("unused")
    private float unk;

    public CM_SUMMON_CASTSPELL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        summonObjId = readD();
        skillId = readH();
        skillLvl = readC();
        targetObjId = readD();
        unk = readF();
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();

        long currentTime = System.currentTimeMillis();
        if (player.getNextSummonSkillUse() > currentTime) {
            return;
        }

        Summon summon = player.getSummon();
        if (summon == null) {
            log.warn("summon castspell without active summon on " + player.getName() + ".");
            return;
        }
        if (summon.getObjectId() != summonObjId) {
            log.warn("summon castspell from a different summon instance on " + player.getName() + ".");
            return;
        }

        Creature target = null;
        if (targetObjId != summon.getObjectId()) {
            VisibleObject obj = summon.getKnownList().getObject(targetObjId);
            if (obj instanceof Creature) {
                target = (Creature) obj;
            }
        } else {
            target = summon;
        }

        if (target != null) {
            player.setNextSummonSkillUse(currentTime + 1100);
            summon.getController().useSkill(skillId, target);
        } else {
            log.warn("summon castspell on a wrong target on " + player.getName());
        }
    }
}
