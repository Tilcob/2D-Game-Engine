package com.game.ui.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.ui.model.MenuViewModel;

public class MenuView extends View<MenuViewModel> {


    public MenuView(Skin skin, Stage stage, MenuViewModel viewModel) {
        super(skin, stage, viewModel);
    }

    @Override
    protected void setupUI() {
        setFillParent(true);

        Image image = new Image(skin, "banner");
        add(image).row();

        setupMenuContent();

        Label label = new Label("by Quillraven 2025", skin, "small");
        label.setColor(skin.getColor("white"));
        add(label).padRight(2.0f).expandX().align(Align.bottomRight);
    }

    private void setupMenuContent() {
        Table contentTable = new Table();
        contentTable.setBackground(skin.getDrawable("frame"));
        contentTable.padLeft(40.0f);
        contentTable.padRight(40.0f);
        contentTable.padTop(25.0f);
        contentTable.padBottom(20.0f);

        TextButton textButton = new TextButton("Start Game", skin);
        contentTable.add(textButton);
        contentTable.row();

        Slider musicSlider = setupVolumesSlider(contentTable, "Music Volume");
        Slider soundSlider = setupVolumesSlider(contentTable, "Sound Volume");

        textButton = new TextButton("Quit Game", skin);
        contentTable.add(textButton).padTop(10.0f);
        add(contentTable).row();
    }

    private Slider setupVolumesSlider(Table contentTable, String title) {
        Table table = new Table();

        Label label = new Label(title, skin);
        label.setColor(skin.getColor("sand"));
        table.add(label).row();

        Slider slider = new Slider(0f, 1f, .05f, false, skin);
        table.add(slider);
        contentTable.add(table).padTop(10.0f).row();

        return slider;
    }
}
