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
package quest.fort_tiamat;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Cheatkiller
 *
 */
public class _10071OnTheTrailOfIsraphel extends QuestHandler {

    private final static int questId = 10071;

    public _10071OnTheTrailOfIsraphel() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {205579, 205535, 730465, 295987, 730465};
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterWorld(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205579) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                }
            } else if (targetId == 205535) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 1) {
                            return sendQuestDialog(env, 1352);
                        }
                    }
                    case SETPRO2: {
                        if (giveQuestItem(env, 182213242, 1)) {
                            return defaultCloseDialog(env, 1, 2);
                        }
                    }
                }
            } else if (targetId == 205987) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 2) {
                            return sendQuestDialog(env, 1693);
                        } else if (var == 4) {
                            return sendQuestDialog(env, 2375);
                        } else if (var == 5) {
                            return sendQuestDialog(env, 2716);
                        }
                    }
                    case SETPRO3: {
                        removeQuestItem(env, 182213242, 1);
                        return defaultCloseDialog(env, 2, 3);
                    }
                    case SETPRO5: {
                        //remove course
                        return defaultCloseDialog(env, 4, 5);
                    }
                    case SET_SUCCEED: {
                        giveQuestItem(env, 182213243, 1);
                        return defaultCloseDialog(env, 5, 6, true, false);
                    }
                }
            } else if (targetId == 730465) { // Mysterious Orb
                switch (dialog) {
                    case USE_OBJECT: {
                        if (var == 3) {
                            //apply course
                            changeQuestStep(env, 3, 4, false);
                            return true;
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205535) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 10070);
    }
}
