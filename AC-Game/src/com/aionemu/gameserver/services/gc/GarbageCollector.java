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
package com.aionemu.gameserver.services.gc;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.GSConfig;


/**
 * @author GiGatR00n v4.7.5.x
 */
public class GarbageCollector extends Thread {

	private static final Logger log = LoggerFactory.getLogger(GarbageCollector.class);

	private static long g_Period = (30 * 60 * 1000); // 30 minutes
	
	public GarbageCollector() {
		g_Period = (GSConfig.GC_OPTIMIZATION_TIME < 1) ? 30 : GSConfig.GC_OPTIMIZATION_TIME;
		g_Period = g_Period * 60 * 1000;
	}
	
    /**
     * instantiate class 
     */
    private static class SingletonHolder {
        protected static final GarbageCollector instance = new GarbageCollector();
    }

    public static GarbageCollector getInstance() {
        return SingletonHolder.instance;
    }
	
	@Override
	public void run()
	{
		if (GSConfig.ENABLE_MEMORY_GC) {
			log.info("Garbage Collector is scheduled at duration: " + String.valueOf(g_Period) + " in milliseconds.");
			StartMemoryOptimization();
		} else {
			log.info("Garbage Collector is turned off by administrator.");
		}
	}
    
    private void StartMemoryOptimization() {
    	
    	Timer t = new Timer();
    	t.schedule(new TimerTask() {
    		
			@Override
			public void run() {
		    	try
		    	{
		    		// When we reload configs, it need to initialized again.
		    		g_Period = (GSConfig.GC_OPTIMIZATION_TIME < 1) ? 30 : GSConfig.GC_OPTIMIZATION_TIME;
		    		g_Period = g_Period * 60 * 1000;
		    		
		    		if (GSConfig.ENABLE_MEMORY_GC) {
			    		log.info("Garbage Collector is optimizing memory to free unused heap memory.");
			    		System.gc();
			    		System.runFinalization();
			    		log.info("Garbage Collector has finished optimizing memory.");		    			
		    		}
		    	} catch (Exception e) {
		    	}				
			}
    	}, g_Period);
    }
}
