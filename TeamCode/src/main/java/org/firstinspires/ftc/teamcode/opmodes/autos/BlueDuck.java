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
public class BlueDuck extends LinearOpMode {
    private DcMotorEx ducky;
    private Webcam1 webcam;

    private SampleMecanumDrive bot;

    private Lift lift;

    private int elementPosition = -1;
    private Logger log = Logger.getLogger(BlueDuck.class.getName());

    @Override
    public void runOpMode() throws InterruptedException {
        log.info("Initializing Webcam");
        webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();
        log.info("Finished Initializing Webcam");

        ducky = hardwareMap.get(DcMotorEx.class, "ducky");

        lift = new Lift(hardwareMap, telemetry);

        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(-36.0, 62, Math.toRadians(270));
        bot.setPoseEstimate(start);

        TrajectorySequence toHub = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(-23, 40), Math.toRadians(310))
                .build();

        TrajectorySequence toDuck = bot.trajectorySequenceBuilder(toHub.end())
                .setReversed(true)
                .splineTo(new Vector2d(-40, 55), Math.toRadians(180))
                .setReversed(false)
                .lineToLinearHeading(new Pose2d(-64, 59, Math.toRadians(90)))
                .build();

        TrajectorySequence path = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(-23, 40), Math.toRadians(310))

                .addDisplacementMarker(this::navigateToLevel)

                .setReversed(true)

                .splineTo(new Vector2d(-40, 55), Math.toRadians(180))
                .setReversed(false)

                .addDisplacementMarker(() -> {
                    ducky.setPower(0.5);
                })

                .lineToLinearHeading(new Pose2d(-64, 59, Math.toRadians(90)))

                .waitSeconds(3)

                .addDisplacementMarker(() -> {
                    ducky.setPower(0);
                })

                .lineTo(new Vector2d(-64, 40))

                .build();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        waitForStart();

        elementPosition = webcam.getElementPosition();

        telemetry.addData("POSITION:", elementPosition);
        telemetry.update();

        bot.followTrajectorySequence(path);
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
