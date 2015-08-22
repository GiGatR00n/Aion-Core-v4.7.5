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
package com.aionemu.gameserver.network.aion.serverpackets;

import javolution.util.FastMap;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PortalCooldownList;
import com.aionemu.gameserver.model.team2.TemporaryPlayerTeam;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author nrg
 * @author GiGatR00n v4.7.5.x 
 */
public class SM_INSTANCE_INFO extends AionServerPacket {

    private Player player;
    private boolean isAnswer;
    private int cooldownId;
    private int worldId;
    private TemporaryPlayerTeam<?> playerTeam;
    //private int maxcount;

    public SM_INSTANCE_INFO(Player player, boolean isAnswer, TemporaryPlayerTeam<?> playerTeam) {
        this.player = player;
        this.isAnswer = isAnswer;
        this.playerTeam = playerTeam;
        this.worldId = 0;
        this.cooldownId = 0;
        //this.maxcount = 0;
    }

    public SM_INSTANCE_INFO(Player player, int instanceId) {
        this.player = player;
        this.isAnswer = false;
        this.playerTeam = null;
        this.worldId = instanceId;
        this.cooldownId = DataManager.INSTANCE_COOLTIME_DATA.getInstanceCooltimeByWorldId(instanceId) != null ? DataManager.INSTANCE_COOLTIME_DATA.getInstanceCooltimeByWorldId(instanceId).getId() : 0;
        //this.maxcount = DataManager.INSTANCE_COOLTIME_DATA.getInstanceCountByWorldId(instanceId) != null ? DataManager.INSTANCE_COOLTIME_DATA.getInstanceCountByWorldId(instanceId).getId() : 0;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        boolean hasTeam = playerTeam != null;
        writeC(!isAnswer ? 0x2 : hasTeam ? 0x1 : 0x0);
        writeC(cooldownId);
        writeD(0x0); //unk1
        //writeH(maxcount); // 4.5 need to be the instance max count entry per days

        if (isAnswer) {
            if (hasTeam) {    //all cooldowns from team
                writeH(playerTeam.getMembers().size());

                for (Player p : playerTeam.getMembers()) {
                    PortalCooldownList cooldownList = p.getPortalCooldownList();
                    writeD(p.getObjectId());
                    writeH(cooldownList.size()); // cooldownList.size() must become countOfSomething => player count entry already set in portal_cooldown database ?

                    for (FastMap.Entry<Integer, Long> e = cooldownList.getPortalCoolDowns().head(), end = cooldownList.getPortalCoolDowns().tail(); (e = e.getNext()) != end; ) {
                        writeD(DataManager.INSTANCE_COOLTIME_DATA.getInstanceCooltimeByWorldId(e.getKey()).getId());
                        writeD(0x0);
                        writeD((int) (e.getValue() - System.currentTimeMillis()) / 1000);
                        //writeD(0x0); // unk 4.5
                        //writeD(0x0); // unk 4.5
                    }
                    writeS(p.getName());
                }
            } else {    //current cooldowns of player
                writeH(1);
                PortalCooldownList cooldownList = player.getPortalCooldownList();
                writeD(player.getObjectId());
                writeH(cooldownList.size()); // cooldownList.size() must become countOfSomething => player count entry already set in portal_cooldown database ?

                for (FastMap.Entry<Integer, Long> e = cooldownList.getPortalCoolDowns().head(), end = cooldownList.getPortalCoolDowns().tail(); (e = e.getNext()) != end; ) {
                    writeD(DataManager.INSTANCE_COOLTIME_DATA.getInstanceCooltimeByWorldId(e.getKey()).getId());
                    writeD(0x0);
                    writeD((int) (e.getValue() - System.currentTimeMillis()) / 1000);
                    writeD(0x0); // unk 4.5
                    writeD(0x0); // unk 4.5
                    writeC(0x1); // unk 4.7
                }
                writeS(player.getName());
            }
        } else {
            if (cooldownId == 0) {    //all current cooldowns from player
                writeH(1);
                PortalCooldownList cooldownList = player.getPortalCooldownList();
                writeD(player.getObjectId());
                writeH(cooldownList.size()); // cooldownList.size() must become countOfSomething => player count entry already set in portal_cooldown database ?

                for (FastMap.Entry<Integer, Long> e = cooldownList.getPortalCoolDowns().head(), end = cooldownList.getPortalCoolDowns().tail(); (e = e.getNext()) != end; ) {
                    writeD(DataManager.INSTANCE_COOLTIME_DATA.getInstanceCooltimeByWorldId(e.getKey()).getId());
                    writeD(0x0);
                    writeD((int) (e.getValue() - System.currentTimeMillis()) / 1000);
                    //writeD(0x0); // unk 4.5
                    //writeD(0x0); // unk 4.5
                }
                writeS(player.getName());
            } else {    //just new cooldown from instance enter
                writeH(1);
                writeD(player.getObjectId());
                writeH(1);
                writeD(cooldownId);
                writeD(0x0);
                long time = player.getPortalCooldownList().getPortalCooldown(worldId);
                writeD((time == 0 ? 0 : ((int) (time - System.currentTimeMillis()) / 1000)));
                //writeD(0x0); // unk 4.5
                //writeD(0x0); // unk 4.5
                writeS(player.getName());
            }
        }
    }
}
