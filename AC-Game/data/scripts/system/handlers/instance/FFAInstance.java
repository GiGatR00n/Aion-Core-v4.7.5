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


package instance;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.controllers.attack.AggroInfo;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.ecfunctions.ffa.FFaStruct;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.audit.AuditLogger;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author kill3r
 */
@InstanceID(300230000)
public class FFAInstance extends GeneralInstanceHandler {

    private static final Logger log = LoggerFactory.getLogger("FFA_LOG");

    static Point3D[] positions = new Point3D[]{
            new Point3D(EventSystem.FFA_SPAWNPOINT_1X, EventSystem.FFA_SPAWNPOINT_1Y,EventSystem.FFA_SPAWNPOINT_1Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_2X,EventSystem.FFA_SPAWNPOINT_2Y,EventSystem.FFA_SPAWNPOINT_2Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_3X,EventSystem.FFA_SPAWNPOINT_3Y,EventSystem.FFA_SPAWNPOINT_3Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_4X,EventSystem.FFA_SPAWNPOINT_4Y,EventSystem.FFA_SPAWNPOINT_4Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_5X,EventSystem.FFA_SPAWNPOINT_5Y,EventSystem.FFA_SPAWNPOINT_5Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_6X,EventSystem.FFA_SPAWNPOINT_6Y,EventSystem.FFA_SPAWNPOINT_6Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_7X,EventSystem.FFA_SPAWNPOINT_7Y,EventSystem.FFA_SPAWNPOINT_7Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_8X,EventSystem.FFA_SPAWNPOINT_8Y,EventSystem.FFA_SPAWNPOINT_8Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_9X,EventSystem.FFA_SPAWNPOINT_9Y,EventSystem.FFA_SPAWNPOINT_9Z),
            new Point3D(EventSystem.FFA_SPAWNPOINT_10X,EventSystem.FFA_SPAWNPOINT_10Y,EventSystem.FFA_SPAWNPOINT_10Z),
    };

    public Point3D randomSpawn() {
        int pos = Rnd.get(positions.length - 1);
        return positions[pos];
    }

    //@Override
   // public void onInstanceCreate(WorldMapInstance instance){

    //    if(instance.getNpcs() != null){
    //        for (Npc npc : this.instance.getNpcs()) {
    //            npc.getController().onDelete();
    //        }
     //   }
    //}

// CHECK IF DOUBLE INSTANCE CREATE WHEN DONE ON SUPER.ONSTANCE - DOne
    @Override
    public void onEnterInstance(Player player){
        PacketSendUtility.sendSys2Message(player, EventSystem.FFA_ANNOUNCER_NAME, EventSystem.FFA_WELCOME_MSG);
        player.setInFFA(true);
        player.getKnownList().doUpdate();
        player.setSpecialKills(0);
        player.setKSLevel(0);
        instance.register(player.getObjectId());
    }

    @Override
    public void onLeaveInstance(Player player){
        player.setInFFA(false);
        player.setSpecialKills(0);
        player.setKSLevel(0);
        PacketSendUtility.sendSys2Message(player, EventSystem.FFA_ANNOUNCER_NAME, EventSystem.FFA_LEAVE_MSG);
    }

    public int getPlayerCount() {
        return instance.getPlayersInside().size();
    }

    @Override
    public void onPlayerLogOut(Player player){
        player.setInFFA(false);
        player.setSpecialKills(0);
        player.setKSLevel(0);
        TeleportService2.moveToBindLocation(player, false);
    }

    @Override
    public boolean isEnemy(Player attacker, Player target) {
        if (attacker != target || attacker.isInFFA() && target.isInFFA() && attacker.getObjectId() != target.getObjectId() && attacker.isCasting() && target.isCasting()) {
            return true;
        }
        return super.isEnemy(attacker, target);
    }

    private void HealPlayer(Player player, boolean withDp, boolean sendUpdatePacket) {
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

    @Override
    public boolean onReviveEvent(Player player) {
        super.onReviveEvent(player);
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        HealPlayer(player, false, true);
        Point3D loc = randomSpawn();
        TeleportService2.teleportTo(player, FFaStruct.worldId, player.getInstanceId(), (float) loc.getX() , (float) loc.getY(), (float) loc.getZ(), player.getHeading() , TeleportAnimation.NO_ANIMATION);
        player.getLifeStats().updateCurrentStats();
        player.getGameStats().updateStatsAndSpeedVisually();
        player.getKnownList().doUpdate();
        AddProtection(player, 5000 , 0);
        return true;
    }

    protected void addShield(final Player player, int duration){
        Skill protector = SkillEngine.getInstance().getSkill(player, 9833, 1, player.getTarget());
        Effect e = new Effect(player, player, protector.getSkillTemplate(), protector.getSkillLevel(), duration);
        for (EffectTemplate et : e.getEffectTemplates()) {
            et.setDuration(duration);
        }
        e.initialize();
        e.applyEffect();

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                RemoveProtection(player);
            }
        }, duration);
    }

    protected void AddProtection(final Player p, final int duration, int delay) {
        if (delay == 0) {
            this.addShield(p, duration);
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    addShield(p, duration);
                }
            }, delay);
        }
    }

    protected void RemoveProtection(Player p) {
        p.getEffectController().removeEffect(9833);
    }

    @Override
    public boolean onDie(Player attacker, Creature target){
        sendDeathPacket(attacker, target);
        this.deathPlayer(target, attacker);
        return true;
    }

    protected void sendDeathPacket(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0, 8));
    }

    public boolean deathPlayer(Creature attacker, Creature attacked) {
        if (attacker instanceof Player && attacked instanceof Player && attacker != attacked) {
            Player winner = (Player) attacker;
            Player loser = (Player) attacked;
            if(checkIfLoserGotAtleastSomeDamageFromWinner(loser) > loser.getLifeStats().getMaxHp() / 100 * 20){
                winner = loser.getAggroList().getMostPlayerDamage();
            }
            loser.getAggroList().clear();
            loser.setSpecialKills(0);
            checkIfSameMac(winner, loser);
            giveKillMsgWithKills(winner, loser);
            checkKillReward(winner, true, false);
            checkKillReward(winner, false, true);
            PacketSendUtility.sendMessage(winner, "You now have " + winner.getSpecialKills() + " kills!");
            log.info("[FFA] " + winner.getName() + " killed " + loser.getName());
            return true;
        } else if (attacked instanceof Player && attacker != attacked) {
            Player loser = (Player) attacked;
            Player winner = null;
            if(checkIfLoserGotAtleastSomeDamageFromWinner(loser) > loser.getLifeStats().getMaxHp() / 100 * 20){
                winner = loser.getAggroList().getMostPlayerDamage();
            }
            loser.getAggroList().clear();
            if (winner == null) {
                winner = loser.getAggroList().getMostPlayerDamage();
            }
            loser.setSpecialKills(0);
            checkIfSameMac(winner, loser);
            giveKillMsgWithKills(winner, loser);
            checkKillReward(winner, true, false);
            checkKillReward(winner, false, true);
            PacketSendUtility.sendMessage(winner, "You now have " + winner.getSpecialKills() + " kills!");
            log.info("[FFA] " + winner.getName() + " killed " + loser.getName());
            return true;
        } else {
            announceAllInFFA("A ghost killed " + attacked.getName());
        }
        return false;
    }

    private void checkIfSameMac(Player winner, Player loser){
        String ip1 = winner.getClientConnection().getIP();
        String mac1 = winner.getClientConnection().getMacAddress();
        String ip2 = loser.getClientConnection().getIP();
        String mac2 = loser.getClientConnection().getMacAddress();
        if ((mac1 != null) && (mac2 != null)) {
            if ((ip1.equalsIgnoreCase(ip2)) && (mac1.equalsIgnoreCase(mac2))) {
                AuditLogger.info(winner, "[TradeKillAlert] You really need to check player " + winner.getName() + " and " + loser.getName() + ", They have same IP and MAC and possible they are Trade Killing in FFA, so please go and check in invisible! (MAC: " + mac1 + ").");
                int lose_ap = 30000;
                int lose_gp = 5000;
                int omegaId = 166020000;
                int tsId = 166030005;
                AbyssPointsService.addAp(winner, -lose_ap); // reducing ap from trade killers
                AbyssPointsService.addAp(loser, -lose_ap);

                AbyssPointsService.addGp(winner, -lose_gp); // reducing gp from trade killers
                AbyssPointsService.addGp(loser, -lose_gp);

                winner.getInventory().decreaseByItemId(omegaId, 1); // removing Omega from trade killers
                loser.getInventory().decreaseByItemId(omegaId, 1);

                loser.getInventory().decreaseByItemId(tsId, 1); //removeing TS from trade killers
                winner.getInventory().decreaseByItemId(tsId, 1);
                PacketSendUtility.sendMessage(winner, "[TradeKillAlert] You lost " + lose_ap + " AP for Trade Killing!"); // AP Lose msg
                PacketSendUtility.sendMessage(loser, "[TradeKillAlert] You lost " + lose_ap + " AP for Trade Killing!");
                PacketSendUtility.sendMessage(winner, "[TradeKillAlert] You lost " + lose_gp + " GP for Trade Killing!"); // GP Lose msg
                PacketSendUtility.sendMessage(loser, "[TradeKillAlert] You lost " + lose_gp + " GP for Trade Killing!");
                PacketSendUtility.sendMessage(winner, "[TradeKillAlert] You lost [item:" + omegaId + "] x 1 and [item:" + tsId + "] x 1 item(s) for Trade Killing!"); // item lose Item msg
                PacketSendUtility.sendMessage(loser, "[TradeKillAlert] You lost [item:" + omegaId + "] x 1 and [item:" + tsId + "] x 1 item(s) for Trade Killing!"); // item lose Item msg
                PacketSendUtility.sendMessage(winner, "[TradeKillAlert] Next Time Don't Trade Kill >_>");
                PacketSendUtility.sendMessage(loser, "[TradeKillAlert] Next Time Don't Trade Kill >_>");
                log.info("[FFA-TradeKill] Player " + winner.getName() + " killed " + loser.getName() + " and Have same IP and MAC!");
                return;
            }
            if (ip1.equalsIgnoreCase(ip2)) {
                AuditLogger.info(winner, "[TradeKillAlert] Possible chances that " + winner.getName() + " and " + loser.getName() + " are trade killing in FFA. They have same ip " + ip1 + ".");
                AuditLogger.info(winner, "[TradeKillAlert] If not, they are in some kinda cafe, with same network. OR USING SAME WTFAST Connection!!");
            }
        }
    }

    public int checkIfLoserGotAtleastSomeDamageFromWinner(Player loser){
        for (AggroInfo ai : loser.getAggroList().getList()) {
            if (!(ai.getAttacker() instanceof Creature)) {
                continue;
            }
            Creature master = ((Creature) ai.getAttacker()).getMaster();
            if (master == null) {
                continue;
            }
            if (master instanceof Player) {
                Player player = (Player) master;
                if(ai.getDamage() > loser.getLifeStats().getMaxHp() / 100 * 20){
                    return ai.getDamage();
                }
            }
        }
        return 0;
    }

    public void checkKillReward(final Player winner, boolean isAp, boolean isGp){

        if(winner.getSpecialKills() <= 5){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP1);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP1);
            }
        }else if(winner.getSpecialKills() <= 10){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP2);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP2);
            }
        }else if(winner.getSpecialKills() <=15){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP3);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP3);
            }
        }else if(winner.getSpecialKills() <=20){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP4);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP4);
            }
        }else if(winner.getSpecialKills() <=25){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP5);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP5);
            }
        }else if(winner.getSpecialKills() <=30){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP6);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP6);
            }
        }else if(winner.getSpecialKills() <=35){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP7);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP7);
            }
        }else if(winner.getSpecialKills() <=40){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP8);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP8);
            }
        }else if(winner.getSpecialKills() <=45){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP9);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP9);
            }
        }else if(winner.getSpecialKills() <=50){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP10);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP10);
            }
        }else if(winner.getSpecialKills() > 50){
            if(isAp){
                AbyssPointsService.addAp(winner, EventSystem.FFA_REWARD_AP11);
            }else if(isGp){
                AbyssPointsService.addGp(winner, EventSystem.FFA_REWARD_GP11);
            }
        }

    }
//MAKE MANUAL SK KILL SET IN .FFA
    public void giveKillMsgWithKills(Player winner, Player loser){
        int TotalKillsNow = winner.getSpecialKills();
        winner.setSpecialKills(TotalKillsNow + 1);
        int updatedKillStreak = TotalKillsNow + 1;
        int kSLevel = winner.getKSLevel();


        if(updatedKillStreak == 5){
            announceBigKillStreakToAll(winner.getName() + " is now on a Killing Spree!(Kills : 5)");
            kSLevel = 1;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 10){
            announceBigKillStreakToAll(winner.getName() + " is now on Rampage!(Kills : 10)");
            kSLevel = 2;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 15){
            announceBigKillStreakToAll(winner.getName() + " is now Dominating!(Kills : 15)");
            kSLevel = 3;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 20){
            announceBigKillStreakToAll("Unstoppable " + winner.getName() + "!(Kills : 20)");
            kSLevel = 4;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 25){
            announceBigKillStreakToAll(winner.getName() + ": CHUUCHUU MUTHAFAKAAASS!(Kills : 25)");
            kSLevel = 5;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 30){
            announceBigKillStreakToAll(winner.getName() + " is now Getting Crazzyyy !!!(Kills : 30)");
            kSLevel = 6;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 35){
            announceBigKillStreakToAll(winner.getName() + " is now GODLIKE!!!(Kills : 35)");
            kSLevel = 7;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 40){
            announceBigKillStreakToAll(winner.getName() + " is now on WICKED SICKKKKKK!(Kills : 40)");
            kSLevel = 8;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 45){
            announceBigKillStreakToAll(winner.getName() + ": Really knows how to kill players!!!!!!(Kills : 45)");
            kSLevel = 9;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else if(updatedKillStreak == 50){
            announceBigKillStreakToAll(winner.getName() + ": IS NOW A TRUE PVP FIGHTER!!!!!!!(Kills : 50)");
            kSLevel = 10;
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + winner.getName() + " is on a Killing Spree of Level " + kSLevel);
        }else{
            announceNormalKillMsg(winner, loser, "has just killed");
            if(loser.getKSLevel() >= 1){
                checkIfKillingSpree(loser, winner);
            }
        }
        winner.setKSLevel(kSLevel);

        if(!winner.getLifeStats().isAlreadyDead()){
            giveSomeHelp(winner);
        }
    }

    public void checkIfKillingSpree(Player oneWithKS, Player ksEnter){
        int kSLevel = oneWithKS.getKSLevel();
        if(kSLevel == 1){
            announceBigKillStreakToAll(oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 2){
            announceBigKillStreakToAll(oneWithKS.getName() + "'s Rampaging kill streak has been ended by " + ksEnter.getName());
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 3){
            announceBigKillStreakToAll(ksEnter.getName() + "'s Dominating Spree has been ended !!(KS Ended :" + oneWithKS.getName() + ")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 4){
            announceBigKillStreakToAll(ksEnter.getName() + " Has been stopped from going any furthur!!(KS Ended :" + oneWithKS.getName() + ")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 5){
            announceBigKillStreakToAll(ksEnter.getName() + ": They see me Rolling They hating!!(KS Ended :" + oneWithKS.getName() + ")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 6){
            announceBigKillStreakToAll(ksEnter.getName() + ": Next Time, Learn some Skills when you face me in a fight!!(KS Ended :" + oneWithKS.getName() + ")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 7){
            announceBigKillStreakToAll(ksEnter.getName() + ": Fukk YeaHHH!!!! (KS Ended :" + oneWithKS.getName() + ")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 8){
            announceBigKillStreakToAll(ksEnter.getName() + ": Cmon!!, Thats all you can do ?!?! O.O (KS Ended :"+oneWithKS.getName()+")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 9){
            announceBigKillStreakToAll(ksEnter.getName() + ": Thats how you kill an OutRaged Bish!! (KS Ended :"+oneWithKS.getName()+")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }else if(kSLevel == 10){
            announceBigKillStreakToAll(ksEnter.getName() + " THE TRUE PVP FIGHTER HAS BEEN TAKEN DOWN!!! (KS Ended :"+oneWithKS.getName()+")");
            oneWithKS.setKSLevel(0);
            log.info(EventSystem.FFA_ANNOUNCER_NAME + " " + oneWithKS.getName() + "'s killing spree has been ended by " + ksEnter.getName());
        }

    }

    public void giveSomeHelp(Player winner){
        winner.getLifeStats().setCurrentHp(winner.getLifeStats().getCurrentHp() + EventSystem.FFA_KILL_BONUS_HP);
        winner.getLifeStats().setCurrentMp(winner.getLifeStats().getCurrentMp() + EventSystem.FFA_KILL_BONUS_MP);
        winner.getCommonData().addDp(EventSystem.FFA_KILL_BONUS_DP);
    }

    public void KillingSpreeMsg(final String msg){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                if(object.getWorldId() == instance.getMapId() && object.isInFFA())
                PacketSendUtility.sendSys2Message(object, EventSystem.FFA_ANNOUNCER_NAME , msg);
            }
        });
    }

    public void announceBigKillStreakToAll(final String msg){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, EventSystem.FFA_ANNOUNCER_NAME , msg);
            }
        });
    }

    public void announceNormalKillMsg(final Player winner,final Player loser, final String msg){
        instance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, EventSystem.FFA_ANNOUNCER_NAME, winner.getName() + " " + msg + " " + loser.getName());
            }
        });
    }

    public void announceAllInFFA(final String msg){
        instance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, EventSystem.FFA_ANNOUNCER_NAME, msg);
            }
        });
    }
}
