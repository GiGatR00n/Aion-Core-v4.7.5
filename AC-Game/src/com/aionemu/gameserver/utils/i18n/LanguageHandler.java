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

import com.aionemu.commons.scripting.classlistener.AggregatedClassListener;
import com.aionemu.commons.scripting.classlistener.OnClassLoadUnloadListener;
import com.aionemu.commons.scripting.classlistener.ScheduledTaskClassListener;
import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
import com.aionemu.gameserver.GameServerError;
import com.aionemu.gameserver.configs.main.GSConfig;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Fennek
 */
public class LanguageHandler {

    private static final File LANGUAGE_DESCRIPTOR_FILE = new File("./data/scripts/system/languages.xml");
    private static Logger log = LoggerFactory.getLogger(Language.class);
    private Map<String, Language> languages = new FastMap<String, Language>();
    private Language language;
    private static final LanguageHandler instance = new LanguageHandler();
    private ScriptManager sm = new ScriptManager();

    public static final LanguageHandler getInstance() {
        AggregatedClassListener acl = new AggregatedClassListener();
        acl.addClassListener(new OnClassLoadUnloadListener());
        acl.addClassListener(new ScheduledTaskClassListener());
        acl.addClassListener(new LanguagesLoader(instance));
        instance.sm.setGlobalClassListener(acl);

        try {
            instance.sm.load(LANGUAGE_DESCRIPTOR_FILE);
        } catch (Exception e) {
            throw new GameServerError("Cannot load languages", e);
        }

        instance.language = instance.getLanguage(GSConfig.LANG);
        return instance;
    }

    private LanguageHandler() {
    }

    public static String translate(CustomMessageId id, Object... params) {
        return instance.language.translate(id, params);
    }

    public void registerLanguage(Language language) {
        if (language == null) {
            throw new NullPointerException("Cannot register null Language");
        }

        List<String> langs = language.getSupportedLanguages();

        for (String lang : langs) {
            if (languages.containsKey(lang)) {
                log.warn("Overriding language " + lang + " with class " + language.getClass().getName());
            }

            languages.put(lang, language);
        }
    }

    public Language getLanguage(String language) {
        if (!languages.containsKey(language)) {
            return new Language();
        }

        return languages.get(language);
    }

    public void clear() {
        languages.clear();
    }

    public int size() {
        return languages.size();
    }
}
