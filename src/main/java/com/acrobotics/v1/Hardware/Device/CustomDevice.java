package com.acrobotics.v1.Hardware.Device;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorControllerEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorImpl;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import java.util.concurrent.CompletableFuture;

/**
 * CustomDevices extend this class
 */
public class CustomDevice  {


    private boolean deviceIsReal = false;
    /**
     * This is a completable future thingy so we can not wait a long time for results for every device we can simply just make them wait later
     *
     * @param testResults completeable voids
     */
    public void addVerificationForLifeResults(CompletableFuture<Boolean> testResults) {
        testResults.whenCompleteAsync();


    }
}