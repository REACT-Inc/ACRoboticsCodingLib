package com.acrobotics.v1.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 1. Keep this annotation alive while the app is running
@Retention(RetentionPolicy.RUNTIME)
// 2. Only allow this to be attached to variables (fields)
@Target(ElementType.FIELD)
public @interface StorageDeviceAllocatedMemory {
    /// TODO figure how annotation works cuz they seem cool


    // You can add properties here, like a default name or key
    String key() default "";
}