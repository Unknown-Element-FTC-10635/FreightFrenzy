package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.LiftLevel;
import org.firstinspires.ftc.teamcode.robot.LimitSwitch;

@TeleOp
public class Testing extends LinearOpMode {
    private LiftLevel liftLevel;

    @Override
    public void runOpMode() throws InterruptedException {
        liftLevel = new LiftLevel(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.square) {
                liftLevel.level0();
            }
            if (gamepad1.cross) {
                liftLevel.level1();
            }
            if (gamepad1.circle) {
                liftLevel.level2();
            }

        }
    }
}
