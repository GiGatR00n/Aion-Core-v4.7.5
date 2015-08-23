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
package playercommands;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mathew
 * @edit Ever'
 */
public class cmd_onlineshop extends PlayerCommand {

    private int nbitem = 0;

    public cmd_onlineshop() {
        super("shop");
    }

    @Override
    public void execute(final Player player, String... params) {
        try {

            DB.select("SELECT object_id, item_id, item_count FROM player_shop WHERE player_id = ?", new ParamReadStH() {
                @Override
                public void setParams(PreparedStatement stmt) throws SQLException {
                    stmt.setInt(1, player.getObjectId());
                }

                @Override
                public void handleRead(ResultSet rset) throws SQLException {
                    while (rset.next()) {
                        final int id = rset.getInt("object_id");
                        int itemId = rset.getInt("item_id");
                        int item_count = rset.getInt("item_count");

                        if (ItemService.addItem(player, itemId, item_count) == 0) {
                            DB.insertUpdate("DELETE FROM player_shop WHERE object_id = ?", new IUStH() {
                                @Override
                                public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                                    ps.setInt(1, id);
                                    ps.execute();
                                }
                            });
                        } else {
                            PacketSendUtility.sendMessage(player, "Your inventory is full. Make free slot and repeat the command.");
                            return;
                        }
                        nbitem++;
                    }
                }
            });
            if (nbitem == 0) {
                PacketSendUtility.sendMessage(player, "No item in queue");
            }
        } catch (Exception ex) {
            PacketSendUtility.sendMessage(player, "Only numbers are allowed");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax: .shop");
    }
}
