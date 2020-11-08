package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class GameScreen implements Screen {

    //screen
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

    private TextureRegion backgroundTexture, playerBoatTexture, enemyDuckTexture,
            fullHUDTexture, halfHUDTexture;

    //timing

    //world parameters
    private final int WORLD_HEIGHT = 600;
    private final int WORLD_WIDTH = 800;

    //Background
    private Background background;
    private ArrayList<Background> backgrounds;

    //game objects
    //player
    private Boat playerBoat;
    private final int playerHealth = 9;

    //enemy
    private LinkedList<Enemy> enemyObjects;

    //HUD
    private HUD hud;

    GameScreen(){
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images5.atlas");

        //set up textures
        playerBoatTexture = textureAtlas.findRegion("player");
        playerBoatTexture.flip(false,true);
        enemyDuckTexture = textureAtlas.findRegion("duck_in_a_top_hat");
        backgroundTexture = textureAtlas.findRegion("sea2");
        fullHUDTexture = textureAtlas.findRegion("full_paddle");
        halfHUDTexture = textureAtlas.findRegion("half_paddle");

        //initialise background
        backgrounds = new ArrayList<Background>(9);

        //top layer
        backgrounds.add(new Background(backgroundTexture,-WORLD_WIDTH,WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT));
        backgrounds.add(new Background(backgroundTexture,0,WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT));
        backgrounds.add(new Background(backgroundTexture,WORLD_WIDTH,WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT));

        //middle layer
        backgrounds.add(new Background(backgroundTexture,-WORLD_WIDTH,0,WORLD_WIDTH,WORLD_HEIGHT));
        backgrounds.add(new Background(backgroundTexture,0,0,WORLD_WIDTH,WORLD_HEIGHT));
        backgrounds.add(new Background(backgroundTexture,WORLD_WIDTH,0,WORLD_WIDTH,WORLD_HEIGHT));

        //bottom layer
        backgrounds.add(new Background(backgroundTexture,-WORLD_WIDTH,-WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT));
        backgrounds.add(new Background(backgroundTexture,0,-WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT));
        backgrounds.add(new Background(backgroundTexture,WORLD_WIDTH,-WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT));

        //set up game objects
        playerBoat = new Boat(WORLD_WIDTH/2, 200,69,
                69, 10, playerBoatTexture, 100, playerHealth,true);
        hud = new HUD(playerHealth,fullHUDTexture,halfHUDTexture);
        enemyObjects = new LinkedList<Enemy>();
        enemyObjects.add(new Duck(200,100,200,200, 3,
                enemyDuckTexture,0));
        enemyObjects.add(new Duck(200,500,200,200, 3,
                enemyDuckTexture,0));

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        batch.begin();

        detectInput(delta);

        //draw background
        renderBackground();

        //draw heads up display
        hud.draw(batch,playerBoat.boundingBox.x,playerBoat.boundingBox.y);

        //draw enemies
        renderEnemies();

        //draw player
        playerBoat.draw(batch);

        //detect collisions
        detectCollisions();

        //detect inputs
        detectInput(delta);

        //updates


        batch.end();
    }

    private void initialiseBackground(){

    }

    private void renderBackground(){
        Iterator iterator = backgrounds.iterator();
        while(iterator.hasNext()){
            Background b = (Background) iterator.next();
            b.draw(batch);
        }
    }

    private void renderEnemies(){
        Iterator iterator = enemyObjects.iterator();
        while(iterator.hasNext()){
            Enemy e = (Enemy) iterator.next();
            if(e.intersects(playerBoat.boundingBox)){
                iterator.remove();
                hud.damage(e);
                playerBoat.damage(e);
                System.out.println("damage");
            }else {
                e.draw(batch);
            }
        }
    }

    private void detectCollisions(){
        //for each boat check if intersects enemy object

    }

    private void detectInput(float delta){
        //keyboard input
        //Background movement
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            Iterator iteratorBackground = backgrounds.iterator();
            while(iteratorBackground.hasNext()){
                Background b = (Background) iteratorBackground.next();
                b.translate(playerBoat.movementSpeed*delta,0f);
                if(b.center().x-playerBoat.boundingBox.x > WORLD_WIDTH*1.05){
                    b.translate(-WORLD_WIDTH*2, 0f);
                }
            }
            Iterator iteratorEnemy = enemyObjects.iterator();
            while(iteratorEnemy.hasNext()){
                Enemy e = (Enemy)iteratorEnemy.next();
                e.translate(playerBoat.movementSpeed*delta,0f);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            Iterator iteratorBackground = backgrounds.iterator();
            while(iteratorBackground.hasNext()){
                Background b = (Background) iteratorBackground.next();
                b.translate(-playerBoat.movementSpeed*delta,0f);
                if(playerBoat.boundingBox.x-b.center().x > WORLD_WIDTH*.95){
                    b.translate(WORLD_WIDTH*2, 0f);
                }
            }
            Iterator iteratorEnemy = enemyObjects.iterator();
            while(iteratorEnemy.hasNext()){
                Enemy e = (Enemy)iteratorEnemy.next();
                e.translate(-playerBoat.movementSpeed*delta,0f);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            Iterator iteratorBackground = backgrounds.iterator();
            while(iteratorBackground.hasNext()){
                Background b = (Background) iteratorBackground.next();
                b.translate(0f, playerBoat.movementSpeed*delta);
                if(b.center().y-playerBoat.boundingBox.y > WORLD_HEIGHT*1.2){
                    b.translate(0f, -WORLD_HEIGHT*2);
                }
            }
            Iterator iteratorEnemy = enemyObjects.iterator();
            while(iteratorEnemy.hasNext()){
                Enemy e = (Enemy)iteratorEnemy.next();
                e.translate(0f, playerBoat.movementSpeed*delta);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            Iterator iteratorBackground = backgrounds.iterator();
            while(iteratorBackground.hasNext()){
                Background b = (Background) iteratorBackground.next();
                b.translate(0f, -playerBoat.movementSpeed*delta);
                if(playerBoat.boundingBox.y-b.center().y > WORLD_HEIGHT*.8){
                    b.translate(0f, WORLD_HEIGHT*2);
                }
            }
            Iterator iteratorEnemy = enemyObjects.iterator();
            while(iteratorEnemy.hasNext()){
                Enemy e = (Enemy)iteratorEnemy.next();
                e.translate(0f, -playerBoat.movementSpeed*delta);
            }
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
        batch.dispose();
    }
}
