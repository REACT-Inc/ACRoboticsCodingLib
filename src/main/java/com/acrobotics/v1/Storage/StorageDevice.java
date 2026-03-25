package com.acrobotics.v1.Storage;

import com.acrobotics.v1.RobotTrace;

import java.io.File;

/**
 * Storafe device
 * Handles the storage
 *
 *
 * Makes the bare bones for writing and reading
 *
 * And handles the logging and Saving stuff
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public final class StorageDevice {




    public StorageDevice(){
        init();
    }

    /**
     * Initilizies the Files
     */
    public void init(){
        USBAttachedStorageDevice.scanForNewDevices();
        if(USBAttachedStorageDevice.newDeviceFound()){
            //If a new device was found we want to go ahead and process it
            RobotTrace.notify("New Device Found");
        }
    }


    /**
     * A class for reading only files so we can ensure they stay intact
     */
    public class ReadOnlyFile{
        private File fileIdentifier;

        /**
         * Here we would const the file by a name the dir
         * @param file the name of the file or dir
         */
        public ReadOnlyFile(String file){

        }
    }

    /**
     * for both reading and writing files
     */
    public class ReadAndWriteFile{
        private File fileIdentifier;

        /**
         * Here we would const the file by a name the dir
         * @param file the name of the file or dir
         */
        public ReadAndWriteFile(String file){

        }
    }


}
