package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HorizontalLiftSubsystem extends SubsystemBase {
    private final Motor extension;

    public HorizontalLiftSubsystem(HardwareMap hardwareMap) {
        extension = hardwareMap.get(Motor.class, "extension");
        extension.setRunMode(Motor.RunMode.PositionControl);
    }

    public void extendToPosition(int position) {
        extension.setTargetPosition(position);
    }

    public void in() {
        extension.set(0.5);
    }

    public void out() {
        extension.set(0);
    }

    public void stop() {
        extension.stopMotor();
    }

    public int getPosition() {
        return extension.getCurrentPosition();
    }

    public boolean atTargetPosition() {
        return extension.atTargetPosition();
    }
}
