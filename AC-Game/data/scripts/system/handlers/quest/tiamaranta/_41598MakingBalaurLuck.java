/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 2 of the License, or (at your option) any
 * later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. *
 *
 * You should have received a copy of the GNU General Public License along with
 * Aion-Lightning. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below Please do
 * not change here something, ragarding the developer credits, except the
 * "developed by XXXX". Even if you edit a lot of files in this source, you
 * still have no rights to call it as "your Core". Everybody knows that this
 * Emulator Core was developed by Aion Lightning
 *
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package quest.tiamaranta;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import static com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE.*;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.*;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Luzien
 * @Reworked Majka Ajural
 *
 */
public class _41598MakingBalaurLuck extends QuestHandler {

	private final static int questId = 41598;
	private final static int requiredQuestItem1Id = 186000096; // Platinum Medal
	private final static int requiredQuestItem2Id = 186000174; // Balaur Serum
	private final static int startingNpcId = 730555; // Basilika Cavern Coin Fountain

	public _41598MakingBalaurLuck() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(startingNpcId).addOnQuestStart(questId);
		qe.registerQuestNpc(startingNpcId).addOnTalkEvent(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();

		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (targetId == startingNpcId) {
				switch (dialog) {
					case QUEST_SELECT: {
						if (QuestService.inventoryItemCheck(env, true)) {
							return sendQuestDialog(env, 1011);
						}
						return true;
					}
					case QUEST_ACCEPT_SIMPLE:
						if (!player.getInventory().isFullSpecialCube()) {
							if (QuestService.startQuest(env)) {
								return sendQuestDialog(env, 2375);
							}
						} else {
							PacketSendUtility.sendPacket(player, STR_MSG_FULL_INVENTORY);
							return sendQuestSelectionDialog(env);
						}
					case QUEST_REFUSE_SIMPLE:
					case FINISH_DIALOG: // To avoid double opening on quest selected window if closed
						return closeDialogWindow(env);
					default:
						return sendQuestSelectionDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {
			if (targetId == startingNpcId) {
				if (dialog.equals(DialogAction.CHECK_USER_HAS_QUEST_ITEM_SIMPLE)) {
					if (QuestService.collectItemCheck(env, false)) {
						changeQuestStep(env, 0, 0, true);
						return sendQuestDialog(env, 5);
					} else { // If removed from inventory for any reason
						sendMissingItemMsg(player, requiredQuestItem1Id);
						sendMissingItemMsg(player, requiredQuestItem2Id);
					}
				}
				
				return sendQuestDialog(env, 2375);
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == startingNpcId) {
				if (dialog == DialogAction.SELECTED_QUEST_NOREWARD) {
					if (QuestService.collectItemCheck(env, true)) {
						return sendQuestEndDialog(env);
					} else { // If removed from inventory for any reason after reward status
						sendMissingItemMsg(player, requiredQuestItem1Id);
						sendMissingItemMsg(player, requiredQuestItem2Id);
						return sendQuestDialog(env, 2375); 
					}
				} else {
					return sendQuestDialog(env, 5);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onCanAct(QuestEnv env, QuestActionType questEventType, Object... objects) {
		return env.getTargetId() == startingNpcId;
	}
	
	/**
	* Sends a warning message to player if some required item is missing.
	*
	* @param	player					the instance of player
	* @param	requiredItemId		id of required item
	* @return	void
	*/
	private void sendMissingItemMsg(Player player, int requiredItemId) {
		if(player.getInventory().getItemCountByItemId(requiredItemId) == 0)  {
			PacketSendUtility.sendPacket(player, STR_QUEST_GET_REWARD_ERROR_NO_QUEST_ITEM_MULTIPLE("1", DataManager.ITEM_DATA.getItemTemplate(requiredItemId).getName()));
		}
	}
}
