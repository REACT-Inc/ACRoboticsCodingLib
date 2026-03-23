package com.acrobotics.v1.RobotTracking;

import com.acrobotics.v1.DataObject;
import com.acrobotics.v1.Simplify.SimpleSensors;
import com.qualcomm.robotcore.hardware.DistanceSensor;

/**
 * THis would have the data all as one thing so we can easily point
 *
 * For example if we have like 8 distance sensors it would
 * combine those
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class SensorDataObject {



    public DataObject sensorData = new DataObject();



    public SensorDataObject(SimpleSensors.Distance... sensors){

        for(int index = 0; index < sensors.length; index++){
            SimpleSensors.Distance sensor = sensors[index];
            sensorData.addEntry(sensor.getHardwareConfiguration(), sensor.getDistance());
        }
    }

    /**
     * This would ocmpute locations of the objcst  found
     */
    public void computeLocationOfObjects(){
        //TODO
    }


}
