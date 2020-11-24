package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Polygon;
import com.quackthulu.boatrace2020.basics.TimedTextureTemplate;

public class BoatStats {
    private final String name;
    private final TimedTextureTemplate[] timedTextures;
    private final Polygon customBounds;
    private final boolean isCollider;
    private final int maxHealth;
    private final int damage;
    private final float maneuverability;
    private final float dragMult;
    private final float mass;

    public BoatStats(String name, TimedTextureTemplate[] timedTextures, Polygon customBounds, boolean isCollider, int maxHealth, int damage, float maneuverability, float mass, float dragMult) {
        this.name = name;
        this.timedTextures = timedTextures;
        this.customBounds = customBounds;
        this.isCollider = isCollider;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.maneuverability = maneuverability;
        this.dragMult = dragMult;
        this.mass = mass;
    }

    public String getName() {
        return name;
    }

    public TimedTextureTemplate[] getTimedTextures() {
        return timedTextures;
    }

    public Polygon getCustomBounds() {
        return customBounds;
    }

    public boolean isCollider() {
        return isCollider;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public float getManeuverability() {
        return maneuverability;
    }

    public float getDragMult() {
        return dragMult;
    }

    public float getMass() {
        return mass;
    }
}
