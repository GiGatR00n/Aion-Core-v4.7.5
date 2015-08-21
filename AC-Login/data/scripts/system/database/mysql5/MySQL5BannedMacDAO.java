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

import com.aionemu.commons.database.DB;
import com.aionemu.loginserver.dao.BannedMacDAO;
import com.aionemu.loginserver.model.base.BannedMacEntry;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author KID
 */
public class MySQL5BannedMacDAO extends BannedMacDAO {

    private static Logger log = LoggerFactory.getLogger(MySQL5BannedMacDAO.class);

    @Override
    public Map<String, BannedMacEntry> load() {
        Map<String, BannedMacEntry> map = new FastMap<String, BannedMacEntry>();
        PreparedStatement ps = DB.prepareStatement("SELECT `address`,`time`,`details` FROM `banned_mac`");
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String address = rs.getString("address");
                map.put(address, new BannedMacEntry(address, rs.getTimestamp("time"), rs.getString("details")));
            }
        } catch (SQLException e) {
            log.error("Error loading last saved server time", e);
        } finally {
            DB.close(ps);
        }
        return map;
    }

    @Override
    public boolean update(BannedMacEntry entry) {
        boolean success = false;
        PreparedStatement ps = DB.prepareStatement("REPLACE INTO `banned_mac` (`address`,`time`,`details`) VALUES (?,?,?)");
        try {
            ps.setString(1, entry.getMac());
            ps.setTimestamp(2, entry.getTime());
            ps.setString(3, entry.getDetails());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Error storing BannedMacEntry " + entry.getMac(), e);
        } finally {
            DB.close(ps);
        }

        return success;
    }

    @Override
    public boolean remove(String address) {
        boolean success = false;
        PreparedStatement ps = DB.prepareStatement("DELETE FROM `banned_mac` WHERE address=?");
        try {
            ps.setString(1, address);
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Error removing BannedMacEntry " + address, e);
        } finally {
            DB.close(ps);
        }

        return success;
    }

    @Override
    public void cleanExpiredBans() {
        DB.insertUpdate("DELETE FROM `banned_mac` WHERE time < current_date");
    }

    @Override
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
