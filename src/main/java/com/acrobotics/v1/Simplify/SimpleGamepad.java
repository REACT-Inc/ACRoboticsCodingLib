package com.acrobotics.v1.Simplify;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * It would allow the gamepad to be turned simple just doing the most basic writing of eahc  button
 * EEXAmples
 * Motor.setPowerWhen(gamepad.x, 100);
 * Or some sort of simplication to where it is striaght forward for what it does
 * Ideally i think one parameter methods would be easier
 *
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class SimpleGamepad {

    protected boolean[] xPressed = {false, false};


    /**
     * THis would be the loop called when we loop everything in the opmode
     * @param pad1 gamepad1
     *
     * @param pad2 gamepad2
     */
    public void loop(Gamepad pad1, Gamepad pad2){
        xPressed[0] = pad1.x;
        xPressed[1] = pad2.x;

    }



}
