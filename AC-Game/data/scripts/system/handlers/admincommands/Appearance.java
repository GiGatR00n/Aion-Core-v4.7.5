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
package admincommands;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Divinity
 */
public class Appearance extends AdminCommand {

    public Appearance() {
        super("appearance");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params == null || params.length < 1) {
            onFail(admin, null);
            return;
        }

        VisibleObject target = admin.getTarget();
        Player player;

        if (target == null) {
            player = admin;
        } else {
            player = (Player) target;
        }

        if (params[0].equals("reset")) {
            PlayerAppearance savedPlayerAppearance = player.getSavedPlayerAppearance();

            if (savedPlayerAppearance == null) {
                PacketSendUtility.sendMessage(admin, "The target has already the normal appearance.");
                return;
            }

            // Edit the current player's appearance with the saved player's appearance
            player.setPlayerAppearance(savedPlayerAppearance);

            // See line 44
            player.setSavedPlayerAppearance(null);

            // Warn the player
            PacketSendUtility.sendMessage(player, "An admin has resetted your appearance.");

            // Send update packets
            TeleportService2.teleportTo(player, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(),
                    player.getZ(), player.getHeading());

            return;
        }

        if (params.length < 2) {
            onFail(player, null);
            return;
        }

        // Get the current player's appearance
        PlayerAppearance playerAppearance = player.getPlayerAppearance();

        // Save a clean player's appearance
        if (player.getSavedPlayerAppearance() == null) {
            player.setSavedPlayerAppearance((PlayerAppearance) playerAppearance.clone());
        }

        if (params[0].equals("size")) // Edit player's size. Min: 0, Max: 50 (prevent bug)
        {
            float height;

            try {
                height = Float.parseFloat(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                onFail(player, e.getMessage());
                return;
            }

            if (height < 0 || height > 50) {
                PacketSendUtility.sendMessage(admin, "Size: Min value : 0 - Max value : 50");
                return;
            }

            // Edit the height
            playerAppearance.setHeight(height);
        } else if (params[0].equals("voice")) // Min: 0, Max: 3
        {
            int voice;

            try {
                voice = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                onFail(player, e.getMessage());
                return;
            }

            if (voice < 0 || voice > 3) {
                PacketSendUtility.sendMessage(admin, "Voice: Min value : 0 - Max value : 3");
                return;
            }

            // Edit the voice
            playerAppearance.setVoice(voice);
        } else if (params[0].equals("hair")) // Min: 1, Max: 43
        {
            int hair;

            try {
                hair = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                onFail(player, e.getMessage());
                return;
            }

            if (hair < 1 || hair > 43) {
                PacketSendUtility.sendMessage(admin, "Hair: Min value : 1 - Max value : 43");
                return;
            }

            // Edit the hair
            playerAppearance.setHair(hair);
        } else if (params[0].equals("face")) // Min: 1, Max: 24
        {
            int face;

            try {
                face = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                onFail(player, e.getMessage());
                return;
            }

            if (face < 1 || face > 24) {
                PacketSendUtility.sendMessage(admin, "Face: Min value : 1 - Max value : 24");
                return;
            }

            // Edit the face
            playerAppearance.setFace(face);
        } else if (params[0].equals("deco")) // Min: 1, Max: 18
        {
            int deco;

            try {
                deco = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                onFail(player, e.getMessage());
                return;
            }

            if (deco < 1 || deco > 18) {
                PacketSendUtility.sendMessage(admin, "Deco: Min value : 1 - Max value : 18");
                return;
            }

            // Edit the deco
            playerAppearance.setDeco(deco);
        } else if (params[0].equals("head_size")) // Min: 0, Max: 100
        {
            int head;

            try {
                head = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                onFail(player, e.getMessage());
                return;
            }

            if (head < 0 || head > 100) {
                PacketSendUtility.sendMessage(admin, "Head Size: Min value : 0 - Max value : 100");
                return;
            }

            // Edit the head
            playerAppearance.setHeadSize(head + 200);
        } else if (params[0].equals("tattoo")) // Min: 1, Max: 13
        {
            int tattoo;

            try {
                tattoo = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                onFail(player, e.getMessage());
                return;
            }

            if (tattoo < 1 || tattoo > 13) {
                PacketSendUtility.sendMessage(admin, "Tattoo: Min value : 1 - Max value : 13");
                return;
            }

            // Edit the tattoo
            playerAppearance.setTattoo(tattoo);
        } else {
            onFail(player, null);
            return;
        }

        // Edit the current player's appearance with our modifications
        player.setPlayerAppearance(playerAppearance);

        // Warn the player
        PacketSendUtility.sendMessage(player, "An admin has changed your appearance.");

        // Send update packets
        TeleportService2.teleportTo(player, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(),
                player.getZ(), player.getHeading());
    }

    @Override
    public void onFail(Player player, String message) {
        String syntax = "Syntax: //appearance <size | voice | hair | face | deco | head_size | tattoo | reset (to reset the appearance)> <value>";
        PacketSendUtility.sendMessage(player, syntax);
    }
}
