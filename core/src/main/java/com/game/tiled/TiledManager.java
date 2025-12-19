package com.game.tiled;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.game.assets.AssetManager;
import com.game.assets.MapAsset;
import com.game.config.Constants;

import java.util.function.Consumer;

public class TiledManager {
    private final AssetManager assetManager;
    private Consumer<TiledMap> mapChangeConsumer;
    private Consumer<TiledMapTileMapObject> loadObjectConsumer;
    private LoadTileConsumer loadTileConsumer;
    private TiledMap currentMap;

    public TiledManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.mapChangeConsumer = null;
        this.loadObjectConsumer = null;
        this.currentMap = null;
        this.loadTileConsumer = null;
    }


    public TiledMap loadMap(MapAsset mapAsset){
        TiledMap tiledMap = assetManager.load(mapAsset);
        tiledMap.getProperties().put(Constants.MAP_ASSET, mapAsset);
        return tiledMap;
    }

    public void setMap(TiledMap map) {
        if (currentMap != null) {
            assetManager.unload(currentMap.getProperties().get(Constants.MAP_ASSET, MapAsset.class));
        }
        currentMap = map;
        loadMapObjects(map);
        if (mapChangeConsumer != null) {
            mapChangeConsumer.accept(map);
        }
    }

    private void loadMapObjects(TiledMap tiledMap) {
        for (MapLayer layer : tiledMap.getLayers()) {
            if (Constants.OBJECT_LAYER.equals(layer.getName())) {
                loadObjectLayer(layer);
            } else if (layer instanceof TiledMapTileLayer tileLayer) {
                loadTileLayer(tileLayer);
            }
        }
    }

    private void loadTileLayer(TiledMapTileLayer tileLayer) {
        if (loadObjectConsumer == null) return;

        for (int y = 0; y < tileLayer.getHeight(); y++) {
            for (int x = 0; x < tileLayer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if (cell == null) continue;

                loadTileConsumer.accept(cell.getTile(), x, y);
            }
        }
    }

    private void loadObjectLayer(MapLayer objectLayer) {
        if (loadObjectConsumer == null) return;

        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof TiledMapTileMapObject tileMapObject) {
                loadObjectConsumer.accept(tileMapObject);
            } else {
                throw new GdxRuntimeException("Unsupported object type: " + object.getClass().getSimpleName());
            }
        }
    }

    public void setMapChangeConsumer(Consumer<TiledMap> mapChangeConsumer) {
        this.mapChangeConsumer = mapChangeConsumer;
    }

    public void setLoadObjectConsumer(Consumer<TiledMapTileMapObject> loadObjectConsumer) {
        this.loadObjectConsumer = loadObjectConsumer;
    }

    public void setLoadTileConsumer(LoadTileConsumer loadTileConsumer) {
        this.loadTileConsumer = loadTileConsumer;
    }

    @FunctionalInterface
    public interface LoadTileConsumer {
        void accept(TiledMapTile tile, float x, float y);


    }
}
