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
package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.InGameShopDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TOLL_INFO;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_TOLL_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import org.apache.commons.lang.StringUtils;

/**
 * @author xTz
 */
public class Gameshop extends AdminCommand {

    public Gameshop() {
        super("gameshop");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length == 0) {
            onFail(admin, null);
            return;
        }
        int itemId = 0;
        int list;
        byte category, subCategory, itemType, gift;
        long count, price, toll;
        Player player = null;
        String titleDescription;
        if ("delete".startsWith(params[0])) {
            try {
                itemId = Integer.parseInt(params[1]);
                category = Byte.parseByte(params[2]);
                subCategory = Byte.parseByte(params[3]);
                list = Integer.parseInt(params[4]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "<itemId, category, subCategory, list> values must be int, byte, byte, int.");
                return;
            }
            DAOManager.getDAO(InGameShopDAO.class).deleteIngameShopItem(itemId, category, subCategory, list - 1);
            PacketSendUtility.sendMessage(admin, "You remove [item:" + itemId + "]");
        } else if ("add".startsWith(params[0])) {
            try {
                itemId = Integer.parseInt(params[1]);
                count = Long.parseLong(params[2]);
                price = Long.parseLong(params[3]);
                category = Byte.parseByte(params[4]);
                subCategory = Byte.parseByte(params[5]);
                itemType = Byte.parseByte(params[6]);
                gift = Byte.parseByte(params[7]);
                list = Integer.parseInt(params[8]);
                titleDescription = Util.convertName(params[9]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "<itemId, count, price, category, subCategory, itemType, gift, list, description> values must be int, long, long, byte, byte, byte, byte, int, string, Object... .");
                return;
            }

            ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
            if (itemTemplate == null) {
                PacketSendUtility.sendMessage(admin, "Item id is incorrect: " + itemId);
                return;
            }

            String description = "";

            for (int i = 10; i < params.length; i++) {
                description += Util.convertName(params[i]) + " ";
            }
            description = description.trim();

            if (list < 1) {
                PacketSendUtility.sendMessage(admin, "<list> : minium is 1.");
                return;
            }
            if (gift < 0 || gift > 1) {
                PacketSendUtility.sendMessage(admin, "<gift> : minimum is 0, maximum is 1.");
                return;
            }
            if (itemType < 0 || itemType > 2) {
                PacketSendUtility.sendMessage(admin, "<itemType> : minimum is 0, maximum is 2.");
                return;
            }
            if (subCategory < 3 || subCategory > 19) {
                PacketSendUtility.sendMessage(admin, "<category> : minimum is 3, maximum is 19.");
                return;
            }
            if (titleDescription.length() > 20) {
                PacketSendUtility.sendMessage(admin, "<title description> : maximum length is 20.");
                return;
            }
            if (titleDescription.equals("empty")) {
                titleDescription = StringUtils.EMPTY;
            }
            DAOManager.getDAO(InGameShopDAO.class).saveIngameShopItem(IDFactory.getInstance().nextId(), itemId, count, price,
                    category, subCategory, list - 1, 1, itemType, gift, titleDescription, description);
            PacketSendUtility.sendMessage(admin, "You add [item:" + itemId + "]");
        } else if ("deleteranking".startsWith(params[0])) {
            try {
                itemId = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "<itemId> value must be an integer.");
            }
            DAOManager.getDAO(InGameShopDAO.class).deleteIngameShopItem(itemId, (byte) -1, (byte) -1, -1);
            PacketSendUtility.sendMessage(admin, "You remove from Ranking Sales [item:" + itemId + "]");
        } else if ("addranking".startsWith(params[0])) {
            try {
                itemId = Integer.parseInt(params[1]);
                count = Long.parseLong(params[2]);
                price = Long.parseLong(params[3]);
                itemType = Byte.parseByte(params[4]);
                gift = Byte.parseByte(params[5]);
                titleDescription = Util.convertName(params[6]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "<itemId, count, price, itemType, gift, description> value must be int, long, long, byte, byte, string, Object... .");
                return;
            }
            String description = "";
            for (int i = 7; i < params.length; i++) {
                description += Util.convertName(params[i]) + " ";
            }
            description = description.trim();

            ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);

            if (itemTemplate == null) {
                PacketSendUtility.sendMessage(admin, "Item id is incorrect: " + itemId);
                return;
            }
            if (titleDescription.equals("empty")) {
                titleDescription = StringUtils.EMPTY;
            }
            DAOManager.getDAO(InGameShopDAO.class).saveIngameShopItem(IDFactory.getInstance().nextId(), itemId, count, price,
                    (byte) -1, (byte) -1, -1, 0, itemType, gift, titleDescription, description);
            PacketSendUtility.sendMessage(admin, "You remove from Ranking Sales [item:" + itemId + "]");
        } else if ("settoll".startsWith(params[0])) {
            if (params.length == 3) {
                try {
                    toll = Integer.parseInt(params[2]);
                } catch (NumberFormatException e) {
                    PacketSendUtility.sendMessage(admin, "<toll> value must be an integer.");
                    return;
                }

                String name = Util.convertName(params[1]);

                player = World.getInstance().findPlayer(name);
                if (player == null) {
                    PacketSendUtility.sendMessage(admin, "The specified player is not online.");
                    return;
                }

                if (LoginServer.getInstance().sendPacket(new SM_ACCOUNT_TOLL_INFO(toll, player.getAcountName()))) {
                    player.getClientConnection().getAccount().setToll(toll);
                    PacketSendUtility.sendPacket(player, new SM_TOLL_INFO(toll));
                    PacketSendUtility.sendMessage(admin, "Tolls setted to " + toll + ".");
                } else {
                    PacketSendUtility.sendMessage(admin, "ls communication error.");
                }
            }
            if (params.length == 2) {
                try {
                    toll = Integer.parseInt(params[1]);
                } catch (NumberFormatException e) {
                    PacketSendUtility.sendMessage(admin, "<toll> value must be an integer.");
                    return;
                }

                if (toll < 0) {
                    PacketSendUtility.sendMessage(admin, "<toll> must > 0.");
                    return;
                }

                VisibleObject target = admin.getTarget();
                if (target == null) {
                    PacketSendUtility.sendMessage(admin, "You should select a target first!");
                    return;
                }

                if (target instanceof Player) {
                    player = (Player) target;
                }

                if (LoginServer.getInstance().sendPacket(new SM_ACCOUNT_TOLL_INFO(toll, player.getAcountName()))) {
                    player.getClientConnection().getAccount().setToll(toll);
                    PacketSendUtility.sendPacket(player, new SM_TOLL_INFO(toll));
                    PacketSendUtility.sendMessage(admin, "Tolls setted to " + toll + ".");
                } else {
                    PacketSendUtility.sendMessage(admin, "ls communication error.");
                }
            }
        } else if ("addtoll".startsWith(params[0])) {
            if (params.length == 3) {
                try {
                    toll = Integer.parseInt(params[2]);
                } catch (NumberFormatException e) {
                    PacketSendUtility.sendMessage(admin, "<toll> value must be an integer.");
                    return;
                }

                if (toll < 0) {
                    PacketSendUtility.sendMessage(admin, "<toll> must > 0.");
                    return;
                }

                String name = Util.convertName(params[1]);

                player = World.getInstance().findPlayer(name);
                if (player == null) {
                    PacketSendUtility.sendMessage(admin, "The specified player is not online.");
                    return;
                }

                PacketSendUtility.sendMessage(admin, "You added " + toll + " tolls to Player: " + name);
                InGameShopEn.getInstance().addToll(player, toll);
            }
            if (params.length == 2) {
                try {
                    toll = Integer.parseInt(params[1]);
                } catch (NumberFormatException e) {
                    PacketSendUtility.sendMessage(admin, "<toll> value must be an integer.");
                    return;
                }

                VisibleObject target = admin.getTarget();
                if (target == null) {
                    PacketSendUtility.sendMessage(admin, "You should select a target first!");
                    return;
                }

                if (target instanceof Player) {
                    player = (Player) target;
                }

                PacketSendUtility.sendMessage(admin, "You added " + toll + " tolls to Player: " + player.getName());
                InGameShopEn.getInstance().addToll(player, toll);
            }
        } else {
            PacketSendUtility.sendMessage(admin,
                    "You can use only, addtoll, settoll, deleteranking, addranking, delete or add.");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "No parameters detected please use:\n"
                + "//gameshop add <itemId> <count> <price> <category> <subCategory> <itemType> <gift> <list> <title description|empty> <item description|null>\n"
                + "//gameshop delete <itemId> <category> <subCategory> <list>\n"
                + "//gameshop addranking <itemId> <count> <price> <itemType> <gift> <title description|empty> <item description|null>\n" + "//gameshop deleteranking <itemId>\n"
                + "//gameshop settoll <target|player> <toll>\n" + "//gameshop addtoll <target|player> <toll>");
    }
}
