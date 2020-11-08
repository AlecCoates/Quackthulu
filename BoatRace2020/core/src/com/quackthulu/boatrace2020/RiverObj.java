package com.quackthulu.boatrace2020;

import com.quackthulu.boatrace2020.basics.Position;
import com.quackthulu.boatrace2020.basics.Sprite;

public class RiverObj {
    Position position;
    Sprite sprite;

    RiverObj() {
        this(new Sprite());
    }

    RiverObj(Sprite sprite) {
        this(new Position(), sprite);
    }

    RiverObj(Position position, Sprite sprite) {
        this.position = position;
        this.sprite = sprite;
    }
}
