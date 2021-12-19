package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.LiftLevel;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.logging.Logger;

@Autonomous
public class RedDuck extends LinearOpMode {
    private DcMotorEx ducky;
    //private Webcam1 webcam;

    private SampleMecanumDrive bot;

    private LiftLevel liftLevel;

    private int elementPosition = 2;
    private Logger log = Logger.getLogger(RedDuck.class.getName());

    @Override
    public void runOpMode() throws InterruptedException {
        /*
        webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();
         */

        ducky = hardwareMap.get(DcMotorEx.class, "ducky");

        liftLevel = new LiftLevel(hardwareMap, telemetry);

        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(-36.0, -62, Math.toRadians(90));
        bot.setPoseEstimate(start);

        TrajectorySequence path = bot.trajectorySequenceBuilder(start)
                .splineTo(new Vector2d(-23, -40), -Math.toRadians(310))

                .addDisplacementMarker(this::navigateToLevel)

                .setReversed(true)

                .splineTo(new Vector2d(-40, -55), 0)
                .setReversed(false)

                .addDisplacementMarker(() -> {
                    ducky.setPower(-0.5);
                })

                .lineTo(new Vector2d(-64, -65))

                .waitSeconds(3)

                .addDisplacementMarker(() -> {
                    ducky.setPower(0);
                })

                .lineTo(new Vector2d(-64, -40))

                .build();

        telemetry.addLine("Ready for Start");
        telemetry.update();

        log.warning("Finished Initing -- ADDED BY ME");

        waitForStart();

      //  elementPosition = webcam.getElementPosition();

        telemetry.addData("POSITION:", elementPosition);
        telemetry.update();

        bot.followTrajectorySequence(path);
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
