package com.quackthulu.boatrace2020.basics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimedTexture {
    private Texture texture;
    private float time;

    public TimedTexture(Texture image) {
        this(image, 0.0f);
    }

    public TimedTexture(Texture texture, float time) {
        this.texture = texture;
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public Texture getTexture() {
        return texture;
    }
}
