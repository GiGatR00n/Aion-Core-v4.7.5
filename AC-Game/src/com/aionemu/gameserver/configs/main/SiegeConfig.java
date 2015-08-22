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

/**
 * @author Sarynth, xTz, Source
 */
public class SiegeConfig {

    /**
     * Siege Enabled
     */
    @Property(key = "gameserver.siege.enable", defaultValue = "true")
    public static boolean SIEGE_ENABLED;
    /**
     * Siege Reward Rate
     */
    @Property(key = "gameserver.siege.medal.rate", defaultValue = "1")
    public static int SIEGE_MEDAL_RATE;
    /**
     * Siege sield Enabled
     */
    @Property(key = "gameserver.siege.shield.enable", defaultValue = "true")
    public static boolean SIEGE_SHIELD_ENABLED;
    /**
     * Balaur Assaults Enabled
     */
    @Property(key = "gameserver.siege.assault.enable", defaultValue = "false")
    public static boolean BALAUR_AUTO_ASSAULT;
    @Property(key = "gameserver.siege.assault.rate", defaultValue = "1")
    public static float BALAUR_ASSAULT_RATE;
    /**
     * Siege Race Protector spawn schedule
     */
    @Property(key = "gameserver.siege.protector.time", defaultValue = "0 0 21 ? * *")
    public static String RACE_PROTECTOR_SPAWN_SCHEDULE;
    /**
     * Berserker Sunayaka spawn time
     */
    @Property(key = "gameserver.sunayaka.time", defaultValue = "0 0 23 ? * *")
    public static String BERSERKER_SUNAYAKA_SPAWN_SCHEDULE;
    /**
     * Berserker Sunayaka spawn time
     */
    @Property(key = "gameserver.moltenus.time", defaultValue = "0 0 22 ? * SUN")
    public static String MOLTENUS_SPAWN_SCHEDULE;
    /**
     * Legendary npc's health mod
     */
    @Property(key = "gameserver.siege.health.mod", defaultValue = "false")
    public static boolean SIEGE_HEALTH_MOD_ENABLED;
    /**
     * Legendary npc's health multiplier
     */
    @Property(key = "gameserver.siege.health.multiplier", defaultValue = "1.0")
    public static double SIEGE_HEALTH_MULTIPLIER = 1.0;
    /**
     * Tiamat's Incarnation dispell avatars
     */
    @Property(key = "gameserver.siege.ida", defaultValue = "false")
    public static boolean SIEGE_IDA_ENABLED;
	
	@Property(key = "gameserver.agent.fight.time", defaultValue = "0 0 1 ? * *")
	public static String AGENT_FIGHT_SPAWN_SCHEDULE;
	
	/**
	 * Beritra Invasions
	 */
	@Property(key = "gameserver.beritra.enable", defaultValue = "true")
	public static boolean BERITRA_ENABLED;
	@Property(key = "gameserver.beritra.schedule", defaultValue = "0 0 4 ? * *")
	public static String BERITRA_SCHEDULE;
	@Property(key = "gameserver.beritra.duration", defaultValue = "2")
	public static int BERITRA_DURATION;
	
}
