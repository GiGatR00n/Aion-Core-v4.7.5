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
package quest.gelkmaros;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Nephis
 * @reworked & modified Gigi
 */
public class _20025QuestForSielsRelics extends QuestHandler {

    private final static int questId = 20025;
    private final static int[] npc_ids = {799225, 799226, 799341, 798800, 204182, 799239, 204837, 799327, 799328};

    public _20025QuestForSielsRelics() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(799342).addOnKillEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("BESHMUNDIRS_WALK_300170000"), questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName != ZoneName.get("BESHMUNDIRS_WALK_300170000")) {
            return false;
        }
        final Player player = env.getPlayer();
        if (player == null) {
            return false;
        }
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getQuestVars().getQuestVars() != 12) {
            return false;
        }
        env.setQuestId(questId);
        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
        updateQuestStatus(env);
        return true;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (targetId == 799342) {
            if (qs.getQuestVarById(0) == 13) {
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(env);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 20024, true);
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
            if (targetId == 799225) {
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id()) {
                    return sendQuestDialog(env, 5);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
            return false;
        } else if (qs.getStatus() != QuestStatus.START) {
            return false;
        }
        if (targetId == 799225) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 0) {
                        return sendQuestDialog(env, 1011);
                    }
                case SETPRO1:
                    return defaultCloseDialog(env, 0, 1); // 1
            }
        } else if (targetId == 799226) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 1) {
                        return sendQuestDialog(env, 1352);
                    } else if (var == 4) {
                        return sendQuestDialog(env, 2375);
                    } else if (var == 9) {
                        return sendQuestDialog(env, 4080);
                    }
                case SETPRO2:
                    return defaultCloseDialog(env, 1, 2); // 2
                case SETPRO5:
                    return defaultCloseDialog(env, 4, 5); // 5
                case SETPRO10:
                    return defaultCloseDialog(env, 9, 10); // 10
            }
        } else if (targetId == 799341) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 2) {
                        return sendQuestDialog(env, 1693);
                    } else if (var == 3) {
                        return sendQuestDialog(env, 2034);
                    }
                case SETPRO3:
                    return defaultCloseDialog(env, 2, 3); // 3
                case SETPRO4:
                    return defaultCloseDialog(env, 3, 4); // 4
            }
        } else if (targetId == 798800) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 5) {
                        return sendQuestDialog(env, 2716);
                    }
                case SETPRO6:
                    return defaultCloseDialog(env, 5, 6); // 6
            }
        } else if (targetId == 204182) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 6) {
                        return sendQuestDialog(env, 3057);
                    }
                case SETPRO7:
                    return defaultCloseDialog(env, 6, 7); // 7
            }
        } else if (targetId == 799239) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 7) {
                        return sendQuestDialog(env, 3398);
                    }
                case SETPRO8:
                    return defaultCloseDialog(env, 7, 8); // 8
            }
        } else if (targetId == 204837) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 8) {
                        return sendQuestDialog(env, 3739);
                    }
                case CHECK_USER_HAS_QUEST_ITEM:
                    if (QuestService.collectItemCheck(env, true)) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    } else {
                        return sendQuestDialog(env, 10001);
                    }

            }
        } else if (targetId == 799327) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 10) {
                        return sendQuestDialog(env, 1267);
                    }
                case SETPRO11:
                    return defaultCloseDialog(env, 10, 11); // 11
            }
        } else if (targetId == 799328) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 11) {
                        return sendQuestDialog(env, 1608);
                    }
                case SETPRO12:
                    return defaultCloseDialog(env, 11, 12); // 12
            }
        }
        return false;
    }
}
