package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Lift;

@TeleOp
public class Testing extends LinearOpMode {
    private Lift lift;

    @Override
    public void runOpMode() throws InterruptedException {
        lift = new Lift(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.square) {
                lift.level0(true);
            }
            if (gamepad1.cross) {
                lift.level1(true);
            }
            if (gamepad1.circle) {
                lift.level2(true);
            }
            if (gamepad1.triangle) {
                lift.toGroundPickUp();
            }
        }
    }
}
