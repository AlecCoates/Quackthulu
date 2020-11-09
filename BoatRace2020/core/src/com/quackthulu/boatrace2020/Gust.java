package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;

import java.util.Random;

public class Gust {
    private Force force;
    private float duration;
    private float probability;
    private float elapsed;

    public Gust() {
        this(new Force());
    }

    public Gust(Force force) {
        this(force, 5.0f, 0.1f);
    }

    public Gust(Force force, float duration, float probability) {
        this.force = force;
        this.duration = duration;
        this.probability = probability;
        this.elapsed = 0.0f;
    }

    public void update(float delta) {
        if (elapsed > duration) {
            elapsed = 0.0f;
        } else if (elapsed > 0.0f || new Random().nextFloat() < delta * probability) {
            elapsed += delta;
        }
    }

    public Force getForce() {
        Random rand = new Random();
        return new Force((1.0f + rand.nextFloat()) / 2 * force.getX(), (1.0f + rand.nextFloat()) / 2 * force.getY());
    }

    public void setMaxForce(Force force) {
        this.force = force;
    }

}
