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
package playercommands;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.eventEngine.battleground.services.battleground.BattleGroundManager;
import com.aionemu.gameserver.eventEngine.battleground.services.factories.SurveyFactory;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Eloann
 */
public class cmd_bg extends PlayerCommand {

    public cmd_bg() {
        super("bg");
    }

    @Override
    public void execute(Player player, String... params) {
        if (params.length < 1 || params == null) {
            onFail(player, null);
            return;
        }
        if (!BattleGroundManager.INITIALIZED) {
            PacketSendUtility.sendMessage(player, "Command disabled.");
            return;
        }

        if (player.isInPrison()) {
            PacketSendUtility.sendMessage(player, "You cannot register for battlegrounds while you are in prison.");
            return;
        }

        if (params[0].equals("register")) {
            if (player.getBattleGround() != null) {
                PacketSendUtility.sendMessage(player, "You are already in a battleground.");
                PacketSendUtility.sendMessage(player, "Use your spell Return to leave the battleground.");
            } else if (player.battlegroundWaiting) {
                PacketSendUtility.sendMessage(player, "You are already registered in a battleground.");
                PacketSendUtility.sendMessage(player, "Use the command .bg unregister to cancel your registration.");
            } else {
                BattleGroundManager.sendRegistrationForm(player);
            }
        } else if (params[0].equals("observe")) {
            if (player.getBattleGround() != null) {
                PacketSendUtility.sendMessage(player, "You are already in a battleground.");
                PacketSendUtility.sendMessage(player, "Use your spell Return to leave the battleground.");
            } else if (player.battlegroundWaiting) {
                PacketSendUtility.sendMessage(player, "You are already registered in a battleground.");
                PacketSendUtility.sendMessage(player, "Use the command .bg unregister to cancel your registration.");
            } else {
                BattleGroundManager.sendRegistrationFormObs(player);
            }
        } else if (params[0].equals("stop")) {
            if (player.getBattleGround() != null && player.battlegroundObserve == 0) {
                PacketSendUtility.sendMessage(player, "You are playing in a battleground, not an observer.");
            } else if (player.getBattleGround() != null && player.battlegroundObserve > 0) {
                player.unsetVisualState(CreatureVisualState.HIDE20);
                PacketSendUtility.broadcastPacket(player, new SM_PLAYER_STATE(player), true);
                PacketSendUtility.sendMessage(player, "You are now visible.");
                player.setInvul(false);
                PacketSendUtility.sendMessage(player, "You are now mortal.");

                if (player.battlegroundBetE > 0 || player.battlegroundBetA > 0) {
                    PacketSendUtility.sendMessage(player, "You have lost your bet of " + (player.battlegroundBetE + player.battlegroundBetA) + "kinah.");
                    player.battlegroundBetE = 0;
                    player.battlegroundBetA = 0;
                }

                if (player.getCommonData().getRace() == Race.ELYOS) {
                    TeleportService2.teleportTo(player, 110010000, 1374f, 1399f, 573f, (byte) 0);
                } else {
                    TeleportService2.teleportTo(player, 120010000, 1324f, 1550f, 210f, (byte) 0);
                }
            } else {
                PacketSendUtility.sendMessage(player, "You are not observing any battleground.");
            }
        } else if (params[0].equals("rank")) {
            if (player.getBattleGround() == null || (!player.getBattleGround().running && !player.battlegroundWaiting)) {
                PacketSendUtility.sendMessage(player, "You are not registered in any battleground or the battleground is over.");
            } else {
                player.battlegroundRequestedRank = true;
                HTMLService.showHTML(player, SurveyFactory.getBattleGroundRanking(player.getBattleGround()), 151000001);
            }
        } else if (params[0].equals("stat") && player.getAccessLevel() > 0) {
            PacketSendUtility.sendMessage(player, BattleGroundManager.getElyosWaitList().get(1).size() + " Elyos " + BattleGroundManager.getAsmodiansWaitList().get(1).size() + " Asmodians for Triniel");
            PacketSendUtility.sendMessage(player, BattleGroundManager.getElyosWaitList().get(2).size() + " Elyos " + BattleGroundManager.getAsmodiansWaitList().get(2).size() + " Asmodians for Sanctum");
            PacketSendUtility.sendMessage(player, BattleGroundManager.getElyosWaitList().get(3).size() + " Elyos " + BattleGroundManager.getAsmodiansWaitList().get(3).size() + " Asmodians for Haramel");
        } else if (params[0].equals("exchange")) {
            if (player.getAccessLevel() < 1) {
                PacketSendUtility.sendMessage(player, "The exchange tool is not available.");
                return;
            }

            if (params[0].equals("exchange")) {
                PacketSendUtility.sendMessage(player, "The exchange rate is 1 BG point for 3 Abyss points.");
                PacketSendUtility.sendMessage(player, "To exchange some points, write .bg exchange <bg_points_number>");
            } else {
                try {
                    int bgPts = Integer.parseInt(params[1]);
                    if (player.getCommonData().getBattleGroundPoints() < bgPts) {
                        PacketSendUtility.sendMessage(player, "You don't have enough BG points.");
                        return;
                    }
                    player.getCommonData().setBattleGroundPoints(player.getCommonData().getBattleGroundPoints() - bgPts);
                    PacketSendUtility.sendMessage(player, "You have lost " + bgPts + " BG points.");
                    AbyssPointsService.addAp(player, bgPts * 3);
                } catch (Exception e) {
                    PacketSendUtility.sendMessage(player, "Syntax error. Use .bg exchange <bg_points_number>.");
                }
            }
        } else if (params[0].equals("end") && player.getAccessLevel() > 0) {
            player.getBattleGround().end();
        } else if (params[0].equals("unregister")) {
            if (!player.battlegroundWaiting) {
                PacketSendUtility.sendMessage(player, "You are not registered in any battleground.");
                return;
            }
            if (player.battlegroundObserve == 0) {
                BattleGroundManager.unregisterPlayer(player);
            } else if (player.battlegroundObserve > 0) {
                BattleGroundManager.unregisterPlayerObs(player);
            }
            PacketSendUtility.sendMessage(player, "Registration canceled.");
        } else if (params[0].equals("help")) {

            PacketSendUtility.sendMessage(player, ".bg register : register in a BG");
            PacketSendUtility.sendMessage(player, ".bg observe : observe a battleground");
            PacketSendUtility.sendMessage(player, ".bg unregister : unregister from the BG (before starting)");
            PacketSendUtility.sendMessage(player, ".bg stop : stop observe and back to home");
            PacketSendUtility.sendMessage(player, ".bg rank : see your rank during a BG");
            PacketSendUtility.sendMessage(player, ".bg points : to see your points");
            PacketSendUtility.sendMessage(player, ".bet : bet on a faction during observe mode");
        } else if (params[0].equals("points")) {
            PacketSendUtility.sendMessage(player, "You have actually " + (player.getCommonData().getBattleGroundPoints() + player.battlegroundSessionPoints) + " BG points" + (player.battlegroundSessionPoints > 0 ? ", including " + player.battlegroundSessionPoints + " in the current BG " : "") + ".");
        } else {
            PacketSendUtility.sendMessage(player, "This command doesn't exist, use .bg help");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Use: .bg help");
    }
}
