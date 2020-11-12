package com.quackthulu.boatrace2020.basics;

public class Force {
    private float x;
    private float y;

    public Force() {
        this(0, 0);
    }

    public Force(float x, float y) {
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

    public void setForce(float x, float y) {
        this.setX(x);
        this.setY(y);
    }

    public void addForce(float x, float y) {
        this.addX(x);
        this.addY(y);
    }

    public float getLinearForce() {
        return (float) Math.sqrt(Math.pow(this.getX(),2) + Math.pow(this.getY(),2));
    }
}
