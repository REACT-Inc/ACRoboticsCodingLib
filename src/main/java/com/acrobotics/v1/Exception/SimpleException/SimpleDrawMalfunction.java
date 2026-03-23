package com.acrobotics.v1.Exception.SimpleException;

/**
 * These are for the Simple Classes this way the error is more direct
 */
public class SimpleDrawMalfunction extends RuntimeException {
    public SimpleDrawMalfunction(String message) {
        super(message);
    }
}
