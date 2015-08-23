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
package quest.eltnen;

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Talk to Tumblusen (203989). Get rid of the Kaidan Scouts (212010) to the
 * southeast of the Observatory (3). Talk to Tumblusen. Report to Telemachus
 * (203901). Talk to Mabangtah (204020). Talk to Targatu (204024). Kill the
 * Guard at the Watchtower (204046) and scout the Kaidan Headquarters (1).
 * Return to Targatu. Talk to Mabangtah. Report to Tumblusen.
 *
 * @author Rhys2002
 * @reworked vlog
 */
public class _1040ScoutingtheScouts extends QuestHandler {

    private final static int questId = 1040;
    private final static int[] npcs = {203989, 203901, 204020, 204024};
	private final static int[] mobs = { 212010, 212011, 204046 };

    public _1040ScoutingtheScouts() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnDie(questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        for (int mob : mobs) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 1036);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {1300, 1036};
        return defaultOnLvlUpEvent(env, quests, true);
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

		if (targetId == 212010) {
			return defaultOnKillEvent(env, targetId, 1, 4); // 2, 3, 4
		} else if (targetId == 212011) {
			return defaultOnKillEvent(env, targetId, 1, 4); // 2, 3, 4
		}else if (targetId == 204046) {
			if (defaultOnKillEvent(env, targetId, 8, 9)) // 9
			{
				playQuestMovie(env, 36);
				return true;
			}
		}
		return false;
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203989) // Tumblusen
            {
                return sendQuestEndDialog(env);
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 203989) // Tumblusen
            {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        } else if (var == 4) {
                            return sendQuestDialog(env, 1352);
                        }
                    case SELECT_ACTION_1013:
                        if (var == 0) {
                            playQuestMovie(env, 183);
                        }
                    case SETPRO1:
                        defaultCloseDialog(env, 0, 1); // 1
                    case SETPRO2:
                        defaultCloseDialog(env, 4, 5); // 5
                }
            } else if (targetId == 203901) // Telemachus
            {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 5) {
                            return sendQuestDialog(env, 1693);
                        }
                    case SETPRO3:
                        if (var == 5) {
			TeleportService2.teleportTo(player, 210020000, 1596, 1529, 317, (byte) 120, TeleportAnimation.BEAM_ANIMATION);
                            return defaultCloseDialog(env, 5, 6); // 6
                        }
                }
            } else if (targetId == 204020) // Mabangtah
            {
                switch (env.getDialog()) {
                    case USE_OBJECT:
                        if (var == 7) {
							return sendQuestDialog(env, 2035);
                        }
                    case QUEST_SELECT:
                        if (var == 6) {
                            return sendQuestDialog(env, 2034);
                        } else if (var == 10) {
                            return sendQuestDialog(env, 3057);
                        }
                    case SETPRO4:
                        if (var == 6 || var == 7) {
                            TeleportService2.teleportTo(player, 210020000, 2211, 811, 513);
                            qs.setQuestVarById(0, 7); // 7
                            updateQuestStatus(env);
		      	    TeleportService2.teleportTo(player, 210020000, 2211, 811, 513);							
                            return true;
                        }
                    case SETPRO7:
                        return defaultCloseDialog(env, 10, 10, true, false); // reward
                }
            } else if (targetId == 204024) // Targatu
            {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 7) {
                            return sendQuestDialog(env, 2375);
                        } else if (var == 9) {
                            return sendQuestDialog(env, 2716);
                        }
                    case SETPRO5:
                        defaultCloseDialog(env, 7, 8); // 8
                    case SETPRO6:
                        if (var == 9) {
                            TeleportService2.teleportTo(player, 210020000, 1606, 1529, 318);
                            qs.setQuestVarById(0, 10); // 10
                            updateQuestStatus(env);
                            return true;
                        }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDieEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            if (var >= 7 && var <= 10) {
                qs.setQuestVarById(0, 6); // 6
                updateQuestStatus(env);
                return true;
            }
        }
        return false;
    }
}
