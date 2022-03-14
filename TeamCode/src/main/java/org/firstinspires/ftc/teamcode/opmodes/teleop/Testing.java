package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimitSwitchSubsystem;

@TeleOp
public class Testing extends LinearOpMode {
    IntakeSubsystem intake;
    LimitSwitchSubsystem liftLimit, topLimit;

    @Override
    public void runOpMode() throws InterruptedException {
        intake = new IntakeSubsystem(hardwareMap);
        topLimit = new LimitSwitchSubsystem(hardwareMap, "topLimit");
        liftLimit = new LimitSwitchSubsystem(hardwareMap, "liftLimit");

        waitForStart();

        intake.in();

        while (opModeIsActive()) {
            int[] rawColor = intake.rawColor();

            telemetry.addData("Has Cube", intake.hasElement());
            telemetry.addData("Just Intaken", intake.justIntaken());
            telemetry.addLine();
            telemetry.addData("R", rawColor[0]);
            telemetry.addData("G", rawColor[1]);
            telemetry.addData("B", rawColor[2]);
            telemetry.addLine();
            telemetry.addData("Top Limit", topLimit.isPressed());
            telemetry.addData("Lift Limit", liftLimit.isPressed());
            telemetry.update();
        }
    }
}
