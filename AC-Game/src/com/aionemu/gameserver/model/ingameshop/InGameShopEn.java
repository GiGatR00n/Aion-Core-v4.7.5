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
package com.aionemu.gameserver.model.ingameshop;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.ingameshop.InGameShopProperty;
import com.aionemu.gameserver.configs.main.InGameShopConfig;
import com.aionemu.gameserver.configs.main.LoggingConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.dao.InGameShopDAO;
import com.aionemu.gameserver.dao.InGameShopLogDAO;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.LetterType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TOLL_INFO;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_PREMIUM_CONTROL;
import com.aionemu.gameserver.services.mail.SystemMailService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author KID
 * @author xTz
 * @author Antraxx
 * @Reworked Kill3r
 */
public class InGameShopEn {

    private static InGameShopEn instance = new InGameShopEn();
    private final Logger log = LoggerFactory.getLogger("INGAMESHOP_LOG");

    public static InGameShopEn getInstance() {
        return instance;
    }

    private FastMap<Byte, List<IGItem>> items;
    private InGameShopDAO dao;
    private InGameShopProperty iGProperty;

    public InGameShopEn() {
        if (!InGameShopConfig.ENABLE_IN_GAME_SHOP) {
            log.info("InGameShop is disabled.");
            return;
        }
        iGProperty = InGameShopProperty.load();
        dao = DAOManager.getDAO(InGameShopDAO.class);
        items = FastMap.newInstance();
        activeRequests = FastList.newInstance();
        items = dao.loadInGameShopItems();
        log.info("Loaded with " + items.size() + " items.");
    }

    public InGameShopProperty getIGSProperty() {
        return iGProperty;
    }

    public void reload() {
        if (!InGameShopConfig.ENABLE_IN_GAME_SHOP) {
            log.info("InGameShop is disabled.");
            return;
        }
        iGProperty.clear();
        iGProperty = InGameShopProperty.load();
        items = DAOManager.getDAO(InGameShopDAO.class).loadInGameShopItems();
        log.info("Loaded with " + items.size() + " items.");
    }

    public IGItem getIGItem(int id) {
        for (byte key : items.keySet()) {
            for (IGItem item : items.get(key)) {
                if (item.getObjectId() == id) {
                    return item;
                }
            }
        }
        return null;
    }

    public Collection<IGItem> getItems(byte category) {
        if (!items.containsKey(category)) {
            return Collections.emptyList();
        }
        return this.items.get(category);
    }

    @SuppressWarnings("unchecked")
    public FastList<Integer> getTopSales(int subCategory, byte category) {
        byte max = 6;
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>(new DescFilter());
        if (!items.containsKey(category)) {
            return FastList.newInstance();
        }
        for (IGItem item : this.items.get(category)) {
            if (item.getSalesRanking() == 0) {
                continue;
            }
            if (subCategory != 2 && item.getSubCategory() != subCategory) {
                continue;
            }
            map.put(item.getSalesRanking(), item.getObjectId());
        }
        FastList<Integer> top = FastList.newInstance();
        byte cnt = 0;
        for (int objId : map.values()) {
            if (cnt <= max) {
                top.add(objId);
                cnt++;
            } else {
                break;
            }
        }
        map.clear();
        return top;
    }

    @SuppressWarnings("rawtypes")
    class DescFilter implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            Integer i1 = (Integer) o1;
            Integer i2 = (Integer) o2;
            return -i1.compareTo(i2);
        }
    }

    public int getMaxList(byte subCategoryId, byte category) {
        int id = 0;
        if (!items.containsKey(category)) {
            return id;
        }
        for (IGItem item : items.get(category)) {
            if (item.getSubCategory() == subCategoryId) {
                if (item.getList() > id) {
                    id = item.getList();
                }
            }
        }
        return id;
    }

    private int lastRequestId = 0;
    private FastList<IGRequest> activeRequests;

    public void acceptRequest(Player player, int itemObjId) {
        IGItem item = InGameShopEn.getInstance().getIGItem(itemObjId);
        lastRequestId++;
        IGRequest request = new IGRequest(lastRequestId, player.getObjectId(), itemObjId);
        request.accountId = player.getClientConnection().getAccount().getId();
        if (LoginServer.getInstance().sendPacket(new SM_PREMIUM_CONTROL(request, item.getItemPrice()))) {
            activeRequests.add(request);
        }
        if (LoggingConfig.LOG_INGAMESHOP) {
            log.info("[INGAMESHOP] > Account name: " + player.getAcountName() + ", PlayerName: " + player.getName() + " is watching item:" + item.getItemId() + " cost " + item.getItemPrice() + " toll.");
        }
    }

    public void sendRequest(Player player, String receiver, String message, int itemObjId) {
        if (receiver.equalsIgnoreCase(player.getName())) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_CANNOT_GIVE_TO_ME);
            return;
        }
        if (!InGameShopConfig.ALLOW_GIFTS) {
            PacketSendUtility.sendMessage(player, "Gifts are disabled !");
            return;
        }
        if (!DAOManager.getDAO(PlayerDAO.class).isNameUsed(receiver)) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_NO_USER_TO_GIFT);
            return;
        }
        PlayerCommonData recipientCommonData = DAOManager.getDAO(PlayerDAO.class).loadPlayerCommonDataByName(receiver);
        if (recipientCommonData.getMailboxLetters() >= 100) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MAIL_MSG_RECIPIENT_MAILBOX_FULL(recipientCommonData.getName()));
            return;
        }
        if (!InGameShopConfig.ENABLE_GIFT_OTHER_RACE && !player.isGM()) {
            if (player.getRace() != recipientCommonData.getRace()) {
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_NO_USER_TO_GIFT);
                return;
            }
        }
        IGItem item = getIGItem(itemObjId);
        lastRequestId++;
        IGRequest request = new IGRequest(lastRequestId, player.getObjectId(), receiver, message, itemObjId);
        request.accountId = player.getClientConnection().getAccount().getId();
        if (LoginServer.getInstance().sendPacket(new SM_PREMIUM_CONTROL(request, item.getItemPrice()))) {
            activeRequests.add(request);
        }
    }

    public void addToll(Player player, long cnt) {
        if (InGameShopConfig.ENABLE_IN_GAME_SHOP) {
            lastRequestId++;
            IGRequest request = new IGRequest(lastRequestId, player.getObjectId(), 0);
            request.accountId = player.getClientConnection().getAccount().getId();
            if (LoginServer.getInstance().sendPacket(new SM_PREMIUM_CONTROL(request, cnt * -1))) {
                activeRequests.add(request);
            }
        } else {
            PacketSendUtility.sendMessage(player, "Ingameshop is disabled !");
        }
    }

    /**
     *
     * @param player The player you want to delete the toll from
     * @param cnt The quantity of tolls you want to delete
     */
    public void removeToll(Player player, int cnt){
        // You need to give positive value on remove toll to remove,
        // ex : removetoll(player, 5000)
        if (cnt < 0) {
            PacketSendUtility.sendMessage(player, "You cannot add an negative value!");
            return;
        }

        PacketSendUtility.sendMessage(player, "You've lost " + cnt + " toll(s) from you're account!");
        rToll(player, cnt);
    }

    /**
     *
     * @param player The player
     * @param toll the Toll
     */
    protected void rToll(Player player, int toll){
        long currentToll = player.getPlayerAccount().getToll();
        long newToll = currentToll - toll;
        player.getPlayerAccount().setToll(newToll);
    }

    public void finishRequest(int requestId, int result, long toll) {
        for (IGRequest request : activeRequests) {
            if (request.requestId == requestId) {
                Player player = World.getInstance().findPlayer(request.playerId);
                if (player != null) {
                    if (result == 1) {
                        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_ERROR);
                    } else if (result == 2) {
                        IGItem item = getIGItem(request.itemObjId);
                        if (item == null) {
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_ERROR);
                            log.error("player " + player.getName() + " requested " + request.itemObjId + " that was not exists in list.");
                            return;
                        }
                        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_NOT_ENOUGH_POINT);
                        PacketSendUtility.sendPacket(player, new SM_TOLL_INFO(toll));
                        if (LoggingConfig.LOG_INGAMESHOP) {
                            log.info("[INGAMESHOP] > Account name: " + player.getAcountName() + ", PlayerName: " + player.getName()
                                    + " has not bought item: " + item.getItemId() + " count: " + item.getItemCount()
                                    + " Cause: NOT ENOUGH TOLLS");
                        }
                    } else if (result == 3) {
                        IGItem item = getIGItem(request.itemObjId);
                        if (item == null) {
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_ERROR);
                            log.error("player " + player.getName() + " requested " + request.itemObjId + " that was not exists in list.");
                            return;
                        }

                        if (request.gift) {
                            // check receiver exists
                            PlayerCommonData pcd = DAOManager.getDAO(PlayerDAO.class).loadPlayerCommonDataByName(request.receiver);
                            if (pcd == null) {
                                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_NO_USER_TO_GIFT);
                                return;
                            }
                            sendShopItemGift(player, item, request.receiver);
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INGAMESHOP_GIFT_SUCCESS);
                            if (LoggingConfig.LOG_INGAMESHOP) {
                                log.info("[INGAMESHOP] > Account name: " + player.getAcountName() + ", PlayerName: " + player.getName()
                                        + " BUY ITEM: " + item.getItemId() + " COUNT: " + item.getItemCount() + " FOR PlayerName: "
                                        + request.receiver);
                            }
                            if (LoggingConfig.LOG_INGAMESHOP_SQL) {
                                DAOManager.getDAO(InGameShopLogDAO.class).log("GIFT", new Timestamp(System.currentTimeMillis()),
                                        player.getName(), player.getAcountName(), request.receiver, item.getItemId(), item.getItemCount(),
                                        item.getItemPrice());
                            }
                        } else {
                            sendShopItem(player, item);
                            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_POSTMAN_NOTIFY_CASH);
                            if (LoggingConfig.LOG_INGAMESHOP) {
                                log.info("[INGAMESHOP] > Account name: " + player.getAcountName() + ", PlayerName: " + player.getName() + " BUY ITEM: " + item.getItemId() + " COUNT: " + item.getItemCount());
                            }
                            if (LoggingConfig.LOG_INGAMESHOP_SQL) {
                                DAOManager.getDAO(InGameShopLogDAO.class).log("BUY", new Timestamp(System.currentTimeMillis()),
                                        player.getName(), player.getAcountName(), player.getName(), item.getItemId(), item.getItemCount(),
                                        item.getItemPrice());
                            }
                            DAOManager.getDAO(InventoryDAO.class).store(player);
                        }
                        item.increaseSales();
                        dao.increaseSales(item.getObjectId(), item.getSalesRanking());
                        PacketSendUtility.sendPacket(player, new SM_TOLL_INFO(toll));
                    } else if (result == 4) {
                        player.getClientConnection().getAccount().setToll(toll);
                        PacketSendUtility.sendPacket(player, new SM_TOLL_INFO(toll));
                    }
                }
                activeRequests.remove(request);
                break;
            }
        }
    }

    private boolean sendShopItem(Player player, IGItem shopItem) {
        Calendar cal = Calendar.getInstance();
        long iTimestamp = cal.getTimeInMillis() / 1000;
        String mailSubject = shopItem.getItemId() + ", " + shopItem.getItemCount();
        String mailMessage = "0," + iTimestamp;
        try {
            SystemMailService.getInstance().sendMail("$$CASH_ITEM_MAIL", player.getName(), mailSubject, mailMessage, shopItem.getItemId(), shopItem.getItemCount(), 0, LetterType.BLACKCLOUD);
            return true;
        } catch (@SuppressWarnings("unused") Exception e) {
            if (LoggingConfig.LOG_INGAMESHOP) {
                log.info("[INGAMESHOP] > Account name: " + player.getAcountName() + ", PlayerName: " + player.getName() + " error while sending mail");
            }
        }
        return false;
    }

    private boolean sendShopItemGift(Player player, IGItem shopItem, String reciverName) {
        String mailSubject = shopItem.getItemId() + ", " + shopItem.getItemCount();
        String mailMessage = "1," + NetworkConfig.GAMESERVER_ID + "," + player.getName();
        try {
            SystemMailService.getInstance().sendMail("$$CASH_ITEM_MAIL", reciverName, mailSubject, mailMessage, shopItem.getItemId(), shopItem.getItemCount(), 0, LetterType.BLACKCLOUD);
            return true;
        } catch (@SuppressWarnings("unused") Exception e) {
            if (LoggingConfig.LOG_INGAMESHOP) {
                log.info("[INGAMESHOP] > Account name: " + player.getAcountName() + ", PlayerName: " + player.getName() + " error while sending gift");
            }
        }
        return false;
    }
}
