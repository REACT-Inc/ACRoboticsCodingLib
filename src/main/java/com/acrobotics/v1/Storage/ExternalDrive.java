package com.acrobotics.v1.Storage;

import com.acrobotics.v1.Exception.ConfigurationManagementException;
import com.acrobotics.v1.Hardcoded;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A external drive
 * for a file etc
 */
public class ExternalDrive {


    private File driveFolder;
     File configFile = null;

    public ExternalDrive(File driveFolder){
        this.driveFolder = driveFolder;
        File[] allConfigFiles = driveFolder.listFiles((dir, name) -> name.endsWith("robotConfig.xml"));
        this.configFile = allConfigFiles[0];
    }

    public File getDrive(){
        return driveFolder;
    }

    /**
     * This would check for a xml file name robotConfig.xml
     *
     * @implNote This file will always be the active config, as when a new config is generated it is added to the drive and then copied to config dir
     */
    public ConfigurationManagement checkForConfigurationFile() {
            return new ConfigurationManagement();
    }


    /**
     * Handles the config methods
     */
    class ConfigurationManagement{

        boolean hasExistingFile;

        public ConfigurationManagement() {

            if(configFile != null) {
                //Double triple check
                this.hasExistingFile = configFile.exists();
            }else{
                this.hasExistingFile = false;
            }
        }

        /**
         * Merges content from the external drives config and the input together
         *
         * Priority merge with the External drive config having the higher priority
         * @param buildConfigContent  the config content
         * @return self
         */
        public ConfigurationManagement priorityMergeConfigurationContent(String buildConfigContent) throws FileNotFoundException {
            if(hasExistingFile){
                //Means it atleast exists
                /// We dont vaildate xml here we should buttttt
            }else{
                if(configFile != null){
                    /// If we get here then the configfile isnt null but the file doesnt exist
                    if(!configFile.exists()){

                        configFile = new File(driveFolder, Hardcoded.configurationName);
                        //Force Create it
                        configFile.mkdirs();
                        hasExistingFile = configFile.exists();
                    }
                }else{
                    configFile = new File(driveFolder, Hardcoded.configurationName);
                    //Force Create it
                    configFile.mkdirs();
                    hasExistingFile = configFile.exists();
                }
            }
            /// BY here we should hopefully have a existing config file in the case we dont panic panic panic!
            if(configFile == null || !configFile.exists()){
                throw new ConfigurationManagementException("There was a error when handling configuration external creation, No Configfile was created");
            }
            if(new FileReader(configFile).
            return this;
        }

        public ConfigurationManagement vaildateConfigurationFileContent(){
            return this;
        }

        public ConfigurationManagement pushConfigurationFileToInternal(){
            return this;
        }
    }
}
