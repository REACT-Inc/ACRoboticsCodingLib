package com.acrobotics.v1.Exception.SimpleException;

/**
 * For when we init a motor type
 */
public class SimpleMotorTypeInitError extends RuntimeException {
    public SimpleMotorTypeInitError(String message) {
        super(message);
    }
}
