package com.acrobotics.v1;

import com.acrobotics.v1.Annotations.StorageDeviceAllocatedMemory;
import com.acrobotics.v1.Exception.MathExException;
import com.acrobotics.v1.Storage.USBAttachedStorageDevice;
import com.pedropathing.geometry.*;
import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.ArrayList;

/**
 * <h1>Math Ex -  A Class for more Math! </h1>
 * <p>
 * This place we can add more math such as converting Field Positons to coordinates etc
 * </p>
 *
 * @author Cayden Riddle
 * @version DEV.1
 *
 * TODO:
 * Actually put code here!
 * Teleop26 Has alot of IsInside etc for shapes but i believe they would fit here good
 * Also in flywheel stuff it has a table to graph data for a curve we would want that here
 */

public class MathEx {


    @StorageDeviceAllocatedMemory(key = "DriveDampen")
    private static float dampen;


    /**
     * Computes the drive speeds for motors when using mech drive
     *
     * @param DrivePad The controller for the driver operated commands
     *
     * @implNote To adjust overall exact Drive Data please refer to The Wiki for the USB Drive System<br>
     * Left Stick Y  Walk
     * Left Stick X Strafe
     * Right Stick X Pivot
     *
     * @see USBAttachedStorageDevice for Mimumal Information
     *
     * @StorageDevice DriveDampen, SpeedOffset
     *
     * @todo 3/20/2026  Implement the properties for speed control system
     *
     * @return float[] Fr, Fl, Br, Bl Drive Powers
     */
    public float[] computeMechDrive(Gamepad DrivePad) {
        float walk = -DrivePad.left_stick_y;     // Forward/Backward

        float strafe = DrivePad.left_stick_x;   // Left/Right
        float pivot = DrivePad.right_stick_x;  // Rotation
        if (!DrivePad.right_bumper) {
            dampen = 0.7F;
        }
        // Kinematics Math
        float fl = walk + strafe;
        float fr = walk - strafe;
        float bl = walk - strafe;
        float br = walk + strafe;

        fl += pivot;
        fr -= pivot;
        bl += pivot;
        br -= pivot;

        // Apply Dampening Scalar (Formula: Power / (DampenWeight + Offset))
        fl /= (dampen * 1.8f) + 0.4f;
        fr /= (dampen * 1.8f) + 0.4f;
        bl /= (dampen * 1.8f) + 0.4f;
        br /= (dampen * 1.8f) + 0.4f;
        return  new float[] {fl ,fr, bl, br};
    }




    /**
     * Checks if point (px, py) is inside triangle ABC AND at least 'margin' away from edges.
     *
     * @param px the X of the point to see if its inside.
     * @param py the Y of the point to see if its inside.
     * @param ax, ay, bx, by, cx, cy Is the points of the triangle.
     * @param margin the Margin for how far away from the outside of the triangle it should be
     *
     * @implNote Method implements IsPointInTriangle() For initial Triangle Check<br>
     * Method implements linePointDisc() To determine distance and margin Check
     *
     * @throws com.acrobotics.v1.Exception.MathExException When something goes wrong.
     *
     * Fixed 3/23/2026 TOD0 3/20/2026  Make the exception that we catch be Outputted somewhere
     *
     * @return T/F If its inside or not
     */
    public boolean isInsideTriangleWithMargin(double px, double py, double ax, double ay, double bx, double by, double cx, double cy, double margin) {
        try {
            // 1. First, check if it's even in the triangle at all
            if (!isPointInTriangle(px, py, ax, ay, bx, by, cx, cy)) return false;

            // 2. Check distance to all three edges (A-B, B-C, C-A)
            double distAB = linePointDist(px, py, ax, ay, bx, by);
            double distBC = linePointDist(px, py, bx, by, cx, cy);
            double distCA = linePointDist(px, py, cx, cy, ax, ay);

            // 3. Return true only if it's further than the margin from every wall
            return (distAB >= margin && distBC >= margin && distCA >= margin);
        }catch( Exception e ){
            RobotTrace.trace(e.getMessage());
            throw new MathExException(e.getMessage());

        }
    }


    /**
     * Sees the distance for point to Line
     *
     * @param x the X of the point to see the distance to line.
     * @param y the Y of the point to see the distance to line.
     * @param x1, y1, x2, y2 the line points.
     *
     *
     * @throws com.acrobotics.v1.Exception.MathExException When something goes wrong.
     *
     * Fixed 3/23/2026 TOD0 3/20/2026  Make the exception that we catch be Outputted somewhere
     *
     * @return the distance of the point to line
     */
    public  double linePointDist(double x, double y, double x1, double y1, double x2, double y2) {
        try {
            double A = x - x1;
            double B = y - y1;
            double C = x2 - x1;
            double D = y2 - y1;

            double dot = A * C + B * D;
            double len_sq = C * C + D * D;
            double param = (len_sq != 0) ? dot / len_sq : -1;

            double xx, yy;

            if (param < 0) {
                xx = x1;
                yy = y1;
            } else if (param > 1) {
                xx = x2;
                yy = y2;
            } else {
                xx = x1 + param * C;
                yy = y1 + param * D;
            }

            double dx = x - xx;
            double dy = y - yy;
            return Math.sqrt(dx * dx + dy * dy);
        }catch(Exception e){
            RobotTrace.trace(e.getMessage());
            throw new MathExException(e.getMessage());
        }
    }



    /**
     * Checks if point (px, py) is inside triangle ABC.
     *
     * @param px the X of the point to see if its inside.
     * @param py the Y of the point to see if its inside.
     * @param ax, ay, bx, by, cx, cy Is the points of the triangle.
     *
     * @implNote Method implements the use of the private Sign method
     *
     * @throws com.acrobotics.v1.Exception.MathExException When something goes wrong.
     *
     * Fixed 3/23/2026 TOD0 3/20/2026  Make the exception that we catch be Outputted somewhere
     *
     * @return T/F If its inside or not
     */
    public  boolean isPointInTriangle(double px, double py, double ax, double ay, double bx, double by, double cx, double cy) {
        try {
            // Calculate the areas (using cross product) of the 3 sub-triangles
            double d1 = sign(px, py, ax, ay, bx, by);
            double d2 = sign(px, py, bx, by, cx, cy);
            double d3 = sign(px, py, cx, cy, ax, ay);

            boolean has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
            boolean has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

            // If there are no negative areas OR no positive areas, the point is inside
            return !(has_neg && has_pos);
        }catch(Exception e){
            RobotTrace.trace(e.getMessage());
            throw new MathExException(e.getMessage());
        }
    }

    private  double sign(double p1x, double p1y, double p2x, double p2y, double p3x, double p3y) {
        return (p1x - p3x) * (p2y - p3y) - (p2x - p3x) * (p1y - p3y);
    }


    /**
     * Checks if point (px, py) is inside polygon AND atleast away 'margin' from borders
     *
     * @param px the X of the point to see if its inside.
     * @param py the Y of the point to see if its inside.
     * @param margin The amount of margin from the walls the point must be.
     * @param vertices a Pose ArrayLIST of Points for the polygon.
     *
     * @implNote Method implements the use of IsInsidePolygonWithMargin(ARRAY VERSION) To Return <br>
     * Uses a advanced For Loop May have unexpected Delay<br>
     * Use Exact Shape Methods if thats a issue
     *
     * This uses the Array Verison of this method; May be inEfficient
     *
     * @throws com.acrobotics.v1.Exception.MathExException When something goes wrong.
     *
     * Fixed 3/23/2026 TOD0 3/20/2026  Make the exception that we catch be Outputted somewhere
     *
     * @return T/F If its inside or not
     */
    public  boolean isInsidePolygonWithMargin(double px, double py, double margin, ArrayList<Pose> vertices){
        try {
            Pose[] ary = new Pose[vertices.size()];
            int index = 0;
            for (Pose vert : vertices) {
                ary[index] = vert;
                index++;
            }
            return isInsidePolygonWithMargin(px, py, margin, ary);
        }catch(Exception e){
            RobotTrace.trace(e.getMessage());
            throw new MathExException(e.getMessage());
        }
    }


    /**
     * Checks if point (px, py) is inside polygon AND atleast away 'margin' from borders
     *
     * @param px the X of the point to see if its inside.
     * @param py the Y of the point to see if its inside.
     * @param margin The amount of margin from the walls the point must be.
     * @param vertices a Pose Array of Points for the polygon.
     *
     * @implNote Method implements the use of IsInsidePolygon(ARRAY VERSION) To Return <br>
     * Uses a advanced For Loop May have unexpected Delay<br>
     * Use Exact Shape Methods if thats a issue
     *
     * @throws com.acrobotics.v1.Exception.MathExException When something goes wrong.
     *
     * Fixed 3/23/2026 TOD0 3/20/2026  Make the exception that we catch be Outputted somewhere
     *
     * @return T/F If its inside or not
     */
    public  boolean isInsidePolygonWithMargin(double px, double py, double margin, Pose... vertices) {
        try {
            // 1. Check if we are inside the shape at all
            if (!isInsidePolygon(px, py, vertices)) return false;

            // 2. Check if we are too close to any of the walls
            for (int i = 0, j = vertices.length - 1; i < vertices.length; j = i++) {
                double d = linePointDist(px, py,
                        vertices[i].getX(), vertices[i].getY(),
                        vertices[j].getX(), vertices[j].getY());
                if (d < margin) return false;
            }

            return true;
        }catch(Exception e){
            RobotTrace.trace(e.getMessage());
            throw new MathExException(e.getMessage());
        }
    }

    /**
     * Checks if point (px, py) is inside polygon
     *
     * @param px the X of the point to see if its inside.
     * @param py the Y of the point to see if its inside.
     * @param vertices a Pose Array of Points for the polygon.
     *
     * @implNote Uses a forloop May have unexpected Delay<br>
     * Use Exact Shape Methods if thats a issue
     *
     * @throws com.acrobotics.v1.Exception.MathExException When something goes wrong.
     *
     * Fixed 3/23/2026 TOD0 3/20/2026  Make the exception that we catch be Outputted somewhere
     *
     * @return T/F If its inside or not
     */
    public  boolean isInsidePolygon(double px, double py, Pose... vertices) {
        try {
            int numVertices = vertices.length;
            boolean inside = false;

            // Iterate through every edge of the polygon
            for (int i = 0, j = numVertices - 1; i < numVertices; j = i++) {
                double xi = vertices[i].getX(), yi = vertices[i].getY();
                double xj = vertices[j].getX(), yj = vertices[j].getY();

                // Ray casting algorithm math
                boolean intersect = ((yi > py) != (yj > py))
                        && (px < (xj - xi) * (py - yi) / (yj - yi) + xi);

                if (intersect) inside = !inside;
            }

            return inside;
        }catch(Exception e){
            RobotTrace.trace(e.getMessage());
            throw new MathExException(e.getMessage());
        }
    }

}
