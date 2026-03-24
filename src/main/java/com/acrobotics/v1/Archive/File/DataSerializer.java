package com.acrobotics.v1.Archive.File;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 * @Archived 3/24/2026
 *
 */
public class DataSerializer {

    private FileContent fileContent;

    // Store loaded objects here: Map<"RobotPose", ActualPoseObject>
    private Map<String, Object> loadedData = new HashMap<>();

    public DataSerializer(FileContent contentHandler) {
        this.fileContent = contentHandler;
    }

    // --- SAVING ---

    /**
     * Converts a Pose object into your custom string format and saves it.
     * Format: %Pose%| Name: 1,2,3;
     */
    public void savePose(String name, Pose pose) {
        String data = String.format("%%Pose%%| %s: %s;\n", name, pose.toString());
        fileContent.appendToFile(data); // You need to add appendToFile to FileContent or use read/write
    }

    /**
     * Generic saver for Enums or Primitives
     */
    public void saveSetting(String type, String name, Object value) {
        String data = String.format("%%%s%%| %s: %s;\n", type, name, value.toString());
        fileContent.appendToFile(data);
    }

    // --- LOADING & PARSING ---

    /**
     * Reads the file, parses every line, and rebuilds the Java Objects.
     */
    public void loadAndParse() {
        String fullText = fileContent.readAll();
        String[] lines = fullText.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            parseLine(line);
        }
    }

    /**
     * The Logic Engine: Turns text back into Objects
     */
    private void parseLine(String line) {
        try {
            // Regex to rip apart the string: %Type%| Name: Value;
            // Group 1: Type, Group 2: Name, Group 3: Value
            Pattern pattern = Pattern.compile("%(.*?)%\\| (.*?): (.*?);");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String type = matcher.group(1); // e.g., "Pose"
                String name = matcher.group(2); // e.g., "RobotPose"
                String value = matcher.group(3); // e.g., "1,1,1" OR "REVERSE"

                Object constructedObject = buildObject(type, value);

                if (constructedObject != null) {
                    loadedData.put(name, constructedObject);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to parse line: " + line);
        }
    }

    /**
     * The Factory: "Oh, it's a Pose? Let me split the commas and new it up."
     */
    private Object buildObject(String type, String valueStr) {
        switch (type) {
            case "Pose":
                String[] parts = valueStr.split(",");
                return new Pose(
                        Double.parseDouble(parts[0]),
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2])
                );

            case "Direction":
                return DcMotorSimple.Direction.valueOf(valueStr);

            case "ZeroPowerBehavior":
                return DcMotor.ZeroPowerBehavior.valueOf(valueStr);

            case "boolean":
                return Boolean.parseBoolean(valueStr);

            case "double":
                return Double.parseDouble(valueStr);

            default:
                return valueStr; // Return as String if unknown
        }
    }

    /**
     * Retrieve a reconstructed object safely
     */
    public Object get(String name) {
        return loadedData.get(name);
    }

    // Helper for specific type retrieval to avoid casting in OpMode
    public Pose getPose(String name) {
        return (Pose) loadedData.get(name);
    }
}