package com.quackthulu.boatrace2020;

public class EnvironmentalConditions {
    Wind wind;
    Current current;

    EnvironmentalConditions() {
        this.wind = new Wind();
        this.current = new Current();
    }
}
