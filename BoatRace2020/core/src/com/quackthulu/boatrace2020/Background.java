package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class Background{
    //position and dimensions
    com.badlogic.gdx.math.Rectangle boundingBox;
    Vector2 center;

    //graphics
    TextureRegion textureRegion;

    public Background(TextureRegion textureRegion,int xPos, int yPos,int width, int height){
        this.textureRegion = textureRegion;
        this.boundingBox = new com.badlogic.gdx.math.Rectangle(xPos,yPos,width,height);
        this.center = center();
    }

    public Vector2 center(){
        return new Vector2((boundingBox.x + boundingBox.width/2),(boundingBox.y + boundingBox.height /2));
    }

    public void draw(Batch batch){
        batch.draw(textureRegion, boundingBox.x,boundingBox.y,boundingBox.width,boundingBox.height);
    }

    public void translate(float xChange, float yChange){
        boundingBox.setPosition(boundingBox.x+xChange,boundingBox.y+yChange);
    }

    public float dictenceTo(Vector2 boat){
        return (float)Math.sqrt(Math.pow((center.x-boat.x),2)+  Math.pow((center.y-boat.y),2));
    }

}
