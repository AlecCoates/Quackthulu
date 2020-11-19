package com.quackthulu.boatrace2020.basics;

public class CustomMath {
    public static int sign(double a) {
        return (int) (a / Math.abs(a));
    }

    public static double signPow(double a, double b) {
        return sign(a) * Math.pow(a, b);
    }

    public static double signSqrt(double a) {
        return sign(a) * Math.sqrt(Math.abs(a));
    }

    public static double sawSin(double a) {
        double b = ((Math.toDegrees(a) % 360) + 360) % 360;
        if (b < 90) {
            return b / 90;
        } else if (b < 270) {
            return (180 - b) / 90;
        } else {
            return (b - 360) / 90;
        }
    }

    public static double sawCos(double a) {
        return sawSin(Math.toRadians(Math.toDegrees(a) - 90));
    }
}
