package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Lift;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.logging.Logger;

@Autonomous
public class RedDuck extends LinearOpMode {
    private Webcam1 webcam;

    private Lift lift;

    private SampleMecanumDrive bot;

    private int elementPosition = 2;

    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();

        lift = new Lift(hardwareMap, telemetry);

        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(13, -62, Math.toRadians(90));
        bot.setPoseEstimate(start);

        TrajectorySequence initialToHub = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(2, -36), Math.toRadians(140))
                .build();

        TrajectorySequence toWarehouse = bot.trajectorySequenceBuilder(initialToHub.end())
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(10, -67, 0))
                .build();

        TrajectorySequence throughGap = bot.trajectorySequenceBuilder(toWarehouse.end())
                .lineTo(new Vector2d(45, -67))
                .waitSeconds(0.5)
                .build();

        TrajectorySequence returnToHub = bot.trajectorySequenceBuilder(throughGap.end())
                .setReversed(true)
                .lineTo(new Vector2d(-11, -67))
                .setReversed(false)
                .turn(Math.toRadians(90))
                .forward(17)
                .build();

        TrajectorySequence finalToWarehouse = bot.trajectorySequenceBuilder(returnToHub.end())
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(8, -68, -Math.toRadians(15)))
                .lineTo(new Vector2d(45, -68))
                .strafeLeft(25)
                .build();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        elementPosition = webcam.getElementPosition();
        webcam.stop();

        telemetry.addData("Going to level:", elementPosition);
        telemetry.update();

        bot.followTrajectorySequence(initialToHub);
        navigateToLevel();

        bot.followTrajectorySequence(toWarehouse);

        // re-localize?

        lift.toGroundPickUp();

        bot.followTrajectorySequence(throughGap);

        lift.resetPickUp();

        bot.followTrajectorySequence(returnToHub);
        lift.level2(false);

        bot.followTrajectorySequence(finalToWarehouse);
    }

    private void navigateToLevel() {
        switch (elementPosition) {
            case 0:
                lift.level0(true);
                break;

            case 1:
                lift.level1(true);
                break;

            case 2:
                lift.level2(true);
                break;
        }
    }
}
