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

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.administration.DeveloperConfig;
import com.aionemu.gameserver.configs.main.*;
import com.aionemu.gameserver.configs.network.IPConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Field;

/**
 * @author ATracer
 * @modified Rolandas
 */
public class Configure extends AdminCommand {

    private static final ImmutableMap<String, Class<?>> commands = new ImmutableMap.Builder<String, Class<?>>()
            .put("admin", AdminConfig.class)
            .put("ai", AIConfig.class)
            .put("autogroup", AutoGroupConfig.class)
            .put("cache", CacheConfig.class)
            .put("craft", CraftConfig.class)
            .put("custom", CustomConfig.class)
            .put("developer", DeveloperConfig.class)
            .put("drop", DropConfig.class)
            .put("enchants", EnchantsConfig.class)
            .put("events", EventsConfig.class)
            .put("falldamage", FallDamageConfig.class)
            .put("gameserver", GSConfig.class)
            .put("geodata", GeoDataConfig.class)
            .put("group", GroupConfig.class)
            .put("html", HTMLConfig.class)
            .put("housing", HousingConfig.class)
            .put("ingameshop", InGameShopConfig.class)
            .put("legions", LegionConfig.class)
            .put("logging", LoggingConfig.class)
            .put("membership", MembershipConfig.class)
            .put("name", NameConfig.class)
            .put("periodicsave", PeriodicSaveConfig.class)
            .put("prices", PricesConfig.class)
            .put("punishment", PunishmentConfig.class)
            .put("ranking", RankingConfig.class)
            .put("rates", RateConfig.class)
            .put("security", SecurityConfig.class)
            .put("shutdown", ShutdownConfig.class)
            .put("siege", SiegeConfig.class)
            .put("thread", ThreadConfig.class)
            .put("weddings", WeddingsConfig.class)
            .put("world", WorldConfig.class)
            .put("ipconfig", IPConfig.class)
            .put("network", NetworkConfig.class)
            .build();

    public Configure() {
        super("configure");
    }

    @Override
    public void execute(Player player, String... params) {
        String command = "";
        if (params.length == 3) {
            // show
            command = params[0];
            if (!"show".equalsIgnoreCase(command)) {
                PacketSendUtility.sendMessage(player, "syntax //configure <set|show> <configname> <property> [<newvalue>]");
                return;
            }
        } else if (params.length == 4) {
            // set
            command = params[0];
            if (!"set".equalsIgnoreCase(command)) {
                PacketSendUtility.sendMessage(player, "syntax //configure <set|show> <configname> <property> [<newvalue>]");
                return;
            }
        } else {
            PacketSendUtility.sendMessage(player, "syntax //configure <set|show> <configname> <property> [<newvalue>]");
            return;
        }

        Class<?> classToMofify = commands.get(params[1].toLowerCase());

        if (command.equalsIgnoreCase("show")) {
            String fieldName = params[2];
            Field someField;
            try {
                someField = classToMofify.getDeclaredField(fieldName.toUpperCase());
                PacketSendUtility.sendMessage(player, "Current value is " + someField.get(null));
            } catch (Exception e) {
                PacketSendUtility.sendMessage(player, "Error! Wrong property or value.");
                return;
            }
        } else if (command.equalsIgnoreCase("set")) {
            String fieldName = params[2];
            String newValue = params[3];
            if (classToMofify != null) {
                Field someField;
                try {
                    someField = classToMofify.getDeclaredField(fieldName.toUpperCase());
                    Class<?> classType = someField.getType();
                    if (classType == String.class) {
                        someField.set(null, newValue);
                    } else if (classType == int.class || classType == Integer.class) {
                        someField.set(null, Integer.parseInt(newValue));
                    } else if (classType == Boolean.class || classType == boolean.class) {
                        someField.set(null, Boolean.valueOf(newValue));
                    } else if (classType == byte.class || classType == Byte.class) {
                        someField.set(null, Byte.valueOf(newValue));
                    } else if (classType == float.class || classType == Float.class) {
                        someField.set(null, Float.valueOf(newValue));
                    }

                } catch (Exception e) {
                    PacketSendUtility.sendMessage(player, "Error! Wrong property or value.");
                    return;
                }
            }
            PacketSendUtility.sendMessage(player, "Property changed and applyed");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //configure <set|show> <configname> <property> [<newvalue>]");
    }
}
