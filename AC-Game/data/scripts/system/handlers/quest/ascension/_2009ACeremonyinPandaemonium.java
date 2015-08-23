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
package quest.ascension;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.craft.CraftSkillUpdateService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author MrPoke
 */
public class _2009ACeremonyinPandaemonium extends QuestHandler {

    private final static int questId = 2009;

    public _2009ACeremonyinPandaemonium() {
        super(questId);
    }

    @Override
    public void register() {
        if (CustomConfig.ENABLE_SIMPLE_2NDCLASS) {
            return;
        }
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(203550).addOnTalkEvent(questId);
        qe.registerQuestNpc(204182).addOnTalkEvent(questId);
        qe.registerQuestNpc(204075).addOnTalkEvent(questId);
        qe.registerQuestNpc(204080).addOnTalkEvent(questId);
        qe.registerQuestNpc(204081).addOnTalkEvent(questId);
        qe.registerQuestNpc(204082).addOnTalkEvent(questId);
        qe.registerQuestNpc(204083).addOnTalkEvent(questId);
        qe.registerQuestNpc(801220).addOnTalkEvent(questId);
        qe.registerQuestNpc(801221).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVars().getQuestVars();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 203550) {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                    case SETPRO1:
                        if (var == 0) {
                            qs.setQuestVar(1);
                            updateQuestStatus(env);
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                            TeleportService2.teleportTo(player, 120010000, 1685f, 1400f, 195f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
                            return true;
                        }
                }
            } else if (targetId == 204182) {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 1) {
                            return sendQuestDialog(env, 1352);
                        }
                    case SELECT_ACTION_1353:
                        if (var == 1) {
                            playQuestMovie(env, 121);
                            return false;
                        }
                    case SETPRO2:
                        return defaultCloseDialog(env, 1, 2); // 2
                }
            } else if (targetId == 204075) {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 2) {
                            return sendQuestDialog(env, 1693);
                        }
                    case SELECT_ACTION_1694:
                        if (var == 2) {
                            playQuestMovie(env, 122);
                            return false;
                        }
                    case SETPRO3:
                        if (var == 2) {
                            PlayerClass playerClass = PlayerClass.getStartingClassFor(player.getCommonData().getPlayerClass());
                            if (playerClass == PlayerClass.WARRIOR) {
                                qs.setQuestVar(10);
                            } else if (playerClass == PlayerClass.SCOUT) {
                                qs.setQuestVar(20);
                            } else if (playerClass == PlayerClass.MAGE) {
                                qs.setQuestVar(30);
                            } else if (playerClass == PlayerClass.PRIEST) {
                                qs.setQuestVar(40);
                            } else if (playerClass == PlayerClass.ENGINEER) {
                                qs.setQuestVar(50);
                            } else if (playerClass == PlayerClass.ARTIST) {
                                qs.setQuestVar(60);
                            }
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(env);
                            return sendQuestSelectionDialog(env);
                        }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 204080 && var == 10) {
                switch (env.getDialogId()) {
                    case -1:
                        return sendQuestDialog(env, 2034);
                    case 1009:
                        return sendQuestDialog(env, 5);
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                        if (QuestService.finishQuest(env, 0)) {
                            return sendQuestSelectionDialog(env);
                        }
                }
            } else if (targetId == 204081 && var == 20) {
                switch (env.getDialogId()) {
                    case -1:
                        return sendQuestDialog(env, 2375);
                    case 1009:
                        return sendQuestDialog(env, 6);
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                        if (QuestService.finishQuest(env, 1)) {
                            return sendQuestSelectionDialog(env);
                        }
                }
            } else if (targetId == 204082 && var == 30) {
                switch (env.getDialogId()) {
                    case -1:
                        return sendQuestDialog(env, 2716);
                    case 1009:
                        return sendQuestDialog(env, 7);
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                        if (QuestService.finishQuest(env, 2)) {
                            return sendQuestSelectionDialog(env);
                        }
                }
            } else if (targetId == 204083 && var == 40) {
                switch (env.getDialogId()) {
                    case -1:
                        return sendQuestDialog(env, 3057);
                    case 1009:
                        return sendQuestDialog(env, 8);
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                        if (QuestService.finishQuest(env, 3)) {
                            return sendQuestSelectionDialog(env);
                        }
                }
            } else if (targetId == 801220 && var == 50) {
                switch (env.getDialogId()) {
                    case -1:
                        return sendQuestDialog(env, 3398);
                    case 1009:
                        return sendQuestDialog(env, 45);
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                        if (QuestService.finishQuest(env, 4)) {
                            return sendQuestSelectionDialog(env);
                        }
                }
            } else if (targetId == 801221 && var == 60) {
                switch (env.getDialogId()) {
                    case -1:
                        return sendQuestDialog(env, 3739);
                    case 1009:
                        return sendQuestDialog(env, 46);
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 18:
                        if (QuestService.finishQuest(env, 5)) {
                            return sendQuestSelectionDialog(env);
                        }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
	CraftSkillUpdateService.getInstance().setMorphRecipe(env.getPlayer());
        return defaultOnLvlUpEvent(env, 2008);
    }
}
