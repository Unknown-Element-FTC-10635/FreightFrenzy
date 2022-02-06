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
public class RedDuckWarehouse extends LinearOpMode {
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

        Pose2d start = new Pose2d(-36.0, -62, Math.toRadians(90));
        bot.setPoseEstimate(start);

        TrajectorySequence toDuck = bot.trajectorySequenceBuilder(start)
                .setReversed(true)
                .lineTo(new Vector2d(-40, -44))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-60, -55, 0))
                .build();

        TrajectorySequence toHub = bot.trajectorySequenceBuilder(toDuck.end())
                .lineTo(new Vector2d(-60, -35))
                .lineTo(new Vector2d(-50, -25))
                .lineTo(new Vector2d(-32, -22))
                .build();

        TrajectorySequence toWarehouse = bot.trajectorySequenceBuilder(toHub.end())
                .lineTo(new Vector2d(-50, -25))
                .lineTo(new Vector2d(-60, -35))
                .lineToLinearHeading(new Pose2d(8, -70, -Math.toRadians(15)))
                .lineTo(new Vector2d(50, -70))
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
        ducky.setPower(-0.45);
        sleep(2500);
        ducky.setPower(0);

        bot.followTrajectorySequence(toHub);
        navigateToLevel();

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
