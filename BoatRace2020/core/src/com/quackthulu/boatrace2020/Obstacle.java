package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Shape2D;
import com.quackthulu.boatrace2020.basics.TimedTexture;

public class Obstacle {
    private int damage;

    public Obstacle() {
        this(1);
    }

    public Obstacle(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
