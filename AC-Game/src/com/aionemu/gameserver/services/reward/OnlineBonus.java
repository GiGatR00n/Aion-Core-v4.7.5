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
package com.aionemu.gameserver.services.reward;

import com.aionemu.gameserver.configs.main.MembershipConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Eloann
 * @Reworked Kill3r
 */
public class OnlineBonus {

    private static final Logger log = LoggerFactory.getLogger(OnlineBonus.class);

    private OnlineBonus() {
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player object) {
                        int time = MembershipConfig.ONLINE_BONUS_TIME;
                        try {
                            if (object.getInventory().isFull()) {
                                log.warn("[OnlineBonus] Player " + object.getName() + " tried to receive item with full inventory.");
                            } else {
                                ItemService.addItem(object, MembershipConfig.ONLINE_BONUS_ITEM, MembershipConfig.ONLINE_BONUS_COUNT);
                                if(MembershipConfig.ONLINE_BONUS_ABYSS_ENABLE){
                                    AbyssPointsService.addAp(object, MembershipConfig.ONLINE_BONUS_AP);
                                    AbyssPointsService.addGp(object, MembershipConfig.ONLINE_BONUS_GP);
                                    PacketSendUtility.sendMessage(object, "[Online Bonus Sevice]: You've Played " + time + " Minutes. You earn a Bonus Item! :)");
                                }else{
                                    PacketSendUtility.sendMessage(object, "[Online Bonus Sevice]: You've Played " + time + " Minutes. You earn a Bonus Item and Some AP! :)");
                                }
                            }
                        } catch (Exception ex) {
                            log.error("Exception during event rewarding of player " + object.getName(), ex);
                        }
                    }
                });
            }
        }, MembershipConfig.ONLINE_BONUS_TIME * 60000, MembershipConfig.ONLINE_BONUS_TIME * 60000);
    }

    public void playerLoggedIn(Player player) {
        if (MembershipConfig.ONLINE_BONUS_ENABLE) {
            player.setOnlineBonusTime(System.currentTimeMillis());
        }
    }

    public void playerLoggedOut(Player player) {
    }

    public static OnlineBonus getInstance() {
        return SingletonHolder.instance;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final OnlineBonus instance = new OnlineBonus();
    }
}
