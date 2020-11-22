package com.quackthulu.boatrace2020;

import com.badlogic.gdx.math.Rectangle;

import java.util.Comparator;

public class RectangleComparator implements Comparator<Rectangle> {
    @Override
    public int compare(Rectangle rect1, Rectangle rect2) {
        if (rect1.y - rect2.y > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
