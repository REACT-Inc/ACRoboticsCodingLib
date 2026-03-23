package com.acrobotics.v1.Simplify;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

/**
 * This would handle the pose the pedropathing this way we can say go to tile 1,1 etc
 *
 * Ex
 * would have a degres at the end or even landmarks?
 * DriveTo(1,1 90)
 */
public class SimplePositioningSystem {

    private Follower follower;

    /**
     * The Constructer for this
     * @param follower Takes the follower from the pedropathing
     */
    public SimplePositioningSystem(Follower follower){
        this.follower = follower;
    }


    /**
     * Automatically goes to a designated position advoiding known obstcalls that are scanned from sensors around the robot
     *
     *
     * @TODO  Make it so we can make everything on th feidl using a number of sensors around the robot to figure where stuff is
     * @param endGoal the ending for the placement for the robot
     */
    public void navigate(Pose endGoal){
        //Todo
    }

    /**
     * Goes to a position on the feild automatically in the most optimal path
     * @param endGoal the ending placement for the robot
     */
    public void goToPosition(Pose endGoal){
        //TODO
    }

    /**
     * This makes a path for you and does all of that stuff for you Cleaning up your code
     * @param startPose Starting pose
     * @param endPose End pose
     * @return the pathChain
     */
    public PathChain constPathChain(Pose startPose, Pose endPose){
        PathChain pathMade = follower.pathBuilder()
                .addPath(new BezierLine(startPose, endPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading())
                .build();
        return pathMade;
    }
}
