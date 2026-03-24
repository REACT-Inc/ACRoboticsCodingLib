package com.acrobotics.v1.Enhancement;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * <h1>Linked MOTORS</h1>
 * <p>
 * A wrapper class that synchronizes two {@link EnhancedDcMotor} instances.
 * Useful for dual-slide lifts, intake arms, or dual-motor drivetrains.
 * </p>
 *
 * <p>
 * <b>CRITICAL SAFETY NOTE:</b>
 * MOTORS MAY STALL FOR 5 SECONDS AT START OF RUN TIME,
 * DO NOT WORRY SYSTEM SHOULD AUTO DETERMINE AND REVERSE PRIMARY NODE,
 * KEYWORD SHOULD BE CATIOUS!
 * </p>
 */
public class LinkedEnhancedDcMotor {

    private EnhancedDcMotor motor1;
    private EnhancedDcMotor motor2;

    /**
     * @param hardwareMap The OpMode hardware map
     * @param configName1 The configuration name of the first motor
     * @param configName2 The configuration name of the second motor
     */
    public LinkedEnhancedDcMotor(HardwareMap hardwareMap, String configName1, String configName2) {
        this.motor1 = new EnhancedDcMotor(hardwareMap, configName1);
        this.motor2 = new EnhancedDcMotor(hardwareMap, configName2);
        motor1.setRunningInLinkConfiguration(EnhancedDcMotor.RunningConfiguration.PRIMARY);
        motor2.setRunningInLinkConfiguration(EnhancedDcMotor.RunningConfiguration.SECONDARY);
    }

    /**
     * Static Constructor for linked motors without the enhancement features.
     */
    public static LinkedEnhancedDcMotor CreateLinkedWithoutEnhancedData(HardwareMap hardwareMap, String name1, String name2) {
        LinkedEnhancedDcMotor linked = new LinkedEnhancedDcMotor(hardwareMap, name1, name2);
        // Overwrite the internal motors with non-enhanced versions
        linked.motor1 = EnhancedDcMotor.CreateMotorWithoutEnhancedData(hardwareMap, name1);
        linked.motor2 = EnhancedDcMotor.CreateMotorWithoutEnhancedData(hardwareMap, name2);
        return linked;
    }

    // =========================================================================
    //                            CONFIGURATION
    // =========================================================================

    /**
     * Sets the physical direction of the motors.
     * CALL THIS BEFORE update() IF MOTORS ARE MECHANICALLY LINKED.
     */
    public void setDirections(DcMotorSimple.Direction dir1, DcMotorSimple.Direction dir2) {
        motor1.setDirection(dir1);
        motor2.setDirection(dir2);
    }

    /**
     * Adds a distance-to-power point to BOTH motors' internal tables.
     * @param distance The target distance (e.g., mm or ticks)
     * @param power The power required to hold or move at that distance
     */
    public void addDistancePowerPoint(double distance, double power) {
        motor1.addDistancePowerPoint(distance, power);
        motor2.addDistancePowerPoint(distance, power);
    }

    // =========================================================================
    //                            CONTROL LOOPS
    // =========================================================================

    /**
     * Runs the training/tuning update loop for BOTH motors.
     * @return true if EITHER motor is still busy (training/tuning).
     */
    public boolean update() {
        boolean m1Busy = motor1.update();
        boolean m2Busy = motor2.update();
        return m1Busy || m2Busy;
    }

    /**
     * Runs both motors using their internal distance-to-power tables.
     */
    public void runByDistance(double distance) {
        motor1.runByDistance(distance);
        motor2.runByDistance(distance);
    }

    /**
     * Triggers the Auto-PIDF tuning on both motors.
     * Note: This might be chaotic if the motors are mechanically linked and try to tune simultaneously
     * while fighting slightly. Use with caution on linked systems.
     */
    public void setMotorToPIDFDriven() {
        motor1.setMotorToPIDFDriven();
        motor2.setMotorToPIDFDriven();
    }

    // =========================================================================
    //                            BASIC ACCESSORS
    // =========================================================================

    public void setPower(double power) {
        motor1.setPower(power);
        motor2.setPower(power);
    }

    public EnhancedDcMotor getMotor1() {
        return motor1;
    }

    public EnhancedDcMotor getMotor2() {
        return motor2;
    }

    public DcMotor[] toDcMotor(){
        return new DcMotor[]{ motor1.toDcMotor(), motor2.toDcMotor()};
    }
}