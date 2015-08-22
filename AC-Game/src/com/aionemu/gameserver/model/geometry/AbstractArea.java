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
package com.aionemu.gameserver.model.geometry;

import com.aionemu.gameserver.model.templates.zone.Point2D;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * Class with basic method implementation for ares.<br>
 * If possible it should be subclassed. <br>
 * In other case {@link com.aionemu.gameserver.model.geometry.Area} should be
 * implemented directly
 */
public abstract class AbstractArea implements Area {

    /**
     * Minimal z of area
     */
    private final float minZ;
    /**
     * Maximal Z of area
     */
    private final float maxZ;
    private ZoneName zoneName;
    private int worldId;

    /**
     * Creates new AbstractArea with min and max z
     *
     * @param minZ min z
     * @param maxZ max z
     */
    protected AbstractArea(ZoneName zoneName, int worldId, float minZ, float maxZ) {
        if (minZ > maxZ) {
            throw new IllegalArgumentException("minZ(" + minZ + ") > maxZ(" + maxZ + ")");
        }
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.zoneName = zoneName;
        this.worldId = worldId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInside2D(Point2D point) {
        return isInside2D(point.getX(), point.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInside3D(Point3D point) {
        return isInside3D(point.getX(), point.getY(), point.getZ());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInside3D(float x, float y, float z) {
        return isInsideZ(z) && isInside2D(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInsideZ(Point3D point) {
        return isInsideZ(point.getZ());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInsideZ(float z) {
        return z >= getMinZ() && z <= getMaxZ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDistance2D(Point2D point) {
        return getDistance2D(point.getX(), point.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDistance3D(Point3D point) {
        return getDistance3D(point.getX(), point.getY(), point.getZ());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D getClosestPoint(Point2D point) {
        return getClosestPoint(point.getX(), point.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point3D getClosestPoint(Point3D point) {
        return getClosestPoint(point.getX(), point.getY(), point.getZ());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point3D getClosestPoint(float x, float y, float z) {
        Point2D closest2d = getClosestPoint(x, y);

        float zCoord;

        if (isInsideZ(z)) {
            zCoord = z;
        } else if (z < getMinZ()) {
            zCoord = getMinZ();
        } else {
            zCoord = getMaxZ();
        }

        return new Point3D(closest2d.getX(), closest2d.getY(), zCoord);
    }

    /**
     * {@inheritDoc}
     */
    public float getMinZ() {
        return minZ;
    }

    /**
     * {@inheritDoc}
     */
    public float getMaxZ() {
        return maxZ;
    }

    @Override
    public int getWorldId() {
        return worldId;
    }

    /**
     * @return the zoneName
     */
    @Override
    public ZoneName getZoneName() {
        return zoneName;
    }
}
