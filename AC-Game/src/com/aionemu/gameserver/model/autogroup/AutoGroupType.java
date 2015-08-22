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
package com.aionemu.gameserver.model.autogroup;

import com.aionemu.gameserver.dataholders.DataManager;

import java.util.List;

/**
 * @author xTz
 * @author GiGatR00n v4.7.5.x
 */
public enum AutoGroupType {

    BARANATH_DREDGION(1, 600000, 12) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoDredgionInstance();
        }
    },
    CHANTRA_DREDGION(2, 600000, 12) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoDredgionInstance();
        }
    },
    TERATH_DREDGION(3, 600000, 12) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoDredgionInstance();
        }
    },
    ELYOS_FIRE_TEMPLE(4, 300000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    NOCHSANA_TRAINING_CAMP(5, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    DARK_POETA(6, 1200000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    STEEL_RAKE(7, 1200000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    UDAS_TEMPLE(8, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    LOWER_UDAS_TEMPLE(9, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    EMPYREAN_CRUCIBLE(11, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ASMODIANS_FIRE_TEMPLE(14, 300000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ARENA_OF_CHAOS_1(21, 110000, 8, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_CHAOS_2(22, 110000, 8, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_CHAOS_3(23, 110000, 8, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_DISCIPLINE_1(24, 110000, 2, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_DISCIPLINE_2(25, 110000, 2, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_DISCIPLINE_3(26, 110000, 2, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    CHAOS_TRAINING_GROUNDS_1(27, 110000, 8, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    CHAOS_TRAINING_GROUNDS_2(28, 110000, 8, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    CHAOS_TRAINING_GROUNDS_3(29, 110000, 8, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    DISCIPLINE_TRAINING_GROUNDS_1(30, 110000, 2, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    DISCIPLINE_TRAINING_GROUNDS_2(31, 110000, 2, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    DISCIPLINE_TRAINING_GROUNDS_3(32, 110000, 2, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_HARMONY_1(33, 110000, 6, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    ARENA_OF_HARMONY_2(34, 110000, 6, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    ARENA_OF_HARMONY_3(35, 110000, 6, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    ARENA_OF_GLORY_1(38, 110000, 4, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_CHAOS_4(39, 110000, 8, 4) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_DISCIPLINE_4(40, 110000, 2, 4) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    ARENA_OF_HARMONY_4(41, 110000, 6, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    ARENA_OF_GLORY_2(42, 110000, 4, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    CHAOS_TRAINING_GROUNDS_4(43, 110000, 8, 4) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    DISCIPLINE_TRAINING_GROUNDS_4(44, 110000, 2, 4) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoPvPFFAInstance();
        }
    },
    HARAMONIOUS_TRAINING_CENTER_4(45, 110000, 6, 4) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    HARAMONIOUS_TRAINING_CENTER_1(101, 110000, 6, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    HARAMONIOUS_TRAINING_CENTER_2(102, 110000, 6, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    HARAMONIOUS_TRAINING_CENTER_3(103, 110000, 6, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    UNITY_TRAINING_GROUNDS_1(104, 110000, 4, 1) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    UNITY_TRAINING_GROUNDS_2(105, 110000, 4, 2) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    UNITY_TRAINING_GROUNDS_3(106, 110000, 4, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoHarmonyInstance();
        }
    },
    KAMAR_BATTLEFIELD(107, 600000, 24) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoKamarBattlefieldInstance();
        }
    },
    ENGULFED_OPHIDAN_BRIDGE(108, 600000, 12) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoOphidanBridgeWarInstance();
        }
    },
    IRON_WALL_WARFRONT(109, 600000, 48) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoEternalBastionWarInstance();
        }
    },
    IDGEL_DOME(111, 600000, 12) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoIdgelDomeInstance();
        }
    },
    UNSTABLE_ABYSSAL_SPLINTER_ELYOS(201, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    UNSTABLE_ABYSSAL_SPLINTER_ASMODIANS(206, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    FIRE_TEMPLE(302, 300000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    NOCHSANA_TRAINING_CAMP_2(303, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    DARK_POETA_2(304, 1200000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    THEOBOMOS_LAB(305, 1200000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ADMA_STRONGHOLD(306, 1200000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    DRAUPNIR_CAVE(307, 1200000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    STEEL_RAKE_2(308, 1200000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    UDAS_TEMPLE_2(309, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    LOWER_UDAS_TEMPLE_2(310, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    EMPYREAN_CRUCIBLE_2(311, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    RAKSANG(312, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    RENTUS_BASE(313, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    OPHIDAN_BRIDGE(314, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    INDRATU_FORTRESS(315, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    DANUAR_RELIQUARY(316, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    SAURO_MILITARY_SUPPLY_BASE(317, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    AETHEROGENETICS_LAB(318, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ELEMENTIS_FOREST(319, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ARGENT_MANOR(320, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    MUADA_TRENCHER(321, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    DRAGON_LORD_REFUGE(322, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ALQUIMIA_RESEARCH_CENTER(323, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    INFINITY_SHARD(324, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    IDGEL_RESEARCH_CENTER(325, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    VOID_CUBE(326, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    STEEL_ROSE_CARGO(327, 600000, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    STEEL_ROSE_QUARTERS(328, 600000, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    STEEL_ROSE_DECK(329, 600000, 3) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    NIGHTMARE_CIRCUS(330, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    BESHMUNDIR_TEMPLE_NORMAL(331, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    BESHMUNDIR_TEMPLE_HARD(332, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    TIAMAT_STRONGHOLD(333, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    AZOTURAN_FORTRESS(334, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ETERNAL_BASTION(335, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    DANUAR_SANCTUARY(336, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    RUKIBUKI_CIRCUS_TROUPE_CAMP(337, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    ILLUMINARY_OBELISK(338, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    SHUGO_IMPERIAL_TOMB(339, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    LUCKY_OPHIDAN_BRIDGE(340, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    },
    LUCKY_DANUAR_RELIQUARY(341, 600000, 6) {
        @Override
        AutoInstance newAutoInstance() {
            return new AutoGeneralInstance();
        }
    };
    private int instanceMaskId;
    private int time;
    private byte playerSize;
    private byte difficultId;
    private AutoGroup template;
    
    private AutoGroupType(int instanceMaskId, int time, int playerSize, int difficultId) {
        this(instanceMaskId, time, playerSize);
        this.difficultId = (byte) difficultId;
    }

    private AutoGroupType(int instanceMaskId, int time, int playerSize) {
        this.instanceMaskId = instanceMaskId;
        this.time = time;
        this.playerSize = (byte) playerSize;
        template = DataManager.AUTO_GROUP.getTemplateByInstaceMaskId(this.instanceMaskId);
    }

    public int getInstanceMapId() {
        return template.getInstanceId();
    }

    public byte getPlayerSize() {
        return playerSize;
    }

    public int getInstanceMaskId() {
        return instanceMaskId;
    }

    public int getNameId() {
        return template.getNameId();
    }

    public int getTittleId() {
        return template.getTitleId();
    }

    public int getTime() {
        return time;
    }

    public int getMinLevel() {
        return template.getMinLvl();
    }

    public int getMaxLevel() {
        return template.getMaxLvl();
    }

    public boolean hasRegisterGroup() {
        return template.hasRegisterGroup();
    }

    public boolean hasRegisterQuick() {
        return template.hasRegisterQuick();
    }

    public boolean hasRegisterNew() {
        return template.hasRegisterNew();
    }

    public boolean containNpcId(int npcId) {
        return template.getNpcIds().contains(npcId);
    }

    public List<Integer> getNpcIds() {
        return template.getNpcIds();
    }

    public boolean isDredgion() {
        switch (this) {
            case BARANATH_DREDGION:
            case CHANTRA_DREDGION:
            case TERATH_DREDGION:
                return true;
        }
        return false;
    }

    public static AutoGroupType getAGTByMaskId(int instanceMaskId) {
        for (AutoGroupType autoGroupsType : values()) {
            if (autoGroupsType.getInstanceMaskId() == instanceMaskId) {
                return autoGroupsType;
            }
        }
        return null;
    }

    public static AutoGroupType getAutoGroup(int level, int npcId) {
        for (AutoGroupType agt : values()) {
            if (agt.hasLevelPermit(level) && agt.containNpcId(npcId)) {
                return agt;
            }
        }
        return null;
    }

    public static AutoGroupType getAutoGroupByWorld(int level, int worldId) {
        for (AutoGroupType agt : values()) {
            if (agt.getInstanceMapId() == worldId && agt.hasLevelPermit(level)) {
                return agt;
            }
        }
        return null;
    }

    public static AutoGroupType getAutoGroup(int npcId) {
        for (AutoGroupType agt : values()) {
            if (agt.containNpcId(npcId)) {
                return agt;
            }
        }
        return null;
    }

    public boolean isPvPSoloArena() {
        switch (this) {
            case ARENA_OF_DISCIPLINE_1:
            case ARENA_OF_DISCIPLINE_2:
            case ARENA_OF_DISCIPLINE_3:
            case ARENA_OF_DISCIPLINE_4:
                return true;
        }
        return false;
    }

    public boolean isTrainigPvPSoloArena() {
        switch (this) {
            case DISCIPLINE_TRAINING_GROUNDS_1:
            case DISCIPLINE_TRAINING_GROUNDS_2:
            case DISCIPLINE_TRAINING_GROUNDS_3:
            case DISCIPLINE_TRAINING_GROUNDS_4:
                return true;
        }
        return false;
    }

    public boolean isPvPFFAArena() {
        switch (this) {
            case ARENA_OF_CHAOS_1:
            case ARENA_OF_CHAOS_2:
            case ARENA_OF_CHAOS_3:
            case ARENA_OF_CHAOS_4:
                return true;
        }
        return false;
    }

    public boolean isTrainigPvPFFAArena() {
        switch (this) {
            case CHAOS_TRAINING_GROUNDS_1:
            case CHAOS_TRAINING_GROUNDS_2:
            case CHAOS_TRAINING_GROUNDS_3:
            case CHAOS_TRAINING_GROUNDS_4:
                return true;
        }
        return false;
    }

    public boolean isTrainigHarmonyArena() {
        switch (this) {
            case HARAMONIOUS_TRAINING_CENTER_1:
            case HARAMONIOUS_TRAINING_CENTER_2:
            case HARAMONIOUS_TRAINING_CENTER_3:
            case HARAMONIOUS_TRAINING_CENTER_4:
            case UNITY_TRAINING_GROUNDS_1:
            case UNITY_TRAINING_GROUNDS_2:
            case UNITY_TRAINING_GROUNDS_3:
                return true;
        }
        return false;
    }

    public boolean isHarmonyArena() {
        switch (this) {
            case ARENA_OF_HARMONY_1:
            case ARENA_OF_HARMONY_2:
            case ARENA_OF_HARMONY_3:
            case ARENA_OF_HARMONY_4:
                return true;
        }
        return false;
    }

    public boolean isGloryArena() {
        switch (this) {
            case ARENA_OF_GLORY_1:
            case ARENA_OF_GLORY_2:
                return true;
        }
        return false;
    }

    public boolean isKamar() {
        switch (this) {
            case KAMAR_BATTLEFIELD:
                return true;
        }
        return false;
    }

    public boolean isOphidan() {
        switch (this) {
            case ENGULFED_OPHIDAN_BRIDGE:
                return true;
        }
        return false;
    }

    public boolean isIronWall() {
        switch (this) {
            case IRON_WALL_WARFRONT:
                return true;
        }
        return false;
    }
    
    public boolean isIdgelDome() {
        switch (this) {
            case IDGEL_DOME:
                return true;
        }
        return false;
    }

    public boolean isPvpArena() {
        return isTrainigPvPFFAArena() || isPvPFFAArena() || isTrainigPvPSoloArena() || isPvPSoloArena();
    }

    public boolean hasLevelPermit(int level) {
        return level >= getMinLevel() && level <= getMaxLevel();
    }

    public byte getDifficultId() {
        return difficultId;
    }

    public AutoInstance getAutoInstance() {
        return newAutoInstance();
    }

    abstract AutoInstance newAutoInstance();
}
