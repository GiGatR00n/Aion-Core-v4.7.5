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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Letter;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob;

import java.util.Collection;

/**
 * @author kosyachok, Source
 */
public abstract class MailServicePacket extends AionServerPacket {

    // private static final Logger log = LoggerFactory.getLogger(MailServicePacket.class);
    protected Player player;

    /**
     * @param player
     */
    public MailServicePacket(Player player) {
        this.player = player;
    }

    protected void writeLettersList(Collection<Letter> letters, Player player, boolean isPostman, int showCount) {
        writeD(player.getObjectId());
        writeC(0);
        writeH(isPostman ? -showCount : -letters.size()); // -loop cnt [stupid nc shit!]
        for (Letter letter : letters) {
            if (isPostman) {
                if (!letter.isExpress()) {
                    continue;
                } else if (!letter.isUnread()) {
                    continue;
                }
            }

            writeD(letter.getObjectId());
            writeS(letter.getSenderName());
            writeS(letter.getTitle());
            writeC(letter.isUnread() ? 0 : 1);
            if (letter.getAttachedItem() != null) {
                writeD(letter.getAttachedItem().getObjectId());
                writeD(letter.getAttachedItem().getItemTemplate().getTemplateId());
            } else {
                writeD(0);
                writeD(0);
            }
            writeQ(letter.getAttachedKinah());
            writeC(letter.getLetterType().getId());
        }
    }

    protected void writeMailMessage(int messageId) {
        writeC(messageId);
    }

    protected void writeMailboxState(int totalCount, int unreadCount, int expressCount, int blackCloudCount) {
        writeH(totalCount);
        writeH(unreadCount);
        writeH(expressCount);
        writeH(blackCloudCount);
    }

    protected void writeLetterRead(Letter letter, long time, int totalCount, int unreadCount, int expressCount, int blackCloudCount) {
        writeD(letter.getRecipientId());
        writeD(totalCount + unreadCount * 0x10000); // total count + unread hex
        writeD(expressCount + blackCloudCount); // unread express + BC letters count
        writeD(letter.getObjectId());
        writeD(letter.getRecipientId());
        writeS(letter.getSenderName());
        writeS(letter.getTitle());
        writeS(letter.getMessage());

        Item item = letter.getAttachedItem();
        if (item != null) {
            ItemTemplate itemTemplate = item.getItemTemplate();

            writeD(item.getObjectId());
            writeD(itemTemplate.getTemplateId());
            writeD(1); // unk
            writeD(0); // unk
            writeNameId(itemTemplate.getNameId());

            ItemInfoBlob itemInfoBlob = ItemInfoBlob.getFullBlob(player, item);
            itemInfoBlob.writeMe(getBuf());
        } else {
            writeQ(0);
            writeQ(0);
            writeD(0);
        }

        writeD((int) letter.getAttachedKinah());
        writeD(0); // AP reward for castle assault/defense (in future)
        writeC(0);
        writeD((int) (time / 1000));
        writeC(letter.getLetterType().getId()); // mail type
    }

    protected void writeLetterState(int letterId, int attachmentType) {
        writeD(letterId);
        writeC(attachmentType);
        writeC(1);
    }

    protected void writeLetterDelete(int totalCount, int unreadCount, int expressCount, int blackCloudCount, int... letterIds) {
        writeD(totalCount + unreadCount * 0x10000); // total count + unread hex
        writeD(expressCount + blackCloudCount); // unread express + BC letters count
        writeH(letterIds.length);
        for (int letterId : letterIds) {
            writeD(letterId);
        }
    }
}
