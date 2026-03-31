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
public class CustomDcMotor extends  CustomDevice{

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




    private boolean toggled = true;

    private boolean flipped = false;
    private float power = 0;

    /**
     * This tracks rotations
     * Element 0 is the forward rotations
     * Element 1 is the backwards rotations
     */
    private double[] rotations = {0,0};

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
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor setPower(float power){
        if(motorsEnabled) {
            this.power = power;
            dcMotor.setPower(power);
        }else{
            RobotTrace.notify("WARNING MOTORS are currently not operating according to requirements");
        }
        return this;
    }


    /**
     * Get the power currently set
     * @return the power
     */
    public double getPower(){
        return dcMotor.getPower();
    }

    /**
     * Gets the total rotations of the motor
     * @// TODO: 3/31/2026 We need to make this work aswell
     * @return the rotations
     */
    public double getTotalRotations(){
        return rotations[0] + rotations[1];
    }

    /**
     * Returns the rotations forwards from the starting position
     * @return the rotations fowards
     * @implNote For example if you rotate 5 forward and  3 back then this would return 2
     */
    public double getRotations(){
        return rotations[0] - rotations[1];
    }
    /**
     * Powers a motor when the theWhen Variable is true at power
     * @param theWhen control variable
     * @param power the power set to
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor powerWhen(boolean theWhen, double power){
        if(theWhen){
            setPower((float)power);
        }else{
            setPower(0);
        }
        return this;
    }
    //TODO
    public CustomDcMotor setPowerDynamically(){
        return this;
    }


    /**
     * Powers a motor when the theWhen Variable is true at power
     * @param theWhen control variable
     * @param power the power set to
     * @param defaultPower the power set when its not true
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor powerWhen(boolean theWhen, double power, double defaultPower){
        if(theWhen){
            setPower((float)power);
        }else{
            setPower((float) defaultPower);
        }
        return this;
    }

    /**
     * Stops the motor temporarily
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor stop(){
        dcMotor.setPower(0);
        return this;
    }

    /**
     * Toggles the motor on and off based off toggler variable
     * @param toggler a boolean for when the motor should be toggled
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor toggle(boolean toggler){
        if(toggled){
            /// Normal
            if(toggler){
                /// When the btn pressed
                setPower(power);
            }else{
                /// When its not pressed
                setPower(0);
            }
        }else{
            /// Reversed
            if(toggler){
                /// When the btn pressed
                setPower(0);
            }else{
                /// When its not pressed
                setPower(power);
            }
        }
        return this;
    }

    /**
     * Flips the on and off state of the toggle method
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor flip(){
        this.toggled = !toggled;
        return this;
    }



    /**
     * Rotates the motor
     * @param rotations of times
     * @// TODO: 3/31/2026 Make the rotation system work
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor rotate(double rotations){
        return this;
    }


    /**
     * Completely Disables motors untill you call start()
     *
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor forceStop(){
        dcMotor.setMotorDisable();
        return this;
    }

    /**
     * After forcestop you must start the motor again
     * @return A instance of this class (For Daisy Chaining)
     */
    public CustomDcMotor start(){
        dcMotor.setMotorEnable();
        return this;
    }



    /**
     * Stops all motors Temporally
     */
    public static void stopAllMotors() {
        allMotors.forEach(CustomDcMotor::stop);
    }

    /**
     * Toggles all motors disabled or enabled
     * @return if they were disabled or enabled
     */
    public static boolean toggleMotorsEnabled(){
        motorsEnabled = !motorsEnabled;
        RobotTrace.notify("Motors are now: " + motorsEnabled);
        if(motorsEnabled){
            allMotors.forEach(CustomDcMotor::forceStop);
        }else{
            allMotors.forEach(CustomDcMotor::start);
        }
        return motorsEnabled;
    }






}
