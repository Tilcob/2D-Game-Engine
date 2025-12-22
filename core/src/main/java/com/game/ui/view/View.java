package com.game.ui.view;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.game.ui.model.ViewModel;

public abstract class View<T extends ViewModel> extends Table {
    protected final Stage stage;
    protected final Skin skin;
    protected final T viewModel;

    public View(Skin skin, Stage stage, T viewModel) {
        super(skin);
        this.skin = skin;
        this.stage = stage;
        this.viewModel = viewModel;
        setupUI();
    }

    @Override
    public void setStage(Stage stage) {
        super.setStage(stage);
        if (stage == null) {
            viewModel.clearPropertyChanges();
        } else {
            setupPropertyChanges();
        }
    }

    protected abstract void setupUI();

    protected void setupPropertyChanges() {

    }
}
