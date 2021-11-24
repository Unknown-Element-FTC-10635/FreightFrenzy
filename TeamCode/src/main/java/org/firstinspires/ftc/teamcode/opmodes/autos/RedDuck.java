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
public class RedDuck extends LinearOpMode {
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

        Pose2d start = new Pose2d(-36.0, -62, Math.toRadians(90));
        bot.setPoseEstimate(start);

        TrajectorySequence path = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(-60, -37), Math.toRadians(90))

                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-36, -60, Math.toRadians(180)))
                .setReversed(false)

                .addDisplacementMarker(() -> {
                    ducky.setPower(-0.7);
                })

                .lineTo(new Vector2d(-60, -55))

                .waitSeconds(3)

                .li
        neTo(new Vector2d(-36, -62))
                .lineToLinearHeading(new Pose2d(-60, -37, Math.toRadians(90)))
                .forward(1)
                .back(1)
                .build();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        bot.followTrajectorySequence(path);
    }
}
