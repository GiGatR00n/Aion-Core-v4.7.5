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
package mysql5;

/**
 * DAO utils for MySQL5
 *
 * @author SoulKeeper
 */
public class MySQL5DAOUtils {

    /**
     * Constant for MySQL name ;)
     */
    public static final String MYSQL_DB_NAME = "MySQL";

    /**
     * Returns true only if DB supports MySQL5
     *
     * @param db           database name
     * @param majorVersion major version
     * @param minorVersion minor version, ignored
     * @return supports or not
     */
    public static boolean supports(String db, int majorVersion, int minorVersion) {
        return MYSQL_DB_NAME.equals(db) && majorVersion == 5;
    }
}
