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
import com.aionemu.commons.database.ReadStH;
import com.aionemu.gameserver.dao.AnnouncementsDAO;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.model.Announcement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Divinity
 */
public class MySQL5Announcements extends AnnouncementsDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Announcement> getAnnouncements() {
        final Set<Announcement> result = new HashSet<Announcement>();
        DB.select("SELECT * FROM announcements ORDER BY id", new ReadStH() {
            @Override
            public void handleRead(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    result.add(new Announcement(resultSet.getInt("id"), resultSet.getString("announce"), resultSet
                            .getString("faction"), resultSet.getString("type"), resultSet.getInt("delay")));
                }
            }
        });
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAnnouncement(final Announcement announce) {
        DB.insertUpdate("INSERT INTO announcements (announce, faction, type, delay) VALUES (?, ?, ?, ?)", new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, announce.getAnnounce());
                preparedStatement.setString(2, announce.getFaction());
                preparedStatement.setString(3, announce.getType());
                preparedStatement.setInt(4, announce.getDelay());
                preparedStatement.execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delAnnouncement(final int idAnnounce) {
        return DB.insertUpdate("DELETE FROM announcements WHERE id = ?", new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1, idAnnounce);
                preparedStatement.execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(String s, int i, int i1) {
        return MySQL5DAOUtils.supports(s, i, i1);
    }
}
