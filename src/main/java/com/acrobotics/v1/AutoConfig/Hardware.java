package com.acrobotics.v1.AutoConfig;

import com.acrobotics.v1.Simplify.SimpleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

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
 * @// TODO: 3/30/2026 Implement the rest of the device getter methods and Other useful methods getDriveDevices etc 
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
    public Hardware(SimpleOpMode opModeObject){
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


    /**
     * Gets a motor by its name from the verified list.
     * Returns null if the motor didn't pass the hardware scan.
     */
    public static DcMotor getMotor(String name) {
        HardwareDevice device = HardwareScanner.verifiedRegistry.get(name);
        if (device instanceof DcMotor) {
            return (DcMotor) device;
        }
        return null;
    }

    /**
     * Gets a servo by its name from the verified list.
     */
    public static Servo getServo(String name) {
        HardwareDevice device = HardwareScanner.verifiedRegistry.get(name);
        if (device instanceof Servo) {
            return (Servo) device;
        }
        return null;
    }
}