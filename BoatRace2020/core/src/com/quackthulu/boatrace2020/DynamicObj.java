package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;
import com.quackthulu.boatrace2020.basics.Velocity;

public class DynamicObj {
    float mass;
    Velocity velocity;
    Force force;

    DynamicObj() {
        mass = 1.0f;
        velocity = new Velocity();
        force = new Force();
    }

    public void update(float delta) {
        velocity.setX(calcVelocity(delta, velocity.getX(), force.getX()));
        velocity.setY(calcVelocity(delta, velocity.getY(), force.getY()));
    }

    float calcVelocity(float delta, float velocity, float force) {
        return delta * (force / mass);
    }

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
}
