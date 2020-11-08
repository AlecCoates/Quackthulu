package com.quackthulu.boatrace2020.basics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SingleSprite {
    TextureRegion image;
    Float time;

    public SingleSprite() {
        this(new TextureRegion());
    }

    public SingleSprite(TextureRegion image) {
        this(image, 0.0f);
    }

    public SingleSprite(TextureRegion image, Float time) {
        this.image = image;
        this.time = time;
    }
}
