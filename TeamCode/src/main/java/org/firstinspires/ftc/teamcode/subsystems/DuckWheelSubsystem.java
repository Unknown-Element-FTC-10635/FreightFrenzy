package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DuckWheelSubsystem extends SubsystemBase {
    private MotorEx ducky;

    public DuckWheelSubsystem(HardwareMap hardwareMap) {
        ducky = new MotorEx(hardwareMap, "ducky", Motor.GoBILDA.RPM_435);
        ducky.setVeloCoefficients(10, 0, 1);
    }

    /**
     * Spins the wheel left
     */
    public void left() {
        ducky.set(-0.3);
    }

    /**
     * Spins the wheel right
     */
    public void right() {
        ducky.set(0.3);
    }

    public void speed(double speed) {ducky.set(speed);}

    /**
     * Stops the duck wheel
     */
    public void stop() {
        ducky.stopMotor();
    }

    public boolean isSpinning() { return (ducky.getCorrectedVelocity() != 0);}
}
