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


    /**
     * This makes a path for you and does all of that stuff for you Cleaning up your code
     * @param follower The pedropathing follower
     * @param startPose Starting pose
     * @param endPose End pose
     * @return the pathChain
     */
    public PathChain constPathChain(Follower follower, Pose startPose, Pose endPose){
        PathChain pathMade = follower.pathBuilder()
                .addPath(new BezierLine(startPose, endPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading())
                .build();
        return pathMade;
    }
}
