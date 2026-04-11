package com.acrobotics.v1.Simplify;

import com.acrobotics.v1.Hardware.Device.CustomDcMotor;
import com.acrobotics.v1.Hardware.Device.CustomDevice;
import com.acrobotics.v1.Hardware.Device.CustomServo;
import com.acrobotics.v1.Hardware.Hardware;
import com.acrobotics.v1.Hardware.HardwareScanner;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * A Simple Op mode extension to the main linear op mode so we can more direclty affect the programs
 *
 *
 * You extend this class  when making your op mode!
 *
 * // TODO: 3/31/2026 implement variables for all decives here etc this way we can do all that stufff  
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public abstract class SimpleOpMode extends OpMode {

    public CustomDcMotor motor0, motor1, motor2, motor3, motor4, motor5, motor6, motor7;
    public boolean squarePressed;
    public boolean squarePressed(CustomDevice device, double power){
        if(squarePressed){
            if(device instanceof CustomDcMotor){
                ((CustomDcMotor) device).setPower((float) power);
            }
            if(device instanceof CustomServo){
                ((CustomServo) device).setPower((float) power);
            }
        }else{
            if(device instanceof CustomDcMotor){
                ((CustomDcMotor) device).setPower(0);
            }
            if(device instanceof CustomServo){
                ((CustomServo) device).setPower(0);
            }
        }
        return squarePressed;
    }




    /**
     * Sense if we override the exisirng methods they lose superclas functions without directly using them we want to keep there existence
     *
     * @// TODO: 3/24/2026 Make this work fully in the init It needs to init our hardware system etc
     * Really it shouldnt allow the user to init intill we have completed preChecks
     */

    /**
     * Not for you to override
     */
    @Override
    public final void init() {
        // Hardware Scan begin
        HardwareScanner.beginScan(this);
        /// TODO make the instance variables for this opmode for the motors and servos be init here
        this.motor0 = Hardware.getMotor("motor0");
        this.motor1 = Hardware.getMotor("motor1");
        this.motor2 = Hardware.getMotor("motor2");
        this.motor3 = Hardware.getMotor("motor3");
//
//        // 2. Access your motors easily
//        DcMotor frontLeft = Hardware.getMotor("front_left");
//
//        // 3. Safety Check: If it wasn't plugged in, frontLeft is null
//        if (frontLeft != null) {
//            frontLeft.setPower(1.0);
//        }
        //This calls onInit in the subclass
        onInit();
    }

    /**
     * Use this method to make stuff happen when we Init the robot!
     *
     */
    public abstract void onInit();


    /**
     *
     * Not for you to override
     */
    @Override
    public final void init_loop() {
        //This calls onInitLoop in the subclass
        onInit_loop();
    }

    /**
     * For you to override for a initloop
     */
    public  void onInit_loop(){
        /// They override this
    }


    /**
     * Not for you to override
     */
    @Override
    public final void start() {
        //This calls Onstart in the subclass
        onStart();
    }

    /**
     * For you to override when the start btn is pressed
     */
    public abstract void onStart();



    /**
     * Not for you to override
     */
    @Override
    public final void loop() {
        //This calls theloop in the subclass
        onStart_loop();
    }

    /**
     * For you to override for the loop while started
     */
    public abstract void onStart_loop();


    /**
     * Theres no reason they need to override this
     */
    @Override
    public final void stop() {
        /// TODO stop stuff

    }
    /**
     * Sleeps for the given amount of milliseconds, or until the thread is interrupted (which usually
     * indicates that the OpMode has been stopped).
     * <p>This is simple shorthand for {@link Thread#sleep(long) sleep()}, but it does not throw {@link InterruptedException}.</p>
     *
     * @param milliseconds amount of time to sleep, in milliseconds
     * @see Thread#sleep(long)
     */
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
