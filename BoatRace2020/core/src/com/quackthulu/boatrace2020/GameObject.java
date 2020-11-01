package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class GameObject {
    //position and dimensions
    float xPos, yPos; //lower left corner
    float width, height;

    //graphics
    TextureRegion thisObjectTexture;


    public GameObject(float xPos, float yPos, float width, float height, TextureRegion thisObjectTexture) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.thisObjectTexture = thisObjectTexture;
    }

    public void draw(Batch batch){
        //damage textures will need to change the ship
        batch.draw(thisObjectTexture, xPos, yPos, width, height);
    }
}
