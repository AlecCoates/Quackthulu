package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class PickBoatMenu implements Screen{
    private BoatRace parent;
    private Stage stage;
    private Label titleLabel;
    private Array<Label> boatLabelArray;
    private Array<CheckBox> checkBoxArray;
    public int boatNumber;

    public PickBoatMenu(BoatRace boatRace) {
        parent = boatRace;
        boatNumber = parent.playerBoatNumber;
    }

    //draws menu to pick a boat to the screen
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
        Skin skin = new Skin(Gdx.files.internal("skins/pixthulhu/skin/pixthulhu-ui.json"));

        //group button creation, so only one can be selected
        ButtonGroup<CheckBox> boatCheckboxes = new ButtonGroup<>();
        for (int i = 0; i < BoatsStats.numOfBoats(); i++) {
            final int finalI = i;
            boatCheckboxes.add(new CheckBox(null, skin));
            boatCheckboxes.getButtons().get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    boatNumber = finalI;
                }
            });
        }
        boatCheckboxes.getButtons().get(boatNumber).setChecked(true);

        //continue to game
        final TextButton continueButton = new TextButton("Continue", skin);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.playerBoatNumber = boatNumber;
                parent.changeScreen(BoatRace.GAME);
            }
        });

        titleLabel = new Label("Pick Your Boat", skin);

        boatLabelArray = new Array<>();
        for (int i = 0; i < BoatsStats.numOfBoats(); i++) {
            boatLabelArray.add(new Label(BoatsStats.getBoatStats(i).getName(), skin));
        }

        table.add(titleLabel).colspan(2);
        table.row().pad(10,0,0,10);
        checkBoxArray = new Array<>(boatCheckboxes.getButtons());
        for(int i = 0; i<checkBoxArray.size; i++){
            table.add(boatLabelArray.get(i)).colspan(2);
            table.add(checkBoxArray.get(i));
            table.row().pad(10,0,0,10);
        }
        table.row().pad(10,0,0,10);
        table.add(continueButton).colspan(2);
        //table.row().pad(10,0,0,10);
        //table.add(returnButton).colspan(2);
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
