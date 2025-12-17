package com.game.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.game.assets.AssetManager;
import com.game.assets.AtlasAsset;
import com.game.component.Controller;
import com.game.component.Graphic;
import com.game.component.Move;
import com.game.component.Transform;
import com.game.config.Constants;

public class TiledAshleyConfigurator {
    private final Engine engine;
    private final AssetManager assetManager;

    public TiledAshleyConfigurator(Engine engine, AssetManager assetManager) {
        this.engine = engine;
        this.assetManager = assetManager;
    }

    public void onLoadObject(TiledMapTileMapObject object) {
        Entity entity = engine.createEntity();
        TiledMapTile tile = object.getTile();
        TextureRegion region = getTextureRegion(tile);
        int z = tile.getProperties().get("z", 1, Integer.class);

        entity.add(new Graphic(Color.WHITE.cpy(), region));
        addEntityTransform(
            object.getX(), object.getY(), z,
            region.getRegionWidth(), region.getRegionHeight(),
            object.getScaleX(), object.getScaleY(), entity
        );
        addEntityController(object, entity);
        addEntityMove(tile, entity);

        engine.addEntity(entity);
    }

    private void addEntityMove(TiledMapTile tile, Entity entity) {
        float speed = tile.getProperties().get("speed", 0f, Float.class);
        if (speed == 0) return;

        entity.add(new Move(speed));
    }

    private void addEntityController(TiledMapTileMapObject object, Entity entity) {
        boolean controller = object.getProperties().get("controller", false, Boolean.class);
        if(!controller) return;

        entity.add(new Controller());
    }

    private void addEntityTransform(float x, float y, int z, float w, float h,
                                    float scaleX, float scaleY, Entity entity) {
        Vector2 position = new Vector2(x, y);
        Vector2 size = new Vector2(w, h);
        Vector2 scaling = new Vector2(scaleX, scaleY);

        position.scl(Constants.UNIT_SCALE);
        size.scl(Constants.UNIT_SCALE);

        entity.add(new Transform(position, z, size, scaling, 0));
    }

    private TextureRegion getTextureRegion(TiledMapTile tile) {
        String atlasAssetStr = tile.getProperties().get("atlasAsset", AtlasAsset.OBJECTS.name(), String.class);
        AtlasAsset atlasAsset = AtlasAsset.valueOf(atlasAssetStr);
        TextureAtlas textureAtlas = assetManager.get(atlasAsset);
        FileTextureData textureData = (FileTextureData) tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(atlasKey + "/" + atlasKey);

        if  (region != null) return region;

        return tile.getTextureRegion();
    }
}
