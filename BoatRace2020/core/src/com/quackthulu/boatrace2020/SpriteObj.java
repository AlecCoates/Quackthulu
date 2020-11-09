package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Shape2D;
import com.quackthulu.boatrace2020.basics.TimedTexture;

public class SpriteObj {
    private Sprite sprite;
    private TimedTexture[] timedTextures;
    private int currentTexture;
    private float elapsedTextureTime;
    private Shape2D customBounds;
    private boolean isCollider;

    public SpriteObj() {
        this(new TimedTexture[] {});
    }

    public SpriteObj(TimedTexture[] timedTextures) {
        this(new Sprite(), timedTextures);
    }

    public SpriteObj(Sprite sprite, TimedTexture[] timedTextures) {
        this.sprite = sprite;
        this.timedTextures = timedTextures;
        this.currentTexture = 0;
        this.elapsedTextureTime = 0.0f;
        this.customBounds = null;
        this.isCollider = true;
        if (this.timedTextures.length > 0) {
            this.sprite.setTexture(timedTextures[0].getTexture());
        }
    }

    public void updateTexture(float delta) {
        if (timedTextures.length > 1) {
            elapsedTextureTime += delta;
            while (timedTextures[currentTexture].getTime() < elapsedTextureTime) {
                elapsedTextureTime -= timedTextures[currentTexture].getTime();
                if (currentTexture < timedTextures.length - 1) {
                    currentTexture += 1;
                } else {
                    currentTexture = 0;
                }
            }
            sprite.setTexture(timedTextures[currentTexture].getTexture());
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setTimedTextures(TimedTexture[] timedTextures) {
        this.timedTextures = timedTextures;
        if (this.timedTextures.length > 0) {
            this.sprite.setTexture(timedTextures[0].getTexture());
        }
        this.currentTexture = 0;
        this.elapsedTextureTime = 0.0f;
    }

    public void setIsCollider(boolean isCollider) {
        this.isCollider = isCollider;
    }

    public boolean getIsCollider() {
        return isCollider;
    }

    public void setBounds(Shape2D bounds) {
        customBounds = bounds;
    }

    public Shape2D getBounds() {
        if (customBounds != null) {
            return customBounds;
        } else {
            return sprite.getBoundingRectangle();
        }
    }
}
