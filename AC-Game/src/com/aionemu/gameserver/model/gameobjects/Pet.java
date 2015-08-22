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

import com.aionemu.gameserver.controllers.PetController;
import com.aionemu.gameserver.controllers.movement.MoveController;
import com.aionemu.gameserver.controllers.movement.PetMoveController;
import com.aionemu.gameserver.model.gameobjects.player.PetCommonData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.pet.PetTemplate;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author ATracer
 */
public class Pet extends VisibleObject {

    private final Player master;
    private MoveController moveController;
    private final PetTemplate petTemplate;

    /**
     * @param petTemplate
     * @param controller
     * @param commonData
     * @param master
     */
    public Pet(PetTemplate petTemplate, PetController controller, PetCommonData commonData, Player master) {
        super(commonData.getObjectId(), controller, null, commonData, new WorldPosition(master.getWorldId()));
        controller.setOwner(this);
        this.master = master;
        this.petTemplate = petTemplate;
        this.moveController = new PetMoveController();
    }

    public Player getMaster() {
        return master;
    }

    public int getPetId() {
        return objectTemplate.getTemplateId();
    }

    @Override
    public String getName() {
        return objectTemplate.getName();
    }

    public final PetCommonData getCommonData() {
        return (PetCommonData) objectTemplate;
    }

    public final MoveController getMoveController() {
        return moveController;
    }

    public final PetTemplate getPetTemplate() {
        return petTemplate;
    }
}
