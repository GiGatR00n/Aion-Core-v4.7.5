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
package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import org.apache.commons.lang.StringUtils;

/**
 * @author ATracer
 */
public class Servant extends SummonedObject<Creature> {

    private NpcObjectType objectType;

    /**
     * @param objId
     * @param controller
     * @param spawnTemplate
     * @param objectTemplate
     * @param level
     */
    public Servant(int objId, NpcController controller, SpawnTemplate spawnTemplate, NpcTemplate objectTemplate,
                   byte level) {
        super(objId, controller, spawnTemplate, objectTemplate, level);
    }

    @Override
    public final boolean isEnemy(Creature creature) {
        return getCreator().isEnemy(creature);
    }

    @Override
    public boolean isEnemyFrom(Player player) {
        return getCreator() != null && getCreator().isEnemyFrom(player);
    }

    @Override
    public NpcObjectType getNpcObjectType() {
        return objectType;
    }

    public void setNpcObjectType(NpcObjectType objectType) {
        this.objectType = objectType;
    }

    @Override
    public String getMasterName() {
        return StringUtils.EMPTY;
    }
}
