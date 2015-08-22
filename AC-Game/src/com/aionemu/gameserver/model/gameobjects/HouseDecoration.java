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
package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.templates.housing.HousePart;

/**
 * @author Rolandas
 */
public class HouseDecoration extends AionObject {

    private int templateId;
    private byte floor;
    private boolean isUsed;
    private PersistentState persistentState;

    public HouseDecoration(int objectId, int templateId) {
        this(objectId, templateId, -1);
    }

    public HouseDecoration(int objectId, int templateId, int floor) {
        super(objectId);
        this.templateId = templateId;
        this.floor = (byte) floor;
        this.persistentState = PersistentState.NEW;
    }

    public HousePart getTemplate() {
        return DataManager.HOUSE_PARTS_DATA.getPartById(templateId);
    }

    public PersistentState getPersistentState() {
        return persistentState;
    }

    public void setPersistentState(PersistentState persistentState) {
        this.persistentState = persistentState;
    }

    @Override
    public String getName() {
        return getTemplate().getName();
    }

    public byte getFloor() {
        return floor;
    }

    public void setFloor(int value) {
        if (value != floor) {
            floor = (byte) value;
            if (persistentState != PersistentState.NEW && persistentState != PersistentState.NOACTION) {
                persistentState = PersistentState.UPDATE_REQUIRED;
            }
        }
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        if (this.isUsed != isUsed && persistentState != PersistentState.DELETED) {
            this.isUsed = isUsed;
            if (persistentState != PersistentState.NEW && persistentState != PersistentState.NOACTION) {
                persistentState = PersistentState.UPDATE_REQUIRED;
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HouseDecoration)) {
            return false;
        } else {
            return ((HouseDecoration) object).getObjectId().equals(this.getObjectId());
        }
    }

    @Override
    public int hashCode() {
        return this.getObjectId();
    }
}
