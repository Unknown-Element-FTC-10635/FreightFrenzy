package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.robot.LimitSwitch;

@TeleOp
public class Testing extends LinearOpMode {
    LimitSwitch testerSwitch;
    ModernRoboticsI2cRangeSensor rangeSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        testerSwitch = new LimitSwitch(hardwareMap, telemetry, "limit");
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "leftDistance");

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("UltraSonic Distance Sensors:", rangeSensor.getDistance(DistanceUnit.CM));
            telemetry.addData("Switch Status: ", testerSwitch.isPressed());
            telemetry.update();
        }
    }
}
