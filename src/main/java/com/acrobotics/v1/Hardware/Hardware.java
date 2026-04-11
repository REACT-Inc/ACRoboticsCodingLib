package com.acrobotics.v1.Hardware;

import com.acrobotics.v1.Hardware.Device.CustomDcMotor;
import com.acrobotics.v1.Hardware.Device.CustomServo;
import com.acrobotics.v1.MathEx;
import com.acrobotics.v1.Simplify.SimpleOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

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
 * @// TODO: 3/30/2026 We need the system that forces active config to a custom one we make!
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
    private static SimpleOpMode opmode;
    /**
     * This would init the hardwares with the opmode object so we can do everything we can do in the actual class here etc (I Hope atleast)
     * @param opModeObject the Class Object of the super
     */
    public Hardware(SimpleOpMode opModeObject){
        opmode = opModeObject;
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
    public static CustomDcMotor getMotor(String name) {
        HardwareDevice device = HardwareScanner.verifiedRegistry.get(name);
        if (device instanceof DcMotorEx) {
            return new CustomDcMotor(device, (int) MathEx.removeLetters(name));
        }
        return null;
    }







//    public static DcMotorEx[]

    /**
     * Gets a servo by its name from the verified list.
     *
     * @implNote  This gets the servo under that name It will be a Servo object but may be configured in Cr Mode
     * @param name The name of the servo EG (When automated config is enabled it will be (HUB_SERVO_PORT) In use it would be (CTRL_SERVO_0)
     * @return the servo device (Or Null if not found)
     * 
     * @// TODO: 3/30/2026 Ensure the int cast works as intendend
     */
    public static CustomServo getServo(String name) {
        HardwareDevice device = HardwareScanner.verifiedRegistry.get(name);
        if (device instanceof Servo) {
            /// The remove letters form name works for getting the port because they are named with the port num in the name
            return new CustomServo(device, (int) MathEx.removeLetters(name));

        }
        return null;
    }

    /**
     * Gets robot voltage
     * @return the voltage
     */
    public static double getRobotVoltage(){

        return opmode.hardwareMap.get(VoltageSensor.class, "voltageSensor").getVoltage();
    }

}