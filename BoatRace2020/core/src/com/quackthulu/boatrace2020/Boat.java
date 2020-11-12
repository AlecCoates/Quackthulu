package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class Boat extends GameObject{

    //ship characteristics
    float movementSpeed = 1; //world units per second
    int maxHealth = 1; //default values
    int currentDamage = 0;
    boolean isPlayer = false;

    public Boat(int xCenter, int yCenter, int width, int height, int outputDamage, TextureRegion thisObjectTexture, float movementSpeed, int health, boolean isPlayer) {
        super(xCenter, yCenter, width, height, outputDamage, thisObjectTexture);
        this.movementSpeed = movementSpeed;
        this.maxHealth = health;
        this.isPlayer = isPlayer;
    }

    public void damage(GameObject gameObject){
        currentDamage = Math.min(currentDamage + gameObject.outputDamage, maxHealth);
    }
}
