package com.quackthulu.boatrace2020;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.quackthulu.boatrace2020.basics.TimedTexture;

public class SpriteObj {
    private Sprite sprite;
    private TimedTexture[] timedTextures;
    private int currentTexture;
    private float elapsedTextureTime;
    private Polygon customBounds;
    private boolean isCollider;
    private int damage;
    public DynamicObj dynamicObj;
    public GameScreen gameScreen;

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
        this.isCollider = true;
        this.damage = 0;
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

    public void setCustomBounds(Polygon bounds) {
        customBounds = bounds;
    }

    public Polygon getBounds() {
        Polygon poly;
        if (customBounds != null) {
            poly = new Polygon(customBounds.getVertices());
        } else {
            poly = new Polygon(new float[] {0, 0, sprite.getWidth(), 0, sprite.getWidth(), sprite.getHeight(), 0, sprite.getHeight()});
        }
        poly.setOrigin(sprite.getOriginX(), sprite.getOriginY());
        poly.setPosition(sprite.getX() * gameScreen.getBackgroundTextureSize(), sprite.getY() * gameScreen.getBackgroundTextureSize());
        poly.setRotation(sprite.getRotation());
        poly.setScale(sprite.getScaleX(), sprite.getScaleY());
        return poly;
    }

    public Rectangle getBoundingRectangle() {
        final float[] vertices = getBounds().getTransformedVertices();

        float minx = vertices[0];
        float miny = vertices[1];
        float maxx = vertices[0];
        float maxy = vertices[1];

        for (int i = 0; i < vertices.length-1; i+=2) {
            minx = Math.min(vertices[i], minx);
            maxx = Math.max(vertices[i], maxx);
        }
        for (int i = 1; i < vertices.length; i+=2) {
            miny = Math.min(vertices[i], miny);
            maxy = Math.max(vertices[i], maxy);
        }

        return new Rectangle(minx, miny, maxx - minx, maxy - miny);
    }

    public TextureRegion getTexture() {
        return timedTextures[currentTexture].getTexture();
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
