package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class RedWarehouse extends LinearOpMode {
    private DcMotor arm;
    private Servo leftGrab, rightGrab;
    private CRServo ducky;
    private Webcam1 webcam;

    private SampleMecanumDrive bot;

    @Override
    public void runOpMode() throws InterruptedException {
        arm = hardwareMap.get(DcMotor.class, "arm");

        leftGrab = hardwareMap.get(Servo.class, "leftGrab");
        rightGrab = hardwareMap.get(Servo.class, "rightGrab");

        ducky = hardwareMap.get(CRServo.class, "ducky");

        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(13, -62, Math.toRadians(90));
        bot.setPoseEstimate(start);

        TrajectorySequence path = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(-3.0, -47.9), Math.toRadians(120))

                .splineTo(new Vector2d(10, -60), Math.toRadians(185))
                .lineTo(new Vector2d(16, -64))
                .waitSeconds(.5)
                .back(30)
                .strafeRight(25)
                .build();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        bot.followTrajectorySequence(path);
    }
}
