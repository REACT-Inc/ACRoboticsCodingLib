package com.acrobotics.v1.Simplify;

import com.pedropathing.follower.Follower;


import com.bylazar.field.FieldManager;
import com.bylazar.field.PanelsField;
import com.bylazar.field.Style;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.*;
import com.pedropathing.math.*;
import com.pedropathing.paths.*;
import com.pedropathing.telemetry.SelectableOpMode;
import com.pedropathing.util.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
Simple Drawing  this way draw polygon etc all types
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 *
 * @// TODO: 3/23/2026 update this to new standards
 */
public class SimpleDraw {

    private Follower follower;
    public SimpleDraw(Follower follower){
        Drawing.init();

    }



    public  void drawOnlyCurrent() {
        try {
            Drawing.drawRobot(follower.getPose());
            Drawing.sendPacket();
        } catch (Exception e) {
            throw new RuntimeException("Drawing failed " + e);
        }
    }

    public  void draw() {
        Drawing.drawDebug(follower);
    }



    static class Drawing {

        public static ArrayList<Pose> allPoses = new ArrayList<>();
        public static final double ROBOT_RADIUS = 9; // woah
        private static final FieldManager panelsField = PanelsField.INSTANCE.getField();

        private static final Style robotLook = new Style(
                "", "#3F51B5", 0.75
        );
        static final Style robotExpectedPath = new Style("#f542bf", "#f542bf", 1.0);
        private static final Style historyLook = new Style(
                "", "#4CAF50", 0.75
        );

        /**
         * This prepares Panels Field for using Pedro Offsets
         */
        public static void init() {
            panelsField.setOffsets(PanelsField.INSTANCE.getPresets().getPEDRO_PATHING());
        }
        public static void drawAll(Follower follower){

        }

        /**
         * This draws everything that will be used in the Follower's telemetryDebug() method. This takes
         * a Follower as an input, so an instance of the DashbaordDrawingHandler class is not needed.
         *
         * @param follower Pedro Follower instance.
         */
        public static void drawDebug(Follower follower) {

            if (follower.getCurrentPath() != null) {
                drawPath(follower.getCurrentPath(), robotLook);
                Pose closestPoint = follower.getPointFromPath(follower.getCurrentPath().getClosestPointTValue());
                drawRobot(new Pose(closestPoint.getX(), closestPoint.getY(), follower.getCurrentPath().getHeadingGoal(follower.getCurrentPath().getClosestPointTValue())), robotLook);
            }
            drawPoseHistory(follower.getPoseHistory(), historyLook);
            drawRobot(follower.getPose(), historyLook);

            sendPacket();
        }
        public static Pose lastPoses = new Pose(0,0,0);
        /**
         * Draws a path etc for teleop  better for teleop cuz it logs and stuff we can retrieve old results from old matches
         */
        public static void drawForTeleop(Follower follower, Timer opmodeTimer) throws IOException {
            /// The robot is always moving slightly so we have to custom round these values
//            telemetrys.addData("L",Round.round(follower.getPose().getY(), 5, Round.Mode.UP) == Round.round(lastPoses.getY(), 5, Round.Mode.UP));
//            if(Round.round(follower.getPose().getX(), 5, Round.Mode.UP) == Round.round(lastPoses.getX(), 5, Round.Mode.UP) &&
//                    Round.round(follower.getPose().getY(), 5, Round.Mode.UP) == Round.round(lastPoses.getY(), 5, Round.Mode.UP)
//        ){
//                //hasnt changed
//            }else {
//                lastPoses = follower.getPose();
//                allPoses.add(follower.getPose());//.getPose();
////                logging.savePose(follower.getPose(), opmodeTimer);
//            }
            if (follower.getCurrentPath() != null) {
                drawPath(follower.getCurrentPath(), robotLook);
                Pose closestPoint = follower.getPointFromPath(follower.getCurrentPath().getClosestPointTValue());
                drawRobot(new Pose(closestPoint.getX(), closestPoint.getY(), follower.getCurrentPath().getHeadingGoal(follower.getCurrentPath().getClosestPointTValue())), robotLook);
            }
            drawPoseHistory(follower.getPoseHistory(), historyLook);
            drawRobot(follower.getPose(), historyLook);
//            Pose lastPose = allPoses.get(0);
//            for(Pose pose : allPoses){
//                drawColoredLine(robotExpectedPath, pose.getX(), pose.getY(), lastPose.getX(), lastPose.getY());
//                lastPose = pose;
//            }
//            telemetrys.addData("LENGTh", allPoses.size());


        }
        public static void addPosesToList(ArrayList<Pose> poses){
            for(Pose pose : poses){
                allPoses.add(pose);
            }
        }

        /**
         * This draws a robot at a specified Pose with a specified
         * look. The heading is represented as a line.
         *
         * @param pose  the Pose to draw the robot at
         * @param style the parameters used to draw the robot with
         */
        public static void drawRobot(Pose pose, Style style) {
            if (pose == null || Double.isNaN(pose.getX()) || Double.isNaN(pose.getY()) || Double.isNaN(pose.getHeading())) {
                return;
            }

            panelsField.setStyle(style);
            panelsField.moveCursor(pose.getX(), pose.getY());
            panelsField.circle(ROBOT_RADIUS);

            Vector v = pose.getHeadingAsUnitVector();
            v.setMagnitude(v.getMagnitude() * ROBOT_RADIUS);
            double x1 = pose.getX() + v.getXComponent() / 2, y1 = pose.getY() + v.getYComponent() / 2;
            double x2 = pose.getX() + v.getXComponent(), y2 = pose.getY() + v.getYComponent();

            panelsField.setStyle(style);
            panelsField.moveCursor(x1, y1);
            panelsField.line(x2, y2);
        }
        public static void drawLine( double x, double y, double x2, double y2, Style style){

            panelsField.moveCursor(x,y);
            panelsField.setStyle(style);
            panelsField.line(x2,y2);

//        panelsField.update();

        }public static void drawColoredLine(Style style, double x, double y, double x2, double y2){
            panelsField.moveCursor(x,y);
            panelsField.setStyle(style);
            panelsField.line(x2,y2);

//        panelsField.update();

        }
//        public static void drawPolygone(TeleOp26.Pose6D pose){
//            //todo
//            ArrayList<Pose> points = pose.allZones.getZones().get(1).Points;
//            Pose lastPoint =points.get(0);
//            panelsField.setStyle(robotExpectedPath);
//            panelsField.setFill("#999999");
////        panelsField.
//            for(Pose point : points){
//                panelsField.moveCursor(lastPoint.getX(), lastPoint.getY());
//                panelsField.line(point.getX(), point.getY());
//                lastPoint = point;
//            }
////        panelsField.
//        }
        public static void drawToNew(Pose pose){
//        panelsField.setStyle(historyLook);
        }
        /**
         * This draws a robot at a specified Pose. The heading is represented as a line.
         *
         * @param pose the Pose to draw the robot at
         */
        public static void drawRobot(Pose pose) {
            drawRobot(pose, robotLook);
        }

        /**
         * This draws a Path with a specified look.
         *
         * @param path  the Path to draw
         * @param style the parameters used to draw the Path with
         */
        public static void drawPath(Path path, Style style) {
            double[][] points = path.getPanelsDrawingPoints();

            for (int i = 0; i < points[0].length; i++) {
                for (int j = 0; j < points.length; j++) {
                    if (Double.isNaN(points[j][i])) {
                        points[j][i] = 0;
                    }
                }
            }

            panelsField.setStyle(style);
            panelsField.moveCursor(points[0][0], points[0][1]);
            panelsField.line(points[1][0], points[1][1]);
        }

        /**
         * This draws all the Paths in a PathChain with a
         * specified look.
         *
         * @param pathChain the PathChain to draw
         * @param style     the parameters used to draw the PathChain with
         */
        public static void drawPath(PathChain pathChain, Style style) {
            for (int i = 0; i < pathChain.size(); i++) {
                drawPath(pathChain.getPath(i), style);
            }
        }

        /**
         * This draws the pose history of the robot.
         *
         * @param poseTracker the PoseHistory to get the pose history from
         * @param style       the parameters used to draw the pose history with
         */
        public static void drawPoseHistory(PoseHistory poseTracker, Style style) {
            panelsField.setStyle(style);

            int size = poseTracker.getXPositionsArray().length;
            for (int i = 0; i < size - 1; i++) {

                panelsField.moveCursor(poseTracker.getXPositionsArray()[i], poseTracker.getYPositionsArray()[i]);
                panelsField.line(poseTracker.getXPositionsArray()[i + 1], poseTracker.getYPositionsArray()[i + 1]);
            }
        }


        /**
         * pushes the packets to the Fielding
         */
        public static void sendPacket() {
            panelsField.update();
        }
    }
}
