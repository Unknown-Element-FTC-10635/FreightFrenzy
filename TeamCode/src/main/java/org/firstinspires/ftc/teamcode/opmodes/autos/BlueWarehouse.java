package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.visionpipeline.TeamElementColor;

@Autonomous
public class BlueWarehouse extends LinearOpMode {
    private DcMotor arm;
    private Servo leftGrab, rightGrab;
    private Webcam1 webcam;

    private int barcode;
    private SampleMecanumDrive bot;

    @Override
    public void runOpMode() throws InterruptedException {
        arm = hardwareMap.get(DcMotor.class, "arm");

        leftGrab = hardwareMap.get(Servo.class, "leftGrab");
        rightGrab = hardwareMap.get(Servo.class, "rightGrab");


        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(13, 62, Math.toRadians(-90));
        bot.setPoseEstimate(start);

        TrajectorySequence path = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(3.0, 47.9), Math.toRadians(120))
                .addDisplacementMarker(() -> {
                    leftGrab.setPosition(0);
                    rightGrab.setPosition(0.4);
                })
                .splineTo(new Vector2d(13, 65), Math.toRadians(5))
                .waitSeconds(.5)
                .forward(30)
                .strafeRight(30)
                .build();

        webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        bot.followTrajectorySequence(path);
    }

}
