package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TeleOp")
public class UKTeleOp extends OpMode {
    DcMotorEx leftFront, leftRear, rightRear, rightFront;
    DcMotor arm;
    CRServo ducky;
    Servo leftGrab, rightGrab;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        arm = hardwareMap.get(DcMotor.class, "arm");

        ducky = hardwareMap.get(CRServo.class, "ducky");
        leftGrab = hardwareMap.get(Servo.class, "leftGrab");
        rightGrab = hardwareMap.get(Servo.class, "rightGrab");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addLine("Waiting for start");
        telemetry.update();

    }

    @Override
    public void loop() {
        leftFront.setPower(((gamepad1.left_stick_y - gamepad1.left_stick_x) - gamepad1.right_stick_x));
        leftRear.setPower(((gamepad1.left_stick_y + gamepad1.left_stick_x) - gamepad1.right_stick_x));
        rightRear.setPower(((gamepad1.left_stick_y - gamepad1.left_stick_x) + gamepad1.right_stick_x));
        rightFront.setPower(((gamepad1.left_stick_y + gamepad1.left_stick_x) + gamepad1.right_stick_x));

        arm.setPower(gamepad1.left_trigger - gamepad1.right_trigger);

        // OPEN
        if (gamepad1.square) {
            leftGrab.setPosition(0.35);
            rightGrab.setPosition(0.75);
        }
        // CLOSE
        if (gamepad1.circle) {
            leftGrab.setDirection(Servo.Direction.REVERSE);

            leftGrab.setPosition(0.1);
            rightGrab.setPosition(0.5);
        }

        if (gamepad1.right_bumper) {
            ducky.setPower(-0.9);
        }

        if (gamepad1.left_bumper) {
            ducky.setPower(0.9);
        }

        if (gamepad1.cross) {
            ducky.setPower(0);
        }


        telemetry.addData("Left Servo:", leftGrab.getPosition());
        telemetry.addData("Right Servo:", rightGrab.getPosition());
        telemetry.update();
    }

}
