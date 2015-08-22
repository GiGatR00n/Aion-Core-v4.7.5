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

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.arcadeupgrade.ArcadeTab;
import com.aionemu.gameserver.model.templates.arcadeupgrade.ArcadeTabItemList;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.ArcadeUpgradeService;

/**
 * @author Raziel
 */
public class SM_UPGRADE_ARCADE extends AionServerPacket {

	private int action;
	private int showicon = 1;
	private int frenzy = 0;
	private int frenzyLevel = 1;
	private boolean success = false;
	private ArcadeTabItemList item;

	public SM_UPGRADE_ARCADE(boolean showicon) {
		this.action = 0;
		this.showicon = showicon? 1 : 0;
	}
	
	public SM_UPGRADE_ARCADE() {
		this.action = 1;
	}
	
	public SM_UPGRADE_ARCADE(int action) {
		this.action = action;
	}
	
	public SM_UPGRADE_ARCADE(int action, boolean success, int frenzy) {
		this.action = action;
		this.success = success;
		this.frenzy = frenzy;
	}
	
	public SM_UPGRADE_ARCADE(int action, int frenzyLevel) {
		this.action = action;
		this.frenzyLevel = frenzyLevel;
	}
	
	public SM_UPGRADE_ARCADE(int action, ArcadeTabItemList item) {
		this.action = action;
		this.item = item;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		Player player = con.getActivePlayer();
			writeC(action);

			switch(action)
			{
				case 0://show icon
					writeD(showicon);
				break;
				case 1: //show start upgrade arcade info
					writeD(64519);//SessionId
					writeD(0);//frenzy meter
					writeD(1);
					writeD(4);
					writeD(6);
					writeD(8);
					writeD(8);//max upgrade
					writeH(272);
					writeS("success_weapon01");
					writeS("success_weapon01");
					writeS("success_weapon01");
					writeS("success_weapon02");
					writeS("success_weapon02");
					writeS("success_weapon03");
					writeS("success_weapon03");
					writeS("success_weapon04");
				break;
				case 3: //try result
					writeC(success ? 1 : 0);//1 success - 0 fail
					writeD(frenzy > 100? 100: frenzy);//frenzyPoints
				break;
				case 4: //try result
					writeD(frenzyLevel);//upgradeLevel
				break;
				case 5: //show fail
					writeD(1);//upgradeLevel
					writeC(0);//canResume? 1 yes - 0 no
					writeD(0);//needed Arcade Token
					writeD(0);//unk
				break;
				case 6: //show reward icon
					writeD(item.getItemId());//templateId
					writeD(item.getNormalCount() > 0 ? item.getNormalCount() : item.getFrenzyCount());//itemCount
					writeD(0);//unk
				break;
				case 10: //show reward list
					List<ArcadeTab> arcadeTabs = ArcadeUpgradeService.getInstance().getTabs();
	
					for (ArcadeTab arcadetab : arcadeTabs){
						writeC(arcadetab.getArcadeTabItems().size());
					}
	
					for (ArcadeTab arcadetab : arcadeTabs){
						for (ArcadeTabItemList arcadetabitem : arcadetab.getArcadeTabItems()){
							writeD(arcadetabitem.getItemId());
							writeD(arcadetabitem.getNormalCount());
							writeD(0);
							writeD(arcadetabitem.getFrenzyCount());
							writeD(0);
						}
					}
				break;
			}
	}
}