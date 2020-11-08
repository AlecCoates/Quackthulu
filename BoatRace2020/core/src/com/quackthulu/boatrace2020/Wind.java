package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;

public class Wind {
    Force force;
    Gust gust;

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

}
