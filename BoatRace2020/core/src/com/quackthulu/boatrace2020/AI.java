package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Rectangle;

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

    public void update() {
        boat.setThrottle(new Random().nextFloat() * 0.1f + 0.9f);
        List<float[]> useableSpaces = new LinkedList<>();
        useableSpaces.add(boat.lane.clone());
        if (boat.getSpriteObj().gameScreen.getEnemyObjects() == null) return;
        for (SpriteObj enemyObject : boat.getSpriteObj().gameScreen.getEnemyObjects()) {
            Rectangle boundingRect = enemyObject.getBounds().getBoundingRectangle();
            List<float[]> newUseableSpaces = new LinkedList<>();
            for (int i = 0; i < useableSpaces.size(); i++) {
                if ((boundingRect.x > useableSpaces.get(i)[0] && boundingRect.x < useableSpaces.get(i)[1]) || (boundingRect.x + boundingRect.width > useableSpaces.get(i)[0] && boundingRect.x + boundingRect.width < useableSpaces.get(i)[1])) {
                    
                    useableSpaces.remove(i);
                }
            }
            useableSpaces.addAll(newUseableSpaces);
        }
        //boat.setSteering();
    }
}
