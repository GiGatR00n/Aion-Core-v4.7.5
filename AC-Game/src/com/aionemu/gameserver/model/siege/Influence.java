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
package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INFLUENCE_RATIO;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

import java.util.Iterator;

/**
 * Calculates fortresses as 10 points and artifacts as 1 point each. Need to
 * find retail calculation. (Upper forts worth more...)
 *
 * @author Sarynth
 * @updated Eloann
 */
public class Influence {

    private static final Influence instance = new Influence();
    private float inggison_e = 0;
    private float inggison_a = 0;
    private float inggison_b = 0;
    private float gelkmaros_e = 0;
    private float gelkmaros_a = 0;
    private float gelkmaros_b = 0;
    private float abyss_e = 0;
    private float abyss_a = 0;
    private float abyss_b = 0;
    private float tiamaranta_e = 0;
    private float tiamaranta_a = 0;
    private float tiamaranta_b = 0;
    private float katalam_e = 0;
    private float katalam_a = 0;
    private float katalam_b = 0;
    private float danaria_e = 0;
    private float danaria_a = 0;
    private float danaria_b = 0;
    private float global_e = 0;
    private float global_a = 0;
    private float global_b = 0;

    private Influence() {
        calculateInfluence();
    }

    public static Influence getInstance() {
        return instance;
    }

    /**
     * Recalculates Influence
     */
    public void recalculateInfluence() {
        calculateInfluence();
    }

    /**
     * calculate influence
     */
    private void calculateInfluence() {
        float balaurea = 0.0019512194f;
        float abyss = 0.006097561f;
        float e_inggison = 0;
        float a_inggison = 0;
        float b_inggison = 0;
        float t_inggison = 0;
        float e_gelkmaros = 0;
        float a_gelkmaros = 0;
        float b_gelkmaros = 0;
        float t_gelkmaros = 0;
        float e_abyss = 0;
        float a_abyss = 0;
        float b_abyss = 0;
        float t_abyss = 0;

        for (SiegeLocation sLoc : SiegeService.getInstance().getSiegeLocations().values()) {
            switch (sLoc.getWorldId()) {
                case 210050000:
                    if (sLoc instanceof FortressLocation) {
                        t_inggison += sLoc.getInfluenceValue();
                        switch (sLoc.getRace()) {
                            case ELYOS:
                                e_inggison += sLoc.getInfluenceValue();
                                break;
                            case ASMODIANS:
                                a_inggison += sLoc.getInfluenceValue();
                                break;
                            case BALAUR:
                                b_inggison += sLoc.getInfluenceValue();
                                break;
                        }
                    }
                    break;
                case 220070000:
                    if (sLoc instanceof FortressLocation) {
                        t_gelkmaros += sLoc.getInfluenceValue();
                        switch (sLoc.getRace()) {
                            case ELYOS:
                                e_gelkmaros += sLoc.getInfluenceValue();
                                break;
                            case ASMODIANS:
                                a_gelkmaros += sLoc.getInfluenceValue();
                                break;
                            case BALAUR:
                                b_gelkmaros += sLoc.getInfluenceValue();
                                break;
                        }
                    }
                    break;
                case 400010000:
                    t_abyss += sLoc.getInfluenceValue();
                    switch (sLoc.getRace()) {
                        case ELYOS:
                            e_abyss += sLoc.getInfluenceValue();
                            break;
                        case ASMODIANS:
                            a_abyss += sLoc.getInfluenceValue();
                            break;
                        case BALAUR:
                            b_abyss += sLoc.getInfluenceValue();
                            break;
                    }
                    break;
            }
        }

        inggison_e = e_inggison / t_inggison;
        inggison_a = a_inggison / t_inggison;
        inggison_b = b_inggison / t_inggison;

        gelkmaros_e = e_gelkmaros / t_gelkmaros;
        gelkmaros_a = a_gelkmaros / t_gelkmaros;
        gelkmaros_b = b_gelkmaros / t_gelkmaros;

        abyss_e = e_abyss / t_abyss;
        abyss_a = a_abyss / t_abyss;
        abyss_b = b_abyss / t_abyss;

        global_e = (inggison_e * balaurea + gelkmaros_e * balaurea + abyss_e * abyss) * 100f;
        global_a = (inggison_a * balaurea + gelkmaros_a * balaurea + abyss_a * abyss) * 100f;
        global_b = (inggison_b * balaurea + gelkmaros_b * balaurea + abyss_b * abyss) * 100f;
    }

    /**
     * Broadcast packet with influence update to all players. - Responsible for
     * the message "The Divine Fortress is now vulnerable."
     */
    @SuppressWarnings("unused")
    private void broadcastInfluencePacket() {
        SM_INFLUENCE_RATIO pkt = new SM_INFLUENCE_RATIO();

        Player player;
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            player = iter.next();
            PacketSendUtility.sendPacket(player, pkt);
        }
    }

    /**
     * @return elyos control
     */
    public float getGlobalElyosInfluence() {
        return this.global_e;
    }

    /**
     * @return asmos control
     */
    public float getGlobalAsmodiansInfluence() {
        return this.global_a;
    }

    /**
     * @return balaur control
     */
    public float getGlobalBalaursInfluence() {
        return this.global_b;
    }

    /**
     * @return elyos control
     */
    public float getInggisonElyosInfluence() {
        return this.inggison_e;
    }

    /**
     * @return asmos control
     */
    public float getInggisonAsmodiansInfluence() {
        return this.inggison_a;
    }

    /**
     * @return balaur control
     */
    public float getInggisonBalaursInfluence() {
        return this.inggison_b;
    }

    /**
     * @return elyos control
     */
    public float getGelkmarosElyosInfluence() {
        return this.gelkmaros_e;
    }

    /**
     * @return asmos control
     */
    public float getGelkmarosAsmodiansInfluence() {
        return this.gelkmaros_a;
    }

    /**
     * @return balaur control
     */
    public float getGelkmarosBalaursInfluence() {
        return this.gelkmaros_b;
    }

    /**
     * @return elyos control
     */
    public float getAbyssElyosInfluence() {
        return this.abyss_e;
    }

    /**
     * @return asmos control
     */
    public float getAbyssAsmodiansInfluence() {
        return this.abyss_a;
    }

    /**
     * @return balaur control
     */
    public float getAbyssBalaursInfluence() {
        return this.abyss_b;
    }

    /**
     * @return elyos control
     */
    public float getTiamarantaElyosInfluence() {
        return this.tiamaranta_e;
    }

    /**
     * @return asmos control
     */
    public float getTiamarantaAsmodiansInfluence() {
        return this.tiamaranta_a;
    }

    /**
     * @return balaur control
     */
    public float getTiamarantaBalaursInfluence() {
        return this.tiamaranta_b;
    }

    /**
     * @return elyos control
     */
    public float getKatalamElyosInfluence() {
        return katalam_e;
    }

    /**
     * @return asmos control
     */
    public float getKatalamAsmodiansInfluence() {
        return katalam_a;
    }

    /**
     * @return balaur control
     */
    public float getKatalamBalaursInfluence() {
        return katalam_b;
    }

    /**
     * @return elyos control
     */
    public float getDanariaElyosInfluence() {
        return danaria_e;
    }

    /**
     * @return asmos control
     */
    public float getDanariaAsmodiansInfluence() {
        return danaria_a;
    }

    /**
     * @return balaur control
     */
    public float getDanariaBalaursInfluence() {
        return danaria_b;
    }

    /**
     * @return float containing dmg modifier for disadvantaged race
     */
    public float getPvpRaceBonus(Race attRace) {
        float bonus = 1;
        float elyos = getGlobalElyosInfluence();
        float asmo = getGlobalAsmodiansInfluence();
        switch (attRace) {
            case ASMODIANS:
                if (elyos >= 0.81f && asmo <= 0.10f) {
                    bonus = 1.2f;
                } else if (elyos >= 0.81f || (elyos >= 0.71f && asmo <= 0.10f)) {
                    bonus = 1.15f;
                } else if (elyos >= 0.71f) {
                    bonus = 1.1f;
                }
                break;
            case ELYOS:
                if (asmo >= 0.81f && elyos <= 0.10f) {
                    bonus = 1.2f;
                } else if (asmo >= 0.81f || (asmo >= 0.71f && elyos <= 0.10f)) {
                    bonus = 1.15f;
                } else if (asmo >= 0.71f) {
                    bonus = 1.1f;
                }
                break;
        }
        return bonus;
    }
}
