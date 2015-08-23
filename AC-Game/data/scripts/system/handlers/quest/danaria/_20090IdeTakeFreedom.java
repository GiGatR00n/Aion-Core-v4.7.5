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
package quest.danaria;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Romanz
 */
public class _20090IdeTakeFreedom extends QuestHandler {

	private final static int questId = 20090;
	
	private final static int[] mobs = {701545};
	

	public _20090IdeTakeFreedom() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcIds = { 800567, 800821, 730736, 800822, 730735, 800823, 800825 };
		qe.registerOnEnterWorld(questId);
		qe.registerOnMovieEndQuest(852, questId);
		qe.registerQuestNpc(206288).addOnAtDistanceEvent(questId);
		qe.registerOnLevelUp(questId);
        qe.registerOnQuestTimerEnd(questId);

        for (int npcId : npcIds) {
			qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
		}
		for (int mob : mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
        qe.registerOnEnterZone(ZoneName.get("IDLDF5RE_SOLO_Q_SENSORYAREA_Q10090A_206288_2_301000000"),questId);
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 20085);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();
		
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 800567) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 0) {
							return sendQuestDialog(env, 1011);
						}
					}
					case SETPRO1: {
						return defaultCloseDialog(env, 0, 1);
					}
				}
			}
			else if (targetId == 800821) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 1) {
							return sendQuestDialog(env, 1352);
						}
					}
					case SETPRO2: {
						return defaultCloseDialog(env, 1, 2); 
					}
				}
			}
			else if (targetId == 730736) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 2) {
							return sendQuestDialog(env, 1693);
						}
					}
					case SETPRO3: {
						WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(301000000);
						InstanceService.registerPlayerWithInstance(newInstance, player);
						TeleportService2.teleportTo(player, 301000000, newInstance.getInstanceId(), 214.67f, 508.323f, 153.23f, (byte) 30, TeleportAnimation.BEAM_ANIMATION);
						return defaultCloseDialog(env, 2, 3); 
					}
				}
			}
			else if (targetId == 800822) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 3) {
							return sendQuestDialog(env, 2034);
						}
					}
					case SETPRO4: {
						return defaultCloseDialog(env, 3, 4); 
					}
				}
			}
			else if (targetId == 730735) { 
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 6) {
							return sendQuestDialog(env, 3057);
						}
					}
					case SETPRO7: {
                        QuestService.addNewSpawn(301000000, player.getInstanceId(), 231068,  544.692383f, 423.005890f,  94.892334f, (byte) 45);
                        QuestService.addNewSpawn(301000000, player.getInstanceId(), 231067,  546.692383f, 438.005890f,  94.892334f, (byte) 45);
                        QuestService.questTimerStart(env, 120);
                        return defaultCloseDialog(env, 6, 7);
					}
				}
			}
			else if (targetId == 800823) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 8) {
							return sendQuestDialog(env, 3398);
						}
					}
					case SET_SUCCEED: {
						TeleportService2.teleportTo(player, 600060000, 1397.245f, 357.63f, 306.26f, (byte) 10, TeleportAnimation.BEAM_ANIMATION);
						return defaultCloseDialog(env, 8, 9, true, false); 
					}
				}
			}
		}
		else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 800825) {
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 5);
				}
				else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onKillEvent(QuestEnv env) {
		return defaultOnKillEvent(env, mobs, 1, 5, 0);
	}
	
	@Override
	public boolean onAtDistanceEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (qs.getQuestVarById(0) == 5) {
				changeQuestStep(env, 5, 6, false);
	  		return true;
			}
  	}
		return false;
	}
	
	@Override
	public boolean onEnterWorldEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
      int var = qs.getQuestVarById(0);
			if (player.getWorldId() == 301000000) {
				if (var == 3) {
					QuestService.addNewSpawn(301000000, player.getInstanceId(), 800822,  218.569061f, 510.007294f,  153.22841f, (byte) 68);
					PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 852));
					return true;
				}
			}
			else {
        if (var >= 3) {
        	qs.setQuestVarById(0, 0);
  				updateQuestStatus(env);
          return true;
        }
      }
		}
		return false;
	}
	
	@Override
	public boolean onDieEvent(QuestEnv env){
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START){
			int var = qs.getQuestVarById(0);
			if (var >= 1) {
				qs.setQuestVar(0);
				updateQuestStatus(env);
				PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1,
				DataManager.QUEST_DATA.getQuestById(questId).getName()));
				return true;
			}
		}
		return false;
	}
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName == ZoneName.get("IDLDF5RE_SOLO_Q_SENSORYAREA_Q10090A_206288_2_301000000")) {
            Player player = env.getPlayer();
            if (player == null)
                return false;
            QuestState qs = player.getQuestStateList().getQuestState(questId);
            if (qs != null && qs.getStatus() == QuestStatus.START) {
                int var = qs.getQuestVarById(0);
                if (var == 5) {
                    changeQuestStep(env, 5, 6, false);
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public boolean onQuestTimerEndEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null)
            return false;
        if  (player.getWorldId() != 301000000)
        {
            changeQuestStep(env, 6, 2, false);
            updateQuestStatus(env);
            return false;
        }
        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
        if (var == 7 && player.getWorldId() == 301000000) {
                changeQuestStep(env, 7,8, false);
                updateQuestStatus(env);
                QuestService.addNewSpawn(301000000, player.getInstanceId(), 800823,  551.692383f, 430.005890f,  94.892334f, (byte) 45);
                return true;
        }
		}
        return false;
    }
}
