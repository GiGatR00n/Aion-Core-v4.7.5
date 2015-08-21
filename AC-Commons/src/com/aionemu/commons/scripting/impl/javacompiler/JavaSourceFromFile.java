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
package com.aionemu.commons.scripting.impl.javacompiler;

import org.apache.commons.io.FileUtils;

import javax.tools.SimpleJavaFileObject;
import java.io.File;
import java.io.IOException;

/**
 * This class is simple wrapper for SimpleJavaFileObject that load class source
 * from file sytem
 *
 * @author SoulKeeper
 */
public class JavaSourceFromFile extends SimpleJavaFileObject {

    /**
     * Construct a JavaFileObject of the given kind and with the given File.
     *
     * @param file the file with source of this file object
     * @param kind the kind of this file object
     */
    public JavaSourceFromFile(File file, Kind kind) {
        super(file.toURI(), kind);
    }

    /**
     * Returns class source represented as string.
     *
     * @param ignoreEncodingErrors not used
     * @return class source
     * @throws IOException if something goes wrong
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return FileUtils.readFileToString(new File(this.toUri()));
    }
}
