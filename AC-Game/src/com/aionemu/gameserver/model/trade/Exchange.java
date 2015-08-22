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
package com.aionemu.gameserver.model.trade;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import javolution.util.FastList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ATracer
 */
public class Exchange {

    private Player activeplayer;
    private Player targetPlayer;
    private boolean confirmed;
    private boolean locked;
    private long kinahCount;
    private Map<Integer, ExchangeItem> items = new HashMap<Integer, ExchangeItem>();
    private List<Item> itemsToUpdate = FastList.newInstance();

    public Exchange(Player activeplayer, Player targetPlayer) {
        super();
        this.activeplayer = activeplayer;
        this.targetPlayer = targetPlayer;
    }

    public void confirm() {
        confirmed = true;
    }

    /**
     * @return the confirmed
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    public void lock() {
        this.locked = true;
    }

    /**
     * @return the locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @param exchangeItem
     */
    public void addItem(int parentItemObjId, ExchangeItem exchangeItem) {
        this.items.put(parentItemObjId, exchangeItem);
    }

    /**
     * @param countToAdd
     */
    public void addKinah(long countToAdd) {
        this.kinahCount += countToAdd;
    }

    /**
     * @return the activeplayer
     */
    public Player getActiveplayer() {
        return activeplayer;
    }

    /**
     * @return the targetPlayer
     */
    public Player getTargetPlayer() {
        return targetPlayer;
    }

    /**
     * @return the kinahCount
     */
    public long getKinahCount() {
        return kinahCount;
    }

    /**
     * @return the items
     */
    public Map<Integer, ExchangeItem> getItems() {
        return items;
    }

    public boolean isExchangeListFull() {
        return items.size() > 18;
    }

    /**
     * @return the itemsToUpdate
     */
    public List<Item> getItemsToUpdate() {
        return itemsToUpdate;
    }

    /**
     * @param item
     */
    public void addItemToUpdate(Item item) {
        itemsToUpdate.add(item);
    }
}
