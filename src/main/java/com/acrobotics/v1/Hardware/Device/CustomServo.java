package com.acrobotics.v1.Hardware.Device;

import com.acrobotics.v1.RobotTrace;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

/**
 * A place to contorl servos easily customly
 *
 * You can tell normal to cr servos apart by if there position changes
 *Alternativly we could have it based off if the you use setpower or position
 */
public class CustomServo {

    /**
     * The Servo Instance
     * and Details for the for it
     */
    private final Servo servo;
    private final int portNum;


    /**
     * The Type of servo we drive (By Default its normal)
     *
     */
    public enum ServoType  {CR_SERVO, SERVO};
    private ServoType type = ServoType.SERVO;

    /**
     * Servo stats
     */
    private float currentPower = 0;
    private float servoMiddlePosition = 0.5f;

    /**
     * Static stuff
     */
    private static ArrayList<CustomServo> allServos = new ArrayList<CustomServo>();
    private static boolean servoEnabled = true;

    /**
     * Makes this a object
     * @param servo the servo Hardware device object
     * @param portNumber the port 0-15
     * @param detectedType the servo type CRSERVO or servo
     */
    public CustomServo(HardwareDevice servo, int portNumber, ServoType detectedType){
        this.servo = (Servo) servo;
        this.portNum = portNumber;
        this.type = detectedType;
        allServos.add(this);
    }

    /**
     * Makes this a object
     * @param servo the servo Hardware device object
     * @param portNumber the port 0-15
     */
    public CustomServo(HardwareDevice servo, int portNumber){
        this.servo = (Servo) servo;
        this.portNum = portNumber;
        allServos.add(this);
    }


    /**
     * @// TODO: 3/30/2026 Make some cool calculation to get a base of the same speed etc
     * Uses set position to make servo move
     * 0.5 is base for middle but that apptally isnt always true
     * @param power the power
     */
    public void setPower(float power){
        if(servoEnabled) {
            servo.setPosition(servoMiddlePosition + power);
            currentPower = power;
        }else{
            RobotTrace.notify("WARNING Servos are currently not operating according to requirements");
        }
    }

    /**
     * sets position of the servo
     * @param position the new pos
     */
    public void setPosition(float position){
        if(servoEnabled) {
            servo.setPosition(position);
        }else{
            RobotTrace.notify("WARNING Servos are currently not operating according to requirements");
        }
    }

    /**
     * Each servo may have a different sligtly center normally around 0.5
     * this allows you to change it if it different
     * @param newPosition the new pos
     */
    public void setServoMiddlePosition(float newPosition){
        servoMiddlePosition = newPosition;
    }

    /**
     * Gets the position of the servo
     * @return double
     */
    public double getPosition(){
        return servo.getPosition();
    }

    /**
     * Get the power currently set
     * @return the power
     */
    public double getPower(){
        return currentPower;
    }

    /**
     * Stops the servo
     */
    public void stopServo(){
        servo.setPosition(0);
    }



    /**
     * Stops all the servos in robot operation
     * Uses Lamba statement
     */
    public static void stopAllServos() {
        allServos.forEach(CustomServo::stopServo);
    }

    /**
     * Toggles the servos on and off
     * @return the state of the servos enabled or not
     */
    public static boolean toggleServosEnabled(){
        servoEnabled = !servoEnabled;
        RobotTrace.notify("Servos are now: " + servoEnabled);
        return servoEnabled;
    }






}
