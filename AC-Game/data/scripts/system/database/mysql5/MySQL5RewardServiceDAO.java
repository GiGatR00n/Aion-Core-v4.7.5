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
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.RewardServiceDAO;
import com.aionemu.gameserver.model.templates.rewards.RewardEntryItem;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author KID
 */
public class MySQL5RewardServiceDAO extends RewardServiceDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5RewardServiceDAO.class);
    public static final String UPDATE_QUERY = "UPDATE `web_reward` SET `rewarded`=?, received=NOW() WHERE `unique`=?";
    public static final String SELECT_QUERY = "SELECT * FROM `web_reward` WHERE `item_owner`=? AND `rewarded`=?";

    @Override
    public boolean supports(String arg0, int arg1, int arg2) {
        return MySQL5DAOUtils.supports(arg0, arg1, arg2);
    }

    @Override
    public FastList<RewardEntryItem> getAvailable(int playerId) {
        FastList<RewardEntryItem> list = FastList.newInstance();

        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_QUERY);
            stmt.setInt(1, playerId);
            stmt.setInt(2, 0);

            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                int unique = rset.getInt("unique");
                int item_id = rset.getInt("item_id");
                long count = rset.getLong("item_count");
                list.add(new RewardEntryItem(unique, item_id, count));
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {
            log.warn("getAvailable() for " + playerId + " from DB: " + e.getMessage(), e);
        } finally {
            DatabaseFactory.close(con);
        }

        return list;
    }

    @Override
    public void uncheckAvailable(FastList<Integer> ids) {
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt;
            for (int uniqid : ids) {
                stmt = con.prepareStatement(UPDATE_QUERY);
                stmt.setInt(1, 1);
                stmt.setInt(2, uniqid);
                stmt.execute();
                stmt.close();
            }
        } catch (Exception e) {
            log.error("uncheckAvailable", e);
        } finally {
            DatabaseFactory.close(con);
        }
    }
}
