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

import javolution.util.FastList;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.ecfunctions.ffa.FFaService;
import com.aionemu.gameserver.world.World;

/**
 * @author Avol modified by ATracer
 * @reworked Kill3r
 */
public class SM_UPDATE_PLAYER_APPEARANCE extends AionServerPacket {

    public int playerId;
    public int size;
    public FastList<Item> items;

    public SM_UPDATE_PLAYER_APPEARANCE(int playerId, FastList<Item> items) {
        this.playerId = playerId;
        this.items = items;
        this.size = items.size();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(playerId);

        int mask = 0;
        for (Item item : items) {
            if (item.getItemTemplate().isTwoHandWeapon()) {
                ItemSlot[] slots = ItemSlot.getSlotsFor(item.getEquipmentSlot());
                mask |= slots[0].getSlotIdMask();
            } else {
                mask |= item.getEquipmentSlot();
            }
        }

        writeD(mask); // item size DBS

        for (Item item : items) {
            Player player = World.getInstance().findPlayer(playerId);
            if(player == null){
                return;
            }
            if(player.isInFFA()){
                writeD(FFaService.getDisplayTemplate(player, item));
                GodStone godStone = item.getGodStone();
                writeD(godStone != null ? 168000118 : 0);
                writeD(item.getItemColor());
                if (item.getAuthorize() > 0 && item.getItemTemplate().isAccessory()){
                    if (item.getItemTemplate().isPlume()) {
                        float aLvl = item.getAuthorize() / 5;
                        if (item.getAuthorize() >= 5){
                            aLvl = aLvl > 2.0f ? 2.0f : aLvl;
                            writeD((int) aLvl << 3);
                        }else{
                            writeD(0);
                        }
                    }else{
                        writeD(item.getAuthorize() >= 5 ? 2 : 0);
                    }
                }else{
                    if (item.getItemTemplate().isWeapon() || item.getItemTemplate().isTwoHandWeapon()){
                        writeD(item.getEnchantLevel() == 15 ? 2 : item.getEnchantLevel() >= 20 ? 4 : 0);
                    }else{
                        writeD(0);
                    }
                }
            }else{
                writeD(item.getItemSkinTemplate().getTemplateId());
                GodStone godStone = item.getGodStone();
                writeD(godStone != null ? godStone.getItemId() : 0);
                writeD(item.getItemColor());
                if (item.getAuthorize() > 0 && item.getItemTemplate().isAccessory()){
                    if (item.getItemTemplate().isPlume()) {
                        float aLvl = item.getAuthorize() / 5;
                        if (item.getAuthorize() >= 5){
                            aLvl = aLvl > 2.0f ? 2.0f : aLvl;
                            writeD((int) aLvl << 3);
                        }else{
                            writeD(0);
                        }
                    }else{
                        writeD(item.getAuthorize() >= 5 ? 2 : 0);
                    }
                }else{
                    if (item.getItemTemplate().isWeapon() || item.getItemTemplate().isTwoHandWeapon()){
                        writeD(item.getEnchantLevel() == 15 ? 2 : item.getEnchantLevel() >= 20 ? 4 : 0);
                    }else{
                        writeD(0);
                    }
                }
            }
        }
    }
}