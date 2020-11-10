package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;
import com.quackthulu.boatrace2020.basics.Velocity;

public class DynamicObj {
    private float mass;
    private Velocity velocity;
    private float rotationalVelocity;
    private Force force;
    private float torque;

    public DynamicObj() {
        this.mass = 1.0f;
        this.velocity = new Velocity();
        this.rotationalVelocity = 0.0f;
        this.force = new Force();
        this.torque = 0.0f;
    }

    public void update(float delta, EnvironmentalConditions env) {
        update(delta, env, null);
    }

    public void update(float delta, EnvironmentalConditions env, SpriteObj spriteObj) {
        float tempRotationalVelocity = calcVelocity(delta, rotationalVelocity, torque);
        float tempVelocityX = calcVelocity(delta, velocity.getX(), force.getX() + env.getCurrent().getForce().getX() + env.getWind().getForce().getX() + env.getWind().getGust().getForce().getX());
        float tempVelocityY = calcVelocity(delta, velocity.getY(), force.getY() + env.getCurrent().getForce().getY() + env.getWind().getForce().getY() + env.getWind().getGust().getForce().getY());
        rotationalVelocity += tempRotationalVelocity;
        velocity.addVelocity(tempVelocityX, tempVelocityY);
        if (spriteObj != null) {
            spriteObj.getSprite().rotate(rotationalVelocity * delta);
            spriteObj.getSprite().setX(spriteObj.getSprite().getX() + velocity.getX() * delta);
            spriteObj.getSprite().setY(spriteObj.getSprite().getY() + velocity.getY() * delta);
        }
    }

    float calcVelocity(float delta, float velocity, float force) {
        return delta * (force - (velocity / mass));
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
