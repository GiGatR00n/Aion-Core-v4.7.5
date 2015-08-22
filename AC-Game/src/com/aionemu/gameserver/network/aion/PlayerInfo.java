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
package com.aionemu.gameserver.network.aion;

import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author AEJTester
 * @author Nemesiss
 * @author Niato
 * @author GiGatR00n
 */
public abstract class PlayerInfo extends AionServerPacket {

    private static Logger log = LoggerFactory.getLogger(PlayerInfo.class);

    protected PlayerInfo() {
    }

    protected void writePlayerInfo(PlayerAccountData accPlData) {
        PlayerCommonData pbd = accPlData.getPlayerCommonData();
        final int raceId = pbd.getRace().getRaceId();
        final int genderId = pbd.getGender().getGenderId();
        final PlayerAppearance playerAppearance = accPlData.getAppereance();
        writeD(pbd.getPlayerObjId());
        writeS(pbd.getName(), 52);
        writeD(genderId);
        writeD(raceId);
        writeD(pbd.getPlayerClass().getClassId());
        writeD(playerAppearance.getVoice());
        writeD(playerAppearance.getSkinRGB());
        writeD(playerAppearance.getHairRGB());
        writeD(playerAppearance.getEyeRGB());
        writeD(playerAppearance.getLipRGB());
        writeC(playerAppearance.getFace());
        writeC(playerAppearance.getHair());
        writeC(playerAppearance.getDeco());
        writeC(playerAppearance.getTattoo());
        writeC(playerAppearance.getFaceContour());
        writeC(playerAppearance.getExpression());
        writeC(4);// always 4 o0
        writeC(playerAppearance.getJawLine());
        writeC(playerAppearance.getForehead());
        writeC(playerAppearance.getEyeHeight());
        writeC(playerAppearance.getEyeSpace());
        writeC(playerAppearance.getEyeWidth());
        writeC(playerAppearance.getEyeSize());
        writeC(playerAppearance.getEyeShape());
        writeC(playerAppearance.getEyeAngle());
        writeC(playerAppearance.getBrowHeight());
        writeC(playerAppearance.getBrowAngle());
        writeC(playerAppearance.getBrowShape());
        writeC(playerAppearance.getNose());
        writeC(playerAppearance.getNoseBridge());
        writeC(playerAppearance.getNoseWidth());
        writeC(playerAppearance.getNoseTip());
        writeC(playerAppearance.getCheek());
        writeC(playerAppearance.getLipHeight());
        writeC(playerAppearance.getMouthSize());
        writeC(playerAppearance.getLipSize());
        writeC(playerAppearance.getSmile());
        writeC(playerAppearance.getLipShape());
        writeC(playerAppearance.getJawHeigh());
        writeC(playerAppearance.getChinJut());
        writeC(playerAppearance.getEarShape());
        writeC(playerAppearance.getHeadSize());
        // 1.5.x 0x00, shoulderSize, armLength, legLength (BYTE) after HeadSize

        writeC(playerAppearance.getNeck());
        writeC(playerAppearance.getNeckLength());
        writeC(playerAppearance.getShoulderSize()); // shoulderSize

        writeC(playerAppearance.getTorso());
        writeC(playerAppearance.getChest());
        writeC(playerAppearance.getWaist());
        writeC(playerAppearance.getHips());
        writeC(playerAppearance.getArmThickness());
        writeC(playerAppearance.getHandSize());
        writeC(playerAppearance.getLegThicnkess());
        writeC(playerAppearance.getFootSize());
        writeC(playerAppearance.getFacialRate());
        writeC(0x00); // 0x00
        writeC(playerAppearance.getArmLength()); // armLength
        writeC(playerAppearance.getLegLength()); // legLength
        writeC(playerAppearance.getShoulders());
        writeC(playerAppearance.getFaceShape());
        writeC(0x00); // always 0 may be acessLevel
        writeC(0x00); // always 0 - unk
        writeC(0x00);
        writeF(playerAppearance.getHeight());
        int raceSex = 100000 + raceId * 2 + genderId;
        writeD(raceSex);
        writeD(pbd.getPosition().getMapId());// mapid for preloading map
        writeF(pbd.getPosition().getX());
        writeF(pbd.getPosition().getY());
        writeF(pbd.getPosition().getZ());
        writeD(pbd.getPosition().getHeading());
        writeH(pbd.getLevel()); // lvl confirmed
        writeH(0);
        writeD(pbd.getTitleId());
        if (accPlData.isLegionMember()) {
            writeD(accPlData.getLegion().getLegionId());
            writeS(accPlData.getLegion().getLegionName(), 82);
        } else {
            writeB(new byte[86]);
        }

        writeH(accPlData.isLegionMember() ? 0x01 : 0x00);//is in legion?
        writeD((int) pbd.getLastOnline().getTime());//last online

        int itemsDataSize = 0;
        // TODO figure out this part when fully equipped
        List<Item> items = accPlData.getEquipment();

        for (Item item : items) {
            if (itemsDataSize >= 208) {
                break;
            }

            ItemTemplate itemTemplate = item.getItemTemplate();
            if (itemTemplate == null) {
                log.warn("Missing item. PlayerId: " + pbd.getPlayerObjId() + " ItemId: " + item.getObjectId());
                continue;
            }

            if (itemTemplate.isArmor() || itemTemplate.isWeapon()) {
                if (itemTemplate.getItemSlot() <= ItemSlot.PANTS.getSlotIdMask()) {
                    writeC(1); // this flas is needed to show equipment on selection screen
                    writeD(item.getItemSkinTemplate().getTemplateId());
                    GodStone godStone = item.getGodStone();
                    writeD(godStone != null ? godStone.getItemId() : 0);
                    writeD(item.getItemColor());

                    itemsDataSize += 13;
                }
            }
        }

        byte[] stupidNc = new byte[208 - itemsDataSize];
        writeB(stupidNc);
        writeD(0x00); // v4.7.5.4 
    }
}
