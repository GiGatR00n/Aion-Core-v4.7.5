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
package com.aionemu.gameserver.model.gameobjects.player;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerLoginRewardDAO;
import com.aionemu.gameserver.model.templates.login_event.LoginRewardTemplate;

/**
 * @author Ranastic
 */

public class LoginReward
{
	private int eventId;
	private int loginCount;
	private Timestamp nextLoginCount;
	private Set<Integer> loginRewardList = new HashSet<Integer>();
	final Logger log = LoggerFactory.getLogger(LoginReward.class);
	
	public LoginReward(int eventId, int loginCount, Timestamp nextLoginCount) {
		this.eventId = eventId;
		this.loginCount = loginCount;
		this.nextLoginCount = nextLoginCount;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public int getLoginCount() {
		return loginCount;
	}
	
	public Timestamp getNextLoginCount() {
		return nextLoginCount;
	}
	
	public void setNextLoginCount(Timestamp time) {
		nextLoginCount = time;
	}
	
	public LoginReward(HashSet<Integer> loginRewardList) {
		this.loginRewardList = loginRewardList;
	}
	
	public LoginReward() {}
	
	public Set<Integer> getRecipeList() {
		return loginRewardList;
	}
	
	public void addLoginReward(Player player, LoginRewardTemplate template) {
		int eventId = template.getId();
		if (!player.getLoginReward().isLoginRewardPresent(eventId) && eventId != 0) {
			if (DAOManager.getDAO(PlayerLoginRewardDAO.class).addLoginReward(player.getObjectId(), eventId, getLoginCount(), getNextLoginCount())) {
				loginRewardList.add(eventId);
			}
		}
	}
	
	public void addLoginReward(int playerId, int eventId, int loginCount, Timestamp nextLoginCount) {
		if (DAOManager.getDAO(PlayerLoginRewardDAO.class).addLoginReward(playerId, eventId, loginCount, nextLoginCount)) {
			loginRewardList.add(eventId);
		}
	}
	
	public void deleteLoginReward(Player player, int eventId, int loginCount, Timestamp nextLoginCount) {
		if (loginRewardList.contains(eventId)) {
			if (DAOManager.getDAO(PlayerLoginRewardDAO.class).delLoginReward(player.getObjectId(), eventId, loginCount, nextLoginCount)) {
				loginRewardList.remove(eventId);
			}
		}
	}
	
	public boolean isLoginRewardPresent(int eventId) {
		return loginRewardList.contains(eventId);
	}
	
	public int size() {
		return this.loginRewardList.size();
	}
}