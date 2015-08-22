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
package com.aionemu.gameserver.questEngine.handlers.template;

import java.util.List;
import java.util.Set;

import javolution.util.FastMap;

import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.questEngine.handlers.models.Monster;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.MathUtil;

/**
 * @author MrPoke reworked Bobobear
 */
public class MentorMonsterHunt extends MonsterHunt {

    private int menteMinLevel;
    private int menteMaxLevel;
    private QuestTemplate qt;

    /**
     * @param questId
     * @param startNpc
     * @param endNpc
     * @param monsters
     */
    public MentorMonsterHunt(int questId, List<Integer> startNpcIds, List<Integer> endNpcIds, FastMap<Monster, Set<Integer>> monsters, int menteMinLevel, int menteMaxLevel) {
        super(questId, startNpcIds, endNpcIds, monsters, 0, 0, null, 0);
        this.menteMinLevel = menteMinLevel;
        this.menteMaxLevel = menteMaxLevel;
        this.qt = DataManager.QUEST_DATA.getQuestById(questId);
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(getQuestId());
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            switch (qt.getMentorType()) {
                case MENTOR:
                    if (player.isMentor()) {
                        PlayerGroup group = player.getPlayerGroup2();
                        for (Player member : group.getMembers()) {
                            if (member.getLevel() >= menteMinLevel && member.getLevel() <= menteMaxLevel
                                    && MathUtil.getDistance(player, member) < GroupConfig.GROUP_MAX_DISTANCE) {
                                return super.onKillEvent(env);
                            }
                        }
                    }
                    break;
                case MENTE:
                    if (player.isInGroup2()) {
                        PlayerGroup group = player.getPlayerGroup2();
                        for (Player member : group.getMembers()) {
                            if (member.isMentor() && MathUtil.getDistance(player, member) < GroupConfig.GROUP_MAX_DISTANCE) {
                                return super.onKillEvent(env);
                            }
                        }
                    }
            }
        }
        return false;
    }
}
