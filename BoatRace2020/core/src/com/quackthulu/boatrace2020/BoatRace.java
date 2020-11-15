package com.quackthulu.boatrace2020;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BoatRace extends Game {

	static public MainMenu MENU_SCREEN;
	static public SettingsScreen SETTINGS_SCREEN;
	static public GameScreen GAME_SCREEN;
	static public Lose LOSE_SCREEN;
	static public Win WIN_SCREEN;
	Settings settings;

	@Override
	public void create() {
		settings = new Settings();
		MENU_SCREEN = new MainMenu(this);
		SETTINGS_SCREEN = new SettingsScreen(this);
		GAME_SCREEN = new GameScreen();
		LOSE_SCREEN = new Lose(this);
		WIN_SCREEN = new Win(this);
		setScreen(MENU_SCREEN);
	}


	@Override
	public void dispose() {
		System.exit(0);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		getScreen().resize(width, height);
	}

	public Settings getPreferences() {
		return settings;
	}

}
