package com.acrobotics.v1;

import java.util.ArrayList;

/**
 * This would hold data but be easily
 * interchangable between classes
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 */
public class DataObject {

    private ArrayList<Object[]> database = new ArrayList<Object[]>();
    /**
     * This would add the data and id to the whole list of data
     * @param deviceId Device id so location
     * @param distance The Distance reading
     */
    public void addEntry(String deviceId, Object distance){
        database.add(new Object[]{deviceId, distance});
    }

    /**
     * Retireves the data from the list
     * @return a list of data in a object array [Id, Distance]
     */
    public ArrayList<Object[]> getData(){
        return database;
    }



}
