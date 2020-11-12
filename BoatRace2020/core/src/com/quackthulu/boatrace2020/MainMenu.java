package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import javax.swing.*;
import javax.swing.event.ChangeEvent;


public class MainMenu implements Screen {
    private Stage stage;
    private BoatRace parent;
    MainMenu(BoatRace boatRace){
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
        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        TextButton startGame = new TextButton("Start Game",skin);
        TextButton settings = new TextButton("Settings",skin);
        TextButton quit = new TextButton("Quit",skin);

        //Adding buttons to the table
        table.add(startGame).fillX().uniformX();
        table.row().pad(20,0,20,0);
        table.add(settings).fillX().uniformX();
        table.row();
        table.add(quit).fillX().uniformX();

        quit.addListener(new ChangeListener(){
            @Override
                    public  void changed(ChangeEvent event, Actor actor){
                Gdx.app.exit();
            }
        });
        startGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(BoatRace.GAME);
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(BoatRace.SETTINGS);
            }
        });
    }

    @Override
    public void render(float delta) {
        //Clears screen, allowing next items to be drawn
        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Telling stage to act and draw itself
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
        // dispose of assets that arent needed at the time
        stage.dispose();
    }
}
