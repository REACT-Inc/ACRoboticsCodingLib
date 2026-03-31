package com.acrobotics.v1;

import com.acrobotics.v1.Hardware.Hardware;

/**
 * This would be a sensing place for voltage etc that gets called every loop so we can make sure etc
 *
 * voltage would prodvide underVotlage sensing
 */
public class InternalSensor {


    /**
     * Makes sure the robot is safe to operate
     *
     * Checks battery voltage to ensure its been above 12v for atleast Blank time
     * @// TODO: 3/30/2026 Make Time To Power Use Calculations
     * @return if it is safe or not
     */
    public boolean safeOperation(){
        if(Hardware.getRobotVoltage() < 11.5){
            return false;
            //Unsafe operations If this continues for some time
        }
        return true;
    }
}
