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
package com.aionemu.gameserver.model.templates.mail;

import org.apache.commons.lang.StringUtils;

import javax.xml.bind.annotation.*;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailPart")
@XmlSeeAlso({Sender.class, Header.class, Body.class, Tail.class, Title.class})
public abstract class MailPart extends StringParamList implements IMailFormatter {

    @XmlAttribute(name = "id")
    protected Integer id;

    @Override
    public MailPartType getType() {
        return MailPartType.CUSTOM;
    }

    public Integer getId() {
        return id;
    }

    public String getFormattedString(IMailFormatter customFormatter) {
        String result = "";
        IMailFormatter formatter = this;
        if (customFormatter != null) {
            formatter = customFormatter;
        }

        result = getFormattedString(getType());

        String[] paramValues = new String[getParam().size()];
        for (int i = 0; i < getParam().size(); i++) {
            Param param = getParam().get(i);
            paramValues[i] = formatter.getParamValue(param.getId());
        }
        String joinedParams = StringUtils.join(paramValues, ',');
        if (StringUtils.isEmpty(result)) {
            return joinedParams;
        } else if (!StringUtils.isEmpty(joinedParams)) {
            result += "," + joinedParams;
        }

        return result;
    }

    public String getFormattedString(MailPartType partType) {
        String result = "";
        if (id > 0) {
            result += id.toString();
        }
        return result;
    }
}
