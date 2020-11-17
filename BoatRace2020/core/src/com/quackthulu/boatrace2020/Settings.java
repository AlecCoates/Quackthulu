package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    private static final String PREF_SOUND_ENABLED = "music.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREF_NAME = "Player 1";
    private static final String PREF_BOAT = "default";

    protected Preferences getPrefs(){
        return Gdx.app.getPreferences(PREF_NAME);
    }

    public boolean isSoundEnabled(){
        return getPrefs().getBoolean(PREF_SOUND_ENABLED,true);
    }

    public void setSoundEnabled(boolean soundEffectsEnabled){
        getPrefs().putBoolean(PREF_SOUND_ENABLED,soundEffectsEnabled);
        getPrefs().flush();
    }

    public float getSoundVolume(){
        return  getPrefs().getFloat(PREF_SOUND_VOL,0.1f);
    }
    public void setSoundVolume(float volume){
        getPrefs().putFloat(PREF_SOUND_VOL,volume);
        getPrefs().flush();
    }

}
