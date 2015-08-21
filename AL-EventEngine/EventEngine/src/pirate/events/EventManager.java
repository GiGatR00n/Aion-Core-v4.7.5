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
package pirate.events;

import pirate.events.enums.EventType;
import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.sql.Timestamp;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pirate.events.enums.EventPlayerLevel;
import pirate.events.enums.EventRergisterState;
import pirate.events.holders.IEventHolder;
import pirate.events.holders.PlayerGroupEventHolder;
import pirate.events.holders.SimpleSinglePlayerEventHolder;
import pirate.events.holders.SinglePlayerHolder;
import pirate.events.xml.EventStartPosition;
import pirate.events.xml.EventStartPositionList;
import pirate.utils.TimeUtil;

/**
 *
 * @author f14shm4n
 */
public class EventManager {

    private final Logger log = LoggerFactory.getLogger(EventManager.class);
    private Map<EventType, Map<Integer, PlayerInfo>> playedPlayers;
    private List<IEventHolder> holders;
    private Map<EventType, Boolean> events;
    private Map<EventType, ScheduledFuture> endRegTasks;
    private boolean launcheAnnounce = false;
    private int holderCounter = 0;

    private EventManager() {
        this.events = new EnumMap<EventType, Boolean>(EventType.class);
        this.endRegTasks = new EnumMap<EventType, ScheduledFuture>(EventType.class);
        this.playedPlayers = new EnumMap<EventType, Map<Integer, PlayerInfo>>(EventType.class);
        this.holders = new FastList<IEventHolder>();
    }

    public void Init() {
        for (final EventType et : EventType.values()) {
            if (!et.IsDone()) {
                continue;
            }
            this.events.put(et, false);
            this.playedPlayers.put(et, new FastMap<Integer, PlayerInfo>().shared());
            CronService.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    RunEvent(et);
                }
            }, et.getEventTemplate().getSchedule());
        }
    }

    public boolean RunEvent(final EventType et) {
        if (this.eventIsActive(et)) {
            return false;
        }
        changeEventActiveState(et, true);
        switch (et) {
            // Regular run type
            default:
                this.endRegTasks.put(et, ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        changeEventActiveState(et, false);
                        endRegTasks.remove(et);
                    }
                }, et.getEventTemplate().getRegistrationTime() * 1000));
                break;
        }
        return true;
    }

    public synchronized EventRegisterInfo register(Player player, EventType etype) {
        switch (etype) {
            case E_1x1:
            case E_LHE:
            case E_FFA:
                return this.registerSinglePlayer(player, etype);
            case E_2x2:
            case E_3x3:
            case E_4x4:
            case E_6x6:
                return this.registerPlayerGroup(player, etype);
            default:
                return new EventRegisterInfo(EventRergisterState.ERROR, "[ERROR_0001] Unknown type of Event.");
        }
    }

    private EventRegisterInfo registerSinglePlayer(Player player, EventType etype) {
        EventRegisterInfo eri = this.standartCheck(etype);
        if (eri != null) {
            return eri;
        }

        if (this.containsInHolders(player)) {
            return new EventRegisterInfo(EventRergisterState.ALREADY_REGISTRED, "You are already registered event. If you want to create another event, you must first exit the current.");
        }

        eri = this.checkInPlayed(player, etype);
        if (eri != null) {
            return eri;
        }

        byte lvl = player.getLevel();
        EventPlayerLevel epl = EventPlayerLevel.getEventLevelByPlayerLevel(lvl);

        String registerMsg;
        IEventHolder holder = this.getFirstFreeHolderForSinglePlayer(player, etype, epl);
        if (holder == null) {
            holderCounter += 1;
            holder = this.createEventHolder(holderCounter, etype, epl);
            holder.addPlayer(player);
            this.holders.add(holder);
            registerMsg = String.format("You have successfully registered with the registrar of the new group.\nEvent: %s\nLevels: %s - %s", etype.name(), epl.getMin(), epl.getMax());
            log.info("[EventManager] Create new {} with index: {}", holder.getClass().getName(), holder.Index());
            log.info("[EventManager] Add {} to {} with index: {}", new Object[]{player.getName(), holder.getClass().getSimpleName(), holder.Index()});
        } else {
            holder.addPlayer(player);
            registerMsg = String.format("Are you a member in the group waiting registrar.\nEvent: %s\nLevels: %s - %s", etype.name(), epl.getMin(), epl.getMax());
            log.info("[EventManager] Add {} to {} with index: {}", new Object[]{player.getName(), holder.getClass().getSimpleName(), holder.Index()});
        }
        this.tryStartEvent(holder);

        return new EventRegisterInfo(EventRergisterState.REGISTRED, registerMsg);
    }

    private EventRegisterInfo registerPlayerGroup(Player leader, EventType etype) {
        if (!leader.isInGroup2()) {
            return new EventRegisterInfo(EventRergisterState.ERROR, "You must be in a group and be its leader.");
        }
        PlayerGroup group = leader.getPlayerGroup2();
        if (!group.isLeader(leader)) {
            return new EventRegisterInfo(EventRergisterState.PLAYER_IS_NOT_GROUP_LEADER, "You are not the leader of the group, you can not carry out the registration of the group.");
        }
        EventRegisterInfo eri = this.standartCheck(etype);
        if (eri != null) {
            return eri;
        }
        for (Player member : group.getMembers()) {
            if (this.containsInHolders(member)) {
                return new EventRegisterInfo(EventRergisterState.ALREADY_REGISTRED,
                        "One or more groups of players already registered in this event or other events.");
            }
        }
        eri = this.checkInPlayedGroup(group, etype);
        if (eri != null) {
            return eri;
        }

        byte lvl = this.getHigherLvlInGroup(group);
        EventPlayerLevel epl = EventPlayerLevel.getEventLevelByPlayerLevel(lvl);
        String registerMsg;
        IEventHolder holder = this.getFirstFreeHolderForPlayerGroup(group, etype, epl);

        if (holder == null) {
            holderCounter += 1;
            holder = this.createEventHolder(holderCounter, etype, epl);
            holder.addPlayerGroup(group);
            this.holders.add(holder);
            registerMsg = String.format("Your group is registered in the list of the new registrar.\nEvent: %s\nlevels: %s - %s", etype.name(), epl.getMin(), epl.getMax());
            log.info("[EventManager] Create new {} with index: {}", holder.getClass().getName(), holder.Index());
            log.info("[EventManager] Add playerGroup Leader {} to {} with index: {}", new Object[]{leader.getName(), holder.getClass().getSimpleName(), holder.Index()});
        } else {
            holder.addPlayerGroup(group);
            registerMsg = String.format("Your group is registered on a waiting list registrar.\nEvent: %s\nlevels: %s - %s", etype.name(), epl.getMin(), epl.getMax());
            log.info("[EventManager] Add playerGroup Leader {} to {} with index: {}", new Object[]{leader.getName(), holder.getClass().getSimpleName(), holder.Index()});
        }
        this.tryStartEvent(holder);

        return new EventRegisterInfo(EventRergisterState.REGISTRED, registerMsg);
    }

    public synchronized EventRegisterInfo unregister(Player player, EventType etype) {
        switch (etype) {
            case E_1x1:
            case E_LHE:
            case E_FFA:
                return this.unregisterSinglePlayer(player, etype);
            case E_2x2:
            case E_3x3:
            case E_4x4:
            case E_6x6:
                return this.unregisterPlayerGroup(player);
            default:
                return new EventRegisterInfo(EventRergisterState.ERROR, "[ERROR_0002] Unknown type of Event.");
        }
    }

    private EventRegisterInfo unregisterSinglePlayer(Player player, EventType et) {
        IEventHolder ph = this.getHolderByPlayer(player);
        if (ph != null) {
            if (ph.deletePlayer(player)) {
                return new EventRegisterInfo(EventRergisterState.UNREGISTRED, String.format("You came out of the group on the event registrar: %s.", et.getEventTemplate().getEventName()));
            }
        }
        return new EventRegisterInfo(EventRergisterState.ERROR, String.format("You are not in the registration list for event: %s.", et.getEventTemplate().getEventName()));
    }

    public EventRegisterInfo unregisterPlayerGroup(Player leader) {
        if (!leader.isInGroup2()) {
            return new EventRegisterInfo(EventRergisterState.ERROR, "You must be in a group and be its leader.");
        }
        PlayerGroup group = leader.getPlayerGroup2();
        if (!group.isLeader(leader)) {
            return new EventRegisterInfo(EventRergisterState.PLAYER_IS_NOT_GROUP_LEADER, "You are not the leader of the group, you can not produce the output of the recorder.");
        }
        IEventHolder holder = this.getHolderByPlayerGroup(group);
        if (holder != null) {
            if (holder.deletePlayerGroup(group)) {
                return new EventRegisterInfo(EventRergisterState.UNREGISTRED, String.format("Your group left the list of the registrar on Event: %s.",
                        holder.getEventType().getEventTemplate().getEventName()));
            }
        }
        return new EventRegisterInfo(EventRergisterState.ERROR, String.format("Your group is not listed registration."));
    }

    public synchronized boolean unregisterPlayer(Player player) {
        IEventHolder holder = this.getHolderByPlayer(player);
        if (holder != null) {
            holder.deletePlayer(player);
            log.info("[EventManager] Player: {} has been unregistered from {} with intex {}", new Object[]{player.getName(), holder.getClass().getSimpleName(), holder.Index()});
            if (holder.isEmpty()) {
                this.deleteHolder(holder);
                log.info("[EventManager] {} with index {} has been deleted from list", holder.getClass().getSimpleName(), holder.Index());
            }
            return true;
        }
        return false;
    }

    /**
     * Sets the state of activity of the event period.
     *
     * @param etype type events
     * @param state state - true (active) - false (inactive)
     * @return operation was successful or not
     */
    public boolean changeEventActiveState(EventType etype, boolean state) {
        if (this.events.containsKey(etype)) {
            this.events.put(etype, state);
            if (state) {
                this.createNewEventSession(etype);
                log.info("Event: {} is started", etype.name());
                if (!this.launcheAnnounce) {
                    this.launcheAnnounce = true;
                    this.announceActiveEvents();
                }
            } else {
                this.removeHolderByEventType(etype);
            }
            return true;
        }
        return false;
    }

    /**
     * Updates the lists of those who have participated in evente of some type, thereby
     * sosdavaya eventa new session.
     *
     * @param etype type eventas
     */
    public void createNewEventSession(EventType etype) {
        if (this.playedPlayers.containsKey(etype)) {
            this.playedPlayers.get(etype).clear();
        }
    }

    /**
     * Removes the player from the list to play.
     *
     * @param player
     * @param etype
     * @return true - deleted ; false - no
     */
    public boolean removePlayerFromPlayedList(Player player, EventType etype) {
        if (this.playedPlayers.containsKey(etype)) {
            if (this.playedPlayers.get(etype).containsKey(player.getObjectId())) {
                return this.playedPlayers.get(etype).remove(player.getObjectId()) != null;
            }
        }
        return false;
    }

    /**
     * Checks if the player is already in the list of attending event.
     *
     * @param etype
     * @param player
     * @return
     */
    public boolean containsInPlayed(EventType etype, Player player) {
        return this.playedPlayers.containsKey(etype) && this.playedPlayers.get(etype).containsKey(player.getObjectId());
    }

    /**
     * Checks whether the event is active or not.
     *
     * @param etype
     * @return
     */
    public boolean eventIsActive(EventType etype) {
        return this.events.containsKey(etype) && this.events.get(etype);
    }

    /**
     * Checks whether there is active opening event.
     *
     * @return
     */
    public boolean hasActiveEvents() {
        for (Boolean active : this.events.values()) {
            if (active) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes an attempt to start events.
     *
     * @param holder Holder that participates in the launch of events
     */
    private void tryStartEvent(IEventHolder holder) {
        if (holder.isReadyToGo()) {
            WorldMapInstance inst = InstanceService.getNextAvailableEventInstance(holder);
            EventStartPositionList poss = holder.getEventType().getEventTemplate().getStartPositionInfo();
            switch (holder.getEventType()) {
                case E_1x1:
                case E_LHE:
                case E_FFA:
                    this.startMovePlayers(poss, ((SinglePlayerHolder) holder).getAllPlayers(), holder, inst, 0);
                    break;
                case E_2x2:
                case E_3x3:
                case E_4x4:
                case E_6x6:
                    List<PlayerGroup> groups = ((PlayerGroupEventHolder) holder).getPlayerGroups();
                    for (int i = 0; i < groups.size(); i++) {
                        this.startMovePlayerGroup(groups.get(i), poss, holder.getEventType(), inst, i + 1);
                    }
                    break;
                default:
                    log.error("[EventManager] Unknown event type. Type: {}", holder);
                    break;
            }

            this.deleteHolder(holder);
            log.info("[EventManager] {} with index {} has been deleted from list", holder.getClass().getSimpleName(), holder.Index());
        }
    }

    private void startMovePlayers(EventStartPositionList positions, Collection<Player> players, IEventHolder ph, WorldMapInstance inst, int pos) {
        for (Player p : players) {
            if (ph.contains(p) && this.addPlayerToPlayedMap(p, ph.getEventType())) {
                InstanceService.registerPlayerWithInstance(inst, p);
                this.teleportSinglePlayer(p, inst.getMapId(), inst.getInstanceId(), positions.getPositions().get(pos), EventTeleportType.SINGLE_PLAYER);
                pos++;
                if (pos == positions.getPositions().size()) {
                    pos = 0;
                }
            }
        }
    }

    private void startMovePlayerGroup(PlayerGroup group, EventStartPositionList poss, EventType et, WorldMapInstance inst, int groupPos) {
        List<EventStartPosition> posList = poss.getPositionForGroup(groupPos);
        int pos = 0;
        for (Player m : group.getMembers()) {
            if (this.addPlayerToPlayedMap(m, et)) {
                InstanceService.registerPlayerWithInstance(inst, m);
                this.teleportSinglePlayer(m, inst.getMapId(), inst.getInstanceId(), posList.get(pos), EventTeleportType.PLAYER_IN_GROUP);
                pos++;
                if (pos == posList.size()) {
                    pos = 0;
                }
            }
        }
    }

    private void teleportSinglePlayer(Player p, int worldId, int instId, EventStartPosition spos, EventTeleportType tpType) {
        if (tpType == EventTeleportType.SINGLE_PLAYER) {
            if (p.getPlayerAlliance2() != null) {
                PlayerAllianceService.removePlayer(p);
            }
            if (p.getPlayerGroup2() != null) {
                PlayerGroupService.removePlayer(p);
            }
        }
        if (p.getKisk() != null) {
            p.getKisk().removePlayer(p);
            p.setKisk(null);
        }
        if (p.getEffectController().getAbnormalEffects().size() > 0) {
            p.getEffectController().removeAllEffects();
        }
        TeleportService2.teleportTo(p, worldId, instId, spos.getX(), spos.getY(), spos.getZ(), spos.getH(), TeleportAnimation.BEAM_ANIMATION);
    }

    /**
     * Adds a player to the list of those who have participated in the opening event.
     *
     * @param p
     * @param etype
     */
    private boolean addPlayerToPlayedMap(Player p, EventType etype) {
        if (this.playedPlayers.containsKey(etype)) {
            if (this.playedPlayers.get(etype).containsKey(p.getObjectId())) {
                log.warn("Player has exist in played map, something wrong. Player: {}", p.getObjectId());
                return false;
            }
            PlayerInfo pi = new PlayerInfo(p.getObjectId(), etype.getEventTemplate().getReentryCooldown());
            this.playedPlayers.get(etype).put(p.getObjectId(), pi);
            log.info("[EventManager] Player: {} add to played list. Time in: {} Next time in: {}",
                    new Object[]{p.getName(), new Timestamp(pi.lastEntryTime).toString(), new Timestamp(pi.nextEntryTime).toString()});
        } else {
            Map<Integer, PlayerInfo> plrs = new FastMap<Integer, PlayerInfo>().shared();
            PlayerInfo pi = new PlayerInfo(p.getObjectId(), etype.getEventTemplate().getReentryCooldown());
            plrs.put(p.getObjectId(), pi);
            log.info("[EventManager] Player: {} add to played list. Time in: {} Next time in: {}",
                    new Object[]{p.getName(), new Timestamp(pi.lastEntryTime).toString(), new Timestamp(pi.nextEntryTime).toString()});
            this.playedPlayers.put(etype, plrs);
        }
        return true;
    }

    public boolean containsInHolders(Player player) {
        for (IEventHolder holder : this.holders) {
            if (holder.contains(player)) {
                return true;
            }
        }
        return false;
    }

    private void removeHolderByEventType(EventType etype) {
        for (IEventHolder holder : this.holders) {
            if (holder.getEventType() == etype) {
                this.holders.remove(holder);
                this.removeHolderByEventType(etype);
                break;
            }
        }
    }

    private void deleteHolder(IEventHolder h) {
        int index = h.Index();
        for (int i = 0; i < this.holders.size(); i++) {
            IEventHolder holder = this.holders.get(i);
            if (holder.Index() == index) {
                this.holders.remove(i);
                return;
            }
        }
    }

    private IEventHolder getFirstFreeHolderForSinglePlayer(Player player, EventType etype, EventPlayerLevel epl) {
        for (IEventHolder holder : this.holders) {
            if (holder.getEventType() == etype && holder.getHolderLevel() == epl && holder.canAddPlayer(player)) {
                return holder;
            }
        }
        return null;
    }

    private IEventHolder getFirstFreeHolderForPlayerGroup(PlayerGroup group, EventType etype, EventPlayerLevel epl) {
        for (IEventHolder holder : this.holders) {
            if (holder.getEventType() == etype && holder.getHolderLevel() == epl && holder.canAddGroup(group)) {
                return holder;
            }
        }
        return null;
    }

    private IEventHolder getHolderByPlayer(Player player, EventType etype, EventPlayerLevel epl) {
        for (IEventHolder holder : this.holders) {
            if (holder.getEventType() == etype && holder.getHolderLevel() == epl && holder.contains(player)) {
                return holder;
            }
        }
        return null;
    }

    private IEventHolder getHolderByPlayer(Player player) {
        for (IEventHolder holder : this.holders) {
            if (holder.contains(player)) {
                return holder;
            }
        }
        return null;
    }

    private IEventHolder getHolderByPlayerGroup(PlayerGroup group, EventType etype, EventPlayerLevel epl) {
        for (IEventHolder holder : this.holders) {
            if (holder.getEventType() == etype && holder.getHolderLevel() == epl && holder.contains(group)) {
                return holder;
            }
        }
        return null;
    }

    private IEventHolder getHolderByPlayerGroup(PlayerGroup group) {
        for (IEventHolder holder : this.holders) {
            if (holder.contains(group)) {
                return holder;
            }
        }
        return null;
    }

    private IEventHolder createEventHolder(int index, EventType etype, EventPlayerLevel epl) {
        switch (etype) {
            case E_1x1:
            case E_LHE:
            case E_FFA:
                return new SimpleSinglePlayerEventHolder(index, etype, epl);
            case E_2x2:
            case E_3x3:
            case E_4x4:
            case E_6x6:
                return new PlayerGroupEventHolder(index, etype, epl);
            default:
                return null;
        }
    }

    private EventRegisterInfo standartCheck(EventType etype) {
        if (!this.events.containsKey(etype)) {
            return new EventRegisterInfo(EventRergisterState.CRITICAL_ERROR, String.format("Event: %s not listed Events.", etype.name()));
        }
        if (!this.eventIsActive(etype)) {
            return new EventRegisterInfo(EventRergisterState.EVENT_NOT_START, "This event has not yet started.");
        }
        return null;
    }

    private EventRegisterInfo checkInPlayed(Player player, EventType etype) {
        if (this.containsInPlayed(etype, player)) {
            if (etype.getEventTemplate().getReentryCooldown() <= 0) {
                return new EventRegisterInfo(EventRergisterState.PLAYER_HAS_VISIT_EVENT, String.format("You have already participated in the event: %s , in this session, expect next.", etype.name()));
            }
            PlayerInfo pi = this.playedPlayers.get(etype).get(player.getObjectId());
            log.info("[EventManager] Player: {} time remainig: {}", player.getName(), TimeUtil.convertToString(pi.getRemainingTime()));
            if (!pi.canEntryNow()) {
                String time = TimeUtil.convertToString(pi.getRemainingTime());
                return new EventRegisterInfo(EventRergisterState.PLAYER_HAS_VISIT_EVENT, String.format("Re-entry is possible through: %s .", time));
            }
            this.removePlayerFromPlayedList(player, etype);
        }
        return null;
    }

    private EventRegisterInfo checkInPlayedGroup(PlayerGroup group, EventType etype) {
        for (Player member : group.getMembers()) {
            if (this.checkInPlayed(member, etype) != null) {
                return new EventRegisterInfo(EventRergisterState.PLAYER_IN_GROUP_ALREADY_VISIT_EVENT, "One or more groups of players have participated in events.");
            }
        }
        return null;
    }

    private byte getHigherLvlInGroup(PlayerGroup pg) {
        byte lvl = 0;
        for (Player p : pg.getMembers()) {
            if (p.getLevel() > lvl) {
                lvl = p.getLevel();
            }
        }
        return lvl;
    }

    /**
     * Runs the job alert players of active opening event.
     *
     * @return
     */
    private void announceActiveEvents() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (hasActiveEvents()) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append("==============================\n");
                    sb.append("Registration on Events is open:\n");
                    for (Entry<EventType, Boolean> entry : events.entrySet()) {
                        if (entry.getValue().booleanValue()) {
                            sb.append("- ").append(entry.getKey().getEventTemplate().getEventName()).append("\n");
                        }
                    }
                    sb.append("==============================");
                    World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                        @Override
                        public void visit(Player object) {
                            PacketSendUtility.sendMessage(object, sb.toString());
                        }
                    });
                }
                launcheAnnounce = false;
            }
        }, 5000);
    }

    public String CMD_StartEvent(EventType etype) {
        if (eventIsActive(etype)) {
            return "Event - " + etype.name() + " is already running. Restarting is impossible.";
        }
        if (RunEvent(etype)) {
            return String.format("Event: %s launched.", etype.name());
        } else {
            return String.format("An error occurred while trying to start of Event: %s", etype.name());
        }
    }

    //------------------------------------------------------------------------//
    public static EventManager getInstance() {
        return EventManagerHolder.INSTANCE;
    }

    private static class EventManagerHolder {

        private static final EventManager INSTANCE = new EventManager();
    }

    private class PlayerInfo {

        int playerId;
        long nextEntryTime;
        long lastEntryTime;

        public PlayerInfo(int playerId, int reentryCd) {
            this.playerId = playerId;
            this.lastEntryTime = System.currentTimeMillis();
            this.nextEntryTime = this.lastEntryTime + reentryCd * 1000;
        }

        public boolean canEntryNow() {
            return this.nextEntryTime < System.currentTimeMillis();
        }

        public int getRemainingTime() {
            return (int) (this.nextEntryTime - System.currentTimeMillis()) / 1000;
        }
    }
}
