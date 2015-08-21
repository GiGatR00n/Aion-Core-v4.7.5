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
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.Collections;
import java.util.concurrent.ScheduledFuture;
import pirate.announcer.Balalaka;
import pirate.events.EventRewardHelper;
import pirate.events.EventScore;
import pirate.events.xml.EventStartPosition;
import pirate.events.xml.EventStartPositionList;

/**
 * Ивент - каждый сам за себя
 *
 * @author flashman
 */
@EventID(eventId = 4)
public class FFAEventHandler extends BaseEventHandler {

    private ScheduledFuture endRoundTask;
    private ScheduledFuture waitingTask;
    private EventStartPositionList spawnPoints;
    private boolean hasFb = false;
    private final int FB_Bonus = 200;
    private final int Points_per_kill = 50;
    private final int Points_per_death = Points_per_kill / 2;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        round = 1;
        waitingTime = 15;
        battle_time = 360;
        this.spawnPoints = eType.getEventTemplate().getStartPositionInfo();
        super.onInstanceCreate(instance);

        this.waitingTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                StartRoundTask();
            }
        }, this.waitingTime * 1000);
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

        if (instance.isRegistered(player.getObjectId()) && !this.containsPlayer(player.getObjectId())) {
            players.add(player);
        } else {
            return;
        }
        AddProtection(player, waitingTime * 1000);
        if (!this.containsInScoreList(player.getObjectId())) {
            this.addToScoreList(player);
        }
        this.startTimer(player, (int) (this.InstanceTime - System.currentTimeMillis()) / 1000);
    }

    @Override
    public boolean onReviveEvent(Player player) {
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        this.HealPlayer(player, false, true);
        player.getGameStats().updateStatsAndSpeedVisually();

        EventStartPosition esp = this.spawnPoints.getRandomPosition();
        if (esp != null) {
            TeleportService2.teleportTo(player, this.mapId, this.instanceId, esp.getX(), esp.getY(), esp.getZ(), esp.getH(), TeleportAnimation.JUMP_AIMATION);
        }
        return true;
    }

    @Override
    public boolean onDie(Player player, Creature lastAttacker) {
        super.onDie(player, lastAttacker);
        this.deathPlayer(player, lastAttacker);
        return true;
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

    private void StartRoundTask() {
        if (endRoundTask == null) {
            this.waitingTask = null;
            for (Player p : this.players) {
                RemoveProtection(p);
            }

            if (this.ifOnePlayer()) {
                return;
            }

            endRoundTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    EndBattle();
                }
            }, battle_time * 1000);
            this.startTimer(this.battle_time);
            this.sendSpecMessage(EventManager, "Here we go!");
        }
    }

    private void EndBattle() {
        for (Player p : this.players) {
            this.AddProtection(p, 60 + 1000);
        }
        this.DoReward();
    }

    protected void deathPlayer(Player victim, Creature lastAttacker) {
        if (lastAttacker.getActingCreature() instanceof Player && victim != lastAttacker) {
            Player winner = (Player) lastAttacker.getActingCreature();
            EventScore winnerScore = this.getScore(winner.getObjectId());
            EventScore loserScore = this.getScore(victim.getObjectId());
            winnerScore.incKills();
            loserScore.incDeath();
            int streakBonus = this.getPointsForKillStreak(winnerScore.getStreak());
            int endStreakBonus = this.getPointsForKillStreakEnd(loserScore.getEndStreak());
            int winnerPoints = this.Points_per_kill + streakBonus + endStreakBonus;
            int loserPoints = this.Points_per_death + endStreakBonus / 2;
            if (!hasFb) {
                winnerPoints += this.FB_Bonus;
                hasFb = true;
                this.sendSpecMessage(EventManager, String.format("%s shed(s) first blood, the victim %s", winner.getName(), victim.getName()));
            }
            winnerScore.incPoints(winnerPoints);
            loserScore.incPoints(-loserPoints);
            PacketSendUtility.sendMessage(winner, String.format("You get %s points.", winnerPoints));
            PacketSendUtility.sendMessage(victim, String.format("You lose %s points.", loserPoints));

            //PacketSendUtility.sendPacket(winner, new SM_SYSTEM_MESSAGE(1360001, victim.getName()));
        }
    }

    private int getPointsForKillStreak(int streakLenght) {
        if (streakLenght == 1 || streakLenght == 2) {
            return 0;
        }
        return streakLenght * 50;
    }

    private int getPointsForKillStreakEnd(int streakLenght) {
        if (streakLenght == 1 || streakLenght == 2) {
            return 0;
        }
        return streakLenght * 40;
    }

    @Override
    protected void DoReward() {
        if (!eventIsComplete) {
            eventIsComplete = true;
            this.stopTimer();
            int rank = 1;
            Collections.sort(score);
            for (EventScore es : this.score) {
                Player player = this.getPlayerFromEventList(es.PlayerObjectId);
                this.sendSpecMessage(EventManager, String.format("you took %s place", rank), player);
                EventRewardHelper.GiveRewardFor(player, eType, es, rank);
                this.moveToEntry(player);
                rank++;
            }

            Balalaka.sayInWorldOrangeTextCenterWithDelay(EventManager, String.format("Event: %s completed, took first place(s): %s .",
                    eType.getEventTemplate().getEventName(), this.getPlayerFromEventList(this.score.get(0).PlayerObjectId).getName()), 3);

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
        }
    }
}
