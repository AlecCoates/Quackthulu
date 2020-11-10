package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;

public class NewBoat {
    private SpriteObj spriteObj;
    private DynamicObj dynamicObj;
    private Rowers rowers;
    private float maneuverability;
    private int maxHealth;
    private int health;

    public NewBoat() {
        this(new SpriteObj());
    }

    public NewBoat(SpriteObj spriteObj) {
        this(spriteObj, new DynamicObj(), new Rowers());
    }

    public NewBoat(SpriteObj spriteObj, DynamicObj dynamicObj, Rowers rowers) {
        this.spriteObj = spriteObj;
        this.dynamicObj = dynamicObj;
        this.rowers = rowers;
        this.maneuverability = 1.0f;
        this.maxHealth = 5;
        this.health = maxHealth;
    }

    public void update(float delta, EnvironmentalConditions env) {
        dynamicObj.update(delta, env, spriteObj);
        spriteObj.updateTexture(delta);
    }

    public void setThrottle(float throttle) {
        dynamicObj.setForce(new Force(throttle * rowers.getMaxForce() * (float) Math.sin(Math.toRadians(spriteObj.getSprite().getRotation())), throttle * rowers.getMaxForce() * (float) Math.cos(Math.toRadians(spriteObj.getSprite().getRotation()))));
    }

    public void setSteering(float steering) {
        dynamicObj.setTorque(steering * rowers.getAgility());
    }

    public SpriteObj getSpriteObj() {
        return spriteObj;
    }

    public DynamicObj getDynamicObj() {
        return dynamicObj;
    }

    public Rowers getRowers() {
        return rowers;
    }

    public float getManeuverability() {
        return maneuverability;
    }

    public void setManeuverability(float maneuverability) {
        this.maneuverability = maneuverability;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }


}
