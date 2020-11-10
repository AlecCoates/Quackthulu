package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class HUD {
    private int maxHealth;
    private int currentDamage = 0;
    private TextureRegion fullTextureRegion, halfTextureRegion;

    public HUD(int health, TextureRegion fullTextureRegion, TextureRegion halfTextureRegion){
        this.maxHealth = health;
        this.fullTextureRegion = fullTextureRegion;
        this.halfTextureRegion = halfTextureRegion;
    }

    public void update(GameObject gameObject){
        currentDamage += gameObject.outputDamage;
    }

    public void draw(Batch batch,float x, float y) {
        int redrawHealth = maxHealth - currentDamage;
        int i = 1;
        while(redrawHealth != 0){
            if(redrawHealth - 2 >= 0){
                batch.draw(fullTextureRegion,x-350+i*64,y+350,64,64);
                redrawHealth -= 2;
            }else{
                batch.draw(halfTextureRegion,x-350+i*64,y+350,64,64);
                redrawHealth -=1;
            }
            i++;
        }
    }

    public void damage(GameObject gameObject){
        currentDamage = Math.min(currentDamage + gameObject.outputDamage, maxHealth);
    }
}
