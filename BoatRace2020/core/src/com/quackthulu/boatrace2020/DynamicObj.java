package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.FloatArray;
import com.quackthulu.boatrace2020.basics.CustomMath;
import com.quackthulu.boatrace2020.basics.Force;
import com.quackthulu.boatrace2020.basics.Velocity;

import java.util.LinkedList;
import java.util.List;

public class DynamicObj {
    private float mass;
    private float dragMult;
    private Velocity velocity;
    private float rotationalVelocity;
    private Force force;
    private float torque;
    //public SpriteObj spriteObj;
    public CollisionCallback collisionCallback;

    public DynamicObj() {
        this.mass = 1.0f;
        this.dragMult = 0.8f;
        this.velocity = new Velocity();
        this.rotationalVelocity = 0.0f;
        this.force = new Force();
        this.torque = 0.0f;
    }

    public void update(float delta, EnvironmentalConditions env) {
        update(delta, env, null, null);
    }

    public void update(float delta, EnvironmentalConditions env, List<SpriteObj> collisionObjs, SpriteObj spriteObj) {
        update(delta, env, collisionObjs, spriteObj, false);
    }

    public void update(float delta, EnvironmentalConditions env, List<SpriteObj> collisionObjs, SpriteObj spriteObj, boolean collided) {
        float tempRotationalVelocity = calcVelocity(delta, rotationalVelocity, torque);
        float tempVelocityX = calcVelocity(delta, velocity.getX(), force.getX() + env.getCurrent().getForce().getX() + env.getWind().getForce().getX() + env.getWind().getGust().getForce().getX());
        float tempVelocityY = calcVelocity(delta, velocity.getY(), force.getY() + env.getCurrent().getForce().getY() + env.getWind().getForce().getY() + env.getWind().getGust().getForce().getY());
        rotationalVelocity += tempRotationalVelocity;
        velocity.addVelocity(tempVelocityX, tempVelocityY);
        if (spriteObj != null) {
            float oldRotation = spriteObj.getSprite().getRotation();
            float oldX = spriteObj.getSprite().getX();
            float oldY = spriteObj.getSprite().getY();
            spriteObj.getSprite().rotate(rotationalVelocity * delta);
            spriteObj.getSprite().setX(spriteObj.getSprite().getX() + velocity.getX() * delta);
            spriteObj.getSprite().setY(spriteObj.getSprite().getY() + velocity.getY() * delta);
            SpriteObj collision = null;
            for (SpriteObj collisionObj : collisionObjs) {
                if (spriteObj != collisionObj && spriteObj.getIsCollider() && collisionObj.getIsCollider() && Intersector.intersectPolygons(new FloatArray(spriteObj.getBounds().getTransformedVertices()), new FloatArray(collisionObj.getBounds().getTransformedVertices()))) {
                    collision = collisionObj;
                }
            }
            if (collision != null) {
                spriteObj.getSprite().setRotation(oldRotation);
                spriteObj.getSprite().setX(oldX);
                spriteObj.getSprite().setY(oldY);
                if (delta > 1) {
                    update(delta / 2, env, collisionObjs, spriteObj, true);
                }
                if (!(collided)) {
                    float totalKineticX = 0.5f * mass * (float) CustomMath.signPow(velocity.getX(), 2);
                    float totalKineticY = 0.5f * mass * (float) CustomMath.signPow(velocity.getY(), 2);
                    totalKineticX += 0.5f * collision.dynamicObj.getMass() * (float) CustomMath.signPow(collision.dynamicObj.getVelocity().getX(), 2);
                    totalKineticY += 0.5f * collision.dynamicObj.getMass() * (float) CustomMath.signPow(collision.dynamicObj.getVelocity().getY(), 2);
                    velocity.setX((float) CustomMath.signSqrt(totalKineticX / mass));
                    velocity.setY((float) CustomMath.signSqrt(totalKineticY / mass));
                    collision.dynamicObj.getVelocity().setX((float) CustomMath.signSqrt(totalKineticX / mass));
                    collision.dynamicObj.getVelocity().setY((float) CustomMath.signSqrt(totalKineticY / mass));
                    collisionCallback.collision(collision);
                }
            }
        }
    }

    private float calcVelocity(float delta, float velocity, float force) {
        return delta * (force - (dragMult * velocity / mass));
    }

    //Getters & setters
    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public float getRotationalVelocity() {
        return rotationalVelocity;
    }

    public void setRotationalVelocity(float rotationalVelocity) {
        this.rotationalVelocity = rotationalVelocity;
    }

    public float getTorque() {
        return torque;
    }

    public void setTorque(float torque) {
        this.torque = torque;
    }
}
