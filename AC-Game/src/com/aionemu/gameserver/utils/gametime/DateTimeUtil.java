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
package com.aionemu.gameserver.utils.gametime;

import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.GSConfig;

/**
 * @author Rolandas
 */
public final class DateTimeUtil {

    static Logger log = LoggerFactory.getLogger(DateTimeUtil.class);
    private static boolean canApplyZoneChange = false;

    public static void init() {
        try {
            if (!GSConfig.TIME_ZONE_ID.isEmpty()) {
                // just check the validity on start (if invalid zone specified in the switch, default id used)
                DateTimeZone.forID(System.getProperty("Duser.timezone"));
                DateTimeZone.forID(GSConfig.TIME_ZONE_ID);
                canApplyZoneChange = true;
            }
        } catch (Throwable e) {
            log.error("Invalid or not supported timezones specified!!!\n"
                    + "Use both -Duser.timezone=\"timezone_id\" switch from command line\n"
                    + "and add a valid value for GSConfig.TIME_ZONE_ID");
        }
    }

    // Get now date and time
    public static DateTime getDateTime() {
        DateTime dt = new DateTime();
        if (canApplyZoneChange) {
            return dt.withZoneRetainFields(DateTimeZone.forID(GSConfig.TIME_ZONE_ID));
        }
        return dt;
    }

    public static DateTime getDateTime(String isoDateTime) {
        DateTime dt = new DateTime(isoDateTime);
        if (canApplyZoneChange) {
            return dt.withZoneRetainFields(DateTimeZone.forID(GSConfig.TIME_ZONE_ID));
        }
        return dt;
    }

    public static DateTime getDateTime(GregorianCalendar calendar) {
        DateTime dt = new DateTime(calendar);
        if (canApplyZoneChange) {
            return dt.withZoneRetainFields(DateTimeZone.forID(GSConfig.TIME_ZONE_ID));
        }
        return dt;
    }

    public static DateTime getDateTime(long millisSinceSeventies) {
        DateTime dt = new DateTime(millisSinceSeventies);
        if (canApplyZoneChange) {
            return dt.withZoneRetainFields(DateTimeZone.forID(GSConfig.TIME_ZONE_ID));
        }
        return dt;
    }

    public static boolean canApplyZoneChange() {
        return canApplyZoneChange;
    }
}
