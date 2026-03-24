package com.acrobotics.v1.Enhancement;
public class Round {

    public enum Mode {
        NORMAL,  // 73 -> 70, 77 -> 80
        UP,      // 73 -> 80
        DOWN     // 77 -> 70
    }

    /**
     * @param value    The number to round
     * @param interval The snap point (e.g., 10, 0.1, 5)
     * @param mode     NORMAL, UP, or DOWN
     */
    public static double snappyRound(double value, double interval, Mode mode) {
        if (interval == 0) return value;

        switch (mode) {
            case UP:
                return Math.ceil(value / interval) * interval;
            case DOWN:
                return Math.floor(value / interval) * interval;
            case NORMAL:
            default:
                return Math.round(value / interval) * interval;
        }
    }
}