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
package com.aionemu.gameserver.questEngine.model;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.QuestEngine;

/**
 * @author MrPoke
 */
public class QuestEnv {

    private VisibleObject visibleObject;
    private Player player;
    private int questId;
    private int dialogId;
    private int extendedRewardIndex;

    /**
     * @param creature
     * @param player
     * @param questId
     */
    public QuestEnv(VisibleObject visibleObject, Player player, Integer questId, Integer dialogId) {
        super();
        this.visibleObject = visibleObject;
        this.player = player;
        this.questId = questId;
        this.dialogId = dialogId;
    }

    /**
     * @return the visibleObject
     */
    public VisibleObject getVisibleObject() {
        return visibleObject;
    }

    /**
     * @param visibleObject the visibleObject to set
     */
    public void setVisibleObject(VisibleObject visibleObject) {
        this.visibleObject = visibleObject;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the questId
     */
    public Integer getQuestId() {
        return questId;
    }

    /**
     * @param questId the questId to set
     */
    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    /**
     * @return the dialogId
     */
    public Integer getDialogId() {
        return dialogId;
    }

    public DialogAction getDialog() {
        DialogAction dialog = QuestEngine.getInstance().getDialog(dialogId);
        if (dialog == null) {
            return DialogAction.NULL;
        }
        return dialog;
    }

    /**
     * @param dialogId the dialogId to set
     */
    public void setDialogId(Integer dialogId) {
        this.dialogId = dialogId;
    }

    public int getTargetId() {
        if (visibleObject == null) {
            return 0;
        } else if (visibleObject instanceof Npc) {
            return ((Npc) visibleObject).getNpcId();
        } else if (visibleObject instanceof Gatherable) {
            return ((Gatherable) visibleObject).getObjectTemplate().getTemplateId();
        } else if (visibleObject instanceof StaticObject) {
            return ((StaticObject) visibleObject).getObjectTemplate().getTemplateId();
        }
        return 0;
    }

    public void setExtendedRewardIndex(int index) {
        this.extendedRewardIndex = index;
    }

    public int getExtendedRewardIndex() {
        return this.extendedRewardIndex;
    }
}
