package com.quackthulu.boatrace2020;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;

public class BoatRace extends Game {
	/**
	 * BoatRace extends Game import and acts as a main class, and a controller acting as parent in all possible game screens
	 * It features all of the games screens and menus, using changeScreen function to switch between screens
	 * @author Aaron Price
	 */
	private MainMenu MENU_SCREEN;
	private InstructionsScreen INSTRUCTIONS_SCREEN;
	private SettingsScreen SETTINGS_SCREEN;
	private GameScreen GAME_SCREEN;
	private Lose LOSE_SCREEN;
	private Win WIN_SCREEN;
	private InterScreen INT_SCREEN;
	public Settings settings;
	public Music music;


	public final static int MENU = 0;
	public final static int GAME = 1;
	public final static int SETTINGS = 2;
	public final static int INSTRUCTIONS = 3;
	public final static int LOSE = 4;
	public final static int WIN = 5;
	public final static int INTER = 6;
	public static int LEVEL = 0;


	//function sets MainMenu as the main screen and renders default settings 
	@Override
	public void create() {
		MENU_SCREEN = new MainMenu(this);
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

  	//function used to change screen
	public void changeScreen(int screen){
		switch (screen){
			case MENU:
				if(LEVEL >= 4) LEVEL = 0;
				if(MENU_SCREEN == null) MENU_SCREEN = new MainMenu(this);
				this.setScreen(MENU_SCREEN);
				break;
			case GAME:
			    LEVEL += 1;
				GAME_SCREEN = new GameScreen(this);
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
				LEVEL = 0;
				if(LOSE_SCREEN == null) LOSE_SCREEN = new Lose(this);
				this.setScreen(LOSE_SCREEN);
				break;
			case WIN:
				if(WIN_SCREEN == null) WIN_SCREEN = new Win(this);
				this.setScreen(WIN_SCREEN);
				break;
            case INTER:
                if(INT_SCREEN == null) INT_SCREEN = new InterScreen(this);
                this.setScreen(INT_SCREEN);
                break;
		}
	}

	//function disposes of assets not being used to clear memory
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
