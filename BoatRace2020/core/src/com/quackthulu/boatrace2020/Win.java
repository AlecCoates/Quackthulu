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

import java.util.LinkedList;

public class Win implements Screen {

    private Stage stage;
    private BoatRace parent;
    private Label WinLable;
    private LinkedList<Boat> finalBoats;
    private Boat playerBoat;
    private int playerPosition = 5;

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

        playerBoat = finalBoats.getLast();
        finalBoats.removeLast();

        //calculates the total boats in the last race
        if (parent.getLevel() <= 2){
            playerPosition = 5;
        }else{
            playerPosition = 7-parent.getLevel();
        }

        //calculates the players final position in the last race
        for (Boat boat: finalBoats){
            if (playerBoat.getFinishingTime() <= boat.getFinishingTime()){
                playerPosition--;
            }
        }

        //selects the correct message to display
        if(parent.getLevel() == 4){
            switch (playerPosition){
                case 1:
                    WinLable = new Label("Gold Medal",skin);
                    break;
                case 2:
                    WinLable = new Label("Silver Medal",skin);
                    break;
                case 3:
                    WinLable = new Label("Bronze Medal",skin);
                    break;
            }
        }else{
            System.out.println("Hello World Else");
            switch (playerPosition) {
                case 1:
                    WinLable = new Label("You Came First!",skin);
                    break;
                case 2:
                    WinLable = new Label("You Came Second!",skin);
                    break;
                case 3:
                    WinLable = new Label("You Came Third",skin);
                    break;
                case 4:
                    WinLable = new Label("You Came Fourth",skin);
                    break;
                case 5:
                    WinLable = new Label("You Came Fifth",skin);
                    break;
            }
        }


        table.add(WinLable).colspan(2);
        table.row().pad(10,0,0,10);
        table.row().pad(10,0,0,10);

        //return to main menu button
        if (parent.getLevel() != 4){
            TextButton nextButton = new TextButton("Next Stage",skin);
            nextButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    parent.changeScreen(BoatRace.INTER);
                }
            });
            table.add(nextButton).colspan(2);
        }
        else {
            final TextButton returnButton = new TextButton("Quit", skin);
            returnButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    parent.changeScreen(BoatRace.MENU);
                }
            });
            table.add(returnButton).colspan(2);
        }
        playerPosition = 5;
    }

    //sets the boats that survived from the last race
    public void setFinalBoats(LinkedList<Boat> finalBoats){
        this.finalBoats = finalBoats;
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
