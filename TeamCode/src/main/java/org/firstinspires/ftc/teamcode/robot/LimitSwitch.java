package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LimitSwitch {
    HardwareMap hardwareMap;
    Telemetry telemetry;
    DigitalChannel digitalChannel;


    public LimitSwitch(HardwareMap hardwareMapIn, Telemetry telemetryIn, String digitalIn) {
        try {
            hardwareMap = hardwareMapIn;
            telemetry = telemetryIn;
            digitalChannel = hardwareMap.get(DigitalChannel.class, digitalIn);
        } catch (Exception e) {
            telemetry.addLine("Unable to connect to " + digitalIn);
            telemetry.update();
        }
    }

    public boolean isPressed() {
        return digitalChannel.getState();
    }
}
