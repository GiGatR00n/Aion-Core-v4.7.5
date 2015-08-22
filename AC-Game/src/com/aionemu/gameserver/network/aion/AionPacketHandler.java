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
package com.aionemu.gameserver.network.aion;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.administration.DeveloperConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;

/**
 * @author -Nemesiss-
 * @author Luno
 * @author GiGatR00n
 */
public class AionPacketHandler {

    /**
     * logger for this class
     */
    private static final Logger log = LoggerFactory.getLogger(AionPacketHandler.class);
    private Map<Integer, AionClientPacket> packetsPrototypes = new HashMap<Integer, AionClientPacket>();

    /**
     * Reads one packet from given ByteBuffer
     *
     * @param data
     * @param client
     * @return AionClientPacket object from binary data
     */
    public AionClientPacket handle(ByteBuffer data, AionConnection client) {
        State state = client.getState();
        int id = data.getShort() & 0xffff;
        /* Second opcodec. */
        data.position(data.position() + 3);

        return getPacket(state, id, data, client);
    }

    public void addPacketPrototype(AionClientPacket packetPrototype) {
        packetsPrototypes.put(packetPrototype.getOpcode(), packetPrototype);
    }

    private AionClientPacket getPacket(State state, int id, ByteBuffer buf, AionConnection con) {
        AionClientPacket prototype = packetsPrototypes.get(id);

        if (prototype == null) {
            unknownPacket(state, id, buf);
            return null;
        }

        /**
         * Display Packets Name + Hex-Bytes in Chat Window
         */
        Player player = con.getActivePlayer();
        
        if (con.getState().equals(State.IN_GAME) && player != null && player.getAccessLevel() >= DeveloperConfig.SHOW_PACKETS_INCHAT_ACCESSLEVEL) {
        	if (isPacketFilterd(DeveloperConfig.FILTERED_PACKETS_INCHAT, getClientPacketName(id))) {
            	if (DeveloperConfig.SHOW_PACKET_BYTES_INCHAT) {            	
                	String PckName = String.format("0x%04X : %s", id, getClientPacketName(id));
                	PacketSendUtility.sendMessage(player, "********************************************");
            		PacketSendUtility.sendMessage(player, PckName);
            		PacketSendUtility.sendMessage(player, Util.toHexStream(getByteBuffer(buf, DeveloperConfig.TOTAL_PACKET_BYTES_INCHAT)));
            		buf.position(5);
            		
                } else if (DeveloperConfig.SHOW_PACKET_NAMES_INCHAT) {
            		String PckName = String.format("0x%04X : %s", id, getClientPacketName(id));
                	PacketSendUtility.sendMessage(player, PckName);
                }
        	}
        }
        AionClientPacket res = prototype.clonePacket();
        res.setBuffer(buf);
        res.setConnection(con);

        if (con.getState().equals(State.IN_GAME) && con.getActivePlayer().getPlayerAccount().getMembership() == 10) {
            PacketSendUtility.sendMessage(con.getActivePlayer(), "0x" + Integer.toHexString(res.getOpcode()).toUpperCase() + " : " + res.getPacketName());
        }
        return res;
    }

    private boolean isPacketFilterd(String filterlist, String PacketName) {
    	
    	//If FilterList was empty, all packets will be shown.
    	if (filterlist == null || filterlist.equalsIgnoreCase("*")) return true;
    	
    	String[] Parts = null;
    	Parts = filterlist.trim().split(",");
    	
    	for (String p: Parts) {
    		if (p.trim().equalsIgnoreCase(PacketName)) {
    			return true;
    		}
    	}
    	return false;
    }

    private ByteBuffer getByteBuffer(ByteBuffer buf, int count) {
    	
    	count = (count <= buf.capacity()) ? count : buf.capacity();
    	ByteBuffer tmpBuffer = buf.asReadOnlyBuffer();
    	tmpBuffer.position(5);
    	tmpBuffer.limit(count);
    	
	   	 // Create an empty ByteBuffer with a Requested Capacity.
	   	ByteBuffer PckBuffer = ByteBuffer.allocate(count);
        try {
        	do 
	        	{
	    		PckBuffer.put(tmpBuffer.get());
	        	}
    		while (tmpBuffer.remaining() > 0);
        } catch (Exception e) {
        	//e.printStackTrace();
        }
        PckBuffer.position(0);
        return PckBuffer;
    }
       
    private String getClientPacketName(int id) {
    	switch (id) {
			case 344:
				return "CM_ABYSS_RANKING_LEGIONS";
			case 414:
				return "CM_ABYSS_RANKING_PLAYERS";
			case 226:
				return "CM_ATTACK";
			case 277:
				return "CM_BUY_ITEM";
			case 227:
				return "CM_CASTSPELL";
			case 376:
				return "CM_CHARACTER_LIST";
			case 436:
				return "CM_CHARACTER_PASSKEY";
			case 368:
				return "CM_CHAT_AUTH";
			case 253:
				return "CM_CHAT_MESSAGE_PUBLIC";
			case 295:
				return "CM_CHECK_MAIL_SIZE";
			case 439:
				return "CM_CHECK_MAIL_SIZE2";
			case 280:
				return "CM_DIALOG_SELECT";
			case 205:
				return "CM_EMOTION";
			case 170:
				return "CM_ENTER_WORLD";
			case 200:
				return "CM_EQUIP_ITEM";
			case 279:
				return "CM_CLOSE_DIALOG";
			case 751:
				return "CM_FIND_GROUP";
			case 332:
				return "CM_FRIEND_STATUS";
			case 203:
				return "CM_GM_BOOKMARK";
			case 386:
				return "CM_INSTANCE_INFO";
			case 375:
				return "CM_L2AUTH_LOGIN_CHECK";
			case 207:
				return "CM_LEGION";
			case 241:
				return "CM_LEGION_SEND_EMBLEM";
			case 210:
				return "CM_LEGION_SEND_EMBLEM_INFO";
			case 171:
				return "CM_LEVEL_READY";
			case 381:
				return "CM_LOOT_ITEM";
			case 415:
				return "CM_MAC_ADDRESS";
			case 412:
				return "CM_MAY_LOGIN_INTO_GAME";
			case 242:
				return "CM_MOVE";
			case 382:
				return "CM_MOVE_ITEM";
			case 206:
				return "CM_PING";
			case 345:
				return "CM_PRIVATE_STORE";
			case 346:
				return "CM_PRIVATE_STORE_NAME";
			case 339:
				return "CM_QUESTIONNAIRE";
			case 165:
				return "CM_QUIT";
			case 294:
				return "CM_SEND_MAIL";
			case 352:
				return "CM_SHOW_BLOCKLIST";
			case 278:
				return "CM_SHOW_DIALOG";
			case 392:
				return "CM_SHOW_FRIENDLIST";
			case 358:
				return "CM_SHOW_MAP";
			case 383:
				return "CM_SPLIT_ITEM";
			case 380:
				return "CM_START_LOOT";
			case 225:
				return "CM_TARGET_SELECT";
			case 209:
				return "CM_TELEPORT_DONE";
			case 374:
				return "CM_TELEPORT_SELECT";
			case 244:
				return "CM_TIME_CHECK";
			case 196:
				return "CM_TOGGLE_SKILL_DEACTIVATE";
			case 172:
				return "CM_UI_SETTINGS";
			case 396:
				return "CM_USE_CHARGE_SKILL";
			case 199:
				return "CM_USE_ITEM";
			case 194:
				return "CM_VERSION_CHECK";
			case 470:
				return "CM_HOTSPOT_TELEPORT";
			case 409:
				return "CM_SERVER_CHECK";
			case 442:
				return "CM_FAST_TRACK";
			case 362:
				return "CM_AUTO_GROUP";
			case 328:
				return "CM_BLOCK_ADD";
			case 329:
				return "CM_BLOCK_DEL";
			case 405:
				return "CM_BLOCK_SET_REASON";
			case 395:
				return "CM_BONUS_TITLE";
			case 401:
				return "CM_BREAK_WEAPONS";
			case 334:
				return "CM_CHANGE_CHANNEL";
			case 269:
				return "CM_CLIENT_COMMAND_ROLL";
			case 404:
				return "CM_COMPOSITE_STONES";
			case 377:
				return "CM_CREATE_CHARACTER";
			case 378:
				return "CM_DELETE_CHARACTER";
			case 274:
				return "CM_DELETE_QUEST";
			case 305:
				return "CM_FRIEND_ADD";
			case 306:
				return "CM_FRIEND_DEL";
			case 400:
				return "CM_FUSION_WEAPONS";
			case 245:
				return "CM_GATHER";
			case 444:
				return "CM_GET_HOUSE_BIDS";
			case 388:
				return "CM_HOUSE_OPEN_DOOR";
			case 750:
				return "CM_LEGION_WH_KINAH";
			case 369:
				return "CM_MACRO_CREATE";
			case 950:
				return "CM_GI_GAT_R0_0N";
			case 370:
				return "CM_MACRO_DELETE";
			case 748:
				return "CM_MANASTONE";
			case 166:
				return "CM_MAY_QUIT";
			case 243:
				return "CM_MOVE_IN_AIR";
			case 173:
				return "CM_OBJECT_SEARCH";
			case 249:
				return "CM_OPEN_STATICDOOR";
			case 248:
				return "CM_PET";
			case 49:
				return "CM_PING_INGAME";
			case 447:
				return "CM_PLACE_BID";
			case 289:
				return "CM_HOUSE_TELEPORT_BACK";
			case 353:
				return "CM_PLAYER_SEARCH";
			case 290:
				return "CM_PLAYER_STATUS_INFO";
			case 326:
				return "CM_QUEST_SHARE";
			case 202:
				return "CM_PLAYER_LISTENER";
			case 276:
				return "CM_QUESTION_RESPONSE";
			case 406:
				return "CM_RECONNECT_AUTH";
			case 167:
				return "CM_REVIVE";
			case 301:
				return "CM_TITLE_SET";
			case 407:
				return "CM_SHOW_BRAND";
			case 266:
				return "CM_GAMEGUARD";
			case 473:
				return "CM_ITEM_PURIFICATION";
			case 309:
				return "CM_FATIGUE_RECOVER";
			case 474:
				return "CM_LOGIN_REWARD";
			case 472:
				return "CM_UPGRADE_ARCADE";
			case 441:
				return "CM_FAST_TRACK_CHECK";
			case 394:
				return "CM_CHALLENGE_LIST";
			case 174:
				return "CM_CUSTOM_SETTINGS";
			case 308:
				return "CM_HOUSE_EDIT";
			case 746:
				return "CM_HOUSE_KICK";
			case 417:
				return "CM_HOUSE_PAY_RENT";
			case 273:
				return "CM_HOUSE_SCRIPT";
			case 747:
				return "CM_HOUSE_SETTINGS";
			case 416:
				return "CM_HOUSE_TELEPORT";
			case 303:
				return "CM_CRAFT";
			case 169:
				return "CM_CHARACTER_EDIT";
			case 272:
				return "CM_CHARGE_ITEM";
			case 287:
				return "CM_CHAT_GROUP_INFO";
			case 254:
				return "CM_CHAT_MESSAGE_WHISPER";
			case 201:
				return "CM_CHAT_PLAYER_INFO";
			case 371:
				return "CM_CHECK_NICKNAME";
			case 342:
				return "CM_DELETE_ITEM";
			case 299:
				return "CM_DELETE_MAIL";
			case 411:
				return "CM_DISTRIBUTION_SETTINGS";
			case 258:
				return "CM_EXCHANGE_ADD_ITEM";
			case 740:
				return "CM_EXCHANGE_ADD_KINAH";
			case 743:
				return "CM_EXCHANGE_CANCEL";
			case 741:
				return "CM_EXCHANGE_LOCK";
			case 742:
				return "CM_EXCHANGE_OK";
			case 257:
				return "CM_EXCHANGE_REQUEST";
			case 340:
				return "CM_DUEL_REQUEST";
			case 285:
				return "CM_LEGION_MODIFY_EMBLEM";
			case 355:
				return "CM_LEGION_UPLOAD_EMBLEM";
			case 354:
				return "CM_LEGION_UPLOAD_INFO";
			case 399:
				return "CM_MEGAPHONE";
			case 262:
				return "CM_VIEW_PLAYER_DETAILS";
			case 204:
				return "CM_GM_COMMAND_SEND";
			case 247:
				return "CM_PET_EMOTE";
			case 324:
				return "CM_READ_EXPRESS_MAIL";
			case 315:
				return "CM_RECIPE_DELETE";
			case 325:
				return "CM_SUBZONE_CHANGE";
			case 310:
				return "CM_STOP_TRAINING";
			case 291:
				return "CM_INVITE_TO_GROUP";
			case 314:
				return "CM_BUY_TRADE_IN_TRADE";
			case 749:
				return "CM_HOUSE_DECORATE";
			case 445:
				return "CM_REGISTER_HOUSE";
			case 379:
				return "CM_RESTORE_CHARACTER";
			case 744:
				return "CM_WINDSTREAM";
			case 275:
				return "CM_PLAY_MOVIE_END";
			case 197:
				return "CM_REMOVE_ALTERED_STATE";
			case 298:
				return "CM_GET_MAIL_ATTACHMENT";
			case 270:
				return "CM_GROUP_DISTRIBUTION";
			case 410:
				return "CM_GROUP_LOOT";
			case 281:
				return "CM_LEGION_TABS";
			case 240:
				return "CM_INSTANCE_LEAVE";
			case 296:
				return "CM_READ_MAIL";
			case 365:
				return "CM_SUMMON_ATTACK";
			case 367:
				return "CM_SUMMON_CASTSPELL";
			case 347:
				return "CM_SUMMON_COMMAND";
			case 364:
				return "CM_SUMMON_EMOTION";
			case 363:
				return "CM_SUMMON_MOVE";
			case 397:
				return "CM_TUNE";
			case 284:
				return "CM_SET_NOTE";
			case 745:
				return "CM_MOTION";
			case 385:
				return "CM_REPORT_PLAYER";
			case 208:
				return "CM_CAPTCHA";
			case 434:
				return "CM_USE_PACK_ITEM";
			case 316:
				return "CM_ITEM_REMODEL";
			case 359:
				return "CM_APPEARANCE";
			case 265:
				return "CM_PING_REQUEST";
			case 349:
				return "CM_BROKER_LIST";
			case 350:
				return "CM_BROKER_SEARCH";
			case 322:
				return "CM_BROKER_CANCEL_REGISTERED";
			case 343:
				return "CM_BROKER_REGISTERED";
			case 292:
				return "CM_BROKER_COLLECT_SOLD_ITEMS";
			case 320:
				return "CM_BUY_BROKER_ITEM";
			case 321:
				return "CM_REGISTER_BROKER_ITEM";
			case 351:
				return "CM_BROKER_SETTLE_LIST";
			case 323:
				return "CM_BROKER_SETTLE_ACCOUNT";
			case 398:
				return "CM_SELECTITEM_OK";
			case 418:
				return "CM_USE_HOUSE_OBJECT";
			case 304:
				return "CM_MARK_FRIENDLIST";
		}
		return null;
    }
    
    /**
     * Logs unknown packet.
     *
     * @param state
     * @param id
     * @param data
     */
    private void unknownPacket(State state, int id, ByteBuffer data) {
        if (NetworkConfig.DISPLAY_UNKNOWNPACKETS) {
            log.warn(String.format("Unknown packet received from Aion client: 0x%04X, state=%s %n%s", id, state.toString(), Util.toHex(data)));
        }
    }    
}
