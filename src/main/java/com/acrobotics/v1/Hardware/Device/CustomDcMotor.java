package com.acrobotics.v1.Hardware.Device;

import com.acrobotics.v1.RobotTrace;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import java.util.ArrayList;

/**
 * CustomDcMotor: A lightweight, daisy-chainable motor wrapper.
 * Features: Absolute rotation tracking, easy toggles, and dynamic power mapping.
 */
public class CustomDcMotor extends CustomDevice {

    private final DcMotorEx motor;
    private final double ticksPerRev;

    // State tracking
    private static ArrayList<CustomDcMotor> allMotors = new ArrayList<>();
    private static boolean motorsEnabled = true;

    private boolean isToggled = false;
    private boolean lastToggleInput = false;
    private boolean isFlipped = false;

    // Rotation tracking variables
    private int lastEncoderPosition = 0;
    private double totalAbsoluteTicks = 0;

    /**
     * make a motor here!
     * @param device The hardware device from the hardwareMap
     * @param ticksPerRev Ticks per revolution (e.g., 537.7 for goBILDA 312)
     */
    public CustomDcMotor(HardwareDevice device, double ticksPerRev) {
        this.motor = (DcMotorEx) device;
        this.ticksPerRev = ticksPerRev;

        // Default behavior: reset and run
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        allMotors.add(this);
    }

    /**
     * Package privated update for the loop,
     * Called within device handler when all hardware is constructed
     */
     void update() {
        int currentPos = motor.getCurrentPosition();
        // Track absolute distance moved regardless of direction
        totalAbsoluteTicks += Math.abs(currentPos - lastEncoderPosition);
        lastEncoderPosition = currentPos;

    }

    // --- BASIC CONTROLS ---

    /**
     * sets the power for the motor
     * @implNote Power is affected by if the direction of the motor is fliped or not!
     * @param power power for the motor
     * @return self
     */
    public CustomDcMotor setPower(double power) {
        if (motorsEnabled) {
            motor.setPower(isFlipped ? -power : power);
        } else {
            RobotTrace.notify("WARNING: Motors disabled. Power ignored.");
        }
        return this;
    }

    /**
     * Stops the motor
     * @return self
     */
    public CustomDcMotor stop() {
        if (motor.getMode() == DcMotor.RunMode.RUN_TO_POSITION) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        motor.setPower(0);
        return this;
    }

    /**
     * Flips the direction logic of the motor without changing hardware config
     * @return self
     */
    public CustomDcMotor flip() {
        this.isFlipped = !this.isFlipped;
        return this;
    }

    // --- SMART LOGIC ---

    /**
     * Powers motor while condition is held, stops otherwise
     * @param condition T/F
     * @param powerPercentage the power set to while condit met
     * @return self
     */
    public CustomDcMotor powerWhen(boolean condition, double powerPercentage) {
        setPower(condition ? (powerPercentage / 100.0) : 0);
        return this;
    }

    /**
     * Toggles motor state on the "rising edge" of the boolean input
     * @param  condition T/F that toggles
     * @param powerPercentage  the power set when toggled
     * @return self
     */
    public CustomDcMotor toggle(boolean condition, double powerPercentage) {
        if (condition && !lastToggleInput) {
            isToggled = !isToggled;
        }
        lastToggleInput = condition;
        setPower(isToggled ? (powerPercentage / 100.0) : 0);
        return this;
    }

    /**
     * Maps a raw value (like sensor distance or trigger) directly to motor power
     * this is just set power.... what was i thinking
     */
    public CustomDcMotor setPowerDynamically(double value) {
        setPower(value);
        return this;
    }

    /**
     * rotates the motor a specific amount
     * @param rotations the amount of rotations (-32 to 32);
     * @return self
     */
    public CustomDcMotor rotate(double rotations) {
        int target = motor.getCurrentPosition() + (int)(rotations * ticksPerRev);
        motor.setTargetPosition(target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower( 100.0);
        return this;
    }

    // --- TELEMETRY / DATA ---

    /**
     * Returns net rotations (Forward 5 + Back 3 = 2)
     */
    public double getRotations() {
        return motor.getCurrentPosition() / ticksPerRev;
    }

    /**
     * Returns absolute total work done (Forward 5 + Back 3 = 8)
     */
    public double getTotalRotations() {
        return totalAbsoluteTicks / ticksPerRev;
    }

    /**
     * gets motor power
     */
    public double getPower() {
        return motor.getPower();
    }

    /**
     * Stops all motors
     */
    public static void stopAll() {
        for (CustomDcMotor m : allMotors) m.stop();
    }

    /**
     * Disables all motors
     */
    public static void globalDisable() {
        motorsEnabled = false;
        for (CustomDcMotor m : allMotors) m.motor.setMotorDisable();
    }

    public static void globalEnable() {
        motorsEnabled = true;
        for (CustomDcMotor m : allMotors) m.motor.setMotorEnable();
    }
}