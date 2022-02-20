package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@TeleOp
public class DelaySetter extends OpMode {
    PrintWriter delayFile;

    int time;

    @Override
    public void init() {
        try {
            delayFile = new PrintWriter(new File("delay.txt"));
            delayFile.print(0);

            telemetry.addLine("Delay is currently set to 0 \n Click play to increase delay");
            telemetry.update();

        } catch (IOException e) {
            telemetry.addLine("Error when accessing file " + e.getMessage());
            telemetry.update();
        }

        GamepadEx gamepad = new GamepadEx(gamepad1);
        GamepadButton increaseTime = new GamepadButton(gamepad, GamepadKeys.Button.B);
        GamepadButton decreaseTime = new GamepadButton(gamepad, GamepadKeys.Button.X);

        increaseTime.whileHeld(() -> time++);
        decreaseTime.whileHeld(() -> time--);
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Current Time Delay (Seconds)", time);
        telemetry.update();
    }

    @Override
    public void stop() {
        delayFile.print(time);

        super.stop();
    }
}
