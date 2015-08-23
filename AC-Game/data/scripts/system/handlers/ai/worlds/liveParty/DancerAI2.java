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
package ai.worlds.liveParty;

import ai.GeneralNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;


@AIName("dancer")
public class DancerAI2 extends GeneralNpcAI2 {
	
	@Override
	protected void handleSpawned() {
		switch(getNpcId()) {
			case 831633:
			case 831634:
			case 831635:
			case 831637:
			case 831638:
			case 831639:
				this.setStateIfNot(AIState.IDLE);
				this.setSubStateIfNot(AISubState.NONE);
				EmoteManager.emoteStartDancing1(this.getOwner());
				break;
			case 831640:
			case 831641:
			case 831642:
			case 831643:
			case 831644:
			case 831645:
			case 831646:
			case 831647:
				this.setStateIfNot(AIState.IDLE);
				this.setSubStateIfNot(AISubState.NONE);
				EmoteManager.emoteStartDancing2(this.getOwner());
				break;
			case 831648:
			case 831649:
			case 831650:
			case 831651:
			case 831652:
			case 831653:
				this.setStateIfNot(AIState.IDLE);
				this.setSubStateIfNot(AISubState.NONE);
				EmoteManager.emoteStartDancing3(this.getOwner());
				break;
			case 831617:
			case 831618:
				this.setStateIfNot(AIState.IDLE);
				this.setSubStateIfNot(AISubState.NONE);
				EmoteManager.emoteStartDancing4(this.getOwner());
				break;
		}
	}
	
	@Override
	protected AIAnswer pollInstance(AIQuestion question) {
		switch (question) {
			case SHOULD_DECAY:
				return AIAnswers.NEGATIVE;
			case SHOULD_RESPAWN:
				return AIAnswers.NEGATIVE;
			case SHOULD_REWARD:
				return AIAnswers.NEGATIVE;
			default:
				return null;
		}
	}
}
