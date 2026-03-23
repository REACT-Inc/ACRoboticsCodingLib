package com.acrobotics.v1.Exception.SimpleException;

/**
 * These are for the Simple Classes this way the error is more direct
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class SimpleTelemetryMalfunction extends RuntimeException {
    public SimpleTelemetryMalfunction(String message) {
        super(message);
    }
}
