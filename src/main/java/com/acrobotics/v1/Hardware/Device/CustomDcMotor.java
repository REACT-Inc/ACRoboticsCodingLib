package com.acrobotics.v1.Hardware.Device;

import com.acrobotics.v1.RobotTrace;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

/**
 * Dc Motor extentions of extensitons
 * Now this needs more work
 * @// TODO: 3/30/2026 We need to work on this implementing alot of different control types like the flywheel mode etc
 * @// TODO: 3/30/2026  Additionally we need to implement drive motor command etc
 */
public class CustomDcMotor {

    /**
     * The Servo Instance
     * and Details for the for it
     */
    private final DcMotorEx dcMotor;
    private final int portNum;



    /**
     * Static stuff
     */
    private static ArrayList<CustomDcMotor> allMotors = new ArrayList<CustomDcMotor>();
    private static boolean motorsEnabled = true;

    /**
     * Makes this a object
     * @param motor the motor Hardware device object
     * @param portNumber the port 0-15
     */
    public CustomDcMotor(HardwareDevice motor, int portNumber){
        this.dcMotor = (DcMotorEx) motor;
        this.portNum = portNumber;
        allMotors.add(this);
    }




    /**
     * Sets the power of the motor
     * @param power the power
     */
    public void setPower(float power){
        if(motorsEnabled) {
            dcMotor.setPower(power);
        }else{
            RobotTrace.notify("WARNING MOTORS are currently not operating according to requirements");
        }
    }


    /**
     * Get the power currently set
     * @return the power
     */
    public double getPower(){
        return dcMotor.getPower();
    }

    /**
     * Stops the motor
     */
    public void stopDcMotor(){
        dcMotor.setMotorDisable();

    }



    /**
     * Stops all the motor in robot operation
     * Uses Lamba statement
     */
    public static void stopAllServos() {
        allMotors.forEach(CustomDcMotor::stopDcMotor);
    }

    /**
     * Toggles the motors on and off
     * @return the state of the motors enabled or not
     */
    public static boolean toggleDcMotorsEnabled(){
        motorsEnabled = !motorsEnabled;
        RobotTrace.notify("Motors are now: " + motorsEnabled);
        return motorsEnabled;
    }






}
