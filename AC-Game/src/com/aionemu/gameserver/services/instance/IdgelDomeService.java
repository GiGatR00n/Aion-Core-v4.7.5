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
package com.aionemu.gameserver.services.instance;

import java.util.Iterator;

import javolution.util.FastList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.AutoGroupConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AUTO_GROUP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.AutoGroupService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;


/**
 * @author GiGatR00n v4.7.5.x
 */
public class IdgelDomeService {

	/*
	 * Used to logs information.
	 */
	private static final Logger log = LoggerFactory.getLogger(IdgelDomeService.class);
	
	/**
	 * <Idgle Dome> Entry Level: 61-65
	 */
	private static final byte minlevel = 60, maxlevel = 66;

	// Determines whether users can still register for running instance?
	private boolean registerAvailable;
	
	// Determines whether the given player is already registered for instance?
	private FastList<Integer> playersWithCooldown = new FastList<Integer>();

	// MaskId for Idgel Dome Instance.
	public static final int maskId = 111;
	
	public static final int InstanceMapId = 301310000;
	
    /**
     * instantiate class 
     */
    private static class SingletonHolder {
        protected static final IdgelDomeService instance = new IdgelDomeService();
    }

    public static IdgelDomeService getInstance() {
        return SingletonHolder.instance;
    }
    
    /**
     * Schedules the Idgel Dome Instance to be launched at the specified Cron-Time.
     */
    public void start() {
        String[] times = AutoGroupConfig.IDGELDOME_TIMES.split("\\|");
        for (String cron : times) {
            CronService.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                	startRegistration();
                }
            }, cron);
            log.info("Scheduled Idgle Dome Instance based on cron expression: " + cron + " Duration: " + AutoGroupConfig.IDGELDOME_TIMER + " in minutes");
        }
    }
    
    private void startRegistration() 
    {
        registerAvailable = true;
        ScheduleUnregistration();
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            Player player = iter.next();
            if (player.getLevel() > minlevel && player.getLevel() < maxlevel) {
            	if (!isInInstance(player)) {
                    PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(maskId, SM_AUTO_GROUP.wnd_EntryIcon));
                    // You can now participate in the Idgel Dome battle.
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_OPEN_IDGEL_DOME);
            	}
            }
        }
    }     
    
    private void ScheduleUnregistration() 
    {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                registerAvailable = false;
                playersWithCooldown.clear();
                AutoGroupService.getInstance().unRegisterInstance(maskId);
                Iterator<Player> iter = World.getInstance().getPlayersIterator();
                while (iter.hasNext()) {
                    Player player = iter.next();
                    if (player.getLevel() > minlevel) {
                        PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(maskId, SM_AUTO_GROUP.wnd_EntryIcon, true));
                    }
                }
            }
        }, AutoGroupConfig.IDGELDOME_TIMER * 60 * 1000);
    }    
    
    public byte getMinLevel() {
    	return minlevel;
    }

    public byte getMaxLevel() {
    	return maxlevel;
    }
    
    public boolean isIdgleDomeAvailable() {
        return registerAvailable;
    }
    
    public void addCoolDown(Player player) {
        playersWithCooldown.add(player.getObjectId());
    }

    public boolean hasCoolDown(Player player) {
        return playersWithCooldown.contains(player.getObjectId());
    }
    
    public void showWindow(Player player, byte instanceMaskId) {
        if (!playersWithCooldown.contains(player.getObjectId())) {
            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(instanceMaskId));
        }
    }
    
    private boolean isInInstance(Player player) {
    	if (player.isInInstance()) {
    		return true;
    	}
        return false;
    }

    public boolean canPlayerJoin(Player player) {
		if (registerAvailable && player.getLevel() > minlevel && player.getLevel() < maxlevel && !hasCoolDown(player) && !isInInstance(player)) {
			 return true;
		}
		return false;
    }
}
