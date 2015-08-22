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
package com.aionemu.gameserver.services;


import java.util.ArrayList;
import java.util.List;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.templates.arcadeupgrade.ArcadeTab;
import com.aionemu.gameserver.model.templates.arcadeupgrade.ArcadeTabItemList;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPGRADE_ARCADE;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Raziel
 */
public class ArcadeUpgradeService {

	private int[] experience = new int[8];
	private int[] expReward = new int[4];

	public ArcadeUpgradeService() {
		experience[0] = 12;
		experience[1] = 36;
		experience[2] = 48;
		experience[3] = 60;
		experience[4] = 72;
		experience[5] = 84;
		experience[6] = 96;
		experience[7] = 100;

		expReward[0] = 0;
		expReward[1] = 4;
		expReward[2] = 6;
		expReward[3] = 8;
	}

	public static final ArcadeUpgradeService getInstance() {
		return SingletonHolder.instance;
	}

	public int getLevelForExp(int expValue) {
		int level = 1;
		for (int i = experience.length; i > 0; i--) {
			if (expValue >= experience[(i - 1)]) {
				level = i;
				break;
			}
		}
		if (level > experience.length)
			return experience.length;
		return level;
	}

	public int getRewardForLevel(int level) {
		int reward = 1;

		for (int i = expReward.length; i > 0; i--) {
			if (level >= expReward[(i - 1)]) {
				reward = i;
				break;
			}
		}
		if (reward > expReward.length)
			return expReward.length;
		return reward;
	}

	public void startArcadeUpgrade(Player player) {
		PacketSendUtility.sendPacket(player, new SM_UPGRADE_ARCADE());
	}

	public void showRewardList(Player player) {
		PacketSendUtility.sendPacket(player, new SM_UPGRADE_ARCADE(10));
	}

	public List<ArcadeTab> getTabs() {
		return DataManager.ARCADE_UPGRADE_DATA.getArcadeTabs();
	}

	public void tryArcadeUpgrade(Player player) {

		if(!EventsConfig.ENABLE_EVENT_ARCADE)
			return;

		Storage inventory = player.getInventory();

		if(player.getFrenzy() == 0){
			if(!inventory.decreaseByItemId(186000389, 1)){
				return;
			}
		}

		if (Rnd.get(1, 100) <= EventsConfig.EVENT_ARCADE_CHANCE) {
			player.setFrenzy(player.getFrenzy() + 8);
			PacketSendUtility.sendPacket(player, new SM_UPGRADE_ARCADE(3, true, player.getFrenzy()));
			PacketSendUtility.sendPacket(player, new SM_UPGRADE_ARCADE(4, getLevelForExp(player.getFrenzy())));
		}else{
			PacketSendUtility.sendPacket(player, new SM_UPGRADE_ARCADE(3, false, player.getFrenzy()));
			PacketSendUtility.sendPacket(player, new SM_UPGRADE_ARCADE(5));
			player.setFrenzy(0);
		}
	}

	public void getReward(Player player) {
		if(!EventsConfig.ENABLE_EVENT_ARCADE)
			return;

		int frenzyLevel = getLevelForExp(player.getFrenzy());

		int rewardLevel = getRewardForLevel(frenzyLevel);

		List<ArcadeTabItemList> rewardList = new ArrayList<ArcadeTabItemList>();

		for (ArcadeTab arcadetab : getTabs()){
			if(rewardLevel >= arcadetab.getId()){
				for (ArcadeTabItemList arcadetabitem : arcadetab.getArcadeTabItems()){
					rewardList.add(arcadetabitem);
				}
			}
		}

		if(rewardList.size() > 0){
			int index = Rnd.get(0, rewardList.size() - 1);
			ArcadeTabItemList item = rewardList.get(index);

			ItemService.addItem(player, item.getItemId(), item.getNormalCount()> 0 ? item.getNormalCount(): item.getFrenzyCount());

			PacketSendUtility.sendPacket(player, new SM_UPGRADE_ARCADE(6, item));
			player.setFrenzy(0);
		}
	}

	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder {
		protected static final ArcadeUpgradeService instance = new ArcadeUpgradeService();
	}
}