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
package quest.sarpan;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author zhkchi
 *
 */
public class _21521BendingKahrunsWill extends QuestHandler {

    private static final int questId = 21521;

    public _21521BendingKahrunsWill() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799266).addOnQuestStart(questId);
        qe.registerQuestNpc(799266).addOnTalkEvent(questId);
        qe.registerOnKillInWorld(600020000, questId);
    }

    @Override
    public boolean onKillInWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        if (env.getVisibleObject() instanceof Player && player != null && player.isInsideZone(ZoneName.get("HEROS_DISCUS_600020000"))) {
            if ((env.getPlayer().getLevel() >= (((Player) env.getVisibleObject()).getLevel() - 5)) && (env.getPlayer().getLevel() <= (((Player) env.getVisibleObject()).getLevel() + 9))) {
                return defaultOnKillRankedEvent(env, 0, 5, true); // reward
            }
        }
        return false;
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (targetId == 799266) {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 4762);
                    default:
                        return sendQuestStartDialog(env);
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799266) {
                switch (env.getDialog()) {
                    case USE_OBJECT:
                        return sendQuestDialog(env, 10002);
                    case SELECT_QUEST_REWARD:
                        return sendQuestDialog(env, 5);
                    default:
                        return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
