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
package com.aionemu.gameserver.model.gameobjects.player.motion;

import com.aionemu.gameserver.model.IExpirable;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MrPoke
 */
public class Motion implements IExpirable {

    static final Map<Integer, Integer> motionType = new HashMap<Integer, Integer>();

    static {
        motionType.put(1, 1);
        motionType.put(2, 2);
        motionType.put(3, 3);
        motionType.put(4, 4);
        motionType.put(5, 1);
        motionType.put(6, 2);
        motionType.put(7, 3);
        motionType.put(8, 4);
        motionType.put(9, 1);
        motionType.put(10, 1);
        //4.3/4.5
        motionType.put(11, 1);
        motionType.put(12, 2);
        motionType.put(13, 3);
        motionType.put(14, 4);
    }

    private int id;
    private int deletionTime = 0;
    private boolean active = false;

    /**
     * @param id
     * @param deletionTime
     */
    public Motion(int id, int deletionTime, boolean isActive) {
        this.id = id;
        this.deletionTime = deletionTime;
        this.active = isActive;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public int getRemainingTime() {
        if (deletionTime == 0) {
            return 0;
        }
        return deletionTime - (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int getExpireTime() {
        return deletionTime;
    }

    @Override
    public void expireEnd(Player player) {
        player.getMotions().remove(id);
    }

    @Override
    public void expireMessage(Player player, int time) {
    }

    @Override
    public boolean canExpireNow() {
        return true;
    }
}
