package com.quackthulu.boatrace2020;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private final static  String FILE_TEXTURE_ATLAS = "images13.atlas";

    public final static String backgroundTexture = "sea5";
    public final static String backgroundTexture2 = "sea5b";
    public final static String landTexture = "land2";
    public final static String flagTexture = "flag1";

    public final static String buoyTexture = "buoy";
    public final static String playerBuoyTexture = "orange_buoy";
    public final static String tankBoatTexture = "tank";
    public final static String speedBoatTexture = "speedboat";
    public final static String vikingBoatTexture = "viking_longboat";
    public final static String jetSkiTexture = "jet_skii";
    public final static String normalBoatTexture = "player";
    public final static String enemyDuckTexture = "duck_in_top_hat2";
    public final static String fullHUDTexture = "full_paddle";
    public final static String halfHUDTexture = "half_paddle";

    public static final AssetDescriptor<TextureAtlas> textureAtlas =
            new AssetDescriptor<>(FILE_TEXTURE_ATLAS, TextureAtlas.class);

    public AssetManager manager = new AssetManager();
    public boolean loaded = false;

    public void load()
    {
        manager.load(textureAtlas);
        loaded = true;
    }

    public void dispose()
    {
        loaded = false;
        manager.dispose();
    }

    public TextureRegion getTexture(String textureName) {
        if (loaded) {
            return manager.get(textureAtlas).findRegion(textureName);
        } else {
            return null;
        }
    }

    public TextureRegion getBoatTexture(int boatNumber) {
        if (loaded) {
            switch (boatNumber) {
                case BoatsStats.TANK_BOAT:
                    return manager.get(textureAtlas).findRegion(Assets.tankBoatTexture);
                case BoatsStats.SPEED_BOAT:
                    return manager.get(textureAtlas).findRegion(Assets.speedBoatTexture);
                case BoatsStats.VIKING_BOAT:
                    return manager.get(textureAtlas).findRegion(Assets.vikingBoatTexture);
                case BoatsStats.JETSKI_BOAT:
                    return manager.get(textureAtlas).findRegion(Assets.jetSkiTexture);
                case BoatsStats.NORMAL_BOAT:
                    return manager.get(textureAtlas).findRegion(Assets.normalBoatTexture);
            }
        }
        return null;
    }

}