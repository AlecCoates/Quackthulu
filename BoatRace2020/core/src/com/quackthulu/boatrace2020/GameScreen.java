package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.quackthulu.boatrace2020.basics.Force;
import com.quackthulu.boatrace2020.basics.TimedTexture;

import java.util.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class GameScreen implements Screen {

    //screen
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private TextureRegion backgroundTexture, landTexture, playerBoatTexture, enemyDuckTexture, fullHUDTexture, halfHUDTexture;

    //timing

    //world parameters
    private int WORLD_HEIGHT = 600;
    private int WORLD_WIDTH = 800;


    //Background
    private Background background;
    private ArrayList<Background> backgrounds;
    private float laneWidthsRiver = 7.0f;
    private float laneWidthsScreen = 7.5f;
    private float minAspectRatio = 1.8f;
    private int backgroundTextureSize = 64;
    private float riverCountX = 0;
    private float riverCountY = 0;

    //game objects
    private NewBoat playerBoat;
    private Duck enemyDuck;
    private LinkedList<NewBoat> opponentBoats;
    private LinkedList<Enemy> enemyObjects;

    //HUD
    private HUD hud;

    //River environment
    private EnvironmentalConditions environmentalConditions;

    GameScreen(){
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images6.atlas");

        //set up textures
        playerBoatTexture = textureAtlas.findRegion("player");
        playerBoatTexture.flip(false,true);
        enemyDuckTexture = textureAtlas.findRegion("duck_in_a_top_hat");
        backgroundTexture = textureAtlas.findRegion("sea3");
        landTexture = textureAtlas.findRegion("land1");
        fullHUDTexture = textureAtlas.findRegion("full_paddle");
        halfHUDTexture = textureAtlas.findRegion("half_paddle");

        //Environmental conditions
        environmentalConditions = new EnvironmentalConditions();
        environmentalConditions.getWind().setForce(new Force( -2.0f, -6.0f));
        environmentalConditions.getWind().setGust(new Gust(new Force(-2.0f, -3.0f), 5.0f, 0.08f));
        environmentalConditions.getCurrent().setForce(new Force(-0.8f, -8.0f));

        //Set lane size
        backgroundTextureSize = 106;

        //set up game objects
        playerBoat = new NewBoat();
        playerBoat.getSpriteObj().setTimedTextures(new TimedTexture[] {new TimedTexture(playerBoatTexture)});
        opponentBoats = new LinkedList<NewBoat>();
        for (int i = 0; i < 4; i++) {
            opponentBoats.add(new NewBoat());
            opponentBoats.get(i).getSpriteObj().setTimedTextures(new TimedTexture[] {new TimedTexture(playerBoatTexture)});
            if (i < 2) {
                opponentBoats.get(i).getSpriteObj().getSprite().setX((i - 2) * backgroundTextureSize);
            } else {
                opponentBoats.get(i).getSpriteObj().getSprite().setX((i - 1) * backgroundTextureSize);
            }
            opponentBoats.get(i).getRowers().setMaxForce(new Random().nextFloat() * 300);
            opponentBoats.get(i).setThrottle(1.0f);
        }

        //enemyDuck = new Duck(2,3,WORLD_WIDTH/2,WORLD_HEIGHT*3/4, enemyDuckTexture,0);

        //hud
        hud = new HUD(playerBoat, fullHUDTexture, halfHUDTexture);

        batch = new SpriteBatch();
    }

    @Override
    public void render(float avgDelta) {
        float delta = Gdx.graphics.getRawDeltaTime();

        Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        detectInput();

        //draw background
        batch.disableBlending();
        renderBackground();
        batch.enableBlending();

        //draw heads up display
        hud.draw(batch, viewport);

        //draw enemies
        //renderEnemies();

        //draw player
        Sprite playerBoatSprite = playerBoat.getSpriteObj().getSprite();
        batch.draw(playerBoatTexture, (viewport.getWorldWidth() - playerBoatTexture.getRegionWidth()) / 2, (viewport.getWorldHeight() - playerBoatTexture.getRegionHeight()) / 2, playerBoatSprite.getOriginX(), playerBoatSprite.getOriginY(), playerBoatSprite.getWidth(), playerBoatSprite.getHeight(), playerBoatSprite.getScaleX(), playerBoatSprite.getScaleY(), -playerBoatSprite.getRotation());

        //draw opponents
        for (NewBoat opponentBoat :opponentBoats) {
            Sprite opponentBoatSprite = opponentBoat.getSpriteObj().getSprite();
            batch.draw(playerBoatTexture, ((viewport.getWorldWidth() - playerBoatTexture.getRegionWidth()) / 2) + (opponentBoatSprite.getX() - playerBoatSprite.getX()), ((viewport.getWorldHeight() - playerBoatTexture.getRegionHeight()) / 2) + (opponentBoatSprite.getY() - playerBoatSprite.getY()), opponentBoatSprite.getOriginX(), opponentBoatSprite.getOriginY(), opponentBoatSprite.getWidth(), opponentBoatSprite.getHeight(), opponentBoatSprite.getScaleX(), opponentBoatSprite.getScaleY(), -opponentBoatSprite.getRotation());
        }

        System.out.print(backgroundTextureSize);
        System.out.print(" ");
        System.out.println(playerBoatSprite.getX());

        //detect collisions
        //detectCollisions();

        //updates
        playerBoat.update(delta, environmentalConditions);
        for (NewBoat opponentBoat :opponentBoats) {
            opponentBoat.update(delta, environmentalConditions);
        }

        batch.end();
    }


    private void initialiseBackground(){

    }

    private void renderBackground(){

        //Draw river
        if ((float) viewport.getWorldWidth() / viewport.getWorldHeight() < minAspectRatio) {
            backgroundTextureSize = (int) (viewport.getWorldWidth() / laneWidthsScreen);
        } else {
            backgroundTextureSize = (int) (viewport.getWorldHeight() / (laneWidthsScreen / minAspectRatio));
        }

        riverCountX = (riverCountX + environmentalConditions.getCurrent().getForce().getX() / 10) % backgroundTextureSize;
        riverCountY = (riverCountY + environmentalConditions.getCurrent().getForce().getY() / 10) % backgroundTextureSize;

        for (int i = -1; i < 8; i++) {
            for (int j = -1; j < (viewport.getWorldHeight() / backgroundTextureSize) + 1; j++) {
                batch.draw(backgroundTexture, (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) + riverCountX - playerBoat.getSpriteObj().getSprite().getX(), j * backgroundTextureSize + (riverCountY - playerBoat.getSpriteObj().getSprite().getY()) % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
            }
        }

        //Draw land
        for (int i = 1; i < (int) ((viewport.getWorldWidth() - laneWidthsRiver) / 2) + 2; i++) {
            for (int j = -1; j < (viewport.getWorldHeight() / backgroundTextureSize) + 1; j++) {
                batch.draw(landTexture, (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 - i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX(), j * backgroundTextureSize - playerBoat.getSpriteObj().getSprite().getY() % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
            }
        }

        for (int i = 0; i < (int) ((viewport.getWorldWidth() - laneWidthsRiver) / 2) + 1; i++) {
            for (int j = -1; j < (viewport.getWorldHeight() / backgroundTextureSize) + 1; j++) {
                batch.draw(landTexture, (int) ((viewport.getWorldWidth() + laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX(), j * backgroundTextureSize - playerBoat.getSpriteObj().getSprite().getY() % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
            }
        }
    }

    private void renderEnemies(){
        Iterator iterator = enemyObjects.iterator();
        while(iterator.hasNext()){
            Enemy e = (Enemy) iterator.next();
            /*if(e.intersects(playerBoat.boundingBox)){
                iterator.remove();
                playerBoat.damage(e);
                System.out.println("damage");
            }else {
                e.draw(batch);
            }*/
        }
    }

    private void detectCollisions(){
        //for each boat check if intersects enemy object

    }

    private void detectInput(){
        //keyboard input
        //Background movement
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerBoat.setThrottle(1.0f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerBoat.setThrottle(-1.0f);
        } else {
            playerBoat.setThrottle(0.0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBoat.setSteering(-1.0f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBoat.setSteering(1.0f);
        } else {
            playerBoat.setSteering(0.0f);
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
