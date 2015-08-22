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
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.eventEngine.battleground.BattleGroundData;
import com.aionemu.gameserver.model.templates.mail.Mails;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * An instance of this class is the result of data loading.
 *
 * @author Luno, orz Modified by Wakizashi
 */
@XmlRootElement(name = "ae_static_data")
@XmlAccessorType(XmlAccessType.NONE)
public class StaticData {

    @XmlElement(name="npc_drops")
    public XmlNpcDropData xmlNpcDropData;

    @XmlElement(name = "world_maps")
    public WorldMapsData worldMapsData;

    @XmlElement(name = "weather")
    public MapWeatherData mapWeatherData;
    @XmlElement(name = "npc_trade_list")
    public TradeListData tradeListData;
    @XmlElement(name = "npc_teleporter")
    public TeleporterData teleporterData;
    @XmlElement(name = "teleport_location")
    public TeleLocationData teleLocationData;
    @XmlElement(name = "bind_points")
    public BindPointData bindPointData;
    @XmlElement(name = "quests")
    public QuestsData questData;
    @XmlElement(name = "quest_scripts")
    public XMLQuests questsScriptData;
    @XmlElement(name = "player_experience_table")
    public PlayerExperienceTable playerExperienceTable;
    @XmlElement(name = "player_stats_templates")
    public PlayerStatsData playerStatsData;
    @XmlElement(name = "summon_stats_templates")
    public SummonStatsData summonStatsData;
    @XmlElement(name = "item_templates")
    public ItemData itemData;
    @XmlElement(name = "random_bonuses")
    public ItemRandomBonusData itemRandomBonuses;
    @XmlElement(name = "npc_templates")
    public NpcData npcData;
    @XmlElement(name = "npc_shouts")
    public NpcShoutData npcShoutData;
    @XmlElement(name = "player_initial_data")
    public PlayerInitialData playerInitialData;
    @XmlElement(name = "skill_data")
    public SkillData skillData;
    @XmlElement(name = "motion_times")
    public MotionData motionData;
    @XmlElement(name = "skill_tree")
    public SkillTreeData skillTreeData;
    @XmlElement(name = "cube_expander")
    public CubeExpandData cubeExpandData;
    @XmlElement(name = "warehouse_expander")
    public WarehouseExpandData warehouseExpandData;
    @XmlElement(name = "player_titles")
    public TitleData titleData;
    @XmlElement(name = "gatherable_templates")
    public GatherableData gatherableData;
    @XmlElement(name = "npc_walker")
    public WalkerData walkerData;
    @XmlElement(name = "zones")
    public ZoneData zoneData;
    @XmlElement(name = "goodslists")
    public GoodsListData goodsListData;
    @XmlElement(name = "tribe_relations")
    public TribeRelationsData tribeRelationsData;
    @XmlElement(name = "recipe_templates")
    public RecipeData recipeData;
    @XmlElement(name = "chest_templates")
    public ChestData chestData;
    @XmlElement(name = "staticdoor_templates")
    public StaticDoorData staticDoorData;
    @XmlElement(name = "item_sets")
    public ItemSetData itemSetData;
    @XmlElement(name = "npc_factions")
    public NpcFactionsData npcFactionsData;
    @XmlElement(name = "npc_skill_templates")
    public NpcSkillData npcSkillData;
    @XmlElement(name = "pet_skill_templates")
    public PetSkillData petSkillData;
    @XmlElement(name = "siege_locations")
    public SiegeLocationData siegeLocationData;

    @XmlElement(name = "dimensional_vortex")
    public VortexData vortexData;

    @XmlElement(name = "rift_locations")
    public RiftData riftData;

    @XmlElement(name = "fly_rings")
    public FlyRingData flyRingData;
    @XmlElement(name = "shields")
    public ShieldData shieldData;
    @XmlElement(name = "pets")
    public PetData petData;
    @XmlElement(name = "pet_feed")
    public PetFeedData petFeedData;
    @XmlElement(name = "dopings")
    public PetDopingData petDopingData;
    @XmlElement(name = "guides")
    public GuideHtmlData guideData;
    @XmlElement(name = "roads")
    public RoadData roadData;
    @XmlElement(name = "instance_cooltimes")
    public InstanceCooltimeData instanceCooltimeData;
    @XmlElement(name = "decomposable_items")
    public DecomposableItemsData decomposableItemsData;
    @XmlElement(name = "ai_templates")
    public AIData aiData;
    @XmlElement(name = "flypath_template")
    public FlyPathData flyPath;
    @XmlElement(name = "windstreams")
    public WindstreamData windstreamsData;
    @XmlElement(name = "item_restriction_cleanups")
    public ItemRestrictionCleanupData itemCleanup;
    @XmlElement(name = "assembled_npcs")
    public AssembledNpcsData assembledNpcData;
    @XmlElement(name = "cosmetic_items")
    public CosmeticItemsData cosmeticItemsData;
    @XmlElement(name = "auto_groups")
    public AutoGroupData autoGroupData;
    @XmlElement(name = "events_config")
    public EventData eventData;
    @XmlElement(name = "spawns")
    public SpawnsData2 spawnsData2;
    @XmlElement(name = "item_groups")
    public ItemGroupsData itemGroupsData;
    @XmlElement(name = "polymorph_panels")
    public PanelSkillsData panelSkillsData;
    @XmlElement(name = "instance_bonusattrs")
    public InstanceBuffData instanceBuffData;
    @XmlElement(name = "housing_objects")
    public HousingObjectData housingObjectData;
    @XmlElement(name = "rides")
    public RideData rideData;
    @XmlElement(name = "instance_exits")
    public InstanceExitData instanceExitData;
    @XmlElement(name = "portal_locs")
    PortalLocData portalLocData;
    @XmlElement(name = "portal_templates2")
    Portal2Data portalTemplate2;
    @XmlElement(name = "house_lands")
    public HouseData houseData;

    @XmlElement(name = "buildings")
    public HouseBuildingData houseBuildingData;

    @XmlElement(name = "house_parts")
    public HousePartsData housePartsData;
    @XmlElement(name = "curing_objects")
    public CuringObjectsData curingObjectsData;
    @XmlElement(name = "house_npcs")
    public HouseNpcsData houseNpcsData;
    @XmlElement(name = "assembly_items")
    public AssemblyItemsData assemblyItemData;
    @XmlElement(name = "lboxes")
    public HouseScriptData houseScriptData;
    @XmlElement(name = "mails")
    public Mails systemMailTemplates;
    @XmlElement(name = "material_templates")
    public MaterialData materiaData;
    @XmlElement(name = "challenge_tasks")
    public ChallengeData challengeData;
    @XmlElement(name = "town_spawns_data")
    public TownSpawnsData townSpawnsData;
    @XmlElement(name = "abyss_race_bonuses")
    public AbyssRaceBonusData abyssRaceBonusData;
    @XmlElement(name = "skill_charge")
    public SkillChargeData skillChargeData;
    @XmlElement(name = "item_purifications")
    public ItemPurificationData itemPurificationData;
    @XmlElement(name = "decomposable_selectitems")
    public DecomposableSelectItemsData decomposableSelectItemsData;
    @XmlElement(name = "enchant_templates")
    public ItemEnchantData itemEnchantData;
    @XmlElement(name = "enchant_tables")
    public ItemEnchantTableData itemEnchantTableData;
    @XmlElement(name = "hotspot_teleport")
    public HotspotTeleporterData hotspotTeleporterData;
    @XmlElement(name = "login_rewards")
    public LoginEventData loginEventData;
    @XmlElement(name = "serial_killers")
    public SerialKillerData serialKillerData;
    @XmlElement(name = "base_locations")
    public BaseData baseData;
    @XmlElement(name = "beritra_invasion")
	public BeritraData beritraData;
    @XmlElement(name = "absolute_stats")
    public AbsoluteStatsData absoluteStatsData;
    @XmlElement(name = "walker_versions")
    public WalkerVersionsData walkerVersionsData;
    @XmlElement(name = "robots")
    public RobotData robotData;
    @XmlElement(name = "arcadelist")
    public ArcadeUpgradeData arcadeUpgradeData;
    @XmlElement(name = "item_multi_returns")
    public MultiReturnItemData multiReturnItemData;		
    @XmlElement(name = "battlegrounds")
    public BattleGroundData battlegroundData;

    // JAXB callback
    @SuppressWarnings("unused")
    private void afterUnmarshal(Unmarshaller unmarshaller, Object parent) {
        DataManager.log.info("Loaded " + worldMapsData.size() + " world maps");
        DataManager.log.info("Loaded " + materiaData.size() + " material ids");
        DataManager.log.info("Loaded " + mapWeatherData.size() + " weather maps");
        DataManager.log.info("Loaded " + playerExperienceTable.getMaxLevel() + " levels");
        DataManager.log.info("Loaded " + playerStatsData.size() + " player stat templates");
        DataManager.log.info("Loaded " + summonStatsData.size() + " summon stat templates");
        DataManager.log.info("Loaded " + itemCleanup.size() + " item cleanup entries");
        DataManager.log.info("Loaded " + itemData.size() + " item templates");
        DataManager.log.info("Loaded " + itemRandomBonuses.size() + " item bonus templates");
        DataManager.log.info("Loaded " + itemGroupsData.bonusSize() + " bonus item group templates");
        DataManager.log.info("Loaded " + itemGroupsData.petFoodSize() + " pet food items");
        DataManager.log.info("Loaded " + npcData.size() + " npc templates");
        DataManager.log.info("Loaded " + systemMailTemplates.size() + " system mail templates");
        DataManager.log.info("Loaded " + npcShoutData.size() + " npc shout templates");
        DataManager.log.info("Loaded " + petData.size() + " pet templates");
        DataManager.log.info("Loaded " + petFeedData.size() + " food flavours");
        DataManager.log.info("Loaded " + petDopingData.size() + " pet doping templates");
        DataManager.log.info("Loaded " + playerInitialData.size() + " initial player templates");
        DataManager.log.info("Loaded " + tradeListData.size() + " trade lists");
        DataManager.log.info("Loaded " + teleporterData.size() + " npc teleporter templates");
        DataManager.log.info("Loaded " + teleLocationData.size() + " teleport locations");
        DataManager.log.info("Loaded " + skillData.size() + " skill templates");
        DataManager.log.info("Loaded " + motionData.size() + " motion times");
        DataManager.log.info("Loaded " + skillTreeData.size() + " skill learn entries");
        DataManager.log.info("Loaded " + cubeExpandData.size() + " cube expand entries");
        DataManager.log.info("Loaded " + warehouseExpandData.size() + " warehouse expand entries");
        DataManager.log.info("Loaded " + bindPointData.size() + " bind point entries");
        DataManager.log.info("Loaded " + questData.size() + " quest data entries");
        DataManager.log.info("Loaded " + gatherableData.size() + " gatherable entries");
        DataManager.log.info("Loaded " + titleData.size() + " title entries");
        DataManager.log.info("Loaded " + walkerData.size() + " walker routes");
        DataManager.log.info("Loaded " + walkerVersionsData.size() + " walker group variants");
        DataManager.log.info("Loaded " + zoneData.size() + " zone entries");
        DataManager.log.info("Loaded " + goodsListData.size() + " goodslist entries");
        DataManager.log.info("Loaded " + tribeRelationsData.size() + " tribe relation entries");
        DataManager.log.info("Loaded " + recipeData.size() + " recipe entries");
        DataManager.log.info("Loaded " + chestData.size() + " chest locations");
        DataManager.log.info("Loaded " + staticDoorData.size() + " static door locations");
        DataManager.log.info("Loaded " + itemSetData.size() + " item set entries");
        DataManager.log.info("Loaded " + npcFactionsData.size() + " npc factions");
        DataManager.log.info("Loaded " + npcSkillData.size() + " npc skill list entries");
        DataManager.log.info("Loaded " + petSkillData.size() + " pet skill list entries");
        DataManager.log.info("Loaded " + siegeLocationData.size() + " siege location entries");
        DataManager.log.info("Loaded " + vortexData.size() + " vortex entries");
        DataManager.log.info("Loaded " + riftData.size() + " rift entries");
        DataManager.log.info("Loaded " + flyRingData.size() + " fly ring entries");
        DataManager.log.info("Loaded " + shieldData.size() + " shield entries");
        DataManager.log.info("Loaded " + petData.size() + " pet entries");
        DataManager.log.info("Loaded " + guideData.size() + " guide entries");
        DataManager.log.info("Loaded " + roadData.size() + " road entries");
        DataManager.log.info("Loaded " + instanceCooltimeData.size() + " instance cooltime entries");
        DataManager.log.info("Loaded " + decomposableItemsData.size() + " decomposable items entries");
        DataManager.log.info("Loaded " + aiData.size() + " ai templates");
        DataManager.log.info("Loaded " + flyPath.size() + " flypath templates");
        DataManager.log.info("Loaded " + windstreamsData.size() + " windstream entries");
        DataManager.log.info("Loaded " + assembledNpcData.size() + " assembled npcs entries");
        DataManager.log.info("Loaded " + cosmeticItemsData.size() + " cosmetic items entries");
    	//DataManager.log.info("Loaded " + npcDropData.size() + " npc drop entries");
        DataManager.log.info("Loaded " + autoGroupData.size() + " auto group entries");
        DataManager.log.info("Loaded " + spawnsData2.size() + " spawn maps entries");
        DataManager.log.info("Loaded " + eventData.size() + " active events");
        DataManager.log.info("Loaded " + panelSkillsData.size() + " skill panel entries");
        DataManager.log.info("Loaded " + instanceBuffData.size() + " instance Buffs entries");
        DataManager.log.info("Loaded " + housingObjectData.size() + " housing object entries");
        DataManager.log.info("Loaded " + rideData.size() + " ride info entries");
        DataManager.log.info("Loaded " + instanceExitData.size() + " instance exit entries");
        DataManager.log.info("Loaded " + portalLocData.size() + " portal loc entries");
        DataManager.log.info("Loaded " + portalTemplate2.size() + " portal templates2 entries");
        DataManager.log.info("Loaded " + houseData.size() + " housing lands");
        DataManager.log.info("Loaded " + houseBuildingData.size() + " house building styles");
        DataManager.log.info("Loaded " + housePartsData.size() + " house parts");
        DataManager.log.info("Loaded " + houseNpcsData.size() + " house spawns");
        DataManager.log.info("Loaded " + houseScriptData.size() + " house default scripts");
        DataManager.log.info("Loaded " + curingObjectsData.size() + " curing Objects entries");
        DataManager.log.info("Loaded " + assemblyItemData.size() + " assembly items entries");
        DataManager.log.info("Loaded " + challengeData.size() + " challenge tasks entries");
        DataManager.log.info("Loaded " + townSpawnsData.getSpawnsCount() + " town spawns");
        DataManager.log.info("Loaded " + abyssRaceBonusData.size() + " abyss race bonus entries");
        DataManager.log.info("Loaded " + skillChargeData.size() + " skill charge entries");
        DataManager.log.info("Loaded " + itemPurificationData.size() + " item purifications entries");
        DataManager.log.info("Loaded " + decomposableSelectItemsData.size() + " decomposable select data");
        DataManager.log.info("Loaded " + itemEnchantData.size() + " item enchant data");
        DataManager.log.info("Loaded " + itemEnchantTableData.size() + " item enchant tables");
        DataManager.log.info("Loaded " + hotspotTeleporterData.size() + " hotspot templates");
        DataManager.log.info("Loaded " + arcadeUpgradeData.size() + " arcade upgrade entries");
        DataManager.log.info("Loaded " + loginEventData.size() + " atreian passports");
        DataManager.log.info("Loaded " + serialKillerData.size() + " serial killer entries");
        DataManager.log.info("Loaded " + baseData.size() + " base entries");
        DataManager.log.info("Loaded " + beritraData.size() + " beritra invasion entries");
        DataManager.log.info("Loaded " + absoluteStatsData.size() + " absolute stat templates");
        DataManager.log.info("Loaded " + robotData.size() + " robot armor entries");
        DataManager.log.info("Loaded " + multiReturnItemData.size() + " multi returns items");     
        DataManager.log.info("Loaded " + xmlNpcDropData.size() + " custom npc drop data");
        DataManager.log.info("Loaded " + battlegroundData.size() + " battleground templates");
    }
}
