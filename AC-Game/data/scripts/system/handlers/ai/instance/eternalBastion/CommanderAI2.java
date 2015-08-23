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

package ai.instance.eternalBastion;


import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.handler.AggroEventHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@AIName("commander_bastion")
public class CommanderAI2 extends AggressiveNpcAI2 {

    int attackCount;

    private List<Integer> percents = new ArrayList<Integer>();

    @Override
    protected void handleSpawned() {
        addPercent();
        super.handleSpawned();
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
        attackCount++;
        if (attackCount == 20) {
            AggroEventHandler.onAggro(this, creature);
        }
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage > 98 && percents.size() < 9) {
            addPercent();
        }

        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 98:
                        shout_attack();
                        break;
                    case 80:
                        shout_attack();
                        break;
                    case 70:
                        shout_attack();
                        break;
                    case 60:
                        shout_attack();
                        break;
                    case 50:
                        shout_attack();
                        break;
                    case 40:
                        shout_attack();
                        break;
                    case 30:
                        shout_attack();
                        break;
                    case 20:
                        shout_attack();
                        break;
                    case 5:
                        shout_attack();
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void shout_attack() { // MSG Notice 05
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401827));
            }
        });
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{98, 80, 70, 60, 50, 40, 30, 20, 5});
    }


    @Override
    protected void handleDespawned() {
        percents.clear();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        percents.clear();
        super.handleDied();
    }

    @Override
    protected void handleBackHome() {
        addPercent();
        super.handleBackHome();
    }

}