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
package admincommands;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.WalkerData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.templates.walker.RouteStep;
import com.aionemu.gameserver.model.templates.walker.WalkerTemplate;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rolandas
 */
public class FixPath extends AdminCommand {

    static volatile boolean canceled = false;
    static volatile boolean isRunning = false;
    static Player runner = null;

    public FixPath() {
        super("fixpath");
    }

    @Override
    public void execute(final Player admin, String... params) {
        if (params == null || params.length < 1) {
            PacketSendUtility.sendMessage(admin, "Syntax : //fixpath <route id> <jump height> | <cancel>");
            return;
        }

        String routeId = "";
        final float z = admin.getZ();
        float jumpHeight = 0;

        try {
            if (isRunning && runner != null && !admin.equals(runner)) {
                PacketSendUtility.sendMessage(admin, "Someone is already running this command!");
                return;
            }
            if ("cancel".equals(params[0])) {
                if (isRunning) {
                    PacketSendUtility.sendMessage(admin, "Canceled.");
                    canceled = true;
                }
                return;
            } else if (params.length < 2) {
                PacketSendUtility.sendMessage(admin, "Syntax : //fixpath <route id> <jump height> | <cancel>");
                return;
            } else {
                routeId = params[0];
                jumpHeight = Float.parseFloat(params[1]);
            }
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(admin, "Only numbers please!!!");
        }

        final WalkerTemplate template = DataManager.WALKER_DATA.getWalkerTemplate(routeId);
        if (template == null) {
            PacketSendUtility.sendMessage(admin, "Invalid route id");
            return;
        }

        PacketSendUtility.sendMessage(admin, "Make sure you are at NPC spawn position. If not use cancel!");

        isRunning = true;
        runner = admin;
        final float height = jumpHeight;

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                boolean wasInvul = admin.isInvul();
                admin.setInvul(true);

                float zDelta = 0;
                HashMap<Integer, Float> corrections = new HashMap<Integer, Float>();

                try {
                    int i = 1;
                    for (RouteStep step : template.getRouteSteps()) {
                        if (canceled || admin.isInState(CreatureState.DEAD)) {
                            corrections.clear();
                            return;
                        }
                        if (step.getX() == 0 || step.getY() == 0) {
                            corrections.put(i++, admin.getZ());
                            PacketSendUtility.sendMessage(admin, "Skipping zero coordinate...");
                            continue;
                        }
                        if (zDelta == 0) {
                            zDelta = z - step.getZ() + height;
                        }
                        PacketSendUtility.sendMessage(admin, "Teleporting to step " + i + "...");
                        TeleportService2.teleportTo(admin, admin.getWorldId(), step.getX(), step.getY(), step.getZ() + zDelta);
                        admin.getController().stopProtectionActiveTask();
                        PacketSendUtility.sendMessage(admin, "Waiting to get Z...");
                        Thread.sleep(5000);
                        step.setZ(admin.getZ());
                        corrections.put(i++, admin.getZ());
                    }

                    PacketSendUtility.sendMessage(admin, "Saving corrections...");

                    WalkerData data = new WalkerData();
                    WalkerTemplate newTemplate = new WalkerTemplate(template.getRouteId());

                    i = 1;
                    ArrayList<RouteStep> newSteps = new ArrayList<RouteStep>();

                    int lastStep = template.isReversed() ? (template.getRouteSteps().size() + 2) / 2
                            : template.getRouteSteps().size();
                    for (int s = 0; s < lastStep; s++) {
                        RouteStep step = template.getRouteSteps().get(s);
                        RouteStep fixedStep = new RouteStep(step.getX(), step.getY(), corrections.get(i), 0);
                        fixedStep.setRouteStep(i++);
                        newSteps.add(fixedStep);
                    }

                    newTemplate.setRouteSteps(newSteps);
                    if (template.isReversed()) {
                        newTemplate.setIsReversed(true);
                    }
                    newTemplate.setPool(template.getPool());
                    data.AddTemplate(newTemplate);
                    data.saveData(template.getRouteId());

                    PacketSendUtility.sendMessage(admin, "Done.");
                } catch (Exception e) {
                } finally {
                    runner = null;
                    isRunning = false;
                    canceled = false;
                    if (!wasInvul) {
                        admin.setInvul(false);
                    }
                }
            }
        }, 5000);
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax : //fixpath <route id> <jump height> | <cancel>");
    }
}
