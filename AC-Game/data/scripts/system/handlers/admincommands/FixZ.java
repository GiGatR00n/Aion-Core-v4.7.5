package admincommands;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

import java.io.IOException;


public class FixZ extends AdminCommand
{

	public FixZ()	{ super("fixz"); }

@Override
public void execute(Player admin, String... params)
{
        if (admin.getAccessLevel() < 1) {
		PacketSendUtility.sendMessage(admin, "You dont have enough rights to use this command!");
                return; }

    if (admin.getTarget() != null)
    {
        if(admin.getTarget() instanceof Npc)
	{
            Npc target = (Npc) admin.getTarget();
            final SpawnTemplate temp = target.getSpawn();
            int respawnTime = 295;
            boolean permanent = true;
				
								
            //delete spawn,npc
            target.getController().delete();
														
            //spawn npc
            int templateId = temp.getNpcId();
            float x = temp.getX();
            float y = temp.getY();
			float z = admin.getZ();
            byte heading = temp.getHeading();
            int worldId = temp.getWorldId();

        SpawnTemplate spawn = SpawnEngine.addNewSpawn(worldId, templateId, x, y, z, heading, respawnTime);

        if (spawn == null) {
            PacketSendUtility.sendMessage(admin, "There is no template with id " + templateId);
            return;
        }

        VisibleObject visibleObject = SpawnEngine.spawnObject(spawn, admin.getInstanceId());

        if (visibleObject == null) {
            PacketSendUtility.sendMessage(admin, "npc id " + templateId + " was not found!");
        } else if (permanent) {
            try {
                DataManager.SPAWNS_DATA2.saveSpawn(admin, visibleObject, false);
            } catch (IOException e) {
                PacketSendUtility.sendMessage(admin, "Could not save spawn");
            }
        }

        String objectName = visibleObject.getObjectTemplate().getName();
        PacketSendUtility.sendMessage(admin, objectName + "FixZ");
    }
	
        }
            else { PacketSendUtility.sendMessage(admin, "Only in target!");
 }
    }

    /**
    * @param walkerId  
    * @param walkerIdx 
    */
    protected VisibleObject spawn(int npcId, int mapId, int instanceId, float x, float y, float z, byte heading, String walkerId, int walkerIdx, int respawnTime) {
        SpawnTemplate template = SpawnEngine.addNewSpawn(mapId, npcId, x, y, z, heading, respawnTime);
        return SpawnEngine.spawnObject(template, instanceId);
    }
    
}