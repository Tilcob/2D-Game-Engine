package com.game.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.game.GdxGame;
import com.game.assets.MapAsset;
import com.game.config.Constants;
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
    private final World physicWorld;

    public GameScreen(GdxGame game) {
        this.game = game;
        this.engine = new Engine();
        this.physicWorld = new World(Constants.GRAVITY, true);
        this.physicWorld.setAutoClearForces(false);
        this.tiledManager = new TiledManager(game.getAssetManager(), physicWorld);
        this.tiledAshleyConfigurator = new TiledAshleyConfigurator(engine, game.getAssetManager(), this.physicWorld);
        this.keyboardController = new KeyboardController(GameControllerState.class, engine);

        this.engine.addSystem(new ControllerSystem());
        this.engine.addSystem(new PhysicMoveSystem());
        this.engine.addSystem(new FsmSystem());
        this.engine.addSystem(new FacingSystem());
        this.engine.addSystem(new PhysicSystem(physicWorld, Constants.FIXED_INTERVAL));
        this.engine.addSystem(new AnimationSystem(game.getAssetManager()));
        this.engine.addSystem(new CameraSystem(game.getCamera()));
        this.engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));
        this.engine.addSystem(new PhysicDebugRenderSystem(physicWorld, game.getCamera()));
    }

    @Override
    public void show() {
        game.setInputProcessors(keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        Consumer<TiledMap> cameraConsumer = engine.getSystem(CameraSystem.class)::setMap;
        tiledManager.setMapChangeConsumer(renderConsumer.andThen(cameraConsumer));
        tiledManager.setLoadObjectConsumer(tiledAshleyConfigurator::onLoadObject);
        tiledManager.setLoadTileConsumer(tiledAshleyConfigurator::onLoadTile);

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
        physicWorld.dispose();
    }
}
