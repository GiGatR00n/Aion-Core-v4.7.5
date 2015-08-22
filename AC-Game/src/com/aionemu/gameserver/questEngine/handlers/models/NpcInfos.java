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
package com.aionemu.gameserver.questEngine.handlers.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Hilgert
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NpcInfos")
public class NpcInfos {

    @XmlAttribute(name = "npc_id", required = true)
    protected int npcId;
    @XmlAttribute(name = "var", required = true)
    protected int var;
    @XmlAttribute(name = "quest_dialog", required = true)
    protected int DialogAction;
    @XmlAttribute(name = "close_dialog")
    protected int closeDialog;
    @XmlAttribute(name = "movie")
    protected int movie;

    /**
     * Gets the value of the npcId property.
     */
    public int getNpcId() {
        return npcId;
    }

    /**
     * Gets the value of the var property.
     */
    public int getVar() {
        return var;
    }

    /**
     * Gets the value of the DialogAction property.
     */
    public int getQuestDialog() {
        return DialogAction;
    }

    /**
     * Gets the value of the closeDialog property.
     */
    public int getCloseDialog() {
        return closeDialog;
    }

    /**
     * @return the movie
     */
    public int getMovie() {
        return movie;
    }

    /**
     * @param movie the movie to set
     */
    public void setMovie(int movie) {
        this.movie = movie;
    }
}
