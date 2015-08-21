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
package com.aionemu.gameserver.world.geo;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.geoEngine.GeoWorldLoader;
import com.aionemu.gameserver.geoEngine.models.GeoMap;
import com.aionemu.gameserver.geoEngine.scene.Spatial;
import com.aionemu.gameserver.model.templates.world.WorldMapTemplate;
import com.aionemu.gameserver.utils.Util;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ATracer
 */
public class RealGeoData implements GeoData {

    private static final Logger log = LoggerFactory.getLogger(RealGeoData.class);
    private TIntObjectHashMap<GeoMap> geoMaps = new TIntObjectHashMap<GeoMap>();

    @Override
    public void loadGeoMaps() {
        Map<String, Spatial> models = loadMeshes();
        loadWorldMaps(models);
        models.clear();
        models = null;
        log.info("Geodata: " + geoMaps.size() + " geo maps loaded!");
    }

    /**
     * @param models
     */
    protected void loadWorldMaps(Map<String, Spatial> models) {
        log.info("Loading geo maps..");
        Util.printProgressBarHeader(DataManager.WORLD_MAPS_DATA.size());
        List<Integer> mapsWithErrors = new ArrayList<Integer>();

        for (WorldMapTemplate map : DataManager.WORLD_MAPS_DATA) {
            GeoMap geoMap = new GeoMap(Integer.toString(map.getMapId()), map.getWorldSize());
            try {
                if (GeoWorldLoader.loadWorld(map.getMapId(), models, geoMap)) {
                    geoMaps.put(map.getMapId(), geoMap);
                }
            } catch (Throwable t) {
                mapsWithErrors.add(map.getMapId());
                geoMaps.put(map.getMapId(), DummyGeoData.DUMMY_MAP);
            }
            Util.printCurrentProgress();
        }
        Util.printEndProgress();

        if (mapsWithErrors.size() > 0) {
            log.warn("Some maps were not loaded correctly and reverted to dummy implementation: ");
            log.warn(mapsWithErrors.toString());
        }
    }

    /**
     * @return
     */
    protected Map<String, Spatial> loadMeshes() {
        log.info("Loading meshes..");
        Map<String, Spatial> models = null;
        try {
            models = GeoWorldLoader.loadMeshs("data/geo/meshs.geo");
        } catch (IOException e) {
            throw new IllegalStateException("Problem loading meshes", e);
        }
        return models;
    }

    @Override
    public GeoMap getMap(int worldId) {
        GeoMap geoMap = geoMaps.get(worldId);
        return geoMap != null ? geoMap : DummyGeoData.DUMMY_MAP;
    }
}
