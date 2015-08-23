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
package ai.instance.rentusBase;

import ai.GeneralNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.walker.WalkerTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("imprisoned_reian")
public class ImprisonedReianAI2 extends GeneralNpcAI2 {

    private AtomicBoolean isSaved = new AtomicBoolean(false);
    private AtomicBoolean isAsked = new AtomicBoolean(false);
    private String walkerId;
    private WalkerTemplate template;

    @Override
    protected void handleSpawned() {
        walkerId = getSpawnTemplate().getWalkerId();
        getSpawnTemplate().setWalkerId(null);
        if (walkerId != null) {
            template = DataManager.WALKER_DATA.getWalkerTemplate(walkerId);
        }
        super.handleSpawned();
    }

    @Override
    protected void handleMoveArrived() {
        int point = getOwner().getMoveController().getCurrentPoint();
        super.handleMoveArrived();
        if (template.getRouteSteps().size() - 4 == point) {
            getSpawnTemplate().setWalkerId(null);
            WalkManager.stopWalking(this);
            AI2Actions.deleteOwner(this);
        }
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        if (walkerId != null) {
            if (creature instanceof Player) {
                final Player player = (Player) creature;
                if (MathUtil.getDistance(getOwner(), player) <= 21) {
                    if (isAsked.compareAndSet(false, true)) {
                        switch (Rnd.get(1, 10)) {
                            case 1:
                                sendMsg(390563);
                                break;
                            case 2:
                                sendMsg(390567);
                                break;
                        }
                    }
                }
                if (MathUtil.getDistance(getOwner(), player) <= 6) {
                    if (isSaved.compareAndSet(false, true)) {
                        getSpawnTemplate().setWalkerId(walkerId);
                        WalkManager.startWalking(this);
                        getOwner().setState(1);
                        PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getObjectId()));
                        switch (Rnd.get(1, 10)) {
                            case 1:
                                sendMsg(342410);
                                break;
                            case 2:
                                sendMsg(342411);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
    }
}
