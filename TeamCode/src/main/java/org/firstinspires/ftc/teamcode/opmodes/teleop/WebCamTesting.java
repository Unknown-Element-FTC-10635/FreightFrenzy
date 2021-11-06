package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.Webcam1;

@TeleOp()
public class WebCamTesting extends OpMode {
    @Override
    public void init() {
        Webcam1 webcam = new Webcam1(hardwareMap);
        webcam.startTeamelementColor();
    }

    @Override
    public void loop() {

    }
}
