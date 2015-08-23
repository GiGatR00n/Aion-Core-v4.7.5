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
package ai.instance.raksang;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("vasuki_lifespark")
public class VasukiLifesparkAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean startedEvent = new AtomicBoolean(false);
    private boolean think = false;

    @Override
    public boolean canThink() {
        return think;
    }

    @Override
    protected void handleSpawned() {
        if (getNpcId() == 217764) {
            think = true;
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (!isAlreadyDead()) {
                        SkillEngine.getInstance().getSkill(getOwner(), 19126, 46, getOwner()).useNoAnimationSkill();
                    }
                }
            }, 3000);
        }
        super.handleSpawned();
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        if (creature instanceof Player) {
            final Player player = (Player) creature;
            if (MathUtil.getDistance(getOwner(), player) <= 30) {
                if (startedEvent.compareAndSet(false, true)) {
                    final int level;
                    final int shoutId;
                    final int skill;
                    switch (getNpcId()) {
                        case 217760:
                            skill = 19972;
                            level = 45;
                            shoutId = 1401107;
                            break;
                        case 217761:
                            skill = 19972;
                            level = 46;
                            shoutId = 1401171;
                            break;
                        case 217763:
                            skill = 20087;
                            level = 46;
                            shoutId = 0;
                            break;
                        default:
                            skill = 20039;
                            level = 46;
                            shoutId = 1401110;
                            break;
                    }
                    if (shoutId != 0) {
                        sendMsg(shoutId);
                    }
                    SkillEngine.getInstance().getSkill(getOwner(), skill, level, getOwner()).useNoAnimationSkill();
                    if (getNpcId() != 217764) {
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                if (!isAlreadyDead()) {
                                    if (getNpcId() == 217763) {
                                        getPosition().getWorldMapInstance().getDoors().get(219).setOpen(true);
                                    }
                                    SkillEngine.getInstance().getSkill(getOwner(), 19967, level, getOwner()).useNoAnimationSkill();
                                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!isAlreadyDead()) {
                                                AI2Actions.deleteOwner(VasukiLifesparkAI2.this);
                                            }
                                        }
                                    }, 3500);

                                }
                            }
                        }, 3500);
                    } else {
                        SkillEngine.getInstance().getSkill(getOwner(), 19974, 46, getOwner()).useNoAnimationSkill();
                    }
                }
            }
        }
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), false, 0, 0);
    }

    @Override
    public AIAnswer ask(AIQuestion question) {
        switch (question) {
            case CAN_RESIST_ABNORMAL:
                return AIAnswers.POSITIVE;
            default:
                return AIAnswers.NEGATIVE;
        }
    }

    @Override
    protected void handleDied() {
        if (getNpcId() == 217764) {
            sendMsg(1401111);
            Npc soul = getPosition().getWorldMapInstance().getNpc(217471);
            Npc sapping = getPosition().getWorldMapInstance().getNpc(217472);
            if (soul != null) {
                soul.getEffectController().removeEffect(19126);
            }
            if (sapping != null) {
                sapping.getEffectController().removeEffect(19126);
            }
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1401140);
        }
        super.handleDied();
    }
}
