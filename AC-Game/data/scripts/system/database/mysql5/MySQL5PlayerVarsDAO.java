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
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.PlayerVarsDAO;
import javolution.util.FastMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author KID
 */
public class MySQL5PlayerVarsDAO extends PlayerVarsDAO {

    @Override
    public Map<String, Object> load(final int playerId) {
        final Map<String, Object> map = FastMap.newInstance();
        DB.select("SELECT param,value FROM player_vars WHERE player_id=?", new ParamReadStH() {
            @Override
            public void handleRead(ResultSet rset) throws SQLException {
                while (rset.next()) {
                    String key = rset.getString("param");
                    String value = rset.getString("value");
                    map.put(key, value);
                }
            }

            @Override
            public void setParams(PreparedStatement st) throws SQLException {
                st.setInt(1, playerId);
            }
        });

        return map;
    }

    @Override
    public boolean set(final int playerId, final String key, final Object value) {
        boolean result = DB.insertUpdate(
                "INSERT INTO player_vars (`player_id`, `param`, `value`, `time`) VALUES (?,?,?,NOW())", new IUStH() {
                    @Override
                    public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                        stmt.setInt(1, playerId);
                        stmt.setString(2, key);
                        stmt.setString(3, value.toString());
                        stmt.execute();
                    }
                });

        return result;
    }

    @Override
    public boolean remove(final int playerId, final String key) {
        boolean result = DB.insertUpdate("DELETE FROM player_vars WHERE player_id=? AND param=?", new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, playerId);
                stmt.setString(2, key);
                stmt.execute();
            }
        });

        return result;
    }

    @Override
    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
