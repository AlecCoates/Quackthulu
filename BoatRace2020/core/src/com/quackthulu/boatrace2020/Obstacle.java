package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Shape2D;
import com.quackthulu.boatrace2020.basics.TimedTexture;

public class Obstacle {
    private SpriteObj spriteObj;
    private Float damage;
    private Float impulse;

    public Obstacle() {
        this.spriteObj = new SpriteObj(new TimedTexture[] {});
        this.damage = 0.0f;
        this.impulse = 0.0f;
    }
}
