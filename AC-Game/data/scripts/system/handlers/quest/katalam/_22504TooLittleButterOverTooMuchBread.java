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
import com.aionemu.gameserver.services.QuestService;

/**
 * 
 * @author Romanz
 */
public class _22504TooLittleButterOverTooMuchBread extends QuestHandler 
{

	private static final int questId = 22504;

	public _22504TooLittleButterOverTooMuchBread() 
	{
		super(questId);
	}

	@Override
	public void register() 
	{
		qe.registerQuestNpc(801314).addOnQuestStart(questId);
		qe.registerQuestNpc(801314).addOnTalkEvent(questId);
		qe.registerQuestNpc(800528).addOnTalkEvent(questId);

	}

	@Override
	public boolean onDialogEvent(QuestEnv env) 
	{
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();

		if(qs == null || qs.getStatus() == QuestStatus.NONE)
		{
			if (targetId == 801314) 
			{
				if (dialog == DialogAction.USE_OBJECT)
					return sendQuestDialog(env, 4);
				else if (dialog == DialogAction.OPEN_STIGMA_WINDOW)
				{
					QuestService.startQuest(env);
					return closeDialogWindow(env);
				}

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
				case 801314: 
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
				case 800528:
				{
					switch (dialog) 
					{
						case QUEST_SELECT: 
						{
							if (var == 1) 
							{
								return sendQuestDialog(env, 2375);
							}
						}
						case SELECT_QUEST_REWARD: 
						{
							return defaultCloseDialog(env, 1, 1, true, true, 5);
						}
					}
				}
			}
		}
		else if (qs.getStatus() == QuestStatus.REWARD)
		{
			if (targetId == 800528)
			{
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}
