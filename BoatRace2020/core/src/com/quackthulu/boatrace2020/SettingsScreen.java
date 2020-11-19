package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Preferences;

public class SettingsScreen implements Screen {
    private BoatRace parent;
    private Stage stage;
    private Label titleLabel;
    private Label volumeSoundLabel;
    private Label soundEnabledLabel;

    public SettingsScreen(BoatRace boatRace) {
        parent = boatRace;
    }

    @Override
    public void show() {
        // stage acts as a controller in which it reacts to user input
        stage = new Stage(new ScreenViewport());

        stage.clear();

        //inputs processor for settings screen
        Gdx.input.setInputProcessor(stage);

        //Table is created. Everything inserted into table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //Button creation
        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        final Slider volumeSoundSlider = new Slider(0f,1f,0.1f,false,skin);
        volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });

        //Sound Enabled check box
        final CheckBox soundCheckbox = new CheckBox(null,skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEnabled());
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEnabled(enabled);
                return false;
            }
        });

        //return to main menu button
        final TextButton returnButton = new TextButton("Return",skin);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(BoatRace.MENU_SCREEN);
            }
        });

        titleLabel = new Label("Settings",skin);
        volumeSoundLabel = new Label("Sound Volume",skin);
        soundEnabledLabel = new Label("Sound Enabled",skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);
        table.row().pad(10,0,0,10);
        table.add(soundEnabledLabel).left();
        table.add(soundCheckbox);
        table.row().pad(10,0,0,10);
        table.add(returnButton).colspan(2);


    }

    @Override
    public void render(float delta) {
        //Clears screen, allowing next items to be drawn
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //informs stage screen size has changed, viewport should be recalculated
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
