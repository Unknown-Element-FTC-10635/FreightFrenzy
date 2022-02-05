package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DuckWheelSubsystem {
    private final Motor ducky;

    public DuckWheelSubsystem(HardwareMap hardwareMap) {
        ducky = new Motor(hardwareMap, "ducky", Motor.GoBILDA.RPM_435);
    }

    /**
     * Spins the wheel left
     */
    public void left() {
        ducky.set(-0.55);
    }

    /**
     * Spins the wheel right
     */
    public void right() {
        ducky.set(0.55);
    }

    /**
     * Stops the duck wheel
     */
    public void stop() {
        ducky.stopMotor();
    }

    public boolean isSpinning() { return (ducky.getCorrectedVelocity() != 0);}
}
