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
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import pirate.announcer.Balalaka;
import pirate.events.EventRewardHelper;
import pirate.events.EventScore;
import pirate.events.xml.EventStartCondition;

/**
 * Team Event - 2x2,3x3,4x4,6x6
 *
 * @author flashman
 */
@EventID(eventId = 2)
public class CommandEventHandler extends BaseEventHandler {

    private ScheduledFuture endTask;
    private ScheduledFuture waitingTask;
    private int winnerGroupId = 0;
    private boolean battleIsStart = false;
    private EventStartCondition playerSizeRestrition;
    private final List<PlayerGroup> groups = new ArrayList<PlayerGroup>();

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        this.round = 1;
        this.waitingTime = 20;
        this.battle_time = 360;
        this.playerSizeRestrition = this.eType.getEventTemplate().getStartCondition();
        super.onInstanceCreate(instance);
        this.waitingTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                startBattle();
            }
        }, this.waitingTime * 1000);
    }

    @Override
    public void onInstanceDestroy() {
        if (endTask != null) {
            endTask.cancel(true);
            endTask = null;
        }
        if (this.waitingTask != null) {
            this.waitingTask.cancel(true);
            this.waitingTask = null;
        }
        this.players = null;
        this.score = null;
    }

    @Override
    public void onEnterInstance(Player player) {
        super.onEnterInstance(player);
        if (instance.isRegistered(player.getObjectId()) && player.isInGroup2() && !this.containsPlayer(player.getObjectId())) {
            players.add(player);

        } else {
            return;
        }
        if (!this.containsGroup(player.getPlayerGroup2())) {
            this.groups.add(player.getPlayerGroup2());
        }
        if (!this.containsInScoreList(player.getObjectId())) {
            this.addToScoreList(player);
        }
        this.startTimer(player, (int) (this.InstanceTime - System.currentTimeMillis()) / 1000);
    }

    @Override
    public boolean isEnemy(Player attacker, Player target) {
        if (attacker != target) {
            PlayerGroup g1 = attacker.getPlayerGroup2();
            PlayerGroup g2 = target.getPlayerGroup2();
            if (g1 != null && g2 != null && g1.getObjectId() != g2.getObjectId()) {
                return true;
            }
        }
        return super.isEnemy(attacker, target);
    }

    @Override
    public void onPlayerLogOut(Player player) {
        super.onPlayerLogOut(player);
        if (!eventIsComplete) {
            this.removeFromScoreList(player.getObjectId());
            this.removeGroupByPlayer(player);
            this.ifOneGroup();
        }
    }

    @Override
    public void onLeaveInstance(Player player) {
        super.onLeaveInstance(player);
        if (!eventIsComplete) {
            this.removeFromScoreList(player.getObjectId());
            this.removeGroupByPlayer(player);
            this.ifOneGroup();
        }
    }

    @Override
    public boolean onDie(Player player, Creature lastAttacker) {
        super.onDie(player, lastAttacker);
        this.deathPlayer(player, lastAttacker);
        return true;
    }

    protected void deathPlayer(Player vic, Creature lastAttacker) {
        if (lastAttacker.getActingCreature() instanceof Player && vic != lastAttacker) {
            Player win = (Player) lastAttacker;

            if (this.containsPlayer(win.getObjectId()) && this.containsPlayer(vic.getObjectId())
                    && vic.isInGroup2() && win.isInGroup2()) {
                EventScore webs = this.getScore(win.getObjectId());
                EventScore vebs = this.getScore(vic.getObjectId());
                webs.incKills();
                vebs.incDeath();

                PlayerGroup pg = this.getPlayerGroup(vic);
                boolean alldead = true;
                for (Player p : pg.getMembers()) {
                    if (isInHere(p) && !p.getLifeStats().isAlreadyDead()) {
                        alldead = false;
                        break;
                    }
                }

                if (alldead) {
                    PlayerGroup winnerGroup = this.groups.get(this.groups.indexOf(pg) == 0 ? 1 : 0);
                    this.endBattle(winnerGroup);
                }
            }
        }
    }

    private void startBattle() {
        if (!this.battleIsStart) {
            this.battleIsStart = true;
            this.waitingTask = null;
            this.stopTimer();
            this.endTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    endBattle(null);
                }
            }, this.battle_time * 1000);
            this.startTimer(this.battle_time);
            this.sendSpecMessage(EventManager, "Here we go!");
        }
    }

    private synchronized void endBattle(PlayerGroup winner) {
        if (!this.eventIsComplete) {
            this.eventIsComplete = true;
            this.stopTimer();
            if (this.endTask != null && !this.endTask.isDone()) {
                this.endTask.cancel(true);
            }
            this.endTask = null;
            if (winner == null) {
                if (this.groups.isEmpty()) {
                    log.info("[EVENT_HANDLER] CommandInstance groups size is empty.");
                    for (Player p : this.players) {
                        this.moveToEntry(p);
                    }
                    return;
                }

                PlayerGroup pg1 = this.groups.get(0);
                PlayerGroup pg2 = null;

                if (this.groups.size() == 2) {
                    pg2 = this.groups.get(1);
                }

                int count1 = this.getNotDeadPlayers(pg1);
                int count2 = this.getNotDeadPlayers(pg2);
                if (count1 > count2) {
                    winner = pg1;
                } else if (count1 < count2) {
                    winner = pg2;
                }
                if (winner != null) {
                    this.winnerGroupId = winner.getObjectId();
                } else {
                    Balalaka.sayInWorldOrangeTextCenter(EventManager, String.format("Event: %s completed, draw.", this.eType.getEventTemplate().getEventName()));
                    for (Player p : this.players) {
                        this.moveToEntry(p);
                    }
                    return;
                }
            } else {
                this.winnerGroupId = winner.getObjectId();
            }
            Balalaka.sayInWorldOrangeTextCenter(EventManager, String.format("Event: %s completed, won the group with group leader: %s.",
                    this.eType.getEventTemplate().getEventName(), winner.getLeaderObject().getName()));
            this.DoReward();
        }
    }

    @Override
    protected void DoReward() {
        int rank;
        for (Player player : this.players) {
            rank = player.getPlayerGroup2().getObjectId() == this.winnerGroupId ? 1 : 2;
            EventRewardHelper.GiveRewardFor(player, eType, this.getScore(player.getObjectId()), rank);
            this.moveToEntry(player);
        }
    }

    private int getNotDeadPlayers(PlayerGroup pg) {
        int count = 0;
        if (pg != null && pg.size() == this.playerSizeRestrition.getPlayersForEachGroup()) {
            for (Player p : pg.getMembers()) {
                if (!p.getLifeStats().isAlreadyDead() && p.getWorldId() == this.mapId) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean ifOneGroup() {
        if (groups.size() == 1) {
            this.DoReward();
            return true;
        }
        return false;
    }

    private boolean isInHere(Player p) {
        return p.getWorldId() == mapId && p.getInstanceId() == this.instanceId;
    }

    private void removeGroupByPlayer(Player member) {
        for (PlayerGroup pg : this.groups) {
            if (pg.hasMember(member.getObjectId())) {
                this.groups.remove(pg);
                break;
            }
        }
    }

    private PlayerGroup getPlayerGroup(int id) {
        for (PlayerGroup pg : this.groups) {
            if (pg.getObjectId() == id) {
                return pg;
            }
        }
        return null;
    }

    private PlayerGroup getPlayerGroup(Player member) {
        for (PlayerGroup pg : this.groups) {
            if (pg.hasMember(member.getObjectId())) {
                return pg;
            }
        }
        return null;
    }

    private boolean containsGroup(PlayerGroup group) {
        for (PlayerGroup pg : this.groups) {
            if (pg.getObjectId() == group.getObjectId()) {
                return true;
            }
        }
        return false;
    }
}
