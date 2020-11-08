package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends GameObject{
    float movementSpeed;

    public Enemy(int xCenter, int yCenter, int width, int height, int outputDamage, TextureRegion thisObjectTexture, float movementSpeed) {
        super(xCenter, yCenter, width, height, outputDamage, thisObjectTexture);
        this.movementSpeed = movementSpeed;
    }


}
