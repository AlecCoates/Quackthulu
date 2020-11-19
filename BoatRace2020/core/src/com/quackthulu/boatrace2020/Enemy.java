package com.quackthulu.boatrace2020;

public class Enemy {
    private SpriteObj spriteObj;
    private int damageOutput = 1;

    public Enemy() {
        this(new SpriteObj());
    }

    public Enemy(SpriteObj spriteObj) {
        this.spriteObj = spriteObj;
    }

    public Enemy(SpriteObj spriteObj, int damageOutput) {
        this.spriteObj = spriteObj;
        this.damageOutput = damageOutput;
    }

    public SpriteObj getSpriteObj() {
        return spriteObj;
    }

    public int getDamageOutput() { return damageOutput; }

    public void setDamageOutput(int damage) { this.damageOutput = damage; }

    public void update(float delta, EnvironmentalConditions env) {
        spriteObj.updateTexture(delta);
    }
}
