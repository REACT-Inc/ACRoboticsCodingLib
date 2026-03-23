package com.acrobotics.v1.Storage;

/**
 * This would handle the event if you plug a usb in it would transfer active data onto the control hub for configs etc
 * Alot of cool stuff we save and read this way it wouldnt delete somehow  if it did then we could grab it etc
 *
 * Allows us to plug a usb drive in and it would take everything from it and use it
 * so for example configs, The onbotjava, and Any saved stuff could be transfered
 * also helps im the case if we update etc
 *
 * In this we could also have  a usb Drive act as a log system so we could easily dignose issues
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class USBAttachedStorageDevice {



    public static boolean deviceFound = false;

    /**
     * Scans for new USB Devices (USB DRIVES)
     *
     * Basically we scan the filesystem for any new drive devices that we dont reconize, That fit the following conditions
     *
     * <ul>
     *  <li>Folder has a file named:"RobotStorageDevice.xml"</li>
     *  <li>Folder contains files</li>
     *  <li>Folder contains vaild XML Formatted files</li>
     * </ul>
     *
     * @author Cayden R
     * @version 0.1
     *
     * TODO make this method
     */
    public static void scanForNewDevices(){

        //Repersents if we found a device or nah
        if(true) {
            deviceFound = false;
        }else{
            deviceFound = true;
        }
    }



    /**
     * Returns if we found a new device
     *
     * @author Cayden R
     * @version 0.1
     *
     * TODO make this method
     */
    public static boolean newDeviceFound(){
        return deviceFound;
    }


}
