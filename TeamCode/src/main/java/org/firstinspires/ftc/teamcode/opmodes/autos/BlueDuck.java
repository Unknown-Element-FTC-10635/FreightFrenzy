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

@Autonomous
public class BlueDuck extends LinearOpMode {
    private Webcam1 webcam;

    private Lift lift;

    private SampleMecanumDrive bot;

    private int elementPosition = 2;

    private DcMotorEx ducky;

    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();

        ducky = hardwareMap.get(DcMotorEx.class, "ducky");

        lift = new Lift(hardwareMap, telemetry);

        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(-36.0, 62, Math.toRadians(270));
        bot.setPoseEstimate(start);

        TrajectorySequence toDuck = bot.trajectorySequenceBuilder(start)
                .setReversed(true)
                .lineTo(new Vector2d(-50, 50))
                .setReversed(false)
                .lineTo(new Vector2d(-60, 56))
                .build();

        TrajectorySequence toHub = bot.trajectorySequenceBuilder(toDuck.end())
                .lineTo(new Vector2d(-63, 40))
                .lineTo(new Vector2d(-50, 25))
                .turn(Math.toRadians(90))
                .lineTo(new Vector2d(-33, 22))

                .build();


        TrajectorySequence toSquare = bot.trajectorySequenceBuilder(toHub.end())
                .lineTo(new Vector2d(-45, 22))
                .lineTo(new Vector2d(-62, 35))
                .build();


        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        elementPosition = webcam.getElementPosition();
        webcam.stop();

        telemetry.addData("Going to level:", elementPosition);
        telemetry.update();

        lift.pushOut();

        bot.followTrajectorySequence(toDuck);
        ducky.setPower(0.45);
        sleep(2500);
        ducky.setPower(0);

        bot.followTrajectorySequence(toHub);
        navigateToLevel();

        bot.followTrajectorySequence(toSquare);
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
