package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.commandgroup.DeliverToTopLevel;
import org.firstinspires.ftc.teamcode.commands.SpinCarousel;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalLiftSubsystem;

import java.util.concurrent.atomic.AtomicBoolean;

@TeleOp(name = "TeleOp")
public class UKTeleOp extends OpMode {
    DcMotorEx leftFront, leftRear, rightRear, rightFront, liftLeft, liftRight, extension;
    CRServo tapeOut;
    Servo tapeYaw, tapePitch;

    DuckWheelSubsystem duck;
    VerticalLiftSubsystem vertical;
    HorizontalLiftSubsystem horizontal;
    IntakeSubsystem intake;

    ElapsedTime time, matchTimer;

    int tapeOutValue = 0;

    @Override
    public void init() {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);

        extension = hardwareMap.get(DcMotorEx.class, "extension");

        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftLeft = hardwareMap.get(DcMotorEx.class, "liftLeft");
        liftRight = hardwareMap.get(DcMotorEx.class, "liftRight");

        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        liftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        tapeOut = hardwareMap.get(CRServo.class, "tapeOut");

        tapeYaw = hardwareMap.get(Servo.class, "tapeYaw");
        tapePitch = hardwareMap.get(Servo.class, "tapePitch");

        telemetry.addLine("Creating subsystems");
        telemetry.update();

        duck = new DuckWheelSubsystem(hardwareMap);
        vertical = new VerticalLiftSubsystem(hardwareMap, telemetry);
        horizontal = new HorizontalLiftSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);

        vertical.reset();
        horizontal.reset();

        telemetry.addLine("Waiting for start");
        telemetry.update();

        time = new ElapsedTime();
        time.startTime();
    }

    @Override
    public void start() {
        tapeYaw.setPosition(0.25);
        tapePitch.setPosition(0.5);

        matchTimer = new ElapsedTime();
        matchTimer.startTime();

        super.start();
    }

    @Override
    public void loop() {
        time.reset();

        leftFront.setPower(((gamepad1.left_stick_y - gamepad1.left_stick_x) - gamepad1.right_stick_x)*0.5);
        leftRear.setPower(((gamepad1.left_stick_y + gamepad1.left_stick_x) - gamepad1.right_stick_x)*0.5);
        rightRear.setPower(((gamepad1.left_stick_y - gamepad1.left_stick_x) + gamepad1.right_stick_x)*0.5);
        rightFront.setPower(((gamepad1.left_stick_y + gamepad1.left_stick_x) + gamepad1.right_stick_x)*0.5);

        tapePitch.setPosition(tapePitch.getPosition() + (0.004 * -Math.signum(gamepad2.right_stick_y)));
        tapeYaw.setPosition(tapeYaw.getPosition() + ((0.004 - (tapeOutValue * 0.000005)) * -Math.signum(gamepad2.left_stick_x)));

        float liftPower = (gamepad2.right_trigger - gamepad2.left_trigger)/2;
        liftLeft.setPower(liftPower);
        liftRight.setPower(liftPower);

        if (gamepad1.left_bumper) {
           extension.setPower(-0.5);
        } else if (gamepad1.right_bumper) {
            extension.setPower(0.5);
        } else {
            extension.setPower(0);
        }

        if (gamepad1.dpad_left) {
            CommandScheduler.getInstance().schedule(new ParallelRaceGroup(
                    new WaitCommand(1550),
                    new SpinCarousel(duck, false)
            ));
        } else if (gamepad1.dpad_right) {
            CommandScheduler.getInstance().schedule(new ParallelRaceGroup(
                    new WaitCommand(1550),
                    new SpinCarousel(duck, true)
            ));
        }

        CommandScheduler.getInstance().run();

        intake.in();
        if (gamepad1.circle) {
            intake.out();
        } else if (gamepad1.cross) {
            intake.stop();
        }

        if (gamepad2.square) {
            tapeOutValue--;
            tapeOut.setPower(-1);
        } else if (gamepad2.circle) {
            tapeOutValue++;
            tapeOut.setPower(1);
        } else {
            tapeOut.setPower(0);
        }

        /*
        Telemetry information to be printed
         */
        telemetry.addData("MATCH TIMER", (int)matchTimer.seconds());
        telemetry.addData("Tape Out", tapeOutValue);
        telemetry.addData("Tape Pitch", tapePitch.getPosition());
        telemetry.addData("Tape Yaw", tapeYaw.getPosition());
        telemetry.addData("Cycle Time", time.milliseconds());
        telemetry.update();
    }
}
