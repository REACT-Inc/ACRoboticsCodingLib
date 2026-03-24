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


    /**
     * All the devices in the current configuration
     */
    private HashMap<String, HardwareDevice> configurationDevices = new HashMap<String, HardwareDevice>();
    /**
     * All the Devices that we know for sure exist in the world
     * THIS IS for devices we found when scanning 
     * @// TODO: 3/24/2026 This needs worked on and what is the parameters for the map 
     */
    private HashMap<String, HardwareDevice> activeDevices = new HashMap<String, HardwareDevice>();

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
