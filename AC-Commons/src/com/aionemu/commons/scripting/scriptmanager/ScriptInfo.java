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
package com.aionemu.commons.scripting.scriptmanager;

import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.List;

/**
 * Simple class that represents script info.<br>
 * <br>
 * It contains Script root, list of libraries and list of child contexes
 *
 * @author SoulKeeper
 */
@XmlRootElement(name = "scriptinfo")
@XmlAccessorType(XmlAccessType.NONE)
public class ScriptInfo {

    /**
     * Root of this script context. Child directories of root will be scanned
     * for script files
     */
    @XmlAttribute(required = true)
    private File root;

    /**
     * List of libraries of this script context
     */
    @XmlElement(name = "library")
    private List<File> libraries;

    /**
     * List of child contexts
     */
    @XmlElement(name = "scriptinfo")
    private List<ScriptInfo> scriptInfos;

    /**
     * Default compiler class name.
     */
    @XmlElement(name = "compiler")
    private String compilerClass = ScriptManager.DEFAULT_COMPILER_CLASS.getName();

    /**
     * Returns root of script context
     *
     * @return root of script context
     */
    public File getRoot() {
        return root;
    }

    /**
     * Sets root for script context
     *
     * @param root root for script context
     */
    public void setRoot(File root) {
        this.root = root;
    }

    /**
     * Returns list of libraries that will be used byscript context and it's
     * children
     *
     * @return lib of libraries
     */
    public List<File> getLibraries() {
        return libraries;
    }

    /**
     * Sets list of libraries that will be used by script context and it's
     * children
     *
     * @param libraries sets list of libraries
     */
    public void setLibraries(List<File> libraries) {
        this.libraries = libraries;
    }

    /**
     * Return list of child context descriptors
     *
     * @return list of child context descriptors
     */
    public List<ScriptInfo> getScriptInfos() {
        return scriptInfos;
    }

    /**
     * Sets list of child context descriptors
     *
     * @param scriptInfos list of child context descriptors
     */
    public void setScriptInfos(List<ScriptInfo> scriptInfos) {
        this.scriptInfos = scriptInfos;
    }

    /**
     * Returns compiler class name
     *
     * @return name of compiler class
     */
    public String getCompilerClass() {
        return compilerClass;
    }

    /**
     * Sets compiler class name
     *
     * @param compilerClass name of compiler class
     */
    public void setCompilerClass(String compilerClass) {
        this.compilerClass = compilerClass;
    }

    /**
     * Returns true if roots are quals
     *
     * @param o object to compare with
     * @return true if this ScriptInfo and anothers ScriptInfo has same root
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScriptInfo that = (ScriptInfo) o;

        return root.equals(that.root);

    }

    /**
     * Returns hashcode of root
     *
     * @return hashcode of root
     */
    @Override
    public int hashCode() {
        return root.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ScriptInfo");
        sb.append("{root=").append(root);
        sb.append(", libraries=").append(libraries);
        sb.append(", compilerClass='").append(compilerClass).append('\'');
        sb.append(", scriptInfos=").append(scriptInfos);
        sb.append('}');
        return sb.toString();
    }
}
