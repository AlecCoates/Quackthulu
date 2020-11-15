package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;
import com.sun.org.apache.bcel.internal.Const;

import java.util.List;

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

    public void update(float delta, EnvironmentalConditions env, List<SpriteObj> collisionObjs) {
        dynamicObj.update(delta, env, collisionObjs, spriteObj);
        spriteObj.updateTexture(delta);
    }

    private void collision() {

    }

    public void setThrottle(float throttle) {
        dynamicObj.setForce(new Force(throttle * rowers.getMaxForce() * (float) Math.sin(Math.toRadians(spriteObj.getSprite().getRotation())), throttle * rowers.getMaxForce() * (float) Math.cos(Math.toRadians(spriteObj.getSprite().getRotation()))));
    }

    public void setSteering(float steering) {
        float cosTheta = (float) Math.cos(Math.acos(dynamicObj.getVelocity().getY() / dynamicObj.getVelocity().getLinearVelocity()) - Math.toRadians(spriteObj.getSprite().getRotation()));
        if (!(cosTheta >= -1)) cosTheta = 1;
        dynamicObj.setTorque((steering * rowers.getAgility()) * (1.1f + cosTheta) / 2);
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
