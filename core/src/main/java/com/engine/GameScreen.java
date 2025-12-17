package com.engine;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.engine.assets.AssetService;
import com.engine.assets.MapAsset;
import com.engine.config.Constants;

public class GameScreen extends ScreenAdapter {
    private final GdxGame game;
    private final Batch batch;
    private final AssetService assetService;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer; // TiledMapRenderer is the parent type

    public GameScreen(GdxGame game) {
        this.game = game;
        this.batch = game.getBatch();
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, Constants.UNIT_SCALE, batch);
    }

    @Override
    public void show() {
        assetService.load(MapAsset.MAIN);
        mapRenderer.setMap(assetService.get(MapAsset.MAIN));
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        batch.setColor(Color.WHITE);
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
