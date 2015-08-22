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
package quest.katalam;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * 
 * @author Ritsu
 */
public class _12500WhileYouWereGone extends QuestHandler 
{

	private static final int questId = 12500;

	public _12500WhileYouWereGone() 
	{
		super(questId);
	}

	@Override
	public void register() 
	{
		qe.registerQuestNpc(800527).addOnQuestStart(questId);
		qe.registerQuestNpc(800527).addOnTalkEvent(questId);
		qe.registerQuestNpc(801026).addOnTalkEvent(questId);
		qe.registerQuestNpc(801027).addOnTalkEvent(questId);
		qe.registerQuestNpc(801254).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if(qs == null || qs.getStatus() == QuestStatus.NONE)
		{
			if (targetId == 800527) 
			{
				if (dialog == DialogAction.QUEST_SELECT)
					return sendQuestDialog(env, 1011);
				else 
					return sendQuestStartDialog(env);
			}
		}

		if (qs == null)
			return false;
		else if (qs.getStatus() == QuestStatus.START) 
		{
			int var = qs.getQuestVarById(0);
			switch (targetId) 
			{
				case 801026: 
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 0) 
							{
								return sendQuestDialog(env, 1352);
							}
						}
						case SETPRO1: 
						{
							return defaultCloseDialog(env, 0, 1);
						}
					}
				}
				break;
				case 801027:
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 1) 
							{
								return sendQuestDialog(env, 1693);
							}
						}
						case SETPRO2: 
						{
							return defaultCloseDialog(env, 1, 2);
						}
					}
				}
				break;
				case 801254:
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 2) 
							{
								return sendQuestDialog(env, 2034);
							}
						}
						case SETPRO3: 
						{
							return defaultCloseDialog(env, 2, 3);
						}
					}
				}
				break;
				case 800527:
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 3) 
							{
								return sendQuestDialog(env, 2375);
							}
						}
						case SELECT_QUEST_REWARD: 
						{
							return defaultCloseDialog(env, 3, 3, true, true, 5);
						}
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 800527)
			{
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
