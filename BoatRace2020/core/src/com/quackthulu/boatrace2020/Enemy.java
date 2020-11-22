package com.quackthulu.boatrace2020;

public class Enemy {
    private SpriteObj spriteObj;

    public Enemy() {
        this(new SpriteObj());
    }

    public Enemy(SpriteObj spriteObj) {
        this.spriteObj = spriteObj;
    }

    public Enemy(SpriteObj spriteObj, int damageOutput) {
        this.spriteObj = spriteObj;
    }

    public SpriteObj getSpriteObj() {
        return spriteObj;
    }

    public void update(float delta, EnvironmentalConditions env) {
        spriteObj.updateTexture(delta);
    }
}
