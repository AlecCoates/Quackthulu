package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;

public class Gust {
    Force force;
    float duration;
    float probability;

    public Gust() {
        this(new Force());
    }

    public Gust(Force force) {
        this(force, 1.0f, 0.01f);
    }

    public Gust(Force force, float duration, float probability) {
        this.force = force;
        this.duration = duration;
        this.probability = probability;
    }

}
