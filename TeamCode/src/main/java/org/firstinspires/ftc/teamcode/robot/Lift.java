package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Optional;

public class Lift {
    private Telemetry telemetry;

    private DcMotorEx liftLeft, liftRight, extension;

    private CRServo intake;

    private ElapsedTime time;

    public Lift(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        liftLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotorEx.class, "liftRight");

        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        extension = hardwareMap.get(DcMotorEx.class, "extension");
        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake = hardwareMap.get(CRServo.class, "intake");

        time = new ElapsedTime();
        time.startTime();
    }

    public void level0(boolean returnHome) {
        final int LIFT_POSITION = 200;
        final int HORIZONTAL_POSITION = -550;

        liftExtendDeliver(LIFT_POSITION, HORIZONTAL_POSITION, returnHome);
    }

    public void level1(boolean returnHome) {
        final int LIFT_POSITION = 800;
        final int HORIZONTAL_POSITION = -800;

        liftExtendDeliver(LIFT_POSITION, HORIZONTAL_POSITION, returnHome);
    }

    public void level2(boolean returnHome) {
        final int LIFT_POSITION = 1500;
        final int HORIZONTAL_POSITION = -1100;

        liftExtendDeliver(LIFT_POSITION, HORIZONTAL_POSITION, returnHome);
    }

    public void toGroundPickUp() {
        // Reset
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Lift over obstacles
        liftLeft.setPower(0.5);
        liftRight.setPower(0.5);
        while (liftLeft.getCurrentPosition() < 100 || liftRight.getCurrentPosition() < 100) {
            telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
            telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
            telemetry.update();
        }
        liftLeft.setPower(0);
        liftRight.setPower(0);

        // Extend
        extension.setPower(-0.5);
        while (extension.getCurrentPosition() > -500) {
            telemetry.addData("Horizontal Position", extension.getCurrentPosition());
            telemetry.update();
        }
        extension.setPower(0);

        // To ground
        liftLeft.setPower(-0.5);
        liftRight.setPower(-0.5);
        while (liftLeft.getCurrentPosition() > -500 || liftRight.getCurrentPosition() > -500) {
            telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
            telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
            telemetry.update();
        }
        liftLeft.setPower(0);
        liftRight.setPower(0);

        intake.setPower(0.7);
    }

    public void resetPickUp() {
        intake.setPower(0);

        // Lift slightly over obstacles
        liftLeft.setPower(0.5);
        liftRight.setPower(0.5);
        while (liftLeft.getCurrentPosition() < 100 || liftRight.getCurrentPosition() < 100) {
            telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
            telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
            telemetry.update();
        }
        liftLeft.setPower(0);
        liftRight.setPower(0);

        // Retract
        extension.setPower(0.5);
        while (extension.getCurrentPosition() < 750) {
            telemetry.addData("Horizontal Position", extension.getCurrentPosition());
            telemetry.update();
        }
        extension.setPower(0);
    }

    private void liftExtendDeliver(int liftPosition, int horizontalPosition, boolean returnHome) {
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftLeft.setPower(0.5);
        liftRight.setPower(0.5);
        while (liftLeft.getCurrentPosition() < liftPosition || liftRight.getCurrentPosition() < liftPosition) {
            telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
            telemetry.addData("Lift Right:", liftRight.getCurrentPosition());
            telemetry.update();
        }
        liftLeft.setPower(0);
        liftRight.setPower(0);

        extension.setPower(-0.5);
        while (extension.getCurrentPosition() > horizontalPosition) {
            telemetry.addData("Horizontal Position", extension.getCurrentPosition());
            telemetry.update();
        }
        extension.setPower(0);

        // Push out element
        intake.setPower(-0.2);
        time.reset();
        while (time.milliseconds() < 1000) { }
        intake.setPower(0);

        if (returnHome) {
            deliverReturn(horizontalPosition);
        }
    }

    private void deliverReturn(int horizontalPosition) {
        // Reset
        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // Retract
        extension.setPower(0.5);
        while (extension.getCurrentPosition() < horizontalPosition * -1) {
            telemetry.addData("Horizontal Position", extension.getCurrentPosition());
            telemetry.update();
        }
        extension.setPower(0);

        // Lower
        liftLeft.setPower(-0.5);
        liftRight.setPower(-0.5);
        while (liftLeft.getCurrentPosition() > 5 || liftRight.getCurrentPosition() > 5) { }
        liftLeft.setPower(0);
        liftLeft.setPower(0);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
