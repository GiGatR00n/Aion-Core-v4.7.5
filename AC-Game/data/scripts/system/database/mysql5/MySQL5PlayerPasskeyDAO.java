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
import com.aionemu.gameserver.dao.PlayerPasskeyDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cura
 */
public class MySQL5PlayerPasskeyDAO extends PlayerPasskeyDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5PlayerPasskeyDAO.class);
    public static final String INSERT_QUERY = "INSERT INTO `player_passkey` (`account_id`, `passkey`) VALUES (?,?)";
    public static final String UPDATE_QUERY = "UPDATE `player_passkey` SET `passkey`=? WHERE `account_id`=? AND `passkey`=?";
    public static final String UPDATE_FORCE_QUERY = "UPDATE `player_passkey` SET `passkey`=? WHERE `account_id`=?";
    public static final String CHECK_QUERY = "SELECT COUNT(*) cnt FROM `player_passkey` WHERE `account_id`=? AND `passkey`=?";
    public static final String EXIST_CHECK_QUERY = "SELECT COUNT(*) cnt FROM `player_passkey` WHERE `account_id`=?";

    @Override
    public void insertPlayerPasskey(int accountId, String passkey) {
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(INSERT_QUERY);

            stmt.setInt(1, accountId);
            stmt.setString(2, passkey);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            log.error("Error saving PlayerPasskey. accountId: " + accountId, e);
        } finally {
            DatabaseFactory.close(con);
        }
    }

    @Override
    public boolean updatePlayerPasskey(int accountId, String oldPasskey, String newPasskey) {
        boolean result = false;
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY);

            stmt.setString(1, newPasskey);
            stmt.setInt(2, accountId);
            stmt.setString(3, oldPasskey);

            if (stmt.executeUpdate() > 0) {
                result = true;
            }
            stmt.close();
        } catch (SQLException e) {
            log.error("Error updating PlayerPasskey. accountId: " + accountId, e);
        } finally {
            DatabaseFactory.close(con);
        }

        return result;
    }

    @Override
    public boolean updateForcePlayerPasskey(int accountId, String newPasskey) {
        boolean result = false;
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE_FORCE_QUERY);

            stmt.setString(1, newPasskey);
            stmt.setInt(2, accountId);

            if (stmt.executeUpdate() > 0) {
                result = true;
            }
            stmt.close();
        } catch (SQLException e) {
            log.error("Error updaing PlayerPasskey. accountId: " + accountId, e);
        } finally {
            DatabaseFactory.close(con);
        }

        return result;
    }

    @Override
    public boolean checkPlayerPasskey(int accountId, String passkey) {
        boolean passkeyChecked = false;
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(CHECK_QUERY);

            stmt.setInt(1, accountId);
            stmt.setString(2, passkey);

            ResultSet rset = stmt.executeQuery();
            if (rset.next()) {
                if (rset.getInt("cnt") == 1) {
                    passkeyChecked = true;
                }
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {
            log.error("Error loading PlayerPasskey. accountId: " + accountId, e);
            return false;
        } finally {
            DatabaseFactory.close(con);
        }

        return passkeyChecked;
    }

    @Override
    public boolean existCheckPlayerPasskey(int accountId) {
        boolean existPasskeyChecked = false;
        Connection con = null;

        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(EXIST_CHECK_QUERY);

            stmt.setInt(1, accountId);

            ResultSet rset = stmt.executeQuery();
            if (rset.next()) {
                if (rset.getInt("cnt") == 1) {
                    existPasskeyChecked = true;
                }
            }

            rset.close();
            stmt.close();
        } catch (SQLException e) {
            log.error("Error loading PlayerPasskey. accountId: " + accountId, e);
            return false;
        } finally {
            DatabaseFactory.close(con);
        }

        return existPasskeyChecked;
    }

    @Override
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
