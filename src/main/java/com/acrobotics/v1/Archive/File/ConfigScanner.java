package com.acrobotics.v1.Archive.File;

import android.os.Environment;
import com.qualcomm.robotcore.hardware.HardwareMap;
import java.io.File;
import java.util.List;

/**
 * <p><strong>Config Scanner System</strong></p>
 * <p>
 * This class performs the "Handshake" logic.
 * 1. It scans the storage for all XML files.
 * 2. It parses them to list their required devices.
 * 3. It checks the current OpMode's HardwareMap to see which devices are physically present/configured.
 * 4. It returns the filename of the config that matches the robot.
 * </p>
 * * @author Criddle
 *
 *
 *
 *
 *
 * 3/24/2026 Rememertive Notes
 * The exploration project resulted in new discoverys for us in the sense of  being able to locate the active config and change it
 * including if the device is mapped
 *
 *
 * The other things this resulted in in other classes for dataserlization for logging only
 * and a easy way to put variables in a file and store them etc make them editable
 *
 *
 * @Archived 3/24/2026
 */
public class ConfigScanner {

    private HardwareMap hardwareMap;
    private String searchPath;

    public ConfigScanner(HardwareMap hwMap) {
        this.hardwareMap = hwMap;
        this.searchPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FIRST/settings"; // Default FTC config path
    }

//    <Robot type="FirstInspires-FTC">
//<Webcam name="Webcam 1" serialNumber="7E9B88DF"/>
//<LynxUsbDevice name="Control Hub Portal" serialNumber="(embedded)" parentModuleAddress="173">
//<LynxModule name="Expansion Hub 2" port="2">
//<goBILDA5201SeriesMotor name="H1" port="0"/>
//<goBILDA5201SeriesMotor name="H2" port="1"/>
//<goBILDA5201SeriesMotor name="VS1" port="2"/>
//<goBILDA5201SeriesMotor name="VS2" port="3"/>
//</LynxModule>
//<LynxModule name="Control Hub" port="173">
//<goBILDA5202SeriesMotor name="FL" port="0"/>
//<goBILDA5202SeriesMotor name="BL" port="1"/>
//<goBILDA5202SeriesMotor name="FR" port="2"/>
//<goBILDA5202SeriesMotor name="BR" port="3"/>
//<Servo name="HS" port="0"/>
//<Servo name="WR" port="1"/>
//<Servo name="GR" port="2"/>
//<ControlHubImuBHI260AP name="imu" port="0" bus="0"/>
//</LynxModule>
//</LynxUsbDevice>
//</Robot>

    /**
     * The main method to find which config file is currently active on the robot.
     * * @return The name of the matching config file (e.g., "CompetitionBot.xml"), or "Unknown" if none match.
     */
    public String identifyActiveConfig() {
        File folder = new File(searchPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) return "No Configs Found";

        String bestMatch = "Unknown";
        int maxDevicesMatched = -1;

        // 1. Go into every file
        for (File file : listOfFiles) {
            // Only check XML files
            if (file.isFile() && file.getName().endsWith(".xml")) {

                // 2. Get devices from the XML
                InternalFile internalFile = new InternalFile(file.getParent(), file.getName());
                FileContent content = new FileContent(internalFile);
                List<String> xmlDevices = content.extractDeviceNamesFromXml();

                // 3. Check if this file is valid for the current robot
                if (isConfigValid(xmlDevices)) {
                    // If multiple configs are valid (e.g. "BaseBot" and "FullBot"),
                    // the one with MORE devices defined is likely the specific one.
                    if (xmlDevices.size() > maxDevicesMatched) {
                        maxDevicesMatched = xmlDevices.size();
                        bestMatch = file.getName();
                    }
                }
            }
        }
        return bestMatch;
    }

    /**
     * Helper: Checks each device in the list against the HardwareMap.
     */
    private boolean isConfigValid(List<String> deviceNames) {
        if (deviceNames.isEmpty()) return false;

        for (String name : deviceNames) {
            // We check if the device exists in the hardware map.
            // HardwareMap.get() usually throws an exception if not found,
            // but we can check the names directly from the map to be safe/faster.
            if (!deviceExistsInMap(name)) {
                return false; // This config requires a device we don't have!
            }
        }
        return true; // All devices in this XML exist in our HardwareMap
    }

    /**
     * Checks if a specific name exists in any of the hardware map device lists.
     */
    private boolean deviceExistsInMap(String name) {
        // This is a "cool" robust way to check without crashing
        // We iterate over all device types in the map
        for (HardwareMap.DeviceMapping<?> mapping : hardwareMap.allDeviceMappings) {
            if (mapping.contains(name)) {
                return true;
            }
        }
        return false;
    }
}