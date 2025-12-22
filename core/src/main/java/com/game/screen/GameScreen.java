package com.game.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.GdxGame;
import com.game.assets.MapAsset;
import com.game.audio.AudioManager;
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
    private final AudioManager audioManager;
    private final Stage stage;
    private final Viewport uiViewport;

    public GameScreen(GdxGame game) {
        this.game = game;
        this.engine = new Engine();
        this.physicWorld = new World(Constants.GRAVITY, true);
        this.physicWorld.setAutoClearForces(false);
        this.tiledManager = new TiledManager(game.getAssetManager(), physicWorld);
        this.tiledAshleyConfigurator = new TiledAshleyConfigurator(engine, game.getAssetManager(), this.physicWorld);
        this.keyboardController = new KeyboardController(GameControllerState.class, engine, null);
        this.audioManager = game.getAudioManager();
        this.uiViewport = new FitViewport(320f, 180f);
        this.stage = new Stage(uiViewport, game.getBatch());

        this.engine.addSystem(new ControllerSystem(audioManager));
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
    public void resize(int width, int height) {
        super.resize(width, height);
        uiViewport.update(width, height, true);
    }

    @Override
    public void show() {
        game.setInputProcessors(stage, keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

        Consumer<TiledMap> renderConsumer = engine.getSystem(RenderSystem.class)::setMap;
        Consumer<TiledMap> cameraConsumer = engine.getSystem(CameraSystem.class)::setMap;
        Consumer<TiledMap> audioConsumer = audioManager::setMap;

        tiledManager.setMapChangeConsumer(renderConsumer.andThen(cameraConsumer).andThen(audioConsumer));
        tiledManager.setLoadObjectConsumer(tiledAshleyConfigurator::onLoadObject);
        tiledManager.setLoadTileConsumer(tiledAshleyConfigurator::onLoadTile);

        TiledMap map = tiledManager.loadMap(MapAsset.MAIN);
        tiledManager.setMap(map);
    }

    @Override
    public void hide() {
        engine.removeAllEntities();
        stage.clear();
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1 / 30f);
        engine.update(delta);

        uiViewport.apply();
        stage.getBatch().setColor(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        for (EntitySystem system : engine.getSystems()) {
            if (system instanceof Disposable disposable) {
                disposable.dispose();
            }
        }
        physicWorld.dispose();
        stage.dispose();
    }
}
