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
package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.stats.calc.StatOwner;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;

/**
 * @author Rolandas
 */
public class AbsoluteStatOwner implements StatOwner {

    Player target;
    ModifiersTemplate template;
    boolean isActive = false;

    public AbsoluteStatOwner(Player player, int templateId) {
        this.target = player;
        setTemplate(templateId);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setTemplate(int templateId) {
        if (isActive) {
            cancel();
        }
        this.template = DataManager.ABSOLUTE_STATS_DATA.getTemplate(templateId);
    }

    public void apply() {
        if (template == null) {
            return;
        }
        target.getGameStats().addEffect(this, template.getModifiers());
        isActive = true;
    }

    public void cancel() {
        if (template == null) {
            return;
        }
        target.getGameStats().endEffect(this);
        isActive = false;
    }
}
