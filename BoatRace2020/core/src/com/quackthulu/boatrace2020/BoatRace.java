package com.quackthulu.boatrace2020;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;

import java.util.LinkedList;
import java.util.List;

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
	private PickBoatMenu PICK_BOAT_SCREEN;
	private Lose LOSE_SCREEN;
	private Win WIN_SCREEN;
	private InterScreen INT_SCREEN;
	public Settings settings;
	public Music music;

	public int playerBoatNumber = 0;
  	public int level = 0;
  	public List<Integer> boats = new LinkedList<>();

	public final static int MENU = 0;
	public final static int GAME = 1;
	public final static int SETTINGS = 2;
	public final static int INSTRUCTIONS = 3;
	public final static int PICK_BOAT = 4;
	public final static int LOSE = 5;
	public final static int WIN = 6;
	public final static int INTER = 7;


	//function sets MainMenu as the main screen and renders default settings
	@Override
	public void create() {
		MENU_SCREEN = new MainMenu(this);
		SETTINGS_SCREEN = new SettingsScreen(this);
		GAME_SCREEN = new GameScreen(this);
		LOSE_SCREEN = new Lose(this);
		WIN_SCREEN = new Win(this);
		PICK_BOAT_SCREEN = new PickBoatMenu(this);
		//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
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
				if (level >= 4) level = 0;
				if (MENU_SCREEN == null) MENU_SCREEN = new MainMenu(this);
				this.setScreen(MENU_SCREEN);
				break;
			case GAME:
			    level += 1;
				GAME_SCREEN = new GameScreen(this);
				this.setScreen(GAME_SCREEN);
				break;
			case SETTINGS:
				if (SETTINGS_SCREEN == null) SETTINGS_SCREEN = new SettingsScreen(this);
				this.setScreen(SETTINGS_SCREEN);
				break;
			case INSTRUCTIONS:
				if (INSTRUCTIONS_SCREEN == null) INSTRUCTIONS_SCREEN = new InstructionsScreen(this);
				this.setScreen(INSTRUCTIONS_SCREEN);
				break;
			case PICK_BOAT:
				if (PICK_BOAT_SCREEN == null) PICK_BOAT_SCREEN = new PickBoatMenu(this);
				this.setScreen(PICK_BOAT_SCREEN);
				break;
			case LOSE:
				level = 0;
				if (LOSE_SCREEN == null) LOSE_SCREEN = new Lose(this);
				this.setScreen(LOSE_SCREEN);
				break;
			case WIN:
				if (WIN_SCREEN == null) WIN_SCREEN = new Win(this);
				WIN_SCREEN.setFinalBoats(GAME_SCREEN.getFinalBoats());
				this.setScreen(WIN_SCREEN);
				break;
      		case INTER:
      			if(INT_SCREEN == null) INT_SCREEN = new InterScreen(this);
        		this.setScreen(INT_SCREEN);
        		break;
		}
	}

	public int getLevel(){
		return level;
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
