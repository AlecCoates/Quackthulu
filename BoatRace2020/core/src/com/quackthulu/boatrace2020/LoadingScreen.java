package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Screen;

public class LoadingScreen implements Screen {
    private BoatRace parent;
    public LoadingScreen(BoatRace boatRace){
        parent = boatRace;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        parent.setScreen(BoatRace.MENU_SCREEN);

    }

    @Override
    public void resize(int width, int height) {

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
