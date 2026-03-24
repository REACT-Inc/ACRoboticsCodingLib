package com.acrobotics.v1.Simplify;

import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *  this would put out telemetry in driver hub and online also handling drawing automatically by pickimg up on keys within
 *  Like when it had Pose to telemetyr it would draw etc all types
 *
 * @author Cayden Riddle
 * @version DEV.1
 * 
 * @// TODO: 3/24/2026 maybe this class should have the ability to handle all panels actions (Expcept Drawing) 
 *
 */
public class SimpleTelemetry {


    private final Telemetry ftcTelemetry;
    private final PanelsTelemetry panelsTelemetry;
    public SimpleTelemetry(Telemetry ftc, PanelsTelemetry panels){
        ftcTelemetry = ftc;
        panelsTelemetry = panels;
    }
    
}
