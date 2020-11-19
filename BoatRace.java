package com.quackthulu.boatrace2020;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BoatRace extends Game {
	private LoadingScreen loadingScreen;
	private MainMenu mainMenu;
	private GameScreen gameScreen;
	private SettingsScreen settingsScreen;
	private Settings settings;

	public final static int MENU = 0;
	public final static int GAME = 1;
	public final static int SETTINGS = 2;

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
		settings = new Settings();
	}
	
	public void changeScreen(int screen){
		switch(screen){
			case MENU:
				if(mainMenu == null) mainMenu = new MainMenu(this);
					this.setScreen(mainMenu);
				break;
			case GAME:
				if(gameScreen == null) gameScreen = new GameScreen(this);
				this.setScreen(gameScreen);
				break;
			case SETTINGS:
				if(settingsScreen == null) settingsScreen = new SettingsScreen(this);
				this.setScreen(settingsScreen);
				break;
		}

	}
	public Settings getPreferences(){
		return this.settings;
	}
}
