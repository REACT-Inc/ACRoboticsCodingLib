package com.acrobotics.v1.AutoConfig;

import java.util.HashMap;

/**
 * Handles the hwardware known device xml formatting
 *
 * this way we can catalog the hardware
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class HardwareNamespace {

    /**
     * Total Hardware devices
     */
    public enum HardwareNamespaces  {
        MOTOR_1,
        MOTOR_2,
        MOTOR_3,
        MOTOR_4,
        MOTOR_5,
        MOTOR_6,
        MOTOR_7,
        MOTOR_8
    };
    /**
     * THis is the map for the hardware devices we want in the config
     * @// TODO: 3/24/2026  These need to bew used below this  and auto added to one string soit is vaild xml
     */
    private final HashMap<HardwareNamespaces, String[]> hardware = new HashMap<HardwareNamespaces, String[]>() {
        {
            put(HardwareNamespaces.MOTOR_1, new String[]{"ctrl_hub_dcMotor_1", "goBILDA5203SeriesMotor"});
            put(HardwareNamespaces.MOTOR_2, new String[]{"ctrl_hub_dcMotor_2", "goBILDA5203SeriesMotor"});
            put(HardwareNamespaces.MOTOR_3, new String[]{"ctrl_hub_dcMotor_3", "goBILDA5203SeriesMotor"});
            put(HardwareNamespaces.MOTOR_4, new String[]{"ctrl_hub_dcMotor_4", "goBILDA5203SeriesMotor"});

            put(HardwareNamespaces.MOTOR_5, new String[]{"exp_hub_dcMotor_1", "goBILDA5203SeriesMotor"});
            put(HardwareNamespaces.MOTOR_6, new String[]{"exp_hub_dcMotor_2", "goBILDA5203SeriesMotor"});
            put(HardwareNamespaces.MOTOR_7, new String[]{"exp_hub_dcMotor_3", "goBILDA5203SeriesMotor"});
            put(HardwareNamespaces.MOTOR_8, new String[]{"exp_hub_dcMotor_4", "goBILDA5203SeriesMotor"});
        }
    };
}
