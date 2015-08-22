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
package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team.legion.Legion;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_INFO;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.services.NameRestrictionService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Simple
 */
public class CM_LEGION extends AionClientPacket {

    private static final Logger log = LoggerFactory.getLogger(CM_LEGION.class);
    /**
     * exOpcode and the rest
     */
    private int exOpcode;
    private short deputyPermission;
    private short centurionPermission;
    private short legionarPermission;
    private short volunteerPermission;
    private int rank;
    private String legionName;
    private String charName;
    private String newNickname;
    private String announcement;
    private String newSelfIntro;

    /**
     * Constructs new instance of CM_LEGION packet
     *
     * @param opcode
     */
    public CM_LEGION(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        exOpcode = readC();

        switch (exOpcode) {
            /**
             * Create a legion *
             */
            case 0x00:
                readD(); // 00 78 19 00 40
                legionName = readS();
                break;
            /**
             * Invite to legion *
             */
            case 0x01:
                readD(); // empty
                charName = readS();
                break;
            /**
             * Leave legion *
             */
            case 0x02:
                readD(); // empty
                readH(); // empty
                break;
            /**
             * Kick member from legion *
             */
            case 0x04:
                readD(); // empty
                charName = readS();
                break;
            /**
             * Appoint a new Brigade General *
             */
            case 0x05:
                readD();
                charName = readS();
                break;
            /**
             * Appoint Centurion *
             */
            case 0x06:
                rank = readD();
                charName = readS();
                break;
            /**
             * Demote to Legionary *
             */
            case 0x07:
                readD(); // char id? 00 78 19 00 40
                charName = readS();
                break;
            /**
             * Refresh legion info *
             */
            case 0x08:
                readD();
                readH();
                break;
            /**
             * Edit announcements *
             */
            case 0x09:
                readD(); // empty or char id?
                announcement = readS();
                break;
            /**
             * Change self introduction *
             */
            case 0x0A:
                readD(); // empty char id?
                newSelfIntro = readS();
                break;
            /**
             * Edit permissions *
             */
            case 0x0D:
                deputyPermission = (short) readH();
                centurionPermission = (short) readH();
                legionarPermission = (short) readH();
                volunteerPermission = (short) readH();
                break;
            /**
             * Level legion up *
             */
            case 0x0E:
                readD(); // empty
                readH(); // empty
                break;
            case 0x0F:
                charName = readS();
                newNickname = readS();
                break;
            default:
                log.info("Unknown Legion exOpcode? 0x" + Integer.toHexString(exOpcode).toUpperCase());
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        final Player activePlayer = getConnection().getActivePlayer();
        if (activePlayer.isLegionMember()) {
            final Legion legion = activePlayer.getLegion();

            if (charName != null) {
                LegionService.getInstance().handleCharNameRequest(exOpcode, activePlayer, charName, newNickname, rank);
            } else {
                switch (exOpcode) {
                    /**
                     * Refresh legion info *
                     */
                    case 0x08:
                        sendPacket(new SM_LEGION_INFO(legion));
                        break;
                    /**
                     * Edit announcements *
                     */
                    case 0x09:
                        LegionService.getInstance().handleLegionRequest(exOpcode, activePlayer, announcement);
                        break;
                    /**
                     * Change self introduction *
                     */
                    case 0x0A:
                        LegionService.getInstance().handleLegionRequest(exOpcode, activePlayer, newSelfIntro);
                        break;
                    /**
                     * Edit permissions *
                     */
                    case 0x0D:
                        if (activePlayer.getLegionMember().isBrigadeGeneral()) {
                            LegionService.getInstance().changePermissions(legion, deputyPermission, centurionPermission, legionarPermission, volunteerPermission);
                        }
                        break;
                    /**
                     * Misc. *
                     */
                    default:
                        LegionService.getInstance().handleLegionRequest(exOpcode, activePlayer);
                        break;
                }
            }
        } else {
            switch (exOpcode) {
                /**
                 * Create a legion *
                 */
                case 0x00:
                    if (NameRestrictionService.isForbiddenWord(legionName)) {
                        PacketSendUtility.sendMessage(activePlayer, "You are trying to use a forbidden name. Choose another one!");
                    } else {
                        LegionService.getInstance().createLegion(activePlayer, legionName);
                    }
                    break;
            }
        }
    }
}
