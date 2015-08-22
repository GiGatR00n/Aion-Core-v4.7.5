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
package com.aionemu.gameserver.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javolution.util.FastMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.dao.PlayerLoginRewardDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.LoginReward;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.templates.login_event.LoginRewardTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LOGIN_REWARD;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Ranastic
 */

public class LoginEventService {
	
	private static final Logger log = LoggerFactory.getLogger(LoginEventService.class);
	
	private int loginCount = 0;
	private Timestamp nextCountTime;
	List<LoginRewardTemplate> newEvents = new ArrayList<LoginRewardTemplate>();
	List<LoginRewardTemplate> allEvents = DataManager.LOGIN_EVENT_DATA.getAllEvents();
	FastMap<Integer, LoginRewardTemplate> template = new FastMap<Integer, LoginRewardTemplate>();
	private List<LoginRewardTemplate> activeEvents;
	
	
	private static class SingletonHolder {
		private static final LoginEventService INSTANCE = new LoginEventService();
	}
	
	public static LoginEventService getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public void start() {
		if (EventsConfig.LOGIN_REWARD_ENABLED) {
			log.info("Loading Login Events...");
			checkEvents();
		} else {
			log.info("Login Events disabled.");
		}
	}
	
	private LoginEventService() {
		activeEvents = Collections.synchronizedList(DataManager.LOGIN_EVENT_DATA.getActiveEvents());
	}
	
	public void onPlayerLogin(Player player) {
		if (!EventsConfig.LOGIN_REWARD_ENABLED) return;
		synchronized (activeEvents) {
			LoginReward recipelist = null;
			recipelist = DAOManager.getDAO(PlayerLoginRewardDAO.class).load(player.getObjectId());
			for (LoginRewardTemplate et : activeEvents) {
				if (et.isActive()) {
					PlayerLoginRewardDAO playerDAO = DAOManager.getDAO(PlayerLoginRewardDAO.class);
					nextCountTime = playerDAO.getNextLoginCountbyObjAndActivatedEventId(player.getObjectId(), et.getId());
					int loginCountDB = playerDAO.getLoginCountByObjAndActivatedEventId(player.getObjectId(), et.getId());
					if (nextCountTime == null) {
						nextCountTime = autoAddTimeColumn();
					}
				  if (!checkOnlineDate(player.getCommonData())) {
						setLoginCount(loginCountDB);
					} else {
						setNextRepeatTime(countNextRepeatTime());
						setLoginCount(loginCountDB++);
						PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_NEW_PASSPORT_AVAIBLE);
					}
					LoginReward lr = new LoginReward(et.getId(), getLoginCount(), countNextRepeatTime());
					player.setLoginReward(lr);
					if (recipelist.isLoginRewardPresent(et.getId())) {
						if (!checkOnlineDate(player.getCommonData())) return;
					} else if (!recipelist.isLoginRewardPresent(et.getId()) && et.getId() != 0) {
						player.getLoginReward().addLoginReward(player.getObjectId(), et.getId(), getLoginCount(), countNextRepeatTime());
					}
					addLoginReward(player, et.getId());
					PacketSendUtility.sendPacket(player, new SM_LOGIN_REWARD(player, template.values()));
				}
			}
		}
	}
	
	private boolean checkOnlineDate(PlayerCommonData pcd) {
	    long lastOnline = pcd.getLastOnline().getTime();
	    long secondsOffline = (System.currentTimeMillis() / 1000) - lastOnline / 1000;
	    double hours = secondsOffline / 3600d;
	    if (hours > 24) hours = 24;
	    return (hours == 24 ? true : false);
	}
	
	/**
	 * @param timestamp  
	 */
	public void getReward(Player player, int timestamp, List<Integer> passportId) {
		synchronized (activeEvents) {
			for (Integer i : passportId) {
				for (LoginRewardTemplate et : activeEvents) {
					if (et.getId() == i) {
						ItemService.addItem(player, et.getReward().getItemId(), et.getReward().getCount());
						PacketSendUtility.sendPacket(player, new SM_LOGIN_REWARD(player, template.values(), et.getId()));
					}	
				}
			}
		}
	}
	
	public static boolean addLoginReward(Player player, int eventId) {
		LoginRewardTemplate template = null;
		template = DataManager.LOGIN_EVENT_DATA.getLoginRewardTemplateById(eventId);
		if (template == null) return false;
		player.getLoginReward().addLoginReward(player, template);
		return true;
	}
	
	private static Timestamp autoAddTimeColumn() {
		DateTime now = DateTime.now();
		DateTime repeatDate = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 9, 0, 0);
		if (now.isAfter(repeatDate)) {
			repeatDate = repeatDate.plusHours(24);
		}
		return new Timestamp(repeatDate.getMillis());
	}
	
	private static Timestamp countNextRepeatTime() {
		DateTime now = DateTime.now();
		DateTime repeatDate = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 9, 0, 0);
		if (now.isAfter(repeatDate)) {
			repeatDate = repeatDate.plusHours(24);
		}
		return new Timestamp(repeatDate.getMillis());
	}
	
	private void checkEvents() {
		for (LoginRewardTemplate et : allEvents) {
			if (et.isActive()) {
				newEvents.add(et);
				template.put(et.getId(), et);
			}
		}
		
		synchronized (activeEvents) {
			for (LoginRewardTemplate et : activeEvents) {
				if (et.isExpired() || !DataManager.LOGIN_EVENT_DATA.Contains(et.getId())) {
				}
			}
			activeEvents.clear();
			activeEvents.addAll(newEvents);
		}
		newEvents.clear();
		allEvents.clear();
		log.info("Loaded " + activeEvents.size() + " Login Events.");
	}
	
	public List<LoginRewardTemplate> getActiveEventIds() {
		return activeEvents;
	}
	
	public Timestamp getNextRepeatTime() {
		return nextCountTime;
	}
	
	public void setNextRepeatTime(Timestamp time) {
		this.nextCountTime = time;
	}	
	
	public int getLoginCount() {
		return loginCount;
	}
	
	public void setLoginCount(int count) {
		this.loginCount = count;
	}
}