package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class Enemy extends GameObject{
    float movementSpeed;

    public Enemy(int xCenter, int yCenter, int width, int height, TextureRegion thisObjectTexture, float movementSpeed) {
        super(xCenter, yCenter, width, height, thisObjectTexture);
        this.movementSpeed = movementSpeed;
    }
}
