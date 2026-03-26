package com.acrobotics.v1.AutoConfig;

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
    public static void beginScan(LinearOpMode opMode){
        //TODO
    }


    /**
     * This would scan plugged in devices
     *
     * how this works is that our active config is a config that has everything filled up so theres somehing for every slot
     * servos when plugged in (I blieve return a position (CHECK FOR CRSERVO))
     * motors with encoders would aswell
     *
     * therfor sensing is possible
     *
     * sensors its a bit different not sure how yet but
     * i2c devices we can get the address but not sure if we can even if inproper mismatch like if its a distance sensor and config as color
     *
     * BUT we could put every type of sesnor as one port and solve that
     *
     * Digital i have no clue for differenting it right now
     * Analog is just voltage and i dont even know what sensors use this really besies the laser distance gobilda
     */
    public static void scanDevices(){
//        HardwareMapEx.getDevices()
        /// Eg foreach device check status (If i send move commands does it move), (does sensor change value or returning anything)
        /// By Default currently the digital channels are touch always so

    }
}
