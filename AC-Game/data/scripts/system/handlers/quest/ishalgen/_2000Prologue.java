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
package quest.ishalgen;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author MrPoke
 */
public class _2000Prologue extends QuestHandler {

    private final static int questId = 2000;

    public _2000Prologue() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterWorld(questId);
        qe.registerOnMovieEndQuest(2, questId);
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        if (player.getCommonData().getRace() != Race.ASMODIANS) {
            return false;
        }
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            env.setQuestId(questId);
            QuestService.startQuest(env);
        }
        qs = player.getQuestStateList().getQuestState(questId);
        if (qs.getStatus() == QuestStatus.START) {
            PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(1, 2));
            return true;
        }
        return false;
    }

    @Override
    public boolean onMovieEndEvent(QuestEnv env, int movieId) {
        if (movieId != 2) {
            return false;
        }
        Player player = env.getPlayer();
        if (player.getCommonData().getRace() != Race.ASMODIANS) {
            return false;
        }
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        qs.setStatus(QuestStatus.REWARD);
        QuestService.finishQuest(env);
        return true;
    }
}
