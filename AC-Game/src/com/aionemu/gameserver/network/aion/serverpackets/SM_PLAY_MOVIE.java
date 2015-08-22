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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author -orz-, MrPoke
 */
public class SM_PLAY_MOVIE extends AionServerPacket {

    private int type = 1; // if 1: CutSceneMovies else CutScenes
    private int movieId = 0;
    private int id = 0; // id scene ?
    private int restrictionId;
    private int objectId;

    public SM_PLAY_MOVIE(int type, int movieId) {
        this.type = type;
        this.movieId = movieId;
    }

    public SM_PLAY_MOVIE(int type, int id, int movieId, int restrictionId) {
        this(type, movieId);
        this.id = id;
        this.restrictionId = restrictionId;
    }

    public SM_PLAY_MOVIE(int type, int id, int movieId, int restrictionId, int objectId) {
        this(type, id, movieId, restrictionId);
        this.objectId = objectId;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(type);
        writeD(objectId);
        writeD(id);
        writeH(movieId);
        writeD(restrictionId);
    }
}
