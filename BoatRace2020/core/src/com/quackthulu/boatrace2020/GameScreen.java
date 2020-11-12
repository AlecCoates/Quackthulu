package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;

public class GameScreen implements Screen {
    private BoatRace parent;
    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

    private TextureRegion background, playerBoatTexture, enemyDuckTexture;

    //timing
    private int backgroundOffset;

    //world parameters
    private final int WORLD_HEIGHT = 72;
    private final int WORLD_WIDTH = 128;

    //game objects
    private final Boat playerBoat;
    private final Duck enemyDuck;
    //private LinkedList<Enemy> enemyObjects;

    GameScreen(BoatRace boatRace){
        parent = boatRace;
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images3.atlas");

        playerBoatTexture = textureAtlas.findRegion("player");
        enemyDuckTexture = textureAtlas.findRegion("duck_in_a_top_hat");
        background = textureAtlas.findRegion("sea2");
        backgroundOffset = 0;

        //set up game objects
        playerBoat = new Boat(2,2,10,
                10, playerBoatTexture, 10, 10, true);
        enemyDuck = new Duck(2,3,WORLD_WIDTH/2,WORLD_HEIGHT*3/4,
                enemyDuckTexture,0);

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        batch.begin();

        detectInput(delta);

        //draw background
        batch.draw(background,0,0,WORLD_WIDTH,WORLD_HEIGHT);

        //draw player
        playerBoat.draw(batch);
        enemyDuck.draw(batch);

        //detect collisions
        detectCollisions();

        //detect inputs
        detectInput(delta);

        //updates
        playerBoat.update(delta);

        batch.end();
    }

    private void detectCollisions(){
        //for each boat check if intersects enemy object

    }

    private void detectInput(float delta){
        //keyboard input

        //strategy: determine the max distance the ship can move
        //check each key that matters and move accordingly

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playerBoat.boundingBox.x;
        downLimit = -playerBoat.boundingBox.y;
        rightLimit = WORLD_WIDTH-playerBoat.boundingBox.x - playerBoat.boundingBox.width;
        upLimit = WORLD_WIDTH-playerBoat.boundingBox.y - playerBoat.boundingBox.height;

        if(Gdx.input.isKeyPressed(Input.Keys.D) && rightLimit > 0){
            playerBoat.translate(Math.min(playerBoat.movementSpeed*delta, rightLimit),0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && leftLimit < 0){
            playerBoat.translate(Math.max(-playerBoat.movementSpeed*delta, leftLimit),0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W) && upLimit > 0){
            playerBoat.translate(0f, Math.min(playerBoat.movementSpeed*delta, upLimit));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) && downLimit < 0){
            playerBoat.translate(0f, Math.max(-playerBoat.movementSpeed*delta, downLimit));
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
        batch.setProjectionMatrix(camera.combined);
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
    public void show() {

    }

    @Override
    public void dispose() {

    }
}
