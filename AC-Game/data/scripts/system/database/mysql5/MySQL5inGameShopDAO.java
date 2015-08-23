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
package mysql5;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.InGameShopDAO;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.model.ingameshop.IGItem;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xTz
 */
public class MySQL5inGameShopDAO extends InGameShopDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5inGameShopDAO.class);
    public static final String SELECT_QUERY = "SELECT `object_id`, `item_id`, `item_count`, `item_price`, `category`, `sub_category`, `list`, `sales_ranking`, `item_type`, `gift`, `title_description`, `description` FROM `ingameshop`";
    public static final String DELETE_QUERY = "DELETE FROM `ingameshop` WHERE `item_id`=? AND `category`=? AND `sub_category`=? AND `list`=?";
    public static final String UPDATE_SALES_QUERY = "UPDATE `ingameshop` SET `sales_ranking`=? WHERE `object_id`=?";

    @Override
    public FastMap<Byte, List<IGItem>> loadInGameShopItems() {
        FastMap<Byte, List<IGItem>> items = FastMap.newInstance();
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_QUERY);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                byte category = rset.getByte("category");
                byte subCategory = rset.getByte("sub_category");
                if (subCategory < 3) {
                    continue;
                }

                int objectId = rset.getInt("object_id");
                int itemId = rset.getInt("item_id");
                long itemCount = rset.getLong("item_count");
                long itemPrice = rset.getLong("item_price");
                int list = rset.getInt("list");
                int salesRanking = rset.getInt("sales_ranking");
                byte itemType = rset.getByte("item_type");
                byte gift = rset.getByte("gift");
                String titleDescription = rset.getString("title_description");
                String description = rset.getString("description");
                if (!items.containsKey(category)) {
                    items.put(category, new ArrayList<IGItem>());
                }
                items.get(category).add(new IGItem(objectId, itemId, itemCount, itemPrice,
                        category, subCategory, list, salesRanking, itemType, gift, titleDescription, description));
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {
            log.error("Could not restore inGameShop data for all from DB: " + e.getMessage(), e);
        } finally {
            DatabaseFactory.close(con);
        }
        return items;
    }

    @Override
    public boolean deleteIngameShopItem(int itemId, byte category, byte subCategory, int list) {
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(DELETE_QUERY);
            stmt.setInt(1, itemId);
            stmt.setInt(2, category);
            stmt.setInt(3, subCategory);
            stmt.setInt(4, list);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            log.error("Error delete ingameshopItem: " + itemId, e);
            return false;
        } finally {
            DatabaseFactory.close(con);
        }
        return true;
    }

    @Override
    public void saveIngameShopItem(int objectId, int itemId, long itemCount, long itemPrice, byte category, byte subCategory, int list, int salesRanking,
                                   byte itemType, byte gift, String titleDescription, String description) {
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con
                    .prepareStatement("INSERT INTO ingameshop(object_id, item_id, item_count, item_price, category, sub_category, list, sales_ranking, item_type, gift, title_description, description)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            stmt.setInt(1, objectId);
            stmt.setInt(2, itemId);
            stmt.setLong(3, itemCount);
            stmt.setLong(4, itemPrice);
            stmt.setByte(5, category);
            stmt.setByte(6, subCategory);
            stmt.setInt(7, list);
            stmt.setInt(8, salesRanking);
            stmt.setByte(9, itemType);
            stmt.setByte(10, gift);
            stmt.setString(11, titleDescription);
            stmt.setString(12, description);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            log.error("Error saving Item: " + objectId, e);
        } finally {
            DatabaseFactory.close(con);
        }
    }

    @Override
    public boolean increaseSales(int object, int current) {
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE_SALES_QUERY);
            stmt.setInt(1, current);
            stmt.setInt(2, object);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            log.error("Error increaseSales Item: " + object, e);
            return false;
        } finally {
            DatabaseFactory.close(con);
        }

        return true;
    }

    @Override
    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
