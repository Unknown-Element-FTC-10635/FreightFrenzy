package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.LiftLevel;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class RedWarehouse extends LinearOpMode {
    private Webcam1 webcam;

    private LiftLevel liftLevel;

    private SampleMecanumDrive bot;

    private int elementPosition = 2;

    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();

        liftLevel = new LiftLevel(hardwareMap, telemetry);

        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(13, -62, Math.toRadians(90));
        bot.setPoseEstimate(start);

        TrajectorySequence initialToHub = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(-23, -40), Math.toRadians(310))
                .build();

        TrajectorySequence toWarehouse = bot.trajectorySequenceBuilder(new Pose2d(-23, -40, Math.toRadians(310)))
                .lineToLinearHeading(new Pose2d(10, -65, 0))
                .build();

        TrajectorySequence throughGap = bot.trajectorySequenceBuilder(toWarehouse.end())
                .lineTo(new Vector2d(40, -65))
                .build();

        TrajectorySequence returnToHub = bot.trajectorySequenceBuilder(throughGap.end())
                .setReversed(true)
                .lineTo(new Vector2d(-5, -65))
                .setReversed(false)
                .splineTo(new Vector2d(0, -38), Math.toRadians(135))
                .build();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        elementPosition = webcam.getElementPosition();

        bot.followTrajectorySequence(initialToHub);
        navigateToLevel();

        for (int i = 0; i < 3; i++) {
            bot.followTrajectorySequence(toWarehouse);

            // re-localize?

            bot.followTrajectorySequence(throughGap);

            // pick up cube somehow

            bot.followTrajectorySequence(returnToHub);
            liftLevel.level2();
        }

        bot.followTrajectorySequence(toWarehouse);
        bot.followTrajectorySequence(throughGap);
    }

    private void navigateToLevel() {
        switch (elementPosition) {
            case 0:
                liftLevel.level0();
                break;

            case 1:
                liftLevel.level1();
                break;

            case 2:
                liftLevel.level2();
                break;
        }
    }
}
