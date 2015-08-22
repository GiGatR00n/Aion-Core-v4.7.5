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

import com.aionemu.gameserver.model.templates.gather.GatherableTemplate;
import com.aionemu.gameserver.model.templates.gather.Material;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author ATracer
 * @author orz
 * @author Antraxx
 */
public class SM_GATHER_UPDATE extends AionServerPacket {

    private GatherableTemplate template;
    private int action;
    private int itemId;
    private int success;
    private int failure;
    private int nameId;

    public SM_GATHER_UPDATE(GatherableTemplate template, Material material, int success, int failure, int action) {
        this.action = action;
        this.template = template;
        this.itemId = material.getItemid();
        this.success = success;
        this.failure = failure;
        this.nameId = material.getNameid();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeH(template.getHarvestSkill());
        writeC(this.action);
        writeD(this.itemId);

        switch (this.action) {
            case 0: {
                writeD(this.success);
                writeD(this.failure);
                writeD(0);
                writeD(1200); // timer??
                writeD(1330011); // ??text??skill??
                writeH(0x24); // 0x24
                writeD(this.nameId);
                writeH(0); // 0x24
                break;
            }
            case 1: {
                writeD(this.success);
                writeD(this.failure);
                writeD(700); // unk timer??
                writeD(1200); // unk timer??
                writeD(0); // unk timer??writeD(700);
                writeH(0);
                break;
            }
            case 2: {
                writeD(this.success);
                writeD(this.failure);
                writeD(700);// unk timer??
                writeD(1200); // unk timer??
                writeD(0); // unk timer??writeD(700);
                writeH(0);
                break;
            }
            case 3: {
                writeD(this.success);
                writeD(this.failure);
                writeD(700);// unk timer??
                writeD(1200); // unk timer??
                writeD(0); // unk timer??writeD(700);
                writeH(0);
                break;
            }
            case 5: // you have stopped gathering
            {
                writeD(0);
                writeD(0);
                writeD(700);// unk timer??
                writeD(1200); // unk timer??
                writeD(1330080); // unk timer??writeD(700);
                writeH(0);
                break;
            }
            case 6: {
                writeD(this.success);
                writeD(this.failure);
                writeD(700); // unk timer??
                writeD(1200); // unk timer??
                writeD(0); // unk timer??writeD(700);
                writeH(0);
                break;
            }
            case 7: {
                writeD(this.success);
                writeD(this.failure);
                writeD(0);
                writeD(1200); // timer??
                writeD(1330079); // ??text??skill??
                writeH(0x24); // 0x24
                writeD(this.nameId);
                writeH(0); // 0x24
                break;
            }
        }
    }
}
