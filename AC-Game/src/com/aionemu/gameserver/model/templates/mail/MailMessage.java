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
package com.aionemu.gameserver.model.templates.mail;

/**
 * @author kosyachok
 */
public enum MailMessage {

    MAIL_SEND_SECCESS(0),
    NO_SUCH_CHARACTER_NAME(1),
    RECIPIENT_MAILBOX_FULL(2),
    MAIL_IS_ONE_RACE_ONLY(3),
    YOU_ARE_IN_RECIPIENT_IGNORE_LIST(4),
    RECIPIENT_IGNORING_MAIL_FROM_PLAYERS_LOWER_206_LVL(5), // WTF??
    MAILSPAM_WAIT_FOR_SOME_TIME(6);
    private int id;

    private MailMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
