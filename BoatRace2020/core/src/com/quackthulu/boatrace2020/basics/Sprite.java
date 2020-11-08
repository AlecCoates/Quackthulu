package com.quackthulu.boatrace2020.basics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite {
    SingleSprite[] images;

    public Sprite() {
        this(new TextureRegion());
    }

    public Sprite(TextureRegion image) {
        this(new SingleSprite[] {new SingleSprite(image)});
    }

    public Sprite(SingleSprite[] images) {
        this.images = images;
    }
}
