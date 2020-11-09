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
}
