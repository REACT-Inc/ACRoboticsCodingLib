package com.acrobotics.v1.Enhancement;

import com.acrobotics.v1.Archive.File.FileSystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * <h1>EnhancedDcMotor</h1>
 * <p>
 * A wrapper for DcMotorEx that provides:
 * - Automated 30s Motor Training (Max Speed, Ramp Up, Arc Timing)
 * - Auto-Archiving of data older than 9 months
 * - Distance-to-Power Interpolation Tables
 * - Automated PIDF Tuning (1-minute timeout)
 * </p>
 *
 *
 * @// TODO: 3/23/2026 Adaptation to the lib needs done
 * @author Cayden Riddle
 * @version 1.0-beta
 */
public class EnhancedDcMotor {



    // Internal State Machine
    protected enum MotorState {
        IDLE,
        TRAINING_DELAY,
        TRAINING_RUNNING,
        TUNING_PID,
        READY
    }

    private final DcMotorEx motor;
    private final String configName;
    public FileSystem fileSystem;
    private MotorState currentState = MotorState.IDLE;
    private boolean isEnhanced = true;

    // Data Storage
    private double maxRPM = 0;
    private double P = 15;
    private double F = 15;
    private double rampUpTime = 0;
    private double endSpeed;
    private double startSpeed;
    private boolean runningTrainingSequence = false;
    private double peakVelocityFound;
    private final ArrayList<Double> arcTiming = new ArrayList<>();
    private final TreeMap<Double, Double> distancePowerTable = new TreeMap<>(); // Key: Distance, Value: Power

    // Tuning/Training Variables
    private final ElapsedTime trainingTimer = new ElapsedTime();
    private final ElapsedTime tuningTimer = new ElapsedTime();

    // Constants
    private static final long NINE_MONTHS_MS = 23328000000L; // Approx 9 months in MS


    /**
     * Standard Constructor
     * Maps everything And makes the motor objects
     * Proceeds to Check if file exists and is vaild if its outdated archives and prepares for retaining
     *
     * @param hardwareMap The Hardware map from the opMode
     * @param configName Config name for the motor
     */
    public EnhancedDcMotor(HardwareMap hardwareMap, String configName) {
        this.motor = hardwareMap.get(DcMotorEx.class, configName);
        this.configName = configName;
        this.fileSystem = new FileSystem(configName);

        checkFileSystem();
    }

    /**
     * None Enhanced Constructor
     *
     * @param hardwareMap Hardware map
     * @param configName Config name for motor
     */
    public static EnhancedDcMotor CreateMotorWithoutEnhancedData(HardwareMap hardwareMap, String configName) {
        EnhancedDcMotor staticMotor = new EnhancedDcMotor(hardwareMap, configName);
        staticMotor.isEnhanced = false;
        staticMotor.currentState = MotorState.READY;
        // Set generic presets
        staticMotor.maxRPM = 300; // Generic placeholder
        staticMotor.motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        return staticMotor;
    }

    // =========================================================================
    //                            LOGIC & UPDATE
    // =========================================================================

    /**
     * MUST be called in your init_loop or loop.
     * Handles the time-based training and tuning logic.
     * Not needed if runing in Unehanced Mode
     *
     * @return true if busy (training/tuning), false if ready.
     */
    public boolean update() {
        if (!isEnhanced) return false;

        switch (currentState) {
            case TRAINING_DELAY:
                // Small delay before starting to ensure safety
                motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//                motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
                //NO if we run wihtout encoder we cant get velocitys no?
                motor.setPower(0);
                trainingTimer.reset();
                currentState = MotorState.TUNING_PID;
//                if (telemetry != null) telemetry.addLine("Starting Motor Training: 30s...");
                break;
            case TUNING_PID:
                runPIDTuningSequence();
                return true;

            case TRAINING_RUNNING:
                runTrainingSequence();
                return true;



            case READY:
            case IDLE:
                return false;
        }
        return false;
    }

    /**
     * Checks if the file system exists or if its older than 9 Months
     * If the file system is older than 9 Months we want to archive it because it could be last season so
     * Values would be different
     */
    private void checkFileSystem() {
        if (fileSystem.exists()) {
            Map<String, String> data = fileSystem.readData();
            long fileDate = Long.parseLong(Objects.requireNonNull(data.getOrDefault("Date", "0")));

            if (System.currentTimeMillis() - fileDate > NINE_MONTHS_MS) {
//                if (telemetry != null)
//                    telemetry.addLine("Motor Data Outdated (>9 Months). Archiving...");
                fileSystem.archiveOldData();
                currentState = MotorState.TRAINING_DELAY;
            } else {
                // Load Data
                this.maxRPM = Double.parseDouble(Objects.requireNonNull(data.getOrDefault("ReportedMotorRPM", "6000")));
                this.P = Double.parseDouble(Objects.requireNonNull(data.getOrDefault("P", this.P + "")));
                this.F = Double.parseDouble(Objects.requireNonNull(data.getOrDefault("F", this.F + "")));
                this.rampUpTime = Double.parseDouble(Objects.requireNonNull(data.getOrDefault("RampUpTime", this.rampUpTime + "")));

                // Load other props here if needed
                currentState = MotorState.READY;
            }
        } else {
            currentState = MotorState.TRAINING_DELAY;
        }
    }

    // =========================================================================
    //                            TRAINING LOGIC
    // =========================================================================
    /**
     * Runs the training for the File system
     * This ensures the program knows the true limits of the motors and such
     * If we know the limits Its way easier
     */
    private void runTrainingSequence() {
        if(!this.runningTrainingSequence){
            this.runningTrainingSequence = true;
            this.startSpeed = motor.getVelocity();
        }
        double elapsed = trainingTimer.seconds();

        // Run at 100% power
        motor.setPower(1.0);

        // Record ARC Timing (Speed every second)
        if ((int) elapsed > arcTiming.size()) {
            arcTiming.add(motor.getVelocity());
        }

        // Calculate Ramp Up (Time to reach 95% of peak velocity found so far)
        double currentVel = motor.getVelocity();
        // Simple logic: if velocity is still increasing significantly, update ramp time
        // (This is a simplified logic for the example)

        if(elapsed > 5 && currentVel < 150 && linkConfiguration == RunningConfiguration.PRIMARY){
            //Either motor is not powerful ehough or its stalled And in Link Configuration and one motor needs reversed
            motor.setDirection(motor.getDirection().inverted());
            currentState = MotorState.TRAINING_DELAY;
            return;
        }
//        if (telemetry != null) {
//            telemetry.addData("Training Motor", configName);
//            telemetry.addData("Time Left", 30 - elapsed);
//            telemetry.addData("Current Vel", currentVel);
//        }
        this.peakVelocityFound = Math.max(motor.getVelocity(), this.peakVelocityFound);
        if (elapsed >= 30) {
            // Finish Training
            motor.setPower(0);
            this.endSpeed = motor.getVelocity();
            maxRPM = endSpeed; // Approximate max

            double threshold = this.peakVelocityFound * 0.95;
            for (int i = 0; i < arcTiming.size(); i++) {
                if (arcTiming.get(i) >= threshold) {
                    this.rampUpTime = i; // The index represents the second it happened
                    break;
                }
            }
            // Save Data
            saveTrainingData(); // 0 is start speed assumption
            currentState = MotorState.READY;
        }
    }
    /**
     * Saves our data from training to the file system
     */
    private void saveTrainingData() {
        // [ConfigName, RanUsingEncoder?, ReportedMotorRPM, TestedTime, StartingSpeed, EndingSpeed, ARCTiming, Rampuptime, PIDF, TrainingData, Date]
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("ConfigName", configName);
        dataMap.put("RanUsingEncoder", "true");
        dataMap.put("ReportedMotorRPM", String.valueOf(endSpeed));
        dataMap.put("TestedTime", "30.0");
        dataMap.put("StartingSpeed", String.valueOf(this.startSpeed));
        dataMap.put("EndingSpeed", String.valueOf(this.endSpeed));
        dataMap.put("MaxSpeedRecorded", String.valueOf(this.peakVelocityFound));

        dataMap.put("ARCTiming", arcTiming.toString());
        dataMap.put("RampUpTime", String.valueOf(this.rampUpTime)); // Needs calculation logic
        dataMap.put("P", P + ""); // Will be set by tuner
        dataMap.put("F", F + ""); // Will be set by tuner
        dataMap.put("TrainingData", "RAW_DATA_HERE");
        dataMap.put("Direction", motor.getDirection().name());
        dataMap.put("Date", String.valueOf(System.currentTimeMillis()));

        fileSystem.writeData(dataMap);
    }

    // =========================================================================
    //                            PIDF TUNING
    // =========================================================================

    /**
     * Triggers the auto PIDF tuning sequence.
     * Sets motor to 50% max speed and tunes P and F.
     * Stops after 1 minute if not resolved.
     */
    public void setMotorToPIDFDriven() {
        if (!isEnhanced) return;
        tuningTimer.reset();
        motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        currentState = MotorState.TUNING_PID;
    }

    private void runPIDTuningSequence() {
        if (tuningTimer.seconds() > 60) {
            // Timeout - Save what we have and stop
            motor.setPower(0);
//            if (telemetry != null)
//                telemetry.addLine("PID Tuning Timed Out. Saving current values.");
            currentState = MotorState.TRAINING_RUNNING;

            return;
        }

        double targetVelocity = maxRPM * 0.5; // Target 50%
        double currentVelocity = motor.getVelocity();

        PIDFCoefficients pidf = motor.getPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // Very basic heuristic tuning logic
        // 1. Tune F (Feedforward) to get close
        if (Math.abs(currentVelocity - targetVelocity) > targetVelocity * 0.2) {
            // If we are far off, adjust F
            if (currentVelocity < targetVelocity) {
                pidf.f += 0.1;
            } else {
                pidf.f -= 0.1;
            }
        } else {
            // 2. Tune P (Proportional) to stabilize
            if (currentVelocity < targetVelocity) {
                pidf.p += 0.05;
            } else {
                pidf.p -= 0.05;
            }
        }

        // Safety clamps
        pidf.f = Math.max(0, pidf.f);
        pidf.p = Math.max(0, pidf.p);
        this.P = pidf.p;
        this.F = pidf.f;

        motor.setVelocity(targetVelocity); // Apply velocity target with internal PID
        motor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER, pidf);

//        if (telemetry != null) {
//            telemetry.addData("Tuning PID", configName);
//            telemetry.addData("Target", targetVelocity);
//            telemetry.addData("Actual", currentVelocity);
//            telemetry.addData("P", pidf.p);
//            telemetry.addData("F", pidf.f);
//        }
    }

    // =========================================================================
    //                            DISTANCE TABLE
    // =========================================================================

    public void addDistancePowerPoint(double distance, double power) {
        distancePowerTable.put(distance, power);
    }

    public void runByDistance(double distance) {
        if (distancePowerTable.isEmpty()) {
            motor.setPower(1/distance);
            return;
        }

        // Linear Interpolation
        Map.Entry<Double, Double> floor = distancePowerTable.floorEntry(distance);
        Map.Entry<Double, Double> ceiling = distancePowerTable.ceilingEntry(distance);

        double power;
        if (floor == null) {
            assert ceiling != null;
            power = ceiling.getValue();
        }
        else if (ceiling == null) power = floor.getValue();
        else {
            double ratio = (distance - floor.getKey()) / (ceiling.getKey() - floor.getKey());
            power = floor.getValue() + ratio * (ceiling.getValue() - floor.getValue());
        }

        motor.setPower(power);
    }

    // =========================================================================
    //                            DC MOTOR
    // =========================================================================
    // Add inside EnhancedDcMotor class
    public void setDirection(DcMotorSimple.Direction direction) {
        this.motor.setDirection(direction);
    }

    public void setPower(double power) {
        this.motor.setPower(power);
    }

    // =========================================================================
    //                            RETURN TYPES
    // =========================================================================
    public DcMotor toDcMotor(){
        return this.motor;
    }
    public DcMotorEx toDcMotorEx(){
        return this.motor;
    }
    // =========================================================================
    //                            RUNNING TYPES
    // =========================================================================
    public enum RunningConfiguration {
        SINGLE, PRIMARY, SECONDARY
    }
    public RunningConfiguration linkConfiguration = RunningConfiguration.SINGLE;

    public void setRunningInLinkConfiguration(RunningConfiguration runningInLinkConfiguration) {
        this.linkConfiguration = runningInLinkConfiguration;
    }

}