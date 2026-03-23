package com.acrobotics.v1.Exception.SimpleException;

/**
 * For when we init a motor type
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class SimpleMotorTypeInitError extends RuntimeException {
    public SimpleMotorTypeInitError(String message) {
        super(message);
    }
}
