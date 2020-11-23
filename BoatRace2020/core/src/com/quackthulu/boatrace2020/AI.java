package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Rectangle;

import java.util.*;

public class AI {
    public Float riskTaking;
    public Float vision;
    public Boat boat;

    public AI() {
        riskTaking = 1.0f;
        vision = 100.0f;
    }

    public void update(float delta) {
        boat.setThrottle(new Random().nextFloat() * riskTaking * 0.2f + 0.8f);

        int backgroundSize = boat.getSpriteObj().gameScreen.getBackgroundTextureSize();
        List<Rectangle> boundingRects = new LinkedList<>();
        for (SpriteObj enemyObject : boat.getSpriteObj().gameScreen.getSpriteObjs()) {
            Rectangle boundingRect = enemyObject.getBounds().getBoundingRectangle();
            boundingRect.x /= backgroundSize;
            boundingRect.y /= backgroundSize;
            boundingRect.width /= backgroundSize;
            boundingRect.height /= backgroundSize;
            if (enemyObject != boat.getSpriteObj() && (boundingRect.x + boundingRect.width > boat.lane[0] || boundingRect.x < boat.lane[1]) && boundingRect.y - boat.getSpriteObj().getSprite().getY() < vision && boundingRect.y - boat.getSpriteObj().getSprite().getY() > 0) {
                boundingRects.add(boundingRect);
            }
        }
        boundingRects.sort(new RectangleComparator());
        float[] laneSelector;
        if (boundingRects.size() == 0) {
            laneSelector = boat.lane.clone();
        } else {
            Rectangle boundingRect = boundingRects.get(0);
            Rectangle boatRect = boat.getSpriteObj().getBoundingRectangle();
            boatRect.x /= backgroundSize;
            boatRect.y /= backgroundSize;
            boatRect.width /= backgroundSize;
            boatRect.height /= backgroundSize;
            if (boundingRect.x - boat.lane[0] > boat.lane[1] - boundingRect.x + boundingRect.width) {
                if (boundingRect.x - boat.lane[0] > boatRect.width * 1.5f) {
                    laneSelector = new float[] {boat.lane[0], boundingRect.x};
                } else {
                    laneSelector = new float[] {boundingRect.x - boatRect.width * 1.5f, boundingRect.x};
                }
            } else {
                if (boat.lane[1] - boundingRect.x + boundingRect.width > boatRect.width * 1.5) {
                    laneSelector = new float[] {boundingRect.x + boundingRect.width, boat.lane[1]};
                } else {
                    laneSelector = new float[] {boundingRect.x + boundingRect.width, boundingRect.x + boundingRect.width + boatRect.width * 1.5f};
                }
            }
        }
        if (boat.getSpriteObj().getSprite().getX() < (laneSelector[0] + laneSelector[1]) / 2) {
            boat.setSteering(-0.2f);
        } else {
            boat.setSteering(0.2f);
        }
        if (boat.lane[0] < -2.0f) {
            System.out.println(Arrays.toString(laneSelector));
        }
        //boat.setSteering();
    }
}
