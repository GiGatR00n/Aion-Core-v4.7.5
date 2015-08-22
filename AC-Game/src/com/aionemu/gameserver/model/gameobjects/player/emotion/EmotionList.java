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
package com.aionemu.gameserver.model.gameobjects.player.emotion;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.MembershipConfig;
import com.aionemu.gameserver.dao.PlayerEmotionListDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION_LIST;
import com.aionemu.gameserver.taskmanager.tasks.ExpireTimerTask;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MrPoke
 */
public class EmotionList {

    private Map<Integer, Emotion> emotions;
    private Player owner;

    /**
     * @param owner
     */
    public EmotionList(Player owner) {
        this.owner = owner;
    }

    public void add(int emotionId, int dispearTime, boolean isNew) {
        if (emotions == null) {
            emotions = new HashMap<Integer, Emotion>();
        }
        Emotion emotion = new Emotion(emotionId, dispearTime);
        emotions.put(emotionId, emotion);

        if (isNew) {
            if (emotion.getExpireTime() != 0) {
                ExpireTimerTask.getInstance().addTask(emotion, owner);
            }
            DAOManager.getDAO(PlayerEmotionListDAO.class).insertEmotion(owner, emotion);
            PacketSendUtility.sendPacket(owner, new SM_EMOTION_LIST((byte) 1, Collections.singletonList(emotion)));
        }
    }

    public void remove(int emotionId) {
        emotions.remove(emotionId);
        DAOManager.getDAO(PlayerEmotionListDAO.class).deleteEmotion(owner.getObjectId(), emotionId);
        PacketSendUtility.sendPacket(owner, new SM_EMOTION_LIST((byte) 0, getEmotions()));
    }

    public boolean contains(int emotionId) {
        if (emotions == null) {
            return false;
        }
        return emotions.containsKey(emotionId);
    }

    public boolean canUse(int emotionId) {
        return emotionId < 64 || emotionId > 148 || (emotions != null && emotions.containsKey(emotionId)) || owner.havePermission(MembershipConfig.EMOTIONS_ALL);
    }

    public Collection<Emotion> getEmotions() {
        if (emotions == null) {
            return Collections.emptyList();
        }
        return emotions.values();
    }
}
