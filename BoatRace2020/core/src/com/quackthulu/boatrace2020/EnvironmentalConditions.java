package com.quackthulu.boatrace2020;

public class EnvironmentalConditions {
    private Wind wind;
    private Current current;

    public EnvironmentalConditions() {
        this.wind = new Wind();
        this.current = new Current();
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
