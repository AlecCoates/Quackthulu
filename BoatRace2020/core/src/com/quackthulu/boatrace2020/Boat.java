package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class Boat extends GameObject{

    //ship characteristics
    float movementSpeed; //world units per second
    int health; //default values
    boolean isPlayer;

    public Boat(int xCenter, int yCenter, int width, int height, TextureRegion thisObjectTexture, float movementSpeed, int health, boolean isPlayer) {
        super(xCenter, yCenter, width, height, thisObjectTexture);
        this.movementSpeed = movementSpeed;
        this.health = health;
        this.isPlayer = isPlayer;
    }
}
