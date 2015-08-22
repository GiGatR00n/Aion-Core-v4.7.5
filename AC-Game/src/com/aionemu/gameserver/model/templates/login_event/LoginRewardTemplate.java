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
package com.aionemu.gameserver.model.templates.login_event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

import com.aionemu.gameserver.utils.gametime.DateTimeUtil;

/**
 * @author Ranastic
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginReward")
public class LoginRewardTemplate
{
	@XmlAttribute(name = "id")
	protected int id;
	
	@XmlAttribute(name = "active")
	protected boolean active;
	
	@XmlAttribute(name = "start", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar startDate;
	
	@XmlAttribute(name = "end", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar endDate;
	
	@XmlAttribute(name = "type")
	protected LoginType type;
	
	@XmlAttribute(name = "attend_num")
	protected int attend_num;
	
	@XmlAttribute(name = "permit_level")
	protected int permit_level;
	
	@XmlElement(name = "reward")
	protected LoginReward reward;
	
	public int getId() {
		return id;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public DateTime getStartDate() {
		return DateTimeUtil.getDateTime(startDate.toGregorianCalendar());
	}
	
	public DateTime getEndDate() {
		return DateTimeUtil.getDateTime(endDate.toGregorianCalendar());
	}
	
	public LoginType getType() {
		return type;
	}
	
	public int getAttendNum() {
		return attend_num;
	}
	
	public int getPermitLevel() {
		return permit_level;
	}
	
	public LoginReward getReward() {
		return reward;
	}
	
	public boolean isActive() {
		return getStartDate().isBeforeNow() && getEndDate().isAfterNow();
	}
	
	public boolean isExpired() {
		return !isActive();
	}
	
	public boolean isDaily() {
		return type.getId() == LoginType.DAILY.getId();
	}
	
	public boolean isCumulative() {
		return type.getId() == LoginType.CUMULATIVE.getId();
	}
	
	public boolean isAnniversary() {
		return type.getId() == LoginType.ANNIVERSARY.getId();
	}
}