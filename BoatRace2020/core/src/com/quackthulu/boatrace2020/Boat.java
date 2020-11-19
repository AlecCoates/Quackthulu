package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Boat implements CollisionCallback {
    private SpriteObj spriteObj;
    private DynamicObj dynamicObj;
    private Rowers rowers;
    public AI ai;
    private float maneuverability;
    private int maxHealth;
    private int health;
    private Map<SpriteObj, Float> collisions = new HashMap<SpriteObj, Float>();
    private float finishingTime = -1;
    private float penaltyTime = 0;
    public float[] lane = new float[] {0,0};

    public Boat() {
        this(new SpriteObj());
    }

    public Boat(SpriteObj spriteObj) {
        this(spriteObj, new DynamicObj(), new Rowers());
    }

    public Boat(SpriteObj spriteObj, DynamicObj dynamicObj, Rowers rowers) {
        this.spriteObj = spriteObj;
        this.dynamicObj = dynamicObj;
        this.rowers = rowers;
        this.maneuverability = 1.0f;
        this.maxHealth = 5;
        this.health = maxHealth;
        this.spriteObj.setDamage(1);
        this.spriteObj.dynamicObj = dynamicObj;
        this.dynamicObj.collisionCallback = this;
    }

    public void update(float delta, EnvironmentalConditions env, List<SpriteObj> collisionObjs) {
        dynamicObj.update(delta, env, collisionObjs, spriteObj);
        spriteObj.updateTexture(delta);
        if (spriteObj.getSprite().getX() < lane[0] || spriteObj.getSprite().getX() > lane[1]) {
            penaltyTime += 4 * delta;
        }
        if (ai != null) {
            ai.update(delta);
        }
    }

    public void collision(SpriteObj collisionObj) {
        boolean newCollision = false;
        if (collisions.containsKey(collisionObj)) {
            if (collisions.get(collisionObj) + 1.5f < spriteObj.gameScreen.getTimer()) {
                newCollision = true;
            }
        } else {
            newCollision = true;
        }
        if (newCollision) {
            collisions.put(collisionObj, spriteObj.gameScreen.getTimer());
            health -= collisionObj.getDamage();
        }
    }

    public void setThrottle(float throttle) {
        dynamicObj.setForce(new Force(throttle * rowers.getMaxForce() * (float) Math.sin(Math.toRadians(-spriteObj.getSprite().getRotation())), throttle * rowers.getMaxForce() * (float) Math.cos(Math.toRadians(-spriteObj.getSprite().getRotation()))));
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

    public void setFinishingTime(float finishingTime) {
        this.finishingTime = finishingTime + penaltyTime;
        System.out.println(this.finishingTime);
    }

    public boolean finishedRace() {
        return finishingTime >= 0;
    }

    public float getPenaltyTime() {
        return penaltyTime;
    }

}
