package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        // TODO: If you experience poor performance, enable this flag
        System.setProperty("sun.java2d.opengl", "true");

        // Declare a MeepMeep instance
        // With a field size of 800 pixels
        MeepMeep meepMeep = new MeepMeep(800, 60);

        // Declare a bot using RoadRunner
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set Color Scheme (BlueLight, BlueDark, RedLight, RedDark)
                .setColorScheme(new ColorSchemeBlueLight())

                // Set constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13)

                .setDimensions(13, 18)

                // Creates a trajectory
                .followTrajectorySequence(driveShim ->
                        driveShim.trajectorySequenceBuilder(new Pose2d(13, 62, Math.toRadians(-90)))
                                .splineTo(new Vector2d(0, 36), Math.toRadians(-125))
                                .waitSeconds(1.5)
                                .splineTo(new Vector2d(13, 62), Math.toRadians(0))
                                .back(20)
                                .strafeRight(50)
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
                .addEntity(myBot)
                .start();
    }
}