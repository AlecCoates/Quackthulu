package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.quackthulu.boatrace2020.basics.Force;
import com.quackthulu.boatrace2020.basics.TimedTexture;

import java.awt.*;
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
    private float timer = 0;

    //world parameters
    private int WORLD_HEIGHT = 600;
    private int WORLD_WIDTH = 800;


    //Background
    private Background background;
    private ArrayList<Background> backgrounds;
    private int noOfBoats = 5;
    private float laneWidthsRiver = noOfBoats + 1.0f;
    private float laneWidthsScreen = 7.5f;
    private float minAspectRatio = 1.8f;
    private float boatWidthsLane = 4.0f;
    private float raceLength = 10.0f;
    private int backgroundTextureSize = 64;
    private float riverCountX = 0;
    private float riverCountY = 0;

    //game objects
    private LinkedList<SpriteObj> spriteObjs;
    private NewBoat playerBoat;
    private Duck enemyDuck;
    private LinkedList<NewBoat> opponentBoats;
    private LinkedList<SpriteObj> enemyObjects;

    //HUD
    private HUD hud;
    private float test = 0.0f;

    //River environment
    private EnvironmentalConditions environmentalConditions;

    GameScreen(){
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images6.atlas");

        playerBoatTexture = textureAtlas.findRegion("player");
        enemyDuckTexture = textureAtlas.findRegion("duck_in_a_top_hat");
        backgroundTexture = textureAtlas.findRegion("sea3");
        landTexture = textureAtlas.findRegion("land1");
        fullHUDTexture = textureAtlas.findRegion("full_paddle");
        halfHUDTexture = textureAtlas.findRegion("half_paddle");

        //
        environmentalConditions = new EnvironmentalConditions();
        environmentalConditions.getWind().setForce(new Force( -0.05f, -0.1f));
        environmentalConditions.getWind().setGust(new Gust(new Force(-0.05f, -0.1f), 5.0f, 0.08f));
        environmentalConditions.getCurrent().setForce(new Force(-0.05f, -0.3f));

        //set up game objects
        spriteObjs = new LinkedList<>();
        playerBoat = new NewBoat();
        playerBoat.getSpriteObj().gameScreen = this;
        playerBoat.getDynamicObj().collisionCallback = playerBoat;
        playerBoat.getSpriteObj().dynamicObj = playerBoat.getDynamicObj();
        playerBoat.getSpriteObj().setTimedTextures(new TimedTexture[] {new TimedTexture(playerBoatTexture)});
        playerBoat.lane = new float[] {-0.5f, 0.5f};
        spriteObjs.add(playerBoat.getSpriteObj());
        opponentBoats = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            opponentBoats.add(new NewBoat());
            opponentBoats.get(i).getSpriteObj().gameScreen = this;
            opponentBoats.get(i).getDynamicObj().collisionCallback = opponentBoats.get(i);
            opponentBoats.get(i).getSpriteObj().dynamicObj = opponentBoats.get(i).getDynamicObj();
            opponentBoats.get(i).getSpriteObj().setTimedTextures(new TimedTexture[] {new TimedTexture(playerBoatTexture)});
            opponentBoats.get(i).ai = new AI();
            opponentBoats.get(i).ai.boat = opponentBoats.get(i);
            spriteObjs.add(opponentBoats.get(i).getSpriteObj());
            if (i < 2) {
                opponentBoats.get(i).getSpriteObj().getSprite().setX(i - 2);
                playerBoat.lane = new float[] {-0.5f + (i - 2), 0.5f + (i - 2)};
            } else {
                opponentBoats.get(i).getSpriteObj().getSprite().setX(i - 1);
                playerBoat.lane = new float[] {-0.5f + (i - 1), 0.5f + (i - 1)};
            }
            opponentBoats.get(i).getRowers().setMaxForce(new Random().nextFloat() * 2.5f);
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
        timer += delta;

        Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        detectInput();

        //draw background
        batch.disableBlending();
        renderBackground();
        batch.enableBlending();

        //check race finish
        if (playerBoat.getSpriteObj().getSprite().getY() > raceLength * backgroundTextureSize && !playerBoat.finishedRace()) playerBoat.setFinishingTime(timer);
        for (NewBoat opponentBoat : opponentBoats) {
            if (opponentBoat.getSpriteObj().getSprite().getY() > raceLength * backgroundTextureSize && !opponentBoat.finishedRace()) opponentBoat.setFinishingTime(timer);
        }

        //draw heads up display
        hud.draw(batch, viewport);

        //draw enemies
        //renderEnemies();

        //draw player
        Sprite playerBoatSprite = playerBoat.getSpriteObj().getSprite();
        playerBoatSprite.setScale(backgroundTextureSize / playerBoatSprite.getWidth() / boatWidthsLane, backgroundTextureSize / playerBoatSprite.getWidth() / boatWidthsLane);
        batch.draw(playerBoatTexture, (viewport.getWorldWidth() - playerBoatTexture.getRegionWidth()) / 2, (viewport.getWorldHeight() - playerBoatTexture.getRegionHeight()) / 2, playerBoat.getSpriteObj().getSprite().getOriginX(), playerBoatSprite.getOriginY(), playerBoatSprite.getWidth(), playerBoatSprite.getHeight(), playerBoatSprite.getScaleX(), playerBoatSprite.getScaleY(), playerBoatSprite.getRotation());

        //draw opponents
        for (NewBoat opponentBoat : opponentBoats) {
            Sprite opponentBoatSprite = opponentBoat.getSpriteObj().getSprite();
            opponentBoatSprite.setScale(backgroundTextureSize / opponentBoatSprite.getWidth() / boatWidthsLane, backgroundTextureSize / opponentBoatSprite.getWidth() / boatWidthsLane);
            batch.draw(playerBoatTexture, ((viewport.getWorldWidth() - playerBoatTexture.getRegionWidth()) / 2) + (opponentBoatSprite.getX() - playerBoatSprite.getX()) * backgroundTextureSize, ((viewport.getWorldHeight() - playerBoatTexture.getRegionHeight()) / 2) + (opponentBoatSprite.getY() - playerBoatSprite.getY()) * backgroundTextureSize, opponentBoatSprite.getOriginX(), opponentBoatSprite.getOriginY(), opponentBoatSprite.getWidth(), opponentBoatSprite.getHeight(), opponentBoatSprite.getScaleX(), opponentBoatSprite.getScaleY(), opponentBoatSprite.getRotation());
        }

        //detect collisions
        //detectCollisions();

        //updates
        playerBoat.update(delta, environmentalConditions, spriteObjs);
        for (NewBoat opponentBoat : opponentBoats) {
            opponentBoat.update(delta, environmentalConditions, spriteObjs);
        }

        batch.end();
    }

    private void renderBackground(){
        //Draw river
        if (viewport.getWorldWidth() / viewport.getWorldHeight() < minAspectRatio) {
            backgroundTextureSize = (int) (viewport.getWorldWidth() / laneWidthsScreen);
        } else {
            backgroundTextureSize = (int) (viewport.getWorldHeight() / (laneWidthsScreen / minAspectRatio));
        }

        riverCountX = (riverCountX + environmentalConditions.getCurrent().getForce().getX() * 2.35f) % backgroundTextureSize;
        riverCountY = (riverCountY + environmentalConditions.getCurrent().getForce().getY() * 2.35f) % backgroundTextureSize;

        for (int i = -1; i < (int) Math.ceil(laneWidthsRiver + 1); i++) {
            for (int j = -1; j < (viewport.getWorldHeight() / backgroundTextureSize) + 1; j++) {
                batch.draw(backgroundTexture, (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) + riverCountX - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, j * backgroundTextureSize + (riverCountY - playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize) % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
            }
        }

        //Draw Start & finish Line
        for (int i = 0; i < (int) Math.ceil(laneWidthsRiver); i++) {
            batch.draw(landTexture, (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, (viewport.getWorldHeight() / 2) - playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
            batch.draw(landTexture, (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, (viewport.getWorldHeight() / 2) + raceLength * backgroundTextureSize - playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
        }

        //Draw land
        for (int i = 0; i < (int) (viewport.getWorldWidth() / backgroundTextureSize / 2) + 2; i++) {
            for (int j = -1; j < (viewport.getWorldHeight() / backgroundTextureSize) + 1; j++) {
                    batch.draw(landTexture, (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 - (i + 1) * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, j * backgroundTextureSize - (playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize) % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
                    batch.draw(landTexture, (int) ((viewport.getWorldWidth() + laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, j * backgroundTextureSize - (playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize) % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
            }
        }
    }

    private void renderEnemies(){
        //for (Enemy e : enemyObjects) {
            /*if(e.intersects(playerBoat.boundingBox)){
                iterator.remove();
                playerBoat.damage(e);
                System.out.println("damage");
            }else {
                e.draw(batch);
            }*/
        //}
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
            playerBoat.setSteering(1.0f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBoat.setSteering(-1.0f);
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

    }

    public int getBackgroundTextureSize() {
        return backgroundTextureSize;
    }

    public float getTimer() {
        return timer;
    }

    public LinkedList<SpriteObj> getEnemyObjects() {
        return enemyObjects;
    }
}
