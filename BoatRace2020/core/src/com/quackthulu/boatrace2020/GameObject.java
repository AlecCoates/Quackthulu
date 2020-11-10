package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    //position and dimensions
    com.badlogic.gdx.math.Rectangle boundingBox;

    //graphics
    TextureRegion thisObjectTexture;

    //damage
    int outputDamage;

    public GameObject(int xCenter, int yCenter, int width, int height, int outputDamage, TextureRegion thisObjectTexture) {
        this.boundingBox = new com.badlogic.gdx.math.Rectangle(xCenter - height/2,yCenter - width/2,width,height);
        this.thisObjectTexture = thisObjectTexture;
        this.outputDamage = outputDamage;
    }

    public void draw(Batch batch){
        //damage textures will need to change the ship
        batch.draw(thisObjectTexture, boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }


    public boolean intersects(com.badlogic.gdx.math.Rectangle otherRectangle){
        return boundingBox.overlaps(otherRectangle);
    }

    public void translate(float xChange, float yChange){
        boundingBox.setPosition(boundingBox.x+xChange,boundingBox.y+yChange);
    }

    public Vector2 center(){
        return new Vector2((boundingBox.x + boundingBox.width/2),(boundingBox.y + boundingBox.height /2));
    }
}
