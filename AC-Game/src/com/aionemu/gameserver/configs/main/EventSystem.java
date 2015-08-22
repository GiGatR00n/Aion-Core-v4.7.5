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
 * Created by Kill3r
 */
public class EventSystem {

    /**
     * FFA Map
     * Default : FFAInstance : 320110000 MapID
     * After writing the Map ID , you need to go to here and change this part at top
     * \AL-Game\data\scripts\system\handlers\instance\FFAInstance.java -- @InstanceID(MAP ID HERE)
     *
     * NOTE: Also you need to remove the spawns of the given map ip,
     * By going static_data > spawns > Instances > [mapid_name].xml <-- just remove it or take backup to desktop
     * Also if that instance has a script file,
     * remove that by going data > script > handlers > ... > .. Instances > [mapName].java   <--- remove it or move to desktop for later use
     */
    @Property(key = "gameserver.eventsystem.ffa.mapid", defaultValue = "320110000")
    public static int EVENTSYSTEM_FFAMAP;

    /**
     * FFA Map's Spawn Points, You need atleast 10 points
     * X:
     * Y:
     * Z:
     * Float values required
     */

    /**
     * Point One
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.onex", defaultValue = "282.82547")
    public static float FFA_SPAWNPOINT_1X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.oney", defaultValue = "501.17563")
    public static float FFA_SPAWNPOINT_1Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.onez", defaultValue = "211.67049")
    public static float FFA_SPAWNPOINT_1Z;

    /**
     * Point Two
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.twox", defaultValue = "603")
    public static float FFA_SPAWNPOINT_2X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.twoy", defaultValue = "527")
    public static float FFA_SPAWNPOINT_2Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.twoz", defaultValue = "200")
    public static float FFA_SPAWNPOINT_2Z;

    /**
     * Point Three
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.threex", defaultValue = "528.2479")
    public static float FFA_SPAWNPOINT_3X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.threey", defaultValue = "207.35722")
    public static float FFA_SPAWNPOINT_3Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.threez", defaultValue = "497.95782")
    public static float FFA_SPAWNPOINT_3Z;

    /**
     * Point Four
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.fourx", defaultValue = "342.00424")
    public static float FFA_SPAWNPOINT_4X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.foury", defaultValue = "515.91003")
    public static float FFA_SPAWNPOINT_4Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.fourz", defaultValue = "209.9201")
    public static float FFA_SPAWNPOINT_4Z;

    /**
     * Point Five
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.fivex", defaultValue = "341.80997")
    public static float FFA_SPAWNPOINT_5X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.fivey", defaultValue = "487.36844")
    public static float FFA_SPAWNPOINT_5Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.fivez", defaultValue = "209.9201")
    public static float FFA_SPAWNPOINT_5Z;

    /**
     * Point Six
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.sixx", defaultValue = "455.51602")
    public static float FFA_SPAWNPOINT_6X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.sixy", defaultValue = "560.1399")
    public static float FFA_SPAWNPOINT_6Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.sixz", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_6Z;

    /**
     * Point Seven
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.sevenx", defaultValue = "444.06195")
    public static float FFA_SPAWNPOINT_7X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.seveny", defaultValue = "505.53363")
    public static float FFA_SPAWNPOINT_7Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.sevenz", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_7Z;

    /**
     * Point Eight
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.eightx", defaultValue = "407.76617")
    public static float FFA_SPAWNPOINT_8X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.eighty", defaultValue = "533.2193")
    public static float FFA_SPAWNPOINT_8Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.eightz", defaultValue = "211.37567")
    public static float FFA_SPAWNPOINT_8Z;

    /**
     * Point Nine
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.ninex", defaultValue = "476.5301")
    public static float FFA_SPAWNPOINT_9X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.niney", defaultValue = "556.405")
    public static float FFA_SPAWNPOINT_9Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.ninez", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_9Z;

    /**
     * Point Ten
     */
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.tenx", defaultValue = "477.02695")
    public static float FFA_SPAWNPOINT_10X;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.teny", defaultValue = "493.03632")
    public static float FFA_SPAWNPOINT_10Y;
    @Property(key = "gameserver.eventsystem.ffa.Coordinates.tenz", defaultValue = "204.69067")
    public static float FFA_SPAWNPOINT_10Z;

    /**
     * FFA Entering and Leaving Messages
     */

    /**
     * Welcome Message
     * Default : Welcome to FFA
     */
    @Property(key = "gameserver.eventsystem.ffa.entermessage", defaultValue = "Welcome to FFA")
    public static String FFA_WELCOME_MSG;

    /**
     * Leaving Message
     * Default : Leaving so Soon?
     */
    @Property(key = "gameserver.eventsystem.ffa.leavemessage", defaultValue = "Leaving so soon?")
    public static String FFA_LEAVE_MSG;

    /**
     * Killing Spree Settings and Announce Name
     * AP Reward / GP Reward
     * Reward increases on each 5 kills
     */

    /**
     * Announcer Name for FFA
     * Default : FFA
     * Example : [FFA] Player1 has just killed Player2
     */
    @Property(key = "gameserver.eventsystem.ffa.announcername", defaultValue = "FFA")
    public static String FFA_ANNOUNCER_NAME;

    /**
     * Bonus HP/MP/DP You gain after each kill
     * Default : 1500 HP
     * Default : 3000 MP
     * Default : 800 DP
     */
    @Property(key = "gameserver.eventsystem.ffa.kill.hp", defaultValue = "1500")
    public static int FFA_KILL_BONUS_HP;

    @Property(key = "gameserver.eventsystem.ffa.kill.mp", defaultValue = "3000")
    public static int FFA_KILL_BONUS_MP;

    @Property(key = "gameserver.eventsystem.ffa.kill.dp", defaultValue = "800")
    public static int FFA_KILL_BONUS_DP;


    /**
     * After 5th Kill Reward
     * First AP then GP
     * Default : AP = 5000
     * Default : GP = 150
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap1", defaultValue = "5000")
    public static int FFA_REWARD_AP1;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp1", defaultValue = "150")
    public static int FFA_REWARD_GP1;

    /**
     * After 10th Kill Reward
     * First AP then GP
     * Default : AP = 7500
     * Default : GP = 250
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap2", defaultValue = "7500")
    public static int FFA_REWARD_AP2;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp2", defaultValue = "250")
    public static int FFA_REWARD_GP2;

    /**
     * After 15th Kill Reward
     * First AP then GP
     * Default : AP = 10000
     * Default : GP = 450
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap3", defaultValue = "10000")
    public static int FFA_REWARD_AP3;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp3", defaultValue = "450")
    public static int FFA_REWARD_GP3;

    /**
     * After 20th Kill Reward
     * First AP then GP
     * Default : AP = 15000
     * Default : GP = 650
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap4", defaultValue = "15000")
    public static int FFA_REWARD_AP4;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp4", defaultValue = "650")
    public static int FFA_REWARD_GP4;

    /**
     * After 25th Kill Reward
     * First AP then GP
     * Default : AP = 20000
     * Default : GP = 850
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap5", defaultValue = "20000")
    public static int FFA_REWARD_AP5;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp5", defaultValue = "850")
    public static int FFA_REWARD_GP5;

    /**
     * After 30th Kill Reward
     * First AP then GP
     * Default : AP = 25000
     * Default : GP = 1000
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap6", defaultValue = "25000")
    public static int FFA_REWARD_AP6;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp6", defaultValue = "1000")
    public static int FFA_REWARD_GP6;

    /**
     * After 35th Kill Reward
     * First AP then GP
     * Default : AP = 30000
     * Default : GP = 1250
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap7", defaultValue = "30000")
    public static int FFA_REWARD_AP7;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp7", defaultValue = "1250")
    public static int FFA_REWARD_GP7;

    /**
     * After 40th Kill Reward
     * First AP then GP
     * Default : AP = 35000
     * Default : GP = 1500
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap8", defaultValue = "35000")
    public static int FFA_REWARD_AP8;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp8", defaultValue = "1500")
    public static int FFA_REWARD_GP8;

    /**
     * After 45th Kill Reward
     * First AP then GP
     * Default : AP = 40000
     * Default : GP = 1750
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap9", defaultValue = "40000")
    public static int FFA_REWARD_AP9;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp9", defaultValue = "1750")
    public static int FFA_REWARD_GP9;

    /**
     * After 50th Kill Reward
     * First AP then GP
     * Default : AP = 50000
     * Default : GP = 2000
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap10", defaultValue = "50000")
    public static int FFA_REWARD_AP10;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp10", defaultValue = "2000")
    public static int FFA_REWARD_GP10;

    /**
     * After 50 kills and higher
     * First AP then GP
     * Default : AP = 55000
     * Default : GP = 2250
     */
    @Property(key = "gameserver.eventsystem.ffa.reward.ap11", defaultValue = "55000")
    public static int FFA_REWARD_AP11;
    @Property(key = "gameserver.eventsystem.ffa.reward.gp11", defaultValue = "2250")
    public static int FFA_REWARD_GP11;

    /**
     *  Returning Settings ( .ffa leave)
     *  Default : False
     *  False - The player will be ported to Pandaemonium/Sanctum
     *  True - The player will be ported to the location where he typed .ffa enter
     */
    @Property(key = "gameserver.eventsystem.ffa.returnposition", defaultValue = "false")
    public static boolean FFA_RETURN_TO_PREVLOCK;    

    /**
     * Crazy Daeva Event
     */
    @Property(key = "gameserver.crazy.daeva.enable", defaultValue = "false")
    public static boolean ENABLE_CRAZY;
    @Property(key = "gameserver.crazy.daeva.tag", defaultValue = "<Crazy>")
    public static String CRAZY_TAG;
    @Property(key = "gameserver.crazy.daeva.lowest.rnd", defaultValue = "10")
    public static int CRAZY_LOWEST_RND;
    @Property(key = "gameserver.crazy.daeva.endtime", defaultValue = "300000")
    public static int CRAZY_ENDTIME;
	
	/**
     * Battleground System
     */
    @Property(key = "gameserver.battleground.enable", defaultValue = "false")
    public static boolean BATTLEGROUNDS_ENABLED;

    /**
     * One vs One Event (Compeleted)
     */
    @Property(key = "gameserver.onevsone.enable", defaultValue = "false")
    public static boolean ENABLE_ONEVONE;

    /**
     * Item Rewarding For Winner
     */
    @Property(key = "gameserver.onevsone.itemreward.enable", defaultValue = "false")
    public static boolean ITEMREWARD_ENABLE;

    /**
     * How Many Map is Used for 1 vs 1 System?
     */
    @Property(key = "gameserver.onevsone.mapcount", defaultValue = "1")
    public static int ONEVSONE_MAPCNT;

    /**
     * MAP ID 1 for the Event
     */
    @Property(key = "gameserver.onevsone.map.id", defaultValue = "300240000")
    public static int ONEVSONE_MAP;

    /*
    * 1st Coordinates for the Player Spawn.
     */
    @Property(key = "gameserver.onevsone.point.x1", defaultValue = "352.30402")
    public static float ONEVSONE_POINTX_1;

    @Property(key = "gameserver.onevsone.point.y1", defaultValue = "850.4435")
    public static float ONEVSONE_POINTY_1;

    @Property(key = "gameserver.onevsone.point.z1", defaultValue = "902.4922")
    public static float ONEVSONE_POINTZ_1;

    /*
    * 2nd Coordinates for the Player Spawn
     */
    @Property(key = "gameserver.onevsone.point.x2", defaultValue = "278.7154")
    public static float ONEVSONE_POINTX_2;

    @Property(key = "gameserver.onevsone.point.y2", defaultValue = "886.48486")
    public static float ONEVSONE_POINTY_2;

    @Property(key = "gameserver.onevsone.point.z2", defaultValue = "902.34296")
    public static float ONEVSONE_POINTZ_2;

    /**
     * MAP ID 2 for the Event
     */
    @Property(key = "gameserver.onevsone.map.id2", defaultValue = "301310000")
    public static int ONEVSONE_MAP2;

    /*
    * 1nd Coordinates for the Player Spawn (Map2)
     */
    @Property(key = "gameserver.onevsone.point2.x1", defaultValue = "288.44086")
    public static float ONEVSONE_POINT2X_1;

    @Property(key = "gameserver.onevsone.point2.y1", defaultValue = "284.43228")
    public static float ONEVSONE_POINT2Y_1;

    @Property(key = "gameserver.onevsone.point2.z1", defaultValue = "92.97291")
    public static float ONEVSONE_POINT2Z_1;

    /*
    * 2nd Coordinates for the Player Spawn (Map2)
     */
    @Property(key = "gameserver.onevsone.point2.x2", defaultValue = "240.41732")
    public static float ONEVSONE_POINT2X_2;

    @Property(key = "gameserver.onevsone.point2.y2", defaultValue = "233.75545")
    public static float ONEVSONE_POINT2Y_2;

    @Property(key = "gameserver.onevsone.point2.z2", defaultValue = "92.98036")
    public static float ONEVSONE_POINT2Z_2;

    /**
     * MAP ID 3 for the Event
     */
    @Property(key = "gameserver.onevsone.map.id3", defaultValue = "320150000")
    public static int ONEVSONE_MAP3;

    /*
    * 1nd Coordinates for the Player Spawn (Map3)
     */
    @Property(key = "gameserver.onevsone.point3.x1", defaultValue = "600.0243")
    public static float ONEVSONE_POINT3X_1;

    @Property(key = "gameserver.onevsone.point3.y1", defaultValue = "231.07872")
    public static float ONEVSONE_POINT3Y_1;

    @Property(key = "gameserver.onevsone.point3.z1", defaultValue = "66.25")
    public static float ONEVSONE_POINT3Z_1;

    /*
    * 2nd Coordinates for the Player Spawn (Map3)
     */
    @Property(key = "gameserver.onevsone.point3.x2", defaultValue = "521.56116")
    public static float ONEVSONE_POINT3X_2;

    @Property(key = "gameserver.onevsone.point3.y2", defaultValue = "203.11626")
    public static float ONEVSONE_POINT3Y_2;

    @Property(key = "gameserver.onevsone.point3.z2", defaultValue = "66.875")
    public static float ONEVSONE_POINT3Z_2;

    /*
     * ARENA MASTER NAME
     */
    @Property(key = "gameserver.onevsone.master.name", defaultValue = "ArenaMaster")
    public static String ONEVSONE_ARENAMASTER;

    /*
     * Item 1
     */
    @Property(key = "gameserver.onevsone.item1", defaultValue = "166020000") // omega item
    public static int ITEM1;

    /**
     * Item 2
     */
    @Property(key = "gameserver.onevsone.item2", defaultValue = "166030005") // TS item
    public static int ITEM2;

    /*
     * Quantity for Item1
     */
    @Property(key = "gameserver.onevsone.quant1", defaultValue = "1")
    public static int QUANTITY1;

    /*
     * Quantity for Item2
     */
    @Property(key = "gameserver.onevsone.quant2", defaultValue = "1")
    public static int QUANTITY2;

    /**
     * Enable AP Rewarding
     */
    @Property(key = "gameserver.onevsone.ap.enable", defaultValue = "true")
    public static boolean ENABLE_AP_REWARD;

    /**
     * Enable GP Rewarding
     */
    @Property(key = "gameserver.onevsone.gp.enable", defaultValue = "true")
    public static boolean ENABLE_GP_REWARD;

    /**
     * Enable Toll Rewarding
     */
    @Property(key = "gameserver.onevsone.toll.enable", defaultValue = "true")
    public static boolean ENABLE_TOLL_REWARD;

    /*
     * AP Winner
     */
    @Property(key = "gameserver.onevsone.ap.reward", defaultValue = "5000")
    public static int AP_REWARD;

    /*
     * GP Winner
     */
    @Property(key = "gameserver.onevsone.gp.reward", defaultValue = "500")
    public static int GP_REWARD;

    /*
     * GP Winner
     */
    @Property(key = "gameserver.onevsone.toll.reward", defaultValue = "50")
    public static int TOLL_REWARD;

    /**
     * RemoveCd Settings
     */
    @Property(key = "gameserver.onevsone.removecd", defaultValue = "true")
    public static boolean ENABLE_REMOVECD;

    /*
     * Touch Xform Skills or not? while removecd'ing at teleport
     */
    @Property(key = "gameserver.onevsone.rc.xform", defaultValue = "false")
    public static boolean TOUCH_XFORM_SKILL;

}
