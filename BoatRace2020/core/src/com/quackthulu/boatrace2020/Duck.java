package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.quackthulu.boatrace2020.Enemy;

public class Duck extends Enemy {

    public Duck(int xCenter, int yCenter, int width, int height, int outputDamage, TextureRegion thisObjectTexture, float movementSpeed) {
        super(xCenter, yCenter, width, height, thisObjectTexture, movementSpeed);
    }

}
