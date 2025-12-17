package com.game.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.game.GdxGame;
import com.game.assets.MapAsset;
import com.game.input.GameControllerState;
import com.game.input.KeyboardController;
import com.game.system.*;
import com.game.tiled.TiledAshleyConfigurator;
import com.game.tiled.TiledManager;

import java.util.function.Consumer;

public class GameScreen extends ScreenAdapter {
    private final Engine engine;  // Could also be done with PoolingEngine for better performance in Java
    private final TiledManager tiledManager;
    private final TiledAshleyConfigurator tiledAshleyConfigurator;
    private final KeyboardController keyboardController;
    private final GdxGame game;

    public GameScreen(GdxGame game) {
        this.game = game;
        this.tiledManager = new TiledManager(game.getAssetManager());
        this.engine = new Engine();
        this.tiledAshleyConfigurator = new TiledAshleyConfigurator(engine, game.getAssetManager());
        this.keyboardController = new KeyboardController(GameControllerState.class, engine);

        this.engine.addSystem(new ControllerSystem());
        this.engine.addSystem(new MoveSystem());
        this.engine.addSystem(new FsmSystem());
        this.engine.addSystem(new FacingSystem());
        this.engine.addSystem(new AnimationSystem(game.getAssetManager()));
        this.engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));
    }

    @Override
    public void show() {
        game.setInputProcessors(keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

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
