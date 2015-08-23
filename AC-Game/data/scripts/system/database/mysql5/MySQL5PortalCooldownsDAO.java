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
import com.aionemu.gameserver.dao.PortalCooldownsDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MySQL5PortalCooldownsDAO extends PortalCooldownsDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5PortalCooldownsDAO.class);
    public static final String INSERT_QUERY = "INSERT INTO `portal_cooldowns` (`player_id`, `world_id`, `reuse_time`, `entry_count`) VALUES (?,?,?,?)";
    public static final String DELETE_QUERY = "DELETE FROM `portal_cooldowns` WHERE `player_id`=?";
    public static final String SELECT_QUERY = "SELECT `world_id`, `reuse_time`, `entry_count` FROM `portal_cooldowns` WHERE `player_id`=?";

    @Override
    public void loadPortalCooldowns(final Player player) {
        Connection con = null;
        FastMap<Integer, Long> portalCoolDowns = new FastMap<Integer, Long>();
        PreparedStatement stmt = null;
        try {
            con = DatabaseFactory.getConnection();
            stmt = con.prepareStatement(SELECT_QUERY);

            stmt.setInt(1, player.getObjectId());
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                int worldId = rset.getInt("world_id");
                long reuseTime = rset.getLong("reuse_time");
                @SuppressWarnings("unused")
                int entryCount = rset.getInt("entry_count");
                if (reuseTime > System.currentTimeMillis()) {
                    portalCoolDowns.put(worldId, reuseTime);
                }
            }
            player.getPortalCooldownList().setPortalCoolDowns(portalCoolDowns);
            rset.close();
        } catch (SQLException e) {
            log.error("LoadPortalCooldowns", e);
        } finally {
            DatabaseFactory.close(stmt, con);
        }
    }

    @Override
    public void storePortalCooldowns(final Player player) {
        deletePortalCooldowns(player);
        Map<Integer, Long> portalCoolDowns = player.getPortalCooldownList().getPortalCoolDowns();

        if (portalCoolDowns == null) {
            return;
        }

        for (Map.Entry<Integer, Long> entry : portalCoolDowns.entrySet()) {
            final int worldId = entry.getKey();
            final long reuseTime = entry.getValue();
            final int entryCount = entry.getKey();

            if (reuseTime < System.currentTimeMillis()) {
                continue;
            }

            Connection con = null;

            PreparedStatement stmt = null;
            try {
                con = DatabaseFactory.getConnection();
                stmt = con.prepareStatement(INSERT_QUERY);

                stmt.setInt(1, player.getObjectId());
                stmt.setInt(2, worldId);
                stmt.setLong(3, reuseTime);
                stmt.setLong(4, entryCount);
                stmt.execute();
            } catch (SQLException e) {
                log.error("storePortalCooldowns", e);
            } finally {
                DatabaseFactory.close(stmt, con);
            }
        }
    }

    private void deletePortalCooldowns(final Player player) {

        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DatabaseFactory.getConnection();
            stmt = con.prepareStatement(DELETE_QUERY);

            stmt.setInt(1, player.getObjectId());
            stmt.execute();
        } catch (SQLException e) {
            log.error("deletePortalCooldowns", e);
        } finally {
            DatabaseFactory.close(stmt, con);
        }
    }

    @Override
    public boolean supports(String arg0, int arg1, int arg2) {
        return MySQL5DAOUtils.supports(arg0, arg1, arg2);
    }
}
