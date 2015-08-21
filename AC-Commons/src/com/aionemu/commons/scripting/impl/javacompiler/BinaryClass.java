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

import com.sun.tools.javac.file.BaseFileObject;

import java.io.*;
import java.net.URI;

//import com.sun.tools.javac.file.BaseFileObject;

/**
 * This class is just a hack to make javac compiler work with classes loaded by
 * prevoius classloader. Also it's used as container for loaded class
 *
 * @author SoulKeeper
 */
public class BinaryClass extends BaseFileObject {

    /**
     * ClassName
     */
    private final String name;

    /**
     * Class data will be written here
     */
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    /**
     * Locaded class will be set here
     */
    private Class<?> definedClass;

    /**
     * Constructor that accepts class name as parameter
     *
     * @param name class name
     */
    protected BinaryClass(String name) {
        super(null);
        this.name = name;
    }

    /**
     * Throws {@link UnsupportedOperationException}
     *
     * @return nothing
     */
    @Override
    public URI toUri() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns name of this class with ".class" suffix
     *
     * @return name of this class with ".class" suffix
     * @deprecated
     */
    @Deprecated
    @Override
    public String getName() {
        return name + ".class";
    }

    /**
     * Creates new ByteArrayInputStream, it just wraps class binary data
     *
     * @return input stream for class data
     * @throws IOException never thrown
     */
    @Override
    public InputStream openInputStream() throws IOException {
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Opens ByteArrayOutputStream for class data
     *
     * @return output stream
     * @throws IOException never thrown
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return baos;
    }

    /**
     * Throws {@link UnsupportedOperationException}
     *
     * @return nothing
     */
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Throws {@link UnsupportedOperationException}
     *
     * @return nothing
     */
    @Override
    public Writer openWriter() throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Unsupported operation, always reutrns 0
     *
     * @return 0
     */
    @Override
    public long getLastModified() {
        return 0;
    }

    /**
     * Unsupported operation, returns false
     *
     * @return false
     */
    @Override
    public boolean delete() {
        return false;
    }

    /**
     * Returns class name
     *
     * @param path doesn't matter
     * @return class name
     */
    @Override
    protected String inferBinaryName(Iterable<? extends File> path) {
        return name;
    }

    /**
     * Returns true if {@link javax.tools.JavaFileObject.Kind#CLASS}
     *
     * @param simpleName doesn't matter
     * @param kind       kind to compare
     * @return true if Kind is {@link javax.tools.JavaFileObject.Kind#CLASS}
     */
    @Override
    public boolean isNameCompatible(String simpleName, Kind kind) {
        return Kind.CLASS.equals(kind);
    }

    /**
     * Returns bytes of class
     *
     * @return bytes of class
     */
    public byte[] getBytes() {
        return baos.toByteArray();
    }

    /**
     * Returns class that was loaded from binary data of this object
     *
     * @return loaded class
     */
    public Class<?> getDefinedClass() {
        return definedClass;
    }

    /**
     * Sets class that was loaded by this object
     *
     * @param definedClass class that was loaded
     */
    public void setDefinedClass(Class<?> definedClass) {
        this.definedClass = definedClass;
    }

    /* (non-Javadoc)
     * @see javax.tools.JavaFileObject#getKind()
     */
    @Override
    public Kind getKind() {
        return Kind.CLASS;
    }

}
