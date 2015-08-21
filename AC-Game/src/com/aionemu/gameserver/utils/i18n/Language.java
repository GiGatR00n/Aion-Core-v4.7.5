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
package com.aionemu.gameserver.utils.i18n;

import javolution.util.FastList;
import javolution.util.FastMap;

import java.util.List;
import java.util.Map;

/**
 * @author blakawk
 */
public class Language {

    private final List<String> supportedLanguages = new FastList<String>();
    private final Map<CustomMessageId, String> translatedMessages = new FastMap<CustomMessageId, String>();

    public Language() {
    }

    protected Language(String language) {
        supportedLanguages.add(language);
    }

    protected void addSupportedLanguage(String language) {
        supportedLanguages.add(language);
    }

    public List<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    public String translate(CustomMessageId id, Object... params) {
        if (translatedMessages.containsKey(id)) {
            return String.format(translatedMessages.get(id), params);
        }

        return String.format(id.getFallbackMessage(), params);
    }

    /*
     * public String translateRU(CustomMessageIdRU id, Object... params) { if (translatedMessages.containsKey(id)) {
     * return String.format(translatedMessages.get(id), params); } return String.format(id.getFallbackMessage(), params);
     * }
     */
    protected void addTranslatedMessage(CustomMessageId id, String message) {
        translatedMessages.put(id, message);
    }
}
