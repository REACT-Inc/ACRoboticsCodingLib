package com.acrobotics.v1.Exception.SimpleException;

/**
 * These are for the Simple Classes this way the error is more direct
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class SimplePositioningSystemMalfunction extends RuntimeException {
    public SimplePositioningSystemMalfunction(String message) {
        super(message);
    }
}
