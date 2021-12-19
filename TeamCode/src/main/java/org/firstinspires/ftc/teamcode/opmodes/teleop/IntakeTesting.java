package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.robot.LimitSwitch;

@TeleOp
public class IntakeTesting extends LinearOpMode {
    CRServo leftIntake, rightIntake, leftIntake2, rightIntake2;
    LimitSwitch testerSwitch;

    @Override
    public void runOpMode() throws InterruptedException {
        testerSwitch = new LimitSwitch(hardwareMap, telemetry, "limit");
        leftIntake = hardwareMap.get(CRServo.class, "leftIntake");
        rightIntake = hardwareMap.get(CRServo.class, "rightIntake");
        leftIntake2 = hardwareMap.get(CRServo.class, "leftIntake2");
        rightIntake2 = hardwareMap.get(CRServo.class, "rightIntake2");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.square) {
                leftIntake.setPower(1);
                leftIntake2.setPower(-1);
                rightIntake.setPower(-1);
                rightIntake2.setPower(1);
            }

            if (gamepad1.circle) {
                leftIntake.setPower(0);
                leftIntake2.setPower(0);
                rightIntake.setPower(0);
                rightIntake.setPower(0);
            }

            telemetry.addData("Switch Status: ", testerSwitch.isPressed());
            telemetry.update();
        }
    }
}
