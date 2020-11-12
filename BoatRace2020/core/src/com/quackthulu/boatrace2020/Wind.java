package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;

public class Wind {
    private Force force;
    private Gust gust;

    public Wind() {
        this(new Force());
    }

    public Wind(Force force) {
        this(force, new Gust());
    }

    public Wind(Force force, Gust gust) {
        this.force = force;
        this.gust = gust;
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public Gust getGust() {
        return gust;
    }

    public void setGust(Gust gust) {
        this.gust = gust;
    }
}
