package com.quackthulu.boatrace2020.basics;

public class Velocity {
    float x;
    float y;

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

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
