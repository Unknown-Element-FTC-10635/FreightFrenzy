package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class LimitSwitchSubsystem extends SubsystemBase {
    private final DigitalChannel limitSwitch;

    public LimitSwitchSubsystem(HardwareMap hardwareMap, String name) {
        limitSwitch = hardwareMap.get(DigitalChannel.class, name);
    }

    public boolean isPressed() {
        return limitSwitch.getState();
    }
}
