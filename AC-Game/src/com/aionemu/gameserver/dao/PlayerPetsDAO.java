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
package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.PetCommonData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.pet.PetDopingBag;

import java.util.List;

/**
 * @author Xitanium, Kamui, Rolandas
 */
public abstract class PlayerPetsDAO implements DAO {

    @Override
    public final String getClassName() {
        return PlayerPetsDAO.class.getName();
    }

    public abstract void insertPlayerPet(PetCommonData petCommonData);

    public abstract void removePlayerPet(Player player, int petId);

    public abstract void updatePetName(PetCommonData petCommonData);

    public abstract List<PetCommonData> getPlayerPets(Player player);

    public abstract void setTime(Player player, int petId, long time);

    public abstract void saveFeedStatus(Player player, int petId, int hungryLevel, int feedProgress, long reuseTime);

    public abstract boolean savePetMoodData(PetCommonData petCommonData);

    public abstract void saveDopingBag(Player player, int petId, PetDopingBag bag);
}
