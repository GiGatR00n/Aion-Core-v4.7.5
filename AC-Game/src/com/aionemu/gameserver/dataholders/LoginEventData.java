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
package com.aionemu.gameserver.dataholders;

import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javolution.util.FastMap;

import com.aionemu.gameserver.model.templates.login_event.LoginRewardTemplate;

/**
 * 
 * @author Ranastic
 *
 */
@XmlRootElement(name = "login_rewards")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginEventData {
	
	@XmlElement(name = "login_reward")
	private List<LoginRewardTemplate> login_reward;
	
	@XmlTransient
	private FastMap<Integer, LoginRewardTemplate> reward = new FastMap<Integer, LoginRewardTemplate>();
	
	@XmlTransient
	private THashMap<Integer, LoginRewardTemplate> activeEvents = new THashMap<Integer, LoginRewardTemplate>();
	
	@XmlTransient
	private THashMap<Integer, LoginRewardTemplate> allEvents = new THashMap<Integer, LoginRewardTemplate>();
	
	@XmlTransient
	private int counter = 0;
	
	/**
	 * @param u  
	 * @param parent 
	 */
	void afterUnmarshal(Unmarshaller u, Object parent) {
		counter = 0;
		activeEvents.clear();
		allEvents.clear();
		Set<Integer> ae = new HashSet<Integer>();
		for (LoginRewardTemplate template : login_reward) {
			if (ae.contains(template.getId()) && template.isActive()) {
				activeEvents.put(template.getId(), new LoginRewardTemplate());
				counter++;
			}
			allEvents.put(template.getId(), template);
			reward.put(template.getId(), new LoginRewardTemplate());
		}
	}
	
	public int size() {
		return reward.size();
	}

	public FastMap<Integer, LoginRewardTemplate> getloginTemplate() {
		return reward;
	}
	
	public List<LoginRewardTemplate> getAllEvents() {
		List<LoginRewardTemplate> result = new ArrayList<LoginRewardTemplate>();
		synchronized (allEvents) {
			result.addAll(allEvents.values());
		}

		return result;
	}
	
	public List<LoginRewardTemplate> getActiveEvents() {
		List<LoginRewardTemplate> result = new ArrayList<LoginRewardTemplate>();
		synchronized (activeEvents) {
			result.addAll(activeEvents.values());
		}

		return result;
	}
	
	public boolean Contains(int eventId) {
		return activeEvents.containsKey(eventId);
	}
	
	public LoginRewardTemplate getLoginRewardTemplateById(int id) {
		return reward.get(id);
	}
}