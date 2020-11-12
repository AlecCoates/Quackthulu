package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Duck extends Enemy {


    public Duck(int xCenter, int yCenter, int width, int height, TextureRegion thisObjectTexture, float movementSpeed) {
        super(xCenter, yCenter, width, height, thisObjectTexture, movementSpeed);
    }
}
