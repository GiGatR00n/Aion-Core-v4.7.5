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

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.EventID;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.stats.container.PlayerLifeStats;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.concurrent.ScheduledFuture;
import pirate.announcer.Balalaka;
import pirate.events.EventRewardHelper;
import pirate.events.EventScore;
import pirate.events.enums.EventType;
import pirate.events.xml.EventStartPosition;

/**
 * Ивент - Один на Один
 *
 * @author flashman
 */
@EventID(eventId = 1)
public class PvPEventHandler extends BaseEventHandler {

    private ScheduledFuture endRoundTask;
    private ScheduledFuture nextRoundTask;
    private boolean isDraw = false;
    // delay before the start of a new round, after the previous one in seconds
    private int delayBeforStartNextRound = 2;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        round = 1;
        winNeeded = 3;
        waitingTime = 10;
        battle_time = 360;
        super.onInstanceCreate(instance);
        // if you do not have to spawn off when creating Insta
        // for (Npc npc : this.instance.getNpcs()) {
        // npc.getController().onDelete();
        // }
    }

    @Override
    public void onInstanceDestroy() {
        if (this.endRoundTask != null) {
            this.endRoundTask.cancel(true);
            this.endRoundTask = null;
        }
        if (this.nextRoundTask != null) {
            this.nextRoundTask.cancel(true);
            this.nextRoundTask = null;
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
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                StartRoundTask();
            }
        }, waitingTime * 1000);
        AddProtection(player, waitingTime * 1000);
        this.HealPlayer(player);
        this.sendSpecMessage(EventManager, "Before the start of Round 1: " + this.waitingTime + " sec.");
        if (!this.containsInScoreList(player.getObjectId())) {
            this.addToScoreList(player);
        }
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
        //super.onDie(player, lastAttacker);
        this.deathPlayer(player, lastAttacker);
        return true;
    }

    @Override
    public boolean onReviveEvent(Player player) {
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        PlayerReviveService.revive(player, 100, 100, false, 0);
        player.getGameStats().updateStatsAndSpeedVisually();
        return true;
    }

    private synchronized void StartRoundTask() {
        if (endRoundTask == null) {
            for (Player p : this.players) {
                RemoveProtection(p);
                this.HealPlayer(p, false, true);
            }

            if (this.ifOnePlayer()) {
                return;
            }
            endRoundTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    NextRound(true);
                }
            }, battle_time * 1000);
            sendSpecMessage(EventManager, "Round: " + round + " - went");
            this.startTimer(this.battle_time);
        }
    }

    private void NextRound(boolean timeIsUp) {
        if (players == null || players.isEmpty()) {
            return;
        }
        if (this.endRoundTask != null) {
            endRoundTask.cancel(true);
            endRoundTask = null;
        }
        round++;

        if (this.ifOnePlayer()) {
            return;
        }

        if (timeIsUp) {
            Player winner = this.timeIsUpEvent();
            if (winner != null) {
                this.getScore(winner.getObjectId()).incWin();
                if (hasWinner()) {
                    sendSpecMessage(EventManager, "Event completed");
                    DoReward();
                    return;
                } else {
                    this.moveToStartPosition();
                    this.sendSpecMessage(EventManager, "The round is over, by the decision of the judges for this round: " + winner.getName());
                }
            } else {
                this.DoReward();
                return;
            }
        }

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                StartRoundTask();
            }
        }, this.delayBeforStartNextRound * 1000);
    }

    protected void deathPlayer(Player victim, Creature lastAttacker) {
        if (lastAttacker.getActingCreature() instanceof Player && victim != lastAttacker) {
            Player winner = (Player) lastAttacker.getActingCreature();
            EventScore winnerScore = this.getScore(winner.getObjectId());
            EventScore loserScore = this.getScore(victim.getObjectId());
            winnerScore.incKills();
            winnerScore.incWin();
            loserScore.incDeath();
            loserScore.incLose();

            PacketSendUtility.sendPacket(winner, new SM_SYSTEM_MESSAGE(1360001, victim.getName()));

            if (this.endRoundTask != null) {
                this.endRoundTask.cancel(true);
                this.endRoundTask = null;
            }

            this.HealPlayer(victim, false, true);
            this.HealPlayer(winner, false, true);
            winner.setTarget(null);
            victim.setTarget(null);

            moveToStartPosition();

            AddProtection(victim, waitingTime * 1000, 1000);
            AddProtection(winner, waitingTime * 1000, 1000);

            this.stopTimer();

            this.sendSpecMessage(EventManager, "Round: " + round + " completed, the Winner: " + winner.getName());

            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (hasWinner()) {
                        sendSpecMessage(EventManager, "Event completed");
                        DoReward();
                        return;
                    }

                    if (nextRoundTask != null) {
                        nextRoundTask.cancel(true);
                        nextRoundTask = null;
                    }
                    for (Player p : players) {
                        HealPlayer(p, false, true);
                    }
                    nextRoundTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            NextRound(false);
                        }
                    }, 4000);
                }
            }, 5000);
        }
    }

    @Override
    protected void DoReward() {
        if (!eventIsComplete) {
            eventIsComplete = true;
            if (!isDraw) {
                int rank;
                Object[] names = {"", ""};
                for (final Player player : this.players) {
                    EventScore es = this.getScore(player.getObjectId());
                    if (es.isWinner) {
                        rank = 1;
                    } else {
                        rank = 2;
                    }
                    this.sendSpecMessage(EventManager, String.format("You took %s Place", rank), player);
                    EventRewardHelper.GiveRewardFor(player, EventType.E_1x1, es, rank);
                    moveToEntry(player);
                    switch (rank) {
                        case 1:
                            names[0] = player.getName();
                            break;
                        case 2:
                            names[1] = player.getName();
                            break;
                    }
                    this.stopTimer(player);
                }
                Balalaka.sayInWorldOrangeTextCenter(EventManager, String.format("Event: %s completed, won(s): %s, lost(s): %s",
                        eType.getEventTemplate().getEventName(), names));
            } else {
                for (Player player : this.players) {
                    moveToEntry(player);
                    this.stopTimer(player);
                    // when the draw is given awards for second place all participants
                    EventRewardHelper.GiveRewardFor(player, EventType.E_1x1, this.getScore(player.getObjectId()), 2);
                }
                Balalaka.sayInWorldOrangeTextCenter(EventManager, String.format("Event: %s completed, draw", eType.getEventTemplate().getEventName()));
            }

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

    private void moveToStartPosition() {
        int i = 0;
        for (Player p : this.players) {
            EventStartPosition point = EventType.E_1x1.getEventTemplate().getStartPositionInfo().getPositions().get(i);
            TeleportService2.teleportTo(p, this.mapId, this.instanceId, point.getX(), point.getY(), point.getZ(), (byte) 0, TeleportAnimation.BEAM_ANIMATION);
            i += 1;
        }
    }

    private boolean hasWinner() {
        for (EventScore es : this.score) {
            if (es.getWins() >= winNeeded) {
                es.isWinner = true;
                return true;
            }
        }
        return false;
    }

    private Player timeIsUpEvent() {
        if (players.size() == 2) {
            Player winner;
            PlayerLifeStats pls1 = players.get(0).getLifeStats();
            PlayerLifeStats pls2 = players.get(1).getLifeStats();
            if (pls1.getCurrentHp() > pls2.getCurrentHp()) {
                winner = players.get(0);
            } else if (pls1.getCurrentHp() < pls2.getCurrentHp()) {
                winner = players.get(1);
            } else {
                if (pls1.getMaxHp() > pls2.getMaxHp()) {
                    winner = players.get(0);
                } else if (pls1.getMaxHp() < pls2.getMaxHp()) {
                    winner = players.get(1);
                } else {
                    winner = players.get(Rnd.get(0, players.size() - 1));
                }
            }
            return winner;
        }
        return null;
    }
}
