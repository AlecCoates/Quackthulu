package com.quackthulu.boatrace2020.basics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.quackthulu.boatrace2020.Assets;

public class TimedTexture {
    private TextureRegion texture;
    private float time;

    public TimedTexture(TextureRegion texture) {
        this(texture, 0.0f);
    }

    public TimedTexture(TextureRegion texture, float time) {
        this.texture = texture;
        this.time = time;
    }

    public TimedTexture(TimedTextureTemplate timedTextureTemplate, Assets assets) {
        this(assets.getTexture(timedTextureTemplate.getTexture()), timedTextureTemplate.getTime());
    }

    public float getTime() {
        return time;
    }

    public TextureRegion getTexture() {
        return texture;
    }
}
