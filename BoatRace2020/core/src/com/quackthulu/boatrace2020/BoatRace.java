package com.quackthulu.boatrace2020;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;

public class BoatRace extends Game {
	private LoadingScreen LOADING_SCREEN;
	private MainMenu MENU_SCREEN;
	private InstructionsScreen INSTRUCTIONS_SCREEN;
	private SettingsScreen SETTINGS_SCREEN;
	private GameScreen GAME_SCREEN;
	private Lose LOSE_SCREEN;
	private Win WIN_SCREEN;
	public Settings settings;
	public Music music;


	public final static int MENU = 0;
	public final static int GAME = 1;
	public final static int SETTINGS = 2;
	public final static int INSTRUCTIONS = 3;
	public final static int LOSE = 4;
	public final static int WIN = 5;


	@Override
	public void create() {
		MENU_SCREEN = new MainMenu(this);
		SETTINGS_SCREEN = new SettingsScreen(this);
		GAME_SCREEN = new GameScreen(this);
		LOSE_SCREEN = new Lose(this);
		WIN_SCREEN = new Win(this);
		setScreen(MENU_SCREEN);
    
		settings = new Settings();

		music = Gdx.audio.newMusic(Gdx.files.internal("music/Daniel_Birch_-_02_-_One_Man.mp3"));
		music.setLooping(true);
		music.setVolume(settings.getSoundVolume());
		if (settings.isSoundEnabled()) {
			music.play();
		}
		else{
			music.pause();
		}
	}
  
	public void changeScreen(int screen){
		switch (screen){
			case MENU:
				if(MENU_SCREEN == null) MENU_SCREEN = new MainMenu(this);
				this.setScreen(MENU_SCREEN);
				break;
			case GAME:
				if(GAME_SCREEN == null) GAME_SCREEN = new GameScreen(this);
				this.setScreen(GAME_SCREEN);
				break;
			case SETTINGS:
				if(SETTINGS_SCREEN == null) SETTINGS_SCREEN = new SettingsScreen(this);
				this.setScreen(SETTINGS_SCREEN);
				break;
			case INSTRUCTIONS:
				if(INSTRUCTIONS_SCREEN == null) INSTRUCTIONS_SCREEN = new InstructionsScreen(this);
				this.setScreen(INSTRUCTIONS_SCREEN);
				break;
			case LOSE:
				if(LOSE_SCREEN == null) LOSE_SCREEN = new Lose(this);
				this.setScreen(LOSE_SCREEN);
				break;
			case WIN:
				if(WIN_SCREEN == null) WIN_SCREEN = new Win(this);
				this.setScreen(WIN_SCREEN);
				break;
		}
	}

	@Override
	public void dispose() {
		music.dispose();
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
