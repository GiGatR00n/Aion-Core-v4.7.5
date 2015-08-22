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
package com.aionemu.gameserver.model.templates.stats;

import javax.xml.bind.annotation.*;

/**
 * This class is only a container for Stats. Created on: 04.08.2009 14:59:10
 *
 * @author Aquanox, Dr2co
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "stats_template")
public abstract class StatsTemplate {

    @XmlAttribute(name = "maxHp")
    private int maxHp;
    @XmlAttribute(name = "maxMp")
    private int maxMp;
    @XmlAttribute(name = "evasion")
    private int evasion;
    @XmlAttribute(name = "block")
    private int block;
    @XmlAttribute(name = "parry")
    private int parry;
    @XmlAttribute(name = "main_hand_attack")
    private int mainHandAttack;
    @XmlAttribute(name = "main_hand_accuracy")
    private int mainHandAccuracy;
    @XmlAttribute(name = "main_hand_crit_rate")
    private int mainHandCritRate;
    @XmlAttribute(name = "magic_accuracy")
    private int magicAccuracy;
    @XmlElement
    protected CreatureSpeeds speeds;

    /* ======================================= */
    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    /* ======================================= */
    public float getWalkSpeed() {
        return speeds == null ? 0 : speeds.getWalkSpeed();
    }

    public float getRunSpeed() {
        return speeds == null ? 0 : speeds.getRunSpeed();
    }

    public float getGroupWalkSpeed() {
        return getWalkSpeed();
    }

    public float getRunSpeedFight() {
        return getRunSpeed();
    }

    public float getGroupRunSpeedFight() {
        return getRunSpeed();
    }

    public float getFlySpeed() {
        return speeds == null ? 0 : speeds.getFlySpeed();
    }

    public void setWalkSpeed(CreatureSpeeds walkSpeed) {
        this.speeds = walkSpeed;
    }

    public void setRunSpeed(CreatureSpeeds runSpeed) {
        this.speeds = runSpeed;
    }

    /* ======================================= */
    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        this.evasion = evasion;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getParry() {
        return parry;
    }

    public void setParry(int parry) {
        this.parry = parry;
    }

    /* ======================================= */
    public int getMainHandAttack() {
        return mainHandAttack;
    }

    public int getMainHandAccuracy() {
        return mainHandAccuracy;
    }

    public int getMainHandCritRate() {
        return mainHandCritRate;
    }

    /* ======================================= */
    public int getMagicAccuracy() {
        return magicAccuracy;
    }
}
