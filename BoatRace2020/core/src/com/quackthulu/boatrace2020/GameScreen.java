package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.quackthulu.boatrace2020.basics.Force;
import com.quackthulu.boatrace2020.basics.TimedTexture;

import java.util.*;
import java.util.LinkedList;

public class GameScreen implements Screen {
    //screen
    public BoatRace parent;
    private OrthographicCamera camera;
    public ExtendViewport viewport;

    //graphics
    private SpriteBatch batch;
    private Assets assets;
    private SpriteObj backgroundSprite;

    //timing
    private float timer = 0;

    //world parameters
    private int WORLD_HEIGHT = 600;
    private int WORLD_WIDTH = 800;

    //Background
    private int noOfBoats = 5;
    private float laneWidthsRiver = noOfBoats + 1.0f;
    private float laneWidthsScreen = 6.5f;
    private float minAspectRatio = 1.8f;
    private float boatWidthsLane = 4.0f;
    private float raceLength = 40.0f;
    private int backgroundTextureSize = 64;
    private float riverCountX = 0;
    private float riverCountY = 0;

    //game objects
    private Boat playerBoat;
    private LinkedList<Boat> opponentBoats;
    private LinkedList<Boat> drawOpponentBoats;
    private LinkedList<LinkedList<Float>> opponentBoatsRotations;
    private LinkedList<SpriteObj> spriteObjs;
    private LinkedList<Enemy> enemyObjects;

    //HUD
    private HUD hud;
    private float test = 0.0f;

    //River environment
    private EnvironmentalConditions environmentalConditions;

    GameScreen(BoatRace boatRace) {
        parent = boatRace;
        camera = new OrthographicCamera(); //no 3d perspective
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //set up assets
        assets = new Assets();
        assets.load();
        assets.manager.finishLoading();

        //set up textures
        backgroundSprite = new SpriteObj(new TimedTexture[]{new TimedTexture(assets.getTexture(Assets.backgroundTexture), 0.3f), new TimedTexture(assets.getTexture(Assets.backgroundTexture2), 0.3f)});
        backgroundSprite.setIsCollider(false);

        //Declare environmental conditions
        environmentalConditions = new EnvironmentalConditions();
        environmentalConditions.getWind().setForce(new Force(-0.05f, -0.07f));
        environmentalConditions.getWind().setGust(new Gust(new Force(-0.05f, -0.07f), 5.0f, 0.08f));
        environmentalConditions.getCurrent().setForce(new Force(-0.08f, -0.18f));

        //Set lane size
        backgroundTextureSize = 106;

        //set up game objects
        spriteObjs = new LinkedList<>();
        playerBoat = new Boat(BoatsStats.getBoatStats(parent.playerBoatNumber), assets);
        playerBoat.getSpriteObj().gameScreen = this;
        playerBoat.lane = new float[] {-0.5f, 0.5f};
        spriteObjs.add(playerBoat.getSpriteObj());
        opponentBoats = new LinkedList<>();
        drawOpponentBoats = new LinkedList<>();
        opponentBoatsRotations = new LinkedList<>();

        if (parent.level == 1) {
            parent.boats.clear();
            for (int i = 0; i < noOfBoats - 1; i++) {
                parent.boats.add(new Random().nextInt(BoatsStats.numOfBoats()));
            }
        }
        for (int i = 0; i < parent.boats.size(); i++) {
            opponentBoats.add(new Boat(BoatsStats.getBoatStats(parent.boats.get(i)), assets));
            opponentBoats.get(i).getSpriteObj().gameScreen = this;
            opponentBoats.get(i).ai = new AI();
            opponentBoats.get(i).ai.boat = opponentBoats.get(i);
            if (i < 2) {
                opponentBoats.get(i).getSpriteObj().getSprite().setX(i - 2);
                opponentBoats.get(i).lane = new float[] {-0.5f + (i - 2), 0.5f + (i - 2)};
            } else {
                opponentBoats.get(i).getSpriteObj().getSprite().setX(i - 1);
                opponentBoats.get(i).lane = new float[] {-0.5f + (i - 1), 0.5f + (i - 1)};
            }
            drawOpponentBoats.add(opponentBoats.get(i));
            spriteObjs.add(opponentBoats.get(i).getSpriteObj());
            opponentBoatsRotations.add(new LinkedList<Float>());
        }

        //Set up obstacle objects
        enemyObjects = new LinkedList<>();
        for (int i = 0; i < 0.2f * noOfBoats * raceLength; i++) {
            enemyObjects.add(new Enemy());
            enemyObjects.get(i).getSpriteObj().setTimedTextures(new TimedTexture[]{new TimedTexture(assets.getTexture(Assets.enemyDuckTexture))});
            enemyObjects.get(i).getSpriteObj().gameScreen = this;
            enemyObjects.get(i).getSpriteObj().setDamage(1);
            enemyObjects.get(i).getSpriteObj().setIsCollider(false);
            Random rand = new Random();
            Sprite enemyObjectSprite = enemyObjects.get(i).getSpriteObj().getSprite();
            enemyObjectSprite.setX((rand.nextFloat() - 0.5f) * noOfBoats);
            enemyObjectSprite.setY(rand.nextFloat() * (raceLength - 2) + 2);
            spriteObjs.add(enemyObjects.get(i).getSpriteObj());
        }

        //hud
        hud = new HUD(playerBoat, assets.getTexture(Assets.fullHUDTexture), assets.getTexture(Assets.halfHUDTexture));

        batch = new SpriteBatch();
    }

    private void safeDraw(TextureRegion region, float x, float y, float originX, float originY) {
        if (batch != null && assets.loaded) {
            batch.draw(region, x, y, originX, originY);
        }
    }

    private void safeDraw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        if (batch != null && assets.loaded) {
            batch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        }
    }

    @Override
    public void render(float avgDelta) {
        if (assets.loaded) {
            try {
                float delta = Gdx.graphics.getRawDeltaTime();
                timer += delta;

                Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();

                detectInput();

                //Physics updates
                playerBoat.update(delta, environmentalConditions, spriteObjs);
                for (Boat opponentBoat : opponentBoats) {
                    opponentBoat.update(delta, environmentalConditions, spriteObjs);
                }
                for (Enemy enemy : enemyObjects) {
                    enemy.update(delta, environmentalConditions);
                }

                //Keeps a list of recent AI boat rotations so we can smooth them out later
                for (int i = 0; i < drawOpponentBoats.size(); i++) {
                    if (opponentBoatsRotations.get(i).size() > 30) {
                        opponentBoatsRotations.get(i).remove();
                    }
                    opponentBoatsRotations.get(i).add(drawOpponentBoats.get(i).getSpriteObj().getSprite().getRotation());
                }

                //Check for deaths
                if (playerBoat.getHealth() <= 0) {
                    parent.changeScreen(BoatRace.LOSE);
                }
                for (Boat opponentBoat : opponentBoats) {
                    if (opponentBoat.getHealth() <= 0) {
                        spriteObjs.remove(opponentBoat.getSpriteObj());
                        drawOpponentBoats.remove(opponentBoat);
                    }
                }


                //Set any finished times
                if (!playerBoat.finishedRace() && playerBoat.getSpriteObj().getSprite().getY() > raceLength) {
                    playerBoat.setFinishingTime(timer);
                }
                for (Boat opponentBoat : opponentBoats) {
                    if (!opponentBoat.finishedRace() && opponentBoat.getSpriteObj().getSprite().getY() > raceLength) {
                        opponentBoat.setFinishingTime(timer);
                    }
                }

                //check if boats have finished race
                float slowestTime = 0;
                float quickestTime = 0;
                boolean anyDead = drawOpponentBoats.size() < opponentBoats.size();
                boolean allFinished = true;
                if (playerBoat.finishedRace()) {
                    slowestTime = Math.max(slowestTime, playerBoat.getFinishingTime());
                    quickestTime = Math.min(quickestTime, playerBoat.getFinishingTime());
                } else {
                    allFinished = false;
                }
                for (Boat opponentBoat : opponentBoats) {
                    if (opponentBoat.finishedRace() || opponentBoat.getHealth() <= 0) {
                        if (opponentBoat.getHealth() > 0) {
                            slowestTime = Math.max(slowestTime, opponentBoat.getFinishingTime());
                            quickestTime = Math.min(quickestTime, opponentBoat.getFinishingTime());
                        }
                    } else {
                        allFinished = false;
                    }
                }
                if (allFinished){
                    List<Integer> newBoats = new LinkedList<>();
                    for (int i = 0; i < opponentBoats.size(); i++) {
                        if (opponentBoats.get(i).getHealth() > 0 && (opponentBoats.get(i).getFinishingTime() != slowestTime || anyDead || parent.level == 1)){
                            newBoats.add(parent.boats.get(i));
                        }
                    }
                    parent.boats = newBoats;
                    if (playerBoat.getFinishingTime() != slowestTime || anyDead || parent.level == 1 || opponentBoats.size() == 0){
                        parent.changeScreen(BoatRace.WIN);
                    } else {
                        parent.changeScreen(BoatRace.LOSE);
                    }
                }

                //draw background
                renderBackground(delta);

                //get playerSprite
                Sprite playerBoatSprite = playerBoat.getSpriteObj().getSprite();


                //draw enemies
                for (Enemy enemy : enemyObjects) {
                    Sprite enemySprite = enemy.getSpriteObj().getSprite();
                    enemySprite.setScale(backgroundTextureSize / enemySprite.getWidth() / 4, backgroundTextureSize / enemySprite.getWidth() / 4);
                    safeDraw(assets.getTexture(Assets.enemyDuckTexture), ((viewport.getWorldWidth() - assets.getBoatTexture(parent.playerBoatNumber).getRegionWidth()) / 2) + (enemySprite.getX() - playerBoatSprite.getX()) * backgroundTextureSize, ((viewport.getWorldHeight() - assets.getBoatTexture(parent.playerBoatNumber).getRegionHeight()) / 2) + (enemySprite.getY() - playerBoatSprite.getY()) * backgroundTextureSize, enemySprite.getOriginX(), enemySprite.getOriginY(), enemySprite.getWidth(), enemySprite.getHeight(), enemySprite.getScaleX(), enemySprite.getScaleY(), enemySprite.getRotation());
                }

                //draw player
                playerBoatSprite.setScale((backgroundTextureSize / boatWidthsLane) / playerBoatSprite.getWidth(), backgroundTextureSize / playerBoatSprite.getWidth() / boatWidthsLane);
                safeDraw(playerBoat.getSpriteObj().getTexture(), (viewport.getWorldWidth() - assets.getBoatTexture(parent.playerBoatNumber).getRegionWidth()) / 2, (viewport.getWorldHeight() - assets.getBoatTexture(parent.playerBoatNumber).getRegionHeight()) / 2, playerBoat.getSpriteObj().getSprite().getOriginX(), playerBoatSprite.getOriginY(), playerBoatSprite.getWidth(), playerBoatSprite.getHeight(), playerBoatSprite.getScaleX(), playerBoatSprite.getScaleY(), playerBoatSprite.getRotation());

                //draw opponents
                for (int i = 0; i < drawOpponentBoats.size(); i++) {
                    Boat opponentBoat = drawOpponentBoats.get(i);
                    Float avgRotation = 0.0f;
                    for (Float opponentBoatRotation : opponentBoatsRotations.get(i)) avgRotation += opponentBoatRotation;
                    avgRotation = avgRotation / (float) opponentBoatsRotations.get(i).size();
                    Sprite opponentBoatSprite = opponentBoat.getSpriteObj().getSprite();
                    opponentBoatSprite.setScale(backgroundTextureSize / opponentBoatSprite.getWidth() / boatWidthsLane, backgroundTextureSize / opponentBoatSprite.getWidth() / boatWidthsLane);
                    safeDraw(opponentBoat.getSpriteObj().getTexture(), ((viewport.getWorldWidth() - assets.getBoatTexture(parent.playerBoatNumber).getRegionWidth()) / 2) + (opponentBoatSprite.getX() - playerBoatSprite.getX()) * backgroundTextureSize, ((viewport.getWorldHeight() - assets.getBoatTexture(parent.playerBoatNumber).getRegionHeight()) / 2) + (opponentBoatSprite.getY() - playerBoatSprite.getY()) * backgroundTextureSize, opponentBoatSprite.getOriginX(), opponentBoatSprite.getOriginY(), opponentBoatSprite.getWidth(), opponentBoatSprite.getHeight(), opponentBoatSprite.getScaleX(), opponentBoatSprite.getScaleY(), avgRotation);
                }

                //draw hud
                hud.draw(batch, viewport);

                batch.end();
            } catch(NullPointerException e) {
                System.out.println(e.toString());
            }
        }
    }

        private void renderBackground ( float delta){
            //Calculate screen variables
            if (viewport.getWorldWidth() / viewport.getWorldHeight() < minAspectRatio) {
                backgroundTextureSize = (int) (viewport.getWorldWidth() / laneWidthsScreen);
            } else {
                backgroundTextureSize = (int) (viewport.getWorldHeight() / (laneWidthsScreen / minAspectRatio));
            }
            riverCountX = (riverCountX + environmentalConditions.getCurrent().getForce().getX() * 2.35f) % backgroundTextureSize;
            riverCountY = (riverCountY + environmentalConditions.getCurrent().getForce().getY() * 2.35f) % backgroundTextureSize;

            //Disable unnecessary transparency blending to increase performance
            batch.disableBlending();

            //Draw river
            backgroundSprite.updateTexture(delta);
            for (int i = -1; i < (int) Math.ceil(laneWidthsRiver + 1); i++) {
                for (int j = -1; j < (viewport.getWorldHeight() / backgroundTextureSize) + 1; j++) {
                    safeDraw(backgroundSprite.getTexture(), (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) + riverCountX - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, j * backgroundTextureSize + (riverCountY - playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize) % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
                }
            }

            //Draw Start & finish Line
            for (int i = 0; i < (int) Math.ceil(laneWidthsRiver); i++) {
                safeDraw(assets.getTexture(Assets.flagTexture), (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, (viewport.getWorldHeight() / 2) + (-0.5f - playerBoat.getSpriteObj().getSprite().getY()) * backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
                safeDraw(assets.getTexture(Assets.flagTexture), (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, (viewport.getWorldHeight() / 2) + (-0.5f + raceLength - playerBoat.getSpriteObj().getSprite().getY()) * backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
            }

            //Draw land
            for (int i = 0; i < (int) (viewport.getWorldWidth() / backgroundTextureSize / 2) + 2; i++) {
                for (int j = -1; j < (viewport.getWorldHeight() / backgroundTextureSize) + 1; j++) {
                    safeDraw(assets.getTexture(Assets.landTexture), (int) ((viewport.getWorldWidth() - laneWidthsRiver * backgroundTextureSize) / 2 - (i + 1) * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, j * backgroundTextureSize - (playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize) % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
                    safeDraw(assets.getTexture(Assets.landTexture), (int) ((viewport.getWorldWidth() + laneWidthsRiver * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize, j * backgroundTextureSize - (playerBoat.getSpriteObj().getSprite().getY() * backgroundTextureSize) % backgroundTextureSize, backgroundTextureSize, backgroundTextureSize);
                }
            }

            //Re-enable transparency blending for textures that contain transparency
            batch.enableBlending();

            //Draw buoys
            for (int i = 0; i < noOfBoats + 1; i++) {
                for (int j = -1; j < (raceLength * 3 / 2) - 2; j++) {
                    if (i >= noOfBoats / 2 && i <= noOfBoats / 2 + 1) {
                        safeDraw(assets.getTexture(Assets.playerBuoyTexture), (int) ((viewport.getWorldWidth() - (noOfBoats) * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize - backgroundTextureSize / 16, (viewport.getWorldHeight() / 2) + j * backgroundTextureSize * 2 / 3 + (1.5f - playerBoat.getSpriteObj().getSprite().getY()) * backgroundTextureSize - backgroundTextureSize / 4, backgroundTextureSize / 8, backgroundTextureSize / 8);
                    } else {
                        safeDraw(assets.getTexture(Assets.buoyTexture), (int) ((viewport.getWorldWidth() - (noOfBoats) * backgroundTextureSize) / 2 + i * backgroundTextureSize) - playerBoat.getSpriteObj().getSprite().getX() * backgroundTextureSize - backgroundTextureSize / 16, (viewport.getWorldHeight() / 2) + j * backgroundTextureSize * 2 / 3 + (1.5f - playerBoat.getSpriteObj().getSprite().getY()) * backgroundTextureSize - backgroundTextureSize / 4, backgroundTextureSize / 8, backgroundTextureSize / 8);
                    }
                }
            }
        }

      
    private void detectInput() {
            ////Keyboard input
            //Return to menu
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                parent.level = 0;
                parent.changeScreen(BoatRace.MENU);
            }
            //Player controls
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                playerBoat.setThrottle(1.05f);
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                playerBoat.setThrottle(-1.05f);
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

    public LinkedList<Boat> getFinalBoats() {
        LinkedList<Boat> finalBoats = new LinkedList<>(opponentBoats);
        finalBoats.add(playerBoat);
        return finalBoats;
    }

        @Override
        public void resize ( int width, int height){
            viewport.update(width, height, true);
            batch.setProjectionMatrix(camera.combined);
        }

        @Override
        public void pause () {

        }

        @Override
        public void resume () {

        }

        @Override
        public void hide () {
            dispose();
        }

        @Override
        public void show () {

        }

        @Override
        public void dispose () {
            assets.dispose();
            batch.dispose();
        }

        public int getBackgroundTextureSize () {
            return backgroundTextureSize;
        }

        public float getLaneWidthsRiver () {
            return laneWidthsRiver;
        }

        public float getTimer () {
            return timer;
        }

        public LinkedList<SpriteObj> getSpriteObjs () {
            return spriteObjs;
        }

        public LinkedList<SpriteObj> getEnemyObjects () {
            LinkedList<SpriteObj> enemySpriteObjects = new LinkedList<SpriteObj>();
            for (Enemy enemy : enemyObjects) {
                enemySpriteObjects.add(enemy.getSpriteObj());
            }
            return enemySpriteObjects;
        }

        public Boat getPlayerBoat () {
            return playerBoat;
        }

        public EnvironmentalConditions getEnvironmentalConditions () {
            return environmentalConditions;
        }
}

