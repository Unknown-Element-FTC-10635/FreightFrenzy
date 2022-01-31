package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.Lift;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Autonomous
public class BlueDuckWarehouse extends LinearOpMode {
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

        TrajectorySequence toHub = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(-21, 39), Math.toRadians(300))
                .build();

        TrajectorySequence toDuck = bot.trajectorySequenceBuilder(toHub.end())
                .setReversed(true)
                .lineToLinearHeading(new Pose2d(-50, 50, Math.toRadians(270)))
                .setReversed(false)
                .lineTo(new Vector2d(-60, 56))
                .build();

        TrajectorySequence toWarehouse = bot.trajectorySequenceBuilder(toDuck.end())
                .lineToLinearHeading(new Pose2d(8, 70, Math.toRadians(10)))
                .lineTo(new Vector2d(50, 70))
                .build();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        elementPosition = webcam.getElementPosition();
        webcam.stop();

        telemetry.addData("Going to level:", elementPosition);
        telemetry.update();

        bot.followTrajectorySequence(toHub);
        navigateToLevel();

        bot.followTrajectorySequence(toDuck);
        ducky.setPower(0.45);

        sleep(2500);

        ducky.setPower(0);
        bot.followTrajectorySequence(toWarehouse);
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
