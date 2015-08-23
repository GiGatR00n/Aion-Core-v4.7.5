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
package ai;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AI2Request;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dao.PlayerBindPointDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TribeClass;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.BindPointPosition;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.BindPointTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ATracer
 */
@AIName("resurrect")
public class ResurrectAI2 extends NpcAI2 {

    private static Logger log = LoggerFactory.getLogger(ResurrectAI2.class);

    @Override
    protected void handleDialogStart(Player player) {
        BindPointTemplate bindPointTemplate = DataManager.BIND_POINT_DATA.getBindPointTemplate(getNpcId());
        Race race = player.getRace();
        if (bindPointTemplate == null) {
            log.info("There is no bind point template for npc: " + getNpcId());
            return;
        }

        if (player.getBindPoint() != null
                && player.getBindPoint().getMapId() == getPosition().getMapId()
                && MathUtil.getDistance(player.getBindPoint().getX(), player.getBindPoint().getY(), player.getBindPoint().getZ(),
                getPosition().getX(), getPosition().getY(), getPosition().getZ()) < 20) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ALREADY_REGISTER_THIS_RESURRECT_POINT);
            return;
        }

        WorldType worldType = player.getWorldType();
        if (!CustomConfig.ENABLE_CROSS_FACTION_BINDING && !getTribe().equals(TribeClass.FIELD_OBJECT_ALL)) {
            if ((!getRace().equals(Race.NONE) && !getRace().equals(race))
                    || (race.equals(Race.ASMODIANS) && getTribe().equals(TribeClass.FIELD_OBJECT_LIGHT))
                    || (race.equals(Race.ELYOS) && getTribe().equals(TribeClass.FIELD_OBJECT_DARK))) {
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_BINDSTONE_CANNOT_FOR_INVALID_RIGHT(player.getCommonData().
                        getOppositeRace().toString()));
                return;
            }
        }
        if (worldType == WorldType.PRISON) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_FAR_FROM_NPC);
            return;
        }
        bindHere(player, bindPointTemplate);
    }

    private void bindHere(Player player, final BindPointTemplate bindPointTemplate) {

        String price = Integer.toString(bindPointTemplate.getPrice());
        AI2Actions.addRequest(this, player, SM_QUESTION_WINDOW.STR_ASK_REGISTER_RESURRECT_POINT, 0, new AI2Request() {
            @Override
            public void acceptRequest(Creature requester, Player responder) {
                // check if this both creatures are in same world
                if (responder.getWorldId() == requester.getWorldId()) {
                    // check enough kinah
                    if (responder.getInventory().getKinah() < bindPointTemplate.getPrice()) {
                        PacketSendUtility.sendPacket(responder,
                                SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_NOT_ENOUGH_FEE);
                        return;
                    } else if (MathUtil.getDistance(requester, responder) > 5) {
                        PacketSendUtility.sendPacket(responder, SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_FAR_FROM_NPC);
                        return;
                    }

                    BindPointPosition old = responder.getBindPoint();
                    BindPointPosition bpp = new BindPointPosition(requester.getWorldId(), responder.getX(), responder.getY(),
                            responder.getZ(), responder.getHeading());
                    bpp.setPersistentState(old == null ? PersistentState.NEW : PersistentState.UPDATE_REQUIRED);
                    responder.setBindPoint(bpp);
                    if (DAOManager.getDAO(PlayerBindPointDAO.class).store(responder)) {
                        responder.getInventory().decreaseKinah(bindPointTemplate.getPrice());
                        TeleportService2.sendSetBindPoint(responder);
                        PacketSendUtility.broadcastPacket(responder, new SM_LEVEL_UPDATE(responder.getObjectId(), 2, responder.getCommonData().getLevel()), true);
                        PacketSendUtility.sendPacket(responder, SM_SYSTEM_MESSAGE.STR_DEATH_REGISTER_RESURRECT_POINT("")); //TODO
                        old = null;
                    } else // if any errors happen, left that player with old bind point
                    {
                        responder.setBindPoint(old);
                    }
                }
            }
        }, price);
    }
}
