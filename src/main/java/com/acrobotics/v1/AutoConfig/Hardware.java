package com.acrobotics.v1.AutoConfig;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import java.util.HashMap;

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



    private HashMap<String, HardwareDevice> configurationDevices = new HashMap<String, HardwareDevice>();

    /**
     * This would init the hardwares with the opmode object so we can do everything we can do in the actual class here etc (I Hope atleast)
     * @param opModeObject the Class Object of the super
     */
    public Hardware(LinearOpMode opModeObject){
        //Scans
        HardwareScanner.beginScan(opModeObject);



    }


    /**
     * gets the hasMap for the devices in our config
     * @return A Hashmap<String, HardwareDevice></String,>
     */
    public HashMap<String, HardwareDevice> getConfigurationDevices(){
        return configurationDevices;
    }




}
