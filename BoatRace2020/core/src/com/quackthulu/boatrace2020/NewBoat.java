package com.quackthulu.boatrace2020;

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

    public void setHealth(int health) {
        this.health = health;
    }
}
