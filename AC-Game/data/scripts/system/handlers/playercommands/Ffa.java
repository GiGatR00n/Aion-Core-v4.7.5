/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
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

package playercommands;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kill3r
 */
public class Ffa extends PlayerCommand {

    public Ffa() {
        super("ffa");
    }

    private WorldMapInstance instance = null;
    protected Map<Integer, WorldPosition> previousLocations = new HashMap<Integer, WorldPosition>();
    private final int worldId = EventSystem.EVENTSYSTEM_FFAMAP;
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

    @Override
    public void execute(final Player player, String... params) {
        if (params.length < 1 || params == null) {
            onFail(player, null);
            return;
        }

        //if(player.getAccessLevel() < 1){
            //PacketSendUtility.sendMessage(player, "Will Open Soon");
            //return;
        //}

        boolean FFAGate = true;
        if (params[0].equals("close")){
            if (player.getAccessLevel() >= 3){
                FFAGate = false;
                PacketSendUtility.sendMessage(player, "FFA Gate has been closed!");
                sendAll(false);
            }
        }
        if (params[0].equals("open")){
            if (player.getAccessLevel() >= 3){
                FFAGate = true;
                PacketSendUtility.sendMessage(player, "FFA Gate has been Opened!");
                sendAll(true);
            }
        }

        if (!FFAGate){
            PacketSendUtility.sendMessage(player, "FFA is currently closed!");
            return;
        }

        if (params[0].equals("enter")) {
            if (player.isInTeam()) {
                PacketSendUtility.sendMessage(player, "You are in team, you cannot enter !");
                return;
            }

            if (player.isInPrison()) {
                PacketSendUtility.sendMessage(player, "You cannot use the command inside Prison !");
                return;
            }

            if (player.isInFFA()) {
                PacketSendUtility.sendMessage(player, "You are already in FFA !");
                return;
            }

            player.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
            player.getEffectController().updatePlayerEffectIcons();
            player.getEffectController().broadCastEffects();
            PacketSendUtility.sendMessage(player, "You will be ported to FFA after 10 Seconds.");
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    player.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                    player.getEffectController().updatePlayerEffectIcons();
                    player.getEffectController().broadCastEffects();
                    player.setInFFA(true);
                    TeleportIn(player);
                }
            }, 10000);
            return;
        }

        if (params[0].equals("leave")) {
            player.getEffectController().setAbnormal(AbnormalState.SLEEP.getId());
            player.getEffectController().updatePlayerEffectIcons();
            player.getEffectController().broadCastEffects();
            PacketSendUtility.sendMessage(player, "You will be ported out of FFA in 10 Seconds.");
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    player.getEffectController().unsetAbnormal(AbnormalState.SLEEP.getId());
                    player.getEffectController().updatePlayerEffectIcons();
                    player.getEffectController().broadCastEffects();
                    TeleportOut(player);
                }
            }, 10000);
            return;
        }

        if (params[0].equals("info")) {
            PacketSendUtility.sendMessage(player, "Total Number of Players Currently in FFA: " + getPlayerCount() + "\n.ffa enter\n.ffa leave\n.ffa info");
        }
    }

    public void sendAll(boolean Switch){
        if (Switch){
            World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player player) {
                    PacketSendUtility.sendSys2Message(player, "FFA HeadQuarters", "FFA Has been Opened!");
                }
            });
        }else{
            World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player player) {
                    PacketSendUtility.sendSys2Message(player, "FFA HeadQuarters", "FFA Has been CLOSED!");
                }
            });
        }
    }

    public Point3D randomSpawn() {
        int pos = Rnd.get(positions.length - 1);
        return positions[pos];
    }

    public void TeleportIn(Player player) {

        if (instance == null) {
            instance = createInstance();
            if(EventSystem.FFA_RETURN_TO_PREVLOCK){
                previousLocations.put(player.getObjectId(), player.getPosition());
            }
            Point3D loc = randomSpawn();
            TeleportService2.teleportTo(player, worldId, instance.getInstanceId(), (float) loc.getX() , (float) loc.getY(), (float) loc.getZ());
            gearUp(player);
        } else {
            if(EventSystem.FFA_RETURN_TO_PREVLOCK){
                previousLocations.put(player.getObjectId(), player.getPosition());
            }
            Point3D loc = randomSpawn();
            TeleportService2.teleportTo(player, worldId, instance.getInstanceId(), (float) loc.getX() , (float) loc.getY(), (float) loc.getZ());
            gearUp(player);
        }
    }


    public void TeleportOut(Player player) {
        if (player.isInFFA()) {
            if(EventSystem.FFA_RETURN_TO_PREVLOCK){
                WorldPosition pos = previousLocations.get(player.getObjectId());
                TeleportService2.teleportTo(player, pos.getMapId(), pos.getX(), pos.getY(), pos.getZ(), pos.getHeading());
                previousLocations.remove(player.getObjectId());
            }else{
                if(player.getRace() == Race.ASMODIANS){
                    TeleportService2.teleportTo(player, 120010000, 1316.4557f, 1425.6559f, 209.09084f);
                }else{
                    TeleportService2.teleportTo(player, 110010000, 1440.6306f, 1575.4629f, 572.8752f);
                }
            }
            gearDown(player);
        } else {
            PacketSendUtility.sendWhiteMessageOnCenter(player, "You're not a member of FFA.");
        }
    }

    protected WorldMapInstance createInstance(){
        WorldMapInstance instance = InstanceService.getNextAvailableInstance(worldId);
        return instance;
    }

    public void gearUp(Player player) {
        player.getController().cancelCurrentSkill();
        player.setSpecialKills(0);
        player.setInFFA(true);
        player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
        player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.BUFF);
        player.getCommonData().setDp(0);
    }


    public void gearDown(Player player) {
        player.setInFFA(false);
        player.getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.HP, player.getLifeStats().getMaxHp() + 1);
        player.getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.MP, player.getLifeStats().getMaxMp() + 1);
        player.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
        player.getKnownList().clear();
        player.getKnownList().doUpdate();
    }

    public int getPlayerCount(){
        if(instance == null){
            return 0;
        }else{
            return instance.getPlayersInside().size();
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, ":::::::USAGE:::::::\n.ffa enter -- Joins the FFA\n.ffa leave -- Leaves the FFA\n.ffa info -- shows how many online + this Info");
    }
}
