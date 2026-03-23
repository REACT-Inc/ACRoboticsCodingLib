package com.acrobotics.v1.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p><strong>File Content Manager</strong></p>
 * <p>
 * Handles the actual Reading and Writing of data.
 * Includes specialized tools for parsing XML Config files to find device names.
 * </p>
 *
 * @author Criddle
 * @version 2.0
 */
public class FileContent {

    private final InternalFile internalFile;

    public FileContent(InternalFile file) {
        this.internalFile = file;
    }

    /**
     * Writes string data to the file.
     * Overwrites existing content by default.
     *
     * @param data The string content to save.
     */
    public void writeToFile(String data) {
        // Ensure the folder exists before trying to write
        internalFile.ensureDirectoryExists();

        try (FileWriter writer = new FileWriter(internalFile.getFile(), false)) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// Inside FileContent.java

    public void appendToFile(String data) {
        internalFile.ensureDirectoryExists();
        // The 'true' in FileWriter constructor enables Append Mode
        try (FileWriter writer = new FileWriter(internalFile.getFile(), true)) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// Inside FileContent.java
    public void loadToFactory(RobotVariable factory) {
        if (!internalFile.exists()) return;

        String allText = readAll();
        String[] lines = allText.split("\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                factory.parseLine(line);
            }
        }
    }
    /**
     * Reads the file content into a single String.
     *
     * @return The file content, or an empty string if reading fails.
     */
    public String readAll() {
        if (!internalFile.exists()) return "";

        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(internalFile.getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return text.toString();
    }

    /**
     * <p><strong>XML Device Extractor</strong></p>
     * <p>
     * Scans the content of this file (assuming it is XML) and finds all
     * "name=" attributes. This is used to cross-reference with the HardwareMap.
     * </p>
     *
     * @return A list of device names found in this file.
     */
    public List<String> extractDeviceNamesFromXml() {
        List<String> devices = new ArrayList<>();
        String content = readAll();

        // Regex to find name="..." inside XML tags
        // This looks for: name="SOMETHING"
        Pattern pattern = Pattern.compile("name=\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String deviceName = matcher.group(1);

            // Filter out system names that aren't real hardware devices
            if (!deviceName.equals("Control Hub Portal") &&
                    !deviceName.equals("Control Hub") &&
                    !deviceName.equals("Expansion Hub") &&
                    !deviceName.equals("(embedded)")) {

                devices.add(deviceName);
            }
        }
        return devices;
    }
}