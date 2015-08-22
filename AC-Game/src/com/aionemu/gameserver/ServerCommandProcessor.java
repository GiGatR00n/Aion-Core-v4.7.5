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
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 */


package com.aionemu.gameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author PenguinJoe
 * ServerCommandProcessor implements a background thread to process commands from the console - either OS shell or a java-based launcher  
 */

public class ServerCommandProcessor extends Thread
{
	private static final Logger log = LoggerFactory.getLogger(ServerCommandProcessor.class);
	@Override
	public void run()
	{
	
	    // commands are only accepted from stdin when <enter> is hit. Otherwise we will waste no time reading char by char.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String command = null;
		log.info("Server command processor thread started");
		try
		{
			while ((command = br.readLine()) != null)
			{
				if ( command.equalsIgnoreCase("shutdown") ||
					 command.equalsIgnoreCase("quit")     ||
					 command.equalsIgnoreCase("exit"))
					System.exit(0); 	// this will run finalizers and shutdown hooks for a clean shutdown.	
			}
		} 
		catch (IOException e)
		{
		   // an IOException here indicates the console (or other launcher) is closing. The server needs to shut down too.
			System.exit(0);  // exit using shutdown handlers.
		}
	}
}
