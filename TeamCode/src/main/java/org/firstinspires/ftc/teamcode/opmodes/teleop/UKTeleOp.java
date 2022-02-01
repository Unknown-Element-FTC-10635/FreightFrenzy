package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.LimitSwitch;

@TeleOp(name = "TeleOp")
public class UKTeleOp extends OpMode {
    DcMotorEx leftFront, leftRear, rightRear, rightFront, ducky, liftLeft, liftRight, extension;
    LimitSwitch leftLiftSwitch, rightLiftSwitch, topSwitch;
    CRServo intake, tapeOut, tapePitch;
    Servo tapeYaw;

    ElapsedTime time;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        ducky = hardwareMap.get(DcMotorEx.class, "ducky");

        extension = hardwareMap.get(DcMotorEx.class, "extension");

        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotorEx.class, "liftRight");

        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftLiftSwitch = new LimitSwitch(hardwareMap, telemetry, "LeftLimit");
        rightLiftSwitch = new LimitSwitch(hardwareMap, telemetry, "RightLimit");
        topSwitch = new LimitSwitch(hardwareMap, telemetry, "TopLimit");

        intake = hardwareMap.get(CRServo.class, "intake");
        tapePitch = hardwareMap.get(CRServo.class, "tapePitch");
        tapeOut = hardwareMap.get(CRServo.class, "tapeOut");
        tapeYaw = hardwareMap.get(Servo.class, "tapeYaw");

        telemetry.addLine("Waiting for start");
        telemetry.update();

        time = new ElapsedTime();
        time.startTime();
    }

    @Override
    public void loop() {
        time.reset();
        leftFront.setPower(((gamepad1.left_stick_y - gamepad1.left_stick_x) - gamepad1.right_stick_x));
        leftRear.setPower(((gamepad1.left_stick_y + gamepad1.left_stick_x) - gamepad1.right_stick_x));
        rightRear.setPower(((gamepad1.left_stick_y - gamepad1.left_stick_x) + gamepad1.right_stick_x));
        rightFront.setPower(((gamepad1.left_stick_y + gamepad1.left_stick_x) + gamepad1.right_stick_x));

        tapePitch.setPower(gamepad2.right_stick_y * 0.1);
        if (gamepad2.left_stick_x != 0) {
            tapeYaw.setPosition(tapeYaw.getPosition() + (0.005 * -Math.signum(gamepad2.left_stick_x)));
        }

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        float liftPower = (gamepad2.right_trigger - gamepad2.left_trigger);
        if (leftLiftSwitch.isPressed() && liftPower > 0) {
            liftLeft.setPower(liftPower);
            liftRight.setPower(liftPower);
        } else if (!(leftLiftSwitch.isPressed())) {
            liftLeft.setPower(liftPower);
            liftRight.setPower(liftPower);
        } else {
            liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            liftLeft.setPower(0);
            liftRight.setPower(0);
        }

        if (gamepad1.left_bumper) {
            extension.setPower(0.5);
        } else if (gamepad1.right_bumper && topSwitch.isPressed()) {
            extension.setPower(-0.5);
        } else {
            extension.setPower(0);
        }

        if (gamepad1.dpad_left) {
            ducky.setPower(-0.55);
        } else if (gamepad1.dpad_right) {
            ducky.setPower(0.55);
        } else if (gamepad1.dpad_down) {
            ducky.setPower(0);
        }

        if (gamepad1.square) {
            intake.setPower(1);
        } else if (gamepad1.circle) {
            intake.setPower(-1);
        }
        if (gamepad1.cross) {
            intake.setPower(0);
        }

        if (gamepad2.x) {
            tapeOut.setPower(-1);
        } else if (gamepad2.b) {
            tapeOut.setPower(1);
        } else {
            tapeOut.setPower(0);
        }

        telemetry.addData("Left Lift:", liftLeft.getCurrentPosition());
        telemetry.addData("Right Lift:", liftRight.getCurrentPosition());
        telemetry.addData("Top Switch:", topSwitch.isPressed());
        telemetry.addData("Extension:", extension.getCurrentPosition());
        telemetry.addData("Cycle Time:", time.milliseconds());
        telemetry.update();
    }
}
