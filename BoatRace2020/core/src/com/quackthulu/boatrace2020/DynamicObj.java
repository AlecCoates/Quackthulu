package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Force;
import com.quackthulu.boatrace2020.basics.Velocity;

public class DynamicObj {
    Float mass;
    Velocity velocity;
    Force force;

    DynamicObj() {
        mass = 1.0f;
        velocity = new Velocity();
        force = new Force();
    }
}
