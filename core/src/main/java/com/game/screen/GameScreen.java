package com.game.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.GdxGame;
import com.game.assets.AssetManager;
import com.game.assets.MapAsset;
import com.game.system.RenderSystem;
import com.game.tiled.TiledAshleyConfigurator;
import com.game.tiled.TiledManager;

import java.util.function.Consumer;

public class GameScreen extends ScreenAdapter {
    private final GdxGame game;
    private final Batch batch;
    private final AssetManager assetManager;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final Engine engine;  // Could also be done with PoolingEngine for better performance in Java
    private final TiledManager tiledManager;
    private final TiledAshleyConfigurator tiledAshleyConfigurator;

    public GameScreen(GdxGame game) {
        this.game = game;
        this.batch = game.getBatch();
        this.assetManager = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.tiledManager = new TiledManager(assetManager);
        this.engine = new Engine();
        this.tiledAshleyConfigurator = new TiledAshleyConfigurator(engine, assetManager);

        this.engine.addSystem(new RenderSystem(batch, viewport, camera));
    }

    @Override
    public void show() {
        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        tiledManager.setMapChangeConsumer(renderConsumer);
        tiledManager.setLoadObjectConsumer(tiledAshleyConfigurator::onLoadObject);
        TiledMap map = tiledManager.loadMap(MapAsset.MAIN);
        tiledManager.setMap(map);
    }

    @Override
    public void hide() {
        engine.removeAllEntities();
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1 / 30f);
        engine.update(delta);
    }

    @Override
    public void dispose() {
        for (EntitySystem system : engine.getSystems()) {
            if (system instanceof Disposable disposable) {
                disposable.dispose();
            }
        }
    }
}
