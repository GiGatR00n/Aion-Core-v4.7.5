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
package com.aionemu.gameserver.skillengine.effect;

import java.util.concurrent.Future;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Trap;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.spawnengine.VisibleObjectSpawner;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author ATracer
 * @modified Kill3r
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummonTrapEffect")
public class SummonTrapEffect extends SummonEffect {

    @XmlAttribute(name = "skill_id", required = true)
    protected int skillId;

    @Override
    public void applyEffect(Effect effect) {
        Creature effector = effect.getEffector();
        // should only be set if player has no target to avoid errors
        if (effect.getEffector().getTarget() == null) {
            effect.getEffector().setTarget(effect.getEffector());
        }
        double radian = Math.toRadians(MathUtil.convertHeadingToDegree((byte) effect.getEffector().getHeading()));
        float x = effect.getX();
        float y = effect.getY();
        float z = effect.getZ();
        if (x == 0 && y == 0) {
            Creature effected = effect.getEffected();
            x = effected.getX() + (float) (Math.cos(radian) * 2);
            y = effected.getY() + (float) (Math.sin(radian) * 2);
            z = effected.getZ();
        }
        byte heading = effector.getHeading();
        int worldId = effector.getWorldId();
        int instanceId = effector.getInstanceId();

        if (npcId == 749300 || npcId == 749301){
            x = effector.getX();
            y = effector.getY();
            z = effector.getZ();
        }

        SpawnTemplate spawn = SpawnEngine.addNewSingleTimeSpawn(worldId, npcId, x, y, z, heading);
        final Trap trap = VisibleObjectSpawner.spawnTrap(spawn, instanceId, effector, skillId);

        Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                trap.getController().onDelete();
            }
        }, time * 1000);
        trap.getController().addTask(TaskId.DESPAWN, task);
    }
}
