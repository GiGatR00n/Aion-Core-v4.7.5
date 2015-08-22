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
package com.aionemu.gameserver.model.autogroup;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author xTz
 */
public class AGPlayer {

    private Integer objectId;
    private Race race;
    private PlayerClass playerClass;
    private String name;
    private boolean isInInstance;
    private boolean isOnline;
    private boolean isPressEnter;

    public AGPlayer(Player player) {
        objectId = player.getObjectId();
        race = player.getRace();
        playerClass = player.getPlayerClass();
        name = player.getName();
        isOnline = true;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public Race getRace() {
        return race;
    }

    public String getName() {
        return name;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setInInstance(boolean result) {
        isInInstance = result;
    }

    public boolean isInInstance() {
        return isInInstance;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean result) {
        isOnline = result;
    }

    public boolean isPressedEnter() {
        return isPressEnter;
    }

    public void setPressEnter(boolean result) {
        isPressEnter = result;
    }
}
