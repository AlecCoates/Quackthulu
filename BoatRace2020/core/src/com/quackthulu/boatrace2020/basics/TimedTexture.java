package com.quackthulu.boatrace2020.basics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimedTexture {
    private TextureRegion texture;
    private float time;

    public TimedTexture(TextureRegion image) {
        this(image, 0.0f);
    }

    public TimedTexture(TextureRegion texture, float time) {
        this.texture = texture;
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public TextureRegion getTexture() {
        return texture;
    }
}
