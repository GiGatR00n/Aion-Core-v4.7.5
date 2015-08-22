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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author alexa026
 * @author ATracer
 * @author kecimis
 */
public class SM_ATTACK_STATUS extends AionServerPacket {

    private Creature creature;
    private TYPE type;
    private int skillId;
    private int value;
    private int logId;

    public static enum TYPE {

        NATURAL_HP(3),
        USED_HP(4),//when skill uses hp as cost parameter
        REGULAR(5),
        ABSORBED_HP(6),
        HP(7),
        DAMAGE(7),
        PROTECTDMG(8),
        DELAYDAMAGE(10),
        FALL_DAMAGE(17),
        HEAL_MP(19),
        ABSORBED_MP(20),
        MP(21),
        NATURAL_MP(22),
        ATTACK(23),
        FP_RINGS(24),
        FP(25),
        NATURAL_FP(26);
        private int value;

        private TYPE(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static enum LOG {

        SPELLATK(1),
        HEAL(3),
        MPHEAL(4),
        SKILLLATKDRAININSTANT(23),
        SPELLATKDRAININSTANT(24),
        POISON(25),
        BLEED(26),
        PROCATKINSTANT(92),
        DELAYEDSPELLATKINSTANT(95),
        SPELLATKDRAIN(130),
        FPHEAL(133),
        REGULARHEAL(170),
        REGULAR(189),
        ATTACK(190);
        private int value;

        private LOG(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public SM_ATTACK_STATUS(Creature creature, TYPE type, int skillId, int value, LOG log) {
        this.creature = creature;
        this.type = type;
        this.skillId = skillId;
        this.value = value;
        this.logId = log.getValue();
    }

    public SM_ATTACK_STATUS(Creature creature, TYPE type, int skillId, int value) {
        this(creature, type, skillId, value, LOG.REGULAR);
    }

    public SM_ATTACK_STATUS(Creature creature, int value) {
        this(creature, TYPE.REGULAR, 0, value, LOG.REGULAR);
    }

    /**
     * {@inheritDoc} ddchcc
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(creature.getObjectId());
        switch (type) {
        	case ATTACK:
        		writeD(0xffffffd9);//unk like objectids for skills O.o
        		break;
            case DAMAGE:
            case DELAYDAMAGE:
                writeD(-value);
                break;
            default:
                writeD(value);
        }
        writeC(type.getValue());
        writeC(creature.getLifeStats().getHpPercentage());
        writeH(skillId);
        if (skillId != 0)
        	writeH(logId);
        else
        	writeH(LOG.ATTACK.getValue());
    }
    // logId
    // depends on effecttemplate
    // effecttemplate (TYPE) LOG.getValue()
    // spellattack(hp) 1
    // poison(hp) 25
    // delaydamage(hp) 95
    // bleed(hp) 26
    // mp regen(natural_mp) 171
    // hp regen(natural_hp) 171
    // fp regen(natural_fp) 171
    // fp pot(fp) 171
    // prochp(hp) 171
    // procmp(mp) 171
    // heal_instant (regular) 171
    // SpellAtkDrainInstantEffect(absorbed_mp) 24(refactoring shard)
    // mpheal(mp) 4
    // heal(hp) 3
    // fpheal(fp) 133
    // spellatkdrain(hp) 130
    // falldmg (17) 170
    // mpheal (19) 171
    // hp as cost parameter(4) logId 170
    // procatkinstant - (7) 92
    // protecteffect on protector - (8) 171
    // TODO find rest of logIds
}
