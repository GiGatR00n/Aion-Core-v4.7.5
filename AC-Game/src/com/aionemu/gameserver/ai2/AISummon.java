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
package com.aionemu.gameserver.ai2;

import com.aionemu.gameserver.controllers.SummonController;
import com.aionemu.gameserver.controllers.movement.SummonMoveController;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;

/**
 * @author xTz
 */
@AIName("summon")
public class AISummon extends AITemplate {

    @Override
    public Summon getOwner() {
        return (Summon) super.getOwner();
    }

    protected NpcTemplate getObjectTemplate() {
        return getOwner().getObjectTemplate();
    }

    protected SpawnTemplate getSpawnTemplate() {
        return getOwner().getSpawn();
    }

    protected Race getRace() {
        return getOwner().getRace();
    }

    protected Player getMaster() {
        return getOwner().getMaster();
    }

    protected SummonMoveController getMoveController() {
        return getOwner().getMoveController();
    }

    protected SummonController getController() {
        return getOwner().getController();
    }
}
