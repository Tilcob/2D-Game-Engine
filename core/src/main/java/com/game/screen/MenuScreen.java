package com.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.GdxGame;
import com.game.assets.MusicAsset;
import com.game.assets.SkinAsset;
import com.game.input.KeyboardController;
import com.game.input.UiControllerState;
import com.game.ui.model.MenuViewModel;
import com.game.ui.view.MenuView;

public class MenuScreen extends ScreenAdapter {
    private final GdxGame game;
    private final Stage stage;
    private final Skin skin;
    private final Viewport uiViewport;
    private final KeyboardController  keyboardController;

    public MenuScreen(GdxGame game) {
        this.game = game;
        this.uiViewport = new FitViewport(800f, 450f);
        this.stage = new Stage(uiViewport, game.getBatch());
        this.skin = game.getAssetManager().get(SkinAsset.DEFAULT);
        this.keyboardController = new KeyboardController(UiControllerState.class, null, stage);
    }

    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height, true);
    }

    @Override
    public void show() {
        game.setInputProcessors(stage, keyboardController);

        stage.addActor(new MenuView(skin, stage, new MenuViewModel(game)));
        game.getAudioManager().playMusic(MusicAsset.MENU);
    }

    @Override
    public void hide() {
        this.stage.clear();
    }

    @Override
    public void render(float delta) {
        uiViewport.apply();
        stage.getBatch().setColor(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
