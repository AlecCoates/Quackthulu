package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;

public class Current {
    private Force force;

    public Current() {
        this(new Force());
    }

    public Current(Force force) {
        this.force = force;
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }
}
