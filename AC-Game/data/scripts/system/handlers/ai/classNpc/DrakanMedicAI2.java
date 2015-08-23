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
package ai.classNpc;

import ai.AggressiveFirstSkillAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.npc.NpcRating;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.spawnengine.VisibleObjectSpawner;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Cheatkiller
 */
@AIName("drakanmedic")
public class DrakanMedicAI2 extends AggressiveFirstSkillAI2 {

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (Rnd.get(1, 100) < 3) {
            spawnServant();
        }
    }

    private void spawnServant() {
        int servant = getOwner().getObjectTemplate().getRating() == NpcRating.NORMAL ? 281621 : 281839;
        Npc holyServant = getPosition().getWorldMapInstance().getNpc(servant);
        if (holyServant == null) {
            rndSpawn(servant);
            NpcShoutsService.getInstance().sendMsg(getOwner(), 341784, getObjectId(), 0, 0);
        }
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        despawnServant();
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        despawnServant();
    }

    private void despawnServant() {
        getOwner().getKnownList().doOnAllNpcs(new Visitor<Npc>() {
            @Override
            public void visit(Npc object) {
                int servant = getOwner().getObjectTemplate().getRating() == NpcRating.NORMAL ? 281621 : 281839;
                Npc holyServant = getPosition().getWorldMapInstance().getNpc(servant);
                if (holyServant != null) {
                    holyServant.getController().onDelete();
                }
            }
        });
    }

    private void rndSpawn(int npcId) {
        SpawnTemplate template = rndSpawnInRange(npcId);
        VisibleObjectSpawner.spawnEnemyServant(template, getOwner().getInstanceId(), getOwner(), (byte) getOwner().getLevel());
    }

    private SpawnTemplate rndSpawnInRange(int npcId) {
        float direction = Rnd.get(0, 199) / 100f;
        float x1 = (float) (Math.cos(Math.PI * direction) * 5);
        float y1 = (float) (Math.sin(Math.PI * direction) * 5);
        return SpawnEngine.addNewSingleTimeSpawn(getPosition().getMapId(), npcId, getPosition().getX() + x1, getPosition().getY()
                + y1, getPosition().getZ(), getPosition().getHeading());
    }
}
