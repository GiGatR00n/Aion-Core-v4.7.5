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
package com.aionemu.loginserver.network.aion;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.loginserver.model.Account;

/**
 * @author -Nemesiss-
 */
public class SessionKey {

    /**
     * accountId - will be used for authentication on Game Server side.
     */
    public final int accountId;
    /**
     * login ok key
     */
    public final int loginOk;
    /**
     * play ok1 key
     */
    public final int playOk1;
    /**
     * play ok2 key
     */
    public final int playOk2;

    /**
     * Create new SesionKey for this Account
     *
     * @param acc
     */
    public SessionKey(Account acc) {
        this.accountId = acc.getId();
        this.loginOk = Rnd.nextInt();
        this.playOk1 = Rnd.nextInt();
        this.playOk2 = Rnd.nextInt();
    }

    /**
     * Create new SesionKey with given values.
     *
     * @param accountId
     * @param loginOk
     * @param playOk1
     * @param playOk2
     */
    public SessionKey(int accountId, int loginOk, int playOk1, int playOk2) {
        this.accountId = accountId;
        this.loginOk = loginOk;
        this.playOk1 = playOk1;
        this.playOk2 = playOk2;
    }

    /**
     * Check if given values are ok.
     *
     * @param accountId
     * @param loginOk
     * @return true if accountId and loginOk match this SessionKey
     */
    public boolean checkLogin(int accountId, int loginOk) {
        return this.accountId == accountId && this.loginOk == loginOk;
    }

    /**
     * Check if this SessionKey have the same values.
     *
     * @param key
     * @return true if key match this SessionKey.
     */
    public boolean checkSessionKey(SessionKey key) {
        return (playOk1 == key.playOk1 && accountId == key.accountId && playOk2 == key.playOk2 && loginOk == key.loginOk);
    }
}
