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
package quest.tiamaranta;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author pralinka
 */
public class _14081TheHeartOfThePresent extends QuestHandler {

    private final static int questId = 14081;

    public _14081TheHeartOfThePresent() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(802059).addOnTalkEvent(questId); //Protector Oriata
        qe.registerQuestNpc(702090).addOnTalkEvent(questId); //Exploding Rock
        qe.registerOnEnterZone(ZoneName.get("LDF4B_SensoryArea_Q14081_206351_600030000"), questId);
        qe.registerQuestNpc(233869).addOnKillEvent(questId);
        qe.registerQuestNpc(218766).addOnKillEvent(questId);
        qe.registerQuestItem(182215404, questId);
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 14080, true);
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3) {
                int targetId = env.getTargetId();
                if (targetId == 218766) {
                    Npc npc = (Npc) env.getVisibleObject();
                    QuestService.addNewSpawn(600030000, player.getInstanceId(), 702090, npc.getX(), npc.getY(), npc.getZ(),
                            (byte) 0);
                    return true;
                }
            } else if (var == 2) {
                int targetId = env.getTargetId();
                if (targetId == 233869) {
                    return defaultOnKillEvent(env, 233869, 2, 3);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (zoneName.equals(ZoneName.get("LDF4B_SensoryArea_Q14081_206351_600030000"))) {
                if (var == 4) {
                    changeQuestStep(env, 4, 4, true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 802059: { //Protector Oriata
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            }
                        }
                        case SETPRO1: {
                            giveQuestItem(env, 182215404, 1);
                            return defaultCloseDialog(env, 0, 1);
                        }
                    }
                    break;
                }
                case 702090: { //Exploding Rock
                    switch (dialog) {
                        case USE_OBJECT: {
                            if (var == 3) {
                                Npc npc = (Npc) env.getVisibleObject();
                                npc.getController().onDelete();
                                return defaultCloseDialog(env, 3, 4);
                            }
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 802059) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.isInsideZone(ZoneName.get("LDF4B_ITEMUSEAREA_Q10060A")) && item.getItemId() == 182215404) {
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 702314, player.getX(), player.getY(), player.getZ(), (byte) 100);
                    }
                }, 3000);
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        for (Npc npc : World.getInstance().getNpcs()) {
                            if (npc.getNpcId() == 702314) {
                                npc.getController().onDelete();
                            }
                        }
                    }
                }, 30000);
                return HandlerResult.fromBoolean(useQuestItem(env, item, 1, 2, false));
            }
        }
        return HandlerResult.FAILED;
    }
}
