package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;

@TeleOp(name = "TeleOp")
public class UKTeleOp extends OpMode {
    DcMotorEx leftFront, leftRear, rightRear, rightFront, extension;
    CRServo tapeOut, tapePitch;
    Servo tapeYaw;

    HorizontalLiftSubsystem liftSubsystem;
    IntakeSubsystem intake;
    DuckWheelSubsystem ducky;
    LimitSwitchSubsystem leftLiftSwitch, rightLiftSwitch, topSwitch;

    ElapsedTime time;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);


        extension = hardwareMap.get(DcMotorEx.class, "extension");

        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftSubsystem = new HorizontalLiftSubsystem(hardwareMap);
        ducky = new DuckWheelSubsystem(hardwareMap);
        leftLiftSwitch = new LimitSwitchSubsystem(hardwareMap, "LeftLimit");
        rightLiftSwitch = new LimitSwitchSubsystem(hardwareMap, "RightLimit");
        topSwitch = new LimitSwitchSubsystem(hardwareMap, "TopLimit");
        intake = new IntakeSubsystem(hardwareMap);

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
        tapeYaw.setPosition(tapeYaw.getPosition() + (0.005 * Math.signum(gamepad2.left_stick_x)));

        float liftPower = gamepad2.right_trigger - gamepad2.left_trigger;
        if (leftLiftSwitch.isPressed() || leftLiftSwitch.isPressed()) {
            liftPower = Math.abs(liftPower);
        }
        liftSubsystem.lift(liftPower);

        if (gamepad1.left_bumper) {
            extension.setPower(0.5);
        } else if (gamepad1.right_bumper && topSwitch.isPressed()) {
            extension.setPower(-0.5);
        } else {
            extension.setPower(0);
        }

        // Handles the duck carousel
        if (gamepad1.dpad_left) {
            ducky.left();
        } else if (gamepad1.dpad_right) {
            ducky.right();
        } else if (gamepad1.dpad_down) {
            ducky.stop();
        }

        // Handles the intake
        if (gamepad1.square) {
            intake.in();
        } else if (gamepad1.circle) {
            intake.out();
        }
        if (gamepad1.cross) {
            intake.stop();
        }

        // Handles the tape measure extension and retraction
        if (gamepad2.x) {
            tapeOut.setPower(-1);
        } else if (gamepad2.b) {
            tapeOut.setPower(1);
        } else {
            tapeOut.setPower(0);
        }

        telemetry.addData("Lift:", liftSubsystem.getEncoder());
        telemetry.addData("Top Switch:", topSwitch.isPressed());
        telemetry.addData("Extension:", extension.getCurrentPosition());
        telemetry.addData("Cycle Time:", time.milliseconds());
        telemetry.update();
    }
}
