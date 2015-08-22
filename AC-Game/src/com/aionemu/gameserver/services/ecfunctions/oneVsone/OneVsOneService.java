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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Kill3r
 */
public class OneVsOneService implements OneVsOneStruct{

    private static final Logger log = LoggerFactory.getLogger(OneVsOneService.class);
    private static final OneVsOneService Service = new OneVsOneService();
    public List<Player> playersToArena = new FastList<Player>();
    public boolean Initialized = false;

    public static OneVsOneService getInstance(){
        return Service;
    }

    @Override
    public ScheduledFuture autoAnnounce(int delayInMin){
        Initialized = true;
        log.info("Starting 1 vs 1 Event Service...");
        duelArenaRegChecker(60);
        return ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                announceEveryOne("1vs1", "You can register to 1vs1 by typing '.1v1'"); // later make config for this
            }
        }, delayInMin / 2 * 1000 * 60, delayInMin / 2 * 1000 * 60);  // also config for delay Timmer
    }

    @Override
    public ScheduledFuture duelArenaRegChecker(int deLayInSec){
        return ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() { // the fixed schedule that calls checkAnyPlayerReged() every 30sec
                //log.info("CHECKING DUELARENAREGCHECKER");
                checkAnyPlayerReged();
            }
        }, deLayInSec * 1000, deLayInSec * 1000);
    }

    private void checkAnyPlayerReged(){ // the main one that checks all the players and selects if there is any code for same code like player reg.. and ports..  DONE
        try{
            if (playersToArena.size() != 0){
                if(playersToArena.size() == 1){
                    announceAllRegistered(ArenaMasterService.getInstance().ArenaMaster, "Sorry, we couldn't find anyone to duel you, will try again..");
                    return;
                }
                //log.info("GOING TO CALCULATEPLAYERS");
                increaseCode();
                //calculatePlayers();
            }else{
                if (playersToArena.size() == 0){
                    log.info("[1vs1] No one Registered on the Queue, Continue Searching...");
                }else if (playersToArena.size() == 1){
                    log.info("[1vs1] Only one player registered in 1vs1 Queue, Need atleast 2!");
                }
            }
        } catch (NullPointerException ex){
            log.info("[1vs1] Could not found a player for the 1vs1!");
        }

    }

    public final boolean contains(Player p) {
        for (Player plr : this.playersToArena) {
            if (plr != null && plr.getObjectId() == p.getObjectId()) {
                return true;
            }
        }
        return false;
    }

    public boolean deletePlayer(Player player) {
        for (int i = 0; i < this.playersToArena.size(); i++) {
            Player p = this.playersToArena.get(i);
            if (p == null || !p.isOnline()) {
                this.playersToArena.remove(i);
                i--;
                continue;
            }
            if (p.getObjectId() == player.getObjectId()) {
                this.playersToArena.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void increaseCode(){
        checkIfAllAreOnlineIfNotRemove(playersToArena);
        List<Player> regEdPlayers = playersToArena;
        Collections.shuffle(regEdPlayers);
        for(Player eachPlayer : regEdPlayers){
            int AC = eachPlayer.getArenaCode();
            if(eachPlayer.getArenaCode() == 0){
                eachPlayer.setArenaCode(AC + 1);
                Collections.shuffle(playersToArena);
                for(Player sPlayer : playersToArena){
                    if(sPlayer.getArenaCode() == eachPlayer.getArenaCode() && sPlayer != eachPlayer){
                        removePortedPlayers(sPlayer, eachPlayer);
                        startPort(sPlayer, eachPlayer);
                        log.info("[1vs1] Partner Found! " + sPlayer.getName() + " and + " + eachPlayer.getName() + " has been ported!");
                        return;
                    }
                }
            }
        }
    }

    private void checkIfAllAreOnlineIfNotRemove(List<Player> allPlayers){
        for (Player p1 : allPlayers){
            if (!p1.isOnline()){
                deletePlayer(p1);
            }
        }
    }

    public void removePortedPlayers(Player p1, Player p2){
        deletePlayer(p1);
        deletePlayer(p2);
        p1.setArenaCode(0);
        p2.setArenaCode(0);
    }

    public boolean checkAlreadyExists(Player p1){
        try{
            if(contains(p1)){
                return true;
            }else{
                PacketSendUtility.sendMessage(p1, "You're ready to Register!");
                return false;
            }
        }catch (NullPointerException ex){
            return false;
        }
    }

    public void startPort(final Player opponent1, final Player opponent2){
        announceSpecificTwoOnly(opponent1, opponent2, ArenaMasterService.getInstance().ArenaMaster, "Found a Player to fight, You will be ported in 5sec..."); // later make the ArenaMaster config to set here
        opponent1.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
        opponent1.getEffectController().updatePlayerEffectIcons();
        opponent1.getEffectController().broadCastEffects();

        opponent2.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
        opponent2.getEffectController().updatePlayerEffectIcons();
        opponent2.getEffectController().broadCastEffects();

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                opponent1.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                opponent1.getEffectController().updatePlayerEffectIcons();
                opponent1.getEffectController().broadCastEffects();

                opponent2.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                opponent2.getEffectController().updatePlayerEffectIcons();
                opponent2.getEffectController().broadCastEffects();
                ArenaMasterService.getInstance().teleportIn(opponent1, opponent2);
            }
        },5000); //port after 5sec

    }

    public void announceAllRegistered(final String senderName, final String Message){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                if(contains(object)){
                    PacketSendUtility.sendSys2Message(object, senderName, Message);
                }
            }
        });
    }

    public void announceSpecificTwoOnly(final Player player1, final Player player2, final String senderName, final String Message){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                if (object == player1 || object == player2){
                    PacketSendUtility.sendSys2Message(object, senderName, Message);
                }
            }
        });
    }

    public void announceOneOnly(final Player player, final String senderName, final String Message){
        PacketSendUtility.sendSys2Message(player, senderName, Message);
    }

    public void announceEveryOne(final String senderName,final String Message){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, senderName, Message);
            }
        });
    }

    @Override
    public boolean isEnemy(Player effector, Player effected){
        return effector.isInDuelArena() && effected.isInDuelArena() && effector.getObjectId() != effected.getObjectId() && effector.isCasting() && effected.isCasting();
    }

}
