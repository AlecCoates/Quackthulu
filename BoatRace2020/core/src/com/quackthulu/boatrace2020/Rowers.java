package com.quackthulu.boatrace2020;

public class Rowers {
    private float agility;
    private float maxStamina;
    private float stamina;
    private float maxForce;
    private float force;

    public Rowers() {
        this(60.0f, 2000.0f, 2.5f);
    }

    public Rowers(float agility, float maxStamina, float maxForce) {
        this.agility = agility;
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
        this.maxForce = maxForce;
        this.force = 0;
    }

    public float getAgility() {
        return agility;
    }

    public void setAgility(float agility) {
        this.agility = agility;
    }

    public float getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(float maxStamina) {
        this.maxStamina = maxStamina;
    }

    public float getStamina() {
        return stamina;
    }

    public float getMaxForce() {
        return maxForce;
    }

    public void setMaxForce(float maxForce) {
        this.maxForce = maxForce;
    }

    public float getForce() {
        return force;
    }
}
