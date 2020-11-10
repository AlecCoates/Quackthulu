package com.quackthulu.boatrace2020;

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
    private int backgroundOffset;

    //world parameters
    private int WORLD_HEIGHT = 600;
    private int WORLD_WIDTH = 800;


    //Background
    private Background background;
    private ArrayList<Background> backgrounds;
    private float riverCountX = 0;
    private float riverCountY = 0;

    //game objects
    private NewBoat playerBoat;
    private Duck enemyDuck;
    //private LinkedList<Enemy> enemyObjects;
    private LinkedList<Enemy> enemyObjects;

    //HUD
    private HUD hud;

    //River environment
    private EnvironmentalConditions environmentalConditions;

    GameScreen(){
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images5.atlas");

        playerBoatTexture = textureAtlas.findRegion("player");
        enemyDuckTexture = textureAtlas.findRegion("duck_in_a_top_hat");
        backgroundTexture = textureAtlas.findRegion("sea");
        landTexture = textureAtlas.findRegion("land");
        fullHUDTexture = textureAtlas.findRegion("full_paddle");
        halfHUDTexture = textureAtlas.findRegion("half_paddle");

        //
        environmentalConditions = new EnvironmentalConditions();
        environmentalConditions.getWind().setForce(new Force( -2.0f, -6.0f));
        environmentalConditions.getWind().setGust(new Gust(new Force(-2.0f, -3.0f), 5.0f, 0.08f));
        environmentalConditions.getCurrent().setForce(new Force(-0.8f, -8.0f));

        //set up game objects
        playerBoat = new NewBoat();
        playerBoat.getSpriteObj().setTimedTextures(new TimedTexture[] {new TimedTexture(playerBoatTexture)});
        System.out.println(playerBoat.getSpriteObj().getSprite().getHeight());
        //enemyDuck = new Duck(2,3,WORLD_WIDTH/2,WORLD_HEIGHT*3/4, enemyDuckTexture,0);

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
        //hud.draw(batch,playerBoat.boundingBox.x,playerBoat.boundingBox.y);

        //draw enemies
        //renderEnemies();

        //draw player
        Sprite boatSprite = playerBoat.getSpriteObj().getSprite();
        batch.draw(playerBoatTexture, boatSprite.getX(), boatSprite.getY(), boatSprite.getOriginX(), boatSprite.getOriginY(), boatSprite.getWidth(), boatSprite.getHeight(), boatSprite.getScaleX(), boatSprite.getScaleY(), -boatSprite.getRotation());
        //playerBoat.getSpriteObj().getSprite().draw(batch);
        System.out.print(playerBoat.getDynamicObj().getVelocity().getX());
        System.out.print(", ");
        System.out.print(playerBoat.getDynamicObj().getVelocity().getY());
        System.out.print(", ");
        System.out.print(playerBoat.getSpriteObj().getSprite().getX());
        System.out.print(", ");
        System.out.println(playerBoat.getSpriteObj().getSprite().getY());
        camera.position.y += 50;

        //detect collisions
        //detectCollisions();

        //updates
        playerBoat.update(delta, environmentalConditions);

        batch.end();
    }


    private void initialiseBackground(){

    }

    private void renderBackground(){
        //Draw river
        int riverTextureWidth;
        int riverTextureHeight;
        if ((float) viewport.getWorldWidth() / viewport.getWorldHeight() < 1.8) {
            float floatWidth = (float) viewport.getWorldWidth() / 6;
            riverTextureWidth = (int) floatWidth;
            riverTextureHeight = (int) (floatWidth * backgroundTexture.getRegionHeight() / backgroundTexture.getRegionWidth());
        } else {
            float floatHeight = (float) (viewport.getWorldHeight() / (6 / 1.8));
            riverTextureHeight = (int) floatHeight;
            riverTextureWidth = (int) (floatHeight * backgroundTexture.getRegionWidth() / backgroundTexture.getRegionHeight());
        }

        riverCountX = (riverCountX + environmentalConditions.getCurrent().getForce().getX() / 10) % riverTextureWidth;
        riverCountY = (riverCountY + environmentalConditions.getCurrent().getForce().getY() / 10) % riverTextureHeight;

        for (int i = -1; i < 8; i++) {
            for (int j = -1; j < (viewport.getWorldHeight() / riverTextureHeight) + 1; j++) {
                batch.draw(backgroundTexture, (int) ((viewport.getWorldWidth() - 7 * riverTextureWidth) / 2 + i * riverTextureWidth) + riverCountX, j * riverTextureHeight + riverCountY, riverTextureWidth, riverTextureHeight);
            }
        }

        //Draw land
        int landTextureWidth;
        int landTextureHeight;
        if ((float) viewport.getWorldWidth() / viewport.getWorldHeight() < 1.8) {
            float floatWidth = (float) viewport.getWorldWidth() / 6;
            landTextureWidth = (int) floatWidth;
            landTextureHeight = (int) (floatWidth * landTexture.getRegionHeight() / landTexture.getRegionWidth());
        } else {
            float floatHeight = (float) (viewport.getWorldHeight() / (6 / 1.8));
            landTextureHeight = (int) floatHeight;
            landTextureWidth = (int) (floatHeight * landTexture.getRegionWidth() / landTexture.getRegionHeight());
        }

        for (int i = 1; i < (int) ((viewport.getWorldWidth() - 7 * riverTextureWidth) / 2 / landTextureWidth) + 2; i++) {
            for (int j = 0; j < (viewport.getWorldHeight() / landTextureHeight); j++) {
                batch.draw(landTexture, (int) ((viewport.getWorldWidth() - 7 * riverTextureWidth) / 2 - i * landTextureWidth), j * riverTextureHeight, riverTextureWidth, riverTextureHeight);
            }
        }

        for (int i = 0; i < (int) ((viewport.getWorldWidth() - 7 * riverTextureWidth) / 2 / landTextureWidth) + 1; i++) {
            for (int j = 0; j < (viewport.getWorldHeight() / landTextureHeight); j++) {
                batch.draw(landTexture, (int) ((viewport.getWorldWidth() + 7 * riverTextureWidth) / 2 + i * landTextureWidth), j * riverTextureHeight, riverTextureWidth, riverTextureHeight);
            }
        }
    }

    private void renderEnemies(){
        Iterator iterator = enemyObjects.iterator();
        while(iterator.hasNext()){
            Enemy e = (Enemy) iterator.next();
            /*if(e.intersects(playerBoat.boundingBox)){
                iterator.remove();
                hud.damage(e);
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

    }
}
