package com.acrobotics.v1.Exception;

/**
 * For when storage malforms
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class StorageDeviceMalfunction extends RuntimeException {
    public StorageDeviceMalfunction(String message) {
        super(message);
    }
}
