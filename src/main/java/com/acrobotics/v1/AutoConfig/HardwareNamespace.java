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
 *
 * @// TODO: 3/26/2026 Implement LED indicator in Digital Channels
 *
 */
public class HardwareNamespace {

    /**
     * Total Hardware devices
     */
    public enum HardwareNamespaces  {
        MOTOR_1, MOTOR_2, MOTOR_3, MOTOR_4,
        MOTOR_5, MOTOR_6, MOTOR_7, MOTOR_8,

        /**
         * Digital Ports contain 2 Channels
         */
        DIGITAL_1, DIGITAL_2,
        DIGITAL_3, DIGITAL_4,
        DIGITAL_5, DIGITAL_6,
        DIGITAL_7, DIGITAL_8,
        DIGITAL_9, DIGITAL_10,
        DIGITAL_11, DIGITAL_12,
        DIGITAL_13, DIGITAL_14,
        DIGITAL_15, DIGITAL_16,

        ANALOG_1, ANALOG_2,
        ANALOG_3, ANALOG_4,

        ANALOG_5, ANALOG_6,
        ANALOG_7, ANALOG_8,

        /**
         * Per each i2c Port(BUS) you can have as many things assigned there as you want they all communicate over same wire at
         * different Addresses then it works
         * Some communicate at the same address but the current 4 i Selected will work together noProb
         */
        I2C_BUS1_1, I2C_BUS1_2, I2C_BUS1_3, I2C_BUS1_4,
        I2C_BUS2_1, I2C_BUS2_2, I2C_BUS2_3, I2C_BUS2_4,
        I2C_BUS3_1, I2C_BUS3_2, I2C_BUS3_3, I2C_BUS3_4,
        I2C_BUS4_1, I2C_BUS4_2, I2C_BUS4_3, I2C_BUS4_4,

        I2C_BUS5_1, I2C_BUS5_2, I2C_BUS5_3, I2C_BUS5_4,
        I2C_BUS6_1, I2C_BUS6_2, I2C_BUS6_3, I2C_BUS6_4,
        I2C_BUS7_1, I2C_BUS7_2, I2C_BUS7_3, I2C_BUS7_4,
        I2C_BUS8_1, I2C_BUS8_2, I2C_BUS8_3, I2C_BUS8_4,


        SERVO_1, SERVO_2, SERVO_3, SERVO_4, SERVO_5, SERVO_6,
        SERVO_7, SERVO_8, SERVO_9, SERVO_10, SERVO_11, SERVO_12
    };
    /**
     * THis is the map for the hardware devices we want in the config
     * @// TODO: 3/24/2026  These need to bew used below this  and auto added to one string soit is vaild xml
     */
    private final HashMap<HardwareNamespaces, String[]> hardware = new HashMap<HardwareNamespaces, String[]>() {
        {
            put(HardwareNamespaces.MOTOR_1,         new String[]{"ctrl_hub_dcMotor_1",          "goBILDA5203SeriesMotor"            });
            put(HardwareNamespaces.MOTOR_2,         new String[]{"ctrl_hub_dcMotor_2",          "goBILDA5203SeriesMotor"            });
            put(HardwareNamespaces.MOTOR_3,         new String[]{"ctrl_hub_dcMotor_3",          "goBILDA5203SeriesMotor"            });
            put(HardwareNamespaces.MOTOR_4,         new String[]{"ctrl_hub_dcMotor_4",          "goBILDA5203SeriesMotor"            });

            put(HardwareNamespaces.DIGITAL_1,       new String[]{"ctrl_hub_digital_1",          "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_2,       new String[]{"ctrl_hub_digital_2",          "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_3,       new String[]{"ctrl_hub_digital_3",          "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_4,       new String[]{"ctrl_hub_digital_4",          "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_5,       new String[]{"ctrl_hub_digital_5",          "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_6,       new String[]{"ctrl_hub_digital_6",          "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_7,       new String[]{"ctrl_hub_digital_7",          "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_8,       new String[]{"ctrl_hub_digital_8",          "TouchSensor"                       });

            put(HardwareNamespaces.ANALOG_1,        new String[]{"ctrl_hub_analog_1",           "Floodgate Power Switch"            });
            put(HardwareNamespaces.ANALOG_2,        new String[]{"ctrl_hub_analog_2",           "Floodgate"                         });
            put(HardwareNamespaces.ANALOG_3,        new String[]{"ctrl_hub_analog_3",           "Floodgate Power Switch"            });
            put(HardwareNamespaces.ANALOG_4,        new String[]{"ctrl_hub_analog_4",           "Floodgate"                         });

            put(HardwareNamespaces.I2C_BUS1_1,      new String[]{"ctrl_hub_i2c_bus1_1",         "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS1_2,      new String[]{"ctrl_hub_i2c_bus1_2",         "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS1_3,      new String[]{"ctrl_hub_i2c_bus1_3",         "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS1_4,      new String[]{"ctrl_hub_i2c_bus1_4",         "PinPoint",         "0x31"          });

            put(HardwareNamespaces.I2C_BUS2_1,      new String[]{"ctrl_hub_i2c_bus2_1",         "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS2_2,      new String[]{"ctrl_hub_i2c_bus2_2",         "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS2_3,      new String[]{"ctrl_hub_i2c_bus2_3",         "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS2_4,      new String[]{"ctrl_hub_i2c_bus2_4",         "PinPoint",         "0x31"          });

            put(HardwareNamespaces.I2C_BUS3_1,      new String[]{"ctrl_hub_i2c_bus3_1",         "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS3_2,      new String[]{"ctrl_hub_i2c_bus3_2",         "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS3_3,      new String[]{"ctrl_hub_i2c_bus3_3",         "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS3_4,      new String[]{"ctrl_hub_i2c_bus3_4",         "PinPoint",         "0x31"          });

            put(HardwareNamespaces.I2C_BUS4_1,      new String[]{"ctrl_hub_i2c_bus4_1",         "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS4_2,      new String[]{"ctrl_hub_i2c_bus4_2",         "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS4_3,      new String[]{"ctrl_hub_i2c_bus4_3",         "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS4_4,      new String[]{"ctrl_hub_i2c_bus4_4",         "PinPoint",         "0x31"          });

            put(HardwareNamespaces.SERVO_1,         new String[]{"ctrl_hub_servo_1",            "Servo"                             });
            put(HardwareNamespaces.SERVO_2,         new String[]{"ctrl_hub_servo_2",            "Servo"                             });
            put(HardwareNamespaces.SERVO_3,         new String[]{"ctrl_hub_servo_3",            "Servo"                             });
            put(HardwareNamespaces.SERVO_4,         new String[]{"ctrl_hub_servo_4",            "Servo"                             });
            put(HardwareNamespaces.SERVO_5,         new String[]{"ctrl_hub_servo_5",            "Servo"                             });
            put(HardwareNamespaces.SERVO_6,         new String[]{"ctrl_hub_servo_6",            "Servo"                             });



            put(HardwareNamespaces.MOTOR_5,         new String[]{"exp_hub_dcMotor_1",           "goBILDA5203SeriesMotor"            });
            put(HardwareNamespaces.MOTOR_6,         new String[]{"exp_hub_dcMotor_2",           "goBILDA5203SeriesMotor"            });
            put(HardwareNamespaces.MOTOR_7,         new String[]{"exp_hub_dcMotor_3",           "goBILDA5203SeriesMotor"            });
            put(HardwareNamespaces.MOTOR_8,         new String[]{"exp_hub_dcMotor_4",           "goBILDA5203SeriesMotor"            });

            put(HardwareNamespaces.DIGITAL_9,       new String[]{"exp_hub_digital_1",           "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_10,      new String[]{"exp_hub_digital_2",           "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_11,      new String[]{"exp_hub_digital_3",           "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_12,      new String[]{"exp_hub_digital_4",           "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_13,      new String[]{"exp_hub_digital_5",           "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_14,      new String[]{"exp_hub_digital_6",           "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_15,      new String[]{"exp_hub_digital_7",           "TouchSensor"                       });
            put(HardwareNamespaces.DIGITAL_16,      new String[]{"exp_hub_digital_8",           "TouchSensor"                       });

            put(HardwareNamespaces.ANALOG_5,        new String[]{"exp_hub_analog_1",            "floodgate"                         });
            put(HardwareNamespaces.ANALOG_6,        new String[]{"exp_hub_analog_2",            "floodgate"                         });
            put(HardwareNamespaces.ANALOG_7,        new String[]{"exp_hub_analog_3",            "floodgate"                         });
            put(HardwareNamespaces.ANALOG_8,        new String[]{"exp_hub_analog_4",            "floodgate"                         });

            put(HardwareNamespaces.I2C_BUS5_1,      new String[]{"exp_hub_i2c_bus1_1",          "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS5_2,      new String[]{"exp_hub_i2c_bus1_2",          "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS5_3,      new String[]{"exp_hub_i2c_bus1_3",          "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS5_4,      new String[]{"exp_hub_i2c_bus1_4",          "PinPoint",         "0x31"          });

            put(HardwareNamespaces.I2C_BUS6_1,      new String[]{"exp_hub_i2c_bus2_1",          "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS6_2,      new String[]{"exp_hub_i2c_bus2_2",          "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS6_3,      new String[]{"exp_hub_i2c_bus2_3",          "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS6_4,      new String[]{"exp_hub_i2c_bus2_4",          "PinPoint",         "0x31"          });

            put(HardwareNamespaces.I2C_BUS7_1,      new String[]{"exp_hub_i2c_bus3_1",          "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS7_2,      new String[]{"exp_hub_i2c_bus3_2",          "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS7_3,      new String[]{"exp_hub_i2c_bus3_3",          "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS7_4,      new String[]{"exp_hub_i2c_bus3_4",          "PinPoint",         "0x31"          });

            put(HardwareNamespaces.I2C_BUS8_1,      new String[]{"exp_hub_i2c_bus4_1",          "Lidar",            "0x29"          });
            put(HardwareNamespaces.I2C_BUS8_2,      new String[]{"exp_hub_i2c_bus4_2",          "ColorSensorV3",    "0x52"          });
            put(HardwareNamespaces.I2C_BUS8_3,      new String[]{"exp_hub_i2c_bus4_3",          "IMU",              "0x8"           });
            put(HardwareNamespaces.I2C_BUS8_4,      new String[]{"exp_hub_i2c_bus4_4",          "PinPoint",         "0x31"          });

            put(HardwareNamespaces.SERVO_7,         new String[]{"exp_hub_servo_1",             "Servo"                             });
            put(HardwareNamespaces.SERVO_8,         new String[]{"exp_hub_servo_2",             "Servo"                             });
            put(HardwareNamespaces.SERVO_9,         new String[]{"exp_hub_servo_3",             "Servo"                             });
            put(HardwareNamespaces.SERVO_10,        new String[]{"exp_hub_servo_4",             "Servo"                             });
            put(HardwareNamespaces.SERVO_11,        new String[]{"exp_hub_servo_5",             "Servo"                             });
            put(HardwareNamespaces.SERVO_12,        new String[]{"exp_hub_servo_6",             "Servo"                             });

        }
    };



}
