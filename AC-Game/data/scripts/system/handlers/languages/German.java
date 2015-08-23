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
 * @Aion-Core Dev.742/22062015
 */


package languages;

import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.Language;

public class German extends Language {

    public German() {
        super("de");
        addSupportedLanguage("de_DE");
        addTranslatedMessage(CustomMessageId.INFO1, "Warnung: Benutzung von fremde software, wie (speed hack&Co ist strengst verboten.");
        addTranslatedMessage(CustomMessageId.INFO2, "Note: Je nach Fall, fuehrt ausnahmslos zu unbefristete Account Sperrung.");
        addTranslatedMessage(CustomMessageId.INFO3, "Warnung: Werben andere Aion Server oder abwerben von Spieler hier auf dem Server, ist untersagt.");
        addTranslatedMessage(CustomMessageId.INFO4, "Anmerkung: Das Team wird niemals nach Account Passwort fragen!");
        addTranslatedMessage(CustomMessageId.INFO5, "Chat: Benutze .faction, .ely, ,asmo <text> um in Chat zu schreiben. ");
        addTranslatedMessage(CustomMessageId.INFO6, "Chat: Benutze /1 /2 /3, um in Chatkanäle zu schreiben.");
        addTranslatedMessage(CustomMessageId.INFO7, "Befehle: Benutze .help, um zu sehen welche Spieler Befehle noch zur Verfügung stehen.");
        addTranslatedMessage(CustomMessageId.ENDMESSAGE, "Viel Spass auf unserem Server: ");
        addTranslatedMessage(CustomMessageId.ANNOUNCE_GM_CONNECTION, " Ist jetzt verfuegbar fuer den Support!");
        addTranslatedMessage(CustomMessageId.ANNOUNCE_GM_DECONNECTION, " Ist nicht mehr verfuegbar fuer den Support!");
        addTranslatedMessage(CustomMessageId.ANNOUNCE_MEMBER_CONNECTION, "%s just entered into Atreia.");
        addTranslatedMessage(CustomMessageId.COMMAND_NOT_ENOUGH_RIGHTS, "Sie haben keine Berechtigung zur Verwendung dieses Befehls");
        addTranslatedMessage(CustomMessageId.PLAYER_NOT_ONLINE, "Spieler nicht online");
        addTranslatedMessage(CustomMessageId.INTEGER_PARAMETER_REQUIRED, "Integer parameter required");
        addTranslatedMessage(CustomMessageId.INTEGER_PARAMETERS_ONLY, "Integer parameters only");
        addTranslatedMessage(CustomMessageId.SOMETHING_WRONG_HAPPENED, "Somthing wrong happened");
        addTranslatedMessage(CustomMessageId.COMMAND_DISABLED, "Befehl deaktiviert");
        addTranslatedMessage(CustomMessageId.COMMAND_ADD_SYNTAX, "Syntax: //add <player name> <itemid> [<amount>]");
        addTranslatedMessage(CustomMessageId.COMMAND_ADD_ADMIN_SUCCESS, "Item success added to player %s");
        addTranslatedMessage(CustomMessageId.COMMAND_ADD_PLAYER_SUCCESS, "GM %s gibt dir %d item(s)");
        addTranslatedMessage(CustomMessageId.COMMAND_ADD_FAILURE, "Item %d not exist or cant be added %s");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDDROP_SYNTAX, "Syntax: //adddrop <npc id> <itemid> <min> <max> <chance>");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_SYNTAX, "Syntax: //addset <player name> <id set>");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_SET_DOES_NOT_EXISTS, "Set %d not exist");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_NOT_ENOUGH_SLOTS, "Not enough inventory %d slots to add this set");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_CANNOT_ADD_ITEM, "Item %d can not be added to the %s");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_ADMIN_SUCCESS, "Set %d added success %s");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSET_PLAYER_SUCCESS, "%s gibt dir set");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSKILL_SYNTAX, "Syntax: //addskill <skill id> <skill lvl");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSKILL_ADMIN_SUCCESS, "Skill %d added success %s");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDSKILL_PLAYER_SUCCESS, "%s give you skill");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_SYNTAX, "Syntax: //addtitle <title id> <player name> [special]");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_TITLE_INVALID, "Title must be from 1 to 50");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_CANNOT_ADD_TITLE_TO_ME, "Cannot add title %d self");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_CANNOT_ADD_TITLE_TO_PLAYER, "Cannot add title %d to %s");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_ADMIN_SUCCESS_ME, "Title %d added success self");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_ADMIN_SUCCESS, "You success added title %d to player %s");
        addTranslatedMessage(CustomMessageId.COMMAND_ADDTITLE_PLAYER_SUCCESS, "GM %s gibt dir Titel %d");
        addTranslatedMessage(CustomMessageId.COMMAND_SEND_SYNTAX, "Syntax: //send <file name>");
        addTranslatedMessage(CustomMessageId.COMMAND_SEND_MAPPING_NOT_FOUND, "%s not found");
        addTranslatedMessage(CustomMessageId.COMMAND_SEND_NO_PACKET, "Send no packet");
        addTranslatedMessage(CustomMessageId.CHANNEL_WORLD_DISABLED, "Kanal %s verboten, benutze %s oder %s je nach Fraktion");
        addTranslatedMessage(CustomMessageId.CHANNEL_ALL_DISABLED, "Alle Kanäle sind deaktiviert");
        addTranslatedMessage(CustomMessageId.CHANNEL_ALREADY_FIXED, "Dein Kanal wurde erfolgreich installiert %s");
        addTranslatedMessage(CustomMessageId.CHANNEL_FIXED, "Installierter Kanal %s");
        addTranslatedMessage(CustomMessageId.CHANNEL_NOT_ALLOWED, "Du kannst diesen Kanal nicht verwenden");
        addTranslatedMessage(CustomMessageId.CHANNEL_FIXED_BOTH, "Installed channels %s and %s");
        addTranslatedMessage(CustomMessageId.CHANNEL_UNFIX_HELP, "Write %s to come out of the channel"); // ;)
        addTranslatedMessage(CustomMessageId.CHANNEL_NOT_FIXED, "You are not installed on the channel");
        addTranslatedMessage(CustomMessageId.CHANNEL_FIXED_OTHER, "Your chat is not installed on this channel, but on %s");
        addTranslatedMessage(CustomMessageId.CHANNEL_RELEASED, "You come out of the channel %s");
        addTranslatedMessage(CustomMessageId.CHANNEL_RELEASED_BOTH, "You are out of %s and %s");
        addTranslatedMessage(CustomMessageId.CHANNEL_BAN_ENDED, "You can rejoin the channels");
        addTranslatedMessage(CustomMessageId.CHANNEL_BAN_ENDED_FOR, "Player %s may again join the channel");
        addTranslatedMessage(CustomMessageId.CHANNEL_BANNED, "Du kannst diesen Kanal nicht betreten, da %s dich gebannt hat wegen: %s, Zeit bis zu freigabe: %s");
        addTranslatedMessage(CustomMessageId.COMMAND_MISSING_SKILLS_STIGMAS_ADDED, "%d skill %d stigma given to you");
        addTranslatedMessage(CustomMessageId.COMMAND_MISSING_SKILLS_ADDED, "%d ability given to you");
        addTranslatedMessage(CustomMessageId.USER_COMMAND_DOES_NOT_EXIST, "DIeser Befehl ist hier nicht vorhanden ^^");
        addTranslatedMessage(CustomMessageId.COMMAND_XP_DISABLED, "XP können nicht verdient werden, gebe .xpon ein um zu entsperren");
        addTranslatedMessage(CustomMessageId.COMMAND_XP_ALREADY_DISABLED, "XP können nicht verdient werden !");
        addTranslatedMessage(CustomMessageId.COMMAND_XP_ENABLED, "XP können verdient werden!");
        addTranslatedMessage(CustomMessageId.COMMAND_XP_ALREADY_ENABLED, "Accrual XP already enabled");
        addTranslatedMessage(CustomMessageId.DREDGION_LEVEL_TOO_LOW, "Dein jetziges Level ist zu niedrig um die Dredgion zu betreten.");
        addTranslatedMessage(CustomMessageId.DEFAULT_FINISH_MESSAGE, "Fertig!");

        /**
         * Asmo and Ely Channel
         */
        addTranslatedMessage(CustomMessageId.ASMO_FAIL, "You are Elyos! You can not use this chat. Ely <message> to post a new faction chat!");
        addTranslatedMessage(CustomMessageId.ELY_FAIL, "You are Asmo! You can not use this chat. Asmo <message> to post a new faction chat!");

        /**
         * PvP Service
         */
        addTranslatedMessage(CustomMessageId.ADV_WINNER_MSG, "[PvP System] Spieler getoetet ");
        addTranslatedMessage(CustomMessageId.ADV_LOSER_MSG, "[PvP System] Du wurdest getoetet von ");
        addTranslatedMessage(CustomMessageId.PLAP_LOST1, "[PL-AP] Du verlierst ");
        addTranslatedMessage(CustomMessageId.PLAP_LOST2, "% deiner totalen AP");
        addTranslatedMessage(CustomMessageId.PVP_TOLL_REWARD1, "Du hast ");
        addTranslatedMessage(CustomMessageId.PVP_TOLL_REWARD2, " Toll erhalten.");
        addTranslatedMessage(CustomMessageId.PVP_NO_REWARD1, "Du bekommst nichts fuer das toeten von ");
        addTranslatedMessage(CustomMessageId.PVP_NO_REWARD2, " weil du ihn schon zu oft getoetet hast!");
        addTranslatedMessage(CustomMessageId.EASY_MITHRIL_MSG, "[Mithril System] du bekamst: ");

        /**
         * Reward Service Login Messages
         */
        addTranslatedMessage(CustomMessageId.REWARD10, "Du kannst .start benutzen um eine Level 10 Ausstattung zu bekommen!");
        addTranslatedMessage(CustomMessageId.REWARD30, "Du kannst .start benutzen um eine Level 30 Ausstattung zu bekommen!");
        addTranslatedMessage(CustomMessageId.REWARD40, "Du kannst .start benutzen um eine Level 40 Ausstattung zu bekommen!");
        addTranslatedMessage(CustomMessageId.REWARD50, "Du kannst .start benutzen um eine Level 50 Ausstattung zu bekommen!");
        addTranslatedMessage(CustomMessageId.REWARD60, "Du kannst .start benutzen um eine Level 60 Ausstattung zu bekommen!");

        /**
         * Advanced PvP System
         */
        addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE1, "Heutige PvP Map: Reshanta");
        addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE2, "Heutige PvP Map: Tiamaranta");
        addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE3, "Heutige PvP Map: Inggison/Gelkmaros");
        addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE4, "Heutige PvP Map: Idian Depths");
        addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE5, "Heutige PvP Map: Katalam");
        addTranslatedMessage(CustomMessageId.PVP_ADV_MESSAGE6, "Heutige PvP Map: Danaria");
        /**
         * Asmo, Ely and World Channel
         */
        addTranslatedMessage(CustomMessageId.ASMO_FAIL, "Du bist Elyos! Du kannst diesen Chat nicht benutzen. Nutze .ely <Nachricht> um im Fraktions Chat zu schreiben!");
        addTranslatedMessage(CustomMessageId.ELY_FAIL, "Du bist Asmo! Du kannst diesen Chat nicht benutzen. Nutze .asmo <Nachricht> um im Fraktions Chat zu schreiben!");

        /**
         * Wedding related
         */
        addTranslatedMessage(CustomMessageId.WEDDINGNO1, "Du kannst dieses Kommando nicht waehrend des Kampfes nutzen!");
        addTranslatedMessage(CustomMessageId.WEDDINGNO2, "Hochzeit wurde nicht gestartet!");
        addTranslatedMessage(CustomMessageId.WEDDINGNO3, "Du hast die Heirat abgelehnt!");
        addTranslatedMessage(CustomMessageId.WEDDINGYES, "Du hast die Heirat akzeptiert!");

        /**
         * Clean Command related
         */
        addTranslatedMessage(CustomMessageId.CANNOTCLEAN, "Du musst eine Item Id eingeben oder einen Link posten!");
        addTranslatedMessage(CustomMessageId.CANNOTCLEAN2, "Du besitzt dieses Item nicht!");
        addTranslatedMessage(CustomMessageId.SUCCESSCLEAN, "Item wurde erfolgreich aus deinem Wuerfel entfernt!");


        /**
         * Mission check command related
         */
        addTranslatedMessage(CustomMessageId.SUCCESCHECKED, "Mission erfolgreich ueberprueft!");
        /**
         * No Exp Command
         */
        addTranslatedMessage(CustomMessageId.EPACTIVATED, "Deine EP wurden wieder aktiviert!");
        addTranslatedMessage(CustomMessageId.ACTODE, "Um deine EP zu deaktivieren, nutze .noexp");
        addTranslatedMessage(CustomMessageId.EPDEACTIVATED, "Deine EP wurden deaktiviert!");
        addTranslatedMessage(CustomMessageId.DETOAC, "Um deine EP zu aktivieren, nutze .noexp");

        /**
         * Auto Quest Command
         */
        addTranslatedMessage(CustomMessageId.WRONGQID, "Bitte gebe eine richtige Quest Id an!");
        addTranslatedMessage(CustomMessageId.NOTSTARTED, "Quest konnte nicht gestartet werden!");
        addTranslatedMessage(CustomMessageId.NOTSUPPORT, "Diese Quest wird nicht von diesem Kommando unterstuetzt!");

        /**
         * Quest Restart Command
         */
        addTranslatedMessage(CustomMessageId.CANNOTRESTART, "] kann nicht neugestartet werden");

        /**
         * Exchange Toll Command
         */
        addTranslatedMessage(CustomMessageId.TOLLTOBIG, "Du hast zu viele Toll!");
        addTranslatedMessage(CustomMessageId.TOLOWAP, "Du hast nicht genug AP!");
        addTranslatedMessage(CustomMessageId.TOLOWTOLL, "Du hast nicht genug Toll!");
        addTranslatedMessage(CustomMessageId.WRONGTOLLNUM, "Irgendwas lief schief!");

        /**
         * Cube Command
         */
        addTranslatedMessage(CustomMessageId.CUBE_ALLREADY_EXPANDED, "Dein Wuerfel ist voll erweitert!");
        addTranslatedMessage(CustomMessageId.CUBE_SUCCESS_EXPAND, "Dein Wuerfel wurde erfolgreich erweitert!");

        /**
         * GMList Command
         */
        addTranslatedMessage(CustomMessageId.ONE_GM_ONLINE, "Ein Team Mitglied ist online: ");
        addTranslatedMessage(CustomMessageId.MORE_GMS_ONLINE, "Es sind folgende Team Mitglieder online: ");
        addTranslatedMessage(CustomMessageId.NO_GM_ONLINE, "Es ist kein Team Mitglied online!");

        /**
         * Go Command (PvP Command)
         */
        addTranslatedMessage(CustomMessageId.NOT_USE_WHILE_FIGHT, "Du kannst dieses Kommando nicht waehrend des Kampfes nutzen!");
        addTranslatedMessage(CustomMessageId.NOT_USE_ON_PVP_MAP, "Du kannst dieses Kommando nicht auf einer PvP Map benutzen!");
        addTranslatedMessage(CustomMessageId.LEVEL_TOO_LOW, "Du kannst dieses Kommando nur mit Level 55 oder hoeher nutzen!");

        /**
         * Paint Command
         */
        addTranslatedMessage(CustomMessageId.WRONG_TARGET, "Bitte benutze ein erlaubtes Ziel!");

        /**
         * Shiva Command
         */
        addTranslatedMessage(CustomMessageId.ENCHANT_SUCCES, "Alle deine Items wurden verzaubert auf: ");
        addTranslatedMessage(CustomMessageId.ENCHANT_INFO, "Info: Dieses Kommando verzaubert alle deine Items auf <value>!");
        addTranslatedMessage(CustomMessageId.ENCHANT_SAMPLE, "Beispiel: (.eq 15) wuerde alle deine Items auf 15 verzaubern");

        /**
         * Userinfo Command
         */
        addTranslatedMessage(CustomMessageId.CANNOT_SPY_PLAYER, "Du kannst keine Infos von anderen Spielern bekommen!");

        /**
         * FFA System
         */
        addTranslatedMessage(CustomMessageId.FFA_IS_ALREADY_IN_TEAM, "Bitte verlasse deine Gruppe und versuche es erneut.");
        addTranslatedMessage(CustomMessageId.FFA_IS_ALREADY_IN, "Du bist bereits im FFA");
        addTranslatedMessage(CustomMessageId.FFA_FROZEN_MESSAGE, "Du bist für eine weile eingefrohren...");
        addTranslatedMessage(CustomMessageId.FFA_CURRENT_PLAYERS, "Jetzige Spieleranzahl :");
        addTranslatedMessage(CustomMessageId.FFA_USAGE, "Anleitung: .FFA enter, um FFA zu betreten .FFA leave, um FFA zu verlassen.\n.FFA info, um die jetzige Anzahl von FFA Spielern zu bekommen ");
        addTranslatedMessage(CustomMessageId.FFA_YOU_KICKED_OUT, "Du bist nicht mehr im FFA!");
        addTranslatedMessage(CustomMessageId.FFA_YOUR_NOT_IN, "Du bist zurzeit kein Mitglied von der FFA Map.");
        addTranslatedMessage(CustomMessageId.FFA_ANNOUNCE_1, "Komme auf die FFA Map mit .ffa enter ! ");
        addTranslatedMessage(CustomMessageId.FFA_ANNOUNCE_2, " Bereits getan.");
        addTranslatedMessage(CustomMessageId.FFA_ANNOUNCE_3, "Komm jetzt auf die FFA Map!");
        addTranslatedMessage(CustomMessageId.FFA_GHOST_KILL, "Ein Geist killte ");
        addTranslatedMessage(CustomMessageId.FFA_KILL_MESSAGE, " hat getötet ");
        addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_1, " tut sein bestes!");
        addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_2, " ist auf seinem trip!");
        addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_3, " möchte mehr blut!");
        addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_4, " ist wie ein krankes Monster!");
        addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_5, " bist du OK? ");
        addTranslatedMessage(CustomMessageId.FFA_KILL_NAME_6, " verfluchte Kills?");

        /**
         * Check AFK Status
         */
        addTranslatedMessage(CustomMessageId.KICKED_AFK_OUT, "Du wurdest gekickt, da du zu lange AFK warst.");

        /**
         * Exchange Command
         */
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_ITEM, "You dont have enough from: ");
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_AP, "You dont have enough ap, you only have: ");

        /**
         * Medal Command
         */
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_SILVER, "Du hast nicht genügend silver medals.");
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_GOLD, "Du hast nicht genügend gold medals.");
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_PLATIN, "Du hast nicht genügend platin medals.");
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_MITHRIL, "Du hast nicht genügend mithril medals.");
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_AP2, "Du hast nicht genügend ap, du brauchst: ");
        addTranslatedMessage(CustomMessageId.EXCHANGE_SILVER, "Du hast getauscht [item:186000031] zu [item:186000030].");
        addTranslatedMessage(CustomMessageId.EXCHANGE_GOLD, "Du hast getauscht [item:186000030] zu [item:186000096].");
        addTranslatedMessage(CustomMessageId.EXCHANGE_PLATIN, "Du hast getauscht [item:186000096] zu [item:186000147].");
        addTranslatedMessage(CustomMessageId.EXCHANGE_MITHRIL, "Du hast getauscht [item:186000147] zu [item:186000223].");
        addTranslatedMessage(CustomMessageId.EX_SILVER_INFO, "\nSyntax: .medal silver - Tausche Silver zu Gold.");
        addTranslatedMessage(CustomMessageId.EX_GOLD_INFO, "\nSyntax: .medal gold - Tausche Gold zu Platin.");
        addTranslatedMessage(CustomMessageId.EX_PLATIN_INFO, "\nSyntax: .medal platinum - Tausche Platin zu Mithril.");
        addTranslatedMessage(CustomMessageId.EX_MITHRIL_INFO, "\nSyntax: .medal mithril - Tausche Mithril zu Honorable Mithril.");

        /**
         * Legendary Raid Spawn Events
         */
        addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_SPAWNED_ASMO, "[Spawn Event] Ragnarok was spawned for Asmodians at Beluslan!");
        addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_SPAWNED_ELYOS, "[Spawn Event] Omega was spawned for Elyos at Heiron!");
        addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_DESPAWNED_ASMO, "[Spawn Event] Ragnarok was unspawned, nobody kill him!");
        addTranslatedMessage(CustomMessageId.LEGENDARY_RAID_DESPAWNED_ELYOS, "[Spawn Event] Omega was unspawned, nobody kill him!");

        /**
         * HonorItems Command
         */
        addTranslatedMessage(CustomMessageId.PLATE_ARMOR, "Plate Armor");
        addTranslatedMessage(CustomMessageId.LEATHER_ARMOR, "Leather Armor");
        addTranslatedMessage(CustomMessageId.CLOTH_ARMOR, "Cloth Armor");
        addTranslatedMessage(CustomMessageId.CHAIN_ARMOR, "Chain Armor");
        addTranslatedMessage(CustomMessageId.WEAPONS, "Weapons");
        addTranslatedMessage(CustomMessageId.PLATE_ARMOR_PRICES, "Plate Armor Prices");
        addTranslatedMessage(CustomMessageId.LEATHER_ARMOR_PRICES, "Leather Armor Prices");
        addTranslatedMessage(CustomMessageId.CLOTH_ARMOR_PRICES, "Cloth Armor Prices");
        addTranslatedMessage(CustomMessageId.CHAIN_ARMOR_PRICES, "Chain Armor Prices");
        addTranslatedMessage(CustomMessageId.WEAPONS_PRICES, "Weapons Prices");
        addTranslatedMessage(CustomMessageId.NOT_ENOUGH_MEDALS, "You dont have enough Medals, you need: ");
        addTranslatedMessage(CustomMessageId.PLATE_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 1");
        addTranslatedMessage(CustomMessageId.LEATHER_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 6");
        addTranslatedMessage(CustomMessageId.CLOTH_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 11");
        addTranslatedMessage(CustomMessageId.CHAIN_ARMOR_USE_INFO, "Use .items and the equal ID (Example: .items 16");
        addTranslatedMessage(CustomMessageId.WEAPONS_USE_INFO, "Use .items and the equal ID (Example: .items 21");
        addTranslatedMessage(CustomMessageId.SUCCESSFULLY_TRADED, "You got successfully your Trade!");

        /**
         * Moltenus Announce
         */
        addTranslatedMessage(CustomMessageId.MOLTENUS_APPEAR, "Moltenus Fragment of the Wrath has spawn in the Abyss.");
        addTranslatedMessage(CustomMessageId.MOLTENUS_DISAPPEAR, "Moltenus Fragment of the Wrath has disappear.");

        /**
         * Dredgion Announce
         */
        addTranslatedMessage(CustomMessageId.DREDGION_ASMO_GROUP, "An asmodian group is waiting for dredgion.");
        addTranslatedMessage(CustomMessageId.DREDGION_ELYOS_GROUP, "An elyos group is waiting for dredgion.");
        addTranslatedMessage(CustomMessageId.DREDGION_ASMO, "An alone asmodian is waiting for dredgion.");
        addTranslatedMessage(CustomMessageId.DREDGION_ELYOS, "An alone elyos is waiting for dredgion.");

        /**
         * PvP Service
         */
        addTranslatedMessage(CustomMessageId.PVP_TOLL_REWARD1, "Sie haben verdient");
        addTranslatedMessage(CustomMessageId.PVP_TOLL_REWARD2, " Abyss Points.");

        /**
         * Invasion Rift
         */
        addTranslatedMessage(CustomMessageId.INVASION_RIFT_MIN_LEVEL, "Dein Level ist zu niedrig um zu passieren.");
        addTranslatedMessage(CustomMessageId.INVASION_RIFT_ELYOS, "A rift for Pandaemonium is open at Ingisson");
        addTranslatedMessage(CustomMessageId.INVASION_RIFT_ASMOS, "A rift for Sanctum is open at Gelkmaros");

        /**
         * PvP Spree Service
         */
        addTranslatedMessage(CustomMessageId.SPREE1, "Blutiger Sturm");
        addTranslatedMessage(CustomMessageId.SPREE2, "Blutbad");
        addTranslatedMessage(CustomMessageId.SPREE3, "Völkermord");
        addTranslatedMessage(CustomMessageId.KILL_COUNT, "Kills in Folge: ");
        addTranslatedMessage(CustomMessageId.CUSTOM_MSG1, " von ");
        addTranslatedMessage(CustomMessageId.MSG_SPREE1, " hat begonnen ein ");
        addTranslatedMessage(CustomMessageId.MSG_SPREE1_1, " !");
        addTranslatedMessage(CustomMessageId.MSG_SPREE2, " fuehrt eine echte ");
        addTranslatedMessage(CustomMessageId.MSG_SPREE2_1, " ! Haltet ihn schnell !");
        addTranslatedMessage(CustomMessageId.MSG_SPREE3, " tut ein ");
        addTranslatedMessage(CustomMessageId.MSG_SPREE3_1, " ! Lauf weg, wenn du kannst!");
        addTranslatedMessage(CustomMessageId.SPREE_END_MSG1, "Der Amoklauf von ");
        addTranslatedMessage(CustomMessageId.SPREE_END_MSG2, " wurde gestoppt von ");
        addTranslatedMessage(CustomMessageId.SPREE_END_MSG3, " nach ");
        addTranslatedMessage(CustomMessageId.SPREE_END_MSG4, " ununterbrochene Morde !");
        addTranslatedMessage(CustomMessageId.SPREE_MONSTER_MSG, "ein Monster");
    }
}
