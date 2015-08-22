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
package com.aionemu.gameserver.services;

import java.util.Collection;
import java.util.Set;

import javolution.util.FastSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dao.WebshopDAO;
import com.aionemu.gameserver.model.Webshop;
import com.aionemu.gameserver.services.mail.MailFormatter;

/**
 * Custom Webshop System
 *
 * @author Blackfire
 */
public class WebshopService {

    private static final Logger log = LoggerFactory.getLogger(WebshopService.class);
    
	private String done = "TRUE";
		    
    private WebshopService() {
        this.load();
    }

    public static final WebshopService getInstance() {
		log.info("Starting Webshop service...");
        return SingletonHolder.instance;
    }

    /**
     * Check webshop entry for non delivered mail every 2 minutes
     */
    private void load() {
		if (CustomConfig.WEBSHOP_ENABLED){
			log.info("Webshop service loaded successfully");
			ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					Collection<Webshop> webshops;
					webshops = new FastSet<Webshop>(getDAO().getWebshop()).shared();
					
					for (final Webshop webshop : webshops) {
						if (webshop.getSend().equals("FALSE")) {
							MailFormatter.sendBlackCloudMail(webshop.getRecipient(), webshop.getItemId(), webshop.getCount());
							setWebshop(done, webshop.getId());
						}
					}
				}
			}, 2 * 60000, 2 * 60000);
		} else {
			log.info("Webshop service disable");	
		}
	}
	
	public void setWebshop(String send, int id) {
        getDAO().setWebshop(send, id);
    }

    public Set<Webshop> getWebshop() {
        return getDAO().getWebshop();
    }
	
	private WebshopDAO getDAO() {
        return DAOManager.getDAO(WebshopDAO.class);
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final WebshopService instance = new WebshopService();
    }
}
