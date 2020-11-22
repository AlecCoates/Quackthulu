package com.quackthulu.boatrace2020;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
    /**
     * Settings is a class that used as a getter and setter for sound enabled and sound volume
     * SettingsScreen uses Settings to correlate volume and sound enabled from inputs to actual changes using setter functions
     * @author Aaron Price
     */
    private static final String PREF_SOUND_ENABLED = "music.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREF_NAME = "Player 1";

    protected Preferences getPrefs(){
        return Gdx.app.getPreferences(PREF_NAME);
    }

    //getter function for sound enabled, default = true
    public boolean isSoundEnabled(){
        return getPrefs().getBoolean(PREF_SOUND_ENABLED,true);
    }

    //setter function to change enabled value
    public void setSoundEnabled(boolean soundEffectsEnabled){
        getPrefs().putBoolean(PREF_SOUND_ENABLED,soundEffectsEnabled);
        getPrefs().flush();
    }

    //getter function for sound volume, default = 10%
    public float getSoundVolume(){
        return  getPrefs().getFloat(PREF_SOUND_VOL,0.1f);
    }

    //setter function to change volume of sound
    public void setSoundVolume(float volume){
        getPrefs().putFloat(PREF_SOUND_VOL,volume);
        getPrefs().flush();
    }

}
