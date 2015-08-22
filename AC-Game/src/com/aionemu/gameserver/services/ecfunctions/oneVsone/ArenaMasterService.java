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
package com.aionemu.gameserver.services.ecfunctions.oneVsone;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RESURRECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_COOLDOWN;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kill3r
 * @Reworked eKsiK
 */
public class ArenaMasterService{

    public WorldMapInstance instance = null;
    public static final ArenaMasterService service = new ArenaMasterService();
    private static final Logger log = LoggerFactory.getLogger(ArenaMasterService.class);
    protected Map<Integer, WorldPosition> previousLocations = new HashMap<Integer, WorldPosition>();
    static Point3D loc1 = new Point3D(EventSystem.ONEVSONE_POINTX_1, EventSystem.ONEVSONE_POINTY_1, EventSystem.ONEVSONE_POINTZ_1); // done config  //winer point++
    static Point3D loc2 = new Point3D(EventSystem.ONEVSONE_POINTX_2, EventSystem.ONEVSONE_POINTY_2, EventSystem.ONEVSONE_POINTZ_2); // done config //loser point++
    static Point3D loc21 = new Point3D(EventSystem.ONEVSONE_POINT2X_1, EventSystem.ONEVSONE_POINT2Y_1, EventSystem.ONEVSONE_POINT2Z_1);
    static Point3D loc22 = new Point3D(EventSystem.ONEVSONE_POINT2X_2, EventSystem.ONEVSONE_POINT2Y_2, EventSystem.ONEVSONE_POINT2Z_2);
    static Point3D loc31 = new Point3D(EventSystem.ONEVSONE_POINT3X_1, EventSystem.ONEVSONE_POINT3Y_1, EventSystem.ONEVSONE_POINT3Z_1);
    static Point3D loc32 = new Point3D(EventSystem.ONEVSONE_POINT3X_2, EventSystem.ONEVSONE_POINT3Y_2, EventSystem.ONEVSONE_POINT3Z_2);
    public String ArenaMaster = EventSystem.ONEVSONE_ARENAMASTER; // make config+++
    public int item1 = EventSystem.ITEM1; //make config++
    public int item2 = EventSystem.ITEM2; //make config++
    public int quantity1 = EventSystem.QUANTITY1; //make config++
    public int quantity2 = EventSystem.QUANTITY2; // make config++
    public FastMap<Integer, Player> players = new FastMap<Integer, Player>();

    public static ArenaMasterService getInstance(){
        return service;
    }

    //make a way to count the kill counts and the winner and also if its TIE

    public boolean onDie(Player attacker, Creature attacked){
        deathPacket((Player) attacked);
        onDeath(attacker, attacked);
        return true;
    }

    public void onPlayerLogOut(Player player){
        player.setInPkMode(false);
        player.setInDuelArena(false);
        player.setArenaTie(false);
        player.setWinCount(0);
        setRound(player, 0);
        for (int world : OneVsOneStruct.worldid){
            if(player.getWorldId() == world){
                final Player opponent = getPlayerFighterFromList(player);
                if(opponent != null){
                    if (opponent.getInstanceId() == player.getInstanceId() || containsInTheList(player)){
                        OneVsOneService.getInstance().announceOneOnly(opponent, ArenaMaster, "You're Opponent has been disconnected , You'll be ported back to bind spot in 5sec!");
                        freezeForSec(opponent, 5);
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                teleportOut(opponent);
                            }
                        }, 5000);
                    }
                }
            }
        }
        onLeaveInstance(player);
    }

    public void onLeaveInstance(Player player){
        if(!EventSystem.ENABLE_ONEVONE){
            return;
        }
        for (int world : OneVsOneStruct.worldid){
            if(player.getWorldId() != world){
                return;
            }
        }
        removePlayerFromFastList(player);
        player.setInPkMode(false);
        player.setWinCount(0);
        setRound(player, 0);
        player.setInDuelArena(false);
        TeleportService2.moveToBindLocation(player, false);
    }

    public void onDeath(final Player attacker, final Creature attacked){
        if (attacker.isInDuelArena() && ((Player) attacked).isInDuelArena()){
            if (attacker.getArenaRound() >= 3){
                freezeForSec(attacker, 6);
                freezeForSec((Player) attacked, 6);
                giveLeaveMessages(attacker, (Player) attacked);
                giveRewards(attacker, (Player) attacked);
                ThreadPoolManager.getInstance().schedule(new Runnable() { // teleport them out after 3 round.. after 5sec delay
                    @Override
                    public void run() {
                        teleportOut(attacker);
                        teleportOut((Player) attacked);
                    }
                }, 5000);
            }else{
                freezeForSec(attacker, 15);
                freezeForSec((Player) attacked, 15);
                sendSeperateWinLoseMsg(attacker, (Player) attacked);
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        giveWinnerMessageAndCheckRound(attacker, (Player) attacked);
                    }
                }, 10000); //10sec
            }
        }
    }

    private boolean isTie(Player p1, Player p2){
        return p1.isArenaTie() && p2.isArenaTie();
    }

    private void sendSeperateWinLoseMsg(Player winner, Player loser){
        if(isTie(winner, loser)){
            OneVsOneService.getInstance().announceSpecificTwoOnly(winner, loser, ArenaMaster, "No Winner has been Found in this Round!!");
            freezeForSec(winner, 10);
            freezeForSec(loser, 10);
            return;
        }
        OneVsOneService.getInstance().announceOneOnly(winner, ArenaMaster, "You are the winner of this round!");
        OneVsOneService.getInstance().announceOneOnly(loser, ArenaMaster, "You've lost this round!");
    }

    private void deathPacket(Player player){
        player.getLifeStats().setCurrentHpPercent(100);
        player.getLifeStats().sendHpPacketUpdate();
        player.getLifeStats().updateCurrentStats();
        PacketSendUtility.sendPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.getX(), player.getY(), player.getZ(), player.getHeading(), player.getTarget().getObjectId()));
        //PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0 , 0));
    }

    private void giveRewards(Player winner, Player loser){ //make config for AP, GP , or ITEM REWARD
        int rnd = Rnd.get(1, 20);
        Player winz = getWinnerPlayer(winner, loser);
        Player lost = getLoserPlayer(winner, loser);
        if(winz.getWinCount() == lost.getWinCount()){ // if its a tie, give lose AP/GP reward for both, no ItemReward and no toll reward
            OneVsOneService.getInstance().announceSpecificTwoOnly(winner, loser, ArenaMaster, "No one Won This Round, its a TIE!");
            if(EventSystem.ENABLE_AP_REWARD){
                AbyssPointsService.addAp(winz, EventSystem.AP_REWARD / 2);
                AbyssPointsService.addAp(lost, EventSystem.AP_REWARD / 2);
            }
            if(EventSystem.ENABLE_GP_REWARD){
                AbyssPointsService.addGp(winz, EventSystem.GP_REWARD / 2);
                AbyssPointsService.addGp(lost, EventSystem.GP_REWARD / 2);
            }
            return;
        }

        if (EventSystem.ITEMREWARD_ENABLE){ // The item reward is not a 100% reward that you get, there is chance that you will get it or not
            if(rnd == 18){
                ItemService.addItem(winz, item1, quantity1);
            }else if (rnd == 11){
                ItemService.addItem(winz, item1, quantity1);
            }else if (rnd == 4){
                ItemService.addItem(winz, item1, quantity1);
            }else if (rnd == 14){
                ItemService.addItem(winz, item2, quantity2);
            }else if (rnd == 1){
                ItemService.addItem(winz, item2, quantity2);
            }
        }

        // Reward for Winners/losers
        if(EventSystem.ENABLE_AP_REWARD){
            AbyssPointsService.addAp(winz, EventSystem.AP_REWARD);
            AbyssPointsService.addAp(lost, EventSystem.AP_REWARD / 2);
        }
        if(EventSystem.ENABLE_GP_REWARD){
            AbyssPointsService.addGp(winz, EventSystem.GP_REWARD);
            AbyssPointsService.addGp(lost, EventSystem.GP_REWARD / 2);
        }
        if(EventSystem.ENABLE_TOLL_REWARD){
            InGameShopEn.getInstance().addToll(winz, EventSystem.TOLL_REWARD);
            InGameShopEn.getInstance().addToll(lost, EventSystem.TOLL_REWARD / 2);
        }
    }

    private void giveLeaveMessages(final Player p1, final Player p2){
        if (p1.getLifeStats().isAlreadyDead()){
            p1.setPlayerResActivate(true);
            PacketSendUtility.sendPacket(p1, new SM_RESURRECT(p2));
            PlayerReviveService.skillRevive(p1);
        }
        if (p2.getLifeStats().isAlreadyDead()){
            p2.setPlayerResActivate(true);
            PacketSendUtility.sendPacket(p2, new SM_RESURRECT(p1));
            PlayerReviveService.skillRevive(p2);
        }
        sendTimerPacket(p1, p2, 0);

        int winCount = p1.getWinCount();
        p1.setWinCount(winCount + 1);

        Player winner = getWinnerPlayer(p1, p2);
        Player lose = getLoserPlayer(p1, p2);
        if (winner == null){
            OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "This match was a TIE, Better luck next time :) !!");
        }else{
            OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "The Winner of the match is " + winner.getName());
            OneVsOneService.getInstance().announceOneOnly(lose, ArenaMaster, "You've Lost the match, But still you will get some Rewards !");
        }
    }

    public Player getWinnerPlayer(Player p1, Player p2){
        if (p1.getWinCount() == p2.getWinCount()){
            return null;
        }else if (p1.getWinCount() >= 2){
            return p1;
        }else{
            return p2;
        }
    }

    public Player getLoserPlayer(Player p1, Player p2){
        if (p1.getWinCount() == p2.getWinCount()){
            return null;
        }else if (p1.getWinCount() >= 2){
            return p2;
        }else{
            return p1;
        }
    }

    private void HealPlayer(Player player1, boolean sendUpdatePacket) {
        player1.getLifeStats().setCurrentHpPercent(100);
        player1.getLifeStats().setCurrentMpPercent(100);

        if (sendUpdatePacket) {
            player1.getLifeStats().sendHpPacketUpdate();
            player1.getLifeStats().sendMpPacketUpdate();
        }
    }

    private void setTie(Player p1, Player p2, boolean tieState){
        p1.setArenaTie(tieState);
        p2.setArenaTie(tieState);
    }

    private void giveWinnerMessageAndCheckRound(final Player Winp1, final Player Losep2){
        //if(round == 1){ // just to make sure if winCount is 0, starting from first round, to count
            //Winp1.setWinCount(0);
        //}
        if(isTie(Winp1, Losep2)){
            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "The winner of this round is ______ :)");
            setTie(Winp1, Losep2, false);
        }else{
            int winCount = Winp1.getWinCount();
            Winp1.setWinCount(winCount + 1);
            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "The winner of this round is " + Winp1.getName());
        }


        log.info("CURRENT ROUND AFTER DEATH = " + Winp1.getArenaRound());
        increaseRound(Winp1, Losep2);
        log.info("ROUND ADDED with 1 and RESULT = " + Losep2.getArenaRound());

        if(Winp1.getArenaRound() == 2){
            sendTimerPacket(Winp1, Losep2, 305);
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (Winp1.getArenaRound() == 2 && Losep2.isInDuelArena()) {
                        freezeForSec(Winp1, 3);
                        freezeForSec(Losep2, 3);
                        OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "Guess no one was able to kill anyone!!");
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                portThemAsideForNextRound(Winp1, Losep2);
                                freezeForSec(Winp1, 5);
                                freezeForSec(Losep2, 5);
                                setTie(Winp1, Losep2, true);
                                onDeath(Winp1, Losep2);
                            }
                        }, 3000);
                    }
                }
            }, 305000);
            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "Round 2 will be Starting...");
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    portThemAsideForNextRound(Winp1, Losep2);
                }
            }, 3000);
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    portThemAsideForNextRound(Winp1, Losep2);
                    OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "3..");
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "2..");
                            ThreadPoolManager.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "1..");
                                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "FIGHT!!");
                                        }
                                    }, 1000);
                                }
                            }, 1000);
                        }
                    }, 1000);
                }
            }, 2 * 1000);
        }

        if (Winp1.getArenaRound() == 3) {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (Winp1.getArenaRound() == 3 && Losep2.isInDuelArena()) {
                        freezeForSec(Winp1, 3);
                        freezeForSec(Losep2, 3);
                        OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "Guess no one was able to kill anyone, This Round Will be TIE !!");
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                portThemAsideForNextRound(Winp1, Losep2);
                                freezeForSec(Winp1, 5);
                                freezeForSec(Losep2, 5);
                                setTie(Winp1, Losep2, true);
                                onDeath(Winp1, Losep2);
                            }
                        }, 3000);
                    }
                }
            }, 305000);
            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "Final Round starting in...");
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            portThemAsideForNextRound(Winp1, Losep2);
                        }
                    }, 3000);
                    OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "3..");
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "2..");
                            ThreadPoolManager.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "1..");
                                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            OneVsOneService.getInstance().announceSpecificTwoOnly(Winp1, Losep2, ArenaMaster, "FIGHT!!");
                                        }
                                    }, 1000);
                                }
                            }, 1000);
                        }
                    }, 1000);
                }
            }, 2 * 1000);
            sendTimerPacket(Winp1, Losep2, 305);
        }
    }

    private void portThemAsideForNextRound(Player p1, Player p2){
        if(p1.getLifeStats().isAlreadyDead()){
            p1.setPlayerResActivate(true);
            PacketSendUtility.sendPacket(p1, new SM_RESURRECT(p2));
            PlayerReviveService.skillRevive(p1);
        }
        if(p2.getLifeStats().isAlreadyDead()){
            p2.setPlayerResActivate(true);
            PacketSendUtility.sendPacket(p1, new SM_RESURRECT(p1));
            PlayerReviveService.skillRevive(p2);
        }
        HealPlayer(p1, true);
        HealPlayer(p2, true);
        p1.getGameStats().updateStatsAndSpeedVisually();
        p2.getGameStats().updateStatsAndSpeedVisually();
        p1.getEffectController().updatePlayerEffectIconsImpl();
        p2.getEffectController().updatePlayerEffectIconsImpl();
        if (p1.getWorldId() == OneVsOneStruct.worldid[0]){
            TeleportService2.teleportTo(p1, OneVsOneStruct.worldid[0], (float) loc1.getX(), (float) loc1.getY(), (float) loc1.getZ());
            TeleportService2.teleportTo(p2, OneVsOneStruct.worldid[0], (float) loc2.getX(), (float) loc2.getY(), (float) loc2.getZ());
        }else if(p1.getWorldId() == OneVsOneStruct.worldid[1]){
            TeleportService2.teleportTo(p1, OneVsOneStruct.worldid[1], (float) loc21.getX(), (float) loc21.getY(), (float) loc21.getZ());
            TeleportService2.teleportTo(p2, OneVsOneStruct.worldid[1], (float) loc22.getX(), (float) loc22.getY(), (float) loc22.getZ());
        }else if(p1.getWorldId() == OneVsOneStruct.worldid[2]){
            TeleportService2.teleportTo(p1, OneVsOneStruct.worldid[2], (float) loc31.getX(), (float) loc31.getY(), (float) loc31.getZ());
            TeleportService2.teleportTo(p2, OneVsOneStruct.worldid[2], (float) loc32.getX(), (float) loc32.getY(), (float) loc32.getZ());
        }
        sendTimerPacket(p1, p2, 305);
    }

    //you have 5sec to give all the msg about winner and all
    private void freezeForSec(final Player alivePlayer, int DelaySeconds){
        alivePlayer.getEffectController().setAbnormal(AbnormalState.PARALYZE.getId());
        alivePlayer.getEffectController().updatePlayerEffectIcons();
        alivePlayer.getEffectController().broadCastEffects();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                unFreeze(alivePlayer);
            }
        }, DelaySeconds * 1000);
    }

    private void unFreeze(Player alivePlayer){
        alivePlayer.getEffectController().unsetAbnormal(AbnormalState.PARALYZE.getId());
        alivePlayer.getEffectController().updatePlayerEffectIcons();
        alivePlayer.getEffectController().broadCastEffects();
    }

    public void teleportIn(Player player1, Player player2) {
        int random = Rnd.get(1, EventSystem.ONEVSONE_MAPCNT);
        if (random == 1) { // if it is 1 = Sanctum Arena
            instance = createNewInstance();
            previousLocations.put(player1.getObjectId(), player1.getPosition());
            previousLocations.put(player2.getObjectId(), player2.getPosition());
            TeleportService2.teleportTo(player1, OneVsOneStruct.worldid[0], instance.getInstanceId(), (float) loc1.getX(), (float) loc1.getY(), (float) loc1.getZ());
            TeleportService2.teleportTo(player2, OneVsOneStruct.worldid[0], instance.getInstanceId(), (float) loc2.getX(), (float) loc2.getY(), (float) loc2.getZ());
        }else if(random == 2){ // Aturam Skyfortress Map
            instance = createNewInstance2();
            previousLocations.put(player1.getObjectId(), player1.getPosition());
            previousLocations.put(player2.getObjectId(), player2.getPosition());
            TeleportService2.teleportTo(player1, OneVsOneStruct.worldid[1], instance.getInstanceId(), (float) loc21.getX(), (float) loc21.getY(), (float) loc21.getZ());
            TeleportService2.teleportTo(player2, OneVsOneStruct.worldid[1], instance.getInstanceId(), (float) loc22.getX(), (float) loc22.getY(), (float) loc22.getZ());
        }else if(random == 3){ // Padma cave
            instance = createNewInstance3();
            previousLocations.put(player1.getObjectId(), player1.getPosition());
            previousLocations.put(player2.getObjectId(), player2.getPosition());
            TeleportService2.teleportTo(player1, OneVsOneStruct.worldid[2], instance.getInstanceId(), (float) loc31.getX(), (float) loc31.getY(), (float) loc31.getZ());
            TeleportService2.teleportTo(player2, OneVsOneStruct.worldid[2], instance.getInstanceId(), (float) loc32.getX(), (float) loc32.getY(), (float) loc32.getZ());
        }
        players.put(player1.getObjectId(), player2);
        player1.setWinCount(0);
        player2.setWinCount(0);
        getReady(player1, player2);
        HealPlayer(player1, true);
        HealPlayer(player2, true);
        freezeForSec(player1, 16);
        freezeForSec(player2, 16);
        sendTimerPacket(player1, player2, 315);
        giveStartMsg(player1, player2);
    }

    private Player getPlayerFighterFromList(Player p1){
        return players.get(p1.getObjectId());
    }

    private void removePlayerFromFastList(Player p1){
        if (players.containsKey(p1.getObjectId())){
            players.remove(p1.getObjectId());
        }
    }

    private boolean containsInTheList(Player p1){
        if (players.containsKey(p1.getObjectId())){
            return true;
        }else{
            return false;
        }
    }

    private void sendTimerPacket(Player p1, Player p2, int timer){
        if(p1 != null){
            PacketSendUtility.sendPacket(p1, new SM_QUEST_ACTION(0, timer));
        }
        if(p2 != null){
            PacketSendUtility.sendPacket(p2, new SM_QUEST_ACTION(0, timer));
        }
    }

    private void setRound(Player player1, int Round){
        player1.setArenaRound(Round);
    }

    private void increaseRound(Player player1, Player player2){
        int p1Round = player1.getArenaRound();
        int p2Round = player2.getArenaRound();
        int p1Final = p1Round + 1;
        int p2Final = p2Round + 1;

        player1.setArenaRound(p1Final);
        player2.setArenaRound(p2Final);
    }

    private void giveStartMsg(final Player p1, final Player p2){
        p1.setArenaRound(1);
        p2.setArenaRound(1);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (p1.getArenaRound() == 1 && p1.isInDuelArena()){
                    freezeForSec(p1, 3);
                    freezeForSec(p2, 3);
                    OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "Guess no one was able to kill anyone in First Round !!");
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            portThemAsideForNextRound(p1, p2);
                            freezeForSec(p1, 5);
                            freezeForSec(p2, 5);
                            setTie(p1, p2, true);
                            onDeath(p1, p2);
                        }
                    }, 3000);
                }
            }
        }, 315000); // 315 sec
        OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "There will be 3 Rounds of Fight, The player who wins the most round will be the Winner!!");
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "Now Starting Round 1 in...");
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "5..");
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "4..");
                                ThreadPoolManager.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "3..");
                                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                                            @Override
                                            public void run() {
                                                OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "2..");
                                                ThreadPoolManager.getInstance().schedule(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "1..");
                                                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                OneVsOneService.getInstance().announceSpecificTwoOnly(p1, p2, ArenaMaster, "FIGHT!!");
                                                            }
                                                        }, 1000);
                                                    }
                                                }, 1000);
                                            }
                                        }, 1000);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                }, 5 * 1000);
            }
        }, 5 * 1000);
    }

    public void teleportOut(Player player1){
        setRound(player1, 0);
        player1.setInPkMode(false);
        player1.setInDuelArena(false);
        player1.setArenaTie(false);
        player1.setWinCount(0);
        sendTimerPacket(player1, null, 0);
        WorldPosition pos1 = previousLocations.get(player1.getObjectId());
        if (pos1 != null){
            TeleportService2.teleportTo(player1, pos1.getMapId(), pos1.getX(), pos1.getY(), pos1.getZ(), pos1.getHeading());
        }else{
            TeleportService2.moveToBindLocation(player1, true);
        }
        removePlayerFromFastList(player1);
        LeavingPlayer(player1);
    }

    protected WorldMapInstance createNewInstance(){
        instance = InstanceService.getNextAvailableInstance(OneVsOneStruct.worldid[0]);
        return instance;
    }

    protected WorldMapInstance createNewInstance2(){
        instance = InstanceService.getNextAvailableInstance(OneVsOneStruct.worldid[1]);
        return instance;
    }

    protected WorldMapInstance createNewInstance3(){
        instance = InstanceService.getNextAvailableInstance(OneVsOneStruct.worldid[2]);
        return instance;
    }

    private void getReady(Player p1, Player p2){ // add removecd without xform skills
        // setting player 1 up
        p1.getController().cancelCurrentSkill();
        p1.setInPkMode(true);
        p1.setInDuelArena(true);
        p1.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
        p1.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.BUFF);
        p1.getCommonData().setDp(0);
        if (EventSystem.ENABLE_REMOVECD){
            resetSkills(p1);
        }

        // setting player 2 up
        p2.getController().cancelCurrentSkill();
        p2.setInPkMode(true);
        p2.setInDuelArena(true);
        p2.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
        p2.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.BUFF);
        p2.getCommonData().setDp(0);
        if (EventSystem.ENABLE_REMOVECD){
            resetSkills(p2);
        }
    }

    private void resetSkills(Player player1){
        List<Integer> delayIds = new ArrayList<Integer>();
        if (player1.getSkillCoolDowns() != null) {
            long currentTime = System.currentTimeMillis();
            for (Map.Entry<Integer, Long> en : player1.getSkillCoolDowns().entrySet()) {

                delayIds.add(en.getKey());

                if (!EventSystem.TOUCH_XFORM_SKILL){
                    if(delayIds.contains(11885) || delayIds.contains(11886) || delayIds.contains(11887) || delayIds.contains(11888) || delayIds.contains(11889) ||
                            delayIds.contains(11890) || delayIds.contains(11891) || delayIds.contains(11892) || delayIds.contains(11893) || delayIds.contains(11894)){
                        delayIds.remove(en.getKey());
                    }
                }
            }

            for (Integer delayId : delayIds) {
                player1.setSkillCoolDown(delayId, currentTime);
            }

            delayIds.clear();
            PacketSendUtility.sendPacket(player1, new SM_SKILL_COOLDOWN(player1.getSkillCoolDowns()));
        }
    }

    private void LeavingPlayer(Player p1){
        // setting player 1 up
        p1.getController().cancelCurrentSkill();
        p1.getLifeStats().setCurrentHpPercent(100);
        p1.getLifeStats().setCurrentMpPercent(100);
    }


}
