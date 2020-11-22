package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.BufferedReader;
import java.io.IOException;

public class InstructionsScreen implements Screen {
    /**
     * InstructionsScreen implements libGDX screen to create an instructions screen
     * InstructionsScreen main function runs FileHandler and BufferedReader to read instructions txt file
     * txt file is then displayed onto table as well as a return button
     * @author Aaron Price
     */
    private BoatRace parent;
    private Stage stage;

    public InstructionsScreen(BoatRace parent) {
        this.parent = parent;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        stage.clear();


        //Button creation
        Skin skin = new Skin(Gdx.files.internal("skin/neon-ui.json"));

        //inputs processor for screen
        Gdx.input.setInputProcessor(stage);

        //reads instruction text file
        FileHandle file = Gdx.files.internal("BoatRace2020 Rules.txt");
        BufferedReader br = file.reader(100, "Big5");

        //create strings to store txt files contents
        String line;
        String content = null;

        //try,catch to ensure no errors when loading and reading file, implements contents of txt file into string via
        //content acting as string storage
        try{
            line = br.readLine();
            content = line;
            while (line != null){
                line = br.readLine();
                if(line != null) {
                    content = content +"\n" + line;
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }

        //Table is created. Everything inserted into table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        //return to main menu button
        final TextButton returnButton = new TextButton("Return",skin);
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(BoatRace.MENU);
            }
        });

        //Adding buttons to the table
        table.add(new Label(content,skin)).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(returnButton).fillX().uniformX();
        table.row().pad(10,0,0,10);
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
        stage.getViewport().update(width, height, true);

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
