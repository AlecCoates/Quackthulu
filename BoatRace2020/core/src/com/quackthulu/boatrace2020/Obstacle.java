package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Shape2D;
import com.quackthulu.boatrace2020.basics.TimedTexture;

public class Obstacle {
    SpriteObj spriteObj;
    Float damage;
    Float impulse;

    Obstacle() {
        this.spriteObj = new SpriteObj(new TimedTexture[] {});
        this.damage = 0.0f;
        this.impulse = 0.0f;
    }
}
