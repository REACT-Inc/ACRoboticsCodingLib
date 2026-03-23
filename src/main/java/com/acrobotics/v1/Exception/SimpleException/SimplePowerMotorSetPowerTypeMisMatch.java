package com.acrobotics.v1.Exception.SimpleException;

/**
 * For when we set power to a servo
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class SimplePowerMotorSetPowerTypeMisMatch extends RuntimeException {
    public SimplePowerMotorSetPowerTypeMisMatch(String message) {
        super(message);
    }
}
