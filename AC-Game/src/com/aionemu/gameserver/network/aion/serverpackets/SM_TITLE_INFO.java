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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.title.Title;
import com.aionemu.gameserver.model.gameobjects.player.title.TitleList;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author cura, xTz
 * @modified -Enomine-
 */
public class SM_TITLE_INFO extends AionServerPacket {

    private TitleList titleList;
    private int action; // 0: list, 1: self set, 3: broad set
    private int titleId;
    private int bonusTitleId;
    private int playerObjId;

    /**
     * title list
     *
     * @param player
     */
    public SM_TITLE_INFO(Player player) {
        this.action = 0;
        this.titleList = player.getTitleList();
    }

    /**
     * self title set
     *
     * @param titleId
     */
    public SM_TITLE_INFO(int titleId) {
        this.action = 1;
        this.titleId = titleId;
    }

    /**
     * broad title set
     *
     * @param player
     * @param titleId
     */
    public SM_TITLE_INFO(Player player, int titleId) {
        this.action = 3;
        this.playerObjId = player.getObjectId();
        this.titleId = titleId;
    }

    public SM_TITLE_INFO(boolean flag) {
        this.action = 4;
        this.titleId = flag ? 1 : 0;
    }

    public SM_TITLE_INFO(Player player, boolean flag) {
        this.action = 5;
        this.playerObjId = player.getObjectId();
        this.titleId = flag ? 1 : 0;
    }

    public SM_TITLE_INFO(int action, int bonusTitleId) {
        this.action = action;
        this.bonusTitleId = bonusTitleId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(action);
        switch (action) {
            case 0:
                writeC(0x00);
                writeH(titleList.size());
                for (Title title : titleList.getTitles()) {
                    writeD(title.getId());
                    writeD(title.getRemainingTime());
                }
                break;
            case 1: // self set
                writeH(titleId);
                break;
            case 2: // unk 4.7
                writeD(bonusTitleId);
                break;
            case 3: // broad set
                writeD(playerObjId);
                writeH(titleId);
                break;
            case 4: // Mentor flag self
                writeH(titleId);
                break;
            case 5: // broad set mentor fleg
                writeD(playerObjId);
                writeH(titleId);
                break;
            case 6://Title wich will take BonusStats from
                writeH(bonusTitleId);
            case 7://Unk 4.7
                writeD(bonusTitleId);
        }
    }
}
