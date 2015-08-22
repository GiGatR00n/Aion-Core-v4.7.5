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
package com.aionemu.gameserver.model.gameobjects;

import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author ATracer
 */
public enum PetEmote {

    MOVE_STOP(0),
    MOVETO(12),
    ALARM(-114),
    UNK_M110(-110),
    UNK_M111(-111),
    UNK_M123(-123),
    FLY(-125),
    UNK_M128(-128),
    UNKNOWN(255);
    private static TIntObjectHashMap<PetEmote> petEmotes;

    static {
        petEmotes = new TIntObjectHashMap<PetEmote>();
        for (PetEmote emote : values()) {
            petEmotes.put(emote.getEmoteId(), emote);
        }
    }

    private int emoteId;

    private PetEmote(int emoteId) {
        this.emoteId = emoteId;
    }

    public int getEmoteId() {
        return emoteId;
    }

    public static PetEmote getEmoteById(int emoteId) {
        PetEmote emote = petEmotes.get(emoteId);
        return emote != null ? emote : UNKNOWN;
    }
}
