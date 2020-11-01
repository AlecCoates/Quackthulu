package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Boat extends GameObject{

    //ship characteristics
    float movementSpeed = 1; //world units per second
    int health = 1; //default values
    boolean isPlayer = false;

    public Boat(float xPos, float yPos, float width, float height, TextureRegion thisObjectTexture) {
        super(xPos, yPos, width, height, thisObjectTexture);
    }

    public Boat(float xPos, float yPos, float width, float height, TextureRegion thisObjectTexture, float movementSpeed, int health, boolean isPlayer) {
        super(xPos, yPos, width, height, thisObjectTexture);
        this.movementSpeed = movementSpeed;
        this.health = health;
        this.isPlayer = isPlayer;
    }
}
