package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.xml.transform.Templates;

public class GameScreen implements Screen {

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;

    private TextureRegion background, playerBoatTexture;

    //timing
    private int backgroundOffset;

    //world parameters
    private final int WORLD_HEIGHT = 72;
    private final int WORLD_WIDTH = 128;

    //game objects
    private Boat playerBoat;

    GameScreen(){
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up texture atlas
        textureAtlas = new TextureAtlas("imgs.atlas");

        playerBoatTexture = textureAtlas.findRegion("player");
        background = textureAtlas.findRegion("sea");
        backgroundOffset = 0;

        //set up game objects
        playerBoat = new Boat(2,3,WORLD_WIDTH/2,
                WORLD_HEIGHT/4, playerBoatTexture, 10, 10, true);

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        batch.begin();

        //draw background
        batch.draw(background,0,0,WORLD_WIDTH,WORLD_HEIGHT);

        //draw player
        playerBoat.draw(batch);

        batch.end();
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
