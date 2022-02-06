package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.robot.Webcam1;

@Disabled
@TeleOp
public class Testing extends LinearOpMode {
    private DcMotorEx ducky;

    @Override
    public void runOpMode() throws InterruptedException {
        ducky = hardwareMap.get(DcMotorEx.class, "ducky");

        Webcam1 webcam = new Webcam1(hardwareMap);
        webcam.startDuckDectionPipeline();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.cross) {
                ducky.setPower(0.2);
            }
        }
    }
}
