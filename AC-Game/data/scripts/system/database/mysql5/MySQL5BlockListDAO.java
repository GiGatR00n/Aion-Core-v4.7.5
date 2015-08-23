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
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.BlockListDAO;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.BlockList;
import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben
 */
public class MySQL5BlockListDAO extends BlockListDAO {

    public static final String LOAD_QUERY = "SELECT blocked_player, reason FROM blocks WHERE player=?";
    public static final String ADD_QUERY = "INSERT INTO blocks (player, blocked_player, reason) VALUES (?, ?, ?)";
    public static final String DEL_QUERY = "DELETE FROM blocks WHERE player=? AND blocked_player=?";
    public static final String SET_REASON_QUERY = "UPDATE blocks SET reason=? WHERE player=? AND blocked_player=?";
    private static Logger log = LoggerFactory.getLogger(MySQL5BlockListDAO.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addBlockedUser(final int playerObjId, final int objIdToBlock, final String reason) {
        return DB.insertUpdate(ADD_QUERY, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerObjId);
                stmt.setInt(2, objIdToBlock);
                stmt.setString(3, reason);
                stmt.execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delBlockedUser(final int playerObjId, final int objIdToDelete) {
        return DB.insertUpdate(DEL_QUERY, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerObjId);
                stmt.setInt(2, objIdToDelete);
                stmt.execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BlockList load(final Player player) {
        final Map<Integer, BlockedPlayer> list = new HashMap<Integer, BlockedPlayer>();

        DB.select(LOAD_QUERY, new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rset) throws SQLException {
                PlayerDAO playerDao = DAOManager.getDAO(PlayerDAO.class);
                while (rset.next()) {
                    int blockedOid = rset.getInt("blocked_player");
                    PlayerCommonData pcd = playerDao.loadPlayerCommonData(blockedOid);
                    if (pcd == null) {
                        log.error("Attempt to load block list for " + player.getName()
                                + " tried to load a player which does not exist: " + blockedOid);
                    } else {
                        list.put(blockedOid, new BlockedPlayer(pcd, rset.getString("reason")));
                    }
                }

            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, player.getObjectId());
            }
        });
        return new BlockList(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setReason(final int playerObjId, final int blockedPlayerObjId, final String reason) {
        return DB.insertUpdate(SET_REASON_QUERY, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, reason);
                stmt.setInt(2, playerObjId);
                stmt.setInt(3, blockedPlayerObjId);
                stmt.execute();

            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
