//package com.acrobotics.v1.Simplify;
//
//import com.qualcomm.robotcore.hardware.Gamepad;
//
///**
// * It would allow the gamepad to be turned simple just doing the most basic writing of eahc  button
// * EEXAmples
// * Motor.setPowerWhen(gamepad.x, 100);
// * Or some sort of simplication to where it is striaght forward for what it does
// * Ideally i think one parameter methods would be easier
// *
// * @// TODO: 3/31/2026  figure if this class is even nessary
// *
// * @author Cayden Riddle
// * @version DEV.1
// *
// */
//public final class SimpleGamepad {
//    /**
//     * Shape Buttons [Pad1, Pad2]
//     */
//    public static boolean[] trianglePressed = {false, false};
//    public static boolean[] crossPressed = {false, false};
//    public static boolean[] circlePressed = {false, false};
//    public static boolean[] squarePressed = {false, false};
//
//    /**
//     * Arrow Buttons [Pad1, Pad2]
//     */
//    public static boolean[] dpadUpPressed = {false, false};
//    public static boolean[] dpadDownPressed = {false, false};
//    public static boolean[] dpadRightPressed = {false, false};
//    public static boolean[] dpadLeftPressed = {false, false};
//
//
//    /**
//     * THis would be the loop called when we loop everything in the opmode
//     * @param pad1 gamepad1
//     * @param pad2 gamepad2
//     * @// TODO: 3/24/2026 add rest of gamepad
//     */
//    public void loop(Gamepad pad1, Gamepad pad2){
//        trianglePressed[0] = pad1.triangle;
//        trianglePressed[1] = pad2.triangle;
//
//        crossPressed[0] = pad1.cross;
//        crossPressed[1] = pad2.cross;
//
//        circlePressed[0] = pad1.circle;
//        circlePressed[1] = pad2.circle;
//
//        squarePressed[0] = pad1.square;
//        squarePressed[1] = pad2.square;
//
//
//
//        dpadUpPressed[0] = pad1.dpad_up;
//        dpadUpPressed[1] = pad2.dpad_up;
//
//        dpadDownPressed[0] = pad1.dpad_down;
//        dpadDownPressed[1] = pad2.dpad_down;
//
//        dpadLeftPressed[0] = pad1.dpad_left;
//        dpadLeftPressed[1] = pad2.dpad_left;
//
//        dpadRightPressed[0] = pad1.dpad_right;
//        dpadRightPressed[1] = pad2.dpad_right;
//
//
//    }
//
//
//
//}
