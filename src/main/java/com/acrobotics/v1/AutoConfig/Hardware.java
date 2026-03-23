package com.acrobotics.v1.AutoConfig;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Maneges the whole config and hardware
 *
 * controlls when we scan etc the hardware and more
 * the brains for the autoconfig
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class Hardware {

    /**
     * This would init the hardwares with the opmode object so we can do everything we can do in the actual class here etc (I Hope atleast)
     * @param opModeObject the Class Object of the super
     */
    public Hardware(LinearOpMode opModeObject){
        //Scans
        HardwareScanner.beginScan(opModeObject);



    }
}
