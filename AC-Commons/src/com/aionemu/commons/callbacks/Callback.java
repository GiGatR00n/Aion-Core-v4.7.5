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
package com.aionemu.commons.callbacks;

/**
 * Basic callback class.<br>
 * Each enhanced method will call "beforeCall" and "afterCall" methods
 *
 * @author SoulKeeper
 */
@SuppressWarnings("rawtypes")
public interface Callback<T> {

    /**
     * Method that is called before actual method is invoked.<br>
     * <p/>
     * Callback should return one of the following results:
     * <ul>
     * <li>{@link CallbackResult#newContinue()}</li>
     * <li>{@link CallbackResult#newCallbackBlocker()}</li>
     * <li>{@link CallbackResult#newFullBlocker(Object)}</li>
     * </ul>
     * <p/>
     * if result is not {@link CallbackResult#newFullBlocker(Object)} then
     * method will be executed normally. In other case
     * {@link CallbackResult#getResult()} will be returned.
     *
     * @param obj  on whom method should be invoked
     * @param args method args
     * @return see {@link CallbackResult}
     */
    public CallbackResult beforeCall(T obj, Object[] args);

    /**
     * Method that is called after actual method call.<br>
     * <p/>
     * Callback should return one of the following results:
     * <ul>
     * <li>{@link CallbackResult#newContinue()}</li>
     * <li>{@link CallbackResult#newCallbackBlocker()}</li>
     * <li>{@link CallbackResult#newFullBlocker(Object)}</li>
     * </ul>
     * <p/>
     * if result is not {@link CallbackResult#newFullBlocker(Object)} then
     * mehodResult will return unmodified. In other case
     * {@link CallbackResult#getResult()} will be returned.
     *
     * @param obj          on whom method was called
     * @param args         method args
     * @param methodResult result of method invocation
     * @return see {@link CallbackResult}
     */
    public CallbackResult afterCall(T obj, Object[] args, Object methodResult);

    /**
     * Returns base class that will be used as callback identificator.<br>
     * {@link com.aionemu.commons.callbacks.metadata.ObjectCallback#value()}
     * should contain class that will be invoked
     *
     * @return base class of callback
     */
    public Class<? extends Callback> getBaseClass();
}
