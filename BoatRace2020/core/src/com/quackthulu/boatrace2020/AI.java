package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Rectangle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AI {
    public Float riskTaking;
    public Float vision;
    public Boat boat;

    public AI() {
        riskTaking = 1.0f;
        vision = 10.0f;
    }

    public void update(float delta) {
        boat.setThrottle(new Random().nextFloat() * riskTaking * 0.2f + 0.8f);
        //if (boat.getSpriteObj().gameScreen.getEnemyObjects() == null) return;
        List<Rectangle> boundingRects = new LinkedList<>();
        for (SpriteObj enemyObject : boat.getSpriteObj().gameScreen.getEnemyObjects()) {
            Rectangle boundingRect = enemyObject.getBounds().getBoundingRectangle();
            if ((boundingRect.x + boundingRect.width > boat.lane[0] || boundingRect.x < boat.lane[1]) && Math.abs(boundingRect.y - boat.getSpriteObj().getSprite().getX()) < vision) {
                boundingRects.add(boundingRect);
            }
        }
        Collections.sort(boundingRects, (rect1, rect2) -> {
            if (rect1.y - rect2.y > 0) {
                return 1;
            } else {
                return -1;
            }
        });
        float[] laneSelector;
        if (boundingRects.size() == 0) {
            laneSelector = boat.lane.clone();
        } else {
            Rectangle boundingRect = boundingRects.get(0);
            Rectangle boatRect = boat.getSpriteObj().getSprite().getBoundingRectangle();
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
        System.out.print(laneSelector[0]);
        System.out.println(", ");
        System.out.println(laneSelector[1]);
        //boat.setSteering();
    }
}
