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
package com.aionemu.gameserver.instance.handlers;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.StageList;
import com.aionemu.gameserver.model.instance.StageType;
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.zone.ZoneInstance;

/**
 * @author ATracer
 */
public interface InstanceHandler {

    /**
     * Executed during instance creation.<br>
     * This method will run after spawns are loaded
     *
     * @param instance created
     */
    void onInstanceCreate(WorldMapInstance instance);

    /**
     * Executed during instance destroy.<br>
     * This method will run after all spawns unloaded.<br>
     * All class-shared objects should be cleaned in handler
     */
    void onInstanceDestroy();

    void onPlayerLogin(Player player);

    void onPlayerLogOut(Player player);

    void onEnterInstance(Player player);

    void onLeaveInstance(Player player);

    void onOpenDoor(int door);

    void onEnterZone(Player player, ZoneInstance zone);

    void onLeaveZone(Player player, ZoneInstance zone);

    void onPlayMovieEnd(Player player, int movieId);

    boolean onReviveEvent(Player player);

    void onExitInstance(Player player);

    void doReward(Player player);

    boolean onDie(Player player, Creature lastAttacker);

    void onStopTraining(Player player);

    void onDie(Npc npc);

    void onChangeStage(StageType type);

    void onChangeStageList(StageList list);

    StageType getStage();

    void onDropRegistered(Npc npc);

    void onGather(Player player, Gatherable gatherable);

    InstanceReward<?> getInstanceReward();

    boolean onPassFlyingRing(Player player, String flyingRing);

    void handleUseItemFinish(Player player, Npc npc);

    boolean isEnemy(Player attacker, Player target);
}
