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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.services.ecfunctions.oneVsone.OneVsOneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Kill3r
 */
public class OneVsOne extends PlayerCommand {

    public OneVsOne() {
        super("1v1");
    }

    /**
     *
     * @param player
     * @param params
     */
    @Override
    public void execute(Player player, String...params){ // remember to put IF in group the player auto leaves .. this part need to be down on the event registarer or porter...
        if (params.length > 1){
            PacketSendUtility.sendMessage(player, "Syntax : .1v1 ");
            return;
        }

        if(!OneVsOneService.getInstance().Initialized){
            PacketSendUtility.sendMessage(player, "One Vs One is Disabled!");
            return;
        }

        //if(player.getAccessLevel() <= 1){  // Remove comment of these 4 lines to disable the 1vs1 enter for players.
            //PacketSendUtility.sendMessage(player, "Under Testing, Will be available Soon!");
            //return;
        //}

        if (player.isInFFA()){
            PacketSendUtility.sendMessage(player, "You cannot use the command while in FFA!");
            return;
        }

        if (player.isInPrison()){
            PacketSendUtility.sendMessage(player, "You cannot use the command inside Prison!");
            return;
        }

        if (player.isInPvEMode()){
            PacketSendUtility.sendMessage(player, "You're in a mode 'PvE' , you cannot register like that, Tell a GM to turn it off!");
            return;
        }

        if (player.isInPkMode()){
            PacketSendUtility.sendMessage(player, "You're in a mode 'PvP' , You cannot register like that, Tell a GM to turn it off!");
            return;
        }
        registerPlayer(player);

    }

    private void registerPlayer(final Player player){
        if(checkIfAlready(player)){
            PacketSendUtility.sendMessage(player, "Already Registered");
            RequestResponseHandler requestResponseHandler = new RequestResponseHandler(player) {
                @Override
                public void acceptRequest(Creature requester, Player responder) {
                    OneVsOneService.getInstance().deletePlayer(player);
                    PacketSendUtility.sendYellowMessageOnCenter(player, "You've removed from the Queue, Good luck!");
                }

                @Override
                public void denyRequest(Creature requester, Player responder) {

                }
            };
            boolean areusure = player.getResponseRequester().putRequest(1300564, requestResponseHandler);
            if(areusure){
                PacketSendUtility.sendPacket(player,new SM_QUESTION_WINDOW(1300564, 0, 0, "You're already registered for 1 vs 1 Event, Press 'YES' to unregister from 1 vs 1 Queue, or Just press 'NO' to ignore this message, and continue on the Queue!"));
            }
        }else{
            OneVsOneService.getInstance().playersToArena.add(player);
            PacketSendUtility.sendMessage(player, "Registered to the queue, Please wait till you get ported, (Ports comes every 1min).");
        }
    }

    private boolean checkIfAlready(Player player){
        return OneVsOneService.getInstance().checkAlreadyExists(player);

    }
}
