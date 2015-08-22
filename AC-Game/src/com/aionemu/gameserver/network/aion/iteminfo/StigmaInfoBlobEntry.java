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
package com.aionemu.gameserver.network.aion.iteminfo;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.templates.item.Stigma;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * This blob contains stigma info.
 *
 * @author -Nemesiss-
 * @modified Rolandas
 */
public class StigmaInfoBlobEntry extends ItemBlobEntry {

    StigmaInfoBlobEntry() {
        super(ItemBlobType.STIGMA_INFO);
    }

    @Override
    public void writeThisBlob(ByteBuffer buf) {
        Item item = ownerItem;
        Stigma stigma = item.getItemTemplate().getStigma();

        writeD(buf, stigma.getSkills().get(0).getSkillId()); // skill id 1
        if (stigma.getSkills().size() >= 2) {
            writeD(buf, stigma.getSkills().get(1).getSkillId()); // skill id 2
        } else {
            writeD(buf, 0);
        }

        writeD(buf, stigma.getShard());

        skip(buf, 192);
        writeH(buf, 0x1); // unk
        writeH(buf, 0);
        skip(buf, 96);
        writeH(buf, 0); // unk
    }

    @Override
    public int getSize() {
        return 8 + 4 + 192 + 4 + 96 + 2;
    }
}
