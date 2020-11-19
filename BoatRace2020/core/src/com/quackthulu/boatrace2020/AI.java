package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AI {
    public Float riskTaking;
    public Float vision;
    public NewBoat boat;

    public AI() {
        riskTaking = 1.0f;
        vision = 1.0f;
    }

    public void update(float delta) {
        boat.setThrottle(new Random().nextFloat() * 0.1f + 0.9f);
        List<float[]> usableSpaces = new LinkedList<>();
        usableSpaces.add(boat.lane.clone());
        if (boat.getSpriteObj().gameScreen.getEnemyObjects() == null) return;
        for (SpriteObj enemyObject : boat.getSpriteObj().gameScreen.getEnemyObjects()) {
            Rectangle boundingRect = enemyObject.getBounds().getBoundingRectangle();
            List<float[]> newUsableSpaces = new LinkedList<>();
            List<Integer> removeUsableSpaces = new LinkedList<>();
            for (int i = 0; i < usableSpaces.size(); i++) {
                if ((boundingRect.x > usableSpaces.get(i)[0] && boundingRect.x < usableSpaces.get(i)[1]) || (boundingRect.x + boundingRect.width > usableSpaces.get(i)[0] && boundingRect.x + boundingRect.width < usableSpaces.get(i)[1])) {
                    if (boundingRect.x > usableSpaces.get(i)[0]) {
                        newUsableSpaces.add(new float[] {usableSpaces.get(i)[0], boundingRect.x});
                    }
                    if (boundingRect.x + boundingRect.width < usableSpaces.get(i)[1]) {
                        newUsableSpaces.add(new float[] {boundingRect.x + boundingRect.width, usableSpaces.get(i)[1]});
                    }
                    removeUsableSpaces.add(i);
                }
            }
            for (int rem : removeUsableSpaces) {
                //usableSpaces.remove(rem);
            }
            //usableSpaces.addAll(newUsableSpaces);
        }
        System.out.println(usableSpaces.size());
        //boat.setSteering();
    }
}
