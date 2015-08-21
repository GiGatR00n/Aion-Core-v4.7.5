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
package com.aionemu.commons.scripting;

import java.util.Arrays;

/**
 * This class represents compilation result of script context
 *
 * @author SoulKeeper
 */
public class CompilationResult {

    /**
     * List of classes that were compiled by compiler
     */
    private final Class<?>[] compiledClasses;

    /**
     * Classloader that was used to load classes
     */
    private final ScriptClassLoader classLoader;

    /**
     * Creates new instance of CompilationResult with classes that has to be
     * parsed and classloader that was used to load classes
     *
     * @param compiledClasses classes compiled by compiler
     * @param classLoader     classloader that was used by compiler
     */
    public CompilationResult(Class<?>[] compiledClasses, ScriptClassLoader classLoader) {
        this.compiledClasses = compiledClasses;
        this.classLoader = classLoader;
    }

    /**
     * Returns classLoader that was used by compiler
     *
     * @return classloader that was used by compiler
     */
    public ScriptClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * Retunrs list of classes that were compiled
     *
     * @return list of classes that were compiled
     */
    public Class<?>[] getCompiledClasses() {
        return compiledClasses;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CompilationResult");
        sb.append("{classLoader=").append(classLoader);
        sb.append(", compiledClasses=")
                .append(compiledClasses == null ? "null" : Arrays.asList(compiledClasses).toString());
        sb.append('}');
        return sb.toString();
    }
}
