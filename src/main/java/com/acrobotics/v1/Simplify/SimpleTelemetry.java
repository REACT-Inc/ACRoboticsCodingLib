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
public final class SimpleTelemetry {


    private static Telemetry ftcTelemetry;
    private static PanelsTelemetry panelsTelemetry;

    /**
     * Consts the telemetry instance
     * @// TODO: 3/24/2026  make the panels instance here so its a one variable constructor #Simplication
     * @param ftc FTC Telemetry
     * @param panels Panels Instacne
     */
    public SimpleTelemetry(Telemetry ftc, PanelsTelemetry panels){
        ftcTelemetry = ftc;
        panelsTelemetry = panels;
    }

    /**
     * Sends a message to the Displays so you can see it!
     * @param title the Title of message
     * @param message the message content
     * @return Instance
     */
    public SimpleTelemetry sendMessage(Object title, Object message){
        ftcTelemetry.addData((String) title, message);
        panelsTelemetry.getTelemetry().addData((String) title, message);
        return this;
    }

    /**
     * Talks a message out loud VERY LOUD WARNING
     *
     * @implNote  THIS IS VERY LOUD
     * @param message the message to speak
     * @return Simpletelemetry Instance
     */
    public SimpleTelemetry talk(Object message){
        ftcTelemetry.speak((String) message);
        panelsTelemetry.getTelemetry().addData("Spoken Message", message);
        return this;
    }

    /**
     * Updates and pushes data to the Outputs
     * 
     * @// TODO: 3/24/2026 Call this in a centerual Looop Method system we make 
     */
    public static void update(){
        ftcTelemetry.update();
        panelsTelemetry.getTelemetry().update();
    }
    
}
