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
package com.aionemu.gameserver.eventEngine.battleground.services.battleground;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundTemplate;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundType;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.services.StaticDoorService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Eloann
 */
public abstract class BattleGround {

    protected List<Player> players = new ArrayList<Player>();
    protected int tplId;
    private long startTime;
    protected WorldMapInstance instance;
    public boolean running = false;
    protected BattleGroundTemplate template;
    private int idInstance;

    public BattleGround(int tplId, WorldMapInstance instance) {
        this.idInstance = BattleGroundManager.getId();
        startTime = System.currentTimeMillis() / 1000;
        this.tplId = tplId;
        this.instance = instance;
        template = DataManager.BATTLEGROUND_DATA.getBattleGroundTemplate(tplId);
        BattleGroundManager.currentBattleGrounds.add(this);
    }

    public void increasePoints(Player player, int value) {
        PacketSendUtility.sendMessage(player, "You have earned " + value + " BG points.");
        player.battlegroundSessionPoints += value;
        if (player.getBattleGround().getTemplate().getType() == BattleGroundType.CTF && value == player.getBattleGround().getTemplate().getRules().getFlagCap()) {
            player.battlegroundSessionFlags += 1;
        } else if (value == player.getBattleGround().getTemplate().getRules().getKillPlayer()) {
            player.battlegroundSessionKills += 1;
        }
    }

    public void decreasePoints(Player player, int value) {
        PacketSendUtility.sendMessage(player, "You have lost " + value + " BG points.");

        player.battlegroundSessionPoints -= value;
        if (player.getBattleGround().getTemplate().getType() == BattleGroundType.ASSAULT && value == player.getBattleGround().getTemplate().getRules().getDie()) {
            player.battlegroundSessionDeaths += 1;
        }

        if (player.battlegroundSessionPoints < 0) {
            player.battlegroundSessionPoints = 0;
        }

    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public long getStartTime() {
        return startTime;
    }

    public WorldMapInstance getInstance() {
        return instance;
    }

    private void goTo(final Player player, int worldId, float x, float y, float z, byte h) {
        TeleportService2.teleportTo(player, worldId, this.idInstance, x, y, z, h);

    }

    public void teleportPlayer(Player player) {
        BattleGroundTemplate template = DataManager.BATTLEGROUND_DATA.getBattleGroundTemplate(tplId);
        if (player.getCommonData().getRace() == Race.ELYOS) {
            goTo(player, template.getWorldId(), template.getInsertPoint().getXe(), template.getInsertPoint().getYe(),
                    template.getInsertPoint().getZe(), template.getInsertPoint().getHe());
        } else {
            goTo(player, template.getWorldId(), template.getInsertPoint().getXa(), template.getInsertPoint().getYa(),
                    template.getInsertPoint().getZa(), template.getInsertPoint().getHa());
        }
    }

    public void broadcastToBattleGround(final String message, final Race targetRace) {
        for (Player p : players) {
            if (targetRace == null || p.getCommonData().getRace() == targetRace) {
                PacketSendUtility.sendPacket(p, new SM_MESSAGE(0, null, message, ChatType.BRIGHT_YELLOW_CENTER));
            }
        }
    }

    public void invitePlayer(final Player player) {
        PacketSendUtility.sendPacket(player, new SM_MESSAGE(0, null, "The Battleground: " + template.getName() + " is now ready to start. You will be teleported in 30 seconds. Have fun :)", ChatType.BRIGHT_YELLOW_CENTER));
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                teleportPlayer(player);
            }
        }, 30000);
    }

    public void start() {

        for (Player p : players) {
            p.battlegroundWaiting = false;
            invitePlayer(p);
        }

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                broadcastToBattleGround(template.getWaitTime() + " seconds before starting ...", null);
                for (Player p : players) {
                    // reset stats
                    p.getLifeStats().setCurrentHpPercent(100);
                    p.getLifeStats().setCurrentMpPercent(100);
                    p.getCommonData().setDp(0);
                    p.getEffectController().removeAllEffects();

                    if (p.battlegroundObserve == 1) {
                        p.battlegroundObserve = 2;
                        p.setVisualState(CreatureVisualState.HIDE20);
                        PacketSendUtility.broadcastPacket(p, new SM_PLAYER_STATE(p), true);
                        PacketSendUtility.sendMessage(p, "You are now invisible.");
                        p.setInvul(true);
                        PacketSendUtility.sendMessage(p, "You are now immortal.");
                    }
                }
            }
        }, 31 * 1000);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                openDoors();
                broadcastToBattleGround("The battleground is now open !", null);
                running = true;
            }
        }, (template.getWaitTime() + 30) * 1000);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (running == true) {
                    broadcastToBattleGround("The battleground will end in 30 seconds !", null);
                }
            }
        }, template.getBgTime() * 1000);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                for (Player p : players) {
                    if (p.battlegroundObserve > 0) {
                        p.battlegroundObserve = 3;
                        PacketSendUtility.sendMessage(p, "The bet time is now ended.");
                    }
                }
            }
        }, ((template.getBgTime() / 2) + 30) * 1000);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (running == true) {
                    end();
                }
            }
        }, (template.getBgTime() + 30) * 1000);
    }

    public BattleGroundTemplate getTemplate() {
        return template;
    }

    public void setTemplate(BattleGroundTemplate template) {
        this.template = template;
    }

    public void end() {
        running = false;
        broadcastToBattleGround("The battle is now ended! Click on the right bottom button to show the rank board. If you are dead, just use the spell Return and you will be teleported back.", null);
        for (Player p : players) {
            if (p.battlegroundObserve > 0) {
                p.unsetVisualState(CreatureVisualState.HIDE20);
                PacketSendUtility.broadcastPacket(p, new SM_PLAYER_STATE(p), true);
                PacketSendUtility.sendMessage(p, "You are now visible.");
                p.setInvul(false);
                PacketSendUtility.sendMessage(p, "You are now mortal.");
            }
        }
    }

    public List<Player> getRanking(Race race, boolean reward) {
        ArrayList<Player> ranking = new ArrayList<Player>();

        for (Player p : players) {
            if (p.getCommonData().getRace() != race) {
                continue;
            }
            if (p.battlegroundObserve >= 1) {
                continue;
            }
            if (ranking.size() == 0) {
                ranking.add(p);
            } else {
                for (int i = 0; i < ranking.size(); i++) {
                    if (p.battlegroundSessionPoints > ranking.get(i).battlegroundSessionPoints) {
                        ranking.add(i, p);
                        break;
                    }
                }
                if (!ranking.contains(p)) {
                    ranking.add(p);
                }
            }
        }
        return ranking;
    }

    public void commitPoints(Player player) {
        player.getCommonData().setBattleGroundPoints(player.getCommonData().getBattleGroundPoints() + player.battlegroundSessionPoints);
        player.getEffectController().removeAllEffects();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                for (final Player p : players) {
                    p.battlegroundObserve = 0;
                    p.battlegroundSessionPoints = 0;
                    p.battlegroundSessionKills = 0;
                    p.battlegroundSessionDeaths = 0;
                    p.battlegroundSessionFlags = 0;
                    p.battlegroundBetE = 0;
                    p.battlegroundBetA = 0;
                    p.setBattleGround(null);

                    if (p.getWorldId() == 110010000 || p.getWorldId() == 120010000) {
                        String message = "Do you want to go play in a battleground again ?";
                        RequestResponseHandler responseHandler = new RequestResponseHandler(p) {
                            public void acceptRequest(Creature requester, Player responder) {
                                if (p.getBattleGround() != null) {
                                    PacketSendUtility.sendMessage(p, "You are already registered in a battleground.");
                                    PacketSendUtility.sendMessage(p, "Use your spell Return to leave the battleground.");
                                    return;
                                } else if (p.battlegroundWaiting) {
                                    PacketSendUtility.sendMessage(p, "You are already registered in a battleground.");
                                    PacketSendUtility.sendMessage(p, "Use the command .bg unregister to cancel your registration.");
                                    return;
                                } else {
                                    BattleGroundManager.sendRegistrationForm(p);
                                }
                                return;
                            }

                            public void denyRequest(Creature requester, Player responder) {
                                return;
                            }
                        };
                        boolean requested = p.getResponseRequester().putRequest(902247, responseHandler);
                        if (requested) {
                            PacketSendUtility.sendPacket(p, new SM_QUESTION_WINDOW(902247, 1, 1, message));
                            return;
                        }
                    }
                }
            }
        }, 5 * 1000);

    }

    public int getTplId() {
        return tplId;
    }

    public int getWorldId() {
        return DataManager.BATTLEGROUND_DATA.getBattleGroundTemplate(tplId).getWorldId();
    }

    private void openDoors() {
        Map<Integer, StaticDoor> doors = this.getInstance().getDoors();
        for (Integer doorKey : doors.keySet()) {
            for (Player p : players) {
                StaticDoorService.getInstance().openStaticDoor(p, doorKey);
            }
        }
    }
}
