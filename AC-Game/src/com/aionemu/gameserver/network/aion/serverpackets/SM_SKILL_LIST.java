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

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.skill.PlayerSkillEntry;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * In this packet Server is sending Skill Info?
 *
 * @author modified by ATracer,MrPoke
 */
public class SM_SKILL_LIST extends AionServerPacket {

    private PlayerSkillEntry[] skillList;
    private int messageId;
    private int skillNameId;
    private String skillLvl;
    public static final int YOU_LEARNED_SKILL = 1300050;
    boolean isNew = false;

    /**
     * This constructor is used on player entering the world Constructs new
     * <tt>SM_SKILL_LIST </tt> packet
     */
    @SuppressWarnings("unused")
    public SM_SKILL_LIST(Player player, PlayerSkillEntry[] basicSkills) {
        this.skillList = player.getSkillList().getBasicSkills();
        this.messageId = 0;
    }

    @SuppressWarnings("unused")
    public SM_SKILL_LIST(Player player, PlayerSkillEntry stigmaSkill) {
        this.skillList = new PlayerSkillEntry[]{stigmaSkill};
        this.messageId = 0;
    }

    public SM_SKILL_LIST(PlayerSkillEntry skillListEntry, int messageId, boolean isNew) {
        this.skillList = new PlayerSkillEntry[]{skillListEntry};
        this.messageId = messageId;
        this.skillNameId = DataManager.SKILL_DATA.getSkillTemplate(skillListEntry.getSkillId()).getNameId();
        this.skillLvl = String.valueOf(skillListEntry.getSkillLevel());
        this.isNew = isNew;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        final int size = skillList.length;
        writeH(size); // skills list size
        if (isNew)
            writeC(0);
        else
            writeC(1);

        if (size > 0) {
            for (PlayerSkillEntry entry : skillList) {
                writeH(entry.getSkillId());// id
                writeH(entry.getSkillLevel());// lvl
                writeC(0x00);
                int extraLevel = entry.getExtraLvl();
                writeC(extraLevel);
                if (isNew && extraLevel == 0 && !entry.isStigma()) {
                    writeD((int) (System.currentTimeMillis() / 1000)); // Learned date NCSoft......
                } else {
                    writeD(0);
                }
                writeC(entry.isStigma() ? 1 : 0); // stigma
            }
        }
        writeD(messageId);
        if (messageId != 0) {
            writeH(0x24); // unk
            writeD(skillNameId);
            writeH(0x00);
            writeS(skillLvl);
        }
    }
}
