package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LiftLevel {
    private Telemetry telemetry;

    private DcMotorEx liftLeft, liftRight;

    private CRServo extension, intake;

    ElapsedTime time;

    public LiftLevel(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        time = new ElapsedTime();

        liftLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotorEx.class, "liftRight");

        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        extension = hardwareMap.get(CRServo.class, "extension");
        intake = hardwareMap.get(CRServo.class, "intake");
    }

    public void level0() {
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
        while (liftLeft.getCurrentPosition() < LIFT_POSITION0 * -1 || liftRight.getCurrentPosition() < LIFT_POSITION0 * -1) {
        }
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void level1() {
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
        while (liftLeft.getCurrentPosition() < LIFT_POSITION1 * -1 || liftRight.getCurrentPosition() < LIFT_POSITION1 * -1) {
        }
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void level2() {
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
        while (liftLeft.getCurrentPosition() < Math.abs(LIFT_POSITION2) || liftRight.getCurrentPosition() < Math.abs(LIFT_POSITION2)) {
        }
        liftLeft.setPower(0);
        liftRight.setPower(0);
    }
}
