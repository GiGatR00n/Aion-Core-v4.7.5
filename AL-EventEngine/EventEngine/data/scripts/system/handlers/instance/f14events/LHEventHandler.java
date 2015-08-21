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

import com.aionemu.gameserver.instance.handlers.EventID;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.concurrent.ScheduledFuture;
import pirate.announcer.Balalaka;
import pirate.events.EventRewardHelper;
import pirate.events.EventScore;

/**
 * Ивент - Последний герой
 *
 * @author flashman
 */
@EventID(eventId = 3)
public class LHEventHandler extends BaseEventHandler {

    private ScheduledFuture endRoundTask;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        round = 1;
        battle_time = 900;
        waitingTime = 15;
        super.onInstanceCreate(instance);
    }

    @Override
    public void onInstanceDestroy() {
        if (this.prestartTasks != null) {
            for (ScheduledFuture sf : this.prestartTasks.values()) {
                sf.cancel(true);
            }
            this.prestartTasks.clear();
            this.prestartTasks = null;
        }
        if (this.endRoundTask != null) {
            this.endRoundTask.cancel(true);
            this.endRoundTask = null;
        }
        this.players = null;
        this.score = null;
    }

    @Override
    public void onEnterInstance(Player player) {
        super.onEnterInstance(player);
        StartPreRoundTask(player);
    }

    @Override
    public boolean isEnemy(Player attacker, Player target) {
        if (attacker != target) {
            return true;
        }
        return super.isEnemy(attacker, target);
    }

    @Override
    public void onPlayerLogOut(Player player) {
        super.onPlayerLogOut(player);
        if (!eventIsComplete) {
            this.removeFromScoreList(player.getObjectId());
            this.ifOnePlayer();
        }
    }

    @Override
    public void onLeaveInstance(Player player) {
        super.onLeaveInstance(player);
        if (!eventIsComplete) {
            this.removeFromScoreList(player.getObjectId());
            this.ifOnePlayer();
        }
    }

    @Override
    public boolean onDie(Player player, Creature lastAttacker) {
        super.onDie(player, lastAttacker);
        this.deathPlayer(player, lastAttacker);
        return true;
    }

    @Override
    public boolean onReviveEvent(Player player) {
        //this.sendSpecMessage(EventManager, "You are eliminated from the fight.", player);
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        PlayerReviveService.revive(player, 100, 100, false, 0);
        player.getGameStats().updateStatsAndSpeedVisually();
        this.moveToEntry(player);
        return true;
    }

    protected void deathPlayer(Player loser, Creature lastAttacker) {
        this.players.remove(loser);
        this.stopTimer(loser);
        if (lastAttacker instanceof Player) {
            EventScore es = this.getScore(lastAttacker.getObjectId());
            es.incKills();
        }

        if (this.players.size() == 1) {
            this.DoReward();
        }
    }

    private void StartPreRoundTask(final Player player) {
        if (instance.isRegistered(player.getObjectId()) && !this.containsPlayer(player.getObjectId())) {
            players.add(player);
        } else {
            return;
        }
        ScheduledFuture task = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                StartRoundTask();
            }
        }, waitingTime * 1000);
        this.startTimer(player, (int) (this.InstanceTime - System.currentTimeMillis()) / 1000);
        AddProtection(player, waitingTime * 1000);
        prestartTasks.put(player.getObjectId(), task);
        this.HealPlayer(player, true, true);
        if (!this.containsInScoreList(player.getObjectId())) {
            this.addToScoreList(player);
        }
    }

    private void StartRoundTask() {
        if (endRoundTask == null) {
            for (Player p : this.players) {
                if (this.prestartTasks.containsKey(p.getObjectId())) {
                    this.prestartTasks.get(p.getObjectId()).cancel(true);
                    this.prestartTasks.remove(p.getObjectId());
                }
                RemoveProtection(p);
                HealPlayer(p);
            }

            if (this.ifOnePlayer()) {
                return;
            }
            this.stopTimer();
            endRoundTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    EndBattle();
                }
            }, battle_time * 1000);
            this.startTimer(this.battle_time);
        }
    }

    private void EndBattle() {
        this.stopTimer();
        for (Player p : this.players) {
            this.sendSpecMessage(EventManager, "Time out");
            this.moveToEntry(p);
        }
    }

    @Override
    protected void DoReward() {
        if (!eventIsComplete) {
            eventIsComplete = true;
            this.stopTimer();
            Player winner = this.players.get(0);
            EventRewardHelper.GiveRewardFor(winner, eType, null, 1);

            Balalaka.sayInWorldOrangeTextCenterWithDelay(EventManager, String.format("Event: %s completed, winner: %s.",
                    eType.getEventTemplate().getEventName(), winner.getName()), 2);

            this.players.clear();
            if (this.prestartTasks != null) {
                for (ScheduledFuture sf : this.prestartTasks.values()) {
                    sf.cancel(true);
                }
                this.prestartTasks.clear();
            }
            if (this.endRoundTask != null) {
                this.endRoundTask.cancel(true);
            }
            this.prestartTasks = null;
            this.endRoundTask = null;

            this.moveToEntry(winner);
        }
    }

    @Override
    protected boolean ifOnePlayer() {
        if (this.players.size() == 1) {
            EventScore es = this.score.get(0);
            es.isWinner = true;
            DoReward();
            return true;
        }
        return false;
    }
}
