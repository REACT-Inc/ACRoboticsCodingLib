package com.acrobotics.v1.File;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p><strong>RobotVariable</strong></p>
 * <p>
 * Automatically converts text strings into real Java Objects using Reflection.
 * It detects if the target is a Class or an Enum and handles the parameters accordingly.
 * </p>
 */
public class RobotVariable {

    // Allows us to use short names in the file (e.g., "Pose" instead of "org.firstinspires...")
    private static final Map<String, Class<?>> classMap = new HashMap<>();

    // Stores the objects we built so the OpMode can get them
    private final Map<String, Object> builtObjects = new HashMap<>();

    /**
     * Registers a class so the system recognizes its name.
     * Do this in your init()!
     */
    public static void registerClass(String shortName, Class<?> clazz) {
        classMap.put(shortName, clazz);
    }




    public static void mapToFile(FileContent content, Object obj, String displayName){
        registerClass(obj.getClass().getCanonicalName(), obj.getClass());
        content.writeToFile("%" + obj.getClass().getCanonicalName() + "%| " + displayName + "");
    }

    /**
     * Reads a formatted line, determines the class, and builds the object.
     * Format: %ClassName%| InstanceName: Param1,Param2,Param3;
     */
    public void parseLine(String line) {
        // Regex to extract: Type, Name, and Parameters
        // Matches: %Pose%| myPose: 1,1,1;
        Pattern pattern = Pattern.compile("%(.*?)%\\| (.*?): (.*?);");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String className = matcher.group(1).trim(); // "Pose"
            String instanceName = matcher.group(2).trim(); // "myPose"
            String paramString = matcher.group(3).trim(); // "1,1,1"

            try {
                Object obj = createObject(className, paramString);
                if (obj != null) {
                    builtObjects.put(instanceName, obj);
                }
            } catch (Exception e) {
                // Log error if file contains bad data
                System.err.println("AutoFactory Error on line: " + line);
                e.printStackTrace();
            }
        }
    }

    /**
     * The Magic: Uses Reflection to instantiate the class or Enum.
     */
    private Object createObject(String className, String paramString) throws Exception {
        Class<?> clazz = classMap.get(className);
        if (clazz == null) {
            System.err.println("Unknown Class: " + className + ". Did you register it?");
            return null;
        }

        // --- CASE 1: HANDLE ENUMS (e.g., Direction.REVERSE) ---
        if (clazz.isEnum()) {
            // For Enums, the "paramString" is just the value name (e.g., "REVERSE")
            // We use unchecked casting safely because we checked isEnum()
            return Enum.valueOf((Class<Enum>) clazz, paramString);
        }

        // --- CASE 2: HANDLE REGULAR CLASSES (e.g., Pose(1,1,1)) ---
        String[] rawParams = paramString.split(",");

        // Find a constructor that matches the number of parameters we have
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterTypes().length == rawParams.length) {

                // We found a matching constructor! Now convert the strings to the right types.
                Object[] convertedArgs = new Object[rawParams.length];
                Class<?>[] paramTypes = constructor.getParameterTypes();

                for (int i = 0; i < rawParams.length; i++) {
                    convertedArgs[i] = convertToType(paramTypes[i], rawParams[i].trim());
                }

                return constructor.newInstance(convertedArgs);
            }
        }

        System.err.println("No matching constructor found for " + className + " with " + rawParams.length + " params.");
        return null;
    }

    /**
     * Converts a String ("1.5") into the type the Constructor wants (double).
     */
    private Object convertToType(Class<?> type, String value) {
        if (type == int.class || type == Integer.class) return Integer.parseInt(value);
        if (type == double.class || type == Double.class) return Double.parseDouble(value);
        if (type == float.class || type == Float.class) return Float.parseFloat(value);
        if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
        if (type == String.class) return value;
        return value; // Fallback
    }

    /**
     * Retrieve the built object.
     */
    public <T> T get(String name) {
        return (T) builtObjects.get(name);
    }
}