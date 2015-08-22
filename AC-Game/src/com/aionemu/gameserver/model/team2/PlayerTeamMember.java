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
package com.aionemu.gameserver.model.team2;

import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author ATracer
 */
public class PlayerTeamMember implements TeamMember<Player> {

    final Player player;
    private long lastOnlineTime;

    public PlayerTeamMember(Player player) {
        this.player = player;
    }

    @Override
    public Integer getObjectId() {
        return player.getObjectId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public Player getObject() {
        return player;
    }

    public long getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void updateLastOnlineTime() {
        lastOnlineTime = System.currentTimeMillis();
    }

    public boolean isOnline() {
        return player.isOnline();
    }

    public float getX() {
        return player.getX();
    }

    public float getY() {
        return player.getY();
    }

    public float getZ() {
        return player.getZ();
    }

    public byte getHeading() {
        return player.getHeading();
    }

    public byte getLevel() {
        return player.getLevel();
    }
}
