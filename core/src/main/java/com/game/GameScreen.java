package com.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.assets.AssetService;
import com.game.assets.MapAsset;
import com.game.system.RenderSystem;

public class GameScreen extends ScreenAdapter {
    private final GdxGame game;
    private final Batch batch;
    private final AssetService assetService;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final Engine engine;  // Could also be done with PoolingEngine for better performance in Java

    public GameScreen(GdxGame game) {
        this.game = game;
        this.batch = game.getBatch();
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.engine = new Engine();

        this.engine.addSystem(new RenderSystem(batch, viewport));
    }

    @Override
    public void show() {
        assetService.load(MapAsset.MAIN);
        engine.getSystem(RenderSystem.class).setMap(assetService.get(MapAsset.MAIN));
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
