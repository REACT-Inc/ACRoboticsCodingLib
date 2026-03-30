package com.acrobotics.v1.AutoConfig;

import com.acrobotics.v1.Simplify.SimpleOpMode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cDevice;

/**
 * handles scanning the hardware for devices and relaying they exist
 *
 * <p>
 *     See the configs are stored in the xml in a folder we can manulplate that folder etc and everything
 *     But i dont believe theres a direct way to see the active configuration file
 *
 *
 *     You can do the same with the onBot Programs theres full access for it to delete itself actually
 *
 * </p>
 *
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class HardwareScanner {

    /**
     * Scansthe configs
     * @// TODO: 3/23/2026 We need to make it so it scans all configs and then determines what is vaild aswell as scanning devices connected
     * in the form it may be a dedicated program that actually scans not sure
     *
     * NEED to see actual overall affect in perfromancee
     * @param opMode the opmode
     */
    public static void beginScan(SimpleOpMode opMode){
        //TODO
    }


    /**
     * This would scan plugged in devices
     *
     * how this works is that our active config is a config that has everything filled up so theres somehing for every slot
     * Servos CR and normal can be driven using the Servo config (Setposition would make it turn contiunuiously Because it will never change from 0)
     * Motors we can run the motor at like 0.01 an still detect a change in encoder value because of the 6000 rpm (aslong as we dpnt use 6000)rpm motors
     * Then for the i2c Ports we assign every port 4 different sensors and check if any of those sensors on each port return anything for us we will use (LIDAR, Pinpoint, Colorv3, IMU)
     * Then Digital For now is always a touch sensor,
     * Also analog is unused
     *
     * THIS would scan the file for the config and access all devices in the config and try to use the hardware map to get them and then move or get singal to check if they exist
     *
     */
    public static void scanDevices(){
///        HardwareMapEx.getDevices()
        /// Eg foreach device check status (If i send move commands does it move), (does sensor change value or returning anything)
        /// By Default currently the digital channels are touch always so

    }
}
