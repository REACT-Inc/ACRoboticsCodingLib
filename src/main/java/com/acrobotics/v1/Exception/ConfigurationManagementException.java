package com.acrobotics.v1.Exception;

/**
 * When a config error happems
 */
public class ConfigurationManagementException extends RuntimeException {
    public ConfigurationManagementException(String message) {
        super(message);
    }
}
