package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.quackthulu.boatrace2020.basics.TimedTexture;

public class SpriteObj {
    private Sprite sprite;
    private TimedTexture[] timedTextures;
    private int currentTexture;
    private float elapsedTextureTime;
    private Polygon customBounds;
    private boolean isCollider;
    private Object collisionHandler;
    private DynamicObj dynamicObj;

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
            this.sprite.setTexture(timedTextures[0].getTexture().getTexture());
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
            sprite.setTexture(timedTextures[currentTexture].getTexture().getTexture());
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setTimedTextures(TimedTexture[] timedTextures) {
        this.timedTextures = timedTextures;
        if (timedTextures.length > 0) {
            sprite.setTexture(timedTextures[0].getTexture().getTexture());
        }
        sprite.setSize(timedTextures[0].getTexture().getRegionWidth(), timedTextures[0].getTexture().getRegionHeight());
        sprite.setOrigin(sprite.getWidth() / 2.0f, sprite.getHeight() / 2.0f);
        currentTexture = 0;
        elapsedTextureTime = 0.0f;
    }

    public void setIsCollider(boolean isCollider) {
        this.isCollider = isCollider;
    }

    public boolean getIsCollider() {
        return isCollider;
    }

    public void setBounds(Polygon bounds) {
        customBounds = bounds;
    }

    public Polygon getBounds() {
        Polygon poly;
        if (customBounds != null) {
            poly = new Polygon(customBounds.getVertices());
        } else {
            poly = rectangleToPolygon(sprite.getBoundingRectangle());
        }
        poly.setOrigin(sprite.getOriginX(), sprite.getOriginY());
        poly.setPosition(sprite.getX(), sprite.getY());
        poly.setRotation(sprite.getRotation());
        poly.setScale(sprite.getScaleX(), sprite.getScaleY());
        return poly;
    }

    private Polygon rectangleToPolygon(Rectangle rect) {
        return new Polygon(new float[] {rect.x, rect.y, (rect.x + rect.width), rect.y, (rect.x + rect.width), (rect.y + rect.height), rect.x, (rect.y + rect.height)});
    }

    public DynamicObj getDynamicObj() {
        return dynamicObj;
    }

    public void setDynamicObj(DynamicObj dynamicObj) {
        this.dynamicObj = dynamicObj;
    }
}
