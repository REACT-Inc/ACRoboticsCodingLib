package com.acrobotics.v1.Exception.SimpleException;

/**
 * These are for the Simple Classes this way the error is more direct
 */
public class SimpleSensorMalfunction extends RuntimeException {
    public SimpleSensorMalfunction(String message) {
        super(message);
    }
}
