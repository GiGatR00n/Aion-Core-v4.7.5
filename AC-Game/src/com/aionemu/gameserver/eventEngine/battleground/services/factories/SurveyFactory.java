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
package com.aionemu.gameserver.eventEngine.battleground.services.factories;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.IStorage;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.utils.chathandlers.ChatCommand;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundTemplate;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundType;
import com.aionemu.gameserver.eventEngine.battleground.services.battleground.BattleGround;
import com.aionemu.gameserver.eventEngine.battleground.services.battleground.BattleGroundManager;

/**
 * @author Eloann
 */
public class SurveyFactory {

    public static String buildAssaultBattleGroundReport(Player player) {
        if (player == null || player.getBattleGround() == null) {
            return "";
        }
        BattleGround bg = player.getBattleGround();
        String html = "<poll>\n";
        html += "<poll_introduction>\n";
        html += "	<![CDATA[<font color='4CB1E5' size='+1'><center>Results of the Assault Battleground</center></font>]]>\n";
        html += "</poll_introduction>\n";
        html += "<poll_title>\n";
        html += "	<font color='ffc519'></font>\n";
        html += "</poll_title>\n";
        html += "<start_date>2010-08-08 00:00</start_date>\n";
        html += "<end_date>2010-09-14 01:00</end_date>\n";
        html += "<servers></servers>\n";
        html += "<order_num></order_num>\n";
        html += "<race></race>\n";
        html += "<main_class></main_class>\n";
        html += "<world_id></world_id>\n";
        html += "<item_id>";
        html += 0;
        html += "</item_id>\n";
        html += "<item_cnt>";
        html += 0;
        html += "</item_cnt>\n";
        html += "<level>1~65</level>\n";
        html += "<questions>\n";

        List<Player> elyos = bg.getRanking(Race.ELYOS, true);
        List<Player> asmos = bg.getRanking(Race.ASMODIANS, true);

        String winner = "";
        String winnerColor = "";

        int EPts = 0;
        int EKills = 0;
        int APts = 0;
        int AKills = 0;
        for (Player e : elyos) {
            EPts += e.battlegroundSessionPoints;
            EKills += e.battlegroundSessionKills;
        }
        for (Player a : asmos) {
            APts += a.battlegroundSessionPoints;
            AKills += a.battlegroundSessionKills;
        }

        if (EPts > APts) {
            winner = "Elyos";
            winnerColor = "4CB1E5";
        } else if (EPts == APts) {
            winner = "The two factions";
            winnerColor = "00C000";
        } else if (EPts < APts) {
            winner = "Asmodians";
            winnerColor = "ffba75";
        }

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "<font color='" + winnerColor + "' size='+2' style='font-weight: bold;'>" + winner + " win !</font>";
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "<font color='4CB1E5'><center>Elyos - Points: " + EPts + " - Kills:" + EKills + "</center></font>";
        html += "<br><br>";

        html += "<table border='0'>";
        html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>&nbsp;Points&nbsp;</center></td><td><center>&nbsp;&nbsp;Kills&nbsp;&nbsp;</center></td><td><center>&nbsp;&nbsp;Deaths&nbsp;&nbsp;</center></td>";
        int counter = 1;
        for (Player e : elyos) {
            html += "<tr><td>" + counter + ". </td><td>" + e.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + e.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionKills
                    + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionDeaths + "</td></tr>";
            counter++;
        }
        html += "</table>";

        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "<font color='ffba75'><center>Asmodians - Points: " + APts + " - Kills:" + AKills + "</center></font>";
        html += "<br><br>";

        html += "<table border='0'>";
        html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>&nbsp;Points&nbsp;</center></td><td><center>&nbsp;&nbsp;Kills&nbsp;&nbsp;</center></td><td><center>&nbsp;Deaths&nbsp;</center></td>";
        counter = 1;
        for (Player a : asmos) {
            html += "<tr><td>" + counter + ". </td><td>" + a.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + a.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionKills
                    + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionDeaths + "</td></tr>";
            counter++;
        }
        html += "</table>";

        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";

        IStorage inventory = player.getStorage(StorageType.CUBE.getId());
        long Kinah0E = (long) (player.battlegroundBetE);
        long Kinah1E = (long) (player.battlegroundBetE * 0.8);
        long Kinah2E = (long) (player.battlegroundBetE * 1.8);
        long Kinah0A = (long) (player.battlegroundBetA);
        long Kinah1A = (long) (player.battlegroundBetA * 0.8);
        long Kinah2A = (long) (player.battlegroundBetA * 1.8);
        if (player.battlegroundObserve == 0) {
            int oldPoints = player.getCommonData().getBattleGroundPoints();
            int earnedPoints = player.battlegroundSessionPoints;

            html += "You have earned " + earnedPoints + " BG points, ";

            html += "your total BG points is now : " + (player.getCommonData().getBattleGroundPoints() + earnedPoints) + ".<br><br>";

            for (BattleGroundTemplate template : BattleGroundManager.getUnlockedBattleGrounds(oldPoints, player.getCommonData().getBattleGroundPoints())) {
                html += "You have unlocked the following battleground : " + template.getName() + " (" + template.getJoinConditions().getRequiredBgPoints() + " pts.)<br><br>";
            }
        } else if (player.battlegroundObserve > 0) {
            if (player.battlegroundBetE == 0 && player.battlegroundBetA == 0) {
                html += "You don't have bet kinah during this battleground.";
            } else if (player.battlegroundBetE > 0) {
                html += "You have bet : " + Kinah0E + " kinah for the elyos.<br><br>";
                if (winner.equals("Elyos")) {
                    html += "" + winner + " win, so you have earned : " + Kinah1E + " kinah !";
                    inventory.increaseKinah(Kinah2E);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah2E + " | Faction : e"));
                } else if (winner.equals("Asmodians")) {
                    html += "" + winner + " win, so you have lost : " + Kinah0E + " kinah !";
                } else if (winner.equals("The two factions")) {
                    html += "" + winner + " win, so you have recovered your bet : " + Kinah0E + " kinah !";
                    inventory.increaseKinah(Kinah0E);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah0E + " | Faction : 0"));
                }
            } else if (player.battlegroundBetA > 0) {
                html += "You have bet : " + Kinah0A + " kinah for the asmodians.<br><br>";
                if (winner.equals("Asmodians")) {
                    html += "" + winner + " win, so you have earned : " + Kinah1A + " kinah !";
                    inventory.increaseKinah(Kinah2A);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah2A + " | Faction : a"));
                } else if (winner.equals("Elyos")) {
                    html += "" + winner + " win, so you have lost : " + Kinah0A + " kinah !";
                } else if (winner.equals("The two factions")) {
                    html += "" + winner + " win, so you have recovered your bet : " + Kinah0A + " kinah !";
                    inventory.increaseKinah(Kinah0A);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah0A + " | Faction : 0"));
                }
            }
        }

        bg.commitPoints(player);

        html += "\n";
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "			<input type='radio'>Close</input>";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "</questions>\n";
        html += "</poll>\n";

        return html;
    }

    public static String buildCTFBattleGroundReport(Player player) {
        if (player == null || player.getBattleGround() == null) {
            return "";
        }
        BattleGround bg = player.getBattleGround();
        String html = "<poll>\n";
        html += "<poll_introduction>\n";
        html += "	<![CDATA[<font color='4CB1E5' size='+1'><center>Results of the Capture the flag Battleground</center></font>]]>\n";
        html += "</poll_introduction>\n";
        html += "<poll_title>\n";
        html += "	<font color='ffc519'></font>\n";
        html += "</poll_title>\n";
        html += "<start_date>2010-08-08 00:00</start_date>\n";
        html += "<end_date>2010-09-14 01:00</end_date>\n";
        html += "<servers></servers>\n";
        html += "<order_num></order_num>\n";
        html += "<race></race>\n";
        html += "<main_class></main_class>\n";
        html += "<world_id></world_id>\n";
        html += "<item_id>";
        html += 0;
        html += "</item_id>\n";
        html += "<item_cnt>";
        html += 0;
        html += "</item_cnt>\n";
        html += "<level>1~55</level>\n";
        html += "<questions>\n";

        List<Player> elyos = bg.getRanking(Race.ELYOS, true);
        List<Player> asmos = bg.getRanking(Race.ASMODIANS, true);

        String winner = "";
        String winnerColor = "";

        int EPts = 0;
        int EFlags = 0;
        int APts = 0;
        int AFlags = 0;
        for (Player e : elyos) {
            EPts += e.battlegroundSessionPoints;
            EFlags += e.battlegroundSessionFlags;
        }
        for (Player a : asmos) {
            APts += a.battlegroundSessionPoints;
            AFlags += a.battlegroundSessionFlags;
        }

        if (EFlags > AFlags) {
            winner = "Elyos";
            winnerColor = "4CB1E5";
        } else if (EFlags == AFlags) {
            if (EPts > APts) {
                winner = "Elyos";
                winnerColor = "4CB1E5";
            } else if (EPts == APts) {
                winner = "The two factions";
                winnerColor = "00C000";
            } else if (EPts < APts) {
                winner = "Asmodians";
                winnerColor = "ffba75";
            }
        } else if (EFlags < AFlags) {
            winner = "Asmodians";
            winnerColor = "ffba75";
        }

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "<font color='" + winnerColor + "' size='+2' style='font-weight: bold;'>" + winner + " win !</font>";
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "<font color='4CB1E5'><center>Elyos - Flags: " + EFlags + " - Points: " + EPts + "</center></font>";
        html += "<br><br>";

        html += "<table border='0'>";
        html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>&nbsp;Points&nbsp;</center></td><td><center>&nbsp;&nbsp;Kills&nbsp;&nbsp;</center></td><td><center>&nbsp;&nbsp;Flags&nbsp;&nbsp;</center></td>";
        int counter = 1;
        for (Player e : elyos) {
            html += "<tr><td>" + counter + ". </td><td>" + e.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + e.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionKills
                    + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionFlags + "</td></tr>";
            counter++;
        }
        html += "</table>";

        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "<font color='ffba75'><center>Asmodians - Flags: " + AFlags + " - Points: " + APts + "</center></font>";
        html += "<br><br>";

        html += "<table border='0'>";
        html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>&nbsp;Points&nbsp;</center></td><td><center>&nbsp;&nbsp;Kills&nbsp;&nbsp;</center></td><td><center>&nbsp;&nbsp;Flags&nbsp;&nbsp;</center></td>";
        counter = 1;
        for (Player a : asmos) {
            html += "<tr><td>" + counter + ". </td><td>" + a.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + a.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionKills
                    + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionFlags + "</td></tr>";
            counter++;
        }
        html += "</table>";

        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";

        IStorage inventory = player.getStorage(StorageType.CUBE.getId());
        long Kinah0E = (long) (player.battlegroundBetE);
        long Kinah1E = (long) (player.battlegroundBetE * 0.8);
        long Kinah2E = (long) (player.battlegroundBetE * 1.8);
        long Kinah0A = (long) (player.battlegroundBetA);
        long Kinah1A = (long) (player.battlegroundBetA * 0.8);
        long Kinah2A = (long) (player.battlegroundBetA * 1.8);
        if (player.battlegroundObserve == 0) {
            int oldPoints = player.getCommonData().getBattleGroundPoints();
            int earnedPoints = player.battlegroundSessionPoints;

            html += "You have earned " + earnedPoints + " BG points, ";

            html += "your total BG points is now : " + (player.getCommonData().getBattleGroundPoints() + earnedPoints) + ".<br><br>";

            for (BattleGroundTemplate template : BattleGroundManager.getUnlockedBattleGrounds(oldPoints, player.getCommonData().getBattleGroundPoints())) {
                html += "You have unlocked the following battleground : " + template.getName() + " (" + template.getJoinConditions().getRequiredBgPoints() + " pts.)<br><br>";
            }
        } else if (player.battlegroundObserve > 0) {
            if (player.battlegroundBetE == 0 && player.battlegroundBetA == 0) {
                html += "You don't have bet kinah during this battleground.";
            } else if (player.battlegroundBetE > 0) {
                html += "You have bet : " + Kinah0E + " kinah for the elyos.<br><br>";
                if (winner.equals("Elyos")) {
                    html += "" + winner + " win, so you have earned : " + Kinah1E + " kinah !";
                    inventory.increaseKinah(Kinah2E);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah2E + " | Faction : e"));
                } else if (winner.equals("Asmodians")) {
                    html += "" + winner + " win, so you have lost : " + Kinah0E + " kinah !";
                } else if (winner.equals("The two factions")) {
                    html += "" + winner + " win, so you have recovered your bet : " + Kinah0E + " kinah !";
                    inventory.increaseKinah(Kinah0E);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah0E + " | Faction : 0"));
                }
            } else if (player.battlegroundBetA > 0) {
                html += "You have bet : " + Kinah0A + " kinah for the asmodians.<br><br>";
                if (winner.equals("Asmodians")) {
                    html += "" + winner + " win, so you have earned : " + Kinah1A + " kinah !";
                    inventory.increaseKinah(Kinah2A);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah2A + " | Faction : a"));
                } else if (winner.equals("Elyos")) {
                    html += "" + winner + " win, so you have lost : " + Kinah0A + " kinah !";
                } else if (winner.equals("The two factions")) {
                    html += "" + winner + " win, so you have recovered your bet : " + Kinah0A + " kinah !";
                    inventory.increaseKinah(Kinah0A);
                    LoggerFactory.getLogger(ChatCommand.class).info(String.format("[BET] - Player : " + player.getName() + " | Get : " + Kinah0A + " | Faction : 0"));
                }
            }
        }

        bg.commitPoints(player);

        html += "\n";
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "			<input type='radio'>Close</input>";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "</questions>\n";
        html += "</poll>\n";

        return html;
    }

    public static String getBattleGroundRanking(BattleGround bg) {
        String html = "<poll>\n";
        html += "<poll_introduction>\n";
        html += "	<![CDATA[<font color='4CB1E5' size='+1'><center>Current rank</center></font>]]>\n";
        html += "</poll_introduction>\n";
        html += "<poll_title>\n";
        html += "	<font color='ffc519'></font>\n";
        html += "</poll_title>\n";
        html += "<start_date>2010-08-08 00:00</start_date>\n";
        html += "<end_date>2010-09-14 01:00</end_date>\n";
        html += "<servers></servers>\n";
        html += "<order_num></order_num>\n";
        html += "<race></race>\n";
        html += "<main_class></main_class>\n";
        html += "<world_id></world_id>\n";
        html += "<item_id>";
        html += 0;
        html += "</item_id>\n";
        html += "<item_cnt>";
        html += 0;
        html += "</item_cnt>\n";
        html += "<level>1~55</level>\n";
        html += "<questions>\n";

        List<Player> elyos = bg.getRanking(Race.ELYOS, false);
        List<Player> asmos = bg.getRanking(Race.ASMODIANS, false);

        String winner = "";
        String winnerColor = "";

        int EPts = 0;
        int EKills = 0;
        int EFlags = 0;
        int APts = 0;
        int AKills = 0;
        int AFlags = 0;
        for (Player e : elyos) {
            EPts += e.battlegroundSessionPoints;
            EKills += e.battlegroundSessionKills;
            EFlags += e.battlegroundSessionFlags;
        }
        for (Player a : asmos) {
            APts += a.battlegroundSessionPoints;
            AKills += a.battlegroundSessionKills;
            AFlags += a.battlegroundSessionFlags;
        }

        if (bg.getTemplate().getType() == BattleGroundType.CTF) {
            if (EFlags > AFlags) {
                winner = "Elyos";
                winnerColor = "4CB1E5";
            } else if (EFlags == AFlags) {
                if (EPts > APts) {
                    winner = "Elyos";
                    winnerColor = "4CB1E5";
                } else if (EPts == APts) {
                    winner = "The two factions";
                    winnerColor = "00C000";
                } else if (EPts < APts) {
                    winner = "Asmodians";
                    winnerColor = "ffba75";
                }
            } else if (EFlags < AFlags) {
                winner = "Asmodians";
                winnerColor = "ffba75";
            }
        } else if (bg.getTemplate().getType() == BattleGroundType.ASSAULT) {
            if (EPts > APts) {
                winner = "Elyos";
                winnerColor = "4CB1E5";
            } else if (EPts == APts) {
                winner = "The two factions";
                winnerColor = "00C000";
            } else if (EPts < APts) {
                winner = "Asmodians";
                winnerColor = "ffba75";
            }
        }

        html += "   <question>\n";
        html += "       <title>\n";
        html += "           <![CDATA[\n";
        html += "<br><br>";
        html += "<font size='+2' style='font-weight: bold;'>Current winner : </font><font color='" + winnerColor + "' size='+2' style='font-weight: bold;'>" + winner + "</font>";
        html += "           ]]>\n";
        html += "       </title>\n";
        html += "       <select>\n";
        html += "       </select>\n";
        html += "   </question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        int counter = 1;
        if (bg.getTemplate().getType() == BattleGroundType.CTF) {
            html += "<font color='4CB1E5'><center>Elyos - Flags: " + EFlags + " - Points: " + EPts + "</center></font>";
            html += "<br><br>";

            html += "<table border='0'>";
            html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>&nbsp;Points&nbsp;</center></td><td><center>&nbsp;&nbsp;Kills&nbsp;&nbsp;</center></td><td><center>&nbsp;&nbsp;Flags&nbsp;&nbsp;</center></td>";
            counter = 1;
            for (Player e : elyos) {
                html += "<tr><td>" + counter + ". </td><td>" + e.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + e.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionKills
                        + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionFlags + "</td></tr>";
                counter++;
            }
            html += "</table>";
        }
        if (bg.getTemplate().getType() == BattleGroundType.ASSAULT) {
            html += "<font color='4CB1E5'><center>Elyos - Points: " + EPts + " - Kills: " + EKills + "</center></font>";
            html += "<br><br>";

            html += "<table border='0'>";
            html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>&nbsp;Points&nbsp;</center></td><td><center>&nbsp;&nbsp;Kills&nbsp;&nbsp;</center></td><td><center>&nbsp;&nbsp;Deaths&nbsp;&nbsp;</center></td>";
            counter = 1;
            for (Player e : elyos) {
                html += "<tr><td>" + counter + ". </td><td>" + e.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + e.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionKills
                        + " &nbsp;</td><td>&nbsp; " + e.battlegroundSessionDeaths + "</td></tr>";
                counter++;
            }
            html += "</table>";
        }
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        if (bg.getTemplate().getType() == BattleGroundType.CTF) {
            html += "<font color='ffba75'><center>Asmodians - Flags: " + AFlags + " - Pts: " + APts + "</center></font>";
            html += "<br><br>";

            html += "<table border='0'>";
            html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>&nbsp;Points&nbsp;</center></td><td><center>&nbsp;&nbsp;Kills&nbsp;&nbsp;</center></td><td><center>&nbsp;&nbsp;Flags&nbsp;&nbsp;</center></td>";
            counter = 1;
            for (Player a : asmos) {
                html += "<tr><td>" + counter + ". </td><td>" + a.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + a.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionKills
                        + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionFlags + "</td></tr>";
                counter++;
            }
            html += "</table>";
        }
        if (bg.getTemplate().getType() == BattleGroundType.ASSAULT) {
            html += "<font color='ffba75'><center>Asmodians - Points: " + APts + " - Kills: " + AKills + "</center></font>";
            html += "<br><br>";

            html += "<table border='0'>";
            html += "<tr><td><center>Pos.</center></td><td><center>Name</center></td><td><center>Points</center></td><td><center>Kills</center></td><td><center>Deaths</center></td>";
            counter = 1;
            for (Player a : asmos) {
                html += "<tr><td>" + counter + ". </td><td>" + a.getName() + "&nbsp;&nbsp;</td><td>&nbsp; " + a.battlegroundSessionPoints + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionKills
                        + " &nbsp;</td><td>&nbsp; " + a.battlegroundSessionDeaths + "</td></tr>";
                counter++;
            }
            html += "</table>";
        }
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "	<question>\n";
        html += "		<title>\n";
        html += "			<![CDATA[\n";
        html += "<br><br>";
        html += "			]]>\n";
        html += "		</title>\n";
        html += "		<select>\n";
        html += "			<input type='radio' checked='checked'>Close</input>";
        html += "		</select>\n";
        html += "	</question>\n";

        html += "</questions>\n";
        html += "</poll>\n";
        return html;

    }

    public static String TeamRegistrationForm() {
        String html = "<poll>\n";
        html += "<poll_introduction>\n";
        html += "   <![CDATA[<font color='4CB1E5'>Team Registration for Battle</font>]]>\n";
        html += "</poll_introduction>\n";
        html += "<poll_title>\n";
        html += "   <font color='ffc519'></font>\n";
        html += "</poll_title>\n";
        html += "<start_date>2010-08-08 00:00</start_date>\n";
        html += "<end_date>2010-09-14 01:00</end_date>\n";
        html += "<servers></servers>\n";
        html += "<order_num></order_num>\n";
        html += "<race></race>\n";
        html += "<main_class></main_class>\n";
        html += "<world_id></world_id>\n";
        html += "<item_id>";
        html += 0;
        html += "</item_id>\n";
        html += "<item_cnt>";
        html += 0;
        html += "</item_cnt>\n";
        html += "<level>1~55</level>\n";
        html += "<questions>\n";

        html += "   <question>\n";
        html += "       <title>\n";
        html += "           <![CDATA[\n";

        html += "<br><br>";
        html += "Choose the team you want to fight with : ";
        html += "<br><br>";

        html += "           ]]>\n";
        html += "       </title>\n";
        html += "       <select>\n";

        html += "           <input type='radio'>Register with your team 2v2</input>";
        html += "           <input type='radio'>Register with your team 3v3</input>";
        html += "           <input type='radio'>Register with your team 5v5</input>";

        html += "           <input type='radio'>Close</input>";
        html += "       </select>\n";
        html += "   </question>\n";

        html += "</questions>\n";
        html += "</poll>\n";
        return html;
    }

    public static String getRemovePlayerMenu() {
        String html = "<poll>\n";
        html += "<poll_introduction>\n";
        html += "   <![CDATA[<font color='4CB1E5'>Remove Team Player</font>]]>\n";
        html += "</poll_introduction>\n";
        html += "<poll_title>\n";
        html += "   <font color='ffc519'></font>\n";
        html += "</poll_title>\n";
        html += "<start_date>2010-08-08 00:00</start_date>\n";
        html += "<end_date>2010-09-14 01:00</end_date>\n";
        html += "<servers></servers>\n";
        html += "<order_num></order_num>\n";
        html += "<race></race>\n";
        html += "<main_class></main_class>\n";
        html += "<world_id></world_id>\n";
        html += "<item_id>";
        html += 0;
        html += "</item_id>\n";
        html += "<item_cnt>";
        html += 0;
        html += "</item_cnt>\n";
        html += "<level>1~55</level>\n";
        html += "<questions>\n";

        html += "   <question>\n";
        html += "       <title>\n";
        html += "           <![CDATA[\n";
        html += "<br><br>";
        html += "Choose which player do you want to remove from your team :<br><br>";
        html += "           ]]>\n";
        html += "       </title>\n";
        html += "       <input type='text' />\n";
        html += "   </question>\n";

        html += "   <question>\n";
        html += "       <title>\n";
        html += "           <![CDATA[\n";
        html += "Are you sure to remove this player ?<br><br>";
        html += "           ]]>\n";
        html += "       </title>\n";
        html += "       <select>";
        html += "       <input type='radio'>Yes, I am</input>\n";
        html += "       <input type='radio'>Cancel remove request</input>\n";
        html += "       </select>";
        html += "   </question>\n";

        html += "</questions>\n";
        html += "</poll>\n";
        return html;
    }
}
