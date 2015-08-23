package playercommands;

import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.PacketSendUtility;


/**
* @author Phenom
* @Rewroked yayaya
*/
public class cmd_rgb extends PlayerCommand {
	
	public cmd_rgb() 
	{
		super("rgb");
	}
	
	private String getColorCode(String color) {
		if (color.equalsIgnoreCase("red")) return "1 0 0";
		if (color.equalsIgnoreCase("orange")) return "1 0.5 0";
		if (color.equalsIgnoreCase("yellow")) return "1 1 0";
		if (color.equalsIgnoreCase("lime")) return "0.5 1 0";
		if (color.equalsIgnoreCase("green")) return "0 1 0";
		if (color.equalsIgnoreCase("cyan")) return "0 1 1";
		if (color.equalsIgnoreCase("blue")) return "0 0 1";
		if (color.equalsIgnoreCase("violet")) return "0.5 0 1";
		if (color.equalsIgnoreCase("pink")) return "1 0 0.5";
		if (color.equalsIgnoreCase("black")) return "0 0 0";
		return null;
	}
	
	@Override
	public void execute(Player player, String... params) {
		
		if (params == null || params.length < 2) 
		{
			onFail(player, null);
			return;
		}
		
		// Only for VIP
		// 		3-9 - membership 3-9
		// 		2 - VIP (membership 2)
		// 		1 - Premium (membership 1)
		// 		0 - All players
		if (player.getClientConnection().getAccount().getMembership() < 2 ) {
            PacketSendUtility.sendMessage(player, "This command is available only to VIP!");
            return;
        }
		
		String color = getColorCode(params[0]);
		if (color == null) {
			PacketSendUtility.sendMessage(player, "Unknown color!" + "  Colors: Red|Orange|Yellow|Lime|Green|Cyan|Blue|Violet|Pink|Black");
			return;
		}
		
		String source = "";
		try {
			for (int i = 1; i < params.length; i++) {
			 source += (i == 1 ? "" : " ") + params[i];
			}
		} catch (NumberFormatException e) {
			PacketSendUtility.sendMessage(player, "Parameters should be text or number!");
			return;
		}
		
		int length = source.length();
		int index = 0;
		String message = player.getName() + ": ";
		while (index < length) {
			int next = index + 5;
			message += "[color:" + source.substring(index, next < length ? next : length) + ";" + color + "]";
			index = next;
		}
		PacketSendUtility.broadcastPacketAndReceive(player, new SM_MESSAGE(player, message, ChatType.GOLDEN_YELLOW));
	}
	
	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Usage: .rgb <color> <message>");
	}
}