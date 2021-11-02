package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

public class TrajectoryTesting {
    public static TrajectorySequence traj(DriveShim driveShim) {
        TrajectorySequence drive = driveShim.trajectorySequenceBuilder(new Pose2d(-36.0, 62, Math.toRadians(270)))
                .splineTo(new Vector2d(-23.8, 36.8), Math.toRadians(-60))
                .waitSeconds(1.5)
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-36, 62, Math.toRadians(180)))
                .setReversed(false)
                .lineTo(new Vector2d(-61, 64))
                .waitSeconds(3)
                .lineTo(new Vector2d(-36, 62))
                .splineTo(new Vector2d(-60.5, 35.6), Math.toRadians(90))
                //.back(25)
                .build();

        return drive;
    }
}
