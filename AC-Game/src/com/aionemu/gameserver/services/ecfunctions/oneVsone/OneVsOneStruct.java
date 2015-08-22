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
package com.aionemu.gameserver.services.ecfunctions.oneVsone;

import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Kill3r
 */
public interface OneVsOneStruct {

    int worldid[] = {EventSystem.ONEVSONE_MAP, EventSystem.ONEVSONE_MAP2, EventSystem.ONEVSONE_MAP3};


    ScheduledFuture<?> autoAnnounce(int delayInMin);

    ScheduledFuture<?> duelArenaRegChecker(int deLayInMin);

    boolean isEnemy(Player effector, Player effected);

    void increaseCode();
}
