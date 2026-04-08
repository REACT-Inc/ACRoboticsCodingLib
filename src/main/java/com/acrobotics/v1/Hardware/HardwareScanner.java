package com.acrobotics.v1.Hardware;

import com.acrobotics.v1.Simplify.SimpleOpMode;

import android.util.Xml;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.xmlpull.v1.XmlPullParser;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
/**
 * handles scanning the hardware for devices and relaying they exist
 *
 * <p>
 *     See the configs are stored in the xml in a folder we can manulplate that folder etc and everything
 *     But i dont believe theres a direct way to see the active configuration file
 *
 *
 *     You can do the same with the onBot Programs theres full access for it to delete itself actually
 *
 * </p>
 *
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 *
 * This would scan plugged in devices
 *
 * how this works is that our active config is a config that has everything filled up so theres somehing for every slot
 * Servos CR and normal can be driven using the Servo config (Setposition would make it turn contiunuiously Because it will never change from 0)
 * Motors we can run the motor at like 0.01 an still detect a change in encoder value because of the 6000 rpm (aslong as we dpnt use 6000)rpm motors
 * Then for the i2c Ports we assign every port 4 different sensors and check if any of those sensors on each port return anything for us we will use (LIDAR, Pinpoint, Colorv3, IMU)
 * Then Digital For now is always a touch sensor,
 * Also analog is unused
 *
 * THIS would scan the file for the config and access all devices in the config and try to use the hardware map to get them and then move or get singal to check if they exist
 *
 *
 *
 * We dont need to read the xml...
 * we just force a xml with all the devices in it automatically..
 *
 
 */
public class HardwareScanner {




    // Registry of devices that physically responded during the scan
    public static HashMap<String, HardwareDevice> verifiedRegistry = new HashMap<>();


    /**
     * Begins scaning the activated config file
     * 
     * @// TODO: 3/30/2026 Need to look back and figure the file structure of the config 
     * 
     * @implNote Uses File and ParseAndVerify(Privated) To scan config
     * 
     * 
     * @param opMode The simple op mode object 
     * @// TODO: 3/30/2026  Implement Issue OpModeVersitilty #2
     * @return False if the file is not found, And true if operation was succesfull
     */
    public static boolean beginScan(SimpleOpMode opMode) {
        // Clear previous scans
        verifiedRegistry.clear();

        // FTC stores the active config in a predictable spot on the Control Hub
        File configFile = new File("/sdcard/FIRST/active_configuration.xml");

        if (!configFile.exists()) {
            opMode.telemetry.addLine("Error: active_configuration.xml missing!");
            opMode.telemetry.update();
            return false;
        }

        parseAndVerify(opMode, configFile);
        return true;
    }


    /**
     * Parses the XML Config file
     * @param opMode simpleOpMode Object
     * @param xmlFile the file for the config
     * @// TODO: 3/30/2026  Implement Issue OpModeVersitilty #2
     * @// TODO: 3/30/2026 Implement Advanced Error Handling
     */
    private static void parseAndVerify(SimpleOpMode opMode, File xmlFile) {
        HardwareMap hwMap = opMode.hardwareMap;

        try (FileInputStream fis = new FileInputStream(xmlFile)) {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String type = parser.getName();
                    String name = parser.getAttributeValue(null, "name");

                    if (name != null) {
                        verifyDevice(opMode, type, name, hwMap);
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            opMode.telemetry.addData("Scanner Error", e.getMessage());
        }
    }

    /**
     * Internal Device Verification
     * This checks if the device is legit verifing it is a pyshical device
     *
     * Sample for now for proof of concept
     *
     * @// TODO: 3/30/2026  Implement Issue OpModeVersitilty #2
     * @param opMode Opmode Object
     * @param type Device xml Type
     * @param name deivce Xml Name
     * @param hwMap the hardware map
     * @// TODO: 3/30/2026 Implement the rest of the devices available
     */
    private static void verifyDevice(SimpleOpMode opMode, String type, String name, HardwareMap hwMap) {
        try {
            if (type.contains("Motor")) {
                DcMotor m = hwMap.get(DcMotor.class, name);
                int start = m.getCurrentPosition();
                m.setPower(0.01);
                opMode.sleep(80);
                if (Math.abs(m.getCurrentPosition() - start) > 0) {
                    verifiedRegistry.put(name, m);
                    opMode.telemetry.addData("Confirmed Motor", name);
                }
                m.setPower(0);
            }
            else if (type.contains("Servo")) {
                Servo s = hwMap.get(Servo.class, name);
                s.setPosition(0.5); // Pulse it
                verifiedRegistry.put(name, s);
                opMode.telemetry.addData("Confirmed Servo", name);
            }
            // Add logic here for I2C (Color, Distance) as needed
        } catch (Exception e) {
            // Device in XML but not in HardwareMap or failed test
        }
    }
}