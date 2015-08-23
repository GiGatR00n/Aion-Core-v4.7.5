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
import com.aionemu.gameserver.dao.InGameShopLogDAO;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author ViAl
 */
public class MySQL5InGameShopLogDAO extends InGameShopLogDAO {

    private static final Logger log = LoggerFactory.getLogger(InGameShopLogDAO.class);
    private static final String INSERT_QUERY = "INSERT INTO `ingameshop_log` (`transaction_type`, `transaction_date`, `payer_name`, `payer_account_name`, `receiver_name`, `item_id`, `item_count`, `item_price`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void log(String transactionType, Timestamp transactionDate, String payerName, String payerAccountName, String receiverName,
                    int itemId, long itemCount, long itemPrice) {
        Connection conn = null;
        try {
            conn = DatabaseFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY);
            stmt.setString(1, transactionType);
            stmt.setTimestamp(2, transactionDate);
            stmt.setString(3, payerName);
            stmt.setString(4, payerAccountName);
            stmt.setString(5, receiverName);
            stmt.setInt(6, itemId);
            stmt.setLong(7, itemCount);
            stmt.setLong(8, itemPrice);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            log.error("Error while inserting ingameshop log. " + e);
        } finally {
            DatabaseFactory.close(conn);
        }
    }

    @Override
    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
