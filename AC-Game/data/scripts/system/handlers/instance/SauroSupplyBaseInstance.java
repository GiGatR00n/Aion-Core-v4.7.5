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
package instance;

import java.util.Map;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * Author boscar
 * Author yayaya
 * Author NightRider
 * Author Ever
 *
 * :: 1. boss : Guard Captain Rohuka 1.BOSS , 1.door (230849) >> Door ID : 383
 * :: 2. boss : Chief Gunner Kurmata 2.BOSS (230851) >> room, door ID : 382
 * (left), 387 (right) :: Door NPC : Sheban Legion Elite Ambusher (230797) >>
 * door ID : 59 :: 3. boss : Derakanak the Reaver 3.BOSS (233258) :: Prison	:
 * 373, 380, 377 (left) ; 384, 374 (right) :: Door NPC : Sheban Legion Elite
 * Gunner (230818) >> door ID : 372 :: 4. boss : Researcher Teselik (230850) >>
 * Door ID : 375 :: 5. boss	: Gatekeeper Stranir (233255) >> Door ID : 378 :: 6.
 * boss : Commander Ranodim (230852) >> Door ID : 388 :: Door ID : Blue : 385
 * (key : 185000177), Red 379 (key : 185000176), Green : 381 (key : 185000178)
 * :: 7/1. boss: Archmagus Sayahum (233257) :: 7/2. boss: Darkblade Ovanuka
 * (233256) :: Ajto NPC : Sheban Legion Elite Assaulter (230791) >> Door ID :
 * 376 :: Port NPC : 8. boss : Chief of Staff Moriata (230853) :: Portal :
 * (730872) ; Portal key : 185000179 :: 1 KEY : Guard Leader Achradim (230857)
 * :: 2 KEY : Brigadian General Sheba (230858)
*
 */
@InstanceID(301130000)
public class SauroSupplyBaseInstance extends GeneralInstanceHandler {
	
    private Map<Integer, StaticDoor> doors;
    private boolean isInstanceDestroyed;

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
    }

    @Override
    public void onDie(Npc npc) {
        int npcId = npc.getNpcId();
        switch (npcId) {

            case 230849: //Guard Captain Rohuka
                sendMsg(1401914);
                doors.get(383).setOpen(true);
                break;

            case 230851: //Chief Cannoneer Kurmata
                if (Rnd.get(1, 100) < 50) {
                    spawn(230797, 611.1872f, 452.91882f, 191.2776f, (byte) 39);
                } else {
                    spawn(230797, 610.7328f, 518.80884f, 191.2776f, (byte) 75);
                }
                doors.get(382).setOpen(true);
                doors.get(387).setOpen(true);
                break;
            case 230797: //Sheban Legion Elite Ambusher
                sendMsg(1401916);
                doors.get(59).setOpen(true);
                break;

            case 230818: //Sheban Legion Elite Gunner.
                sendMsg(1401916);
                doors.get(372).setOpen(true);
                break;
            case 230850: //Research Teselik.
                sendMsg(1401917);
                doors.get(375).setOpen(true);
                break;

            case 233255: //Gate Sentry Slurt
                sendMsg(1401918);
                doors.get(378).setOpen(true);
                break;
            case 230852: //Supplies Commander Ranodim
                sendMsg(1401919);
                doors.get(388).setOpen(true);
                break;

            case 230790: // Sheban Legion Elite Assaulter
                sendMsg(1401920);
                doors.get(376).setOpen(true);
                break;
            case 230853: // Staff Commander Moriata
                sendMsg(1401921);
                spawn(730872, 129.16f, 432.33f, 153.33f, (byte) 0, 3); //portal to boss
                break;

			//230847 : Mystery Box Key spawn
            case 233258: // Dark Devourer Derakanak
                spawn(230847, 139.66022f, 437.02383f, 150.99849f, (byte) 106);
                break;
            case 233257: // Inspector Officer Sayahum
                spawn(230847, 153.27214f, 436.90598f, 150.99849f, (byte) 76);
                break;
            case 233256: // Inspection Officer Obanuka
                spawn(230847, 153.34435f, 423.44098f, 150.99849f, (byte) 45);
                break;

            case 230857: // Guard Leader Achradim , 1 key boss
                spawn(801967, 689.85376f, 903.41785f, 411.45676f, (byte) 105);
                scheduleExit();
                break;
            case 230858: // Brigadian General Sheba , 2 key boss
                spawn(801967, 886.4798f, 876.16693f, 411.45676f, (byte) 15); // exit portal
                spawn(702659, 897.20306f, 886.9926f, 411.57693f, (byte) 15); // Noble Abbey Box
                scheduleExit();
                break;
        }
    }

    private void scheduleExit() {
        instance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 180));
                PacketSendUtility.sendMessage(player, "You will leave this instance in 3 minutes");
            }
        });
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                instance.doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player player) {
                        onExitInstance(player);
                    }
                });
                onInstanceDestroy();
            }
        }, 300000);
    }

    @Override
    public void onExitInstance(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }

    @Override
    public void onInstanceDestroy() {
        doors.clear();
        isInstanceDestroyed = true;
    }
}
