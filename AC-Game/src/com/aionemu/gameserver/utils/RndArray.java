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
package com.aionemu.gameserver.utils;

import java.util.List;

import com.aionemu.commons.utils.MTRandom;


public class RndArray
{
  private static final MTRandom rnd = new MTRandom();
  
  public static float get()
  {
    return rnd.nextFloat();
  }
  
  public static int get(int n)
  {
    return (int)Math.floor(rnd.nextDouble() * n);
  }
  
  public static int get(int min, int max)
  {
    return min + (int)Math.floor(rnd.nextDouble() * (max - min + 1));
  }
  
  public static boolean chance(int chance)
  {
    return (chance >= 1) && ((chance > 99) || (nextInt(99) + 1 <= chance));
  }
  
  public static boolean chance(double chance)
  {
    return nextDouble() <= chance / 100.0D;
  }
  
  public static <E> E get(E[] list)
  {
    return list[get(list.length)];
  }
  
  public static int get(int[] list)
  {
    return list[get(list.length)];
  }
  
  public static <E> E get(List<E> list)
  {
    return list.get(get(list.size()));
  }
  
  public static int nextInt(int n)
  {
    return (int)Math.floor(rnd.nextDouble() * n);
  }
  
  public static int nextInt()
  {
    return rnd.nextInt();
  }
  
  public static double nextDouble()
  {
    return rnd.nextDouble();
  }
  
  public static double nextGaussian()
  {
    return rnd.nextGaussian();
  }
  
  public static boolean nextBoolean()
  {
    return rnd.nextBoolean();
  }
}
