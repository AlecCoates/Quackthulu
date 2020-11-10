package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
<<<<<<< Updated upstream
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
=======
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
>>>>>>> Stashed changes
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.quackthulu.boatrace2020.basics.Force;

public class GameScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
<<<<<<< Updated upstream

    private TextureRegion background, playerBoatTexture, enemyDuckTexture;
=======
    private TextureRegion backgroundTexture, landTexture, playerBoatTexture, enemyDuckTexture, fullHUDTexture, halfHUDTexture;
>>>>>>> Stashed changes

    //timing
    private int backgroundOffset;

    //world parameters
<<<<<<< Updated upstream
    private final int WORLD_HEIGHT = 72;
    private final int WORLD_WIDTH = 128;
=======
    private int WORLD_HEIGHT = 600;
    private int WORLD_WIDTH = 800;


    //Background
    private Background background;
    private ArrayList<Background> backgrounds;
    private int riverCountX = 0;
    private int riverCountY = 0;
>>>>>>> Stashed changes

    //game objects
    private Boat playerBoat;
    private Duck enemyDuck;
    //private LinkedList<Enemy> enemyObjects;

    //River environment
    private EnvironmentalConditions environmentalConditions;

    GameScreen(){
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("images3.atlas");

        playerBoatTexture = textureAtlas.findRegion("player");
        enemyDuckTexture = textureAtlas.findRegion("duck_in_a_top_hat");
<<<<<<< Updated upstream
        background = textureAtlas.findRegion("sea2");
        backgroundOffset = 0;
=======
        backgroundTexture = textureAtlas.findRegion("sea");
        landTexture = textureAtlas.findRegion("land");
        fullHUDTexture = textureAtlas.findRegion("full_paddle");
        halfHUDTexture = textureAtlas.findRegion("half_paddle");

        //
        environmentalConditions = new EnvironmentalConditions();
        environmentalConditions.getWind().setForce(new Force( -2.0f, -6.0f));
        environmentalConditions.getWind().setGust(new Gust(new Force(-2.0f, -3.0f), 5.0f, 0.08f));
        environmentalConditions.getCurrent().setForce(new Force(-0.8f, -8.0f));

>>>>>>> Stashed changes

        //set up game objects
        playerBoat = new Boat(2,2,10,
                10, playerBoatTexture, 10, 10, true);
        enemyDuck = new Duck(2,3,WORLD_WIDTH/2,WORLD_HEIGHT*3/4,
                enemyDuckTexture,0);

        batch = new SpriteBatch();
    }

    @Override
    public void render(float avgDelta) {
        float delta = Gdx.graphics.getRawDeltaTime();

<<<<<<< Updated upstream

=======
        Gdx.gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
>>>>>>> Stashed changes
        batch.begin();

        //detectInput(delta);

        //draw background
<<<<<<< Updated upstream
        batch.draw(background,0,0,WORLD_WIDTH,WORLD_HEIGHT);

        //draw player
        playerBoat.draw(batch);
        enemyDuck.draw(batch);
=======
        batch.disableBlending();
        renderBackground();
        batch.enableBlending();

        //draw heads up display
        //hud.draw(batch,playerBoat.boundingBox.x,playerBoat.boundingBox.y);

        //draw enemies
        //renderEnemies();

        //draw player
        //playerBoat.draw(batch);
>>>>>>> Stashed changes

        //detect collisions
        //detectCollisions();

        //detect inputs
        //detectInput(delta);

        //updates
        playerBoat.update(delta);

        batch.end();
    }

<<<<<<< Updated upstream
=======
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

        riverCountX = (int) (riverCountX + environmentalConditions.getCurrent().getForce().getX()) % riverTextureWidth;
        riverCountY = (int) (riverCountY + environmentalConditions.getCurrent().getForce().getY()) % riverTextureHeight;

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
        //batch.draw(backgroundTexture, 200, 5 * riverTextureHeight , riverTextureWidth, riverTextureHeight);
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

>>>>>>> Stashed changes
    private void detectCollisions(){
        //for each boat check if intersects enemy object

    }

    private void detectInput(float delta){
        //keyboard input
<<<<<<< Updated upstream

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
=======
        //Background movement
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            for (Enemy e : enemyObjects) {
                e.translate(playerBoat.movementSpeed * delta, 0f);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            for (Enemy e : enemyObjects) {
                e.translate(-playerBoat.movementSpeed * delta, 0f);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            for (Enemy e : enemyObjects) {
                e.translate(0f, playerBoat.movementSpeed * delta);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            for (Enemy e : enemyObjects) {
                e.translate(0f, -playerBoat.movementSpeed * delta);
            }
>>>>>>> Stashed changes
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
