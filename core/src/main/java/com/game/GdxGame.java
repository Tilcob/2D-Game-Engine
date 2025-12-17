package com.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.assets.AssetManager;
import com.game.config.Constants;
import com.game.screen.GameScreen;
import com.game.screen.LoadingScreen;

import java.util.HashMap;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GdxGame extends Game {
    private Batch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private AssetManager assetManager;
    private GLProfiler glProfiler;
    private FPSLogger fpsLogger;
    private final Map<Class<? extends Screen>, Screen> screenCache = new HashMap<>();

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
        assetManager = new AssetManager(new InternalFileHandleResolver());
        glProfiler = new GLProfiler(Gdx.graphics);
        fpsLogger = new FPSLogger();

        glProfiler.enable();
        addScreen(new LoadingScreen(this, assetManager));
        setScreen(LoadingScreen.class);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        super.resize(width, height);
    }

    @Override
    public void render() {
        glProfiler.reset();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        Gdx.graphics.setTitle("GdxGame - Draw Cals: " + glProfiler.getDrawCalls()); // Draw calls should be minimized!!
        fpsLogger.log();
    }

    public void addScreen(Screen screen) {
        screenCache.put(screen.getClass(), screen);
    }

    public void removeScreen(Screen screen) {
        screenCache.remove(screen.getClass());
    }

    public void setScreen(Class<? extends Screen> screenClass) {
        Screen screen = screenCache.get(screenClass);
        if (screen == null) {
            throw new GdxRuntimeException("No screen with class: " + screenClass + " found in the screen cache.");
        }
        super.setScreen(screen);
    }

    @Override
    public void dispose() {
        screenCache.values().forEach(Screen::dispose);
        screenCache.clear();
        batch.dispose();
        assetManager.debugDiagnostics();
        assetManager.dispose();
    }

    public Batch getBatch() {
        return batch;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public AssetManager getAssetService() {
        return assetManager;
    }
}
