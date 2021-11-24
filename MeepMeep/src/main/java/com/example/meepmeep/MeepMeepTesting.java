package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        // TODO: If you experience poor performance, enable this flag
        //System.setProperty("sun.java2d.opengl", "true");

        // Declare a MeepMeep instance
        // With a field size of 800 pixels
        MeepMeep meepMeep = new MeepMeep(800, 60);

        // Declare a bot using RoadRunner
        RoadRunnerBotEntity blueWarehouse = new DefaultBotBuilder(meepMeep)
                // Set Color Scheme (BlueLight, BlueDark, RedLight, RedDark)
                .setColorScheme(new ColorSchemeBlueLight())

                // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)

                .setDimensions(12.5, 18)

                // Creates a trajectory
                .followTrajectorySequence(driveShim ->
                        driveShim.trajectorySequenceBuilder(new Pose2d(13, 62, Math.toRadians(-90)))
                                .splineTo(new Vector2d(5, 45), Math.toRadians(-120))
                                .lineTo(new Vector2d(0, 38))
                                .waitSeconds(1.5)
                                .setReversed(true)
                                .splineTo(new Vector2d(13, 63), Math.toRadians(0))
                                .setReversed(false)
                                .back(25)
                                .waitSeconds(1)
                                .build()
                );

        // Declare a bot using RoadRunner
        RoadRunnerBotEntity blueSquare = new DefaultBotBuilder(meepMeep)
                // Set Color Scheme (BlueLight, BlueDark, RedLight, RedDark)
                .setColorScheme(new ColorSchemeBlueLight())

                // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)

                .setDimensions(12.5, 18)

                // Creates a trajectory
                .followTrajectorySequence(driveShim ->
                        driveShim.trajectorySequenceBuilder(new Pose2d(-36.0, 62, Math.toRadians(270)))
                                .splineTo(new Vector2d(-60.5, 35.6), -Math.toRadians(90))
                                .waitSeconds(1.5)

                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(-36, 62, Math.toRadians(180)))
                                .setReversed(false)

                                .lineTo(new Vector2d(-61, 64))
                                .waitSeconds(3)

                                .lineTo(new Vector2d(-36, 62))
                                .splineTo(new Vector2d(-60.5, 35.6), Math.toRadians(0))

                                .build()
                );

        RoadRunnerBotEntity redWarehouse = new DefaultBotBuilder(meepMeep)
                .setColorScheme(new ColorSchemeRedLight())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)

                .setDimensions(12.5, 18)
                .followTrajectorySequence(driveShim ->
                                driveShim.trajectorySequenceBuilder(new Pose2d(13, -62, Math.toRadians(90)))
                                        .splineTo(new Vector2d(3.0, -47.9), Math.toRadians(120))

                                        .splineTo(new Vector2d(13, -65), Math.toRadians(5))
                                        .waitSeconds(.5)
                                        .forward(30)
                                        .strafeLeft(25)
                                        .build()
                );

        RoadRunnerBotEntity redSquare = new DefaultBotBuilder(meepMeep)
                // Set Color Scheme (BlueLight, BlueDark, RedLight, RedDark)
                .setColorScheme(new ColorSchemeRedLight())

                // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)

                .setDimensions(12.5, 18)

                // Creates a trajectory
                .followTrajectorySequence(driveShim ->
                                driveShim.trajectorySequenceBuilder(new Pose2d(-36.0, -62, Math.toRadians(90)))
                                        .splineTo(new Vector2d(-61, -37), Math.toRadians(90))

                                        .setReversed(true)
                                        .lineToLinearHeading(new Pose2d(-36, -60, Math.toRadians(180)))
                                        .setReversed(false)

                                        .lineTo(new Vector2d(-60, -60))

                                        .waitSeconds(3)

                                        .lineTo(new Vector2d(-36, -62))
                                        .lineToLinearHeading(new Pose2d(-60, -35, Math.toRadians(90)))
                                        .forward(1)
                                        .back(1)
                                        .build()
                );

            meepMeep
                // Set field image
                .setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_OFFICIAL)

                // Set Color Scheme (BlueLight, BlueDark, RedLight, RedDark)
                .setTheme(new ColorSchemeBlueLight())

                // Background opacity from 0-1
                .setBackgroundAlpha(0.95f)

                // Adds the bot
                .addEntity(blueWarehouse)
                .addEntity(blueSquare)
                .addEntity(redWarehouse)
                .addEntity(redSquare)
                .start();
    }
}