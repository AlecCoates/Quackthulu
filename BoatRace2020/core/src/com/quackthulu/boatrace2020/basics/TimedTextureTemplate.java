package com.quackthulu.boatrace2020.basics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TimedTextureTemplate {
    private String texture;
    private float time;

    public TimedTextureTemplate(String texture) {
        this(texture, 0.0f);
    }

    public TimedTextureTemplate(String texture, float time) {
        this.texture = texture;
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public String getTexture() {
        return texture;
    }
}
