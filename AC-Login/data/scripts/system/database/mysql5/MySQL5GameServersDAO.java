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
import com.aionemu.commons.database.ReadStH;
import com.aionemu.loginserver.GameServerInfo;
import com.aionemu.loginserver.dao.GameServersDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * GameServers DAO implementation for MySQL5
 *
 * @author -Nemesiss-
 */
public class MySQL5GameServersDAO extends GameServersDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Byte, GameServerInfo> getAllGameServers() {

        final Map<Byte, GameServerInfo> result = new HashMap<Byte, GameServerInfo>();
        DB.select("SELECT * FROM gameservers", new ReadStH() {
            @Override
            public void handleRead(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    byte id = resultSet.getByte("id");
                    String ipMask = resultSet.getString("mask");
                    String password = resultSet.getString("password");
                    GameServerInfo gsi = new GameServerInfo(id, ipMask, password);
                    result.put(id, gsi);
                }
            }
        });
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
