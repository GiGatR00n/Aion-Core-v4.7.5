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
package com.aionemu.gameserver.model.house;

import java.sql.Timestamp;

/**
 * @author Rolandas
 */
public class PlayerHouseBid implements Comparable<PlayerHouseBid> {

    private int playerId;
    private int houseId;
    private long offer;
    private Timestamp time;

    public PlayerHouseBid(int playerId, int houseId, long offer, Timestamp time) {
        this.playerId = playerId;
        this.houseId = houseId;
        this.offer = offer;
        this.time = time;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getHouseId() {
        return houseId;
    }

    public long getBidOffer() {
        return offer;
    }

    public Timestamp getTime() {
        return time;
    }

    /**
     * Order by date ascending
     */
    @Override
    public int compareTo(PlayerHouseBid o) {
        return (int) (time.getTime() - o.getTime().getTime());
    }
}
