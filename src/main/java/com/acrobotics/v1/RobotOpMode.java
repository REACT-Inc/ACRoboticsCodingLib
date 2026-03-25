package com.acrobotics.v1;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * THis is th eadvanced opmode for us
 *
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public abstract class RobotOpMode extends OpMode {




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
        //This calls onInit in the subclass
        onInit();
    }

    /**
     * For you to override and use as the Init block in your code!
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
        onStop();
    }

    /**
     * You override this
     */
    public abstract void onStop();
}