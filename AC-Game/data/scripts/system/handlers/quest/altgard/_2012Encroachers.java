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
/**
 * This file is part of Aion Eternity Core <Ver:4.5>.
 *
 * Aion Eternity Core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Aion Eternity Core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Aion Eternity Core. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Pyro refix by Nephis Aller tuer 4 brutes et retourner voir Meiyer
 * Status locked de toutes les missions de Altgard
 * @Fix Majka Ajural
 */
public class _2012Encroachers extends QuestHandler {

    private final static int questId = 2012;
    private final static int[] mob_ids = {210714, 210715}; // Secret Agent, Goon lvl 10

    public _2012Encroachers() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(203559).addOnTalkEvent(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        for (int mob_id : mob_ids) {
            qe.registerQuestNpc(mob_id).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 2011);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 2200, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        /**
         * Initialisation de l'event *
         */
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        /**
         * Si on start la quete *
         */
        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 203559) {

                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 0) // Initialisation du dialogue
                        {
                            return sendQuestDialog(env, 1011);
                        } else if (var <= 5) // Rendu de la quete
                        {
                            return sendQuestDialog(env, 1352);
                        } else if (var >= 5) {
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(env);
                        }
                    case SETPRO1:
                    case SETPRO2:
                        if (var == 0 || var == 5) {
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(env);
                            return sendQuestSelectionDialog(env);
                        }
                }

            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203559) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        /**
         * Checks *
         */
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs.getStatus() != QuestStatus.START) {
            return false;
        }

        switch (targetId) {
            case 210714: // Secret Agent
            case 210715: // Goon
                if (var > 0 && var < 4) // En tuer 4
                {
                    qs.setQuestVarById(0, var + 1);
                    updateQuestStatus(env);
                    return true;
                } else if (var == 4) // Au 4eme REWARD
                {
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(env);
                    return true;
                }
        }
        return false;
    }
}
/**
 * FIN MODIF EVO *
 */
