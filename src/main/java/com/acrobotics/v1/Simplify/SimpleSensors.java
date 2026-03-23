package com.acrobotics.v1.Simplify;

/**
 * Simple sensors so they would basically relate more with how spike has a dropdown instead of return like
 * rgb it owuld be a number 1-9
 *
 * Distance would return the num of course or could be used for motor easily
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class SimpleSensors {



    public class Distance {

        /**
         * This would get the device config that its regerstred with
         * @return The Id
         */
        public String getHardwareConfiguration(){
            return "";
        }

        /**
         * Gets the distance reading it
         * @return a float the distance
         */
        public Float getDistance(){
            return 0.0f;
        }
    }
}
