package com.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.game.GdxGame;
import com.game.assets.AssetManager;
import com.game.assets.AtlasAsset;

public class LoadingScreen extends ScreenAdapter {
    private final GdxGame game;
    private final AssetManager assetManager;

    public LoadingScreen(GdxGame game, AssetManager assetManager) {
        this.game = game;
        this.assetManager = assetManager;
    }

    @Override
    public void show() {
        for (AtlasAsset asset: AtlasAsset.values()) {
            assetManager.queue(asset);
        }
    }

    @Override
    public void render(float delta) {
        if (assetManager.update()) {
            Gdx.app.debug("LoadingScreen", "Finished asset loading.");
            creatScreens();
            game.removeScreen(this);
            dispose();
            game.setScreen(GameScreen.class);
        }
    }

    private void creatScreens() {
        game.addScreen(new GameScreen(game));
    }
}
