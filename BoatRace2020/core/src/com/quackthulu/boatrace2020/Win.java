package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Win implements Screen {

    private Stage stage;
    private BoatRace parent;
    private Label WinLable;
    Win(BoatRace boatRace){
        parent = boatRace;

    }

    @Override
    public void show() {
        // stage acts as a controller in which it reacts to user input
        stage = new Stage(new ScreenViewport());
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        //Table is created. Everything inserted into table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //Button creation
        Skin skin = new Skin(Gdx.files.internal("skins/pixthulhu/skin/pixthulhu-ui.json"));

        //return to main menu button
        final TextButton returnButton = new TextButton("Replay", skin);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(BoatRace.MENU);
            }
        });

        WinLable = new Label("You Win!",skin);

        table.add(WinLable).colspan(2);
        table.row().pad(10,0,0,10);
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

    }

    @Override
    public void dispose() {

    }

}
