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
package quest.weapon_enchant;

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;

public class _22410ThePerfectAethercannon extends QuestHandler {

    private final static int questId = 22410;

    public _22410ThePerfectAethercannon() {
        super(questId);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {10064};
        return defaultOnLvlUpEvent(env, quests, true);
    }

    @Override
    public void register() {
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(205892).addOnQuestStart(questId); //Ulian.
        qe.registerQuestNpc(205892).addOnTalkEvent(questId); //Ulian.
        qe.registerQuestNpc(205891).addOnTalkEvent(questId); //Grais.
        qe.registerQuestNpc(205575).addOnTalkEvent(questId); //Lumen.
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205892) { //Ulian.
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1011);
                    case QUEST_ACCEPT_SIMPLE:
                        return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205891) { //Grais.
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1352);
                    case SETPRO1:
                        TeleportService2.teleportTo(player, 600020000, 1444.7386f, 1359.0792f, 602.5101f, (byte) 103, TeleportAnimation.BEAM_ANIMATION);
                        return defaultCloseDialog(env, 0, 1);
                }
            }
            if (targetId == 205575) { //Lumen.
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1693);
                    case SETPRO2:
                        return defaultCloseDialog(env, 1, 2);
                }
            }
            if (targetId == 205892) { //Ulian.
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 2375);
                    case CHECK_USER_HAS_QUEST_ITEM_SIMPLE:
                        if (QuestService.collectItemCheck(env, true)) {
                            removeQuestItem(env, 101900783, 1); //Adella's Aethercannon.
                            removeQuestItem(env, 186000153, 1); //Upsorceller.
                            removeQuestItem(env, 186000154, 1); //Magical Aether Crystal.
                            removeQuestItem(env, 186000155, 1); //Fusion Fragment.
                            qs.setStatus(QuestStatus.REWARD);
                            return sendQuestDialog(env, 5);
                        }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205892) //Ulian.
            {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }
}
