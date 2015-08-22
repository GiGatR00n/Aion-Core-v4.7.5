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
package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dao.PlayerLoginRewardDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.login_event.LoginRewardTemplate;
import com.aionemu.gameserver.model.templates.login_event.LoginType;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Ranastic
 */

public class SM_LOGIN_REWARD extends AionServerPacket
{
	Logger log = LoggerFactory.getLogger(SM_LOGIN_REWARD.class);
	private Calendar calendar = Calendar.getInstance();
	private Calendar creationDate = Calendar.getInstance();
	private Collection<LoginRewardTemplate> rewardtemp;
	private Player player;
	private int finishedId;
	
	public SM_LOGIN_REWARD(Player player, Collection<LoginRewardTemplate> collection) {
		this.player = player;
		this.rewardtemp = collection;
	}
	
	public SM_LOGIN_REWARD(Player player, Collection<LoginRewardTemplate> collection, int id) {
		this.player = player;
		this.rewardtemp = collection;
		this.finishedId = id;
	}
	
	@Override
	protected void writeImpl(AionConnection con) {
		PlayerDAO playerDAO = DAOManager.getDAO(PlayerDAO.class);
		PlayerLoginRewardDAO plrDAO = DAOManager.getDAO(PlayerLoginRewardDAO.class);
		creationDate.setTime(new Date(playerDAO.getCharacterCreationDateId(player.getObjectId()).getTime()));
		writeH(creationDate.get(Calendar.YEAR));
		writeH(creationDate.get(Calendar.MONTH)+1);
		writeH(creationDate.get(Calendar.DAY_OF_MONTH));
		writeH(rewardtemp.size());
		for (LoginRewardTemplate a : rewardtemp) {
			if (a.isActive()) {
				if (a.getId() == finishedId)
					writeD(0);
				else
					writeD(a.getId());
				
				if (a.getAttendNum() == calendar.get(Calendar.DAY_OF_MONTH) && a.getType() == LoginType.CUMULATIVE)
					writeD(0);
				else
					writeD(plrDAO.getLoginCountByObjAndActivatedEventId(player.getObjectId(), a.getId()));
				
				writeD(a.getType().getId());
				writeD((int) player.getCommonData().getLastOnline().getTime() / 1000);
			}
		}
	}
}