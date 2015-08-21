/*
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
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
 *
 */
package system.handlers.instance.f14events;

import com.aionemu.gameserver.instance.handlers.GeneralEventHandler;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemCooldown;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_COOLDOWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pirate.events.EventScore;

/**
 *
 * @author flashman
 */
public class BaseEventHandler extends GeneralEventHandler {

    protected static final Logger log = LoggerFactory.getLogger(BaseEventHandler.class);
    public final String EventManager = "EventManager";
    protected int round = 1;
    protected int winNeeded = 0;
    protected int waitingTime = 0;
    protected int battle_time = 0;
    protected boolean eventIsComplete = false;
    protected List<EventScore> score;
    protected long StartTime;
    protected long InstanceTime;
    protected FastMap<Integer, ScheduledFuture> prestartTasks;
    protected List<Player> players;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        this.score = new FastList<EventScore>();
        this.StartTime = System.currentTimeMillis() + this.waitingTime * 1000;
        this.InstanceTime = this.StartTime;
        this.players = new FastList<Player>();
        this.prestartTasks = new FastMap<Integer, ScheduledFuture>().shared();
    }

/*
    @Override
    public void onEnterInstance(Player player) {
      //  this.removeCd(player, true);
    }

*/

    @Override
    public void onPlayerLogin(Player player) {
        TeleportService2.moveToBindLocation(player, false);
    }

    @Override
    public boolean onDie(Player player, Creature lastAttacker) {
        this.sendDeathPacket(player, lastAttacker);
        return true;
    }

    @Override
    public void onPlayerLogOut(Player player) {
        players.remove(player);
    }

    @Override
    public void onLeaveInstance(Player player) {
        players.remove(player);
    }

    protected void DoReward() {
    }

    protected void HealPlayer(Player player) {
        this.HealPlayer(player, true, true);
    }

    protected void HealPlayer(Player player, boolean withDp, boolean sendUpdatePacket) {
        player.getLifeStats().setCurrentHpPercent(100);
        player.getLifeStats().setCurrentMpPercent(100);
        if (withDp) {
            player.getCommonData().setDp(4000);
        }
        if (sendUpdatePacket) {
            player.getLifeStats().sendHpPacketUpdate();
            player.getLifeStats().sendMpPacketUpdate();
        }
    }

    /**
     *
     * @param p
     * @param duration в миллисекундах
     */
    protected void AddProtection(final Player p, int duration) {
        Skill protector = SkillEngine.getInstance().getSkill(p, 9833, 1, p.getTarget());
        Effect e = new Effect(p, p, protector.getSkillTemplate(), protector.getSkillLevel(), duration);
        for (EffectTemplate et : e.getEffectTemplates()) {
            et.setDuration2(duration);
        }
        e.initialize();
        e.applyEffect();

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                RemoveProtection(p);
            }
        }, duration);
    }

    /**
     *
     * @param p
     * @param duration в миллисекундах
     * @param delay в миллисекундах
     */
    protected void AddProtection(final Player p, final int duration, int delay) {
        if (delay == 0) {
            this.AddProtection(p, duration);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    AddProtection(p, duration);
                }
            }, delay);
        }
    }

    protected void RemoveProtection(Player p) {
        p.getEffectController().removeEffect(9833);
    }

    /**
     * Посылает всем игрокам в инстансе системное сообщение
     *
     * @param msg
     */
    protected final void sendSysMessageToAll(String msg) {
        for (Player p : this.players) {
            PacketSendUtility.sendBrightYellowMessageOnCenter(p, msg);
        }
    }

    /**
     * Посылает указанному игроку системное сообщение.
     *
     * @param p
     * @param msg
     */
    protected final void sendSysMessageToPlayer(Player p, String msg) {
        PacketSendUtility.sendBrightYellowMessageOnCenter(p, msg);
    }

    /**
     * Посылает сообщение всем игрокам в инстансе, тип сообщения <tt>оранжевый
     * текст без бокса по центру</tt>
     *
     * @param msg
     */
    protected final void sendSpecMessage(String sender, String msg) {
        for (Player p : this.players) {
            PacketSendUtility.sendMessage(p, sender, msg, ChatType.GROUP_LEADER);
        }
    }

    /**
     * Посылает игроку в инстансе, тип сообщения <tt>оранжевый текст без бокса
     * по центру</tt>
     *
     * @param msg
     */
    protected final void sendSpecMessage(String sender, String msg, Player target) {
        PacketSendUtility.sendMessage(target, sender, msg, ChatType.GROUP_LEADER);
    }

    /**
     * Посылает сообщение всем игрокам в инстансе, с указанной задержкой в
     * секундах, тип сообщения <tt>оранжевый текст без бокса по центру</tt>
     *
     * @param sender
     * @param msg
     * @param delay
     */
    protected final void sendSpecMessage(final String sender, final String msg, int delay) {
        if (delay == 0) {
            this.sendSpecMessage(sender, msg);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    sendSpecMessage(sender, msg);
                }
            }, delay * 1000);
        }
    }

    /**
     * Посылает игроку в инстансе, с указанной задержкой в секундах, тип
     * сообщения <tt>оранжевый текст без бокса по центру</tt>
     *
     * @param sender
     * @param msg
     * @param delay
     */
    protected final void sendSpecMessage(final String sender, final String msg, final Player target, int delay) {
        if (delay == 0) {
            this.sendSpecMessage(sender, msg, target);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    sendSpecMessage(sender, msg, target);
                }
            }, delay * 1000);
        }
    }

    protected void sendDeathPacket(Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0, 8));
    }

   protected void removeCd(Player player, boolean withItemsCd) {
        try {
                                List<Integer> delayIds = new ArrayList<Integer>();
				if (player.getSkillCoolDowns() != null) {
					long currentTime = System.currentTimeMillis();
					for (Map.Entry<Integer, Long> en : player.getSkillCoolDowns().entrySet())
						delayIds.add(en.getKey());

					for (Integer delayId : delayIds)
						player.setSkillCoolDown(delayId, currentTime);

					delayIds.clear();
					PacketSendUtility.sendPacket(player, new SM_SKILL_COOLDOWN(player.getSkillCoolDowns()));
            }
            if (withItemsCd) {
                if (player.getItemCoolDowns() != null) {
                    for (Map.Entry<Integer, ItemCooldown> en : player.getItemCoolDowns().entrySet()) {
                        delayIds.add(en.getKey());
                    }
                    for (Integer delayId : delayIds) {
                        player.addItemCoolDown(delayId, 0, 0);
                    }
                    delayIds.clear();
                    PacketSendUtility.sendPacket(player, new SM_ITEM_COOLDOWN(player.getItemCoolDowns()));
                }
            }
            if (player.getSummon() != null) {
                for (Map.Entry<Integer, Long> en : player.getSummon().getSkillCoolDowns().entrySet()) {
                    delayIds.add(en.getKey());
                }
                for (Integer delayId : delayIds) {
                    player.getSummon().setSkillCoolDown(delayId, 0);
                }
                delayIds.clear();
                PacketSendUtility.sendPacket(player, new SM_SKILL_COOLDOWN(player.getSkillCoolDowns()));
            }
        } catch (Exception ex) {
            log.error("[RemoveCD] Error: ", ex);
        }
    }

    protected void moveToEntry(final Player p) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                TeleportService2.moveToBindLocation(p, true);
            }
        }, 5000);
    }

    protected boolean containsPlayer(int id) {
        for (Player p : this.players) {
            if (p != null && p.isOnline() && p.getObjectId() == id) {
                return true;
            }
        }
        return false;
    }

    protected Player getPlayerFromEventList(int id) {
        for (Player p : this.players) {
            if (p != null && p.isOnline() && p.getObjectId() == id) {
                return p;
            }
        }
        return null;
    }

    protected boolean containsInScoreList(int plrObjId) {
        for (EventScore es : this.score) {
            if (es.PlayerObjectId == plrObjId) {
                return true;
            }
        }
        return false;
    }

    protected void addToScoreList(Player player) {
        EventScore es = new EventScore(player.getObjectId());
        this.score.add(es);
    }

    protected boolean removeFromScoreList(int id) {
        for (EventScore es : this.score) {
            if (es.PlayerObjectId == id) {
                this.score.remove(es);
                return true;
            }
        }
        return false;
    }

    protected EventScore getScore(int id) {
        for (EventScore es : this.score) {
            if (es.PlayerObjectId == id) {
                return es;
            }
        }
        return null;
    }

    protected Player getWinnerFromScoreByKills() {
        int kills = -1;
        Player winner = null;
        for (Player p : this.players) {
            EventScore es = this.getScore(p.getObjectId());
            if (es.getKills() > kills) {
                kills = es.getKills();
                winner = p;
            }
        }
        return winner;
    }

    protected boolean ifOnePlayer() {
        if (this.players.size() == 1) {
            EventScore es = this.score.get(0);
            es.setWins(this.winNeeded);
            es.setLoses(0);
            es.isWinner = true;
            DoReward();
            return true;
        }
        return false;
    }

    protected void startTimer(int time) {
        for (Player player : this.players) {
            PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, time));
        }
    }

    protected void stopTimer() {
        for (Player player : this.players) {
            this.stopTimer(player);
        }
    }

    protected void startTimer(Player player, int time) {
        PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, time));
    }

    protected void stopTimer(Player player) {
        PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 0));
    }
}
