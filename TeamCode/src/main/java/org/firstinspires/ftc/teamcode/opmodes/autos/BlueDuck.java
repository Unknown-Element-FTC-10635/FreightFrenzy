package org.firstinspires.ftc.teamcode.opmodes.autos;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.robot.LimitSwitch;
import org.firstinspires.ftc.teamcode.robot.Webcam1;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous
public class BlueDuck extends LinearOpMode {
    private DcMotorEx ducky, liftLeft, liftRight;
    private Webcam1 webcam;

    private CRServo extension, intake;

    private SampleMecanumDrive bot;

    LimitSwitch topSwitch;

    ElapsedTime time;
    private int elementPosition = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();

        time = new ElapsedTime();

        ducky = hardwareMap.get(DcMotorEx.class, "ducky");

        liftLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotorEx.class, "liftRight");

        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        extension = hardwareMap.get(CRServo.class, "extension");
        intake = hardwareMap.get(CRServo.class, "intake");

        topSwitch = new LimitSwitch(hardwareMap, telemetry, "TopLimit");

        bot = new SampleMecanumDrive(hardwareMap);

        Pose2d start = new Pose2d(-36.0, 62, Math.toRadians(270));
        bot.setPoseEstimate(start);

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



        telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
        telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
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
                final int LIFT_POSITION0 = -200;

                liftLeft.setPower(0.5);
                liftRight.setPower(0.5);

                extension.setPower(-1);

                time.reset();
                while (time.milliseconds() < 2250) {
                    telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
                    telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
                    telemetry.addData("Time:", time.milliseconds());
                    telemetry.update();

                    if (liftLeft.getCurrentPosition() < LIFT_POSITION0 || liftRight.getCurrentPosition() < LIFT_POSITION0) {
                        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }
                }
                extension.setPower(0);

                // Push out element
                intake.setPower(-0.2);
                time.reset();
                while (time.milliseconds() < 1000) {
                }
                intake.setPower(0);

                // Retract
                extension.setPower(1);
                time.reset();
                while (time.milliseconds() < 2000) {
                }
                extension.setPower(0);

                liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                // Raise
                liftLeft.setPower(-0.5);
                liftRight.setPower(-0.5);
                while (liftLeft.getCurrentPosition() < LIFT_POSITION0 * -1 || liftRight.getCurrentPosition() < LIFT_POSITION0 * -1) {}
                liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                break;

            case 1:
                final int LIFT_POSITION1 = -500;

                liftLeft.setPower(0.5);
                liftRight.setPower(0.5);

                extension.setPower(-1);

                time.reset();
                while (time.milliseconds() < 3000) {
                    telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
                    telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
                    telemetry.addData("Time:", time.milliseconds());
                    telemetry.update();

                    if (liftLeft.getCurrentPosition() < LIFT_POSITION1 || liftRight.getCurrentPosition() < LIFT_POSITION1) {
                        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }
                }
                extension.setPower(0);

                // Push out element
                intake.setPower(-0.2);
                time.reset();
                while (time.milliseconds() < 1000) {
                }
                intake.setPower(0);

                // Retract
                extension.setPower(1);
                time.reset();
                while (time.milliseconds() < 2000) {
                }
                extension.setPower(0);

                liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                // Raise
                liftLeft.setPower(-0.5);
                liftRight.setPower(-0.5);
                while (liftLeft.getCurrentPosition() < LIFT_POSITION1 * -1 || liftRight.getCurrentPosition() < LIFT_POSITION1 * -1) {}
                liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                break;

            case 2:
                final int EXTENSION_TIME2 = 3000;
                final int LIFT_POSITION2 = -900;

                liftLeft.setPower(1);
                liftRight.setPower(1);

                extension.setPower(-1);

                time.reset();
                while (time.milliseconds() < EXTENSION_TIME2) {
                    telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
                    telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
                    telemetry.addData("Time:", time.milliseconds());
                    telemetry.update();

                    if (liftLeft.getCurrentPosition() < LIFT_POSITION2 || liftRight.getCurrentPosition() < LIFT_POSITION2) {
                        liftLeft.setPower(0);
                        liftRight.setPower(0);

                        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }
                }
                extension.setPower(0);

                intake.setPower(-0.25);
                time.reset();
                while (time.milliseconds() < 1000) {
                }
                intake.setPower(0);

                extension.setPower(1);
                time.reset();
                while (time.milliseconds() < EXTENSION_TIME2) {
                }
                extension.setPower(0);

                liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                liftLeft.setPower(-1);
                liftRight.setPower(-1);
                while(liftLeft.getCurrentPosition() < Math.abs(LIFT_POSITION2) || liftRight.getCurrentPosition() < Math.abs(LIFT_POSITION2)) {
                }
                liftLeft.setPower(0);
                liftRight.setPower(0);

                break;
        }
    }
}
