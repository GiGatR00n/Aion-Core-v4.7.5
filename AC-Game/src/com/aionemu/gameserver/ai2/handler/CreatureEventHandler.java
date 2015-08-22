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
package com.aionemu.gameserver.ai2.handler;

import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplateType;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.TribeRelationService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.geo.GeoService;

/**
 * @author ATracer
 */
public class CreatureEventHandler {

    /**
     * @param npcAI
     * @param creature
     */
    public static void onCreatureMoved(NpcAI2 npcAI, Creature creature) {
        checkAggro(npcAI, creature);
        if (creature instanceof Player) {
            Player player = (Player) creature;
            QuestEngine.getInstance().onAtDistance(new QuestEnv(npcAI.getOwner(), player, 0, 0));
        }
    }

    /**
     * @param npcAI
     * @param creature
     */
    public static void onCreatureSee(NpcAI2 npcAI, Creature creature) {
        checkAggro(npcAI, creature);
        if (creature instanceof Player) {
            Player player = (Player) creature;
            QuestEngine.getInstance().onAtDistance(new QuestEnv(npcAI.getOwner(), player, 0, 0));
        }
    }

    /**
     * @param ai
     * @param creature
     */
    protected static void checkAggro(NpcAI2 ai, Creature creature) {
        Npc owner = ai.getOwner();

        if (ai.isInState(AIState.FIGHT)) {
            return;
        }

        if (creature.getLifeStats().isAlreadyDead()) {
            return;
        }

        if (!owner.canSee(creature)) {
            return;
        }

        if (!owner.getActiveRegion().isMapRegionActive()) {
            return;
        }

        boolean isInAggroRange = false;

        if (ai.poll(AIQuestion.CAN_SHOUT)) {
            int shoutRange = owner.getObjectTemplate().getMinimumShoutRange();
            double distance = MathUtil.getDistance(owner, creature);
            if (distance <= shoutRange) {
                ShoutEventHandler.onSee(ai, creature);
                isInAggroRange = shoutRange <= owner.getAggroRange();
            }
        }

        if (!ai.isInState(AIState.FIGHT) && (isInAggroRange || MathUtil.isIn3dRange(owner, creature, owner.getAggroRange()))) {
            if (checkAggroRelation(owner, creature) && GeoService.getInstance().canSee(owner, creature)) {
                if (!ai.isInState(AIState.RETURNING)) {
                    ai.getOwner().getMoveController().storeStep();
                }
                if (ai.canThink()) {
                    ai.onCreatureEvent(AIEventType.CREATURE_AGGRO, creature);
                }
            }
        }
    }

    private static boolean checkAggroRelation(Npc owner, Creature creature) {
        if (TribeRelationService.isAggressive(owner, creature)) {
            if (creature.getLevel() - owner.getLevel() <= 10 || owner.getObjectTemplate().getNpcTemplateType() == NpcTemplateType.ABYSS_GUARD) {
                return true;
            }
        }
        return false;
    }
}
