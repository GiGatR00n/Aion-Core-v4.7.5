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
package com.aionemu.gameserver.eventEngine.battleground.services.battleground;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundTemplate;
import com.aionemu.gameserver.eventEngine.battleground.services.factories.SurveyFactory;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Eloann
 */
public class CTFBattleGround extends BattleGround {

    private int elyosFlagCount = 0;
    private int asmosFlagCount = 0;

    public CTFBattleGround(int tplId, WorldMapInstance instance) {
        super(tplId, instance);
    }

    public void score(Race race) {
        if (race == Race.ELYOS) {
            elyosFlagCount++;
        } else {
            asmosFlagCount++;
        }
        if (elyosFlagCount >= template.getTargetScore() || asmosFlagCount >= template.getTargetScore()) {
            end();
        }
    }

    @Override
    public void start() {
        BattleGroundTemplate bgTemplate = DataManager.BATTLEGROUND_DATA.getBattleGroundTemplate(tplId);
        /*
         * Spawn Healers
         */
        SpawnTemplate se = SpawnEngine.addNewSingleTimeSpawn(bgTemplate.getWorldId(), 203098, bgTemplate.getHealerLocation().getXe(),
                bgTemplate.getHealerLocation().getYe(), bgTemplate.getHealerLocation().getZe(), (byte) 0);
        SpawnTemplate sa = SpawnEngine.addNewSingleTimeSpawn(bgTemplate.getWorldId(), 203557, bgTemplate.getHealerLocation().getXa(),
                bgTemplate.getHealerLocation().getYa(), bgTemplate.getHealerLocation().getZa(), (byte) 0);

        /*
         * Spawn Flags
         */
        SpawnTemplate sfe = SpawnEngine.addNewSingleTimeSpawn(bgTemplate.getWorldId(), 700336, bgTemplate.getFlagLocation().getXe(),
                bgTemplate.getFlagLocation().getYe(), bgTemplate.getFlagLocation().getZe(), (byte) 0);
        SpawnTemplate sfa = SpawnEngine.addNewSingleTimeSpawn(bgTemplate.getWorldId(), 700037, bgTemplate.getFlagLocation().getXa(),
                bgTemplate.getFlagLocation().getYa(), bgTemplate.getFlagLocation().getZa(), (byte) 0);

        SpawnEngine.spawnBGHealer(se, instance.getInstanceId(), Race.ELYOS);
        SpawnEngine.spawnBGHealer(sa, instance.getInstanceId(), Race.ASMODIANS);

        SpawnEngine.spawnBGFlag(sfe, instance.getInstanceId(), Race.ELYOS);
        SpawnEngine.spawnBGFlag(sfa, instance.getInstanceId(), Race.ASMODIANS);

        super.start();

    }

    @Override
    public void end() {
        super.end();

        if (elyosFlagCount > asmosFlagCount) {
            instance.doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player player) {
                    if (player.getCommonData().getRace() == Race.ELYOS) {
                        player.getBattleGround().increasePoints(player, player.getBattleGround().getTemplate().getRules().getCTFReward());
                    }
                }
            });
        } else if (elyosFlagCount == asmosFlagCount) {
            instance.doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player player) {
                    player.getBattleGround().increasePoints(player, player.getBattleGround().getTemplate().getRules().getCTFReward());
                }
            });
        } else {
            instance.doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player player) {
                    if (player.getCommonData().getRace() == Race.ASMODIANS) {
                        player.getBattleGround().increasePoints(player, player.getBattleGround().getTemplate().getRules().getCTFReward());
                    }
                }
            });
        }

        instance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                HTMLService.showHTML(object, SurveyFactory.buildCTFBattleGroundReport(object), 152000001);
                object.getEffectController().removeAllEffects();
                if (!object.getLifeStats().isAlreadyDead()) {
                    if (object.getCommonData().getRace() == Race.ELYOS) {
                        TeleportService2.teleportTo(object, 110010000, 1374f, 1399f, 573f, (byte) 0);
                    } else {
                        TeleportService2.teleportTo(object, 120010000, 1324f, 1550f, 210f, (byte) 0);
                    }
                }
            }
        });

    }

    public int getElyosFlagCount() {
        return elyosFlagCount;
    }

    public int getAsmosFlagCount() {
        return asmosFlagCount;
    }
}
