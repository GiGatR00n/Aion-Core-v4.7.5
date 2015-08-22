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
package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class LoggingConfig {

    @Property(key = "gameserver.enable.advanced.logging", defaultValue = "false")
    public static boolean ENABLE_ADVANCED_LOGGING;
    /**
     * Logging
     */
    @Property(key = "gameserver.log.audit", defaultValue = "true")
    public static boolean LOG_AUDIT;
    @Property(key = "gameserver.log.autogroup", defaultValue = "true")
    public static boolean LOG_AUTOGROUP;
    @Property(key = "gameserver.log.chat", defaultValue = "true")
    public static boolean LOG_CHAT;
    @Property(key = "gameserver.log.craft", defaultValue = "true")
    public static boolean LOG_CRAFT;
    @Property(key = "gameserver.log.faction", defaultValue = "false")
    public static boolean LOG_FACTION;
    @Property(key = "gameserver.log.gmaudit", defaultValue = "true")
    public static boolean LOG_GMAUDIT;
    @Property(key = "gameserver.log.ingameshop", defaultValue = "false")
    public static boolean LOG_INGAMESHOP;
    @Property(key = "gameserver.log.ingameshop.sql", defaultValue = "false")
    public static boolean LOG_INGAMESHOP_SQL;
    @Property(key = "gameserver.log.item", defaultValue = "true")
    public static boolean LOG_ITEM;
    @Property(key = "gameserver.log.kill", defaultValue = "false")
    public static boolean LOG_KILL;
    @Property(key = "gameserver.log.pl", defaultValue = "false")
    public static boolean LOG_PL;
    @Property(key = "gameserver.log.mail", defaultValue = "false")
    public static boolean LOG_MAIL;
    @Property(key = "gameserver.log.player.exchange", defaultValue = "false")
    public static boolean LOG_PLAYER_EXCHANGE;
    @Property(key = "gameserver.log.broker.exchange", defaultValue = "false")
    public static boolean LOG_BROKER_EXCHANGE;
    @Property(key = "gameserver.log.siege", defaultValue = "false")
    public static boolean LOG_SIEGE;
    @Property(key = "gameserver.log.sysmail", defaultValue = "false")
    public static boolean LOG_SYSMAIL;
    @Property(key = "gameserver.log.auction", defaultValue = "true")
    public static boolean LOG_HOUSE_AUCTION;
}
