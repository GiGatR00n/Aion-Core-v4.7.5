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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemCategory;
import com.aionemu.gameserver.model.templates.item.actions.EnchantItemAction;
import com.aionemu.gameserver.model.templates.item.actions.GodstoneAction;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.EnchantService;
import com.aionemu.gameserver.services.item.ItemSocketService;
import com.aionemu.gameserver.services.trade.PricesService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer, Wakizashi
 */
public class CM_MANASTONE extends AionClientPacket {

    private int npcObjId;
    private int slotNum;
    private int actionType;
    private int targetFusedSlot;
    private int stoneUniqueId;
    private int targetItemUniqueId;
    private int supplementUniqueId;
    @SuppressWarnings("unused")
    private ItemCategory actionCategory;

    /**
     * @param opcode
     */
    public CM_MANASTONE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        actionType = readC();
        targetFusedSlot = readC();
        targetItemUniqueId = readD();
        switch (actionType) {
            case 1:
            case 2:
            case 4:
            case 8:
                stoneUniqueId = readD();
                supplementUniqueId = readD();
                break;
            case 3:
                slotNum = readC();
                readC();
                readH();
                npcObjId = readD();
                break;
        }
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        VisibleObject obj = player.getKnownList().getObject(npcObjId);

        switch (actionType) {
            case 1: // enchant stone
            case 2: // add manastone
                EnchantItemAction action = new EnchantItemAction();
                Item manastone = player.getInventory().getItemByObjId(stoneUniqueId);
                Item targetItem = player.getEquipment().getEquippedItemByObjId(targetItemUniqueId);
                if (targetItem == null) {
                    targetItem = player.getInventory().getItemByObjId(targetItemUniqueId);
                }
                if (action.canAct(player, manastone, targetItem)) {
                    Item supplement = player.getInventory().getItemByObjId(supplementUniqueId);
                    if (supplement != null) {
                        if (supplement.getItemId() / 100000 != 1661) { // suppliment id check
                            return;
                        }
                    }
                    action.act(player, manastone, targetItem, supplement, targetFusedSlot);
                }
                break;
            case 3: // remove manastone
                long price = PricesService.getPriceForService(500, player.getRace());
                if (player.getInventory().getKinah() < price) {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_NOT_ENOUGH_KINA(price));
                    return;
                }
                if (obj != null && obj instanceof Npc && MathUtil.isInRange(player, obj, 7)) {
                    player.getInventory().decreaseKinah(price);
                    if (targetFusedSlot == 1) {
                        ItemSocketService.removeManastone(player, targetItemUniqueId, slotNum);
                    } else {
                        ItemSocketService.removeFusionstone(player, targetItemUniqueId, slotNum);
                    }
                }
                break;
            case 4: // add godstone
                Item godStone = player.getInventory().getItemByObjId(stoneUniqueId);
                Item targetItemGod = player.getEquipment().getEquippedItemByObjId(targetItemUniqueId);
                if (targetItemGod == null) {
                	targetItemGod = player.getInventory().getItemByObjId(targetItemUniqueId);
                }
                GodstoneAction godAction = new GodstoneAction();
                if (godAction.canAct(player, godStone, targetItemGod)) {
                    godAction.act(player, godStone, targetItemGod);
                }
                break;
            case 8: // amplification
            	Item amplyMaterial = player.getInventory().getItemByObjId(supplementUniqueId);
            	Item targetItemAmply = player.getEquipment().getEquippedItemByObjId(targetItemUniqueId);
                if (targetItemAmply == null) {
                	targetItemAmply = player.getInventory().getItemByObjId(targetItemUniqueId);
                }
            	Item tool = player.getInventory().getItemByObjId(stoneUniqueId);
            	EnchantService.amplifyItem(player, targetItemAmply, amplyMaterial, tool);
            	break;
        }
    }
}