package com.quackthulu.boatrace2020.basics;

public class Velocity {
    private float x;
    private float y;

    public Velocity() {
        this(0, 0);
    }

    public Velocity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void addX(float x) {
        this.x += x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void addY(float y) {
        this.y += y;
    }

    public void setVelocity(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void addVelocity(float x, float y) {
        this.addX(x);
        this.addY(y);
    }
}
